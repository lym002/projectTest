package com.jsjf.dao.vip;

import com.jsjf.model.vip.MemberGrowthDetail;

public interface MemberGrowthDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberGrowthDetail record);

    int insertSelective(MemberGrowthDetail record);

    MemberGrowthDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberGrowthDetail record);

    int updateByPrimaryKey(MemberGrowthDetail record);
}