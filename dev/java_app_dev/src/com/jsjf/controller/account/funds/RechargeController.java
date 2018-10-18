package com.jsjf.controller.account.funds;

import com.SensorsAnalytics.SensorsAnalytics;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.*;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.system.SysArticle;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.member.DrMemberBankService;
import com.jsjf.service.member.DrMemberCrushService;
import com.jsjf.service.member.DrMemberFundsService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.system.SysArticleService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jytpay.AppException;
import com.jytpay.utils.DateTimeUtils;
import com.jzh.FuiouConfig;
import com.reapal.config.ReapalConfig;
import com.reapal.utils.Decipher;
import com.reapal.utils.ReapalSubmit;
import com.sftpay.config.ExpressGlobalConfig;
import com.sftpay.utils.BaseExpressService;
import net.sf.json.JSONArray;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequestMapping("/recharge")
@Controller
public class RechargeController {
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private DrMemberCrushService payService;
	@Autowired
	private DrMemberFundsService drMemberFundsService;
	@Autowired
	private DrMemberBankService drMemberBankService;
	@Autowired
	public RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	public SysArticleService sysArticleService;
	@Autowired
	public DrMemberCrushService drMemberCrushService;
	@Autowired
	public SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	public DrMemberBankService srMemberBankService;
	
	/**
	 * 充值直连
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/fuiouFastRecharg",method=RequestMethod.POST)
	@ResponseBody
	public String fuiouFastRecharg(HttpServletRequest req,Integer uid,Integer channel,String yzm,String amt,String order) {
		BaseResult br = new BaseResult();
		DrMember m = drMemberService.selectByPrimaryKey(uid);
        SensorsAnalytics instance = SensorsAnalyticsUtil.getInstance();//充值成功
        Map<String, Object> properties = new HashMap<String, Object>();
        SensorsAnalytics instance2 = SensorsAnalyticsUtil.getInstance();//设置用户属性
        Map<String, Object> properties2 = new HashMap<String, Object>();
		Map<String,Object> dateMap = new HashMap<String, Object>();
		dateMap.put("payTime",System.currentTimeMillis());
		String fSS = null;
		boolean relockFlag = false;
        String value = String.valueOf(System.currentTimeMillis());
        try {
            //判断客户是否是首冲
            if (Utils.isObjectNotEmpty(uid)){
                properties2.put("uid",uid);
                List<DrMemberCrush> list=drMemberCrushService.selectCrushByUid(properties2);
                properties2.clear();
                if (list==null || 0==list.size()){//是首冲
                    properties2.put("first_recharge_time",new Date());
                    instance2.profileSet(String.valueOf(uid),true,properties2);
                    instance2.flush();
                }
            }
            DrMemberBank drMemberBank = drMemberBankService.selectFuiouIdentificationBank(uid);
            properties.put("RechargeAmount",new BigDecimal(amt));
            properties.put("BankName",drMemberBank.getBankName());
            properties.put("BankCard",drMemberBank.getBankNum());
            properties.put("RechargeMode","快捷充值");
            properties.put("RechargeAmounts","富友快捷充值");
            if(!Utils.strIsNull(yzm)  && Utils.isObjectNotEmpty(m) && !Utils.strIsNull(order) && !Utils.strIsNull(amt)){
                fSS = "fSS_"+m.getUid();
//				redisClientTemplate.expire(fSS, 180);//防止订单处理中 失效
                DrMemberCrush dmc = drMemberCrushService.getDrMemberCrushByPayNum(order);
                if(Utils.isObjectNotEmpty(dmc) && dmc.getAmount().compareTo(new BigDecimal(amt))==0){
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("mchnt_txn_ssn", order);
                    map.put("txn_date", Utils.format(new Date(), "yyyyMMdd"));//交易日期
                    map.put("yzm", yzm);//验证码
                    map.put("login_id", m.getMobilephone());//M交易用户
//					map.put("bank_mobile", bank_mobile);//o
                    map.put("channel", channel+"");

                    dateMap.put("confirmTime",System.currentTimeMillis());
                    //TODO 这个地方没有redis锁经常报错
                    br = FuiouConfig.fastRecharg(map);//
					relockFlag  = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash+m.getUid(),  SystemConstant.TIME_OUT, TimeUnit.SECONDS,true,value);// 枷锁
					
					if(relockFlag){
						String resp_code = "";
						String resp_desc = "";
						dmc = drMemberCrushService.getDrMemberCrushByPayNum(order);//获取最新的充值数据
						if(dmc.getStatus() ==0){
							resp_code =(String)br.getMap().get("resp_code");
							resp_desc =(String)br.getMap().get("resp_desc");
							resp_desc = Utils.strIsNull(resp_desc)?getFuiouCode(resp_code):resp_desc;
							
							if(br.isSuccess()){
								if( "0000".equals(br.getMap().get("resp_code"))){
									//插入充值记录
									dmc.setStatus(1);
									dmc.setRemark("成功");
									br.setSuccess(true);
                                    properties.put("IsSuccess",true);
								}else{
									dmc.setStatus(2);
									dmc.setRemark(resp_code+"|"+resp_desc);
                                    properties.put("IsSuccess",false);
									br.setSuccess(false);
								}
							}else{//失败
								resp_desc = resp_desc ==null?"系统错误":resp_desc;
								dmc.setRemark(resp_code+"|"+resp_desc);
                                properties.put("IsSuccess",false);
							}
							br.setErrorMsg(resp_desc);
							br.setErrorCode(resp_code);
							
							drMemberCrushService.depositsRecharge(dmc, m);//充值数据处理
							 //微信模板消息
                            /*String openId = drMemberService.queryOpenId(uid);
                            ModelPassivityMessageSendUtil modelMessage = new ModelPassivityMessageSendUtil();
                            DrMemberFunds drMemberFunds = drMemberFundsService.selectDrMemberFundsByUid(uid);
                            modelMessage.payJson(openId,Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                            		amt, drMemberFunds.getFuiou_balance().toString());*/
						}else if(dmc.getStatus() ==1){
							br.setMsg("成功");
                            properties.put("IsSuccess",true);
							br.setSuccess(true);
						}else{
                            properties.put("IsSuccess",false);
							br.setSuccess(false);
							br.setErrorMsg("失败");
						}
					}else{
                        properties.put("IsSuccess",false);
						br.setErrorMsg("系统超时");
					}
				}else{
                    properties.put("IsSuccess",false);
					br.setErrorMsg("订单号不存在");
				}
			}else{
                properties.put("IsSuccess",false);
				br.setErrorMsg("参数错误或登录超时");
			}
		} catch (Exception e) {
			log.error("充值直连",e);
			br.setSuccess(false);
            properties.put("IsSuccess",false);
			e.printStackTrace();
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
		}finally{
			br.setMap(null);
			if(!Utils.strIsNull(order) && !Utils.strIsNull(fSS)){
				redisClientTemplate.del(fSS);//删掉 标识继续充值
			}
			if(relockFlag){
                redisClientTemplate.releaseLock(ConfigUtil.AboutTheCash + uid, value);//释放枷锁
			}
		}
        try{
            instance.track(String.valueOf(uid), true, "RechargeResult" ,properties);
            instance.flush();
        }catch (Exception e){
            log.error("神策埋点报错!:"+e.getMessage());
        }
		dateMap.put("paySuccessTime",System.currentTimeMillis());
		br.setMap(dateMap);
		return JSON.toJSONString(br);
	}
	/**
	 * 充值验证码直连
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/fuiouSendSms",method=RequestMethod.POST)
	@ResponseBody
	public String fuiouSendSms(HttpServletRequest req,Integer uid,Integer channel,String amt,String bank_mobile){
		BaseResult br = new BaseResult();
		DrMember m = drMemberService.selectByPrimaryKey(uid);
		Map<String,Object> dateMap = new HashMap<String, Object>();
		try {
			if(!Utils.strIsNull(amt) && !Utils.strIsNull(bank_mobile) &&  Utils.isNumber(amt) && !Utils.isBlank(channel)){
				//查询卡预留手机号
				DrMemberBank bank = drMemberBankService.selectFuiouIdentificationBank(m.getUid());

	
				// 本地预留手机 !=null  and 新的手机预留手机号 是数字   就验证 是否相等 
//				if(!(!Utils.strIsNull(bank.getMobilePhone()) && Utils.isNumber(bank_mobile)) || bank_mobile.equals(bank.getMobilePhone()) ){
					
					if(!Utils.strIsNull(bank.getMobilePhone())){
						bank_mobile =  bank.getMobilePhone();
					}else if(!Utils.isNumber(bank_mobile)){
						bank_mobile = m.getMobilephone();
					}				
					
					String fSS = "fSS_"+m.getUid();
					String order =  Utils.createOrderNo(6, m.getUid(), null); //订单流水
					long l = redisClientTemplate.setnx(fSS, order);//流水号放到redis里
					if(l == 1){
						redisClientTemplate.expire(fSS, 30);// 标识符添加成功 存在30秒失效 
						Map<String,String> map = new HashMap<String, String>();
						map.put("mchnt_txn_ssn", order);
						map.put("login_id", m.getMobilephone());//M交易用户    
						map.put("amt", amt); //M交易金额   
						map.put("bank_mobile", bank_mobile);//o预留手机号
						map.put("channel", channel+"");
						
						
						br = FuiouConfig.sendSms(map);//发送手机验证吗
						
						String resp_code =(String)br.getMap().get("resp_code");
						String resp_desc =(String)br.getMap().get("resp_desc");
						resp_desc = resp_desc!=null?resp_desc:getFuiouCode(resp_code);
						
						if(br.isSuccess() ){
							if( "0000".equals(br.getMap().get("resp_code"))){
								//插入充值记录
								drMemberCrushService.insertFuiouOrder(order, 5, m.getUid(), new BigDecimal(amt),channel);
								
								dateMap.put("order", order);
								
								if(Utils.strIsNull(bank.getMobilePhone())){//如过预留手机号为空就修改
									bank.setMobilePhone(bank_mobile);
									drMemberBankService.updateDrMemberBank(bank);
								}
								
								br.setSuccess(true);
							}else{
								br.setSuccess(false);
							}
						}else{
							resp_desc = resp_desc ==null?"系统错误":resp_desc;
						}
						br.setErrorMsg(resp_desc);
						br.setErrorCode(resp_code);
					}else{
						br.setErrorMsg("订单创建频繁,30秒后重试");
					}
//				}else{
//					br.setErrorMsg("银行预留手机号错误");
//				}
//				
				
			}else{
				br.setErrorMsg("参数错误");
			}
		} catch (Exception e) {
			log.error("充值验证码直连",e);
			br.setSuccess(false);
			e.printStackTrace();
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
		}
		br.setMap(dateMap);
		return JSON.toJSONString(br);
	}
	
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(HttpServletRequest req,Integer uid){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(Utils.isObjectEmpty(member)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			DrMemberFunds drMemberFunds = drMemberFundsService.selectDrMemberFundsByUid(member.getUid());
			map.put("funds",drMemberFunds.getBalance().setScale(2));
			map.put("fundsFuiou",drMemberFunds.getFuiou_balance().setScale(2));
			map.put("isFuiou",member.getIsFuiou());
			
			if(1 == member.getRealVerify() || 1== member.getIsFuiou()){
				map.put("realName", member.getRealName().substring(0, 1) + (member.getSex()==1?"先生":"女士"));
				map.put("idCards", member.getIdCards().subSequence(0, 4) +"****"+ member.getIdCards().substring(member.getIdCards().length()-4));
				Map<String,Object> param = new HashMap<String, Object>();	
				param.put("uid", uid);
				List<SysArticle> sysArticleList = sysArticleService.getArticleByUid(param);
				map.put("sysArticleList", sysArticleList);
			}
			
			if(1 == member.getRealVerify()){
				DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
				if(Utils.isObjectNotEmpty(drMemberBank)){
					drMemberBank.setBankNum(drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
					map.put("bankNum",drMemberBank.getBankNum());   
					map.put("mobilePhone",drMemberBank.getMobilePhone().substring(0,3)+"****"+drMemberBank.getMobilePhone().substring(drMemberBank.getMobilePhone().length()-4));   
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("bankName", drMemberBank.getBankName());
					SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
					queryMap.clear();
					
					map.put("bankCode",sysBank.getId());
					if(1 == sysBank.getChannel()){
						map.put("singleQuota",sysBank.getSingleQuotaJYT());
						map.put("dayQuota",sysBank.getDayQuotaJYT());
					}else{
						map.put("singleQuota",sysBank.getSingleQuotaSFT());
						map.put("dayQuota",sysBank.getDayQuotaSFT());
					}					
				}
			}
			if(1== member.getIsFuiou()){
				DrMemberBank drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
				String bankNo = drMemberBank.getBankNum();
				map.put("bankNoFuiou", bankNo.substring(0,4)+"****"+bankNo.substring(bankNo.length()-4, bankNo.length()));
		        map.put("bankNumFuiou",bankNo.substring(bankNo.length()-4,bankNo.length())); 
		        map.put("bankMobilePhoneFuiou",Utils.strIsNull(drMemberBank.getMobilePhone())? "" : drMemberBank.getMobilePhone().substring(0,3) + "****" + drMemberBank.getMobilePhone().substring(drMemberBank.getMobilePhone().length()-4) ); 
		        
		        Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("bankName", drMemberBank.getBankName());
				SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
				queryMap.clear();	
				map.put("bankCodeFuiou",sysBank != null?sysBank.getId():0);
				
				map.put("bankNameFuiou", sysBank.getBankName());
				
				map.put("bankName", sysBank.getBankName());
				if(1 == sysBank.getChannel()){
					map.put("singleQuota",sysBank.getSingleQuotaJYT());
					map.put("dayQuota",sysBank.getDayQuotaJYT());
				}else{
					map.put("singleQuota",sysBank.getSingleQuotaSFT());
					map.put("dayQuota",sysBank.getDayQuotaSFT());
				}
			}
	
			map.put("fuiou_balance",drMemberFunds.getFuiou_balance().setScale(2));
			
			
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("充值首页", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}

	
	/**
	 * @Deprecated
	 * 金运通充值
	 * @throws Exception
	 */
	@RequestMapping("/goJYTPay")
	@ResponseBody
	public String goJYTPay(HttpServletRequest req,Integer uid,String smsCode,BigDecimal amount,Integer channel) {
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
		try{
			String code = redisClientTemplate.get("rechargeMsgCode_"+drMemberBank.getMobilePhone());
		
			if(Utils.isObjectEmpty(amount)){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			if(new BigDecimal(amount.toString()).compareTo(new BigDecimal(1))<0){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			
			if(Utils.isObjectEmpty(channel)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(smsCode)){//短信验证码为空
				br.setSuccess(false);
				br.setErrorCode("1002");
				return JSON.toJSONString(br);
			}else if(!smsCode.equals(code) || StringUtils.isBlank(code)){//验证码错误
				br.setSuccess(false);
				br.setErrorCode("1003");
				return JSON.toJSONString(br);
			}

			redisClientTemplate.lock("recharge"+member.getUid().toString());
			DrMemberCrush drMemberCrush =payService.insertDrMemberCrush(member, amount.toString(), drMemberBank,channel);
			if(Utils.isObjectNotEmpty(drMemberCrush.getId())){
				br = payService.saveJYTPay(member,amount.toString(),drMemberBank,drMemberCrush);
				redisClientTemplate.del("rechargeMsgCode_"+drMemberBank.getMobilePhone());
				redisClientTemplate.del("rechargeMsgSendTime_"+drMemberBank.getMobilePhone());
//				redisClientTemplate.del("rechargeMsgUse_"+drMemberBank.getMobilePhone());
			}else{
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
		} catch (AppException e) {
			redisClientTemplate.del("rechargeMsgCode_"+drMemberBank.getMobilePhone());
			redisClientTemplate.del("rechargeMsgSendTime_"+drMemberBank.getMobilePhone());
//			redisClientTemplate.del("rechargeMsgUse_"+drMemberBank.getMobilePhone());
			log.error("认证充值超时", e);
        	br.setSuccess(false);
        	br.setErrorCode("1004");
			return JSON.toJSONString(br);
		} catch (Exception e) {
			log.error("充值失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		return JSON.toJSONString(br);
	}
	

	/**
	 * @Deprecated
	 * 发送充值验证码
	 * @param req
	 */
	@RequestMapping("/sendRechargeMsg")
	@ResponseBody
	public String sendRechargeMsg(HttpServletRequest req,Integer uid,Integer type){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
		String mobilePhone = drMemberBank.getMobilePhone();
		try {
			if(Utils.isObjectEmpty(member)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(type) || type == 2){
				type = 1;
			}
			String redisCode = redisClientTemplate.get("rechargeMsgCode_"+mobilePhone);
			int flag = 0;
			int  seconds = 0;
			if(StringUtils.isNotEmpty(redisCode)){
				//当前时间和当前手机号码上次发送短信时间的秒数差
				seconds = Utils.getDateSecondsSub(Utils.getStrDatetime(new Date()), redisClientTemplate.get("rechargeMsgSendTime_"+mobilePhone));
				if(seconds < 59){
					if(2 == type){
						String rechargeMsgUse = redisClientTemplate.get("rechargeMsgUse_"+mobilePhone);
						if("1".equals(rechargeMsgUse)){
							br.setSuccess(false);
							br.setErrorCode("8888");//语音频繁操作
							return JSON.toJSONString(br);
						}else{
							seconds = 60;
						}
					}
				}else{
					redisCode = Utils.getRandomNumber6();//生成验证码
					//短信验证码 10分钟有效
					redisClientTemplate.setex("rechargeMsgCode_"+mobilePhone, 600, redisCode);
					//短信发送时间 10分钟有效
					redisClientTemplate.setex("rechargeMsgSendTime_"+mobilePhone, 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				}
			}else if(StringUtils.isBlank(redisCode)){//短信内容是否存在redis中
				redisCode = Utils.getRandomNumber6();//生成验证码
				seconds = 60;
				//短信验证码 10分钟有效
				redisClientTemplate.setex("rechargeMsgCode_"+mobilePhone, 600, redisCode);
				//短信发送时间 10分钟有效
				redisClientTemplate.setex("rechargeMsgSendTime_"+mobilePhone, 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
			if(seconds > 59){//两次时间间隔60秒，重新发送短信
				String content = redisClientTemplate.getProperties("rechargeCode")
						.replace("${1}",redisCode)
						.replace("${2}",drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
//				if(2 == type){
//					content = redisCode;
//					redisClientTemplate.setex("rechargeMsgUse_"+mobilePhone, 600, "1");
//				}
//				if(1 == type){
//					redisClientTemplate.del("rechargeMsgUse_"+mobilePhone);
//				}
				SysMessageLog logs = new SysMessageLog(member.getUid(),content,16, null, mobilePhone);
				flag = sysMessageLogService.sendMsg(logs,type);
			}else{
				flag = 1;
			}
			//短信是否发送成功
			if (flag > 0) {
				br.setSuccess(true);
			} else {
				br.setSuccess(false);
				br.setErrorCode("1002");//短信发送失败
			}
		} catch (Exception e) {
			log.error("短信发送失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 认证充值-创建订单
	 * @param uid 用户uid，唯一标识
	 * @param amount 充值金额
	 * @param channel 渠道
	 * @throws Exception
	 */
	@RequestMapping("/createPayOrder")
	@ResponseBody
	public String createPayOrder(HttpServletRequest req,Integer uid,BigDecimal amount,Integer channel) {
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
		if(Utils.isObjectEmpty(drMemberBank)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		try{
			if(Utils.isObjectEmpty(amount)){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			if(new BigDecimal(amount.toString()).compareTo(new BigDecimal(2))<0){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			
			if(Utils.isObjectEmpty(channel)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			
			redisClientTemplate.lock("payOrder"+member.getUid().toString());
			
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("bankName", drMemberBank.getBankName());
			SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
			
			br = payService.insertPayOrder(req,member, amount.toString(), drMemberBank,sysBank,channel);
		} catch (AppException e) {
			log.error("充值-创建订单超时", e);
        	br.setSuccess(false);
        	br.setErrorCode("1002");
			return JSON.toJSONString(br);
		} catch (Exception e) {
			log.error("充值-创建订单失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 快捷充值
	 * @param payNum 订单号
	 * @param smsCode 短信验证码
	 * @throws Exception
	 */
	@RequestMapping("/goPay")
	@ResponseBody
	public String goPay(HttpServletRequest req,Integer uid,String payNum,String smsCode,Integer channel) {
		Map<String,Object> map = new HashMap<String,Object>();
		Date payTime = new Date();
		map.put("payTime", payTime);
		BaseResult br = new BaseResult();
		
		if(Utils.isObjectEmpty(channel)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}

		DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());

		if(Utils.isObjectEmpty(payNum)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		
		redisClientTemplate.lock("recharge"+member.getUid().toString());
		
		DrMemberCrush drMemberCrush = payService.getDrMemberCrushByPayNum(payNum.toString());
		if(Utils.isObjectEmpty(drMemberCrush)){
			br.setSuccess(false);
			br.setErrorCode("1005");
			return JSON.toJSONString(br);
		}
		try{
			if(Utils.isObjectEmpty(smsCode)){//短信验证码为空
				br.setSuccess(false);
				br.setErrorCode("1003");
				return JSON.toJSONString(br);
			}
			if(1 == drMemberCrush.getType()){
				String code = redisClientTemplate.get("rechargeMsgCode_"+drMemberBank.getMobilePhone());
				if(!smsCode.equals(code) || StringUtils.isBlank(code)){//验证码错误
					br.setSuccess(false);
					br.setErrorCode("1003");
					return JSON.toJSONString(br);
				}
			}
			
			if(1 == drMemberCrush.getType()){
				redisClientTemplate.del("rechargeMsgCode_"+drMemberBank.getMobilePhone());
				redisClientTemplate.del("rechargeMsgSendTime_"+drMemberBank.getMobilePhone());
			}
			br = payService.savePay(req,member,drMemberBank,drMemberCrush,smsCode.toString());
		} catch (AppException e) {
			if(1 == drMemberCrush.getType()){
				redisClientTemplate.del("rechargeMsgCode_"+drMemberBank.getMobilePhone());
				redisClientTemplate.del("rechargeMsgSendTime_"+drMemberBank.getMobilePhone());
			}
			log.error("认证充值超时", e);
			map.put("payTime", payTime);
			br.setMap(map);
        	br.setSuccess(false);
        	br.setErrorCode("1004");
			return JSON.toJSONString(br);
		} catch (Exception e) {
			log.error("认证充值失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		if(br.getMap()!=null){
			((Map<String,Object>)br.getMap()).putAll(map);
		}else
			br.setMap(map);
		return JSON.toJSONString(br);
	}

	/**
	 * 发送充值验证码
	 * @param req
	 * @return
	 */
	@RequestMapping("/sendRechargeSms")
	@ResponseBody
	public BaseResult sendRechargeMsg(HttpServletRequest req,Integer uid,String payNum,Integer type,Integer channel){
		BaseResult br = new BaseResult();
		if(Utils.isObjectEmpty(channel)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return br;
		}
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return br;
		}
		
		DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
		if(Utils.isObjectEmpty(drMemberBank)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return br;
		}
		String mobilePhone = drMemberBank.getMobilePhone();
		
		if(Utils.isObjectEmpty(type)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return br;
		}
		
		if(Utils.isObjectEmpty(payNum)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return br;
		}
		DrMemberCrush drMemberCrush = payService.getDrMemberCrushByPayNum(payNum.toString());
		if(Utils.isObjectEmpty(drMemberCrush)){
			br.setSuccess(false);
			br.setErrorCode("1005");
			return br;
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("bankName", drMemberBank.getBankName());
		SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
		if(Utils.isObjectEmpty(sysBank)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return br;
		}
		try {
			if(1 == drMemberCrush.getType()){
				br = sendJYTSms(mobilePhone,drMemberBank,type,member);
			}else if(4 == drMemberCrush.getType()){
				br = payService.insertPayRB(req, member, drMemberCrush.getAmount().toString(), drMemberBank, sysBank, drMemberCrush);
			}else{
				br = sendSFTSms(req,member,drMemberBank,sysBank,drMemberCrush);
			}
		} catch (AppException e) {
			log.error("短信发送失败超时", e);
        	br.setSuccess(false);
        	br.setErrorCode("9999");
		} catch (Exception e) {
			log.error("短信发送失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return br;
	}
	

	public BaseResult sendRB(String payNum,DrMemberCrush drMemberCrush) throws Exception {
		BaseResult br = new BaseResult();
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.getMerchant_id());
		map.put("version", ReapalConfig.getVersion());
		//订单号
		map.put("order_no", payNum);  
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, ReapalConfig.getrBSms());
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    net.sf.json.JSONObject jasonObject = net.sf.json.JSONObject.fromObject(res);
	    
    	if ("0000".equals(jasonObject.get("result_code"))) {
			br.setSuccess(true);
		}else{
			drMemberCrush.setStatus(2);
			br.setErrorCode("1002");
			br.setSuccess(false);
		}
		payService.updateMemberCrushById(drMemberCrush);
		return br;
	}
	
	/**
	 * 发送金运通充值验证码
	 * @param type 1-金运通短信 2-金运通语音
	 * @return BaseResult
	 * @throws Exception 
	 */
	public BaseResult sendJYTSms(String mobilePhone,DrMemberBank drMemberBank,int type,DrMember member) throws Exception{
		BaseResult br = new BaseResult();
		String redisCode = redisClientTemplate.get("rechargeMsgCode_"+mobilePhone);
		int flag = 0;
		int  seconds = 0;
		if(StringUtils.isNotEmpty(redisCode)){
			//当前时间和当前手机号码上次发送短信时间的秒数差
			seconds = Utils.getDateSecondsSub(Utils.getStrDatetime(new Date()), redisClientTemplate.get("rechargeMsgSendTime_"+mobilePhone));
			if(seconds < 59){
				if(2 == type){
					String rechargeMsgUse = redisClientTemplate.get("rechargeMsgUse_"+mobilePhone);
					if("1".equals(rechargeMsgUse)){
						br.setSuccess(false);
						br.setErrorCode("8888");//语音频繁操作
						return br;
					}else{
						seconds = 60;
					}
				}
			}else{
				redisCode = Utils.getRandomNumber6();//生成验证码		
				//短信验证码 10分钟有效
				redisClientTemplate.setex("rechargeMsgCode_"+mobilePhone, 600, redisCode);
				//短信发送时间 10分钟有效
				redisClientTemplate.setex("rechargeMsgSendTime_"+mobilePhone, 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
		}else if(StringUtils.isBlank(redisCode)){//短信内容是否存在redis中
			redisCode = Utils.getRandomNumber6();//生成验证码		
			seconds = 60;
			//短信验证码 10分钟有效
			redisClientTemplate.setex("rechargeMsgCode_"+mobilePhone, 600, redisCode);
			//短信发送时间 10分钟有效
			redisClientTemplate.setex("rechargeMsgSendTime_"+mobilePhone, 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
		if(seconds > 59){//两次时间间隔60秒，重新发送短信
			String content = redisClientTemplate.getProperties("rechargeCode")
					.replace("${1}",redisCode)
					.replace("${2}",drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
//		if(2 == type){
//			content = redisCode;
//			redisClientTemplate.setex("rechargeMsgUse_"+mobilePhone, 600, "1");
//		}
//		if(1 == type){
//			redisClientTemplate.del("rechargeMsgUse_"+mobilePhone);
//		}
			SysMessageLog logs = new SysMessageLog(member.getUid(),content,16, null, mobilePhone);
			flag = sysMessageLogService.sendMsg(logs,type);
		}else{
			flag = 1;
		}
		//短信是否发送成功
		if (flag > 0) {
			br.setSuccess(true);
		} else {
			br.setSuccess(false);
			br.setErrorCode("1002");//短信发送失败
		}
		return br;
	}
	
	/**
	 * 发送盛付通充值验证码
	 *DrMemberBank 会员银行卡信息
	 *DrMember 会员基本信息
	 *SysBank 银行卡基本信息
	 *sessionToken 支付token，用于后续的支付预校验和支付确认
	 *BaseResult
	 * @throws Exception 
	 */
	public BaseResult sendSFTSms(HttpServletRequest req,DrMember member,DrMemberBank drMemberBank,SysBank sysBank,DrMemberCrush drMemberCrush) throws Exception{
		BaseResult br = new BaseResult();
		String responseBody = "";
		//第二步.支付预校验(发送短信验证码)
		if(StringUtils.isBlank(drMemberBank.getAgreementNo())){
			NameValuePair[] smsParam = {
					new NameValuePair("merchantNo", ExpressGlobalConfig.merchantNo),
					new NameValuePair("charset", ExpressGlobalConfig.utf8),
					new NameValuePair("requestTime", Utils.getTime()),
					new NameValuePair("sessionToken",drMemberCrush.getSessionToken()),
					new NameValuePair("agreementNo", ""),
					new NameValuePair("isResendValidateCode", "false"),
					new NameValuePair("outMemberId",member.getUid().toString()),
					new NameValuePair("bankCode", sysBank.getBankJC()),
					new NameValuePair("bankCardType",ExpressGlobalConfig.bankCardType),
					new NameValuePair("bankCardNo", drMemberBank.getBankNum()),
					new NameValuePair("realName", member.getRealName()),
					new NameValuePair("idNo", member.getIdCards()),
					new NameValuePair("idType", ExpressGlobalConfig.idType),
					new NameValuePair("mobileNo", drMemberBank.getMobilePhone()),
					new NameValuePair("userIp", Utils.getIpAddr(req)),
					new NameValuePair("riskExtItems",getRiskExtItems(member,req)) };
			responseBody = BaseExpressService.httpSend(ExpressGlobalConfig.expressPrecheckForPaymentUrl, smsParam);
		}else{
			NameValuePair[] smsParam = {
				new NameValuePair("merchantNo", ExpressGlobalConfig.merchantNo),
				new NameValuePair("charset", ExpressGlobalConfig.utf8),
				new NameValuePair("requestTime", Utils.getTime()),
				new NameValuePair("sessionToken",drMemberCrush.getSessionToken()),
				new NameValuePair("agreementNo", drMemberBank.getAgreementNo()),
				new NameValuePair("isResendValidateCode", "false"),
				new NameValuePair("outMemberId",member.getUid().toString()),
				new NameValuePair("realName", member.getRealName()),
				new NameValuePair("idNo", member.getIdCards()),
				new NameValuePair("idType", ExpressGlobalConfig.idType),
				new NameValuePair("mobileNo", drMemberBank.getMobilePhone()),
				new NameValuePair("userIp", Utils.getIpAddr(req)),
				new NameValuePair("riskExtItems",getRiskExtItems(member,req)) };
			responseBody = BaseExpressService.httpSend(ExpressGlobalConfig.expressPrecheckForPaymentUrl, smsParam);
		}
		Map<String, Object> result = JSONObject.parseObject(responseBody);
		String returnMessage = result.get("returnMessage") == null ? "" : result.get("returnMessage").toString();
		String returnCode = result.get("returnCode") == null ? "" : result.get("returnCode").toString();
		String returnDetailCode = result.get("returnDetailCode") == null ? "" : result.get("returnDetailCode").toString();
		
		drMemberCrush.setRemark("支付预签约："+returnCode+"|"+returnDetailCode+"|"+returnMessage);
		if("SUCCESS".equals(result.get("returnCode"))){
			if("999999".equals(result.get("returnDetailCode"))){
				br.setSuccess(true);
			}else{
				drMemberCrush.setStatus(2);
				br.setErrorCode("1002");
				br.setSuccess(false);
			}
		}else{
			drMemberCrush.setStatus(2);
			br.setErrorCode("1002");
			br.setSuccess(false);
		}
		payService.updateMemberCrushById(drMemberCrush);
		return br;
	}
	
	// 风控数据(JSON值需要全部为字符串)
	public String getRiskExtItems(DrMember member,HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("outMemberId", member.getUid().toString()); // 商户会员ID
		map.put("outMemberRegistTime", DateTimeUtils.getDateTimeToString(member.getRegDate(), "yyyyMMddHHmmss"));  // 商户会员在商户平台的注册时间
		map.put("outMemberRegistIP", member.getRegIp() == null ? Utils.getIpAddr(req) : member.getRegIp()); // 商户会员在商户平台注册时的IP
		map.put("outMemberVerifyStatus", "1"); // 商户会员的实名认证状态
		map.put("outMemberName", member.getRealName()); // 商户会员的姓名
		map.put("outMemberMobile", member.getMobilephone()); // 商户会员的手机号
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取银行限额列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/getBankQuotaList",method = RequestMethod.POST)
	@ResponseBody
	public String getBankQuotaList(HttpServletRequest req){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			List<Map<String,Object>> list  = drMemberBankService.selectSysBankQuotaList();
			map.put("bankQuotaList", list);
			br.setSuccess(true);
			br.setMap(map);
		}catch (Exception e) {
			log.error("银行限额列表查询失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			br.setMsg("银行限额列表查询失败");
		}
		return JSON.toJSONString(br);
		
	}
	
	@RequestMapping("/depositsRecharge")
	@ResponseBody
	public String depositsRecharge(HttpServletRequest req,Integer uid,String amt,Integer channel){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		try {
			if(1==member.getIsFuiou() && Utils.isNumber(amt)){
				Map<String, Object> map = new HashMap<String, Object>();
				String order = Utils.createOrderNo(6, member.getUid(), "");	

				map.put("amt", amt);
				map.put("channel", channel == 1||channel ==2 ?"app":"wap");
				map.put("login_id", member.getMobilephone());
				map.put("mchnt_txn_ssn", order);
				map.put("icd", FuiouConfig.APPQRECHARGE2);
				
				String signature= FuiouConfig.rechargeFirst(map);
				map.clear();
				map.put("signature", signature);
				map.put("fuiouUrl", FuiouConfig.APPQRECHARGE2URL);
				//插入充值记录
				drMemberCrushService.insertFuiouOrder(order, 5, member.getUid(), new BigDecimal(amt),channel);
				br.setSuccess(true);
				br.setMap(map);
			}else{
				br.setErrorMsg("参数有误");
				br.setErrorCode("1001");
			}
		} catch (Exception e) {
			log.error("充值首页", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	/**
	 * fuiou 前端通知
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/fuiouNotice")
	public String openAccountSuccess(HttpServletRequest req,Map<String,Object> model,HttpServletResponse resp){
		resp.setCharacterEncoding("UTF-8");
		BaseResult br = new BaseResult();
		
		String wap = req.getParameter("wap");
		
		if("app".equals(wap)){
			br.setMsg("jsmp://page=999");
		}else{
			br.setMsg("/main/myaccountHome");
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		SysFuiouNoticeLog noticeLog = null;
		try {
			req.setCharacterEncoding("UTF-8");
			net.sf.json.JSONObject json = getParams(req);
			if(Utils.isObjectNotEmpty(json)){
				net.sf.json.JSONObject message = json.getJSONObject("message"); //报文正文JSON对象
				noticeLog = sysFuiouNoticeLogService.selectObjectBy_txn_ssn((String)message.get("mchnt_txn_ssn"));
				
				br.setErrorMsg(new String(java.net.URLEncoder.encode(message.containsKey("resp_desc")?(String)message.get("resp_desc"):getFuiouCode((String)message.get("resp_code")),"utf-8").getBytes()));
				
				if(message.containsKey("amt")){
					map.put("amount", FuiouConfig.centToYuan((String)message.get("amt")));
				}
				if(message.containsKey("acnts_amt")){
					JSONArray acnts_amt = message.getJSONArray("acnts_amt");
					if(acnts_amt!=null && acnts_amt.size()>0){
						map.put("amount", FuiouConfig.centToYuan(((net.sf.json.JSONObject)acnts_amt.get(0)).getString("amt")));
					}
				}
				if(Utils.isObjectNotEmpty(noticeLog)){
					if(FuiouConfig.APPWEBREG.equals(noticeLog.getIcd())){//个人开户
						map.put("type", 0);//type   0开通 1充值  2提现
					}else if(FuiouConfig.APPQRECHARGE2.equals(noticeLog.getIcd())){//充值
						map.put("type", 1);						
					}else if(FuiouConfig.APPWITHDRAW.equals(noticeLog.getIcd())){//提现异步通知
						map.put("type", 2);
						
					} else if(FuiouConfig.APPRESETPASSWORD.equals(noticeLog.getIcd())){//修改操作密码
						map.put("type", 3);
					}
					
				}
				if("0000".equals((String)message.get("resp_code"))){
					br.setSuccess(true);
				}
				
				if(json.isEmpty() || message.isEmpty() || !message.containsKey("resp_code")){
					map.put("type", 3);
					br.setSuccess(true);
				}
			}
		} catch (Exception e) {
			log.error("fuiou同步通知失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			model.put("br", br);
		}
		if(Utils.isObjectEmpty(br.getMap())){
			br.setMap(map);
		}else{
			((Map<String,Object>)br.getMap()).putAll(map);
		}
		model.put("br", br);
		return "openAccountSuccess";
	}
	/**
	 * 获得 request 请求参数
	 * @param request
	 * @return
	 */
	private net.sf.json.JSONObject getParams(HttpServletRequest request) {  
        Enumeration<String> paramNames = request.getParameterNames();
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        net.sf.json.JSONObject message = new net.sf.json.JSONObject();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();  
            String[] paramValues = request.getParameterValues(paramName);  
            if (paramValues.length == 1) {  
                String paramValue = paramValues[0];  
                if (paramValue.length() != 0) {  
                    if(!"signature".equals(paramName)){
                    	message.put(paramName, paramValue);
                    }else{
                    	json.put(paramName, paramValue);
                    }
                    	
                }  
            }  
        }
        json.put("message", message);
        log.info("-------------------------");
        log.info("恒丰2.0返回参数:"+json.toString());
        return json;
    } 
	/**
	 *  获取恒丰返回码描述
	 * @param code
	 * @return
	 */
	private String getFuiouCode(String code){
		String desc = "";
		if(!Utils.strIsNull(code)){
			try {
				desc = redisClientTemplate.hget("fuiouCode", code);
			} catch (Exception e) {
				log.error("获取恒丰返回码描述失败", e);
			}
		}
		return desc;
	}
	
}
