package com.jsjf.dao.claims;

import java.util.List;
import java.util.Map;

import com.jsjf.model.claims.JsClaimsAuditEdit;

public interface JsClaimsAuditEditDAO {
	/**
	 * 通过用户UID获取用户信息
	 * @param uid
	 * @return
	 */
	public JsClaimsAuditEdit selectByPrimaryKey(Integer id);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<JsClaimsAuditEdit> selectByMap(Map<String,Object> map);
	/**
	 * 
	 * @param ob
	 */
	public void update(JsClaimsAuditEdit obj);
	/**
	 * 
	 * @param ob
	 */
	public void insert(JsClaimsAuditEdit obj);
	
}
