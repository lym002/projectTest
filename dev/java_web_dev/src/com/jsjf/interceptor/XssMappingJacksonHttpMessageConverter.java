package com.jsjf.interceptor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.type.JavaType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.alibaba.fastjson.JSON;

//默认会过滤所有请求路径,只会过滤String类型的字段，需要其他类型过滤的请自行完善
public class XssMappingJacksonHttpMessageConverter extends
		MappingJacksonHttpMessageConverter implements
		MessageConverterHandler<Object, Type> {

	// 重写该方法，我们只需要加上Object tempObj = this.process( obj, type, inputMessage );
	// 并返回tempObj,process方法里面我们过滤白名单和进行xss处理
	@Override
	public Object read(Type type, Class<?> contextClass,HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {

		JavaType javaType = getJavaType((java.lang.reflect.Type) type, contextClass);

		Object obj = readJavaType(javaType, inputMessage);

		Object tempObj = this.readAfter(obj, type);// 只对Map 对象里String 的 value 过滤

		return tempObj;
	}

	// 这个就是父类的readJavaType方法，由于父类该方法是private的，所以我们copy一个用
	private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
		try {
			return super.getObjectMapper().readValue(inputMessage.getBody(),
					javaType);
		} catch (IOException ex) {
			throw new HttpMessageNotReadableException("Could not read JSON: "
					+ ex.getMessage(), ex);
		}
	}

	// 最重要的一步，进行xss过滤
	@Override
	public Object readAfter(Object obj, Type type) {
		try {
			
			if (type.toString().equals("java.util.Map<java.lang.String, java.lang.Object>")) {
				
				Map<String, Object> map = (Map<String, Object>) obj;
				Set<Entry<String, Object>> entries = map.entrySet();
				
				for (Entry<String, Object> entry : entries) {
//					System.out.println(entry.getKey() + ":" + entry.getValue());
					Object o = map.get(entry.getKey());
					if (o instanceof String)
						map.put(entry.getKey(), cleanXSS(o.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("BACK_ERROR," + this.getClass().getCanonicalName()
					+ ",XSS处理失败,obj=" + JSON.toJSONString(obj) + ",javaType="
					+ JSON.toJSONString(type) + ",ERROR=", e);
			return obj;
		}

		return obj;
	}

	/**
	 * 处理字符转义
	 * 
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value) {
		if (value == null || "".equals(value)) {
			return value;
		}
		// You'll need to remove the spaces from the html entities below
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
}