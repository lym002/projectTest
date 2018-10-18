package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrCouponsIssuedRulesDAO;
import com.jsjf.dao.activity.DrManuallySendCouponDAO;
import com.jsjf.model.activity.DrCouponsIssuedRules;
import com.jsjf.model.activity.DrManuallySendCoupon;
import com.jsjf.service.activity.DrActivityParameterService;
import com.jsjf.service.activity.DrCouponsIssuedRulesService;
@Service
public class DrCouponsIssuedRulesServiceImpl implements DrCouponsIssuedRulesService {
	@Autowired
	private DrCouponsIssuedRulesDAO drCouponsIssuedRulesDAO;
	@Autowired
	private DrManuallySendCouponDAO drManuallySendCouponDAO;
	
	public void insertCouponsIssuedRules(DrCouponsIssuedRules drCouponsIssuedRules) {
		drCouponsIssuedRulesDAO.insertCouponsIssuedRules(drCouponsIssuedRules);
	}

	@Override
	public void updateCouponsIssuedRules(DrCouponsIssuedRules drCouponsIssuedRules) {
		drCouponsIssuedRulesDAO.updateCouponsIssuedRules(drCouponsIssuedRules);
	}

	@Override
	public BaseResult getCouponsIssuedRulesList(Map<String, Object> map,PageInfo pi) {
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrCouponsIssuedRules> list = drCouponsIssuedRulesDAO.getCouponsIssuedRulesList(map);
		Integer total = drCouponsIssuedRulesDAO.getCouponsIssuedRulesTotal(map);
		map.clear();
		pi.setTotal(total);
		pi.setRows(list);
		map.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(map);
		return br;
	}

	@Override
	public DrCouponsIssuedRules getCouponsIssuedRulesById(Integer id) {
		return drCouponsIssuedRulesDAO.getCouponsIssuedRulesById(id);
	}

	@Override
	public void batchInsertDrManuallySendCoupon(List<DrManuallySendCoupon> list) {
		drManuallySendCouponDAO.batchInsert(list);
	}

	@Override
	public PageInfo getDrManuallySendCouponList(Map<String, Object> map,
			PageInfo pi) {
		map.put("limit", pi.getPageInfo().getLimit());
		map.put("offset", pi.getPageInfo().getOffset());
		List<DrManuallySendCoupon> list = drManuallySendCouponDAO.getDrManuallySendCouponList(map);
		int total = drManuallySendCouponDAO.getDrManuallySendCouponCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}

	@Override
	public int executeManuallySendCoupon(Map<String, Object> map) {
		return drManuallySendCouponDAO.executeManuallySendCoupon(map);
	}

	@Override
	public int updateSendParameter(Map<String, Object> map) {
		return drManuallySendCouponDAO.updateSendParameter(map);
	}

	@Override
	public int executeSendMessage(Map<String, Object> map) {
		return drManuallySendCouponDAO.executeSendMessage(map);
	}

}
