package com.jsjf.controller.integral;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.integral.IntegralSourceBean;
import com.jsjf.service.integral.IntegralSourceService;


@Controller
@RequestMapping("/integral")
public class IntegralSourceController {
	private Logger log = Logger.getLogger(IntegralSourceController.class);
	
	@Autowired
	private IntegralSourceService integralSourceService;
	
	@RequestMapping("integralSource")
	public String integralSource(){
		return "/system/integral/integralSourceList";
	}
	
	@RequestMapping("integralSourceList")
	@ResponseBody
	public PageInfo integralSourceList(HttpServletRequest req,IntegralSourceBean integralSourceBean,
			Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		try {
			integralSourceService.queryIntegralSourceList(info, integralSourceBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping("updateIntegralSource")
	@ResponseBody
	public BaseResult updateIntegralSource(HttpServletRequest request,IntegralSourceBean integralSourceBean){
		BaseResult result = new BaseResult();
		try {
			integralSourceService.updateIntegralSource(integralSourceBean);
			result.setMsg("修改成功!");
			result.setSuccess(true);
			
		} catch (Exception e) {
			result.setMsg("系统错误!");
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		}
		return result;
	}
}
