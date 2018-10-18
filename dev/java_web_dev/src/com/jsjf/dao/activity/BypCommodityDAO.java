package com.jsjf.dao.activity;

import com.jsjf.model.activity.BypCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BypCommodityDAO {

    BypCommodity selectByPrimaryKey(Integer prid);

	 BypCommodity selectIntegralByPid(String pid);

    List<BypCommodity> selectCommodityList(Map<String, Object> map);

    BypCommodity selectCommodityByPid(@Param("pid") int pid);

    int updateByPrimaryKeySelective(BypCommodity bcd);
}