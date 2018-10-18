package com.jsjf.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.jsjf.model.member.DrFeedback;
import com.jsjf.service.member.DrFeedbackService;
import com.jsjf.service.system.SysArticleService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Controller
@RequestMapping("/system")
public class SystemController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrFeedbackService drFeedbackService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	
	/**
	 * //系统维护
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/systemMaintenance", method = RequestMethod.POST)
	@ResponseBody
	public String systemMaintenance(HttpServletRequest req){
		BaseResult br = new BaseResult();
		
		br.setErrorCode("XTWH");
		
		return JSON.toJSONString(br);
	}
	@RequestMapping(value="/version", method = RequestMethod.POST)
	@ResponseBody
	public String version(HttpServletRequest req){
		BaseResult br = new BaseResult();
		String channel = req.getParameter("channel");
		try {
			String version = redisClientTemplate.getProperties("version_"+channel);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("version", version);
			if("2".equals(channel)){//android需要版本下载链接
				map.put("link_url", redisClientTemplate.getProperties("link_url"));
			}
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("版本获取失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}
	
	@RequestMapping(value="/feedback", method = RequestMethod.POST)
	@ResponseBody
	public String feedback(HttpServletRequest req, Integer uid, String content, String contactInformation){
		BaseResult br = new BaseResult();
		String channel = req.getParameter("channel");
		try {
			DrFeedback fb = new DrFeedback(uid, Integer.parseInt(channel), contactInformation, content, new Date(), 0);
			drFeedbackService.insertFeedback(fb);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("版本获取失败", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}	
}
