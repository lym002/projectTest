package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.JsActivityReward;

public interface JsActivityRewardService {

	/**
	 * 查询双旦管理的奖品表
	 * @param param
	 * @param pi
	 * @return
	 */
	public BaseResult queryJsActivityRewardList(Map<String, Object> param, PageInfo pi);

	/**
	 * 优惠ID下拉款数据查询
	 * @return
	 */
	public List<JsActivityReward>  queryCouponIdList();

	/**
	 * 添加奖品管理数据
	 * @param jsActivityReward
	 * @return
	 */
	public BaseResult addActivityReward(JsActivityReward jsActivityReward);

	/**
	 * 修改奖品管理数据
	 * @param jsActivityReward
	 * @return
	 */
	public BaseResult updateActivityReward(JsActivityReward jsActivityReward);

	/**
	 * 删除奖品管理数据
	 * @param id
	 * @return
	 */
	public BaseResult deleteActivityReward(Integer id);

}
