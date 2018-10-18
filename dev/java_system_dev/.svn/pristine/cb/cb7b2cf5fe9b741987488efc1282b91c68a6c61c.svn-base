package com.jsjf.dao.product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.product.JsProductPrize;

public interface JsProductPrizeDAO {
	
	/**
 	 * 添加礼品
 	 * @param  JsProductPrize
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void insertJsProductPrize(JsProductPrize jsProductPrize) throws SQLException; 
    
	/**
	 * 修改礼品
	 * @param  JsProductPrize
	 * @return void
	 * @throws SQLException;
	 */
	public void updateJsProductPrize(JsProductPrize jsProductPrize) throws SQLException; 
	
	/**
	 * 获取礼品列表
	 * @return
	 */
	public List<JsProductPrize> getJsProductPrizeList(Map<String,Object> map);
	/**
	 * 获取礼品列表总数
	 * @return
	 */
	public int getJsProductPrizeListCount(Map<String,Object> map);
	
	/**
	 * 根据id获取jsproductPrize
	 * @param id
	 * @return
	 */
	public JsProductPrize getJsProductPrizeById(@Param("id") Integer id);
	/**
	 * 根据status查找奖品
	 * @param status
	 * @return
	 */
	public List<Map<String,Object>> getJsProductPrizeforProduct(@Param(value = "status") Integer status);
	
	/**
	 * 产品修改的查找奖品
	 * @param prizeId
	 * @return
	 */
	public List<Map<String,Object>> getJsProductPrizeforProductUpdate(Integer prizeId);
	/**
	 * 查询奖品
	 * @return
	 */
	public JsProductPrize selectJsPorudctPrize(Map<String,Object> map);
	
}
