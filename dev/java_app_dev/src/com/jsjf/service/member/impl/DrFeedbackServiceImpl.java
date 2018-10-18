package com.jsjf.service.member.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.DrFeedbackDAO;
import com.jsjf.model.member.DrFeedback;
import com.jsjf.service.member.DrFeedbackService;


@Service
@Transactional
public class DrFeedbackServiceImpl implements DrFeedbackService {
	@Autowired
	private DrFeedbackDAO drFeedbackDAO;
	/**
	 * 添加意见信息
	 */
	@Override
	public void insertFeedback(DrFeedback feedback) throws Exception{
		drFeedbackDAO.insertFeedback(feedback);
	}
	
}
