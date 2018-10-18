package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.activity.JsGratitudeBlessingDAO;
import com.jsjf.service.activity.JsGratitudeBlessingService;

@Service
@Transactional
public class JsGratitudeBlessingServiceImpl implements JsGratitudeBlessingService {
	
	@Autowired JsGratitudeBlessingDAO jsGratitudeBlessingDAO;
	
	@Override
	public BaseResult selectGratitudeBlessing(PageInfo pi,Map<String, Object>map) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String, Object>> list = jsGratitudeBlessingDAO.selectGratitudeBlessing(map);
		Integer total = jsGratitudeBlessingDAO.selectGratitudeBlessingCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public void updateGratitudeBlessing(Map<String, Object> map) {
		jsGratitudeBlessingDAO.updateGratitudeBlessing(map);
	}
}
