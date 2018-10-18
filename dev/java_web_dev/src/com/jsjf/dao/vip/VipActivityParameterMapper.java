package com.jsjf.dao.vip;

import com.jsjf.model.vip.VipActivityParameter;

import java.util.List;

public interface VipActivityParameterMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(VipActivityParameter record);

    int insertSelective(VipActivityParameter record);

    VipActivityParameter selectByPrimaryKey(Integer id);

    List<VipActivityParameter> selectByVipLevel(Integer vipLevel);

    int updateByPrimaryKeySelective(VipActivityParameter record);

    int updateByPrimaryKey(VipActivityParameter record);

}