package com.jsjf.service.activity;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.system.SysUsersVo;

public interface DrMemberFavourableService {
	
	/**
	 * 发放优惠券到用户表中
	 * @param uid
	 * @param drActivityParameter
	 * @param usersVo
	 * @throws Exception
	 */
	public void selectActivity(Integer uid,DrActivityParameter drActivityParameter,SysUsersVo usersVo) throws Exception;
	
	/**
	 * 获取用户优惠券列表
	 * @param map
	 * @param pi
	 * @return
	 */
	public BaseResult selectFavourableByParam(Map<String, Object> map, PageInfo pi);
	/**
	 * 批量用户发放操作
	 * @param uid
	 * @param drActivityParameter
	 * @param usersVo
	 * @throws Exception 
	 */
	public void batchSelectActivity(Integer[] uids, DrActivityParameter drActivityParameter, SysUsersVo usersVo)throws Exception;

}
