package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.activity.JsOpenDayLogDao;
import com.jsjf.model.activity.JsOpenDayLog;
import com.jsjf.service.activity.JsOpenDayLogService;

@Transactional
@Service
public class JsOpenDayLogServiceImpl implements JsOpenDayLogService {
	
	@Autowired
	private JsOpenDayLogDao jsOpenDayLogDao;

	@Override
	public BaseResult getOpenDayLogList(JsOpenDayLog jsOpenDayLog, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map = new HashMap<>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		map.put("openDayId", jsOpenDayLog.getOpenDayId());
		List<JsOpenDayLog> list = jsOpenDayLogDao.getOpenDayLogList(map);
		Integer total = jsOpenDayLogDao.getOpenDayLogCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<JsOpenDayLog> exportOpenDayLog(Integer openDayId) {
		Map<String,Object> map = new HashMap<>();
		map.put("openDayId", openDayId);
		return jsOpenDayLogDao.getOpenDayLogList(map);
	} 

	
}
