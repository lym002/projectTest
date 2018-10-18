package com.jsjf.service.product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;

public interface JsProductPrizeLogService {
	
	/**
	 * 获取列表
	 * @return
	 */
	public BaseResult getJsProductPrizeLogList(Map<String,Object> map,PageInfo pi);
	
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
	 * 获取订单列表
	 * @return
	 */
	public BaseResult getJsproductPrizeLogOrderList(Map<String,Object> map,PageInfo pi);
	/**
	 * 导出订单
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

}
