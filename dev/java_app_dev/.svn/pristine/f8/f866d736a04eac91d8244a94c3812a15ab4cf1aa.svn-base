package com.sftpay.utils;

import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.jytpay.AppException;
import com.jytpay.utils.StringUtil;
import com.sftpay.config.ExpressGlobalConfig;

public class BaseExpressService {
	protected static final Logger log = Logger.getLogger(BaseExpressService.class);
	
	public static String httpSend(String url, NameValuePair[] params){
		log.info("请求接口地址 : " + url);
		String responseBody = null;
		try {
			// 构建HttpClient
			HttpClient client = new HttpClient();
			HttpConnectionManagerParams httpParams = client.getHttpConnectionManager().getParams();
			httpParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 设置连接超时时间(单位毫秒) 
			httpParams.setConnectionTimeout(20000); 
			// 设置读数据超时时间(单位毫秒) 
			httpParams.setSoTimeout(20000); 
			PostMethod postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			// 设置请求参数列表
			postMethod.addParameters(params);
	
			// 签名<与参数列表顺序等无关，request body作为签名的明文>, 详细请参见《盛付通快捷支付API》--RSA签名
			RequestEntity requestEntity = postMethod.getRequestEntity();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			requestEntity.writeRequest(baos);
			String plainText = baos.toString();
			log.info("请求签名明文: " + plainText);
			String signMsg = RSA.sign(plainText,ExpressGlobalConfig.merchantPrivateKey, "utf-8");
	
			// 设置签名类型与签名串到请求header里面
			postMethod.addRequestHeader("signType", ExpressGlobalConfig.rsaSignType);
			postMethod.addRequestHeader("signMsg", signMsg);
	//		System.out.println("请求签名串 : " + signMsg);
	
			// 发起快捷请求
			int httpCode = client.executeMethod(postMethod);
			responseBody = postMethod.getResponseBodyAsString();
//			System.out.println("http请求响应状态码 : " + httpCode);
//			System.out.println("http请求响应body : " + responseBody);
	
			// 验证签名，响应的签名类型与签名串同样也是从header里面去取
			Header responseSignMsgHeader = postMethod.getResponseHeader("signMsg");
			Header responseSignTypeHeader = postMethod.getResponseHeader("signType"); // 快捷API只会返回RSA
			if ((null != responseSignMsgHeader) && (null != responseSignTypeHeader)) {
				String responseSignType = responseSignTypeHeader.getValue();
				String responseSignMsg = responseSignMsgHeader.getValue();
//				System.out.println("响应签名类型 : " + responseSignType);
//				System.out.println("响应签名串 : " + responseSignMsg);
	
				boolean signResult = false;
				// 盛付通公钥
				if (StringUtil.equalsIgnoreCase("RSA", responseSignType)) {
					signResult = RSA.verify(responseBody, responseSignMsg,
							ExpressGlobalConfig.sftRsaPublicKey, "utf-8");
				} else {
					log.info("未知的签名类型  : " + responseSignType);
				}
	
				if (signResult) {
					log.info("验证签名成功");
				} else {
					log.info("验证签名失败");
				}
			} else {
				log.info("找不到签名相关信息，验证签名失败");
			}
		}catch (SocketTimeoutException e) {
           throw new AppException(e);
		}catch (Exception e) {
           throw new RuntimeException();
		}
		return responseBody;
	}
}
