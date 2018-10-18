package com.esign.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.esign.bean.CertificateBean;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.Utils;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;

public class EviUtil {
	
	private static Logger log = Logger.getLogger(EviUtil.class);
	
	private static final String encoding = "UTF-8";
	/***
	 * 模拟发送文档保全Url请求 ，请求方式：POST
	 * 
	 * @return
	 */
	public static JSONObject eviRequestByPost(String filePath , String eviName , List<String> signServiceIds) {
		// 电子签名签署成功时返回的SignServiceId（签署记录ID）
		//signServiceIds.add("932325712256339968");

		// 电子签名证据ID列表
		Map<String, List<String>> eSignIds = new LinkedHashMap<String, List<String>>();
		// 0=SignServiceId（签署记录ID）
		eSignIds.put("0", signServiceIds);

		// 实名认证服务成功时返回的ServiceId（实名认证记录ID）
		/*List<String> serviceId = new ArrayList<String>();
		serviceId.add("cbf52ea8-8055-4fbd-b362-92949ca294cb");
		serviceId.add("f59cfb4a-338e-49e7-aae5-4d10c2de0fd5");*/

		// e签宝业务ID列表
		/*Map<String, List<String>> bizIds = new LinkedHashMap<String, List<String>>();
		// 0=ServiceId（实名认证记录ID）
		bizIds.put("0", serviceId);*/

		// 获取原文保全上传地址API的请求参数（JSON字符串）
		String param = JSONHelper.getOriginalRequestParameter(eviName, filePath, eSignIds, null);
		// 请求签名值
		String signature = AlgorithmHelper.getSignature(param, ConfigUtil.projectSecret, ConfigUtil.algorithm, encoding);
		// HTTP请求内容类型
		String ContentType = "application/json";
		// 设置HTTP请求头信息
		LinkedHashMap<String, String> headers = HttpRequestHelper.
				getPOSTHeaders(ConfigUtil.projectId, signature, ConfigUtil.algorithm,ContentType, encoding);
		// 向指定URL发送POST方法的请求
		JSONObject result = HttpRequestHelper.sendPOST(ConfigUtil.eviUrl, param, headers, encoding);
		return result;
	}

	public static JSONObject uploadOriginalDocumen(String evId, String fileUploadUrl, String filePath) {
		String contentBase64Md5 = AlgorithmHelper.getContentMD5(filePath);
		String ContentType = "application/octet-stream";
		// 设置HTTP请求头信息
		LinkedHashMap<String, String> headers = HttpRequestHelper.getPUTHeaders(contentBase64Md5, ContentType,
			encoding);
		// 向指定URL发送PUT方法的请求
		JSONObject result = HttpRequestHelper.sendPUT(evId, fileUploadUrl, filePath, headers);
		return result;
	}
	
	/***
	 * 03 存证记录关联至指定用户
	 * 
	 * @return
	 */
	public static JSONObject relevUser(String evId , String carNumber , String cardType , String name) {
		List<CertificateBean> certificates = new ArrayList<CertificateBean>();
		CertificateBean personBean = new CertificateBean();
		if(Utils.isObjectNotEmpty(name)){
			personBean.setName(name);
		}
		personBean.setType(cardType);
		personBean.setNumber(carNumber);

		certificates.add(personBean);

		// 获取原文保全上传地址API的请求参数（JSON字符串）
		String param = JSONHelper.getRelateRequestParameter(evId, certificates);
		// 请求签名值
		String signature = AlgorithmHelper.getSignature(param, ConfigUtil.projectSecret, ConfigUtil.algorithm, encoding);
		// HTTP请求内容类型
		String ContentType = "application/json";
		// 设置HTTP请求头信息
		LinkedHashMap<String, String> headers = HttpRequestHelper.getPOSTHeaders(ConfigUtil.projectId, signature, ConfigUtil.algorithm,
			ContentType, encoding);
		// 向指定URL发送POST方法的请求
		JSONObject result = HttpRequestHelper.sendPOST(ConfigUtil.relevUserUrl, param, headers, encoding);
		return result;
	}
	

}
