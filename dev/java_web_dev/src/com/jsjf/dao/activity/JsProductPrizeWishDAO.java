package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsProductPrizeOrderShare;
import com.jsjf.model.activity.JsProductPrizeWish;

public interface JsProductPrizeWishDAO {
	
	/**
	 * 根据map查询list
	 * @param param
	 * @return
	 */
	public List<JsProductPrizeWish> selectByMap(Map<String,Object> map);
	
	/**
	 * 插入
	 * @param jsProductPrizeWish
	 */
	public void insert(JsProductPrizeWish jsProductPrizeWish);
}
