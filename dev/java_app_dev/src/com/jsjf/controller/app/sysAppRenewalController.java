package com.jsjf.controller.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.model.app.SysAppRenewal;
import com.jsjf.service.app.JsJiGuangPushService;
import com.jsjf.service.app.SysAppRenewalService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Controller
@RequestMapping("/app")
public class sysAppRenewalController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SysAppRenewalService sysAppRenewalService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private JsJiGuangPushService jsJiGuangPushService;
	
	
	/**
	 * 获取极光相关对象
	 * @param req
	 * @param uid
	 * @param type
	 * @return
	 */
	@RequestMapping("/selectPushAudience")
	@ResponseBody
	public String selectPushAudience(HttpServletRequest req,Integer uid,Integer type){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(!Utils.isBlank(uid) && !Utils.isBlank(type)){
				map.put("uid", uid);
				map.put("type", type);
				String audience = jsJiGuangPushService.selectAudienceGroupConcat(map);
				map.put("code", Utils.strIsNull(audience)?"":audience);
				br.setMap(map);
				br.setSuccess(true);
			}else{
				br.setErrorCode("1000");
			}
		} catch (Exception e) {
			log.error("失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	
	
	/**
	 * 获取极光相关对象
	 * @param req
	 * @param uid
	 * @param type
	 * @return
	 */
	@RequestMapping("/setPushRegistrationId")
	@ResponseBody
	public String setPushRegistrationId(HttpServletRequest req,Integer uid,String registrationId,String appKey){
		BaseResult br = new BaseResult();
		try {
			br = jsJiGuangPushService.setPushDevice(uid, registrationId, appKey);
		} catch (Exception e) {
			log.error("系统失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}

	
	@RequestMapping(value = "/renewal", method = RequestMethod.POST)
	@ResponseBody
	public String renewal(HttpServletRequest req){
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String channel = req.getParameter("channel");
			map.put("containers", channel);
			map.put("status", 1);
			SysAppRenewal sysAppRenewal = sysAppRenewalService.getAppRenewalList(map);
			
			map.clear();
			map.put("sysAppRenewal", sysAppRenewal);
			map.put("maxVersion", redisClientTemplate.getProperties("version_"+channel));
			String url = redisClientTemplate.getProperties("isMaintenance_"+channel);
			map.put("isMaintenance", url ==null ? "" : url);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("获取更新公告失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
}
