package com.jsjf.dao.integral;

import java.util.List;
import java.util.Map;

import com.jsjf.model.integral.UserDetailIntegralBean;

public interface UserDetailIntegralDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDetailIntegralBean record);

    int insertSelective(UserDetailIntegralBean record);

    int updateByPrimaryKeySelective(UserDetailIntegralBean record);

    int updateByPrimaryKey(UserDetailIntegralBean record);

	List<UserDetailIntegralBean> queryDetailintegralList(Map<String, Object> map);

	int queryDetailintegralListCount(Map<String, Object> map);

	List<UserDetailIntegralBean> queryIntegralManageList(Map<String, Object> map);

	int queryIntegralManageListCount(Map<String, Object> map);

    Integer queryTodayInvestTask(Integer uid);

    List<Map<String,Object>> queryExpirationIntegral(Map<String, Object> param);
}