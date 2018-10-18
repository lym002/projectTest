package com.jsjf.dao.store;

import java.util.List;
import java.util.Map;

import com.jsjf.model.store.CommodityExchange;
import com.jsjf.model.vip.VipEquities;

public interface CommodityExchangeDao {
    int deleteCommodityExchange(Integer id);

    int insert(CommodityExchange record);

    int addCommodityExchange(CommodityExchange record);

    CommodityExchange selectByPrimaryKey(Integer id);

    int updateCommodityExchange(CommodityExchange record);

    int updateByPrimaryKey(CommodityExchange record);

	List<VipEquities> queryCommodityExchangeList(Map<String, Object> param);

	Integer queryCommodityExchangeListCount(Map<String, Object> param);

	void addImportCommodity(List<CommodityExchange> beanList);

	int queryCommodityInto(Integer exchangeId);
}