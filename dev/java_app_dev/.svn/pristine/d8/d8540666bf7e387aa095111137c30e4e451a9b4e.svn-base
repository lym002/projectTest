package com.jsjf.service.vip.impl;

import com.jsjf.dao.vip.VipEquitiesMemberMapper;
import com.jsjf.model.vip.VipEquitiesMember;
import com.jsjf.service.vip.VipEquitiesMemberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VipEquitiesMemberServiceImpl implements VipEquitiesMemberService{

    private static transient Logger log = Logger.getLogger(VipEquitiesMemberServiceImpl.class);
    @Autowired
    private VipEquitiesMemberMapper vipEquitiesMemberMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return vipEquitiesMemberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(VipEquitiesMember record) {
        return vipEquitiesMemberMapper.insert(record);
    }

    @Override
    public int insertSelective(VipEquitiesMember record) {
        return vipEquitiesMemberMapper.insertSelective(record);
    }

    @Override
    public VipEquitiesMember selectByPrimaryKey(Integer id) {
        return vipEquitiesMemberMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(VipEquitiesMember record) {
        return vipEquitiesMemberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(VipEquitiesMember record) {
        return vipEquitiesMemberMapper.updateByPrimaryKey(record);
    }
}