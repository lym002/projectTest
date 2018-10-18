package com.jsjf.service.member.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
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
	/**
	 * 查找反馈信息列表
	 * @param map
	 * @return
	 */
	@Override
	public BaseResult getYsFeedbackList(Map<String, Object> map,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		List<DrFeedback> list=drFeedbackDAO.getDrFeedbackList(map);
		Integer total=drFeedbackDAO.getDrFeedbackCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	/**
	 * 查看反馈信息详情
	 * @param id
	 * @return
	 */
	@Override
	public DrFeedback selectByPrimaryKey(Integer id) {
		return drFeedbackDAO.selectByPrimaryKey(id);
	}
	/**
	 * 修改反馈信息
	 * @param ysFeedback
	 * @return
	 */
	@Override
	public void updateYsFeedback(DrFeedback ysFeedback) {
		drFeedbackDAO.updateDrFeedback(ysFeedback);
	}
}
