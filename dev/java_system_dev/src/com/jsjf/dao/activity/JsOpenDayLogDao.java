package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsOpenDayLog;

public interface JsOpenDayLogDao {

	/**
	 * 根据开放日id查看预约列表
	 * @param param
	 * @return
	 */
	public List<JsOpenDayLog> getOpenDayLogList(Map<String,Object> param);
	
	public int getOpenDayLogCount(Map<String,Object> param);
	
}
