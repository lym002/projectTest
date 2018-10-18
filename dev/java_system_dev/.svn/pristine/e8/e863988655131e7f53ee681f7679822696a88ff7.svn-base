package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrManuallySendCoupon;

public interface DrManuallySendCouponDAO {
	/**
	 * 批量插入
	 * @param list
	 */
	public void batchInsert(List<DrManuallySendCoupon> list);
	
	/**
	 * 获取导入用户列表
	 * @param map
	 * @return
	 */
	public List<DrManuallySendCoupon> getDrManuallySendCouponList(Map<String, Object> map);
	
	/**
	 * 获取查询总条数
	 * @param map
	 * @return
	 */
	public int getDrManuallySendCouponCount(Map<String, Object> map);
	
	/**
	 * 更新发送状态
	 * @param map
	 */
	public int updateSendParameter(Map<String,Object> map);
	
	/**
	 * 执行批量发券操作
	 * @param map
	 * @return
	 */
	public int executeManuallySendCoupon(Map<String, Object> map);
	
	/**
	 * 执行批量发送站内信
	 * @param map
	 */
	public int executeSendMessage(Map<String,Object> map);
	
}
