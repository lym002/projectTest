package com.jsjf.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrFeedback;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.member.DrFeedbackService;
import com.jsjf.service.system.SysArticleService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Controller
@RequestMapping("/system")
public class SystemController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DrFeedbackService drFeedbackService;

	/**
	 * //系统维护
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/systemMaintenance", method = RequestMethod.POST)
	@ResponseBody
	public String systemMaintenance(HttpServletRequest req,@RequestBody Map<String, Object> param){
		BaseResult br = new BaseResult();
		
		br.setErrorCode("XTWH");
		
		return JSON.toJSONString(br);
	}
	
	@RequestMapping(value="/feedback", method = RequestMethod.POST)
	@ResponseBody
	public String feedback(HttpServletRequest req,@RequestBody Map<String, Object> param){
		DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		BaseResult br = new BaseResult();
		try {
			if(Utils.isObjectNotEmpty(param.get("content"))){
				DrFeedback fb = new DrFeedback(m.getUid(),(Integer)param.get("channel"), (String)param.get("contactInformation"), (String)param.get("content"), new Date(), 0);
				drFeedbackService.insertFeedback(fb);
				br.setSuccess(true);
			}else{				
				br.setErrorCode("0001");
				br.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("系统异常", e);
			br.setErrorCode("9999");
			br.setSuccess(false);
		}
		return JSON.toJSONString(br);
	}	
}
