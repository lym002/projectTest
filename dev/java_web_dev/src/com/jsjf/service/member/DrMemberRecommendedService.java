/**
 * 
 */
package com.jsjf.service.member;

import java.math.BigDecimal;
import java.util.Map;

import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberRecommended;

/**
 * @author Gerald
 *
 */
public interface DrMemberRecommendedService {
	/**
	 * 插入用户推荐关系
	 * @param drMemberRecommened
	 */
	public void insertDrMemberRecommended(DrMemberRecommended drMemberRecommened);
	
	/**
	 * 获取好友列表
	 * @param map
	 * @return
	 */
	public PageInfo getDrMemberRecommended(Map<String, Object> map);
	
	/**
	 * 根据推荐人ID获取推荐人获取的奖励总和
	 * @param referrerId
	 * @return
	 */
	public BigDecimal getRewardByReferrerId(Integer referrerId);
	

}
