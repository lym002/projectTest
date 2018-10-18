package com.jsjf.dao.cpa;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.cpa.DrChannelInfo;

public interface DrChannelInfoDAO {
	/**
	 * 得到渠道信息列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoList(Map<String, Object> map);

	/**
	 * 得到渠道信息总数
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getDrChannelInfoCounts(Map<String, Object> map);

	/**
	 * 添加渠道信息
	 * 
	 * @param DrChannelInfo
	 * @return void
	 * @throws SQLException
	 *             ;
	 */
	public void insertDrChannelInfo(DrChannelInfo drChannelInfo)
			throws SQLException;

	/**
	 * 修改渠道信息
	 * 
	 * @param DrProductInfo
	 * @return void
	 * @throws SQLException
	 *             ;
	 */
	public void updateDrChannelInfo(DrChannelInfo drChannelInfo)
			throws SQLException;

	/**
	 * 根据id得到渠道信息
	 * 
	 * @param id
	 * @return DrChannelInfo
	 */
	public DrChannelInfo getDrChannelInfoByid(Integer id);

	/**
	 * 根据Map得到渠道信息
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoListForMap(
			Map<String, Object> map);

	/**
	 * 得到渠道用户列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoUserList(Map<String, Object> map);

	/**
	 * 得到渠道用户总数
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getDrChannelInfoUserCounts(Map<String, Object> map);

	/**
	 * 得到渠道订单列表
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoOrderList(Map<String, Object> map);

	/**
	 * 得到渠道订单总数
	 * 
	 * @param Map
	 * @return Integer
	 */
	public Integer getDrChannelInfoOrderCounts(Map<String, Object> map);

}
