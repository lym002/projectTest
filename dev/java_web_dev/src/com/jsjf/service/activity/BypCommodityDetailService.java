package com.jsjf.service.activity;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.BypCommodityDetail;
import com.jsjf.model.member.DrMember;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
	public Map<String, BigDecimal> selectDoubleTwelve(Integer uid);

	/**积分兑换top10
	 * @return
	 */
	public List<BypCommodityDetail> selectTopIntegralLog();

	/**插入用户兑换记录
	 * @param bcdl
	 */
	public void insertSelective(BypCommodityDetail bcdl);

	/**根据UID,pid更新实物兑换
	 * @param uid
	 * @param pid
	 * @return
	 */
	public BaseResult updateConvertGiftByUidAndPid(Integer uid, String pid);

	/**
	 * 根据UID,pid积分商城实物兑换
	 * @param uid
	 * @param pid
	 * @return
	 */
	BaseResult updateIntegralConvertGiftByUidAndPid(Integer uid, String pid, Integer number);
	/**
	 * 根据UID,pid积分商城红包兑换兑换
	 * @param uid
	 * @param pid
	 * @return
	 */
	BaseResult updateIntegralRedPacketByUidAndPid(Integer uid, String pid, Integer number);
	/**
	 * 根据UID,pid兑换猕猴桃
	 * @param dm
	 * @param pid
	 * @return
	 */
	BaseResult getFruitsByUidAndPid(DrMember dm, String pid);

	List<Map<String,Object>> selectToHelpFarmersTop(Map<String, Object> map);

	List<Map<String,Object>> selectToHelpFarmers(Map<String, Object> map);

	BaseResult getLoveByUidAndPid(DrMember member, String pid, Integer number);

    List<BypCommodityDetail> selectMyIntegralLog(Integer uid);
}
