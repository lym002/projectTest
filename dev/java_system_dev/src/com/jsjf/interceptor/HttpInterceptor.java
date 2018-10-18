package com.jsjf.interceptor;

import java.sql.SQLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class HttpInterceptor {
	
//	@Autowired
//	private SysLFSLogService sysLFSLogService;
//	private SysLFSLog sysLfsLog;
//	
	@Pointcut("execution(* com.jsjf.service.httpclient.impl.HttpClientServiceImpl.*(..))")  
	public void anyMethod() {}//声明一个切入点
	
	
	/**
	 * 前置通知，方法调用前执行
	 */
	@Before("anyMethod()")
	public void before(JoinPoint jp) {
//		Object[] obj= jp.getArgs();
//		if (obj.length <= 0) {  
//			System.out.println("====" + jp.getSignature().getName() + "方法没有参数");  
//        } else {  
//            for (int i = 0; i < obj.length; i++) {  
//            	System.out.println("  " + (i + 1) + "：" + obj[i]);  
//            }  
//        } 
//		sysLfsLog = new SysLFSLog();
//		sysLfsLog.setUrl(obj[0].toString());
//		sysLfsLog.setParam(obj[1].toString());
//		log.info("请求[" + obj[0] + "],请求参数["+obj[1]+"]");
	}
	
	/**
	 * 后置【try】通知
	 * @param result 请求返回结果
	 * @throws SQLException 
	 */
	@AfterReturning(pointcut="anyMethod()",returning="result")
	public void doAfterReturning(String result){
//		log.info("请求结果:"+ result);
//		sysLfsLog.setResult(result);
//		sysLfsLog.setAddtime(new Date());
//		sysLfsLog.setStatus(JSONObject.parseObject(result).getString("ret_code"));
//		sysLFSLogService.insert(sysLfsLog);
		
	}
	
	/**
	 * 后置【catch】通知
	 */
	@AfterThrowing(pointcut="anyMethod()",throwing="e")
	public void doAfterThrowing(Exception e) {
		System.out.println("例外通知:"+ e);
	}
	
	/**
	 * 后置【finally】通知
	 */
	@After("anyMethod()")
	public void doAfter() {
		// TODO
	}
	
	/**
	 * 正常运行完方法后执行
	 */
	@Around("anyMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		return result;
	}

	
	
}
