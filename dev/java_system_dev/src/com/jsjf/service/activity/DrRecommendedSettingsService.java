/**
 * 
 */
package com.jsjf.service.activity;

import java.math.BigDecimal;

import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.DrRecommendedSettings;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.system.SysUsersVo;

/**
 * @author Gerald
 *
 */
public interface DrRecommendedSettingsService {
	
	/**
	 * 插入新的好友推荐设置
	 * @param settings
	 */
	public void insertDrRecommendedSetting(DrRecommendedSettings settings,SysUsersVo vo);
	/**
	 * 获取好友推荐设置列表
	 * @param info
	 * @param recommendedSettings
	 * @return
	 */
	public PageInfo getDrRecommendedSettingsList(PageInfo info,DrRecommendedSettings recommendedSettings);
	
	/**
	 * 根据好友推荐设置id获取设置详情
	 * @param rid
	 * @return
	 */
	public PageInfo getSettingDetailsList(PageInfo info,Integer rid);
	
	/**
	 * 根据ID获取好友推荐设置
	 * @param id
	 * @return
	 */
	public DrRecommendedSettings getRecommendedSettingsById(Integer id);
	
	/**
	 * 更改好友推荐设置状态
	 * @param id
	 */
	public void updateStatus(Integer id);
	
	/**
	 * 计算推荐人返利金额
	 * @param invest 投资记录
	 * @return 推荐人应该获取的返利金额
	 */
	public BigDecimal FriendRecommendedRebate(DrProductInvest invest);
	
}
