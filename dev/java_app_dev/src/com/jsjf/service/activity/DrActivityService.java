package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.DrActivity;

public interface DrActivityService {

	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public DrActivity selectByPrimaryKey(Integer id);
	
	/**
	 * 根据时间来判断活动是否开启
	 */
	public List<DrActivity> selectByTime(Map<String,Object> param);
	/**
	 * 条件查询list
	 * @param map
	 * @return
	 */
	public List<DrActivity> selectDrActivityList(Map<String,Object> map);
	
	/**
	 * 翻牌活动
	 * @param param
	 * @return
	 */
	public BaseResult flop(Map<String,Object> param);
	/**
	 * 翻牌活动首页
	 * @return
	 */
	public BaseResult flopIndex();
	/**
	 * 翻牌分享
	 * @return
	 */
	public BaseResult flopShare(Map<String,Object> map);
	
	/**
	 * 通过活动名字查询
	 * @param id
	 * @return
	 */
	public DrActivity selectObjectByName(String name);
}
