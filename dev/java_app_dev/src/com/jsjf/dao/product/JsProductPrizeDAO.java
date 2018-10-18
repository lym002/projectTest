package com.jsjf.dao.product;

import java.util.List;
import java.util.Map;

import com.jsjf.model.product.JsProductPrize;

public interface JsProductPrizeDAO {
	/**
	 * 查询活动页的
	 * @return
	 */
	public List<JsProductPrize> selectActivityIndexJsproductPrize();
	
	
	/**
	 * 查询奖品
	 * @return
	 */
	public JsProductPrize selectJsPorudctPrize(Map<String,Object> map);
	
	JsProductPrize selectByPrimaryKey(Map<String,Object> pid);
	
}