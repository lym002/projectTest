package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberCpsFavourableRule;

public interface DrMemberCpsFavourableRuleDAO {
	
	/**
	 * 查询7天回款的红包
	 * @param uId
	 * @param shouldPrincipalCount
	 * @return
	 */
	public List<DrMemberCpsFavourableRule> selectActivityByCps(Map<String,Object> paramMap);
	

}
