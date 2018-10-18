package com.sftpay.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.httpclient.NameValuePair;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public StringUtils() {
        super();
    }

    /**
     * 使用给定的 charset 将此 String 编码到 byte 序列，并将结果存储到新的 byte 数组。
     * 
     * @param content 字符串对象
     * 
     * @param charset 编码方式
     * 
     * @return 所得 byte 数组
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }

        try {
            return content.getBytes(charset);
        }
        catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Not support:" + charset, ex);
        }
    }

    /**
     * 构建键值对
     * @param key
     * @param value
     * @return
     * @throws UnsupportedEncodingException
     */
    public static NameValuePair createNameValuePair(String key, String value)
			throws UnsupportedEncodingException {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			return null;
		}
		return new NameValuePair(key, URLEncoder.encode(value, "utf-8"));
	}
    
    /**
     * 构建键值对
     * @param key
     * @param value
     * @return
     * @throws UnsupportedEncodingException
     */
    public static NameValuePair createNameValuePairSupportBlankValue(String key, String value)
			throws UnsupportedEncodingException {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return new NameValuePair(key, URLEncoder.encode(value, "utf-8"));
	}
    
    /**
     * 获取UUID
     */
    public static String getUUIDString() {
    	return UUID.randomUUID().toString().substring(0, 30);
	}
    
}