package com.jsjf.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsActivityFriendDetail;

public interface JsActivityFriendDetailDAO {
	
	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public JsActivityFriendDetail selectByPrimaryKey(@Param(value="id") Integer id);
	
	/**
	 * 获得返利活动
	 * @param map
	 * @return
	 */
	public List<JsActivityFriendDetail> selectJsActivityFriendDetail(Integer fid);
	
	
}
