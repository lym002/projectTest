package com.jsjf.service.vip;

import com.jsjf.model.vip.MemberGrowthValueDetail;

public interface MemberGrowthValueDetailService {
    int deleteByPrimaryKey(Integer uid);

    int insert(MemberGrowthValueDetail record);

    int insertSelective(MemberGrowthValueDetail record);

    MemberGrowthValueDetail selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(MemberGrowthValueDetail record);

    int updateByPrimaryKey(MemberGrowthValueDetail record);
}