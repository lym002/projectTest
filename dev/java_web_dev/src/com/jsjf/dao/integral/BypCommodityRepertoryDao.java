package com.jsjf.dao.integral;

import com.jsjf.model.integral.BypCommodityRepertory;

public interface BypCommodityRepertoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BypCommodityRepertory record);

    int insertSelective(BypCommodityRepertory record);

    BypCommodityRepertory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BypCommodityRepertory record);

    int updateByPrimaryKey(BypCommodityRepertory record);
}