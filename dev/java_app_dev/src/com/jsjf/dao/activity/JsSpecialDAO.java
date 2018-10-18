package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.model.activity.JsOpenDayLog;
import com.jsjf.model.activity.JsSpecial;
import com.jsjf.model.activity.JsSpecialPic;

public interface JsSpecialDAO {
	
	/**
	 * 查询专题页内容
	 * @param id
	 */
	public JsSpecial selectJsSpecial();
	/**
	 * 查询专题页图片
	 */
	public List<JsSpecialPic> selectJsSpecialPic();
}
