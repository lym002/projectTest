package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.activity.ActivityFriendDAO;
import com.jsjf.model.activity.ActivityFriend;
import com.jsjf.service.activity.ActivityFriendService;

@Service
public class ActivityFriendServiceImpl implements ActivityFriendService {
	
	@Autowired
	private ActivityFriendDAO friendDao;

	@Override
	public void insert(ActivityFriend friend) {
		friendDao.insert(friend);
	}

	@Override
	public List<Map<String, Object>> findInviteCashback(Map<String, Object> param) {
		return friendDao.findInviteCashback(param);
	}

	@Override
	public int findInviteCashbackCount(Map<String, Object> param) {
		return friendDao.findInviteCashbackCount(param);
	}

	@Override
	public int findExistsInPeriod(String startDate, String endDate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		return friendDao.findExistsInPeriod(param);
	}

	@Override
	public int findExistsByNow() {
		return friendDao.findExistsByNow();
	}

	@Override
	public List<ActivityFriend> selectObjectByMap(Map<String, Object> map) {
		
		return friendDao.selectObjectByMap(map);
	}

	@Override
	public List<Integer> selectIsSend(Integer afid) {
		
		return friendDao.selectIsSend(afid);
	}

	@Override
	public List<ActivityFriend> selectActivityFriendByMap(
			Map<String, Object> map) {
		return friendDao.selectActivityFriendByMap(map);
	}

}
