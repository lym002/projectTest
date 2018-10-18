package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.product.JsProductPrizeLog;
import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsProductReservation;
import com.jsjf.model.activity.JsProductReservationLog;

public interface JsProductReservationLogDAO {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public JsProductReservationLog selectByPrimaryKey(@Param(value="id") Integer id);
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<JsProductReservation> selectJsProductReservationLogByMap(Map<String,Object> map);
	
	/**
	 * 添加
	 * @param jprl
	 */
	public void insert (JsProductReservationLog jprl);


    public List<JsProductPrizeLog> selectTopTen(Map<String, Object> map);

	List<JsProductPrizeLog> selectMyZeroBuy(Map<String, Object> map);
}
