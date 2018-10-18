package com.jsjf.aop;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.druid.util.StringUtils;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.DbcontextHolder;
import com.jsjf.common.Utils;
import com.jsjf.model.system.SysLog;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.system.SysLogService;

public class SystemLogRecord {
	
	@Autowired
	private SysLogService logService;
	
	private static final Map<String,Object> filterMap = new HashMap<String,Object>();
	
	static {
		filterMap.put("redEnvelopeManager", "");
		filterMap.put("incrRestManager", "");
		filterMap.put("doubleCouponsManager", "");
		filterMap.put("tasteMoneyManager", "");
		filterMap.put("drChannelInfoUserList", "");

		filterMap.put("drChannelInfoUserList", "");
		filterMap.put("drChannelInfoOrderList", "");
		filterMap.put("drAllChannelInfoList", "");
		filterMap.put("drChannelInfoOrderUpdateList", "");
		filterMap.put("drChannelInfoOrderListFirst", "");
		filterMap.put("drChannelInfoOrderListNew", "");
		
		filterMap.put("companyFundsLogList", "");
		filterMap.put("companyFundsLogSum", "");
		filterMap.put("exportCompanyFundsLogList", "");
		filterMap.put("exportMemberCarry", "");
		filterMap.put("memberList", "");
		filterMap.put("queryUserByRole", "");

		filterMap.put("queryUserByRole", "");
		filterMap.put("selCustomerManagement", "");
		filterMap.put("getFundsRecord", "");
		filterMap.put("fundsRecordSum", "");
		filterMap.put("exportFundsRecord", "");

		filterMap.put("exportMemberCrushRecord", "");
		filterMap.put("memberFundsLogList", "");
		filterMap.put("memberFundsLogSum", "");
		filterMap.put("investOrderList", "");
		filterMap.put("exportInvestOrderInfo", "");
		filterMap.put("getInvestListForFuTou", "");
		
		filterMap.put("exportInvestOrderInfo", "");
		filterMap.put("JsMemberInfoList", "");
		filterMap.put("getInvestAmountSum", "");
		filterMap.put("regMemberInfoList", "");
		filterMap.put("drProductInvestList", "");
		
	}
	
	public void before(JoinPoint joinpoint) {
		  String methodName = joinpoint.getSignature().getName();
	      
	      if("initBinder".equals(methodName)) return;
	      
	      if(filter(methodName)){//走从库设置  
	    	  DbcontextHolder.setDbType(DbcontextHolder.DATA_SOURCE_SLAVE);//设置从库查询
	      }
	}

	public void after(JoinPoint joinpoint,Object returnVal) {
		//获取方法名  
        String methodName = joinpoint.getSignature().getName();
        String controllerName = joinpoint.getSignature().getDeclaringTypeName();
        if("initBinder".equals(methodName)|| controllerName.contains("controller.task") || controllerName.contains("SysDepositsController")){
        	return;
        }
        
        if( filter(methodName)){//清除 从库设置   ,操作日志走默认主库
        	DbcontextHolder.clearContext();
        }
        
        String params = "";
        Object[] o = joinpoint.getArgs();
        for(Object object : o){
        	params+= object+",";
        }
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();        
    	SysUsersVo user= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
        String ip = Utils.getIpAddr(request);
        if(user!=null && !StringUtils.isEmpty(methodName)){
        	SysLog log = new SysLog();
    		log.setOperator(user.getUserKy().intValue());
    		log.setAddtime(new Date());
    		log.setLog( joinpoint.getSignature().getDeclaringTypeName()+"."+methodName);
    		log.setParams(params);
    		log.setIp(ip);
    		logService.saveSysLog(log);
        }
	}
	
	public void afterThrowing(JoinPoint joinpoint,Throwable ex){
		  if(joinpoint.getSignature().getDeclaringTypeName().contains("controller.task")){
	        	return;
	       }
		  if( filter(joinpoint.getSignature().getName())){//清除 从库设置     ,操作日志走默认主库
	        	DbcontextHolder.clearContext();
	        }
		//获取方法名  
        String methodName = joinpoint.getSignature().getName();
        Object[] o = joinpoint.getArgs();
        String params = "";
        for(Object object : o){
        	params+=object+",";
        }
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();        
        SysUsersVo user= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
        String ip = Utils.getIpAddr(request);
		if(user!=null){
        	SysLog log = new SysLog();
    		log.setOperator(user.getUserKy().intValue());
    		log.setAddtime(new Date());
    		log.setLog(joinpoint.getSignature().getDeclaringTypeName()+"."+methodName);
    		log.setErrorMessage(ex.getMessage());
    		log.setParams(params);
    		log.setIp(ip);
    		logService.saveSysLog(log);
		}
	}
	/**
	 * 过来不需要处理的方法
	 * @param methodName
	 * @return
	 */
	public boolean filter(String methodName){
		if(methodName == null) return false;
		
		Object obj = filterMap.get(methodName);
		
		if(obj != null)return true;
		
		return false;
	}
	
	
}
