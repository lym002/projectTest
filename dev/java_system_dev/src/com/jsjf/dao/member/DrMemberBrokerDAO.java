package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberBroker;

public interface DrMemberBrokerDAO {
	
	/**
	 * 根据查询条件查询申请理财代理人信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DrMemberBroker> getDrMemberBrokerList(Map<String,Object> map)throws SQLException;
	
	/**
	 * 获取查询总条数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Integer getDrMemberBrokerCounts(Map<String,Object> map) throws SQLException; 
	
	/**
	 * 根据ID获取理财代理人申请对象
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DrMemberBroker selectByPrimaryKey(int id)throws SQLException;
	
	
	public void updateDrMemberBrokerStatus(DrMemberBroker drMemberBroker)throws SQLException;

}