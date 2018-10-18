package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberCpsFavourableRule;

public interface DrMemberCpsFavourableRuleDAO {
	
	/**
	 * 查询回款促复投送红包发放规则
	 * @param param 查询参数
	 * @return
	 */
	public List<DrMemberCpsFavourableRule> selectByParam(Map<String, Object> param);
	
	/**
	 * 查询回款促复投送红包发放规则的条数
	 * @param param
	 * @return
	 */
	public Integer selectCountByParam(Map<String, Object> param);
	
	/**
	 *  插入发放规则
	 * @param cpsRule 红包发放规则
	 */
	public void insert(DrMemberCpsFavourableRule cpsRule);
	
	/**
	 * 修改发放规则
	 * @param cpsRule 最新红包发放规则
	 */
	public void updateBySelective(DrMemberCpsFavourableRule cpsRule);

}
