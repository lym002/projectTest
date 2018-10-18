package com.jsjf.service.filing;


import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.filing.DrFiling;

import net.sf.json.JSONArray;

public interface FilingService {

	
	/**
	 * 交易报备补报查询
	 * @param drTransaction
	 * @param pi
	 * @return
	 */
	BaseResult getInvestFilingList(DrFiling drTransaction, PageInfo pi);
	
	/**
	 *  P2P商户交易（冻结，动账）
	 *  获取投标记录
	 * @param jsonArray
	 * @param businesstype
	 */
	void getInvestFiling(JSONArray jsonArray, String businesstype);

	/**
	 * 满标放款
	 * @param filingListData
	 * @param businesstype
	 */
	void getFullCreditFiling(JSONArray filingListData, String businesstype);
	
	/**
	 * 回款
	 * @param filingListData
	 * @param businesstype
	 */
	void getInvestReturnedMoneyFiling(JSONArray filingListData, String businesstype);


}
