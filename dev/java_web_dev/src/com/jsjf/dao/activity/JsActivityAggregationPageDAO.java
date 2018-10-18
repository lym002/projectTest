package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsActivityAggregationPage;

public interface JsActivityAggregationPageDAO {
	
	/**
	 * 获取活动列表
	 * @return
	 */
	public List<JsActivityAggregationPage> selectJsActivityAggregationPageList(Map<String,Object> map);
	/**
	 * 查询活动总条数
	 * @param map
	 * @return
	 */
	public int selectJsActivityAggregationPageCount(Map<String,Object> map);
}
