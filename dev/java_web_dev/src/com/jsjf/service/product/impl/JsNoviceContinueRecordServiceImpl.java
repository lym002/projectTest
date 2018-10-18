package com.jsjf.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberFundsDAO;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.dao.product.JsNoviceContinueRecordDAO;
import com.jsjf.dao.system.DrCompanyFundsLogDAO;
import com.jsjf.model.member.DrMemberFunds;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.JsNoviceContinueRecord;
import com.jsjf.model.system.DrCompanyFundsLog;
import com.jsjf.service.product.JsNoviceContinueRecordService;
import com.jsjf.service.system.impl.RedisClientTemplate;
@Service
@Transactional
public class JsNoviceContinueRecordServiceImpl implements
		JsNoviceContinueRecordService {
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired 
	JsNoviceContinueRecordDAO jsNoviceContinueRecordDAO;
	@Autowired 
	DrMemberFundsDAO drMemberFundsDAO;
	@Autowired 
	DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired 
	DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired 
	DrMemberMsgDAO drMemberMsgDAO;
	@Autowired 
	DrCompanyFundsLogDAO drCompanyFundsLogDAO;
	
	
	@Override
	public Map<String, Object> getContinueReward(DrProductInvest dpi) throws Exception {
		
		String[] continueConfig = redisClientTemplate.getProperties("continueConfig").split("-");
		List<Map<String,Object>> rewardList = new ArrayList<>();
		Map<String,Object> map ;
		String[] config ;////0.6,35,6,88,1 -> (获得的红包比例/100,续投的产品期限,续投的产品利率,获得红包最大金额,续投产品的活动利率
		for (int i = 0; i < continueConfig.length; i++) {
			map = new HashMap<String, Object>();
			config = continueConfig[i].split(",");
			int amount =  (int) (dpi.getAmount().intValue() * Double.valueOf(config[0])/100);
			amount = amount <=1?1:amount;
			
			BigDecimal activityRate = BigDecimal.ZERO;
			if(Utils.isObjectNotEmpty(config[4])){
				activityRate = new BigDecimal(config[4]);
			}
			
			BigDecimal profitAmount = dpi.getAmount().multiply(new BigDecimal(config[2]).add(activityRate))
					.multiply(new BigDecimal(config[1])).divide(new BigDecimal("360").multiply(new BigDecimal("100")),2,BigDecimal.ROUND_DOWN);
						
			map.put("investAmount", dpi.getAmount());
			map.put("deadline", Integer.parseInt(config[1]));
			map.put("rate", Double.parseDouble(config[2]));			
			map.put("profitAmount", profitAmount);
			map.put("activityRate", activityRate);
			map.put("amount", amount >= Integer.valueOf(config[3])?Integer.valueOf(config[3]):amount);
			
			rewardList.add(map);
		}
		//查询续投记录
		map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("limit", 10);
		List<Map<String,Object>> parcelList = jsNoviceContinueRecordDAO.selectResultByMap(map);
		
		map.clear();
		map.put("parcelList", parcelList);
		map.put("rewardList", rewardList);
		return map;
	}

	@Override
	public List<JsNoviceContinueRecord> selectJsNoviceContinueRecord(
			Map<String, Object> map) {
		

		return null;
	}

	@Override
	public BaseResult addContinueReward(DrProductInvest dpi,Integer period) throws Exception {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("uid", dpi.getUid());		
		List<JsNoviceContinueRecord> list = jsNoviceContinueRecordDAO.selectJsNoviceContinueRecordByMap(map);
		if(Utils.isEmptyList(list)){//没有续投过  
			String[] continueConfig = redisClientTemplate.getProperties("continueConfig").split("-");  //0.6,35,6,88,1 -> (获得的红包比例/100,续投的产品期限,续投的产品利率,获得红包最大金额,续投产品的活动利率
			int amount =0 ;//红包金额
			String[] config ;
			BigDecimal profitAmount = BigDecimal.ZERO;
			for (int i = 0; i < continueConfig.length; i++) {//计算红包应得红包
				config = continueConfig[i].split(",");
				if(Integer.parseInt(config[1])==period){				
					amount =  (int) (dpi.getAmount().intValue() * Double.valueOf(config[0])/100);
					amount = amount <=1?1:amount;
					amount = amount >= Integer.valueOf(config[3])? Integer.valueOf(config[3]) : amount;
					/*profitAmount = dpi.getAmount().multiply(new BigDecimal(config[2]))
							.multiply(new BigDecimal(config[1])).divide(new BigDecimal("360")).divide(new BigDecimal("100")).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_DOWN);*/
					BigDecimal activityRate = BigDecimal.ZERO;
					if(Utils.isObjectNotEmpty(config[4])){
						activityRate = new BigDecimal(config[4]);
					}
					
					profitAmount = dpi.getAmount().multiply(new BigDecimal(config[2]).add(activityRate))
							.multiply(new BigDecimal(config[1])).divide(new BigDecimal("360").multiply(new BigDecimal("100")),2,BigDecimal.ROUND_DOWN);
					break;
				}	
			}	
			//插入续投
			JsNoviceContinueRecord bean = 
					new JsNoviceContinueRecord(dpi.getUid(), dpi.getId(), new Date(),
							Utils.getDayNumOfAppointDate(dpi.getInvestTime(), -1-dpi.getDeadline()), period, dpi.getAmount(),
							0, null, 0,new BigDecimal(amount));
			jsNoviceContinueRecordDAO.insert(bean);
		
			//      现金返现
			DrMemberFunds fund = drMemberFundsDAO.queryDrMemberFundsByUid(dpi.getUid());
			fund.setBalance(Utils.nwdBcadd(fund.getBalance(), new BigDecimal(amount)));
			fund.setInvestProfit(fund.getInvestProfit().add(new BigDecimal(amount)));//已收益加上红包收益
			drMemberFundsDAO.updateDrMemberFunds(fund);
			//返现日志1
			DrMemberFundsRecord record= new DrMemberFundsRecord(dpi.getPid(), dpi.getId(),  dpi.getUid(), 4, 1, new BigDecimal(amount), fund.getBalance(),
					3, "新手标续投领现金红包", null);
			drMemberFundsRecordDAO.insert(record);
			//返现日志2
			DrMemberFundsLog log = new DrMemberFundsLog(dpi.getUid(), record.getId(), new BigDecimal(amount), 20, 1, "新手标续投领现金红包");
			drMemberFundsLogDAO.insertDrMemberFundsLog(log);
			
			//公司资金交易记录表
			DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11, dpi.getUid(), null,new BigDecimal(amount),
					0, "新手标续投领现金红包", 0);
			drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
			
			//站内信
			String context = redisClientTemplate.getProperties("noviceContinueMsg")
					.replace("${period}", period+"")
					.replace("${amount}", dpi.getAmount().toString())
					.replace("${profitAmount}", profitAmount.toString())
					.replace("${date}", Utils.format(Utils.getDayNumOfAppointDate(dpi.getInvestTime(), -1-period), "yyyy-MM-dd"));
			DrMemberMsg msg = new DrMemberMsg(dpi.getUid(), 0, 1, "续投申请成功", new Date(), 0, 0, context);
			drMemberMsgDAO.insertDrMemberMsg(msg);	
//			msg = new DrMemberMsg(dpi.getUid(), 0, 2, "新手标续投领现金红包", new Date(), 0, 0, "文案没有,等...");
//			drMemberMsgDAO.insertDrMemberMsg(msg);
			br.setSuccess(true);
		}else{
			br.setErrorCode("1003");
			br.setErrorMsg("已经续投过");
		}		
		return br;
	}

}
