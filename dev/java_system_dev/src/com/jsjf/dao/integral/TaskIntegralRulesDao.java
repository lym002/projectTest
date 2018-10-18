package com.jsjf.dao.integral;

import com.jsjf.model.integral.TaskIntegralRules;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaskIntegralRulesDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskIntegralRules record);

    int insertSelective(TaskIntegralRules record);

    TaskIntegralRules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskIntegralRules record);

    int updateByPrimaryKey(TaskIntegralRules record);

    List<TaskIntegralRules> selectByType(@Param("type") int type);

    TaskIntegralRules selectByTaskName(@Param("taskType") String source);

    TaskIntegralRules queryNextTaskByTaskMoney(Map<String, Object> param);

    List<TaskIntegralRules> queryTaskList(Map<String, Object> param);
}