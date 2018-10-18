package com.jsjf.dao.vip;

import com.jsjf.model.vip.VipAcivityParameter;

public interface VipAcivityParameterDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VipAcivityParameter record);

    int insertSelective(VipAcivityParameter record);

    VipAcivityParameter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipAcivityParameter record);

    int updateByPrimaryKey(VipAcivityParameter record);
}