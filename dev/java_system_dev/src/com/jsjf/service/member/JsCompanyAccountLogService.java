package com.jsjf.service.member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.JsCompanyAccountLog;

public interface JsCompanyAccountLogService {
	
	public void insertCompanyAccountLog(JsCompanyAccountLog accountLog);

	public List<Map<String, Object>> getCompanyAccountLog(Map<String, Object> map);
	
	public int getCompanyAccountLogCount(Map<String, Object> map);

	/**
	 * 统计收入
	 * @param map
	 * @return
	 */
	public BigDecimal getCompanyAccountSRSum(Map<String, Object> map);
	
	/**
	 * 统计支出
	 * @param map
	 * @return
	 */
	public BigDecimal getCompanyAccountZCSum(Map<String, Object> map);
	
	public void updateCompanyAccountLog(JsCompanyAccountLog accountLog);


}
