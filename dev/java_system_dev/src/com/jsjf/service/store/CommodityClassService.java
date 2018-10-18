package com.jsjf.service.store;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.store.CommodityClass;
import com.jsjf.model.store.CommodityRepertory;

public interface CommodityClassService {

	BaseResult queryCommodityClassList(Map<String, Object> param, PageInfo pi);

	BaseResult addCommodityClass(CommodityClass bean);

	BaseResult updateCommodityClass(CommodityClass bean);

	BaseResult deleteCommodityClass(Integer id);

	List<CommodityRepertory> queryDd();

}
