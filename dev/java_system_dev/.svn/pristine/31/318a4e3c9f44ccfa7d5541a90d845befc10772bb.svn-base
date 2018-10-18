package com.jsjf.dao.store;

import java.util.List;
import java.util.Map;

import com.jsjf.model.store.CommodityClass;
import com.jsjf.model.store.CommodityRepertory;
import com.jsjf.model.vip.VipEquities;

public interface CommodityClassDao {
    void deleteCommodityClass(Integer id);

    int insert(CommodityClass record);

    int insertSelective(CommodityClass record);

    CommodityClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommodityClass record);

    int updateByPrimaryKey(CommodityClass record);

	List<VipEquities> queryCommodityClassList(Map<String, Object> param);

	Integer queryCommodityClassListCount(Map<String, Object> param);

	void addCommodityClass(CommodityClass bean);

	void updateCommodityClass(CommodityClass bean);

	List<CommodityRepertory> queryDd();
}