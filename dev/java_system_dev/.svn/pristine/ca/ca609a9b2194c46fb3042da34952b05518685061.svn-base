package com.jsjf.controller.activity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.service.activity.JsGratitudeBlessingService;



@Controller
@RequestMapping("/gratitudeBlessing")
public class JsGratitudeBlessingController {

	@Autowired JsGratitudeBlessingService jsGratitudeBlessingService;
	
	/**
	 * 进入感恩回馈祝福审页面
	 * @return
	 */
	@RequestMapping("/toGratitudeBlessingList")
	public String toGratitudeBlessingList(HttpServletRequest req,Map<String, Object> model){
		return "system/activity/gratitudeBlessingList";	
	}
	
	/**
	 * 获取感恩回馈祝福审核列表
	 * @param mobilePhone
	 * @param status
	 * @param startaddtime
	 * @param endaddtime
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/selectGratitudeBlessing")
	@ResponseBody
	public PageInfo selectGratitudeBlessing(String mobilePhone,String status,String startaddtime,String endaddtime,
			Integer page, Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo info = new PageInfo(page, rows);
		Map<String, Object> map=new HashMap<>();
		map.put("mobilePhone", mobilePhone);
		map.put("status", status);
		map.put("startaddtime", startaddtime);
		map.put("endaddtime", endaddtime);
		BaseResult br = jsGratitudeBlessingService.selectGratitudeBlessing(info,map);
		return (PageInfo) br.getMap().get("page");
	}
	
	/**
	 * 审核
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateGratitudeBlessing")
	@ResponseBody
	public String updateGratitudeBlessing(Integer status,Integer id) {
		Map<String, Object> map=new HashMap<>();
		map.put("id", id);
		map.put("status", status);
		jsGratitudeBlessingService.updateGratitudeBlessing(map);
		return "success";
	}
	
	
}
