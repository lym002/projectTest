package com.jsjf.service.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.system.JsErrorDAO;
import com.jsjf.service.system.JsErrorService;

@Transactional
@Service
public class JsErrorServiceImpl implements JsErrorService{

	@Autowired JsErrorDAO jsErrorDAO;

	@Override
	public List<Map<String, Object>> getJsError(Map<String, Object> map) {
		return jsErrorDAO.getJsError(map);
	}
}
