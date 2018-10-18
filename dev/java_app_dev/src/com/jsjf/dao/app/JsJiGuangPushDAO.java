package com.jsjf.dao.app;

import java.util.List;
import java.util.Map;

import com.jsjf.model.app.JsJiGuangPush;
import com.jsjf.model.app.JsPushApp;
import com.jsjf.model.app.JsPushDevice;

public interface JsJiGuangPushDAO {
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public JsJiGuangPush selectObjectById(int id);
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public List<JsJiGuangPush> selectObjectList(Map<String,Object> map);
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public int selectObjectListCount(Map<String,Object> map);
	/**
	 * 查询  Group_concat(code)
	 */
	public String selectAudienceGroupConcat(Map<String,Object> map);
	/**
	 * 查询 RegistrationId
	 * @param map
	 * @return
	 */
	public Map<String,Object> selectJsJiguangPushRegistrationId(String registrationId);
	/**
	 * 修改 RegistrationId
	 * @param map
	 * @return
	 */
	public void updateJsJiguangPushRegistrationId(Map<String,Object> map);
	/**
	 * 添加 RegistrationId
	 * @param map
	 * @return
	 */
	public void insertJsJiguangPushRegistrationId(Map<String,Object> map);
	
	
	/**
	 * 查询app
	 * @param appKey
	 * @return
	 */
	public JsPushApp selectJsPushApp(String appKey);
	/**
	 * 查询Device
	 * @param appKey
	 * @return
	 */
	public JsPushDevice selectJsPushDevice(String device);
	/**
	 * 修改Device
	 * @param appKey
	 * @return
	 */
	public void updateJsPushDevice(JsPushDevice device);
	/**
	 * 修改Device
	 * @param appKey
	 * @return
	 */
	public void insertJsPushDevice(JsPushDevice device);
	
	
	
	
}
