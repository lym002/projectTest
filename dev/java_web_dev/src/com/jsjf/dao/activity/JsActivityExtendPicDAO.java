package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsActivityExtendPic;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.model.activity.JsOpenDayLog;

public interface JsActivityExtendPicDAO {
	
	/**
	 * 根据map查询
	 * @param id
	 */
	public List<JsActivityExtendPic> selectJsActivityExtendPicListByMap(Map<String,Object> map);
}
