package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberFundsRecord;

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
	public BaseResult getTheRewards(Integer uid,Integer afid);
	
	/**
	 * 查询累计收益
	 * @param map
	 * @return
	 */
	public BaseResult selectAccumulatedIncomeByUid(Map<String, Object> map,PageInfo pi);

	Integer selectInvestSumByOnlineTime(Map<String, Object> param);

    BaseResult selectAccumulatedClassification(Map<String, Object> param);
}
