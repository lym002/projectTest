package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.JsActivityFriend;
import com.jsjf.model.member.DrMember;

public interface JsActivityFriendService {

	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public JsActivityFriend selectByPrimaryKey(Integer id);
	/**
	 * 获得最新的活动
	 * @return
	 */
	public BaseResult selectNewJsActivityFriend(DrMember m,Map<String,Object> map);
	
	/**
	 * //邀请好有返利活动列表
	 * @return
	 */
	public BaseResult selectJsActivityFriend(Map<String,Object> map);
	/**
	 * //邀请好有返利列表
	 * @return
	 */
	public BaseResult getActivityFriendStatistics(DrMember m,Map<String,Object> map);
	
	
	public BaseResult myInvitation(DrMember m,Map<String,Object> map);
	/**
	 * 邀请好友三重礼，我的返现首投列表
	 * @param uid
	 * @return
	 */
	public BaseResult firstInvestList(int uid);
	/**
	 * 邀请好友三重礼奖励信息
	 * @param uid
	 * @return
	 */
	public BaseResult threePresent(Integer uid);
	/**
	 * 根据条件获取活动
	 * @param map
	 * @return
	 */
	public List<JsActivityFriend> selectJsActivityFriendByParam(Map<String,Object> map);

    BaseResult getActivityFriendBonus(Integer uid);

	BaseResult getMyFriendBonus(Integer uid);
}
