package com.jsjf.service.product;

import java.util.List;
import java.util.Map;

import com.jsjf.model.product.JsProductPrizeLog;


import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.product.JsProductPrizeLog;

public interface JsProductPrizeLogService {
	/**
	 * 查询投资记录
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> selectListMap(Map<String,Object> map);
	/**
	 * 查询投资记录
	 * @param map
	 * @return
	 */
	public int selectListMapCount(Map<String,Object> map);	
	/**
	 * 投即送，查询账户中心投资记录
	 * @param map
	 * @return
	 */
	public BaseResult selectPrizeLogByUid(Map<String, Object> map);
	/**
	 * 插入预约记录
	 * @param jsProductPrizeLog
	 */
	public void insert (JsProductPrizeLog jsProductPrizeLog);

	List<JsProductPrizeLog> selectLogByUid(Integer uid);

	List<JsProductPrizeLog> selectLogByPid(Integer pid);
}