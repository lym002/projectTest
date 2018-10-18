package com.esign.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 
* @Description: 公共工具辅助类
* @Team: 公有云技术支持小组
* @Author: 天云小生
* @Date: 2017年11月19日
 */
public class ToolsHelper {
    /***
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
	if (null == str || str.length() == 0) {
	    return true;
	}
	return false;
    }
    /***
     * 判断对象是否为空
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
	if (null == obj) {
	    return true;
	}
	return false;
    }
    
    /* 
     * 将普通时间转换为时间戳
     */    
    public static String dateToStamp(String dateTime) {
        String res = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
	try {
	    date = simpleDateFormat.parse(dateTime);
	    long ts = date.getTime();
	        res = String.valueOf(ts);
	} catch (ParseException e) {
	    e.printStackTrace();
	}        
        return res;
    }
    
    /* 
     * 将时间戳转换为普通时间
     */
    public static String stampToDate(String timestamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timestamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    
    /* 
     * 将时间戳转换为字符串
     */
    public static String stampToString(long timestamp){
        String res = String.valueOf(System.currentTimeMillis());
        return res;
    }
    

}
