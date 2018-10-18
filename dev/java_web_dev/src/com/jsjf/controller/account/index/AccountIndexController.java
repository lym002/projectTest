package com.jsjf.controller.account.index;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCpsFavourableRule;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.DrProductInvestRepayInfo;
import com.jsjf.service.activity.DrMemberFavourableService;
import com.jsjf.service.member.DrMemberBankService;
import com.jsjf.service.member.DrMemberCpsFavourableRuleService;
import com.jsjf.service.member.DrMemberFundsRecordService;
import com.jsjf.service.member.DrMemberFundsService;
import com.jsjf.service.member.DrMemberMsgService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestRepayInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@RequestMapping("/accountIndex")
@Controller
public class AccountIndexController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private RedisClientTemplate redis;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrMemberFundsService DrMemberFundsService;
	@Autowired
	private DrMemberBankService DrMemberBankService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrMemberFundsRecordService drMemberFundsRecordService;
	@Autowired
	private DrMemberMsgService drMemberMsgService;
	@Autowired
	DrProductInvestRepayInfoService drProductInvestRepayInfoService;
	@Autowired
	DrMemberCpsFavourableRuleService drMemberCpsFavourableRuleService;
	@Autowired
	private DrMemberFavourableService drMemberFavourableService;
	
	
	@RequestMapping("/info")
	@ResponseBody
	public String info(HttpServletRequest req){
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		map.put("isPayment", false);//没回款
		try {
			
			DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
			DrMember m = drMemberService.selectByPrimaryKey(member.getUid());
			DrMemberBank bank = DrMemberBankService.selectIdentificationBank(member.getUid());
			map.put("mobileVerify", m.getMobileVerify());
			map.put("realVerify", m.getRealVerify());
			map.put("isFuiou", m.getIsFuiou());
			map.put("bankVerify", Utils.isObjectEmpty(bank)?0:1);
			map.put("tpwdVerify", StringUtils.isBlank(m.getTpassWord())?0:1);
			map.put("sex", m.getSex());
			map.put("realName", m.getRealName());
			map.put("mobilephone", m.getMobilephone());
			//票据安选待收总额
			param.put("uid", m.getUid());
			param.put("type", 2);
			map.put("axInvestSum", drProductInvestService.selectInvestSumByParam(param));
			//票据优选待收总额
			param.put("type", 3);
			map.put("yxInvestSum", drProductInvestService.selectInvestSumByParam(param));
			param.put("type", 1);
			//新手标待收
			map.put("xsInvestSum", drProductInvestService.selectInvestSumByParam(param));
			//未读消息条数
			param.clear();
			param.put("uid", m.getUid());
			param.put("isRead", 0);
			Integer unReadCount = drMemberMsgService.getUnReadCountByMap(param);
			map.put("unReadMsg", unReadCount);
			//账户资金
			DrMemberFunds funds = DrMemberFundsService.selectDrMemberFundsByUid(member.getUid());
			map.put("balance", funds.getBalance());
			map.put("free", funds.getFreeze().add(funds.getFuiou_freeze()));
			BigDecimal collectAmount = funds.getWinterest().add(funds.getWpenalty()).add(funds.getWprincipal()
					.add(funds.getFuiou_winterest().add(funds.getFuiou_wpenalty()).add(funds.getFuiou_wprincipal())));
			map.put("collectAmount", collectAmount);
			
			//存管
			map.put("balanceFuiou", funds.getFuiou_balance());
			map.put("freeFuiou", funds.getFuiou_freeze());
			BigDecimal collectAmountFuiou = funds.getFuiou_winterest().add(funds.getFuiou_wpenalty()).add(funds.getFuiou_wprincipal());
			map.put("collectAmountFuiou", collectAmountFuiou);		
			
			map.put("profitFuiou", funds.getFuiou_investProfit().add(funds.getFuiou_spreadProfit()));//已收投资收益+推广收益
			map.put("winterestFuiou", funds.getFuiou_winterest());//待收收益
			
			
			//我的收益
			map.put("profit", funds.getInvestProfit().add(funds.getSpreadProfit())
					.add(funds.getFuiou_investProfit()).add(funds.getFuiou_spreadProfit()));//已收投资收益+推广收益
			map.put("winterest", funds.getWinterest().add(funds.getFuiou_winterest()));//待收收益
			
			//我的投资产品
			PageInfo pi = new PageInfo(1, 5);
			param.clear();
			param.put("uid", member.getUid());
			List<DrProductInvest> investList =  (List<DrProductInvest>)((PageInfo)drProductInvestService.selectInvestLogByParam(param, pi).getMap().get("page")).getRows();
			map.put("investList", investList);
			
			//资金记录
			param.put("uid", member.getUid());
			List<DrMemberFundsRecord> record = (List<DrMemberFundsRecord>)((PageInfo)drMemberFundsRecordService.selectMemberFundsRecordByParam(param , pi).getMap().get("page")).getRows();
			map.put("fundsRecord", record);
			//产品推荐
			List<DrProductInfo> ductInfoList = drProductInfoService.selectHotProductInfo();
			map.put("infoList", ductInfoList);
			//前5天后5天有无回款
//			List<DrProductInvestRepayInfo> drProductInvestRepayInfo = drProductInvestRepayInfoService.selectPayment(m.getUid());
			//前5天后5天回款总和
//			if(drProductInvestRepayInfo.size()>0){
//				BigDecimal shouldPrincipalCount = BigDecimal.ZERO;
//				for (DrProductInvestRepayInfo drProductInvestRepayInfo2 : drProductInvestRepayInfo) {
//					shouldPrincipalCount = shouldPrincipalCount.add(drProductInvestRepayInfo2.getShouldPrincipal()); 
//				}
//				if(shouldPrincipalCount.compareTo(new BigDecimal(0))==1){
//					Map<String,Object> paramMap = new HashMap<String,Object>();
//					//查询用户是否首投
//					int isFirst = drMemberService.selectFirstPayMent(m.getUid());
//					paramMap.put("uid", m.getUid());
//					paramMap.put("shouldPrincipalCount", shouldPrincipalCount.intValue());
//					paramMap.put("isFirst", isFirst ==0?1:0);
//					//回款大于0并且存在投资送的规则
//					List<DrMemberCpsFavourableRule> list = drMemberCpsFavourableRuleService.selectActivityByCps(paramMap);
//					if(list.size()>0){
						map.put("isPayment", false);//有回款
//						//复投红包机会统计次数
//						long flag =redis.sadd("fuTouSet", m.getUid().toString());
//						if(flag==1){
//							redis.incr("fuTouHongBaoJiHuiTongJi");	
//						}
//					}
//				}
//			}
			//是否有体验金
			param.clear();
			param.put("uid", m.getUid());
			param.put("type", new Integer[]{3});
			param.put("status", 0);
			List<DrMemberFavourable> list = drMemberFavourableService.selectByParam(param);
			map.put("isExperience", list.size()>0?true:false);
			//体验金
			BigDecimal experienceAmount = drMemberFavourableService.selectRegSendExperienceGold(m.getUid());
			map.put("experienceAmount", experienceAmount != null?experienceAmount:0);
			param.clear();
			param.put("status", 0);
			param.put("source", 100);
			param.put("uid", m.getUid());
			param.put("type",new Integer[]{3} );
			List<DrMemberFavourable> drMemberFavourableList = drMemberFavourableService.getMemberFavourableByParam(param);
			map.put("drMemberFavourableList", drMemberFavourableList);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("我的账户信息读取错误", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	
	@RequestMapping("/indexFunds")
	@ResponseBody
	public BaseResult indexFunds(HttpServletRequest req){
		BaseResult result = new BaseResult();
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		Map<String,Object> map = new HashMap<String,Object>();
		DrMemberFunds drMemberFunds = DrMemberFundsService.selectDrMemberFundsByUid(member.getUid());
		map.put("balance", drMemberFunds.getBalance().intValue()==0?10:drMemberFunds.getBalance());
		map.put("wprincipal", drMemberFunds.getWprincipal().intValue()==0?10:drMemberFunds.getWprincipal());
		map.put("freeze", drMemberFunds.getFreeze().intValue()==0?10:drMemberFunds.getFreeze());
		result.setMap(map);
		return result;
	}
	
	@RequestMapping("/verifyRealAndTrade")
	@ResponseBody
	public BaseResult verifyRealAndTrade(HttpServletRequest req){
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String,Object>();
		DrMember member = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		member = drMemberService.selectByPrimaryKey(member.getUid());
		
		map.put("real", member.getRealVerify());
		String trade = "1";
		if(null == member.getTpassWord()){
			trade = "0";
		}
		map.put("trade", trade);
		result.setMap(map);
		return result;
	}
	
}
