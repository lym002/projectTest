package com.jsjf.dao.product;

import java.util.List;
import java.util.Map;


public interface JsActivityProductDAO {
	
	/**
	 * 修改活动产品关联表   js_activity_product
	 * @param map
	 */
	public void updateJsActivityProduct(Map<String,Object> map);
	
	/**
	 * 查询活动期数
	 * @param map
	 * @return
	 */
	public int  selectActivityPeriods(Map<String,Object> map);
	
	/**
	 * 查询活动固定编码
	 */
	public String  selectActivityCodeFixation(int atid);
	
	/**
	 * 查询活动模板信息
	 */
	public Map<String,Object>  selectActivityTemplate(int atid);
	
	
	/**
	 * 查询开奖结果
	 */
	public List<Map<String,Object>> selectActivityProductByPid(Map<String,Object> map);
	
	public   Map<String,Object>  selectActivityProduct(int pid);
}
