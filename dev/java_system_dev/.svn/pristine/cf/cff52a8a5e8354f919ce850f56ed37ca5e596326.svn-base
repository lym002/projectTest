package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrFeedback;

/**
 * 意见信息
 * @author tangxiangping
 *
 */
public interface DrFeedbackDAO {
	/**
	 * 添加意见信息
	 * @param feedback
	 */
	void insertFeedback(DrFeedback feedback) throws SQLException;
	
	/**
	 * 查找反馈信息列表
	 * @param map 
	 * @return
	 */
	public List<DrFeedback> getDrFeedbackList(Map<String, Object> map);
	/**
	 * 获得反馈信息的总数
	 * @param map
	 * @return
	 */
	Integer getDrFeedbackCounts(Map<String, Object> map);
	
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
	public void updateDrFeedback(DrFeedback ysFeedback);
    
}