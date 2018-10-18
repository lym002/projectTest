package com.jsjf.service.app.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.app.JsJiGuangPushDAO;
import com.jsjf.model.app.JsJiGuangPush;
import com.jsjf.model.app.JsPushApp;
import com.jsjf.model.app.JsPushDevice;
import com.jsjf.service.app.JsJiGuangPushService;
@Service
@Transactional
public class JsJiGuangPushServiceImpl implements JsJiGuangPushService {
	@Autowired
	JsJiGuangPushDAO jsJiGuangPushDAO;
	@Override
	public JsJiGuangPush selectObjectById(int id) {
	
		return jsJiGuangPushDAO.selectObjectById(id);
	}

	@Override
	public List<JsJiGuangPush> selectObjectList(Map<String, Object> map) {
		
		return jsJiGuangPushDAO.selectObjectList(map);
	}

	@Override
	public int selectObjectListCount(Map<String, Object> map) {
		
		return jsJiGuangPushDAO.selectObjectListCount(map);
	}

	@Override
	public String selectAudienceGroupConcat(Map<String, Object> map) {
		
		return jsJiGuangPushDAO.selectAudienceGroupConcat(map);
	}

	@Override
	public BaseResult setPushRegistrationId(String registrationId, Integer uid) {
		BaseResult result = new BaseResult();
		if(!Utils.strIsNull(registrationId) && !Utils.isBlank(uid)){
			Map<String,Object> map = jsJiGuangPushDAO.selectJsJiguangPushRegistrationId(registrationId);
			if(Utils.isObjectNotEmpty(map)){
				//update
				if(uid.intValue() != Integer.parseInt(map.get("uid").toString())){
					map.put("uid", uid);
					jsJiGuangPushDAO.updateJsJiguangPushRegistrationId(map);
				}
			}else{
				//insert
				map = new HashMap<String, Object>();
				map.put("uid", uid);
				map.put("registrationId", registrationId);
				jsJiGuangPushDAO.insertJsJiguangPushRegistrationId(map);
			}
			result.setMsg("成功");
			result.setSuccess(true);
		}else{
			result.setErrorMsg("参数错误");
		}
		return result;
	}
	
	@Override
	public BaseResult setPushDevice(Integer uid,String registrationId,String appKey) {
		BaseResult result = new BaseResult();
		if(!Utils.strIsNull(registrationId) && !Utils.isBlank(uid) && !Utils.strIsNull(appKey)){
			JsPushApp app = jsJiGuangPushDAO.selectJsPushApp(appKey);
			if(Utils.isObjectNotEmpty(app)){
				JsPushDevice device = jsJiGuangPushDAO.selectJsPushDevice(registrationId);
				if(Utils.isObjectNotEmpty(device)){
					//update
					if(uid.intValue() != device.getUid().intValue()){
						device.setUid(uid);
						jsJiGuangPushDAO.updateJsPushDevice(device);
					}
				}else{
					//insert
					device = new JsPushDevice();
					device.setDevice(registrationId);
					device.setUid(uid);
					device.setAppId(app.getId());
					jsJiGuangPushDAO.insertJsPushDevice(device);
				}
			}
			result.setMsg("成功");
			result.setSuccess(true);
		}else{
			result.setErrorMsg("参数错误");
		}
		return result;
	}

}
