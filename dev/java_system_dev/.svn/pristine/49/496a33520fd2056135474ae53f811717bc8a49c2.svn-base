package com.jzh.util;

import java.lang.reflect.InvocationTargetException;

import net.sf.json.JSONObject;

public class StringUtil {
	
	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.trim().length() > 0));
	}

	public static boolean isEmpty(String aStr) {
		return ((aStr == null) || (aStr.trim().length() == 0));
	}
	
	public static String trim(String str) {
		return StringUtil.isEmpty(str) ? "" : str.trim();
    }
	
	/**
     * 接口请求/响应数据封装
     *
     * @param object 数据对象
     * @return String JSON格式，样例：
     * {
     * "message" :
     * {
     * "ver" : "1.0",
     * "icd" : "101001",
     * "mchnt_cd" : "0000000F0000000"
     * },
     * "signature" : "fdeymdigDV46CQpYYtyOAczQi9xN5MrtdTRlCGNAN8H4Wjyd7XoehnUQgGZEGvrU1a4+Q2Alidu7zmc24v6NzYF+SqUJPUXyl05Lc3U1oqjsERJ96zkA+WbmG4oLZcoVn2uhBWm3rlkZ9YigzFNEUSUmLkwBIwk2AIPG3dVCR6s="
     * }
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
     */
    public static String encaJSONstring(Object object){
        JSONObject json = new JSONObject();
        
        JSONObject message = JSONObject.fromObject(object);
		System.out.println("存管(页面)请求明文======" + message.toString());
		if(message != null) {
        	json.element("message", message.toString());
            try {
				json.element("signature", RequestData.createSignValueForReg(object));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
        }

        return json.toString();
    }
}