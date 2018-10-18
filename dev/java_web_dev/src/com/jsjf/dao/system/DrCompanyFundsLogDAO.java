package com.jsjf.dao.system;

import java.sql.SQLException;
import java.util.Map;

import com.jsjf.model.system.DrCompanyFundsLog;

public interface DrCompanyFundsLogDAO {
	
	/**
	 * 插入数据
	 * @param DrCompanyFundsLog
	 * @return void
	 * @throws SQLException
	 */
	public void insertDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
	public DrCompanyFundsLog selectDrCompanyFundsLogByMap(Map<String,Object> param);
	
	public void update(Map<String,Object> param);
}