package com.esign.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/***
 * 
 * @Description: HTTP请求辅助类
 * @Team: 公有云技术支持小组
 * @Author: 天云小生
 * @Date: 2017年11月20日
 */
public class HttpRequestHelper {
    private static Logger LOG = LoggerFactory.getLogger(HttpRequestHelper.class);
    /***
     * POST请求的头部信息
     * @param projectId
     * @param signature
     * @param algorithm
     * @param ContentType
     * @param encoding
     * @return
     */
    public static LinkedHashMap<String, String> getPOSTHeaders(String projectId, String signature, String algorithm,
	String ContentType, String encoding) {
			LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
			headers.put("X-timevale-project-id", projectId);
			headers.put("X-timevale-signature", signature);
			headers.put("signature-algorithm", algorithm);
			headers.put("Content-Type", ContentType);
			headers.put("Charset", encoding);
			return headers;
    }
    /***
     * PUT请求的头部信息
     * @param filePath
     * @param ContentMD5
     * @param ContentType
     * @param encoding
     * @return
     */
    public static LinkedHashMap<String, String> getPUTHeaders(String ContentMD5, String ContentType, String encoding) {
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("Content-MD5", ContentMD5);
		headers.put("Content-Type", ContentType);
		headers.put("Charset", encoding);
		return headers;
    }

    /***
     * 向指定URL发送POST方法的请求
     * @param apiUrl
     * @param data
     * @param projectId
     * @param signature
     * @param encoding
     * @return
     */
    public static JSONObject sendPOST(String apiUrl, String data, LinkedHashMap<String, String> headers,String encoding) {
		StringBuffer strBuffer = null;
		JSONObject obj = null;
		try {
		    // 建立连接
		    URL url = new URL(apiUrl);
		    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		    // 需要输出
		    httpURLConnection.setDoOutput(true);
		    // 需要输入
		    httpURLConnection.setDoInput(true);
		    // 不允许缓存
		    httpURLConnection.setUseCaches(false);
	
		    httpURLConnection.setRequestMethod("POST");
		    // 设置Headers
		    if (null != headers) {
			for (String key : headers.keySet()) {
			    httpURLConnection.setRequestProperty(key, headers.get(key));
			}
		    }
	
		    // 连接会话
		    httpURLConnection.connect();
		    // 建立输入流，向指向的URL传入参数
		    DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
		    // 设置请求参数
		    dos.write(data.getBytes(encoding));
		    LOG.info("获取原文保全上传地址API的请求参数" + data);
		    dos.flush();
		    dos.close();
		    // 获得响应状态
		    int resultCode = httpURLConnection.getResponseCode();
		    strBuffer = new StringBuffer();
		    String readLine = new String();
		    BufferedReader responseReader = new BufferedReader(
			    new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
		    while ((readLine = responseReader.readLine()) != null) {
			strBuffer.append(readLine);
		    }
		    responseReader.close();
		    
		    if (HttpURLConnection.HTTP_OK == resultCode) {
		    	LOG.info("获取原文保全上传地址请求的Http-Status = " + resultCode + " " + httpURLConnection.getResponseMessage());
			obj = JSONHelper.toJSONObject(strBuffer.toString());
		    } else {
		    	LOG.info("获取原文保全上传地址请求的Http-Status = " + resultCode + " " + httpURLConnection.getResponseMessage() + strBuffer.toString());
		    }
		    
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return obj;
    }
    
    /***
     * 向指定URL发送PUT方法的请求
     * 
     * @return
     */
    public static JSONObject sendPUT(String evId,String fileUploadUrl, String filePath, LinkedHashMap<String, String> headers) {
		StringBuffer strBuffer = null;
		JSONObject obj = null;
		try {
		    // 建立连接
		    URL url = new URL(fileUploadUrl);
		    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		    // 需要输出
		    httpURLConnection.setDoOutput(true);
		    // 需要输入
		    httpURLConnection.setDoInput(true);
		    // 不允许缓存
		    httpURLConnection.setUseCaches(false);
	
		    httpURLConnection.setRequestMethod("PUT");
		    // 设置Headers
		    if (null != headers) {
			for (String key : headers.keySet()) {
			    httpURLConnection.setRequestProperty(key, headers.get(key));
			}
		    }
		    // 连接会话
		    httpURLConnection.connect();
		    // 建立输入流，向指向的URL传入参数
		    DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
		    // 设置请求参数
		    dos.write(FileHelper.getBytes(filePath));
		    dos.flush();
		    dos.close();
		    // 获得响应状态
		    Integer resultCode = httpURLConnection.getResponseCode();
		    obj = new JSONObject();
		    strBuffer = new StringBuffer();
			String readLine = new String();
			BufferedReader responseReader = new BufferedReader(
				new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
			    strBuffer.append(readLine);
			}
			responseReader.close();
		    if (HttpURLConnection.HTTP_OK == resultCode) {
			    LOG.info(evId + " 上传成功！Http-Status = " + resultCode + " " + httpURLConnection.getResponseMessage());
				obj.put("errCode", resultCode.toString());
				obj.put("msg", "上待保全文件上传成功！");
		    } else {
				LOG.info(evId + " 上传失败！Http-Status = " + resultCode + " " + httpURLConnection.getResponseMessage());
				obj.put("errCode", resultCode.toString());
				obj.put("msg", "上待保全文件上传失败！");
		    }
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return obj;
    }
}
