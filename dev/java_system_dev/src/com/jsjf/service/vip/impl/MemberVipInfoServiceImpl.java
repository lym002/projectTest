package com.jsjf.service.vip.impl;

import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.vip.MemberVipInfoMapper;
import com.jsjf.model.vip.MemberVipInfo;
import com.jsjf.service.vip.MemberVipInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberVipInfoServiceImpl implements MemberVipInfoService {

    @Autowired
    private MemberVipInfoMapper memberVipInfoMapper;

    private static transient Logger log = Logger.getLogger(MemberVipInfoServiceImpl.class);

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return memberVipInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MemberVipInfo record) {
        return memberVipInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberVipInfo record) {
        return memberVipInfoMapper.insertSelective(record);
    }

    @Override
    public MemberVipInfo selectByPrimaryKey(Integer id) {
        return memberVipInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberVipInfo record) {
        return memberVipInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberVipInfo record) {
        return memberVipInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MemberVipInfo> selectTodayBirthdayMemberCode(){
        return memberVipInfoMapper.selectTodayBirthdayMemberCode();
    }

}
