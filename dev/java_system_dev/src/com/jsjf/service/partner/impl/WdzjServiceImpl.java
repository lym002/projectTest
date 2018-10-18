package com.jsjf.service.partner.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.Utils;
import com.jsjf.dao.claims.DrClaimsBillDAO;
import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.dao.product.DrProductInfoDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.dao.subject.DrSubjectInfoDAO;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.service.partner.WdzjService;

@Service
public class WdzjServiceImpl implements WdzjService {
	@Autowired
	private DrProductInfoDAO drProductInfoDAO;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private DrClaimsLoanDAO drClaimsLoanDAO; 

	@Override
	public Map<String, Object> getData(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		param.put("statuses", new Integer[]{6,8,9});
		param.put("hasSid", 1);
		List<DrProductInfo>  list = drProductInfoDAO.getDrProductInfoListByMap(param);
		Integer total = drProductInfoDAO.getDrProductInfoListCountByMap(param);
		result.put("totalCount", total);
		result.put("totalPage", (total+Integer.parseInt(param.get("pageSize").toString())-1)/Integer.parseInt(param.get("pageSize").toString()));
		result.put("currentPage", param.get("page"));
		List<Map<String, Object>> borrowList = new ArrayList<Map<String,Object>>();
		Map<String, Object> borrowObj = null;
		Map<String, Object> queryParam = null;
		Map<String, Object> investObj = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DrProductInfo info = (DrProductInfo) iterator.next();
			borrowObj = new HashMap<String, Object>();
			borrowObj.put("projectId", info.getId());//项目主键
			borrowObj.put("title", info.getFullName());//借款标题
			borrowObj.put("amount", info.getAmount());//借款金额
			borrowObj.put("schedule", 100);//进度
			borrowObj.put("interestRate", info.getRate().add(info.getActivityRate())+"%");//利率
			borrowObj.put("deadline", info.getDeadline());//借款期限
			borrowObj.put("deadlineUnit", "天");//期限单位
			borrowObj.put("reward", 0);//奖励
			borrowObj.put("type", "质押标");//标的类型 例如：抵押标，质押标，信用标，债权转让标，净值标，秒标
			borrowObj.put("repaymentType", 1);//还款方式
			
			DrClaimsLoan loan = drClaimsLoanDAO.getDrClaimsLoanBySid(info.getSid());
			borrowObj.put("userName", loan.getLoanName());//发标人名称
			
			borrowObj.put("loanUrl", "https://www.byp.cn/main/billDetail?id="+info.getId());//产品详情页地址
			borrowObj.put("successTime", Utils.format(info.getFullDate(), "yyyy-MM-dd HH:mm:ss"));//满标时间
			borrowObj.put("publishTime", Utils.format(info.getStartDate(), "yyyy-MM-dd HH:mm:ss"));//发布时间

			queryParam = new HashMap<String, Object>();
			queryParam.put("pid", info.getId());
			queryParam.put("limit", 9999);
			queryParam.put("offset", 0);
			List<DrProductInvest> investList = drProductInvestDAO.getDrProductInvestList(queryParam);
			List<Map<String, Object>> subList = new ArrayList<Map<String,Object>>();
			for (Iterator iterator2 = investList.iterator(); iterator2
					.hasNext();) {
				DrProductInvest invest = (DrProductInvest) iterator2.next();
				investObj = new HashMap<String, Object>();
				investObj.put("subscribeUserName", Utils.getHanyuToPinyin(invest.getRealname()));
				investObj.put("amount", invest.getAmount());
				investObj.put("validAmount", invest.getAmount());
				investObj.put("addDate", Utils.format(invest.getInvestTime(), "yyyy-MM-dd HH:mm:ss"));
				investObj.put("status", 1);
				investObj.put("type", 0);
				investObj.put("sourceType", invest.getJoinType()==0?1:invest.getJoinType()==1||invest.getJoinType()==2?3:2);
				subList.add(investObj);
			}
			borrowObj.put("subscribes", subList);//投资人数据
			borrowList.add(borrowObj);
		}
		result.put("borrowList", borrowList);
		return result;
	}
	
	

}
