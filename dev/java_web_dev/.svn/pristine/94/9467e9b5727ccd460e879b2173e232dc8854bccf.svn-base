package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsActivityHitIceLog;

public interface JsActivityHitIceLogDAO {
	
	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public JsActivityHitIceLog selectByPrimaryKey(@Param(value="id") Integer id);
	/**
	 * 添加
	 * @param map
	 * @return
	 */
	public void insert(JsActivityHitIceLog obj);
	/**
	 * 添加批量
	 * @param map
	 * @return
	 */
	public void insertBatch(List<JsActivityHitIceLog> list);
	/**
	 * 根据条件查询
	 * @param map
	 * @return
	 */
	public List<JsActivityHitIceLog> selectObjectByMap(Map<String,Object> map);
	/**
	 * 根据条件查询总数
	 * @param map
	 * @return
	 */
	public int selectObjectCountByMap(Map<String,Object> map);
	
	/**
	 * 砸冰块活动期间邀请好友数量
	 * @param map
	 * @return
	 */
	public int selectActivityTimeInviteFriendsCount(Map<String,Object> map);
	/**
	 * 砸冰块活动期间 新的投资
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectActivityTimeNewInvest(Map<String,Object> map);
	/**
	 * 活动期间参与人数
	 * @param map
	 * @return
	 */
	public int selectActivityTimePartakeCount(Map<String,Object> map);
	/**
	 * 活动期间用户获得金币总数 
	 * @param map
	 * @return
	 */
	public int selectActivityTimeGoldCount(Map<String,Object> map);
	/**
	 * 修改领取时间
	 * @param obj
	 */
	public void updateReceiveTime (@Param(value="id") Integer id);
	
	
	
	
	
}
