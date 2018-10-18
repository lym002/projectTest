package com.jsjf.service.member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberFundsRecord;

public interface DrMemberFundsRecordService {
	/**
	 * 获取用户资金日志
	 * @param map
	 * @return
	 */
	public BaseResult getDrMemberFundsRecordList(Map<String,Object> map,PageInfo pi);
	
	public List<Map<String, Object>> getFundsRecord(Map<String, Object> map);
	
	public int getFundsRecordCount(Map<String, Object> map);
	
	/**
	 * 统计收入
	 * @param map
	 * @return
	 */
	public BigDecimal getFundsRecordSRSum(Map<String, Object> map);
	
	/**
	 * 统计支出
	 * @param map
	 * @return
	 */
	public BigDecimal getFundsRecordZCSum(Map<String, Object> map);


    List<Integer> selectUidByCondition(Map<String, Object> map);

}
