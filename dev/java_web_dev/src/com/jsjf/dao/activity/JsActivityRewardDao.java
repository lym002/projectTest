package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsActivityReward;

public interface JsActivityRewardDao {
	
	/**
	 * 根据活动id 和类型来查询奖品列表
	 * @param map
	 * @return
	 */
	public List<JsActivityReward> getJsActivityRewardByAid(Map<String,Object> map);

	/**
	 * @param parseInt
	 * @return
	 */
	public JsActivityReward getProNameById(Map<String,Object> parseInt);
}
