package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberFundsRecord;

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
	 * 根据流水号查询
	 * @param orderNo
	 * @return
	 */
	public DrMemberFundsRecord selectMemberFundsRecordByOrderNo(String orderNo);

    Integer selectInvestSumByOnlineTime(Map<String, Object> map);

	Integer selectInvestListCountByParam(Map<String, Object> param);

	Integer selectFristRechar(Map<String, Object> param);

    List<DrMemberFundsRecord> selectBidFullByUid(Map<String, Object> map);
}