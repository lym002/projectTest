package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsActivityFriend;

public interface JsActivityFriendDAO {
	
	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public JsActivityFriend selectByPrimaryKey(@Param(value="id") Integer id);
	
	/**
	 * 获得返利活动
	 * @param map
	 * @return
	 */
	public List<JsActivityFriend> selectJsActivityFriend(Map<String,Object> map);
	
	
	/**
	 * 获得返利活动count
	 * @param map
	 * @return
	 */
	public int selectJsActivityFriendCount(Map<String,Object> map);
	
	
}
