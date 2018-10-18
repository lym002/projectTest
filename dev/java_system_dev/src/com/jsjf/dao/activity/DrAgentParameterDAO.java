/**
 * 
 */
package com.jsjf.dao.activity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrAgentParameter;

/**
 * @author Gerald
 *
 */
public interface DrAgentParameterDAO {
	
	/**
	 * 获取理财代理人返佣参数设置
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DrAgentParameter> getDrAgentParameterList(Map<String,Object> map) throws SQLException;
	
	/**
	 * 获取理财代理人返佣参数设置
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int getDrAgentParameterTotal(Map<String,Object> map) throws SQLException;
	
	/**
	 * 根据ID获取详情
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DrAgentParameter getDrAgentParameterById(Integer id)throws SQLException;
	
	/**
	 * 修改返佣参数
	 * @param dragentParameter
	 * @throws SQLException
	 */
	public void udpateDrAgentParameter(DrAgentParameter drAgentParameter);
	

}
