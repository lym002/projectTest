package com.jsjf.service.integral.impl;

import com.jsjf.dao.integral.SignInRulesDao;
import com.jsjf.model.integral.SignInRules;
import com.jsjf.service.integral.SignInRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInRulesServiceImpl implements SignInRulesService{

    @Autowired
    private SignInRulesDao signInRulesDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return signInRulesDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SignInRules record) {
        return signInRulesDao.insert(record);
    }

    @Override
    public int insertSelective(SignInRules record) {
        return signInRulesDao.insertSelective(record);
    }

    @Override
    public SignInRules selectByPrimaryKey(Integer id) {
        return signInRulesDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SignInRules record) {
        return signInRulesDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SignInRules record) {
        return signInRulesDao.updateByPrimaryKey(record);
    }
}