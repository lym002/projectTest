package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsSignIn;

public interface JsSignInDAO {
	/**
	 * 根据条件查询签到
	 * @return
	 */
	public List<JsSignIn> selectSameMonthSignInCount(Map<String,Object> map);
	/**
	 * 添加
	 * @param jsSignIn
	 */
	public void insert(JsSignIn jsSignIn);
	/**
	 * 更新
	 * @param jsSignIn
	 */
	public void update(JsSignIn jsSignIn);
}
