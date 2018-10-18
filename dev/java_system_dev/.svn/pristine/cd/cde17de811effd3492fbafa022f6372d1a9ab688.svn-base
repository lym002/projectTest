/**
 * 
 */
package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrRecommendedSettings;

/**
 * @author Gerald
 *
 */
public interface DrRecommendedSettingsDAO {
	
	/**
	 * 
	 * @param setting
	 */
	public Integer insertReCommendedSettings(DrRecommendedSettings setting);
	
	/**
	 * 获取好友推荐设置列表
	 * @param map
	 * @return
	 */
	public List<DrRecommendedSettings> getReCommendedSettingsList(Map<String,Object> map);
	
	/**
	 * 获取好友推荐设置总条数
	 * @param map
	 * @return
	 */
	public int getReCommendedSettingsCount(Map<String,Object> map);
	
	/**
	 * 根据参数ID获取详情
	 * @param id
	 * @return
	 */
	public DrRecommendedSettings getReCommendedSettingsById(Map<String,Object> map);
	
	/**
	 * 更新参数状态
	 * @param id
	 */
	public void updateStatus(Integer id);

}
