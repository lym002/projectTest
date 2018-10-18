package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrRecommendedSettingsDetail;

/**
 * @author Gerald
 *
 */
public interface DrRecommendedSettingsDetailDAO {
	
	/**
	 * 添加详情 
	 * @param detail
	 */
	public void insertDetail(DrRecommendedSettingsDetail detail);
	
	/**
	 * 根据RID查找到好友推荐设置详情
	 * @param id
	 * @return 
	 */
	public List<DrRecommendedSettingsDetail> getDetailByRid(Map<String,Object> map);
	
	public int getDetailByRidTotal(Map<String,Object> map);
	
	/**
	 * 获取离传入金额最近的且金额小于等于传入金额的数据
	 * @param map 
	 * @return
	 */
	public DrRecommendedSettingsDetail getDetailByRidAndAmount(Map<String,Object> map);
}
