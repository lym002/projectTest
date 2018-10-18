package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.integral.TaskIntegralRules;
import com.jsjf.model.member.DrMemberFundsRecord;
import com.jsjf.model.member.JsCompanyAccountLog;

public interface DrMemberFundsRecordDAO {
	
	public void insert(DrMemberFundsRecord record) throws SQLException;
	
	/**
	 * 查找符合条件的资产记录
	 * @param map
	 * @return
	 */
	public List<DrMemberFundsRecord> selectMemberFundsRecordListByParam(Map<String, Object> map);
	
	public Integer selectMemberFundsRecordListCountByParam(Map<String, Object> map);
	/**
	 * 根据订单号修改
	 * @param DrMemberFundsRecord
	 * @return void
	 */
	public void updateStatusByNo(DrMemberFundsRecord record) throws SQLException;
	/**
	 * 查询累计收益
	 * @param map
	 * @return
	 */
	public List<DrMemberFundsRecord> selectAccumulatedIncomeByUid(Map<String, Object> map);
	/**
	 * 查询累计收益Count
	 * @param map
	 * @return
	 */
	public Integer selectAccumulatedIncomeByUidCount(Map<String, Object> map);
	/**
	 * 查询累计收益sum
	 * @param map
	 * @return
	 */
	public BigDecimal selectAccumulatedIncomeSumByUid(Map<String, Object> map);

	Integer selectInvestSumByOnlineTime(Map<String, Object> param);

    Integer selectInvestListCountByParam(Map<String, Object> param);

    Integer selectFristRechar(Map<String, Object> param);

    List<DrMemberFundsRecord> selectMemberFundsRecordByParam(Map<String, Object> param);

    List<DrMemberFundsRecord> selectMemberFundsRecordListByParams(Map<String, Object> param);

    List<DrMemberFundsRecord> selectBidFullByUid(Map<String, Object> map);
}
