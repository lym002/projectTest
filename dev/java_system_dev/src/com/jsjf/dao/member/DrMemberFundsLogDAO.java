package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberFundsLog;


public interface DrMemberFundsLogDAO {
	
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
    public List<DrMemberFundsLog> getDrMemberFundsLogList(Map<String,Object> map); 
   
	/**
	 * 得到客户收支记录数据总数
	 * @param Map
	 * @return Integer
	 * @throws SQLException
	 */
    public Integer getDrMemberFundsLogCounts(Map<String,Object> map); 
    
	/**
	 * 统计收入支出
	 * @param Map
	 * @return BigDecimal
	 */
    public BigDecimal getDrMemberFundsLogSum(Map<String,Object> map); 
    
    /**
     * 批量插入日志
     * @param list
     */
    public void batchInsert(List<DrMemberFundsLog> list);


	public String queryCouponAmount(Integer id);
    
}