package com.jsjf.controller.account.funds;

import com.SensorsAnalytics.SensorsAnalytics;
import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.SensorsAnalyticsUtil;
import com.jsjf.common.SystemConstant;
import com.jsjf.common.Utils;
import com.jsjf.dao.vip.MemberVipInfoMapper;
import com.jsjf.dao.vip.VipInfoMapper;
import com.jsjf.model.member.*;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.vip.MemberVipInfo;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipEquitiesMember;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.service.member.DrMemberBankService;
import com.jsjf.service.member.DrMemberCarryService;
import com.jsjf.service.member.DrMemberFundsService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jsjf.service.vip.VipEquitiesMemberService;
import com.jsjf.service.vip.VipEquitiesService;
import com.jytpay.AppException;
import com.jzh.FuiouConfig;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/withdrawals")
@Controller
public class WithdrawalsController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DrMemberCarryService drMemberCarryService;
	@Autowired
	private DrMemberFundsService drMemberFundsService;
	@Autowired
	private DrMemberBankService drMemberBankService;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	public RedisClientTemplate redisClientTemplate;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private MemberVipInfoMapper memberVipInfoMapper;
	@Autowired
	private VipInfoMapper vipInfoMapper;
	@Autowired
	private VipEquitiesMemberService vipEquitiesMemberService;
	@Autowired
	private VipEquitiesService vipEquitiesService;
	
	@RequestMapping("/depositsWithdrawals")
	@ResponseBody
	public String depositsWithdrawals(HttpServletRequest req,Integer channel,BigDecimal amount,Integer uid,Integer isChargeFlag){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
        SensorsAnalytics instance = SensorsAnalyticsUtil.getInstance();//申请提现
        Map<String, Object> properties = new HashMap<String, Object>();
        Boolean falg=false;
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			br.setErrorMsg("系统异常");
			return JSON.toJSONString(br);
		}
		try {
		    //神策埋点
            properties.put("OperTime",Utils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            properties.put("AmountOfWithdraw",amount);
            properties.put("WithdrawWay","普通提现");
            if (0!=isChargeFlag){
                properties.put("WithdrawFee",new BigDecimal(2.00));
            }else {
                properties.put("WithdrawFee",BigDecimal.ZERO);
            }
			if(1==member.getIsFuiou()){
				/*Integer errorNums = StringUtils.isBlank(redisClientTemplate.get("error.tpwd.uid."+member.getUid()))?0:
				Integer.parseInt(redisClientTemplate.get("error.tpwd.uid."+member.getUid()));//交易密码错误次数
				if(errorNums>2){
					br.setSuccess(false);
					br.setErrorCode("2001");
					return JSON.toJSONString(br);
				}*/
				if(Utils.isObjectEmpty(member)){
					br.setSuccess(false);
					br.setErrorCode("9999");
					br.setErrorMsg("系统异常");
					properties.put("IsSuccess",falg);
					return JSON.toJSONString(br);
				}
				
				if(Utils.isObjectEmpty(amount)){
					br.setSuccess(false);
					br.setErrorCode("1001");
					br.setErrorMsg("提现金额有误");
                    properties.put("IsSuccess",falg);
					return JSON.toJSONString(br);
				}
					
				if(Utils.isObjectEmpty(isChargeFlag)){
					br.setSuccess(false);
					br.setErrorCode("9999");
					br.setErrorMsg("系统异常");
                    properties.put("IsSuccess",falg);
					return JSON.toJSONString(br);
				}
				boolean result = drProductInvestService.selectInvestCount(member.getUid(), new BigDecimal(amount.toString()),1);
				if(!result){
					br.setSuccess(false);
					br.setErrorCode("2002");
					br.setErrorMsg("返现或体验金收益需完成一次真实投资后才可提现");
                    properties.put("IsSuccess",falg);
					return JSON.toJSONString(br);
				}
				redisClientTemplate.lock("fuiou_withdrawals"+member.getUid().toString());
				DrMemberCarry drMemberCarry = new DrMemberCarry();

				//根据vip等级获取用户免费提现次数
				MemberVipInfo memberVipInfo = memberVipInfoMapper.selectByUid(member.getUid());
				if (memberVipInfo == null){
					memberVipInfo = new MemberVipInfo(null, member.getUid(), new BigDecimal(0),1,Utils.getExpirationTime());
					memberVipInfoMapper.insertSelective(memberVipInfo);
					memberVipInfo = memberVipInfoMapper.selectByUid(member.getUid());
				}
				Integer free = 2;
				VipInfo vipInfo = vipInfoMapper.selectByVipLevel(memberVipInfo.getVipLevel());
				if (vipInfo != null){
					free = vipInfo.getFreeWithdrawDeposit();
				}

				Integer isCharge = drMemberCarryService.getDrCarryParamIsCharge(member.getUid(),free);
				if(isCharge.intValue() != isChargeFlag.intValue()){
					if(isCharge == 1){
						br.setSuccess(false);
						br.setErrorCode("1007");
						br.setErrorMsg("该笔需要收取手续费");
                        properties.put("IsSuccess",falg);
						return JSON.toJSONString(br);
					}else{
						br.setSuccess(false);
						br.setErrorCode("1008");
						br.setErrorMsg("该笔不需要收取手续费");
                        properties.put("IsSuccess",falg);
						return JSON.toJSONString(br);
					}
				}else{
					if(isCharge == 1){
						if(new BigDecimal(amount.toString()).compareTo(new BigDecimal(3))<0){
							br.setSuccess(false);
							br.setErrorCode("1001");
							br.setErrorMsg("提现金额有误");
                            properties.put("IsSuccess",falg);
							return JSON.toJSONString(br);
						}
						drMemberCarry.setPoundage(new BigDecimal(2));
					}else{
						if(new BigDecimal(amount.toString()).compareTo(new BigDecimal(1))<0){
							br.setSuccess(false);
							br.setErrorCode("1001");
							br.setErrorMsg("提现金额有误");
                            properties.put("IsSuccess",falg);
							return JSON.toJSONString(br);
						}
						drMemberCarry.setPoundage(new BigDecimal(0));
					}
				}
				drMemberCarry.setAmount(new BigDecimal(amount.toString()).subtract(drMemberCarry.getPoundage()));
				
				/*if(Utils.isObjectEmpty(tpw)){
					br.setSuccess(false);
					br.setErrorCode("1002");
					return JSON.toJSONString(br);
				}
				
				if(!member.getTpassWord().equals(SecurityUtils.MD5AndSHA256(tpw.toString()))){
					if(errorNums>=2){
						redisClientTemplate.setex("error.tpwd.uid."+member.getUid(), 3600, String.valueOf(errorNums+1));
						br.setSuccess(false);
						br.setErrorCode("2001");
						return JSON.toJSONString(br);
					}
					Integer seconds = Utils.getDateSecondsSub(Utils.format(new Date(), "yyyy-MM-dd 23:59:59"), Utils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
					redisClientTemplate.setex("error.tpwd.uid."+member.getUid(), seconds, String.valueOf(errorNums+1));
					br.setSuccess(false);
					br.setErrorCode("1003");
					return JSON.toJSONString(br);
				}*/
				DrMemberBank drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
				
				DrMemberFunds drMemberFunds = drMemberFundsService.selectDrMemberFundsByUid(member.getUid());
				//TODO 判断存管户
				if(drMemberFunds.getFuiou_balance().compareTo(drMemberCarry.getAmount().add(drMemberCarry.getPoundage()))<0){
					br.setSuccess(false);
					br.setErrorCode("1004");
					br.setErrorMsg("余额不足");
                    properties.put("IsSuccess",falg);
                    return JSON.toJSONString(br);
				}
				
				DrCarryParam drCarryParam = drMemberCarryService.getDrCarryParam();
				drMemberCarry.setChannel(channel);
				Map<String, Object> map = drMemberCarryService.insertFuiouDrMemberCarry(member,drMemberCarry,drMemberBank,drMemberFunds,drCarryParam);
				if(map.size()==0){
					br.setSuccess(false);
					br.setErrorCode("1006");
					br.setErrorMsg("账户资金异常");
                    properties.put("IsSuccess",falg);
					return JSON.toJSONString(br);
				}
				// 插入免费提现权益表
				if (isChargeFlag == 0){
					VipEquities vipEquities = vipEquitiesService.selectByEquitiesName(SystemConstant.FREE_WITHDRAW_DEPOSIT);
					if (vipEquities != null) {
						vipEquitiesMemberService.insertSelective(new VipEquitiesMember(null, member.getUid(), member.getMobilephone(), memberVipInfo.getVipLevel(), vipEquities.getId(), new Date()));
					}
				}
				DrMemberCarry carry = (DrMemberCarry)map.get("drMemberCarry");
				if(Utils.isObjectNotEmpty(carry.getId())){
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("signature", FuiouConfig.withdrawals(member.getMobilephone().toString(),carry.getPaymentNum(),carry.getAmount().toString(),channel == 1||channel ==2 ?"app":"wap"));
					m.put("fuiouUrl", FuiouConfig.APPWITHDRAWURL);
					m.putAll(JSONObject.fromObject(m.get("signature")));
					br.setMap(m);
					br.setSuccess(true);
                    properties.put("IsSuccess",falg);
				}	
			}else{
				br.setErrorMsg("请先开通存管账户");
				br.setSuccess(false);
                properties.put("IsSuccess",falg);
			}
            properties.put("IsSuccess",true);
		} catch (Exception e) {
			log.error("充值首页", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			br.setErrorMsg("系统异常");
            properties.put("IsSuccess",falg);
		}
		try {
            instance.track(String.valueOf(uid), true, "ApplyWithdraw" ,properties);
            instance.flush();
        }catch (Exception e){
            log.error("神策埋点报错!:"+e.getMessage());
        }
        return JSON.toJSONString(br);
	}
	
	
	@RequestMapping("/index")
	@ResponseBody
	public String index(HttpServletRequest req,Integer uid){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		Map<String,Object> map = new HashMap<String, Object>();
		if(Utils.isObjectEmpty(member)){
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		try {

			//根据vip等级获取用户免费提现次数
			MemberVipInfo memberVipInfo = memberVipInfoMapper.selectByUid(member.getUid());
			if (memberVipInfo == null){
				memberVipInfo = new MemberVipInfo(null, member.getUid(), new BigDecimal(0),1, Utils.getExpirationTime());
				memberVipInfoMapper.insertSelective(memberVipInfo);
				memberVipInfo = memberVipInfoMapper.selectByUid(member.getUid());
			}
			Integer free = 2;
			VipInfo vipInfo = vipInfoMapper.selectByVipLevel(memberVipInfo.getVipLevel());
			if (vipInfo != null){
				free = vipInfo.getFreeWithdrawDeposit();
			}
			Integer isCharge = drMemberCarryService.getDrCarryParamIsCharge(member.getUid(),free);
			map.put("isChargeFlag", isCharge);
			map.put("isFuiou", member.getIsFuiou());
			DrMemberFunds drMemberFunds = drMemberFundsService.selectDrMemberFundsByUid(member.getUid());
			map.put("funds",drMemberFunds.getBalance().setScale(2));
	
			if(1 == member.getRealVerify() || member.getIsFuiou() == 1){
				DrMemberBank drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
				
				if(Utils.isObjectEmpty(drMemberBank)){
					 drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
				}
				if(Utils.isObjectNotEmpty(drMemberBank)){
					drMemberBank.setBankNum(drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
					map.put("bankNum",drMemberBank.getBankNum()); 
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("bankName", drMemberBank.getBankName());
					SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
					map.put("bankName", drMemberBank.getBankName());
					map.put("bankId", sysBank.getId());
					map.put("bankCode",sysBank.getId());
				}
				map.put("realName", member.getRealName());
				map.put("idCards",member.getIdCards().substring(0,3)+"********"+member.getIdCards().substring(member.getIdCards().length()-4));
			}
			if(1== member.getIsFuiou()){
				DrMemberBank drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
				
				if(Utils.isObjectNotEmpty(drMemberBank)){
					drMemberBank.setBankNum(drMemberBank.getBankNum().substring(drMemberBank.getBankNum().length()-4,drMemberBank.getBankNum().length()));
				}
				map.put("bankNumFuiou",drMemberBank.getBankNum()); 
				
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("bankName", drMemberBank.getBankName());
				SysBank sysBank = drMemberBankService.selectSysBank(queryMap);
				queryMap.clear();				
				map.put("bankCodeFuiou",sysBank !=null?sysBank.getId():1);
				
				map.put("realName","*"+ member.getRealName().substring(member.getRealName().length()-1,member.getRealName().length()));
				map.put("idCards",member.getIdCards().substring(0,4)+"***"+member.getIdCards().substring(member.getIdCards().length()-3));
				map.put("bankName", sysBank.getBankName());
				map.put("tpwdFlag", StringUtils.isBlank(member.getTpassWord())?0:1);
				map.put("bankName", sysBank.getBankName());
				map.put("tpwdFlag", StringUtils.isBlank(member.getTpassWord())?0:1);
			}
			map.put("fuiou_balance",drMemberFunds.getFuiou_balance().setScale(2));
			
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("提现首页", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	@RequestMapping("/addWithdrawals")
	@ResponseBody
	public String addWithdrawals(HttpServletRequest req,Integer uid,String tpw,BigDecimal amount,Integer isChargeFlag,Integer channel){
		BaseResult br = new BaseResult();
		DrMember member = drMemberService.selectByPrimaryKey(uid);
		Date withdrawalsTime = new Date();
		try{
			Integer errorNums = StringUtils.isBlank(redisClientTemplate.get("error.tpwd.uid."+member.getUid()))?0:
				Integer.parseInt(redisClientTemplate.get("error.tpwd.uid."+member.getUid()));//交易密码错误次数
			Map<String,Object> param = new HashMap<String,Object>();
			Boolean result = drProductInvestService.selectInvestCount(member.getUid(),amount,2);
			if(!result){
				br.setErrorCode("2002");
				br.setSuccess(false);
				return JSON.toJSONString(br);
			}
			if(errorNums>2){
				br.setSuccess(false);
				br.setErrorCode("2001");
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(member)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(isChargeFlag)){
				br.setSuccess(false);
				br.setErrorCode("9999");
				return JSON.toJSONString(br);
			}
			
			if(Utils.isObjectEmpty(amount)){
				br.setSuccess(false);
				br.setErrorCode("1001");
				return JSON.toJSONString(br);
			}
			if(Utils.isObjectEmpty(channel)){
				br.setSuccess(false);
				br.setErrorCode("1009");
				return JSON.toJSONString(br);
			}
			redisClientTemplate.lock("withdrawals"+member.getUid());
			DrMemberCarry drMemberCarry = new DrMemberCarry();

			//根据vip等级获取用户免费提现次数
			MemberVipInfo memberVipInfo = memberVipInfoMapper.selectByUid(member.getUid());
			if (memberVipInfo == null){
				memberVipInfo = new MemberVipInfo(null, member.getUid(), new BigDecimal(0),1,Utils.getExpirationTime());
				memberVipInfoMapper.insertSelective(memberVipInfo);
				memberVipInfo = memberVipInfoMapper.selectByUid(member.getUid());
			}
			Integer free = 2;
			VipInfo vipInfo = vipInfoMapper.selectByVipLevel(memberVipInfo.getVipLevel());
			if (vipInfo != null){
				free = vipInfo.getFreeWithdrawDeposit();
			}
			Integer isCharge = drMemberCarryService.getDrCarryParamIsCharge(member.getUid(),free);
			if(isCharge != isChargeFlag){
				if(isCharge == 1){
					br.setSuccess(false);
					br.setErrorCode("1007");
					return JSON.toJSONString(br);
				}else{
					br.setSuccess(false);
					br.setErrorCode("1008");
					return JSON.toJSONString(br);
				}
			}else{
				if(isCharge == 1){
					if(new BigDecimal(amount.toString()).compareTo(new BigDecimal(3))<0){
						br.setSuccess(false);
						br.setErrorCode("1001");
						return JSON.toJSONString(br);
					}
					drMemberCarry.setPoundage(new BigDecimal(2));
				}else{
					if(new BigDecimal(amount.toString()).compareTo(new BigDecimal(1))<0){
						br.setSuccess(false);
						br.setErrorCode("1001");
						return JSON.toJSONString(br);
					}
					drMemberCarry.setPoundage(new BigDecimal(0));
				}
			}
			drMemberCarry.setAmount(new BigDecimal(amount.toString()).subtract(drMemberCarry.getPoundage()));
			if(Utils.isObjectEmpty(tpw)){
				br.setSuccess(false);
				br.setErrorCode("1002");
				return JSON.toJSONString(br);
			}
			
			if(!member.getTpassWord().equals(tpw.toString())){
				if(errorNums>=2){
					redisClientTemplate.setex("error.tpwd.uid."+member.getUid(), 3600, String.valueOf(errorNums+1));
					br.setSuccess(false);
					br.setErrorCode("2001");
					return JSON.toJSONString(br);
				}
				Integer seconds = Utils.getDateSecondsSub(Utils.format(new Date(), "yyyy-MM-dd 23:59:59"), Utils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
				redisClientTemplate.setex("error.tpwd.uid."+member.getUid(), seconds, String.valueOf(errorNums+1));
				br.setSuccess(false);
				br.setErrorCode("1003");
				return JSON.toJSONString(br);
			}
			drMemberCarry.setChannel(channel);
			//如果客户未绑卡则去找存管开户的卡
			DrMemberBank drMemberBank = new DrMemberBank();
			drMemberBank = drMemberBankService.selectIdentificationBank(member.getUid());
			if(Utils.isObjectEmpty(drMemberBank)){
				drMemberBank = drMemberBankService.selectFuiouIdentificationBank(member.getUid());
			}
			
			DrMemberFunds drMemberFunds = drMemberFundsService.selectDrMemberFundsByUid(member.getUid());
			if(drMemberFunds.getBalance().compareTo(drMemberCarry.getAmount().add(drMemberCarry.getPoundage()))<0){
				br.setSuccess(false);
				br.setErrorCode("1004");
				return JSON.toJSONString(br);
			}
			
			DrCarryParam drCarryParam = drMemberCarryService.getDrCarryParam();
			Map<String, Object> map = drMemberCarryService.insertDrMemberCarry(member,drMemberCarry,drMemberBank,drMemberFunds,drCarryParam);
			if(map.size()==0){
				Date confirmTime = new Date();
				Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("confirmTime", confirmTime);
				map2.put("withdrawalsTime", withdrawalsTime);
				br.setMap(map2);
				br.setSuccess(false);
				br.setErrorCode("1006");
				return JSON.toJSONString(br);
			}
			DrMemberCarry carry = (DrMemberCarry)map.get("drMemberCarry");
			if(Utils.isObjectNotEmpty(carry.getId())){
				br = drMemberCarryService.saveJYTpayment(member,map,drMemberBank,drCarryParam);
			}
		} catch (AppException e) {
        	br.setSuccess(false);
        	br.setErrorCode("1006");
			log.error("认证提现超时", e);
			return JSON.toJSONString(br);
		} catch (Exception e) {
			log.error("提现失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
			return JSON.toJSONString(br);
		}
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("withdrawalsTime", withdrawalsTime);
		if(br.getMap()==null){
			br.setMap(map2);
		}else
		((Map<String,Object>)br.getMap()).putAll(map2);
		return JSON.toJSONString(br);
	}
	
}
