package com.jsjf.controller.vip;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.BypCommodityBean;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.service.activity.PrizeManageService;
import com.jsjf.service.vip.VipEquitiesService;
import com.jsjf.service.vip.VipInfoService;

@Controller
@RequestMapping("/vipInfo")
public class VipInfoController {
	
	@Autowired
	private VipInfoService vipInfoService;
	
	@Autowired
	private PrizeManageService prizeManageService;
	
	@Autowired
	private VipEquitiesService vipEquitiesService;

	@RequestMapping("/vipInfoView")
	public String yearInvestView(Map<String,Object> model){
		List<BypCommodityBean> list = prizeManageService.queryHb();
		List<VipEquities> qyList = vipEquitiesService.queryQy();
		model.put("qy", qyList);
		model.put("hb", list);
		return "system/vip/bypVipInfo";
	}
	
	@RequestMapping("/vipInfoList")
	@ResponseBody
	public PageInfo vipInfoList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = vipInfoService.queryVipInfoList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("/addVipinfo")
	@ResponseBody
	public BaseResult addVipinfo(HttpServletRequest request,VipInfo vipInfoBean){
		BaseResult br = new BaseResult();
		br = vipInfoService.addVipinfo(vipInfoBean);
		return br;
	}
	
	@RequestMapping("/updateVipInfo")
	@ResponseBody
	public BaseResult updateVipInfo(VipInfo vipInfoBean){
		BaseResult br = new BaseResult();
		br = vipInfoService.updateVipInfo(vipInfoBean);
		return br;
	}
	
	
	
}
