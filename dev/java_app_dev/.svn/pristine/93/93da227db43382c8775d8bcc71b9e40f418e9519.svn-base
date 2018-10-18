package com.jsjf.controller.account.mycenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jsjf.common.ConfigUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.member.DrMemberBankService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;

@RequestMapping("/memberSetting")
@Controller
public class MemberSettingController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrMemberBankService drMemberBankService;
	@Autowired
	public DrChannelInfoDAO drChannelInfoDAO;
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	@ResponseBody
	public String index(HttpServletRequest req,Integer uid){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		try {
			if(Utils.isObjectEmpty(member)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			map.put("mobilephone",member.getMobilephone().substring(0, 3)+"****"+member.getMobilephone().substring(member.getMobilephone().length()-4));
			if(StringUtils.isBlank(member.getTpassWord())){
				map.put("tpwdFlag", 0);
			}else{
				map.put("tpwdFlag", 1);
			}
			
			if(1 == member.getRealVerify() || member.getIsFuiou() == 1){
				map.put("realName", member.getRealName().substring(0,1)+"**");
				map.put("idCards",member.getIdCards().substring(0,4)+"***"+member.getIdCards().substring(member.getIdCards().length()-4));
								
			}

			map.put("realVerify", member.getRealVerify());
			DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
			if(Utils.isObjectEmpty(drMemberBank) && 1 == member.getIsFuiou() ){
				drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
			}
			if(Utils.isObjectNotEmpty(drMemberBank)){
				String bankNum = drMemberBank.getBankNum();
				if(0 == member.getIsFuiou()){
					map.put("bankNum", bankNum);
				}else{
					map.put("bankNum", bankNum.substring(0,4)+"******"+bankNum.substring(bankNum.length()-4, bankNum.length()));
				}
				map.put("bankName", drMemberBank.getBankName());
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("bankName", drMemberBank.getBankName());
				SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
				if(Utils.isObjectNotEmpty(sysBank)){
					map.put("bankId", sysBank.getId());
					map.put("bankCode", sysBank.getBankCode());
				}
			}				
			
			map.put("isFuiou", member.getIsFuiou());
			if(1 == member.getIsFuiou()){
				drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
				if(Utils.isObjectNotEmpty(drMemberBank)){
					String bankNum = drMemberBank.getBankNum();
					map.put("bankNumFuiou", bankNum.substring(0,4)+"******"+bankNum.substring(bankNum.length()-4, bankNum.length()));
					map.put("bankNameFuiou", drMemberBank.getBankName());
					map.put("bankMobileFuiou",Utils.strIsNull(drMemberBank.getMobilePhone())?"": drMemberBank.getMobilePhone().substring(0,3)+"****"+drMemberBank.getMobilePhone().substring(7));
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("bankName", drMemberBank.getBankName());
					SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
					if(Utils.isObjectNotEmpty(sysBank)){
						map.put("bankIdFuiou", sysBank.getId());
						map.put("bankCodeFuiou", sysBank.getBankCode());
					}
				}				
			}
			
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("我(uid="+uid+")的信息读取失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	@RequestMapping(value = "/myBankInfo", method = RequestMethod.POST)
	@ResponseBody
	public String myBankInfo(HttpServletRequest req,Integer uid){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		try {
			if(Utils.isObjectEmpty(member)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			if(1 == member.getIsFuiou()){
				DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("bankName", drMemberBank.getBankName());
				SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
				map.put("realName", member.getRealName());
				map.put("idCards",member.getIdCards().substring(0,3)+"********"+member.getIdCards().substring(member.getIdCards().length()-4));
				map.put("bankNum",drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
				map.put("bankCode",sysBank.getId());
				map.put("bankName",sysBank.getBankName());
				map.put("phone",drMemberBank.getMobilePhone());
				if(1 == sysBank.getChannel()){
					map.put("singleQuota",sysBank.getSingleQuotaJYT());
					map.put("dayQuota",sysBank.getDayQuotaJYT());
				}else{
					map.put("singleQuota",sysBank.getSingleQuotaSFT());
					map.put("dayQuota",sysBank.getDayQuotaSFT());
				}
			}
			
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("我(uid="+uid+")的信息读取失败", e);
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/*
	 * 认证银行卡四要素
	 */
	@RequestMapping("/bankInfoVerify")
	@ResponseBody
	public String bankInfoVerify(HttpServletRequest req,Integer uid,String realName,String idCards,String bankNum,String phone,String smsCode,Integer channel){
		BaseResult br = new BaseResult();
		
		redisClientTemplate.lock("memberSetting"+uid.toString());
		
		String bankPhoneCode = redisClientTemplate.get("bankMsgCode_"+phone);
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		if(Utils.strIsNull(realName)){
			br.setSuccess(false);
			br.setErrorCode("1001");
			return JSON.toJSONString(br);
		}
		if(Utils.strIsNull(idCards)){
			br.setSuccess(false);
			br.setErrorCode("1002");
			return JSON.toJSONString(br);
		}
		if(Utils.strIsNull(bankNum)){
			br.setSuccess(false);
			br.setErrorCode("1003");
			return JSON.toJSONString(br);
		}
		if(Utils.strIsNull(phone)){
			br.setSuccess(false);
			br.setErrorCode("1004");
			return JSON.toJSONString(br);
		}
		if(Utils.isObjectEmpty(channel)){
			br.setSuccess(false);
			br.setErrorCode("1013");
			return JSON.toJSONString(br);
		}
		if(1 == member.getRealVerify()){
			br.setSuccess(true);
			return JSON.toJSONString(br);
		}
		if(Utils.strIsNull(smsCode)){//短信验证码为空
			br.setSuccess(false);
			br.setErrorCode("1005");
			return JSON.toJSONString(br);
		}else if(!smsCode.equals(bankPhoneCode) || StringUtils.isBlank(bankPhoneCode)){//验证码错误
			br.setSuccess(false);
			br.setErrorCode("1006");
			return JSON.toJSONString(br);
		}
		try {
			br = drMemberBankService.insertDrMemberBank(member.getUid(),realName,idCards,bankNum,phone,channel);
			if(br.isSuccess()){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("code", member.getToFrom());
				List<DrChannelInfo> channelList = drChannelInfoDAO.getDrChannelInfoListForMap(map);//获取toFrom对应的渠道
				map.clear();
				if(!Utils.isEmptyList(channelList)){
					DrChannelInfo channel1 = channelList.get(0);//获取渠道
					map.put("isCps", channel1.getType()==null?0:channel1.getType());//渠道类型，如果为null默认为非CPS
				}else{
					map.put("isCps", 0);//如果渠道不存在，则默认是非CPS
				}
				if(Utils.isObjectNotEmpty(br.getMap())){
					((Map<String,Object>)br.getMap()).putAll(map);
				} else{
					br.setMap(map);
				}
//				Map<String, Object> map2 = new HashMap<String,Object>();
//				map2.put("type",3);
//				map2.put("uid", member.getUid());
//				redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(),SerializeUtil.serialize(map2));
			}
			redisClientTemplate.del("bankMsgCode_"+phone);
			redisClientTemplate.del("bankMsgSendTime_"+phone);
//			redisClientTemplate.del("bankMsgUse_"+phone);
		} catch (Exception e) {
			log.error("真实姓名："+realName+",身份证号:"+idCards+",银行卡号:"+bankNum+",手机号:"+phone,e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 发送验证银行四要素短信
	 * @param req
	 * @param mobilephone
	 * @return
	 */
	@RequestMapping("/sendBankMsg")
	@ResponseBody
	public String sendBankMsg(HttpServletRequest req,Integer uid,String mobilePhone,String bankNum,Integer type){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		try {
			if(Utils.isObjectEmpty(member)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			if(Utils.strIsNull(mobilePhone)){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			if(Utils.strIsNull(bankNum)){
				br.setSuccess(false);
				br.setErrorCode("1004");
				return JSON.toJSONString(br);
			}
			Map<String,Object> map = new HashMap<String, Object>();
			//判断手机发送短信条数
			map.put("uid", member.getUid());
			map.put("phone", mobilePhone);
			map.put("type", 15);
			int sendCount = sysMessageLogService.selectMsgLogSendCount(map);
			map.clear();
			if(sendCount > 5){
				br.setSuccess(false);
				br.setErrorCode("1002");//每天只能发6条短信
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(type) || type == 2){
				type = 1;
			}
			String redisCode = redisClientTemplate.get("bankMsgCode_"+mobilePhone);
			int flag = 0;
			int  seconds = 0;
			if(StringUtils.isNotEmpty(redisCode)){
				//当前时间和当前手机号码上次发送短信时间的秒数差
				seconds = Utils.getDateSecondsSub(Utils.getStrDatetime(new Date()), redisClientTemplate.get("bankMsgSendTime_"+mobilePhone));
				if(seconds < 59){
					if(2 == type){
						String bankMsgUse = redisClientTemplate.get("bankMsgUse_"+mobilePhone);
						if("1".equals(bankMsgUse)){
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
					redisClientTemplate.setex("bankMsgCode_"+mobilePhone, 600, redisCode);
					//短信发送时间 10分钟有效
					redisClientTemplate.setex("bankMsgSendTime_"+mobilePhone, 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				}
			}else if(StringUtils.isBlank(redisCode)){//短信内容是否存在redis中
				redisCode = Utils.getRandomNumber6();//生成验证码
				seconds = 60;
				//短信验证码 10分钟有效
				redisClientTemplate.setex("bankMsgCode_"+mobilePhone, 600, redisCode);
				//短信发送时间 10分钟有效
				redisClientTemplate.setex("bankMsgSendTime_"+mobilePhone, 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
			if(seconds > 59){//两次时间间隔60秒，重新发送短信
				String content = redisClientTemplate.getProperties("bankSms")
						.replace("${1}",redisCode)
						.replace("${2}",bankNum);
//				if(2 == type){
//					content = redisCode;
//					redisClientTemplate.setex("bankMsgUse_"+mobilePhone, 600, "1");
//				}
//				if(1 == type){
//					redisClientTemplate.del("bankMsgUse_"+mobilePhone);
//				}
				SysMessageLog logs = new SysMessageLog(member.getUid(),content,15, null, mobilePhone);
				flag = sysMessageLogService.sendMsg(logs,type);
			}else{
				flag = 1;
			}
			//短信是否发送成功
			if (flag > 0) {
				br.setSuccess(true);
			} else {
				br.setSuccess(false);
				br.setErrorCode("1003");//短信发送失败
			}
		} catch (Exception e) {
			log.error("短信发送失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 重置登录密码，发送手机验证码
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/forgetPwdSmsCode", method = RequestMethod.POST)
	@ResponseBody
	public String forgetPwdSmsCode(HttpServletRequest req, Integer uid, String mobilephone,Integer type){
		BaseResult br = new BaseResult();
		try {
			DrMember member;
			if(StringUtils.isBlank(mobilephone)){
				member = drMemberService.selectByPrimaryKey(uid);
			}else{
				member = drMemberService.selectDrMemberByMobilephone(mobilephone);
			}
			if(member==null){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(type) || type == 2){
				type = 1;
			}
			String randCode = redisClientTemplate.get("forgetPwdCode_"+member.getMobilephone());
			int flag = 0;
			int seconds = 60;
			if(StringUtils.isNotBlank(randCode)){
				//当前时间和当前手机号码上次发送短信时间的秒数差
				seconds = Utils.getDateSecondsSub(Utils.getStrDatetime(new Date()), redisClientTemplate.get("forgetPwdSendTime_"+member.getMobilephone()));
				if(seconds < 59){
					if(2 == type){
						String forgetPwdUse = redisClientTemplate.get("forgetPwdUse_"+member.getMobilephone());
						if("1".equals(forgetPwdUse)){
							br.setSuccess(false);
							br.setErrorCode("8888");//语音频繁操作
							return JSON.toJSONString(br);
						}else{
							seconds = 60;
						}
					}
				}else{
					randCode = Utils.getRandomNumber6();//生成验证码
					//短信验证码 10分钟有效
					redisClientTemplate.setex("forgetPwdCode_"+member.getMobilephone(), 600, randCode);
					//短信发送时间 10分钟有效
					redisClientTemplate.setex("forgetPwdSendTime_"+member.getMobilephone(), 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				}
			}else if(StringUtils.isBlank(randCode)){//短信内容是否存在redis中
				randCode = Utils.getRandomNumber6();//生成验证码
				seconds = 60;
				//短信验证码 10分钟有效
				redisClientTemplate.setex("forgetPwdCode_"+member.getMobilephone(), 600, randCode);
				//短信发送时间 10分钟有效
				redisClientTemplate.setex("forgetPwdSendTime_"+member.getMobilephone(), 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
			
			if(seconds > 59){//两次时间间隔60秒，重新发送短信
				String content = redisClientTemplate.getProperties("forgetPwdSms").replace("${1}",member.getRealName()==null?"用户":member.getRealName())
						.replace("${2}",randCode);
//				if(2 == type){
//					content = randCode;
//					redisClientTemplate.setex("forgetPwdUse_"+member.getMobilephone(), 600, "1");
//				}
//				if(1 == type){
//					redisClientTemplate.del("forgetPwdUse_"+member.getMobilephone());
//				}
				SysMessageLog logs = new SysMessageLog(member.getUid(), content, 5, null, member.getMobilephone());
				flag = sysMessageLogService.sendMsg(logs,type);
			}else{
				flag = 1;
			}
			//短信是否发送成功
			if (flag > 0) {
//				br.setMsg(randCode);				
				br.setSuccess(true);
			} else {
				br.setSuccess(false);
				br.setErrorCode("1002");
				log.info("短信发送失败，返回码："+flag);
			}
		} catch (Exception e) {
			log.error("忘记密码时短信发送错误",e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	
	/*
	 * 修改登录密码
	 */
	@RequestMapping("/updateLoginPassWord")
	@ResponseBody
	public String updateLoginPassWord(HttpServletRequest req, String pwd, Integer uid, String smsCode, String mobilephone){
		BaseResult br = new BaseResult();
		DrMember member;
		if(StringUtils.isBlank(mobilephone)){
			member = drMemberService.selectByPrimaryKey(uid);
		}else{
			member = drMemberService.selectDrMemberByMobilephone(mobilephone);
		}
		String forgetPwdCode_ = redisClientTemplate.get("forgetPwdCode_"+member.getMobilephone());
		if(StringUtils.isBlank(smsCode) || !smsCode.equals(forgetPwdCode_)){//短信验证码是否正确
			br.setSuccess(false);
        	br.setErrorCode("1001");
        	return JSON.toJSONString(br);
		}else if(StringUtils.isBlank(pwd)){//新密码为空
			br.setSuccess(false);
        	br.setErrorCode("1002");
        	return JSON.toJSONString(br);
		}
		member.setPassWord(pwd);
		try {
			drMemberService.update(member);
			br.setSuccess(true);
			redisClientTemplate.del("forgetPwdCode_"+mobilephone);
			redisClientTemplate.del("forgetPwdSendTime_"+mobilephone);
			redisClientTemplate.del(ConfigUtil.PWD_ERROR+mobilephone);
//			redisClientTemplate.del("forgetPwdUse_"+member.getMobilephone());
			// 发送手机短信
			String sms = redisClientTemplate.getProperties("updatePwdSms").replace("${1}", StringUtils.isNotBlank(member.getRealName())?member.getRealName():"用户");
			if (member.getMobilephone() != null && !member.getMobilephone().equals("")) {
				SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 13, null, member.getMobilephone());
				sysMessageLogService.sendMsg(logs,1);
			}
		} catch (Exception e) {
			log.error("修改登录密码失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 发送修改交易密码验证码
	 */
	@RequestMapping("/sendForgetTpwdCode")
	@ResponseBody
	public String sendForgetTpwdCode(HttpServletRequest req,Integer uid,Integer type){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		try {
			if(Utils.isObjectEmpty(type) || type == 2){
				type = 1;
			}
			String redisCode = redisClientTemplate.get("updateTpwdSms_"+member.getMobilephone());
			int  seconds = 60;
			int result = 0;
			if(StringUtils.isNotEmpty(redisCode)){
				//当前时间和当前手机号码上次发送短信时间的秒数差
				seconds = Utils.getDateSecondsSub(Utils.getStrDatetime(new Date()), redisClientTemplate.get("updateTpwdSmsTime_"+member.getMobilephone()));
				if(seconds < 59){
					if(2 == type){
						String updateTpwdSmsUse = redisClientTemplate.get("updateTpwdSmsUse_"+member.getMobilephone());
						if("1".equals(updateTpwdSmsUse)){
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
					redisClientTemplate.setex("updateTpwdSms_"+member.getMobilephone(), 600, redisCode);
					redisClientTemplate.setex("updateTpwdSmsTime_"+member.getMobilephone(), 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				}
			}else if(StringUtils.isBlank(redisCode)){//短信内容是否存在redis中
				redisCode = Utils.getRandomNumber6();//生成验证码
				seconds = 60;
				//短信验证码 10分钟有效
				redisClientTemplate.setex("updateTpwdSms_"+member.getMobilephone(), 600, redisCode);
				redisClientTemplate.setex("updateTpwdSmsTime_"+member.getMobilephone(), 600, Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
			if(seconds>59){
				String content = redisClientTemplate.getProperties("forgetTpwdSms")
						.replace("${realname}", member.getRealName()==null?"用户":member.getRealName())
						.replace("${code}", redisCode);//取到修改交易密码的短信模板
//				if(2 == type){
//					content = redisCode;
//					redisClientTemplate.setex("updateTpwdSmsUse_"+member.getMobilephone(), 600, "1");
//				}
//				if(1 == type){
//					redisClientTemplate.del("updateTpwdSmsUse_"+member.getMobilephone());
//				}
				SysMessageLog logs = new SysMessageLog(member.getUid(), content,6, null, member.getMobilephone());
				result = sysMessageLogService.sendMsg(logs,type);
			}else{
				br.setSuccess(false);
				br.setErrorCode("8888");
				return JSON.toJSONString(br);
			}
			if(result>0){
//				br.setMsg(redisCode);
				br.setSuccess(true);
			}else{
				br.setSuccess(false);
				br.setErrorCode("1001");//短信发送失败
			}
		} catch (Exception e) {
			log.error("[uid="+uid+"]重置交易密码短信发送失败",e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	
	@RequestMapping(value="/updateTpwdBySms", method = RequestMethod.POST)
	@ResponseBody
	public String updateTpwdBySms(HttpServletRequest req, String tpwd, String smsCode, Integer uid){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		//判断新密码是否为空
		if(StringUtils.isBlank(tpwd)){
			br.setSuccess(false);
			br.setErrorCode("1002");
			return JSON.toJSONString(br);
		}
		//首次设置密码不需要短信验证
		if(StringUtils.isNotBlank(member.getTpassWord())){
			String forgetTpwdCode = redisClientTemplate.get("updateTpwdSms_"+member.getMobilephone());
			if( Utils.isObjectEmpty(forgetTpwdCode) || !forgetTpwdCode.equals(smsCode) ){//短信验证码是否正确
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
		}
		member.setTpassWord(tpwd);
		try {
			drMemberService.update(member);
			redisClientTemplate.del("updateTpwdSms_"+member.getMobilephone());
			redisClientTemplate.del("updateTpwdSendTime_"+member.getMobilephone());
			redisClientTemplate.del("error.tpwd.uid."+member.getUid());
			// 发送手机短信updateTpwdSms
			String sms = redisClientTemplate.getProperties("updateTpwdSms").replace("${1}", member.getRealName()==null?"用户":member.getRealName());
			if (member.getMobilephone() != null && !member.getMobilephone().equals("")) {
				SysMessageLog logs = new SysMessageLog(member.getUid(), sms, 12, null, member.getMobilephone());
				sysMessageLogService.sendMsg(logs,1);
			}
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("修改交易密码失败", e);
			br.setSuccess(true);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	
	@RequestMapping("/fuiouIndex")
	@ResponseBody
	public String fuiouIndex(HttpServletRequest req,Integer uid){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
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
			
			map.put("isFuiou", drMember.getIsFuiou());
			
			map.put("mobilePhone",drMember.getMobilephone().substring(0,4)+"****"+drMember.getMobilephone().substring(drMember.getMobilephone().length()-3));
			
		
			if(1 == member.getIsFuiou()){
				DrMemberBank drMemberBank = drMemberBankService.selectFuiouIdentificationBank(drMember.getUid());
				
				if(Utils.isObjectNotEmpty(drMemberBank.getMobilePhone())){
					map.put("mobilePhone",drMemberBank.getMobilePhone().substring(0,4)+"****"+drMemberBank.getMobilePhone().substring(drMemberBank.getMobilePhone().length()-3));
				}
				
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("bankName", drMemberBank.getBankName());
				SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
				queryMap.clear();
				map.put("realName","*"+ drMember.getRealName().substring(drMember.getRealName().length()-1,drMember.getRealName().length()));
				map.put("idCards",drMember.getIdCards().substring(0,4)+"***"+drMember.getIdCards().substring(drMember.getIdCards().length()-3));
				map.put("bankNum",drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
				if(Utils.isObjectNotEmpty(sysBank)){
					map.put("bankCode",sysBank.getId());
				}
			}
			
			
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("我(uid="+member.getUid()+")的信息读取失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	@RequestMapping("fuiouUpdatePwd")
	@ResponseBody
	public String fuiouUpdatePwd(HttpServletRequest req,Integer uid,Integer channel,String busi_tp){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		try {
			if(member.getIsFuiou() ==1){
				if(busi_tp !=null){
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("id", member.getUid());
					params.put("login_id", member.getMobilephone());
					params.put("busi_tp", busi_tp);
					params.put("channel", channel == 1||channel ==2 ?"app":"wap");
					
					map.put("signature", FuiouConfig.resetPassword(params));
					map.put("fuiouUrl", FuiouConfig.APPRESETPASSWORDURL);
					
					map.putAll(JSONObject.fromObject(map.get("signature")));
					br.setMap(map);
					br.setSuccess(true);
				}else{
					br.setErrorCode("1002");
					br.setErrorMsg("业务类型编码错误");
				}				
			}else{
				br.setErrorCode("1001");
				br.setErrorMsg("未开通存管");
			}
		} catch (Exception e) {
			log.error("富友修改密码失败",e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		return JSON.toJSONString(br);
	}
}
