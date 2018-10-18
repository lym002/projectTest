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
import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.model.vip.VipMemberInfo;
import com.jsjf.service.vip.VipEquitiesMemberService;
import com.jsjf.service.vip.VipEquitiesService;
import com.jsjf.service.vip.VipMemberInfoService;

@Controller
@RequestMapping("/vipMemberInfo")
public class VipMemberInfoController {
	
	@Autowired
	private VipEquitiesMemberService vipEquitiesMemberService;
	
	@Autowired
	private VipMemberInfoService vipMemberInfoService;
	
	@RequestMapping("/vipMemberInfoView")
	public String yearInvestView(Map<String,Object> model){
		List<VipInfo> list = vipEquitiesMemberService.queryVipLevel();
		model.put("level", list);
		return "system/vip/bypVipMemberInfo";
	}
	
	@RequestMapping("/vipMemberInfoList")
	@ResponseBody
	public PageInfo vipInfoList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = vipMemberInfoService.queryVipMemberInfoList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("/addVipMemberInfo")
	@ResponseBody
	public BaseResult addVipMemberInfo(VipMemberInfo bean){
		BaseResult br = new BaseResult();
		br = vipMemberInfoService.addVipMemberInfo(bean);
		return br;
	}
	
	@RequestMapping("/updateVipMemberInfo")
	@ResponseBody
	public BaseResult updateVipMemberInfo(VipMemberInfo bean){
		BaseResult br = new BaseResult();
		br = vipMemberInfoService.updateVipMemberInfo(bean);
		return br;
	}

}
