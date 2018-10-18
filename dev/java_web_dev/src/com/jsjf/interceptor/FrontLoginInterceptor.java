package com.jsjf.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jsjf.common.ConfigUtil;
import com.jsjf.model.member.DrMember;

public class FrontLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		DrMember m = (DrMember)request.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
		if(m != null){
			return true;
		}
		if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with")
				.equalsIgnoreCase("XMLHttpRequest")){//ajax请求
			 response.setHeader("sessionstatus", "timeout");//
			 return false;
		}
		return false;
	}
}
