package com.jsjf.controller.activity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.model.activity.JsActivityReward;
import com.jsjf.service.activity.JsActivityRewardService;

/**
 * 双旦活动管理
 * @author cece
 *
 */
@Controller
@RequestMapping("/activityreward")
public class JsActivityRewardController {

	private Logger log = Logger.getLogger(JsActivityRewardController.class);
	
	@Autowired
	private JsActivityRewardService jsActivityRewardService;
	
	@RequestMapping("/queryActivityRewardList")
	@ResponseBody
	public PageInfo queryJsActivityRewardList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
			if(page == null){
				page = PageInfo.DEFAULT_PAGE_ON;
			}
			if(rows == null){
				rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
			}
			PageInfo pi = new PageInfo(page,rows);
			BaseResult result = jsActivityRewardService.queryJsActivityRewardList(param, pi);
			return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping("/activityRewardList")
	public String jsActivityRewardList(Map<String,Object> model){
		List<JsActivityReward> list = jsActivityRewardService.queryCouponIdList();
		model.put("coupon",list);
		return "system/activity/jsActivityRewardList";
	}
	
	@RequestMapping("/addActivityReward")
	@ResponseBody
	public BaseResult addActivityReward(JsActivityReward jsActivityReward){
		BaseResult result = jsActivityRewardService.addActivityReward(jsActivityReward);
		return result;
	}
	
	@RequestMapping("/updateActivityReward")
	@ResponseBody
	public BaseResult updateActivityReward(JsActivityReward jsActivityReward){
		BaseResult result = jsActivityRewardService.updateActivityReward(jsActivityReward);
		return result;
	}
	
	@RequestMapping("/deleteActivityReward")
	@ResponseBody
	public BaseResult deleteActivityReward(@RequestParam Integer id){
		BaseResult result = jsActivityRewardService.deleteActivityReward(id);
		return result;
	}
	
	
}
