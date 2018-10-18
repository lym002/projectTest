package com.jsjf.dao.vip;

import java.util.List;
import java.util.Map;

import com.jsjf.model.vip.VipMemberInfo;

public interface VipMemberInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VipMemberInfo record);

    int insertSelective(VipMemberInfo record);

    VipMemberInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipMemberInfo record);

    int updateByPrimaryKey(VipMemberInfo record);

	List<VipMemberInfo> queryVipMemberInfoList(Map<String, Object> param);

	Integer queryVipMemberInfoListCount(Map<String, Object> param);
}