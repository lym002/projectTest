package com.jsjf.common;

import com.sensorsdata.SensorsAnalytics;


/**
 * 神策分析埋点
 * @author cece
 *
 */
public class SensorsAnalyticsUtils {
    static final String SA_SERVER_URL = "http://47.97.125.172:8106/sa?project=default";//测试
    //static final String SA_SERVER_URL = "http://47.97.125.172:8106/sa?project=production";//正式
     // 1. 用户匿名访问网站
     //private String cookieId = "ABCDEF123456789"; // 用户未登录时，可以使用产品自己生成的cookieId来标注用户
     private static volatile SensorsAnalytics instance;
     private final static boolean SA_WRITE_DATA = true;
     
	 private SensorsAnalyticsUtils(){}
	 
	 public static SensorsAnalytics getInstance(){
	     if(null == instance){
	    	 synchronized(SensorsAnalytics.class){
	    		 if(null == instance){
		    		 instance = new SensorsAnalytics(new SensorsAnalytics.DebugConsumer
		                 (SA_SERVER_URL, SA_WRITE_DATA));
	    		 }
	    	 }
	     }
	 return instance;
	}
}
