package com.jsjf.service.app;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.app.JsJiGuangPush;

public interface JsJiGuangPushService {
	
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
	 * 设置RegistrationId
	 * @param RegistrationId
	 * @param uid
	 * @return
	 */
	public BaseResult setPushRegistrationId(String registrationId,Integer uid);
	/**
	 * 设置device
	 * @param RegistrationId
	 * @param uid
	 * @return
	 */
	public BaseResult setPushDevice(Integer uid,String registrationId,String appKey);

}
