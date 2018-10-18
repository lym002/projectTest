package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.activity.JsActivityAggregationPageDAO;
import com.jsjf.model.activity.JsActivityAggregationPage;
import com.jsjf.service.activity.JsActivityAggregationPageService;

@Service
@Transactional
public class JsActivityAggregationPageServiceImpl implements JsActivityAggregationPageService {
	
	@Autowired
	private JsActivityAggregationPageDAO jsActivityAggregationPageDAO;

	@Override
	public PageInfo selectJsActivityAggregationPageList(Map<String,Object> map,PageInfo pi) {
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<JsActivityAggregationPage> list = jsActivityAggregationPageDAO.selectJsActivityAggregationPageList(map);
		int total = jsActivityAggregationPageDAO.selectJsActivityAggregationPageCount(map);
		map.clear();
		pi.setTotal(total);
		pi.setRows(list);
		return pi;
	}


}
