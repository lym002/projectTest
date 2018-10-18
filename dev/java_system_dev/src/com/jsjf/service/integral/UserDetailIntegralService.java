package com.jsjf.service.integral;

import java.math.BigDecimal;
import java.util.Map;

import com.jsjf.common.PageInfo;

public interface UserDetailIntegralService {

	/**
	 * 查询积分明细
	 * @param info
	 * @param userDetailIntegralBean
	 */
	PageInfo queryDetailintegralList(PageInfo info, Map<String, Object> param);

	/**
	 * 积分管理查询
	 * @param info
	 * @param param
	 */
	PageInfo queryIntegralManageList(PageInfo info, Map<String, Object> param);

	void eranPoints(Integer uid, BigDecimal points, Integer source, Integer taskId);

	Integer queryTodayInvestTask(Integer uid);
}
