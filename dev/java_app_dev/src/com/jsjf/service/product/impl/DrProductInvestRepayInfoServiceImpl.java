package com.jsjf.service.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.product.DrProductInvestRepayInfoDAO;
import com.jsjf.model.product.DrProductInvestRepayInfo;
import com.jsjf.service.product.DrProductInvestRepayInfoService;

@Service
@Transactional
public class DrProductInvestRepayInfoServiceImpl implements
		DrProductInvestRepayInfoService {

	@Autowired
	private DrProductInvestRepayInfoDAO drProductInvestRepayInfoDAO;
	
	@Override
	public Integer selectInvestRepayInfoNumsByParam(Map<String, Object> map) {
		return drProductInvestRepayInfoDAO.selectInvestRepayInfoNumsByParam(map);
	}

	@Override
	public List<Map<String, Object>> selectRepayInfoDetail(Integer investId) {
		return drProductInvestRepayInfoDAO.selectRepayInfoDetailByInvestId(investId);
	}
	
	@Override
	public int selectReturnedCount(Integer uid) {
		return drProductInvestRepayInfoDAO.selectReturnedCount(uid);
	}
	
	@Override
	public List<DrProductInvestRepayInfo> selectPayment(Integer uid) {
		return drProductInvestRepayInfoDAO.selectPayment(uid);
	}
	
	@Override
	public void update(DrProductInvestRepayInfo drProductInvestRepayInfo) {
		drProductInvestRepayInfoDAO.update(drProductInvestRepayInfo);
	}

}
