package com.jsjf.dao.vip;

import com.jsjf.model.vip.VipInfo;

import java.util.List;
import java.util.Map;

public interface VipInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipInfo record);

    int insertSelective(VipInfo record);

    VipInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipInfo record);

    int updateByPrimaryKey(VipInfo record);

    List<VipInfo> queryNextVipLevel(Map<String, Object> param);

    VipInfo selectByVipLevel(Integer vipLevel);
}