package com.jsjf.dao.product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.product.JsProductReservation;

public interface JsProductReservationDAO {
	/**
	 * 新建预约规则
	 * @param jsProductReservation
	 * @throws SQLException
	 */
	public void insertProductReservation(JsProductReservation jsProductReservation) throws SQLException;
	
	/**
	 * 修改预约规则
	 * @param jsProductReservation
	 * @throws SQLException
	 */
	public void updateProductReservation(JsProductReservation jsProductReservation) throws SQLException;
	
	/**
	 * 获取规则
	 * @param map
	 * @return
	 */
	public List<JsProductReservation> selectJsProductReservationList(Map<String, Object> map);
	
	/**
	 * 获取规则条数
	 * @param map
	 * @return
	 */
	public int selectJsProductReservationCount(Map<String,Object> map);
	
	/**
	 * 根据id获取规则
	 * @param id
	 * @return
	 */
	public JsProductReservation getJsProductReservationById(Integer id);
	
	public List<JsProductReservation> selectJsProductReservationByMap(Map<String,Object> map);
	
	/**
	 * 修改
	 */
	public void update (JsProductReservation jsProductReservation);
	
}
