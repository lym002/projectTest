package com.jsjf.service.member;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionException;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrCompanyFundsLog;

public interface DrCompanyFundsLogService {
	
	/**
	 * 得到公司收支记录列表数据
	 * @param DrCompanyFundsLog
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getCompanyFundsLogList(DrCompanyFundsLog drCompanyFundsLog,PageInfo pi);
	
	/**
	 * 统计收入支出
	 * @param Map<String,Object>
	 * @return BigDecimal
	 * @throws SQLException
	 */
	public Map<String,Object> getDrCompanyFundsLogSum(DrCompanyFundsLog drCompanyFundsLog);
	
	/**
	 * 插入数据
	 * @param DrCompanyFundsLog
	 * @return void
	 * @throws SQLException
	 */
	public void insertDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
	/**
	 * 更新
	 * @param drCompanyFundsLog
	 * @return
	 * @throws SQLException
	 */
	public BaseResult upDateDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog);
	/**
	 * 插入放款数据
	 * @param DrCompanyFundsLog
	 * @return void
	 * @throws SQLException
	 */
	public void insertDrCompanyFundsLogFK(DrCompanyFundsLog drCompanyFundsLog) throws SQLException;
	
}