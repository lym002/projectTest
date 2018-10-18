package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.ProductReward;
import com.jsjf.model.product.DrProductInfo;

public interface ProductRewardService {

	/**
	 * 查询产品关联奖励
	 * @return
	 */
	public List<Map<String, Object>> selProductReward(Map<String, Object> map);
	
	/**
	 * 查询产品关联奖励总条数
	 * @return
	 */
	public int selProductRewardCount(Map<String, Object> map);
	
	/**
	 * 查询符合活动的产品
	 * @return
	 */
	public List<DrProductInfo> selProductList();
	
	/**
	 * 查询红包加息券
	 * @return
	 */
	public List<Map<String, Object>> selParameterList(Map<String, Object> map);
	
	/**
	 * 生成奖励关联
	 * @param productReward
	 */
	public void insert(ProductReward productReward);
	
	/**
	 * 删除关联
	 * @param map
	 */
	public void delete(Map<String, Object> map);
}




