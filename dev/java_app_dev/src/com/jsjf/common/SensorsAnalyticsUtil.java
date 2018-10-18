package com.jsjf.common;

import com.SensorsAnalytics.SensorsAnalytics;

public class SensorsAnalyticsUtil {
    static final String SA_SERVER_URL = "http://47.97.125.172:8106/sa?project=default";//测试
    //static final String SA_SERVER_URL = "http://47.97.125.172:8106/sa?project=production";//正式
            // 1. 用户匿名访问网站
            String cookieId = "ABCDEF123456789"; // 用户未登录时，可以使用产品自己生成的cookieId来标注用户
            private static SensorsAnalytics instance;
    private SensorsAnalyticsUtil(){}
        public static synchronized SensorsAnalytics getInstance(){
            if(instance == null){
                instance = new SensorsAnalytics(new SensorsAnalytics.DebugConsumer
                        (SA_SERVER_URL, true));
        }
        return instance;
    }
}
