package com.jsjf.service.product;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.product.JsProductReservation;
import com.jsjf.model.product.JsProductReservationLog;
import com.jsjf.model.system.SysUsersVo;

public interface JsProductReservationService {
	/**
	 * 查询规则集合
	 * @param jsProductReservation
	 * @param pi
	 * @return
	 */
	public BaseResult getJsProductReservationList(JsProductReservation jsProductReservation, PageInfo pi);
	
	/**
	 * 插入规则
	 * @param jsProductReservation
	 * @param usersVo
	 * @return
	 */
	public BaseResult insertJsProductReservation(JsProductReservation jsProductReservation,SysUsersVo usersVo);
	
	/**
	 * 根据id获取规则
	 * @param id
	 * @return
	 */
	public JsProductReservation getJsProductReservationById(Integer id);
	
	/**
	 * 规则修改
	 * @param jsProductReservation
	 * @param usersVo
	 */
	public void updateJsProductReservation(JsProductReservation jsProductReservation,SysUsersVo usersVo);
	
	/**
	 * 根据规则获取预约记录
	 * @param jprLog
	 * @param pi
	 * @return
	 */
	public PageInfo getJsPReservationLogByPrid(Map<String,Object> map,PageInfo pi);


}
