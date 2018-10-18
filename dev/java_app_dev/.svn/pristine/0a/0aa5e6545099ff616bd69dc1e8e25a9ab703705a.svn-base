package com.jsjf.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

public class Http {
	public static String httpPost(String strURL, String params, String charset) {
		return Http.httpPost(strURL, params, charset, null);
	}

	public static String httpPost(String strURL, String params, String charset,
			HashMap<String, String> header) {
		String rStr = null;
		byte[] buf = null;
		URL url = null;
		HttpURLConnection urlConn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream baos = null;

		try {
			StringBuffer sb = new StringBuffer();
			url = new URL(strURL);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(10000);
			urlConn.setReadTimeout(10000);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			if (header != null) {
				Iterator<String> iterator = header.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = header.get(key);
					urlConn.setRequestProperty(key, value);
				}
			}
			bos = new BufferedOutputStream(urlConn.getOutputStream());
			bos.write(params.getBytes());
			bos.flush();
			baos = new ByteArrayOutputStream();
			bis = new BufferedInputStream(urlConn.getInputStream());
			buf = new byte[1024 * 1024];
			int i = 0;
			while ((i = bis.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, i);
			}
			byte[] buf1 = baos.toByteArray();
			rStr = new String(buf1, charset);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (Exception e) {

			}
			try {
				if (bos != null)
					bos.close();
			} catch (Exception e) {

			}
			try {
				if (bis != null)
					bis.close();
			} catch (Exception e) {

			}
			try {
				if (urlConn != null)
					urlConn.disconnect();
			} catch (Exception e) {

			}
		}
		return rStr;

	}

	public static String httpGet(String strURL, String charset)
			throws Exception {
		String rStr = null;
		byte[] buf = null;
		URL url = null;
		HttpURLConnection urlConn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			StringBuffer sb = new StringBuffer();
			url = new URL(strURL);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(10000);
			urlConn.setReadTimeout(10000);
			bis = new BufferedInputStream(urlConn.getInputStream());
			
			buf = new byte[1024 * 100];
			int i = 0;
			int total = 0;
			while ((i = bis.read(buf, total, buf.length - total)) != -1) {
				total += i;
			}
			rStr = new String(buf, 0, total, charset);

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (Exception e) {

			}
			try {
				if (bis != null)
					bis.close();
			} catch (Exception e) {

			}
			try {
				if (urlConn != null)
					urlConn.disconnect();
			} catch (Exception e) {

			}
		}
		return rStr;

	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
