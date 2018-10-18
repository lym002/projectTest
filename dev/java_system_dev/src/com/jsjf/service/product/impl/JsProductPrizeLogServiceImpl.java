package com.jsjf.service.product.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.product.JsProductPrizeLogDAO;
import com.jsjf.model.product.JsProductPrizeLog;
import com.jsjf.service.product.JsProductPrizeLogService;

@Service
public class JsProductPrizeLogServiceImpl implements JsProductPrizeLogService {

	@Autowired
	private JsProductPrizeLogDAO jsProductPrizeLogDAO;
	
	@Override
	public BaseResult getJsProductPrizeLogList(Map<String, Object> map,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<JsProductPrizeLog> list = jsProductPrizeLogDAO.getJsProductPrizeLogList(map);
		Integer total = jsProductPrizeLogDAO.getJsProductPrizeLogListCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<Map<String, Object>> getJsProductPrizeLogListForExl(
			Map<String, Object> param) {
		return jsProductPrizeLogDAO.getJsProductPrizeLogListForExl(param);
	}

	@Override
	public int getJsProductPrizeLogCountByPPid(Map<String, Object> param) {
		return jsProductPrizeLogDAO.getJsProductPrizeLogCountByPPid(param);
	}

	@Override
	public BaseResult getJsproductPrizeLogOrderList(Map<String, Object> map, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("type", 0);//定单类型,0投资定单,1预约定单  
		List<JsProductPrizeLog> list = jsProductPrizeLogDAO.getJsproductPrizeLogOrderList(map);
		Integer total = jsProductPrizeLogDAO.getJsproductPrizeLogOrderListCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<Map<String, Object>> getJsproductPrizeLogOrderListForExl(Map<String, Object> param) {
		return jsProductPrizeLogDAO.getJsproductPrizeLogOrderListForExl(param);
	}

	@Override
	public BigDecimal getInvestAmountSum(Map<String, Object> param) {
		return jsProductPrizeLogDAO.getInvestAmountSum(param);
	}

}
