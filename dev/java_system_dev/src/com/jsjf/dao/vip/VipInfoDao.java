package com.jsjf.dao.vip;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.BypCommodityDetailBean;
import com.jsjf.model.vip.VipInfo;

public interface VipInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VipInfo record);

    int addVipinfo(VipInfo record);

    VipInfo selectByPrimaryKey(Integer id);

    int updateVipInfo(VipInfo record);

    int updateByPrimaryKey(VipInfo record);

	List<BypCommodityDetailBean> queryVipInfoList(Map<String, Object> param);

	Integer queryVipInfoListCount(Map<String, Object> param);


}