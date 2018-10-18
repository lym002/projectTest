package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsProductReservation;

public interface JsProductReservationDAO {
	/**
	 * 
	 * @param id
	 * @return
	 */
	public JsProductReservation selectByPrimaryKey(@Param(value="id") Integer id);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<JsProductReservation> selectJsProductReservationByMap(Map<String,Object> map);
	/**
	 * 修改
	 */
	public void update (JsProductReservation jsProductReservation);
	
}
