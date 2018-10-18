package com.jsjf.service.activity;

import java.util.Map;

import com.jsjf.model.activity.JsProductReservation;
import com.jsjf.model.product.DrProductInfo;

public interface JsProductReservationService {
	
	/**
	 * 预约标
	 * @param drProductInfo
	 * @return
	 */
	public Map<String,Object> reservationProduct (DrProductInfo drProductInfo,Integer uid); 
	
	/**
	 * select
	 * @param drProductInfo
	 * @return
	 */
	public JsProductReservation selectReservationProduct (int id); 
}
