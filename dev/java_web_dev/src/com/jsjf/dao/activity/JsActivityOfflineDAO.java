package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsActivityOffline;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.model.activity.JsOpenDayLog;

public interface JsActivityOfflineDAO {
	
	/**
	 * 根据map查询list
	 * @param map
	 */
	public List<JsActivityOffline> selectJsActivityOfflineListByMap(Map<String,Object> map);
	/**
	 * 根据map查详情
	 * @param map
	 * @return
	 */
	public JsActivityOffline selectJsActivityOfflineDetailByMap(Map<String,Object> map);
	public Integer selectJsActivityOfflineListByMapCount(Map<String,Object> map);
}
