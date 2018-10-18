package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.model.activity.JsOpenDayLog;

public interface JsOpenDayDAO {
	
	/**
	 * 根据id查询
	 * @param id
	 */
	public JsOpenDay selectJsOpenDayById(Integer id);
	/**
	 * 根据map查询
	 * @param id
	 */
	public List<JsOpenDay> selectJsOpenDayByParam(Map<String,Object> map);
	public Integer selectJsOpenDayCountByParam(Map<String,Object> map);
}
