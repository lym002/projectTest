package com.jsjf.dao.integral;

import com.jsjf.model.integral.SignInRules;

import java.util.List;

public interface SignInRulesDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SignInRules record);

    int insertSelective(SignInRules record);

    SignInRules selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignInRules record);

    int updateByPrimaryKey(SignInRules record);

    List<SignInRules> selectSigninList();
}