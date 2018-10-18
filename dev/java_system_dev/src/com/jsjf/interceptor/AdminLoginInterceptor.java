package com.jsjf.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jsjf.common.ConfigUtil;

public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
	

	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	boolean result = false;
    	HttpSession session = request.getSession();
    	if(session.getAttribute(ConfigUtil.ADMIN_LOGIN_USER) == null){
    		if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有，x-requested-with  
    			response.setHeader("sessionstatus", "timeout");//在响应头设置session状态 
                result = false;
    		}else{//非ajax 请求
    			 result = false;
    			 response.sendRedirect("/user/doLoginUser.do"); 
    		}
    	}else{
    		result = true;
    	}
    	return result ;
    }
}
