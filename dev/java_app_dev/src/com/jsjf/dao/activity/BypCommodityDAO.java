package com.jsjf.dao.activity;

import com.jsjf.model.activity.BypCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BypCommodityDAO {
    int deleteByPrimaryKey(Integer prid);

    int insert(BypCommodity record);

    int insertSelective(BypCommodity record);

    BypCommodity selectByPrimaryKey(Integer prid);

    int updateByPrimaryKeySelective(BypCommodity record);

    int updateByPrimaryKey(BypCommodity record);
    /**
     * 查询兑换奖品所需积分
     * @param pid
     * @return
     */
	public BypCommodity selectIntegralByPid(String activity_id);

    List<BypCommodity> selectCommodityList(Map<String, Object> map);

    BypCommodity selectCommodityByPid(@Param("pid") Integer pid);
}