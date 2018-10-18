package com.jsjf.service.activity;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;

public interface JsGratitudeBlessingService {
	
	public BaseResult selectGratitudeBlessing(PageInfo pi,Map<String, Object>map);
	
	/**
	 * 审核
	 * @param map
	 */
	public void updateGratitudeBlessing(Map<String, Object> map);

	
}
