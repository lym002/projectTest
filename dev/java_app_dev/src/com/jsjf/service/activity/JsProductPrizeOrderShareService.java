package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsProductPrizeOrderShare;

public interface JsProductPrizeOrderShareService {

	/**
	 * 根据map查询list
	 * @param param
	 * @return
	 */
	public List<JsProductPrizeOrderShare> selectByMap(Map<String,Object> map);
	/**
	 * 根据map查询list
	 * @param param
	 * @return
	 */
	public BaseResult selectOrderShareByMap(PageInfo pageInfo,Integer uid);
	
	/**
	  * 添加晒单
	  * @param obj
	  */
	public BaseResult insert (JsProductPrizeOrderShare obj,MultipartFile picUrl);
}
