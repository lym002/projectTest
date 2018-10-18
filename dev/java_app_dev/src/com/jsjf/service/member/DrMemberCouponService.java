package com.jsjf.service.member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberCoupon;

public interface DrMemberCouponService {

	public void insertDrMemberCoupon(DrMemberCoupon record);
	
	public List<DrMemberCoupon> findAll(Map<String, Object> param);
	
	/**
	 * 获取各种将要过期的理财劵张数
	 * @return
	 */
	public List<Map<String,Object>> getNearlyDestroyCouponByAmount(Map<String, Object> map);
	
	public BigDecimal getProfitCountByUid(Integer uid);
	
	public void updateDrMemberCouponByPrimaryKey(DrMemberCoupon record);
	
	public DrMemberCoupon selectByPrimaryKey(Integer id);
}
