package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.ActivityFriend;

public interface ActivityFriendService {

	/**
	 * 新增
	 * @param friend
	 */
	public void insert(ActivityFriend friend);
	
	/**
	 * 查询邀请返现
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findInviteCashback(Map<String, Object> param);
	
	public int findInviteCashbackCount(Map<String, Object> param);
	
	/**
	 * 判断活动时间段是否交叉
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int findExistsInPeriod(String startDate,String endDate);
	
	/**
	 * 判断当前时间是否已有活动
	 * @return
	 */
	public int findExistsByNow();
	
	public List<ActivityFriend> selectObjectByMap (Map<String,Object> map);
	public List<ActivityFriend> selectActivityFriendByMap (Map<String,Object> map);
	
	/**
	 * 查询可以返现的人
	 * @param afid
	 * @return
	 */
	public List<Integer> selectIsSend(Integer afid);
}
