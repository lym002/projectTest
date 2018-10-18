package com.jsjf.dao.activity;

import java.util.List;

import com.jsjf.model.activity.BypActivityIntegral;

public interface BypActivityIntegralDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(BypActivityIntegral record);

    int insertSelective(BypActivityIntegral record);

    BypActivityIntegral selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BypActivityIntegral record);

    int updateByPrimaryKey(BypActivityIntegral record);

	List<BypActivityIntegral> selectBypActivityIntegral();
}