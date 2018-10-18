package com.jsjf.service.member.impl;

import com.jsjf.dao.member.BypMemberInviteDao;
import com.jsjf.model.member.BypMemberInvite;
import com.jsjf.service.member.BypMemberInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BypMemberInviteServiceImpl implements BypMemberInviteService{

    @Autowired
    private BypMemberInviteDao bypMemberInviteDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return bypMemberInviteDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BypMemberInvite record) {
        return bypMemberInviteDao.insert(record);
    }

    @Override
    public int insertSelective(BypMemberInvite record) {
        return bypMemberInviteDao.insertSelective(record);
    }

    @Override
    public BypMemberInvite selectByPrimaryKey(Integer id) {
        return bypMemberInviteDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BypMemberInvite record) {
        return bypMemberInviteDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BypMemberInvite record) {
        return bypMemberInviteDao.updateByPrimaryKey(record);
    }
}