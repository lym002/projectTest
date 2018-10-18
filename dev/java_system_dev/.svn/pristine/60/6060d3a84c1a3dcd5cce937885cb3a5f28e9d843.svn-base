package com.jsjf.dao.vip;

import java.util.List;
import java.util.Map;

import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipEquitiesMember;
import com.jsjf.model.vip.VipInfo;

public interface VipEquitiesMemberDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VipEquitiesMember record);

    int insertSelective(VipEquitiesMember record);

    VipEquitiesMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipEquitiesMember record);

    int updateByPrimaryKey(VipEquitiesMember record);

	List<VipEquitiesMember> queryVipEquitiesList(Map<String, Object> param);

	Integer queryVipEquitiesListCount(Map<String, Object> param);

	List<VipInfo> queryVipLevel();
}