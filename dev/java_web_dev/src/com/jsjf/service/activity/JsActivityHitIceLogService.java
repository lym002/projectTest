package com.jsjf.service.activity;

import java.util.Date;

import com.jsjf.common.BaseResult;
import com.jsjf.model.member.DrMember;

public interface JsActivityHitIceLogService {

	/**
	 * //初始化 用户机会
	 * @param uid
	 * @param start
	 * @param end
	 */
	public void insertHitIceChanceByRule(int uid,Date start,Date end);
	
	/**
	 * 查询
	 * @return
	 */
	public BaseResult selectHitIceParam(DrMember member,Date start,Date end);
	
	/**
	 * 砸冰块
	 * @param member
	 * @param start
	 * @param end
	 * @return
	 */
	public BaseResult hitIce (DrMember member,Date start,Date end);
	
	
}
