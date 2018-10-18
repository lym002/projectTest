package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrCompanyFundsLog;

public interface DrCompanyFundsLogDAO {
	/**
	 * 插入数据
	 * @param DrCompanyFundsLog
	 * @return void
	 * @throws SQLException
	 */
	public void insertDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
	
	/**
	 * 得到公司收支记录数据
	 * @param Map
	 * @return List<DrCompanyFundsLog>
	 */
    public List<DrCompanyFundsLog> getDrCompanyFundsLogList(Map<String,Object> map); 
   
	/**
	 * 得到公司收支记录数据总数
	 * @param Map
	 * @return Integer
	 */
    public Integer getDrCompanyFundsLogCounts(Map<String,Object> map); 
    
 	/**
 	 * 统计支出收支
 	 * @param Map
 	 * @return BigDecimal
 	 * @throws SQLException
 	 */
     public BigDecimal getDrCompanyFundsLogSum(Map<String,Object> map) throws SQLException; 
     
 	/**
 	 * 更新数据
 	 * @param DrCompanyFundsLog
 	 * @return void
 	 * @throws SQLException
 	 */
 	public void updateDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
 	
	/**
	 * 插入数据
	 * @param DrCompanyFundsLog
	 * @return void
	 * @throws SQLException
	 */
	public void insertDrCompanyFundsLogFK(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
	public void updateDrCompanyFundsLogByPid(DrCompanyFundsLog drCompanyFundsLog);
}