package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.service.member.JsCompanyAccountLogService;

@Service
@Transactional
public class JsCompanyAccountLogServiceImpl implements JsCompanyAccountLogService{

	@Autowired JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
	
	@Override
	public void insertCompanyAccountLog(JsCompanyAccountLog accountLog) {
		jsCompanyAccountLogDAO.insertCompanyAccountLog(accountLog);
	}

	@Override
	public List<Map<String, Object>> getCompanyAccountLog(Map<String, Object> map) {
		return jsCompanyAccountLogDAO.getCompanyAccountLog(map);
	}

	@Override
	public int getCompanyAccountLogCount(Map<String, Object> map) {
		return jsCompanyAccountLogDAO.getCompanyAccountLogCount(map);
	}

	@Override
	public BigDecimal getCompanyAccountSRSum(Map<String, Object> map) {
		return jsCompanyAccountLogDAO.getCompanyAccountSRSum(map);
	}

	@Override
	public BigDecimal getCompanyAccountZCSum(Map<String, Object> map) {
		return jsCompanyAccountLogDAO.getCompanyAccountZCSum(map);
	}

	@Override
	public void updateCompanyAccountLog(JsCompanyAccountLog accountLog) {
		jsCompanyAccountLogDAO.updateCompanyAccountLog(accountLog);
	}

}
