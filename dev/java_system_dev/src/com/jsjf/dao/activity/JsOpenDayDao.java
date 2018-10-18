package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsOpenDay;

public interface JsOpenDayDao {

	/**
	 * 根据name，活动时间查询
	 * @param param
	 * @return
	 */
	public List<JsOpenDay> getOpenDayList(Map<String,Object> param);
	
	public int getOpenDayCount(Map<String,Object> param);
	
	public JsOpenDay selectByPrimaryKey(Integer id);
	
	public void insert(JsOpenDay jsOpenDay);
	
	public void update(JsOpenDay jsOpenDay);
	
	/**
	 * 根据状态来查询个数
	 * @param status
	 * @return
	 */
	public List<JsOpenDay> getOpenDayByStatus(Integer status);
}
