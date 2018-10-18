package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.activity.ActivityFriendDetailDAO;
import com.jsjf.model.activity.ActivityFriendDetail;
import com.jsjf.service.activity.ActivityFriendDetailService;

@Service
@Transactional
public class ActivityFriendDetailServiceImpl implements ActivityFriendDetailService {

	@Autowired
	private ActivityFriendDetailDAO detailDao;
	
	@Override
	public void insert(ActivityFriendDetail detail) {
		detailDao.insert(detail);
	}

	@Override
	public List<Map<String, Object>> findActivityFriendDetails(Map<String, Object> param) {
		return detailDao.findActivityFriendDetails(param);
	}

}
