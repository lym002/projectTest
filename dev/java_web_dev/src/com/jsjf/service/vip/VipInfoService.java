package com.jsjf.service.vip;

import com.jsjf.model.vip.VipInfo;

public interface VipInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(VipInfo record);

    int insertSelective(VipInfo record);

    VipInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipInfo record);

    int updateByPrimaryKey(VipInfo record);
}