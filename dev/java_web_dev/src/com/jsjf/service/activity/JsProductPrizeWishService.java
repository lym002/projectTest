package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.JsProductPrizeWish;

public interface JsProductPrizeWishService {

	/**
	 * 
	 * @param map
	 * @return
	 */
	public BaseResult insertPrizeWish(JsProductPrizeWish jsProductPrizeWish);

	BaseResult newInsertPrizeWish(JsProductPrizeWish prizeWish);

	BaseResult selectWish(Integer uid);
}
