package com.jsjf.dao.activity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrCouponsIssuedRules;

public interface DrActivityParameterDAO {
	
	/**
	 * 新建活动
	 * @param drActivityParameter
	 * @throws SQLException
	 */
	public void insertActivityParameter(DrActivityParameter drActivityParameter)throws SQLException ;
	
	
	/**
	 * 更新活动
	 * @param drActivityParameter
	 * @throws SQLException
	 */
	public void updateActivityParameter(DrActivityParameter drActivityParameter) throws SQLException ;
	
	/**
	 * 查询活动信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DrActivityParameter getDrActivityParameterByParam(Map<String,Object> map);
	/**
	 * 查询活动列表总条数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int getDrActivityParameterTotal(Map<String,Object> map) throws SQLException;
	
	/**
	 * 根据ID获取活动详情
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DrActivityParameter getActivityParameterById(Integer id)throws SQLException;
	/**
	 * xi
	 * @param drActivityParameter
	 * @throws SQLException
	 */
	public void toModifyActivity(DrActivityParameter drActivityParameter)throws SQLException;
	
	public List<DrCouponsIssuedRules> getCouponsIssuedRules(Map<String, Object> map)throws SQLException;
	/**
	 * 根据PID获取活动详情
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<DrActivityParameter> getActivityParameterByPid(Integer pid);
	
	/**
	 * 查询活动绑定募集中产品的最高利率的加息券
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> selectActivityParameterMap();
	/**
	 * 根据map查询红包list
	 * @return
	 */
	public List<Map<String,Object>> selectActivityParameterByMap(Map<String, Object> map);


	/**查询所有红包信息
	 * @return
	 */
	public DrActivityParameter selectActivityParameter();

	/**
	 * 根据ids查询优惠券
	 * @param map
	 * @return
	 */
	public List<DrActivityParameter> selectActivityParamterByIds(Map<String,Object> map);

	/**
	 * 查询接口
	 * @param pam
	 * @return
	 */
	DrActivityParameter selectActivityParameterPrimaryKey(Map<String, Object> pam);

	/**
	 * 兑换查询接口
	 * @param pam
	 * @return
	 */
	DrActivityParameter selectParameterPrimaryKey(Map<String, Object> pam);

	/**
	 * 查询月度礼包
	 * @param vipLevel
	 * @return
	 */
	List<DrActivityParameter> selectParameterByVipMonth(Integer vipLevel);

	/**
	 * 查询月度礼包是否被领取
	 * @param uid
	 * @return
	 */
	DrActivityParameter selectParameterByVipMonthIsGet(Integer uid);
}
