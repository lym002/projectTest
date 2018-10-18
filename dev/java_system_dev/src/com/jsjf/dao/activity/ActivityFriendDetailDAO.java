package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.ActivityFriendDetail;

public interface ActivityFriendDetailDAO {

	public void insert(ActivityFriendDetail detail);
	
	public List<Map<String, Object>> findActivityFriendDetails(Map<String, Object> param);
}
