package com.jsjf.dao.integral;

import com.jsjf.model.integral.BypCommodityExchange;

import java.util.HashMap;
import java.util.List;

public interface BypCommodityExchangeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BypCommodityExchange record);

    int insertSelective(BypCommodityExchange record);

    BypCommodityExchange selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BypCommodityExchange record);

    int updateByPrimaryKey(BypCommodityExchange record);

    List<BypCommodityExchange> selectByExchangeId(HashMap<String, Object> param);
}