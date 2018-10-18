package com.jsjf.dao.integral;

import com.jsjf.model.integral.UserDetailIntegralBean;

import java.util.List;
import java.util.Map;

public interface UserDetailIntegralDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDetailIntegralBean record);

    int insertSelective(UserDetailIntegralBean record);

    int updateByPrimaryKeySelective(UserDetailIntegralBean record);

    int updateByPrimaryKey(UserDetailIntegralBean record);

	List<UserDetailIntegralBean> queryDetailintegralList(Map<String, Object> map);

	int queryDetailintegralListCount(Map<String, Object> map);

    List<UserDetailIntegralBean> queryEarnPoint(Map<String, Object> map);

    List<UserDetailIntegralBean> queryConsumptionPoint(Map<String, Object> map);

    UserDetailIntegralBean queryExpirationDate(String expirationDate);

    Integer queryTodayInvestTask(Integer uid);
}