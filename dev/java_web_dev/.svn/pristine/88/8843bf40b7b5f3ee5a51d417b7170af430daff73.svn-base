package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberFundsLog;


public interface DrMemberFundsLogDAO {
	/**
	 * 查询推荐活动返利总收益及用户信息
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getRecommendActivityRebateSum(Map<String,Object> map);
	/**
	 * 查询送出注册返利红包的总数
	 * @param map
	 * @return
	 */
	
	public Integer getRegRebateSumNumber(Map<String,Object> map);
	
	/**
	 * 插入数据
	 * @param DrMemberFundsLog
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberFundsLog(DrMemberFundsLog DrMemberFundsLog) throws SQLException; 
    
	
	/**
	 * 得到客户收支记录数据
	 * @param Map
	 * @return List<DrMemberFundsLog>
	 * @throws SQLException
	 */
    public List<DrMemberFundsLog> getDrMemberFundsLogList(Map<String,Object> map) throws SQLException; 
    
//    /**
//     * 导出客户查询到的收支记录数据
//     * @param Map
//     * @return List<DrMemberFundsLog>
//     * @throws SQLException
//     */
//    public List<DrMemberFundsLog> exportDrMemberFundsLogList(Map<String,Object> map) throws SQLException; 
    
	/**
	 * 得到客户收支记录数据总数
	 * @param Map
	 * @return Integer
	 * @throws SQLException
	 */
    public Integer getDrMemberFundsLogCounts(Map<String,Object> map) throws SQLException; 

    /**
     * 活动返利明细
     * @param map
     * @return List<Map{"varyType":?,"amounts":?}>
     * @throws SQLException
     */
    public List<Map<String,Object>> getDrMemberRegSum(Map<String,Object> map) throws SQLException; 
    
	/**
	 * 根据连连支付支付单号查询
	 * @param flowNo
	 * @return DrMemberFundsLog
	 */
    public DrMemberFundsLog getDrMemberFundsLogByFlowNo(String flowNo); 
    
    /**
     * 客户资产记录
     * @param map
     * @return
     */
    public List<DrMemberFundsLog> getMemberAssetRecordList(Map<String,Object> map);
    //资产记录总数
    public Integer getMemberAssetRecordListCount(Map<String,Object> map);

}