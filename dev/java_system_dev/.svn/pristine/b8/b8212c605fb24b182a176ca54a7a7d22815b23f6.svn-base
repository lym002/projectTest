package com.jsjf.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
//private static ApplicationContext context;
//	
//	static{
//		context = new ClassPathXmlApplicationContext(new String[]{""});
//	}
//
//	public static Object getBean(String beanName){
//		return context.getBean(beanName);
//	}
//	
//	public static String getText(String key){
//		return context.getMessage(key, null, Locale.getDefault());
//	}
//	
//	public static String getText(String key,Object[] param){
//		return context.getMessage(key, param, Locale.getDefault()	);
//	}
//	
	public static String getProperties(String key) throws IOException{
		Properties pro = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/informContent.properties");
		pro.load(in);
		in.close();
		InputStream in1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/dictionary.properties");
		pro.load(in1);
		in1.close();
		if(key == null)
			return "";
		else
			return pro.getProperty(key);
	}
	
}
