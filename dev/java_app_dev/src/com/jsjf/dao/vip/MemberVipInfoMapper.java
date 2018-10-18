package com.jsjf.dao.vip;

import com.jsjf.model.vip.MemberVipInfo;

public interface MemberVipInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberVipInfo record);

    int insertSelective(MemberVipInfo record);

    MemberVipInfo selectByPrimaryKey(Integer id);

    MemberVipInfo selectByUid(Integer uid);

    int updateByPrimaryKeySelective(MemberVipInfo record);

    int updateByPrimaryKey(MemberVipInfo record);
}