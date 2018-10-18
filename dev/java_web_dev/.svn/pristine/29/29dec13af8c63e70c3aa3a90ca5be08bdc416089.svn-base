package com.jsjf.service.activity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.activity.BypMemberIntegralDAO;
import com.jsjf.model.activity.BypMemberIntegral;
import com.jsjf.service.activity.BypMemberIntegralService;

@Service
public class BypMemberIntegralServiceImpl implements BypMemberIntegralService {
	@Autowired
	private BypMemberIntegralDAO bypMemberIntegralDAO;

	@Override
	public void insertBypMemberIntegral(BypMemberIntegral bmi) {
		bypMemberIntegralDAO.insert(bmi);
	}

	

}
