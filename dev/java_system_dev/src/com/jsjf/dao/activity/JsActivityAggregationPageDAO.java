package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.visitor.functions.Insert;
import com.jsjf.model.activity.JsActivityAggregationPage;

public interface JsActivityAggregationPageDAO {
	
	/**
	 * 获取活动列表
	 * @return
	 */
	public List<JsActivityAggregationPage> selectJsActivityAggregationPageList(Map<String,Object> map);
	
	/**
 	 * 得到活动总数
 	 * @param Map
 	 * @return Integer
 	 */
     public Integer getJsActivityAggregationPageCounts(Map<String,Object> map); 
     
     /**
      * 插入聚合活动页
      * @param jsActivityAggregationPage
      */
     public void insert(JsActivityAggregationPage jsActivityAggregationPage);
     
     /**
      * 更新聚合活动页
      * @param jsActivityAggregationPage
      */
     public void update(JsActivityAggregationPage jsActivityAggregationPage);
     
     public JsActivityAggregationPage selectJsActivityAggregationPageById(Integer id);
}
