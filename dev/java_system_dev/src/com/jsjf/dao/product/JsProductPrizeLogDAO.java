package com.jsjf.dao.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.product.JsProductPrizeLog;

public interface JsProductPrizeLogDAO {
	
	/**
	 * 添加
	 * @param jsProductPrizeLog
	 */
	public void insertJsProductPrizeLog(JsProductPrizeLog jsProductPrizeLog);
	
	/**
	 * 查询
	 * @param param
	 * @return
	 */
	public List<JsProductPrizeLog> getJsProductPrizeLogList(Map<String,Object> param);
	
	/**
	 * 获取列表总数
	 * @return
	 */
	public int getJsProductPrizeLogListCount(Map<String,Object> map);
	
	/**
	 * 导出
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getJsProductPrizeLogListForExl(Map<String,Object> param);
	
	/**
	 * 根据条件查询预约总数
	 * @param param
	 * @return
	 */
	public int getJsProductPrizeLogCountByPPid(Map<String,Object> param);
	
	/**
	 * 订单列表
	 * @param param
	 * @return
	 */
	public List<JsProductPrizeLog> getJsproductPrizeLogOrderList(Map<String,Object> param);
	/**
	 * 订单总数
	 * @param param
	 * @return
	 */
	public int getJsproductPrizeLogOrderListCount(Map<String,Object> param);
	/**
	 * 订单列表导出
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getJsproductPrizeLogOrderListForExl(Map<String,Object> param);
	
	/**
	 * 统计订单总金额
	 * @param param
	 * @return
	 */
	public BigDecimal getInvestAmountSum(Map<String,Object> param);
	/**
	 * 插入奖品记录
	 * @param jsProductPrizeLog
	 */
	public void insert (JsProductPrizeLog jsProductPrizeLog);
}
