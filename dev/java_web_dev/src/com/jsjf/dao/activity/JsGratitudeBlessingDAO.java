package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsGratitudeBlessing;

public interface JsGratitudeBlessingDAO {
	/**
	 * 条件查询list
	 * @param map
	 * @return
	 */
	public List<JsGratitudeBlessing> selectJsGratitudeBlessingList(Map<String,Object> map);
	/**
	 * 根据uid查询
	 * @param uid
	 * @return
	 */
	public JsGratitudeBlessing selectJsGratitudeBlessingByUid(Integer uid);
	/**
	 * 添加祝福
	 * @param jsGratitudeBlessing
	 */
	public void insert(JsGratitudeBlessing jsGratitudeBlessing);
	/**
	 * 更新祝福
	 * @param jsGratitudeBlessing
	 */
	public void update(JsGratitudeBlessing jsGratitudeBlessing);
}
