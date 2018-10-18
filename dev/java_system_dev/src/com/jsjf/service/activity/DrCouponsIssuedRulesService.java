package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.DrCouponsIssuedRules;
import com.jsjf.model.activity.DrManuallySendCoupon;

public interface DrCouponsIssuedRulesService {
	/**
	 * 新增规则
	 * @param drCouponsIssuedRules
	 */
	public void insertCouponsIssuedRules(DrCouponsIssuedRules drCouponsIssuedRules);
	
	/**
	 * 修改规则
	 * @param drCouponsIssuedRules
	 */
	public void updateCouponsIssuedRules(DrCouponsIssuedRules drCouponsIssuedRules);
	
	/**
	 * 获取规则列表
	 * @param map
	 * @return
	 */
	public BaseResult getCouponsIssuedRulesList(Map<String,Object> map,PageInfo pi);
	
	/**
	 * 获取规则
	 * @param id
	 * @return
	 */
	public DrCouponsIssuedRules getCouponsIssuedRulesById(Integer id); 
	
	/**
	 * 批量导入发券用户
	 * @param list
	 */
	public void batchInsertDrManuallySendCoupon(List<DrManuallySendCoupon> list);
	
	/**
	 * 获取发券用户列表
	 * @param map
	 * @param pi
	 * @return
	 */
	public PageInfo getDrManuallySendCouponList(Map<String,Object> map,PageInfo pi);
	
	/**
	 * 执行发送优惠券
	 * @param map
	 * @return
	 */
	public int executeManuallySendCoupon(Map<String,Object> map);
	
	/**
	 * 更新发送状态
	 * @param map
	 */
	public int updateSendParameter(Map<String,Object> map);
	
	/**
	 * 发送站内信
	 * @param map
	 * @return
	 */
	public int executeSendMessage(Map<String,Object> map);
	
	
	

}
