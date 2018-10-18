package com.jsjf.service.store;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.store.CommodityExchange;

public interface CommodityExchangeServer {

	BaseResult queryCommodityExchangeList(Map<String, Object> param, PageInfo pi);

	BaseResult addCommodityExchange(CommodityExchange bean);

	BaseResult updateCommodityExchange(CommodityExchange bean);

	BaseResult deleteCommodityExchange(int id);

	BaseResult addImportCommodity(String name, MultipartFile bannerAddPicFile) throws Exception;

}
