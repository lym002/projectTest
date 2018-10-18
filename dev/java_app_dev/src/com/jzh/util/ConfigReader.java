package com.jzh.util;

import java.util.ResourceBundle;

public class ConfigReader {
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("jzh");
	
	/**
	 * @param propName
	 * @param key
	 */
	public static String getConfig(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "";
		}
    }
	
    public static int getInt(String key) {
		return Integer.parseInt(RESOURCE_BUNDLE.getString(key));
    }
}