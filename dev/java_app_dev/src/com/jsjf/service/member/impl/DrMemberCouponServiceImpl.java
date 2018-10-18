package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.DrMemberCouponDAO;
import com.jsjf.model.member.DrMemberCoupon;
import com.jsjf.service.member.DrMemberCouponService;

@Service
@Transactional
public class DrMemberCouponServiceImpl implements DrMemberCouponService {
	
	@Autowired
	public DrMemberCouponDAO couponDAO;

	@Override
	public void insertDrMemberCoupon(DrMemberCoupon record) {
		couponDAO.insertDrMemberCoupon(record);
	}

	@Override
	public List<DrMemberCoupon> findAll(Map<String, Object> param) {
		return couponDAO.findAll(param);
	}
	
	/**
	 * 获取将要过期的理财劵
	 * @param amount 理财劵金额
	 * @return
	 */
	public List<Map<String,Object>> getNearlyDestroyCouponByAmount(Map<String, Object> param){
		return  couponDAO.getNearlyDestroyCouponByAmount(param);
	}

	@Override
	public BigDecimal getProfitCountByUid(Integer uid) {
		return couponDAO.getProfitCountByUid(uid);
	}

	@Override
	public void updateDrMemberCouponByPrimaryKey(DrMemberCoupon record) {
		couponDAO.updateDrMemberCouponByPrimaryKey(record);
	}
	
	@Override
	public DrMemberCoupon selectByPrimaryKey(Integer id){
		return couponDAO.selectByPrimaryKey(id);
	}
}
