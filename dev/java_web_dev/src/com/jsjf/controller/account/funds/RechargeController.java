package com.jsjf.controller.account.funds;

import com.SensorsAnalytics.SensorsAnalytics;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.*;
import com.jsjf.dao.member.DrMemberLotteryLogDAO;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.system.SysArticle;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.integral.TaskIntegralRulesService;
import com.jsjf.service.member.DrMemberBankService;
import com.jsjf.service.member.DrMemberCrushService;
import com.jsjf.service.member.DrMemberFundsService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.system.SysArticleService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jytpay.AppException;
import com.jytpay.utils.DateTimeUtils;
import com.jytpay.utils.XmlMsgConstant;
import com.jzh.FuiouConfig;
import com.jzh.data.RechargeAndWithdrawalNoticeData;
import com.jzh.service.JZHService;
import com.reapal.config.ReapalConfig;
import com.reapal.utils.Decipher;
import com.reapal.utils.ReapalSubmit;
import com.sftpay.config.ExpressGlobalConfig;
import com.sftpay.utils.BaseExpressService;
import com.wechat.util.ModelPassivityMessageSendUtil;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	private DrMemberService drMemberService;
	@Autowired
	private DrMemberFundsService drMemberFundsService;
	@Autowired
	private DrMemberBankService drMemberBankService;
	@Autowired
	public RedisClientTemplate redisClientTemplate;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	public SysArticleService sysArticleService;
	@Autowired
	public DrMemberCrushService drMemberCrushService;
	@Autowired
	public SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	public DrProductInfoDAO drProductInfoDAO;
	@Autowired
	public DrMemberLotteryLogDAO drMemberLotteryLogDAO; 
	@Autowired
	public DrProductInfoService drProductInfoService;
    @Autowired
    private TaskIntegralRulesService taskIntegralRulesService;
	
	/**
	 * 充值直连异步通知
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/fuiouRechargNotice",method=RequestMethod.POST)
	@ResponseBody
	public String fuiouRechargNotice(HttpServletRequest req){
		System.out.print("PC充值异步回调===========\n");
		RechargeAndWithdrawalNoticeData noticeData = new RechargeAndWithdrawalNoticeData();
		String signature = req.getParameter("signature");
		boolean relockFlag = false;
		String resp_code = "0001";
		DrMember m = new DrMember();
        String convertvalue = "";
        Boolean convert=false;
        SensorsAnalytics instance = SensorsAnalyticsUtil.getInstance();//充值成功
        Map<String, Object> properties = new HashMap<String, Object>();
        Integer uid=0;
        String value = String.valueOf(System.currentTimeMillis());
		try {
            properties.put("IsSuccess",false);
			BeanUtils.populate(noticeData, req.getParameterMap());//
            //异步回调日志
            log.info("noticeData=====>:"+noticeData.toString());
			m = drMemberService.selectDrMemberByMobilephone(noticeData.getMobile_no());
			if(JZHService.verifySignAsynNotice(noticeData, signature)){
				if(Utils.isObjectNotEmpty(m)){
					relockFlag  = redisClientTemplate.tryLock(ConfigUtil.AboutTheCash+m.getUid(),  SystemConstant.TIME_OUT, TimeUnit.SECONDS,true,value);// 枷锁
					if(relockFlag){
						DrMemberCrush dmc = drMemberCrushService.getFuiouDrMemberCrushByPayNum(noticeData.getMchnt_txn_ssn());
						if(Utils.isObjectEmpty(dmc)){//订单不存在,客户直接在存管充值的  POS机充值
							//创建订单
							drMemberCrushService.insertFuiouOrder(noticeData.getMchnt_txn_ssn(), 7, m.getUid(), FuiouConfig.centToYuan(noticeData.getAmt()));
						
							dmc = drMemberCrushService.getDrMemberCrushByPayNum(noticeData.getMchnt_txn_ssn());	
							
							//交易成功
							if(Utils.isObjectNotEmpty(dmc) && dmc.getStatus() ==0){
								dmc.setStatus(1);
								drMemberCrushService.depositsRecharge(dmc, m);
							}
						}else if(dmc.getStatus()!=1){//订单存在且不是成功的
							dmc.setStatus(1);
							dmc.setRemark("成功|异步通知");
                            convertvalue=String.valueOf(System.currentTimeMillis());
                            convert = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + m.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, convertvalue);
                            if(convert){
                                drMemberCrushService.depositsRecharge(dmc, m);
                            }
                            //神策埋点
                            uid=m.getUid();
                            log.info("用户id为:"+uid.toString()+"开始埋点");
                            properties.put("RechargeAmount",dmc.getAmount());
                            properties.put("BankName","网银充值");
                            properties.put("BankCard","网银充值无法获取");
                            properties.put("RechargeMode","网银充值");
                            properties.put("RechargeAmounts","富友网银异步回调充值");
                            properties.put("IsSuccess",true);
                        }else {
                            convertvalue=String.valueOf(System.currentTimeMillis());
                            convert = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + m.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, convertvalue);
                            if(convert){
                                if (dmc.getAmount().intValue()>=100) {
                                    //充值任务添加积分
                                    taskIntegralRulesService.addPoints(m.getUid(), SystemConstant.RECHARGE_TYPE,dmc.getAmount().intValue());
                                    log.info("-----这里执行了已经给客户积分了------");
                                }
                            }
                            //微信模板消息
                            String openId = drMemberService.queryOpenId(m.getUid());
                            if(Utils.isObjectNotEmpty(openId)){
	                            ModelPassivityMessageSendUtil modelMessage = new ModelPassivityMessageSendUtil();
	                            DrMemberFunds drMemberFunds = drMemberFundsService.selectDrMemberFundsByUid(m.getUid());
	                            modelMessage.payJson(openId,Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
	                            		dmc.getAmount().toString(), drMemberFunds.getFuiou_balance().toString());
                            }
                        }
						//通知 处理完成  返回结果
						resp_code = "0000";
						
					}else{
						log.info("存管快捷充值-异步通知:系统繁忙");
					}
				}else{
					log.info("存管快捷充值-异步通知:没有用户["+net.sf.json.JSONObject.fromObject(noticeData)+"]");
				}
			}else{
				//验签失败
				log.info("存管快捷充值-异步通知:验签失败["+net.sf.json.JSONObject.fromObject(noticeData)+"]");
			}
		} catch (Exception e) {
            properties.put("IsSuccess",false);
			//验签失败
			log.error("------存管快捷充值-异步通知:系统错误["+net.sf.json.JSONObject.fromObject(noticeData)+"]\n"+e.getMessage(),e);
		}finally{
		    if (convert){
		        redisClientTemplate.releaseLock(ConfigUtil.REDIS_KEY_CONVERT + m.getUid(),convertvalue);
            }
			if(relockFlag){
                redisClientTemplate.releaseLock(ConfigUtil.AboutTheCash+m.getUid(),value);//释放
			}
            try {
			    if ("网银充值".equals(properties.get("BankName")))
                    instance.track(String.valueOf(uid), true, "RechargeResult" ,properties);
            } catch (Exception e) {
				log.error("神策充值报错"+e.getMessage() + "\n  用户id："+ uid + "\n 充值金额" + noticeData.getAmt() + "\n reqParameter：" + req.getParameterMap() + "\n RechargeResult" + properties);
            }
            instance.flush();
		}
		log.info("-----存管充值直连异步通知------");
		System.out.println("-----存管充值直连异步通知------\n"+net.sf.json.JSONObject.fromObject(noticeData));
		return FuiouConfig.notifyRspXml(resp_code, noticeData.getMchnt_txn_dt());
	}


	/**
	 * 充值直连
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/fuiouFastRecharg",method=RequestMethod.POST)
	@ResponseBody
	public String fuiouFastRecharg(HttpServletRequest req,@RequestBody Map<String, Object> param){
		BaseResult br = new BaseResult();
		DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
        SensorsAnalytics instance = SensorsAnalyticsUtil.getInstance();//充值成功
        Map<String, Object> properties = new HashMap<String, Object>();
		Map<String,Object> dateMap = new HashMap<String, Object>();
		dateMap.put("payTime",System.currentTimeMillis());
        String convertvalue = "";
        Boolean convert=false;
		String order =null;
		String fSS = null;
		boolean relockFlag = false;
        String value = String.valueOf(System.currentTimeMillis());
        SensorsAnalytics instance2 = SensorsAnalyticsUtil.getInstance();//设置用户属性
        Map<String, Object> properties2 = new HashMap<String, Object>();
		try {
            //判断客户是否是首冲
            if (Utils.isObjectNotEmpty(m.getUid())){
                properties2.put("uid",m.getUid());
                List<DrMemberCrush> list=drMemberCrushService.selectCrushByUid(properties2);
                properties2.clear();
                if (list==null || 0==list.size()){//是首冲
                    properties2.put("first_recharge_time",new Date());
                    instance2.profileSet(String.valueOf(m.getUid()),true,properties2);
                    instance2.flush();
                }
            }
            DrMemberBank drMemberBank = drMemberBankService.selectFuiouIdentificationBank(m.getUid());
            properties.put("RechargeAmount",new BigDecimal(param.get("amt").toString()));
            properties.put("BankName",drMemberBank.getBankName());
            properties.put("BankCard",drMemberBank.getBankNum());
            properties.put("RechargeMode","快捷充值");
            properties.put("RechargeAmounts","富友快捷充值");
			if(Utils.isObjectNotEmpty(param.get("yzm"))  && Utils.isObjectNotEmpty(m)
					&& Utils.isObjectNotEmpty(param.get("order")) && Utils.isObjectNotEmpty(param.get("amt"))){//
				fSS = "fSS_"+m.getUid();
				order =  param.get("order").toString(); //订单流水

				DrMemberCrush dmc = drMemberCrushService.getDrMemberCrushByPayNum(order);
				if(Utils.isObjectNotEmpty(dmc) && dmc.getAmount().compareTo(new BigDecimal(param.get("amt").toString()))==0){
					Map<String,String> map = new HashMap<String, String>();
					map.put("mchnt_txn_ssn", order);
					map.put("txn_date", Utils.format(new Date(), "yyyyMMdd"));//交易日期
					map.put("yzm", param.get("yzm").toString());//验证码
					map.put("login_id", m.getMobilephone());//M交易用户
//					map.put("bank_mobile",  param.get("bank_mobile").toString());//o预留手机号

					dateMap.put("confirmTime",System.currentTimeMillis());

					br = FuiouConfig.fastRecharg(map);//发送手机验证吗
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
								if("0000".equals(resp_code)){
									//插入充值记录
									dmc.setStatus(1);
									dmc.setRemark("成功");
									br.setSuccess(true);
                                    properties.put("IsSuccess",true);
								}else{
									dmc.setStatus(2);
									dmc.setRemark(resp_code+"|"+resp_desc);
									br.setSuccess(false);
                                    properties.put("IsSuccess",false);
								}
							}else{
								resp_desc = resp_desc ==null?"系统错误":resp_desc;
								dmc.setRemark(resp_code+"|"+resp_desc);
                                properties.put("IsSuccess",false);
							}
							br.setErrorMsg(resp_desc);
							br.setErrorCode(resp_code);
                            convertvalue=String.valueOf(System.currentTimeMillis());
                            convert = redisClientTemplate.tryLock(ConfigUtil.REDIS_KEY_CONVERT + m.getUid(), SystemConstant.TIME_OUT, TimeUnit.SECONDS, true, convertvalue);
                            if(convert){
                                drMemberCrushService.depositsRecharge(dmc, m);//充值数据处理
                            }
						}else if(dmc.getStatus() ==1){
							br.setMsg("成功");
							br.setSuccess(true);
                            properties.put("IsSuccess",true);
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
			e.printStackTrace();
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
			br.setSuccess(false);
            properties.put("IsSuccess",false);
		}finally{
			br.setMap(null);
			if(!Utils.strIsNull(order) && !Utils.strIsNull(fSS)){
				redisClientTemplate.del(fSS);//删掉 标识继续充值
			}
			if(relockFlag){
                redisClientTemplate.releaseLock(ConfigUtil.AboutTheCash+m.getUid(),value);
			}
            if (convert){
                redisClientTemplate.releaseLock(ConfigUtil.REDIS_KEY_CONVERT + m.getUid(),convertvalue);
            }
            try {
                instance.track(String.valueOf(m.getUid()), true, "RechargeResult" ,properties);
            } catch (Exception e) {
				log.error("神策充值报错"+e.getMessage() + "\n  用户id："+ m.getUid() + "\n 充值金额" + param.get("amt") + "\n reqParameter：" + req.getParameterMap() + "\n RechargeResult" + properties);
            }
            instance.flush();
		}
		dateMap.put("paySuccessTime",System.currentTimeMillis());
		br.setMap(dateMap);
		return JSON.toJSONString(br);
	}
	/**
	 * 充值验证码直连发短信
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/fuiouSendSms",method=RequestMethod.POST)
	@ResponseBody
	public String fuiouSendSms(HttpServletRequest req,@RequestBody Map<String, Object> param){
		BaseResult br = new BaseResult();
		DrMember m = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		Map<String,Object> dateMap = new HashMap<String, Object>();
		try {
			if(Utils.isObjectNotEmpty(param.get("bank_mobile")) && Utils.isObjectNotEmpty(param.get("amt")) && Utils.isNumber(param.get("amt").toString()) ){
				//查询卡预留手机号
				DrMemberBank bank = drMemberBankService.selectFuiouIdentificationBank(m.getUid());
				
				// 本地预留手机 !=null  and 新的手机预留手机号 是数字   就验证 是否相等 
//				if(!(!Utils.strIsNull(bank.getMobilePhone()) && Utils.isNumber(param.get("bank_mobile").toString())) || param.get("bank_mobile").toString().equals(bank.getMobilePhone()) ){
					if(!Utils.strIsNull(bank.getMobilePhone())){//
						param.put("bank_mobile", bank.getMobilePhone());
					}else if(!Utils.isNumber(param.get("bank_mobile").toString())){
						param.put("bank_mobile", m.getMobilephone());
					}
					
					String fSS = "fSS_"+m.getUid();
					String order =  Utils.createOrderNo(6, m.getUid(), null); //订单流水
					long l = redisClientTemplate.setnx(fSS, order);//流水号放到redis里
					if(l == 1){
						redisClientTemplate.expire(fSS, 30);// 标识符添加成功 存在15秒失效 
						Map<String,String> map = new HashMap<String, String>();
						map.put("mchnt_txn_ssn", order);
						map.put("login_id", m.getMobilephone());//M交易用户    
						map.put("amt", param.get("amt").toString()); //M交易金额    
						map.put("bank_mobile",  param.get("bank_mobile").toString());//o预留手机号
						
						br = FuiouConfig.sendSms(map);//发送手机验证吗
						String resp_code =(String)br.getMap().get("resp_code");
						String resp_desc =(String)br.getMap().get("resp_desc");
						resp_desc = resp_desc!=null?resp_desc:getFuiouCode(resp_code);
						
						if(br.isSuccess() ){							
							if( "0000".equals(resp_code)){
								//插入充值记录
								drMemberCrushService.insertFuiouOrder(order, 5, m.getUid(), new BigDecimal(param.get("amt").toString()));
								
								dateMap.put("order", order);//交易流水
								
								if(Utils.strIsNull(bank.getMobilePhone())){//如过预留手机号为空就修改
									bank.setMobilePhone(param.get("bank_mobile").toString());
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
						
						br.setMap(null);
					}else{
						br.setErrorMsg("订单创建频繁,30秒后重试");
					}
//				}else{
//					br.setErrorMsg("银行预留手机号错误");
//				}
				
				
			}else{
				br.setErrorMsg("参数错误");
			}
		} catch (Exception e) {
			br.setSuccess(false);
			e.printStackTrace();
			br.setErrorMsg("系统错误");
			br.setErrorCode("9999");
		}
		br.setMap(dateMap);
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
		Map<String,Object> map = new HashMap<String, Object>();
		SysFuiouNoticeLog noticeLog = null;
		net.sf.json.JSONObject json = getParams(req);
		try {
			req.setCharacterEncoding("UTF-8");

			if(Utils.isObjectNotEmpty(json)){				
				net.sf.json.JSONObject message = json.getJSONObject("message"); //报文正文JSON对象
				noticeLog = sysFuiouNoticeLogService.selectObjectBy_txn_ssn((String)message.get("mchnt_txn_ssn"));
				br.setErrorMsg(message.containsKey("resp_desc")?(String)message.get("resp_desc"):getFuiouCode((String)message.get("resp_code")));
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
					if(FuiouConfig.WEBREG.equals(noticeLog.getIcd()) || FuiouConfig.APPWEBREG.equals(noticeLog.getIcd())){//个人开户
						br.setMsg("/main/myAccount/storageSuccess");
						drMemberService.openAccountRes(json,req);
						
					}else if(FuiouConfig.PCQRECHARGE500405.equals(noticeLog.getIcd())){//充值
						br.setMsg("/main/myAccount/recharge");
						System.out.println("进入快捷充值跳转---------------------");
						
					}else if(FuiouConfig.WITHDRAW.equals(noticeLog.getIcd())){//提现异步通知
						br.setMsg("/main/myAccount/Withdraw");
						System.out.println("进入提现跳转---------------------");
					}else if(FuiouConfig.BRECHARGE12.equals(noticeLog.getIcd())){//网银
						br.setMsg("/main/myAccount/recharge");
						System.out.println("进入网银跳转---------------------");
						
					}else if(FuiouConfig.RESETPASSWORD.equals(noticeLog.getIcd())){//修改操作密码
						br.setMsg("/main/myAccount/storageinfo");
						System.out.println("进入密码操作跳转---------------------");
						
					}else {
						br.setMsg("/404");
					}
					
					br.setSuccess("0000".equals((String)message.get("resp_code"))?true:false);
					
				}
				if(json.isEmpty() || message.isEmpty()){
					map.put("type", 3);
					br.setMsg("/main/myAccount/storageinfo");
					br.setSuccess(true);
				}
			}else{
				br.setMsg("/404");				
			}
			model.put("br", br);
		} catch (Exception e) {
			br.setMsg("/404");
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
		return "openAccountSuccess";
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
			}
		}
		return desc;
	}
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
        System.out.println("-------------------------");
        System.out.println("恒丰2.0返回参数:"+json.toString());
        return json;
    }  
	@RequestMapping("/depositsRecharge")
	@ResponseBody
	public String depositsRecharge(HttpServletRequest req,@RequestBody Map<String,Object> param){
		BaseResult br = new BaseResult();
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		try {
			if(1==member.getIsFuiou() && Utils.isObjectNotEmpty(param.get("amt")) && Utils.isNumber(param.get("amt").toString())){
				Map<String, Object> map = new HashMap<String, Object>();
				String order = Utils.createOrderNo(6, member.getUid(), "");
				
				map.put("amt", param.get("amt").toString());
				map.put("login_id", member.getMobilephone());
				map.put("mchnt_txn_ssn", order);
				map.put("icd", FuiouConfig.PCQRECHARGE500405);
				
				String signature= FuiouConfig.rechargeFist(map);
				map.clear();
				map.put("signature", signature);
				map.put("fuiouUrl", FuiouConfig.PCQRECHARGE500405URL);
				//插入充值记录
				drMemberCrushService.insertFuiouOrder(order, 5, member.getUid(), new BigDecimal(param.get("amt").toString()));
				
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
	 * 网银充值
	 * @param req
	 * @param param
	 * @return
	 */
	@RequestMapping("/onlineBankingRecharge")
	@ResponseBody
	public String onlineBankingRecharge(HttpServletRequest req,@RequestBody Map<String,Object> param){
		BaseResult br = new BaseResult();
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		try {
			if(1==member.getIsFuiou() && Utils.isObjectNotEmpty(param.get("amt")) && Utils.isNumber(param.get("amt").toString())
					&& Utils.isObjectNotEmpty(param.get("iss_ins_cd"))&& Utils.isObjectNotEmpty(param.get("order_pay_type"))){
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String,String> params = new HashMap<String,String>();
				String mchnt_txn_ssn = Utils.createOrderNo(6, member.getUid(), "");
				params.put("mchnt_txn_ssn", mchnt_txn_ssn);
				params.put("amt", param.get("amt").toString());
				params.put("login_id", member.getMobilephone());
				params.put("iss_ins_cd", param.get("iss_ins_cd").toString());
				params.put("order_pay_type", param.get("order_pay_type").toString());
				
				map.put("signature", FuiouConfig.onlineBankingRecharge12Data(params));
				map.put("fuiouUrl", FuiouConfig.BRECHARGE12URL);
				//插入充值记录
				drMemberCrushService.insertFuiouOrder(mchnt_txn_ssn, 6, member.getUid(), new BigDecimal(param.get("amt").toString()));
				
				br.setMap(map);
				br.setSuccess(true);
			}else{
				br.setErrorMsg("参数有误");
				br.setErrorCode("1001");
			}
		} catch (Exception e) {
			log.error("网银充值",e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(HttpServletRequest req,Map<String,Object> model){
		BaseResult br = new BaseResult();
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		try {
			DrMember drMember = drMemberService.selectByPrimaryKey(member.getUid());
			if(Utils.isObjectEmpty(drMember)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			
			DrMemberFunds drMemberFunds = drMemberFundsService.selectDrMemberFundsByUid(drMember.getUid());
			model.put("funds",drMemberFunds.getBalance().setScale(2));
			model.put("fundsFuiou",drMemberFunds.getFuiou_balance().setScale(2));
	
			model.put("realFlag",drMember.getRealVerify());
			model.put("isFuiou",drMember.getIsFuiou());
			model.put("bankList", drMemberBankService.selectSysBankList());
			String fuiouOnlineBank = "{\"sss\":[{\"bankName\":\"中国工商银行\",\"bankCode\":\"0801020000\",\"id\":\"1\"},{\"bankName\":\"中国农业银行\",\"bankCode\":\"0801030000\",\"id\":\"2\"},{\"bankName\":\"中国建设银行\",\"bankCode\":\"0801050000\",\"id\":\"3\"},{\"bankName\":\"中国银行\",\"bankCode\":\"0801040000\",\"id\":\"4\"},{\"bankName\":\"中国邮政储蓄银行\",\"bankCode\":\"0801000000\",\"id\":\"5\"},{\"bankName\":\"招商银行\",\"bankCode\":\"0803080000\",\"id\":\"6\"},{\"bankName\":\"兴业银行\",\"bankCode\":\"0803090000\",\"id\":\"7\"},{\"bankName\":\"中国光大银行\",\"bankCode\":\"0803030000\",\"id\":\"8\"},{\"bankName\":\"广发银行\",\"bankCode\":\"0803060000\",\"id\":\"9\"},{\"bankName\":\"中国民生银行\",\"bankCode\":\"0803050000\",\"id\":\"11\"},{\"bankName\":\"浦发银行\",\"bankCode\":\"0803100000\",\"id\":\"12\"},{\"bankName\":\"中信银行\",\"bankCode\":\"0803020000\",\"id\":\"13\"},{\"bankName\":\"北京银行\",\"bankCode\":\"0804031000\",\"id\":\"15\"},{\"bankName\":\"交通银行\",\"bankCode\":\"0803010000\",\"id\":\"16\"},{\"bankName\":\"华夏银行\",\"bankCode\":\"0803040000\",\"id\":\"18\"}]}";
			
			net.sf.json.JSONObject ss = net.sf.json.JSONObject.fromObject(fuiouOnlineBank);				
			List<Map<String,Object>> fuiouOnlineBankList = (List<Map<String,Object>>)ss.getJSONArray("sss");
			model.put("onlineBankList", fuiouOnlineBankList);
			
			if(1 == drMember.getRealVerify() || 1== drMember.getIsFuiou()){
				model.put("realName", member.getRealName().substring(0, 1) + (member.getSex()==1?"先生":"女士"));
				model.put("idCards", member.getIdCards().subSequence(0, 4) +"****"+ member.getIdCards().substring(member.getIdCards().length()-4));
				Map<String,Object> map = new HashMap<String, Object>();	
				map.put("uid", drMember.getUid());
				List<SysArticle> sysArticleList = sysArticleService.getArticleByUid(map);
			
				model.put("sysArticleList", sysArticleList);
			}
			
			if(1 == drMember.getRealVerify()){
				DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(drMember.getUid());
				if(Utils.isObjectNotEmpty(drMemberBank)){
					drMemberBank.setBankNum(drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
					model.put("bankNum",drMemberBank.getBankNum());   
					model.put("mobilePhone",drMemberBank.getMobilePhone().substring(0,3)+"****"+drMemberBank.getMobilePhone().substring(drMemberBank.getMobilePhone().length()-4));   
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("bankName", drMemberBank.getBankName());
					SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
					queryMap.clear();
					
					model.put("bankCode",sysBank.getId());
					if(1 == sysBank.getChannel()){
						model.put("quota",sysBank.getSingleQuotaJYT()); 
					}else{
						model.put("quota",sysBank.getSingleQuotaSFT()); 
					}
				}
				
			}
			if(1== drMember.getIsFuiou()){
				DrMemberBank drMemberBank = drMemberBankService.selectFuiouIdentificationBank(drMember.getUid());
				
				String bankNo = drMemberBank.getBankNum();
				model.put("bankNoFuiou", bankNo.substring(0,4)+"****"+bankNo.substring(bankNo.length()-4, bankNo.length()));
				model.put("bankNumFuiou",bankNo.substring(bankNo.length()-4,bankNo.length())); 
				model.put("bankMobilePhoneFuiou",Utils.strIsNull(drMemberBank.getMobilePhone())? "" : drMemberBank.getMobilePhone().substring(0, 3) + "****" + drMemberBank.getMobilePhone().substring(drMemberBank.getMobilePhone().length()-4) ); 
		        
		        
		        Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("bankName", drMemberBank.getBankName());
				SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
				queryMap.clear();	
				model.put("bankCodeFuiou",sysBank != null?sysBank.getId():0);
				
				if(1 == sysBank.getChannel()){
					model.put("quota",sysBank.getSingleQuotaJYT()); 
				}else{
					model.put("quota",sysBank.getSingleQuotaSFT()); 
				}
			}
			model.put("fuiou_balance",drMemberFunds.getFuiou_balance().setScale(2));
			
			
			br.setMap(model);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("充值首页", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	

	/**
	 * 认证充值-创建订单同时发送验证码
	 * @param req
	 * @return
	 */
	@RequestMapping("/sendRechargeSms")
	@ResponseBody
	public BaseResult sendRechargeMsg(HttpServletRequest req,@RequestBody Map<String,Object> params){
		BaseResult br = new BaseResult();
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		member = drMemberService.selectByPrimaryKey(member.getUid());
		
		DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid()); //查询认证银行卡信息
		if(Utils.isObjectEmpty(drMemberBank)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return br;
		}
		try{
			Object amount = params.get("amount");
			Object orderNo = null;
			
			if(Utils.isObjectEmpty(amount)){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return br;
			}
			if(new BigDecimal(amount.toString()).compareTo(new BigDecimal(3))<0){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return br;
			}
			redisClientTemplate.lock("payOrder"+member.getUid().toString());
			
			String mobilePhone = drMemberBank.getMobilePhone();
			
			Object typeObj = params.get("type");
			if(Utils.isObjectEmpty(typeObj)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return br;
			}
			int type = (Integer) typeObj;
			
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("bankName", drMemberBank.getBankName());
			SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
			if(Utils.isObjectEmpty(sysBank)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return br;
			}
			
			//认证充值-创建订单
			br = payService.insertPayOrder(req,member, amount.toString(), drMemberBank,sysBank);
			if(br.isSuccess()){
				Map<String, Object> map = new HashMap<String, Object>();
				DrMemberCrush drMemberCrush = (DrMemberCrush) br.getMap().get("drMemberCrush");
				if(1 == drMemberCrush.getType()){
					br = sendJYTSms(mobilePhone,drMemberBank,type,member);
					if(br.isSuccess()){
						map.put("payNum", drMemberCrush.getPayNum());
						br.setMap(map);
					}
				}else if(4 == drMemberCrush.getType()){
					//融宝认证
					if(br.isSuccess()){
						map.put("payNum", drMemberCrush.getPayNum());
						br.setMap(map);
					}
				}else{
					br = sendSFTSms(req,member,drMemberBank,sysBank,drMemberCrush);
					if(br.isSuccess()){
						map.put("payNum", drMemberCrush.getPayNum());
						br.setMap(map);
					}
				}
			}else{
				return br;
			}
		} catch (AppException e) {
			log.error("短信发送失败超时", e);
        	br.setSuccess(false);
        	br.setErrorCode("9999");
		} catch (Exception e) {
			log.error("短信发送失败", e);
			br.setSuccess(false);
			br.setErrorCode("1004");
		}
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
			br.setErrorCode("1004");//短信发送失败
		}
		return br;
	}
	
	/**
	 * 发送盛付通充值验证码
	 * HttpServletRequest
	 * DrMemberBank 会员银行卡信息
	 * DrMember 会员基本信息
	 * SysBank 银行卡基本信息
	 * sessionToken 支付token，用于后续的支付预校验和支付确认
	 * @return BaseResult
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
				br.setErrorCode("1004");
				br.setSuccess(false);
			}
		}else{
			drMemberCrush.setStatus(2);
			br.setErrorCode("1004");
			br.setSuccess(false);
		}
		payService.updateMemberCrushById(drMemberCrush);
		return br;
	}
	
	/**
	 * 发送融宝充值验证码
	 * @return BaseResult
	 * @throws Exception 
	 */
	public BaseResult sendRBSms(DrMemberCrush drMemberCrush,String order_no) throws Exception{
		BaseResult br = new BaseResult();
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", ReapalConfig.getMerchant_id());
		map.put("version", ReapalConfig.getVersion());
		//订单号
		map.put("order_no", order_no);  
		//请求接口	
		String url = "/fast/sms";
		//返回结果
		String post = ReapalSubmit.buildSubmit(map, url);
	    //解密返回的数据
	    String res = Decipher.decryptData(post);
	    net.sf.json.JSONObject  jasonObject = net.sf.json.JSONObject.fromObject(res); 
		
		String returnMessage = jasonObject.get("result_msg") == null ? "" : jasonObject.get("result_msg").toString();
		String returnCode = jasonObject.get("returnCode") == null ? "" : jasonObject.get("returnCode").toString();
		
		drMemberCrush.setRemark("支付预签约："+returnCode+"|"+"|"+returnMessage);
		
		if("0000".equals(jasonObject.get("code"))){
			br.setSuccess(true);
		}else{
			drMemberCrush.setStatus(2);
			br.setErrorCode("1004");
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
	 * 快捷充值
	 * @throws Exception
	 */
	@RequestMapping("/goPay")
	@ResponseBody
	public String goPay(HttpServletRequest req,@RequestBody Map<String, Object> params) {
		BaseResult br = new BaseResult();
		DrMember member = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		member = drMemberService.selectByPrimaryKey(member.getUid());
		
		DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());

		if(Utils.isObjectEmpty(drMemberBank)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		
		Object payNum = params.get("payNum");
		if(Utils.isObjectEmpty(payNum)){
			br.setSuccess(false);
			br.setErrorCode("1005");
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
			Object smsCode = params.get("smsCode");//短信验证码
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
			
			br = payService.savePay(req,member,drMemberBank,drMemberCrush,smsCode.toString());
			if(1 == drMemberCrush.getType()){
				redisClientTemplate.del("rechargeMsgCode_"+drMemberBank.getMobilePhone());
				redisClientTemplate.del("rechargeMsgSendTime_"+drMemberBank.getMobilePhone());
			}
		} catch (AppException e) {
			if(1 == drMemberCrush.getType()){
				redisClientTemplate.del("rechargeMsgCode_"+drMemberBank.getMobilePhone());
				redisClientTemplate.del("rechargeMsgSendTime_"+drMemberBank.getMobilePhone());
			}
			log.error("认证充值超时", e);
        	br.setSuccess(false);
        	br.setErrorCode("1004");
			return JSON.toJSONString(br);
		} catch (Exception e) {
			log.error("认证充值失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		return JSON.toJSONString(br);
	}

	/**
	 * 金运通认证充值异步通知
	 * @param req
	 * @return
	 */
	@RequestMapping("/receiveNotifyJYT")
	@ResponseBody
	public void receiveNotifyJYT(HttpServletRequest req,
			HttpServletResponse resp) {
		resp.setCharacterEncoding("UTF-8");
		try {
			payService.receiveNotifyJYT(req, resp);
		} catch (Exception e) {
			log.error("金运通认证充值异步通知失败", e);
		}
	}
	
	/**
	 * 盛付通认证充值异步通知
	 * @param req
	 * @return
	 */
	@RequestMapping("/receiveNotifySFT")
	@ResponseBody
	public String receiveNotifySFT(HttpServletRequest req,HttpServletResponse resp) {
		resp.setCharacterEncoding("UTF-8");
		String result = "";
		try {
			result = payService.receiveNotifySFT(req, resp);
		} catch (Exception e) {
			log.error("盛付通认证充值异步通知失败", e);
		}
		return result;
	}
	
	/**
	 * 金运通网银充值
	 * @throws Exception
	 */
	@RequestMapping("/goJYTWYPay")
	@ResponseBody
	public String goJYTWYPay(HttpServletRequest req,@RequestBody Map<String, Object> params) {
		BaseResult br = new BaseResult();
		DrMember member = (DrMember) req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		try{
			if(Utils.isObjectEmpty(member)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			
			Object amount = params.get("amount");
			Object bankCode = params.get("bankCode");//银行编号
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
			if(Utils.isObjectEmpty(bankCode)){
				br.setSuccess(false);
				br.setErrorCode("1002");
				return JSON.toJSONString(br);
			}
			redisClientTemplate.lock("recharge"+member.getUid().toString());
			Map<String,String> map = new HashMap<String, String>();
			map = payService.insertWYDrMemberCrush(member, amount.toString(), bankCode.toString());
			br.setSuccess(true);
			br.setMap(map);
		} catch (Exception e) {
			log.error("网银充值失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 金运通网银同步通知
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/rechargeSuccess")
	public String rechargeSuccess(HttpServletRequest req,Map<String,Object> model,HttpServletResponse resp) {
		resp.setCharacterEncoding("UTF-8");
		BaseResult br = new BaseResult();
		try {
			req.setCharacterEncoding("UTF-8");
			String respCode = req.getParameter("respCode");

			if(!Utils.strIsNull(respCode)){
				br = payService.saveJYTWYPay(req);
				model.put("br", br);
			}else{
				br.setSuccess(false);
				br.setErrorCode("9999");
				model.put("br", br);
			}
			String respDesc = req.getParameter("respDesc");
			log.info("网银同步数据:"+"respCode:"+respCode+",respDesc:"+respDesc);
			log.info("网银同步通知参数部分：");
			showParams(req);
		} catch (Exception e) {
			log.error("网银同步通知失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			model.put("br", br);
		}
		return "rechargeSuccess";
	}
	
	
	private void showParams(HttpServletRequest request) {  
        Map<String, String>map = new HashMap<String, String>();  
        Enumeration<String> paramNames = request.getParameterNames();  
        while (paramNames.hasMoreElements()) {  
            String paramName = (String) paramNames.nextElement();  
  
            String[] paramValues = request.getParameterValues(paramName);  
            if (paramValues.length == 1) {  
                String paramValue = paramValues[0];  
                if (paramValue.length() != 0) {  
                    map.put(paramName, paramValue);  
                }  
            }  
        }  
        Set<Map.Entry<String, String>> set = map.entrySet();  
        log.info("------------------------------");  
        System.out.println("-------------------------");
        System.out.println("恒丰2.0返回参数:"+JSON.toJSONString(map));
        for (Map.Entry entry : set) {  
            log.info(entry.getKey() + "=" + entry.getValue() + "&");  
        }  
        log.info("------------------------------");  
    }  
	
	/**
	 * 金运通网银异步通知
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/receiveNotifyJYTWY")
	@ResponseBody
	public String receiveNotifyJYTWY(HttpServletRequest req) {
		try {
			String respCode = req.getParameter("respCode");
			if(!Utils.strIsNull(respCode)){
				payService.saveJYTWYPay(req);
			}
			String respDesc = req.getParameter("respDesc");
			log.info("网银异步数据:"+"respCode:"+respCode+",respDesc:"+respDesc);
			log.info("网银异步通知参数部分：");
			showParams(req);
			return XmlMsgConstant.MSG_RES_CODE_SUCCESS;
		} catch (Exception e) {
			log.error("网银同步通知失败", e);
			return XmlMsgConstant.MSG_RES_CODE_SUCCESS;
		}
	}
	
	//盛付通解绑
	public static void main(String[] args) throws Exception {
		NameValuePair[] nameValuePairs = {
				new NameValuePair("merchantNo", "601924"),
				new NameValuePair("charset", ExpressGlobalConfig.utf8),
				new NameValuePair("requestTime", Utils.getTime()),
				new NameValuePair("agreementNo", "853285"),
				new NameValuePair("principalId", "19429")};

		String responseBody = BaseExpressService.httpSend(ExpressGlobalConfig.expressUnsignUrl, nameValuePairs);
		System.out.println(responseBody);
		Map<String, Object> result = JSONObject.parseObject(responseBody);
		System.out.println("returnCode : " + result.get("returnCode"));
		System.out.println("returnMessage : " + result.get("returnMessage"));
	}
	
   //融宝快捷异步通知
	@RequestMapping("/receiveNotifyRB")
	@ResponseBody
	public String receiveNotifyRB(HttpServletRequest req, String data, String merchant_id, String encryptkey) {
		String result = "fail";
		try {
			if (!Utils.strIsNull(data)) {
				if (merchant_id.equals(ReapalConfig.getMerchant_id())) {
					String decryData = Decipher.decryptData(encryptkey, data);
					result = payService.receiveNotifyRB(decryData, merchant_id);
				}
			}
		} catch (Exception e) {
			log.error("快捷同步通知失败", e);
		}
		return result;
	}
}