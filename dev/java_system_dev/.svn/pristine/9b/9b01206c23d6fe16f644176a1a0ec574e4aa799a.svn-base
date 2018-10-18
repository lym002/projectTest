package com.jsjf.controller.integral;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.PageInfo;
import com.jsjf.service.integral.UserDetailIntegralService;

@Controller
@RequestMapping("/detailintegral")
public class UserDetailIntegralController {
	
	private Logger log = Logger.getLogger(UserDetailIntegralController.class);
	
	@Autowired
	private UserDetailIntegralService userDetailIntegralService;
	
	/**
	 * 积分规则
	 * @return
	 */
	@RequestMapping("/detailintegralView")
	public String detailintegralView(){
		return "/system/integral/detailintegralList";
	}
	
	@RequestMapping("/detailintegralList")
	@ResponseBody
	public PageInfo detailintegralList(@RequestParam Map<String,Object> param,HttpServletRequest req,
			Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		try {
			userDetailIntegralService.queryDetailintegralList(info, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	
	/**
	 * 积分管理
	 * @return
	 */
	@RequestMapping("/integralManageView")
	public String integralManageView(){
		return "/system/integral/integralManageView";
	}
	
	@RequestMapping("/integralManageList")
	@ResponseBody
	public PageInfo integralManageList(@RequestParam Map<String,Object> param,HttpServletRequest req,
			Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		try {
			userDetailIntegralService.queryIntegralManageList(info, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
}
