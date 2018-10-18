package com.jsjf.dao.system;

import java.util.List;
import java.util.Map;

import com.jsjf.model.system.JsPushApp;
import com.jsjf.model.system.JsPushDevice;
import com.jsjf.model.system.JsPushLog;

public interface JsPushDeviceDAO {
	
	/**
	 * 插入推送日志
	 * @param map
	 */
	public void insertJsPushLog(Map<String,Object> map);
	
	/**
	 * 查询pushapp
	 * @param id
	 * @return
	 */
	public JsPushApp selectAppByKey(Integer id);
	/**
	 * 查询pushapp
	 * @param id
	 * @return
	 */
	public List<JsPushApp> selectAppList(Map<String,Object> map);
	/**
	 * 查询 token
	 * @param uid
	 * @param appId
	 * @return
	 */
	public List<String> selectPushDevice(Map<String,Object> map);
	/**
	 * 查询 DeviceId
	 * @param uid
	 * @param appId
	 * @return
	 */
	public List<Integer> selectPushDeviceId(Map<String,Object> map);
}
