package com.jsjf.service.member;

/**
 * 意见信息
 */
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrFeedback;


public interface DrFeedbackService {
	/**
	 * 添加反馈信息
	 * @param feedback
	 * @throws Exception
	 */
	public void insertFeedback(DrFeedback feedback)throws Exception;
	
	/**
	 * 查找反馈信息列表
	 * @param map
	 * @return
	 */
	public BaseResult getYsFeedbackList(Map<String, Object> map,PageInfo pi);
	/**
	 * 查看反馈信息详情
	 * @param id
	 * @return
	 */
	public DrFeedback selectByPrimaryKey(Integer id);
	/**
	 * 修改反馈信息
	 * @param ysFeedback
	 * @return
	 */
	public void updateYsFeedback(DrFeedback ysFeedback);
	
}

