package com.jsjf.service.vip;

import com.jsjf.common.BaseResult;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.vip.MemberVipInfo;

import java.math.BigDecimal;

public interface MemberVipInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberVipInfo record);

    int insertSelective(MemberVipInfo record);

    MemberVipInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberVipInfo record);

    int updateByPrimaryKey(MemberVipInfo record);

    void addMemberGrowthValue(Integer uid, DrProductInfo productInfo, BigDecimal amount);

    BaseResult memberVipInfo(Integer uid);
}