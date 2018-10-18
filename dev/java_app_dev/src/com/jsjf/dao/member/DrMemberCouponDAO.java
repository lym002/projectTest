package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMemberCoupon;

public interface DrMemberCouponDAO {

	public void insertDrMemberCoupon(DrMemberCoupon record);
	
	public List<DrMemberCoupon> findAll(Map<String, Object> param);
	
	/**
	 *  修改理财金劵
	 * @param record
	 */
	public void updateDrMemberCouponByPrimaryKey(DrMemberCoupon record);
	
	/**
	 * 获取将要过期的理财劵
	 * @param amount 理财劵金额
	 * @return
	 */
	public List<Map<String,Object>> getNearlyDestroyCouponByAmount(Map<String, Object> map);
	
	/**
	 * 获取最近一张要过期的理财金券
	 * @param uid
	 * @param amount
	 * @return
	 */
	public DrMemberCoupon getNearlyDestroyCouponByUidAndAmount(@Param("uid") Integer uid, @Param("amount") BigDecimal amount);
	
	/**
	 * 查询理财金券总收益
	 * @param uid
	 * @return
	 */
	public BigDecimal getProfitCountByUid(Integer uid);
	
	public DrMemberCoupon selectByPrimaryKey(Integer id);
}
