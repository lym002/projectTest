package com.zcdj.util;

import com.jsjf.common.ConfigUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubmitUtil {

	public static String buildSubmit(Map<String, Object> para, String url) throws Exception {
		// 生成签名
		String mysign = Md5Utils.md5(para.get("json")+ ConfigUtil.KEY, "UTF-8");
		para.put("signature", mysign);
		// 将map转化为json 串
		String json = JSONObject.fromObject(para).toString();
		Map<String, String> map = new HashMap<String, String>();
	    map.put("data", json);
	 // 发送请求 并接收
		String post = HttpClientUtil.post(url, map);
		return post;
	}
}
