package com.jsjf.dao.vip;

import com.jsjf.model.vip.VipEquitiesMember;

public interface VipEquitiesMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipEquitiesMember record);

    int insertSelective(VipEquitiesMember record);

    VipEquitiesMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipEquitiesMember record);

    int updateByPrimaryKey(VipEquitiesMember record);
}