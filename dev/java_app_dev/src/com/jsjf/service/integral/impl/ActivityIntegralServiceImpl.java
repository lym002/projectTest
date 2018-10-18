package com.jsjf.service.integral.impl;

import com.jsjf.dao.integral.ActivityIntegralDao;
import com.jsjf.model.integral.ActivityIntegralBean;
import com.jsjf.service.integral.ActivityIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityIntegralServiceImpl implements ActivityIntegralService {

    @Autowired
    private ActivityIntegralDao activityIntegral;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return activityIntegral.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ActivityIntegralBean record) {
        return activityIntegral.insert(record);
    }

    @Override
    public int insertSelective(ActivityIntegralBean record) {
        return activityIntegral.insertSelective(record);
    }

    @Override
    public ActivityIntegralBean selectByPrimaryKey(Integer id) {
        return activityIntegral.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ActivityIntegralBean record) {
        return activityIntegral.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ActivityIntegralBean record) {
        return activityIntegral.updateByPrimaryKey(record);
    }
}