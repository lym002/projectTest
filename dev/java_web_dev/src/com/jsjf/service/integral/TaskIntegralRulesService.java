package com.jsjf.service.integral;

import com.jsjf.common.BaseResult;
import com.jsjf.model.integral.TaskIntegralRules;
import com.jsjf.model.product.DrProductInfo;

import java.math.BigDecimal;

public interface TaskIntegralRulesService {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskIntegralRules record);

    int insertSelective(TaskIntegralRules record);

    TaskIntegralRules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskIntegralRules record);

    int updateByPrimaryKey(TaskIntegralRules record);

    BaseResult selectMyTask(Integer uid);

    void addPoints(Integer uid,String source, Integer amount);

    void addPointsByInvest(Integer uid, Integer amountSum, BigDecimal invest, DrProductInfo info);
}