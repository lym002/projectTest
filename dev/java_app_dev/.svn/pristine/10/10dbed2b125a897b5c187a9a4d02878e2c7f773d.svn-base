package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsProductPrizeOrderShare;

public interface JsProductPrizeOrderShareDAO {
	
	/**
	 * 根据map查询list
	 * @param param
	 * @return
	 */
	public List<JsProductPrizeOrderShare> selectByMap(Map<String,Object> map);
	
	/**
	  * 添加
	  * @param obj
	  */
	public void insert (JsProductPrizeOrderShare obj);
	/**
	 * 根据uid查询晒单次数
	 * @param uid
	 * @return
	 */
	public Integer selectOrderShareCountByUid(Integer uid);
	/**
	 * 晒单列表count
	 * @param map
	 * @return
	 */
	public Integer selectOrderShareCount(Map<String,Object> map);
}
