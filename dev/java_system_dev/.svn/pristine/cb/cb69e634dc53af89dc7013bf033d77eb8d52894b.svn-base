package com.jsjf.controller.partner;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.CSRFTokenManager;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.service.partner.WdzjService;

/**
 * 网贷之家数据接口
 * @author DELL
 *
 */
@RequestMapping("/wdzj")
@Controller
public class WdzjController {
	private static final Logger log = Logger.getLogger(WdzjController.class);
	
	public static final String user = "wdzj";
	public static final String pwd = "wdzj123456";
	public static String session_token = "";
	
	@Autowired
	private WdzjService wdzjService;
	
	@RequestMapping(value="/data",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String data(HttpServletRequest req, String date, Integer pageSize, Integer page, String token){
		log.info(Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"抓取"+date+"数据");
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(token)&&!"".equals(session_token)&&token.equals(session_token)){
			Long time = Long.valueOf(session_token.split("byp")[1]);
			Date d = new Date();
			if(d.getTime()-time<=1*60*60*1000){
				PageInfo pi = new PageInfo(page, pageSize);
				param.put("limit", pi.getPageInfo().getLimit());
				param.put("offset", pi.getPageInfo().getOffset());
				param.put("pageSize", pageSize);
				param.put("page", page);
				param.put("fullDate", date);
				param = wdzjService.getData(param);
			}else{
				session_token = "";
			}
		}
		log.info("网贷之家返回数据："+JSON.toJSONString(param));
		return JSON.toJSONString(param);
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(HttpServletRequest req, String username, String password){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> result = new HashMap<String, String>();
		if(user.equals(username) && pwd.equals(password)){
			result.put("token", CSRFTokenManager.getTokenForSession(req.getSession())+"byp"+(new Date()).getTime());
			session_token = result.get("token");
			map.put("data", result);
		}else{
			map.put("data", "登录失败");
		}
		return JSON.toJSONString(map);
	}
	
	
}
