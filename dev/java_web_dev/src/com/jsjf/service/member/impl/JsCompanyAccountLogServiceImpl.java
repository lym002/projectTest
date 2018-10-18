package com.jsjf.service.member.impl;

import com.jsjf.dao.member.JsCompanyAccountLogDAO;
import com.jsjf.model.member.JsCompanyAccountLog;
import com.jsjf.service.member.JsCompanyAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
@Transactional
public class JsCompanyAccountLogServiceImpl implements
		JsCompanyAccountLogService {
	@Autowired
	private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
	@Override
	public List<JsCompanyAccountLog> selectBidPrize() {
		return jsCompanyAccountLogDAO.selectBidPrize();
	}


	@Override
	public List<JsCompanyAccountLog> selectBidPrizeByUid(Map<String, Object> map) {
		return jsCompanyAccountLogDAO.selectBidPrizeByUid(map);
	}

    @Override
    public List<JsCompanyAccountLog> selectBidFullByUid(Map<String, Object> map) {
        return jsCompanyAccountLogDAO.selectBidFullByUid(map);
    }
}
