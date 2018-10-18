package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.ActivityFriend;

public interface ActivityFriendDAO {
	
	public void insert(ActivityFriend friend);

	/**
	 * 查询邀请返现
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findInviteCashback(Map<String, Object> param); 
	
	public int findInviteCashbackCount(Map<String, Object> param);
	
	public int findExistsInPeriod(Map<String, Object> param);
	
	public int findExistsByNow();
	
	public List<ActivityFriend> selectObjectByMap (Map<String,Object> map);
	
	public List<ActivityFriend> selectActivityFriendByMap (Map<String,Object> map);
	
	/**
	 * 查询可以返现的人
	 * @param afid
	 * @return
	 */
	public List<Integer> selectIsSend(Integer afid);
	/**
	 * 查询邀请好友返现 用户列表
	 * @return
	 */
	public List<Map<String, Integer>> getUidS();
	
	public void addActivityUser(Map<String, Object> param);

	//落地页：定时查询好友邀请返现总额的排名
	public List<Map<String, Object>> selectReversal();
	/**
	 * 根据min查询出应该奖励的金额
	 * @param min
	 * @param i 
	 * @return
	 */
	public List<Map<String, Integer>> getEnvelopeLevel(@Param(value = "min")int min,@Param(value = "max") int max);

	
}
