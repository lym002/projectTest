package com.jsjf.service.member;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;

public interface DrMemberFundsRecordService {
	
	/**
	 * 查询用户资产记录
	 * @param map
	 * @param pi
	 * @return
	 */
	public BaseResult selectMemberFundsRecordByParam(Map<String, Object> map, PageInfo pi);
	/**
	 * 领取奖金
	 * @param map
	 * @return
	 */
	public BaseResult getTheRewards(DrMember m,Map<String, Object> map);

	/**
	 * 插叙上线累计投资额
	 * @param param
	 * @return
	 */
	Integer selectInvestSumByOnlineTime(Map<String, Object> param);

}
