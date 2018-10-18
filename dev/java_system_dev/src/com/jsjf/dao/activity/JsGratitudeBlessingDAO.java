package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsGratitudeBlessing;

public interface JsGratitudeBlessingDAO {
	
	/**
	 * 查询所有祝福
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectGratitudeBlessing(Map<String, Object> map);
	
	/**
	 * 查询所有祝福总数
	 * @param map
	 * @return
	 */
	public Integer  selectGratitudeBlessingCount(Map<String, Object> map);
	
	/**
	 * 审核
	 * @param map
	 */
	public void updateGratitudeBlessing(Map<String, Object> map);
	
	
	/**
	 * 修改
	 */
	public void update (JsGratitudeBlessing obj);
	
	/**
	 * 根据uid查询
	 * @param uid
	 * @return
	 */
	public JsGratitudeBlessing selectObjectOneByUid(Integer uid);
	
	/**
	 * 添加
	 * @param obj
	 */
	public void insert (JsGratitudeBlessing obj);
}


