package com.jsjf.dao.claims;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.claims.DrAuditInfo;

public interface DrAuditInfoDAO {
	
 	/**
 	 * 添加审核意见
 	 * @param  DrAuditInfo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertDrAuditInfo(DrAuditInfo drAuditInfo) throws SQLException; 
 	
	/**
	 * 查询审核意见
	 * @param Map<String,Object>
	 * @return List<DrAuditInfo>
	 */
    public List<DrAuditInfo> getDrAuditInfo(Map<String,Object> map); 
}