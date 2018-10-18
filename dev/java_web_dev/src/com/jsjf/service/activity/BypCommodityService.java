package com.jsjf.service.activity;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.BypCommodity;
import com.jsjf.model.member.DrMember;

import java.util.List;

public interface BypCommodityService {

	/**
	 * @param pid
	 * @return
	 */
	BypCommodity selectIntegralByPid(String pid);

	/**
	 * 积分商城列表
	 * @return
	 */
	BaseResult selectIntegralShopping(DrMember m, int type);

	/**
	 * 获取积分商品列表/红包列表
	 * @param type
	 * @return
	 */
	List<BypCommodity> selectCommodityList(int type);

	/**
	 * 获取积分商品详情
	 * @param uid
	 * @param pid
	 * @return
	 */
	BaseResult selectCommodityInfo(Integer uid, String pid);

    List<BypCommodity> selectToHelpFarmers();
}