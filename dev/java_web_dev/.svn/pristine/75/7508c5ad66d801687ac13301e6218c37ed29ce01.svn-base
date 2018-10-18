package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.JsOpenDayLogDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsOpenDayLog;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.activity.JsOpenDayLogService;

@Service
@Transactional
public class JsOpenDayLogServiceImpl implements JsOpenDayLogService {

	@Autowired
	JsOpenDayLogDAO jsOpenDayLogDAO;
	
	@Override
	public void insert(JsOpenDayLog jsOpenDayLog) {
		jsOpenDayLogDAO.insert(jsOpenDayLog);
	}
	

}
