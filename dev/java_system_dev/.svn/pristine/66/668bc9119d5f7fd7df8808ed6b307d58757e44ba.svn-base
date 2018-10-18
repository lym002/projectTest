package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsChannelCouponPut;
import com.jsjf.model.activity.JsChannelCouponPutDetail;

public interface JsChannelCouponPutDAO {
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public JsChannelCouponPut selectObjectById(Integer id);
	/**
	 * 添加
	 * @param jsChannelCouponPut
	 */
	public void insert (JsChannelCouponPut jsChannelCouponPut);
	/**
	 * 修改
	 * @param jsChannelCouponPut
	 */
	public void update (JsChannelCouponPut jsChannelCouponPut);
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public List<JsChannelCouponPut> selectObjectList(Map<String, Object> map);
	/**
	 * 查询条数
	 * @param map
	 * @return
	 */
	public int selectObjectListCount(Map<String, Object> map);
	/**
	 * 查询获取发放详情
	 * @param map
	 * @return
	 */
	public List<JsChannelCouponPutDetail> selectCouponDetailListList(Map<String, Object> map);
	/**
	 * 查询发放详情条数
	 * @param map
	 * @return
	 */
	public int selectCouponDetailListCount(Map<String, Object> map);
	
	/**
	 * 添加 发放详情
	 * @param map
	 */
	public void insertPutDetail(Map<String,Object> map);
	
	/**
	 * 校验发放详情
	 * @param map
	 * @return
	 */
	public void checkOutCouponPutDetail(Integer putId);
	/**
	 * 根据发放详情发送优惠券
	 * @param map
	 * @return
	 */
	public int insertCouponByPutDetail(Integer putId);
	/**
	 * 删除发放详情
	 * @param map
	 * @return
	 */
	public void deleteCouponByPutDetail(Integer putId);
	
	
	
}
