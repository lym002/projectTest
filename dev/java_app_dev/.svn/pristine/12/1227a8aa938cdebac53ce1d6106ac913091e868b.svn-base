package com.jzh.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.jzh.util.ConfigReader;
import com.jzh.util.StringUtil;

public class WebUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static String sendHttp(String url, Object parameters) throws Exception  {
		String outStr="";
		try {
			logger.info("存管直连-请求报文:"+JSONObject.fromObject(parameters).toString());
			String charSet="UTF-8";
			String timeOut = ConfigReader.getConfig("TimeOut");//自行配置
			outStr = HttpClientHelper.doHttp(url,charSet,parameters, timeOut);
			if(outStr==null){
				throw new Exception("请求接口失败!");
			}
			logger.info("存管直连-返回报文:"+outStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("请求接口失败!");
		}
		return outStr;
	}
}