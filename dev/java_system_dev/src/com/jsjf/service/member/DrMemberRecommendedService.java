/**
 * 
 */
package com.jsjf.service.member;


import java.util.List;
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
	 * @param drMemberRecommended
	 */
	public void insertDrMemberRecommended(DrMemberRecommended drMemberRecommended);
	
	/**
	 * 获取好友列表
	 * @param map
	 * @param pi
	 * @return
	 */
	public PageInfo getDrMemberRecommmended(DrMemberRecommended drMemberRecommended,PageInfo pi);
	
	/**
	 * 好友数据汇总
	 * @param map
	 * @return
	 */
	public List<DrMemberRecommended> getRecommendInfo(Map<String,Object> map);
	
}
