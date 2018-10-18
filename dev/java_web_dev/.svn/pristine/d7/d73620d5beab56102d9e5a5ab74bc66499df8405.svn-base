package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsSignIn;


public interface JsSignInDAO {
	
	/**
	 * 修改
	 * @param map
	 * @return
	 */
	public void update(JsSignIn obj);
	/**
	 *添加
	 * @param map
	 * @return
	 */
	public void insert(JsSignIn obj);
	
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public List<JsSignIn> selectObjectByMap(Map<String,Object> map);
	
	
	/**
	 * 更新签到排行榜
	 */
	public List<Map<String,Object>> updateSignInTop(int signInType) throws Exception;
	

	/**
	 *更新老用户投资排行榜
	 */
	public List<Map<String,Object>> updateOldMemberTop() throws Exception;
	/**
	 *更新新用户投资排行榜
	 */
	public List<Map<String,Object>> updateNewMemberTop() throws Exception;
	/**
	 *更新邀请用户投资排行榜
	 */
	public List<Map<String,Object>> updateRecommMemberTop() throws Exception;
}
