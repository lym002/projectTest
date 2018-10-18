package com.jsjf.controller.partner.yrt;

import com.jsjf.common.SecurityUtils;

/**
 * 易瑞特
 * 
 * @author Administrator
 *
 */
public class YrtBase {
	private static String key = "udj83kd93i";// 币优铺提供的key，用于MD5加密
	public static final String TOFROM = "yrt";
	public static class innerClass {
		private static YrtBase yrtBase = new YrtBase();
	}

	public static YrtBase getInstance() {
		return innerClass.yrtBase;
	}
	/**
	 * 验证签名
	 * @param startday
	 * @param endday
	 * @param sign
	 * @return
	 */
	public boolean validateSign(String startday, String endday, String sign){
		  String signKey = SecurityUtils.MD5(startday+endday+key);
		  if(sign.equals(signKey)){
			  return true;
		  }else{
			  return false;
		  }
	}
	public static void main(String[] args) {
		 String signKey = SecurityUtils.MD5("2016-09-22 09:50:28"+"2016-09-29 09:50:28"+"yysr3r2h5i");
		 System.out.println(signKey);
	}
}
