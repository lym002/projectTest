package com.jsjf.service.vip.impl;

import com.jsjf.dao.vip.MemberGrowthValueDetailMapper;
import com.jsjf.model.vip.MemberGrowthValueDetail;
import com.jsjf.service.vip.MemberGrowthValueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MemberGrowthValueDetailServiceImpl implements MemberGrowthValueDetailService {

    @Autowired
    private MemberGrowthValueDetailMapper memberGrowthValueDetailMapper;

    @Override
    public int deleteByPrimaryKey(Integer uid) {
        return memberGrowthValueDetailMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insert(MemberGrowthValueDetail record) {
        return memberGrowthValueDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(MemberGrowthValueDetail record) {
        return memberGrowthValueDetailMapper.insertSelective(record);
    }

    @Override
    public MemberGrowthValueDetail selectByPrimaryKey(Integer uid) {
        return memberGrowthValueDetailMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberGrowthValueDetail record) {
        return memberGrowthValueDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MemberGrowthValueDetail record) {
        return memberGrowthValueDetailMapper.updateByPrimaryKey(record);
    }

}
