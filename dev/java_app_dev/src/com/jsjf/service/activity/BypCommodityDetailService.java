package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.BypCommodityDetail;
import com.jsjf.model.member.DrMember;

public interface BypCommodityDetailService {

	/**
	 * 查询用户送京东卡的详情
	 *
	 * @param uid
	 * @return
	 */
	public List<BypCommodityDetail> doubleTwelve(Integer uid);

	/**
	 * 查看用户累计页面
	 *
	 * @param uid
	 * @return
	 */
	public Map<String, Object> selectDoubleTwelve(Integer uid);

	/**
	 * Top10查询兑换记录
	 *
	 * @return
	 */
	public List<BypCommodityDetail> selectTopIntegralLog();

	/**
	 * 查看我的双旦兑换记录
	 *
	 * @param uid
	 * @return
	 */
	public List<BypCommodityDetail> selectMyIntegralLog(Integer uid);

	public void insertSelective(BypCommodityDetail bcdl);

	/**
	 * 插入兑换根据用户id和pid
	 *
	 * @param uid
	 * @param pid
	 * @return
	 */
	public BaseResult insertConvertByUserAndPid(Integer uid, String pid);

	/**
	 * 根据类型查询用户的礼品
	 *
	 * @param uid
	 * @param type
	 * @return
	 */
	public List<BypCommodityDetail> selectMyAward(Map<String, Object> map);

	/**
	 * 根据UID,pid积分商城实物兑换
	 *
	 * @param uid
	 * @param pid
	 * @return
	 */
	BaseResult updateIntegralConvertGiftByUidAndPid(Integer uid, String pid, Integer number);

	/**
	 * 根据UID,pid积分商城红包兑换兑换
	 *
	 * @param uid
	 * @param pid
	 * @return
	 */
	BaseResult updateIntegralRedPacketByUidAndPid(Integer uid, String pid, Integer number);

	/**
	 * 根据UID,pid兑换猕猴桃
	 *
	 * @param dm
	 * @param pid
	 * @return
	 */
	BaseResult getFruitsByUidAndPid(DrMember dm, String pid);

	List<Map<String, Object>> selectToHelpFarmersTop(Map<String, Object> map);

	List<Map<String, Object>> selectToHelpFarmers(Map<String, Object> map);

	BaseResult getLoveByUidAndPid(DrMember member, String pid, Integer number);
}
