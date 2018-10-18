package com.jsjf.dao.activity;

import java.math.BigDecimal;
import java.util.Map;


public interface JsActivityMemberAccountDAO {
	
	/**
	 * 活动奖励sum
	 * @param uid
	 * @return
	 */
	public BigDecimal selectActivityRewardsSum(Map<String,Object> map);
	
	/**
	 * 修改
	 */
	public void update(Map<String,Object> map);
	
}
