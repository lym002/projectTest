package com.jsjf.dao.integral;

import java.util.List;
import java.util.Map;

import com.jsjf.model.integral.InvestRulesBean;
import com.jsjf.model.integral.TaskRulesBean;

public interface TaskRulesDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskRulesBean record);

    int insertSelective(TaskRulesBean record);

    TaskRulesBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskRulesBean record);

    int updateByPrimaryKey(TaskRulesBean record);

	void addTaskRules(TaskRulesBean taskRulesBean);

	void updateTaskRules(TaskRulesBean taskRulesBean);

	List<InvestRulesBean> queryTaskRulesList(Map<String, Object> map);

	int queryTaskRulesListCount(Map<String, Object> map);
}