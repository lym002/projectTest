/**
 * 
 */
package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberRecommended;

/**
 * @author Gerald
 *
 */
public interface DrMemberRecommendedDAO {
	/**
	 * 插入推荐关系表
	 * @param drMemberRecommended
	 */
	public void insertMemberRecommended(DrMemberRecommended drMemberRecommended);
	/**
	 * 根据参数获取推荐的好友列表
	 * @param map
	 * @return
	 */
	public List<DrMemberRecommended> getDrMemberRecommended(Map<String, Object> map);
	/**
	 * 根据参数计算推荐好友数量
	 * @param map
	 * @return
	 */
	public Integer getDrMemberRecommendedCount(Map<String, Object> map);
	
	/**
	 * 通过uid批量修改用户首投信息
	 * @param list
	 */
	public void batchUpdate(List<DrMemberRecommended> list);
	
	/**
	 * 获取好友汇总信息
	 * @param map
	 * @return
	 */
	public List<DrMemberRecommended> getDrMemberRecommendedStat(Map<String,Object> map);
	
	/**
	 *根据uid 获取
	 * @param map
	 * @return
	 */
	public DrMemberRecommended selectByPrimaryKey(Integer uid);

}
