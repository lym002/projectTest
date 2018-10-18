package com.jsjf.dao.integral;

import java.util.List;
import java.util.Map;

import com.jsjf.model.integral.InvestRulesBean;

public interface InvestRulesDao {
    int deleteByPrimaryKey(Integer id);

    int insert(InvestRulesBean record);

    int insertSelective(InvestRulesBean record);

    InvestRulesBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvestRulesBean record);

    int updateByPrimaryKey(InvestRulesBean record);

	List<InvestRulesBean> queryInvestrulesListList(Map<String, Object> map);

	int queryInvestrulesListListCount(Map<String, Object> map);

	void addInvestRules(InvestRulesBean investRulesBean);

	void updateInvestRules(InvestRulesBean investRulesBean);
}