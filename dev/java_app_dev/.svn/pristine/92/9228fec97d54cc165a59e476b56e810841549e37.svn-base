package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;

public interface DrActivityDAO {
	
	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public DrActivity selectByPrimaryKey(@Param(value="id") Integer id);
	
	/**
	 * 根据时间来判断活动是否开启
	 */
	public List<DrActivity> selectByTime(Map<String,Object> param);
	/**
	 * 条件查询list
	 * @param map
	 * @return
	 */
	public List<DrActivity> selectDrActivityList(Map<String,Object> map);
	
	/**
	 * 通过活动名字查询
	 * @param id
	 * @return
	 */
	public DrActivity selectObjectByName(@Param(value="name")String name);
}
