package com.jsjf.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrLotteryParam;

public interface DrLotteryParamDAO {

	/**
	 * 根据活动ID获取礼品信息
	 * @param aid
	 * @return
	 */
	public List<DrLotteryParam> selectByAid(@Param(value="aid") Integer aid);
	
}
