package com.jsjf.service.integral;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.integral.TaskRulesBean;

public interface TaskRulesService {


	PageInfo queryTaskRulesList(PageInfo info, TaskRulesBean taskRulesBean);

	BaseResult updateTaskRules(TaskRulesBean taskRulesBean);

	BaseResult addTaskRules(TaskRulesBean taskRulesBean);

	BaseResult deleteTask(String id);


}
