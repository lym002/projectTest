package com.jsjf.service.member;

/**
 * 意见信息
 */
import com.jsjf.model.member.DrFeedback;


public interface DrFeedbackService {
	/**
	 * 添加反馈信息
	 * @param feedback
	 * @throws Exception
	 */
	public void insertFeedback(DrFeedback feedback)throws Exception;
	
	
}

