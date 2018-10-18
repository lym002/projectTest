package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsGratitudeBlessing;

public interface JsGratitudeBlessingDAO {
	
	/**
	 * 查列表(查询状态为2)
	 * @param param
	 * @return
	 */
	public List<JsGratitudeBlessing> selectGratitudeBlessing(Map<String,Object> param);
	
	/**
	 * 添加
	 * @param vo
	 */
	public void insertGratitudeBlessing(JsGratitudeBlessing vo);
	
	/**
	 * 修改
	 * @param vo
	 */
	public void updateGratitudeBlessing(JsGratitudeBlessing vo);
	
	/**
	 * 根据uid查询
	 * @param uid
	 * @return
	 */
	public JsGratitudeBlessing selectGratitudeBlessingByUid(Integer uid);

}
