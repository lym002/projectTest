package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.JsActivityAggregationPageDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsActivityAggregationPage;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.activity.JsActivityAggregationPageService;

@Service
public class JsActivityAggregationPageServiceImpl implements JsActivityAggregationPageService {
	
	@Autowired
	private JsActivityAggregationPageDAO jsActivityAggregationPageDAO;

	@Override
	public BaseResult selectJsActivityAggregationPageList(Map<String,Object> map) {
		BaseResult br = new BaseResult();
		PageInfo pageInfo = new PageInfo();
		List<JsActivityAggregationPage> list = jsActivityAggregationPageDAO.selectJsActivityAggregationPageList(map);
		int total = jsActivityAggregationPageDAO.selectJsActivityAggregationPageCount(map);
		pageInfo.setRows(list);
		pageInfo.setTotal(total);
		map.clear();
		map.put("pageInfo", pageInfo);
		br.setMap(map);
		br.setSuccess(true);
		
		return br;
	}


}
