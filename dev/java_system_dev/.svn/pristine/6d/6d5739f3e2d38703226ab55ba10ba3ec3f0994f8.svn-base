/**
 * 
 */
package com.jsjf.controller.activity;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.model.activity.DrRecommendedSettings;
import com.jsjf.model.activity.DrRecommendedSettingsDetail;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.DrRecommendedSettingsService;


/**
 * @author Gerald
 *
 */
@Controller
@RequestMapping("/recommendedSettings")
public class DrRecommendedSettingController {
	private Logger log =Logger.getLogger(DrRecommendedSettingController.class);
	
	@Autowired
	private DrRecommendedSettingsService drRecommendedSettingsService;
	
	@RequestMapping("/toRecommendedSettingsList")
	public String toAgentParameterList(HttpServletRequest req){
		return "/system/activity/recommendedSettingsList";
	}
	
	@RequestMapping("/recommendedSettingsList")
	@ResponseBody
	public PageInfo recommendedSettingsList(DrRecommendedSettings recommendedSettings,
			Integer page, Integer rows, HttpServletRequest request) {
		PageInfo info = new PageInfo(page, rows);
		try {
			info = drRecommendedSettingsService.getDrRecommendedSettingsList(info, recommendedSettings);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	
	
	@RequestMapping("/toSettingsDetailsList")
	public String toSettingsDetailsList(HttpServletRequest req,Integer id,Integer modality,Map<String,Object> model){
		DrRecommendedSettings settings = new DrRecommendedSettings();
		try {
			settings = drRecommendedSettingsService.getRecommendedSettingsById(id);
			model.put("norm", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("norm")));
			model.put("modality", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("modality")));
			model.put("standard", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("standard")));
			Map<Integer,String> map =ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType"));
			Map<String,String> productMap = new HashMap<String, String>();
			for (Integer key : map.keySet()) {
				productMap.put(key.toString(), map.get(key));
			}
			model.put("productType", productMap);
			model.put("settings", settings);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/system/activity/settingsDetails";
	}
	
	@RequestMapping("/settingsDetailsList")
	@ResponseBody
	public PageInfo settingsDetailsList(Integer rid,Integer page,Integer rows,HttpServletRequest request){
		PageInfo info = new PageInfo();
		try {
			info = drRecommendedSettingsService.getSettingDetailsList(info, rid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	
	@RequestMapping("/toModify")
	public String toModify(Integer id,Map<String,Object> model){
		DrRecommendedSettings settings = new DrRecommendedSettings();
		try {
			settings = drRecommendedSettingsService.getRecommendedSettingsById(id);
			model.put("norm", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("norm")));
			model.put("modality", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("modality")));
			model.put("standard", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("standard")));
			Map<Integer,String> map =ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productType"));
			Map<String,String> productMap = new HashMap<String, String>();
			for (Integer key : map.keySet()) {
				productMap.put(key.toString(), map.get(key));
			}
			model.put("productType", productMap);
			model.put("settings", settings);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/activity/settingsModify";
	}
	
	@RequestMapping("/modifySettings")
	@ResponseBody
	public BaseResult modifySettings(DrRecommendedSettings drRecommendedSettings,
			HttpServletRequest request){
		BaseResult result = new BaseResult();
		try {
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			String details = drRecommendedSettings.getDetails();
			List<DrRecommendedSettingsDetail> list = JSONObject.parseArray(details,DrRecommendedSettingsDetail.class);
			drRecommendedSettingsService.updateStatus(drRecommendedSettings.getId());
			drRecommendedSettings.setDetailList(list);
			drRecommendedSettings.setId(null);
			drRecommendedSettingsService.insertDrRecommendedSetting(drRecommendedSettings, usersVo);
			result.setSuccess(true);
			result.setMsg("修改成功!");
		} catch (Exception e) {
			result.setErrorMsg("系统错误!");
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 表单提交日期绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
