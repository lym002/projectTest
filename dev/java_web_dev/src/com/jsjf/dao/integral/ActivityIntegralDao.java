package com.jsjf.dao.integral;

import com.jsjf.model.integral.ActivityIntegralBean;

public interface ActivityIntegralDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityIntegralBean record);

    int insertSelective(ActivityIntegralBean record);

    ActivityIntegralBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityIntegralBean record);

    int updateByPrimaryKey(ActivityIntegralBean record);
}