package com.jsjf.service.activity;

import java.util.List;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.JsGratitudeBlessing;

public interface JsGratitudeBlessingService {
	
	/**
	 * 查列表(查询状态为2)
	 * @param param
	 * @return
	 */
	public List<JsGratitudeBlessing> selectGratitudeBlessing();
	
	/**
	 * 添加
	 * @param vo
	 */
	public BaseResult insertGratitudeBlessing(JsGratitudeBlessing vo);
	
	/**
	 * 修改
	 * @param vo
	 */
	public BaseResult updateGratitudeBlessing(JsGratitudeBlessing vo);
	/**
	 * 根据uid查询
	 * @param uid
	 * @return
	 */
	public JsGratitudeBlessing selectGratitudeBlessingByUid(Integer uid);
	
	
	public List<DrActivityParameter> selectGratitudeBlessingFavourable();

}
