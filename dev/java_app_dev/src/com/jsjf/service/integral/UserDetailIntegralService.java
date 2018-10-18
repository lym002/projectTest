package com.jsjf.service.integral;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.integral.UserDetailIntegralBean;

import java.math.BigDecimal;
import java.util.HashMap;

public interface UserDetailIntegralService {

	/**
	 * 查询积分明细
	 * @param info
	 * @param userDetailIntegralBean
	 */
	PageInfo queryDetailintegralList(PageInfo info,
                                     UserDetailIntegralBean userDetailIntegralBean);

    BaseResult queryEarnPoint(int uid);

    BaseResult queryConsumptionPoint(Integer uid);

	UserDetailIntegralBean queryExpirationDate(String expirationDate);

	BaseResult queryTopTenCommodity();
    /**
     *
     * @param uid 用户id
     * @param points 获得的积分数
     * @param source 积分来源（1：投资，3：任务）
     * @param taskId 任务id
     */
	void eranPoints(Integer uid, BigDecimal points, Integer source, Integer taskId);

    Integer queryTodayInvestTask(Integer uid);

    /**
     * 积分签到
     * @return
     */
    BaseResult integralSignin(Integer uid) throws Exception;

    BaseResult integralJDCard(HashMap<String, Object> param) throws Exception;
}
