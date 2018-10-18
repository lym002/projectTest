package com.jytpay.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MapHelper {

	private static final Log log = LogFactory.getLog(MapHelper.class);

	public static Map<String, String> signMap(Map<String, String> reqMap) {

		Map<String, String> maps = MapHelper.sortMapByKey(reqMap);
		StringBuffer reqTex = new StringBuffer();
		
		Iterator<Entry<String, String>> it = maps.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> ent = it.next();
			if ("key".equals(ent.getKey())){
				continue;
			}
			if (StringUtil.isNotBlank(ent.getValue()))
				reqTex.append(ent.getKey()).append("=").append(ent.getValue())
						.append("&");
		}
		reqTex.deleteCharAt(reqTex.length() - 1);
		
		log.info("网银发送数据：" + reqTex.toString());
		String key = reqMap.get("key");
		String sign = DigestUtils.sha256Hex(reqTex.toString() + key);
		log.info("key：" + key + " " + "签名：" + sign);
		maps.put("sign", sign);
		
		return maps;
	}
	
	/**
	 * 转换请求参数为MAP
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestMap(HttpServletRequest request) {
		Map<String, String[]> reqParams = request.getParameterMap();

		Map<String, String> paramMap = new HashMap<String, String>(); // 请求参数map

		Iterator<Entry<String, String[]>> it = reqParams.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> ent = it.next();

			if (ent.getValue().length == 1) {
				paramMap.put(ent.getKey(), ent.getValue()[0]);
			} else {
				log.error("获取参数" + ent.getKey() + "的值异常。ent.getValue().length="	+ ent.getValue().length);
			}
		}
		return paramMap;
	}
	
	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(
				new MapKeyComparator());
		sortMap.putAll(map);

		return sortMap;
	}

	// 比较器类
	private static class MapKeyComparator implements Comparator<String> {
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
	}

	/**
	 * 把一个map转成一个明文string字符串，例如：key1=value1&key2=value2...(去掉sign)
	 * 
	 * @param map
	 * @return
	 */
	public static String spliceMapToString(Map<String, String> map) {
		Map<String, String> sortMap = sortMapByKey(map);
		
		StringBuffer targetStr = new StringBuffer();
		Iterator<Entry<String, String>> it = sortMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> ent = it.next();
			if ("sign".equals(ent.getKey())) {
				continue;
			}
			if (StringUtil.isNotBlank(ent.getValue())) {
				targetStr.append(ent.getKey()).append("=")
						.append(ent.getValue()).append("&");
			}
		}

		targetStr.deleteCharAt(targetStr.length() - 1);
		return targetStr.toString();
	}

}
