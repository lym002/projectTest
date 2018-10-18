package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.JsActivityAggregationPage;

public interface JsActivityAggregationPageService {

	/**
	 * 获取活动列表
	 * @return
	 */
	public BaseResult selectJsActivityAggregationPageList(Map<String,Object> map);
}
