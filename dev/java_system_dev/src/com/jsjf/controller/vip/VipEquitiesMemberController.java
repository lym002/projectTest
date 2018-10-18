package com.jsjf.controller.vip;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.service.vip.VipEquitiesMemberService;
import com.jsjf.service.vip.VipEquitiesService;

@Controller
@RequestMapping("/vipEquitiesMember")
public class VipEquitiesMemberController {
	
	@Autowired
	private VipEquitiesMemberService vipEquitiesMemberService;
	
	@Autowired
	private VipEquitiesService vipEquitiesService;
	
	@RequestMapping("/vipEquitiesMemberView")
	public String yearInvestView(Map<String,Object> model){
		List<VipEquities> qyList = vipEquitiesService.queryQy();
		List<VipInfo> list = vipEquitiesMemberService.queryVipLevel();
		model.put("level", list);
		model.put("qy", qyList);
		return "system/vip/bypVipEquitiesMember";
	}
	
	@RequestMapping("/vipEquitiesMemberList")
	@ResponseBody
	public PageInfo vipInfoList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = vipEquitiesMemberService.queryVipEquitiesList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	


}
