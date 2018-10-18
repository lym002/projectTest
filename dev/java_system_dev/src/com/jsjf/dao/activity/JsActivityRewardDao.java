package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsActivityReward;

public interface JsActivityRewardDao {
	List<JsActivityReward> queryJsActivityRewardList(Map<String, Object> param);
	
	Integer queryJsActivityRewardListCount(Map<String, Object> param);

    JsActivityReward selectByPrimaryKey(Integer id);

	List<JsActivityReward> queryCouponIdList();

	void addActivityReward(JsActivityReward jsActivityReward);

	void updateActivityReward(JsActivityReward jsActivityReward);

	void deleteActivityReward(Integer id);

}