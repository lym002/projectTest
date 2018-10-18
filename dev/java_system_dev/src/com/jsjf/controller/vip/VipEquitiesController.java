package com.jsjf.controller.vip;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.service.vip.VipEquitiesService;

@Controller
@RequestMapping("/vipEquities")
public class VipEquitiesController {
	
	@Autowired
	private VipEquitiesService vipEquitiesService;
	
	@RequestMapping("/vipEquitiesView")
	public String yearInvestView(Map<String,Object> model){
		return "system/vip/bypVipEquities";
	}
	
	@RequestMapping("/vipEquitiesList")
	@ResponseBody
	public PageInfo vipInfoList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = vipEquitiesService.queryVipEquitiesList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("/addVipEquities")
	@ResponseBody
	public BaseResult addVipinfo(HttpServletRequest request,VipEquities bean){
		BaseResult br = new BaseResult();
		br = vipEquitiesService.addVipEquities(bean);
		return br;
	}
	
	@RequestMapping("/updateVipEquities")
	@ResponseBody
	public BaseResult updateVipInfo(VipEquities bean){
		BaseResult br = new BaseResult();
		br = vipEquitiesService.updateVipEquities(bean);
		return br;
	}

}
