package com.jsjf.controller.partner.yrt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.jsjf.common.SecurityUtils;

/**
 * 易瑞特
 * 
 * @author Administrator
 *
 */
public class YrtBase {
	private static Logger log = Logger.getLogger(YrtBase.class);
//	private static String yrt_url = "http://app.offer99.com/callback/callback_adv/callback_adv_v32f6196016ccad94706aec015e93737.php";
//	private static String ad_key = "yysr3r2h5i";// 易瑞特提供的安全码,供sign参数值组合加密时用
	//正式
	private static String yrt_url = "http://app.offer99.com/callback/callback_adv/callback_adv_i393368205cacece4316ea5a7d7a1933.php";
	private static String ad_key = "5hdwf77006";// 易瑞特提供的安全码,供sign参数值组合加密时用
	private static Map<String, String>msgMap = new ConcurrentHashMap<String, String>();
	static{
		msgMap.put("error_tid", "交易号错误");
		msgMap.put("error_1", "交易号重复处理过");
		msgMap.put("error_2", "Ip完成过");
		msgMap.put("error_3", "一天内ip段完成过");
		msgMap.put("error_safe_filename", "回调文件与交易号不匹配");
		msgMap.put("error_callback_ip", "广告方IP加调不正确");
		msgMap.put("error_sign", "签名不正确");
		msgMap.put("success", "成功");
	}
	public static class innerClass {
		private static YrtBase yrtBase = new YrtBase();
	}

	public static YrtBase getInstance() {
		return innerClass.yrtBase;
	}
	public static Map<String, String> getMsgMap() {
		return msgMap;
	}
	/**
	 * 易瑞特注册回调接口
	 */
	public String registerRollbackYrt(String tid, String uid) throws IOException {
		StringBuffer sb = new StringBuffer(yrt_url);
		sb.append("?tid=").append(tid).append("&uid=").append(uid).append("&sign=")
		.append(SecurityUtils.MD5(tid + uid + ad_key));
		HttpURLConnection connection = (HttpURLConnection) new URL(sb.toString())
				.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		connection.setConnectTimeout(10000);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		log.info("加密前：" + tid + uid + ad_key);
		log.info("sign:" + SecurityUtils.MD5(tid + uid + ad_key));
		String result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result = result + line;
		}
		log.info("用户：tid=" + tid + "，回调返回Code==>" + result + "，信息：" + msgMap.get(result));
		return msgMap.get(result);
	}
	public static void main(String[] args) {
		try {
			YrtBase.getInstance().registerRollbackYrt("h802048a3b011d593c9ba80e72512550", "15801868241");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
