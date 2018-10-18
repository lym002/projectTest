package com.esign.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jsjf.common.Utils;
import com.timevale.esign.sdk.tech.bean.PosBean;
import com.timevale.esign.sdk.tech.bean.SignPDFFileBean;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.impl.constants.SignType;
import com.timevale.esign.sdk.tech.service.SelfSignService;
import com.timevale.esign.sdk.tech.service.factory.SelfSignServiceFactory;

public class UploadEvi {

	/***
	 * 用户获取文档保全Url和存证编号
	 * 
	 * @return
	 */
	public static Map<String, String> getEviUrlAndEvId(String filePath , String eviName , List<String> signServiceIds){
		Map<String, String> hashMap = null;
		JSONObject jsonObj = EviUtil.eviRequestByPost(filePath,eviName,signServiceIds);
		
		String errCode = jsonObj.get("errCode").toString();
		if ("0".equals(errCode)) {
			hashMap = new HashMap<String, String>();
			String evId = jsonObj.get("evid").toString();
			String updateUrl = jsonObj.get("url").toString();
			hashMap.put("errCode", errCode);
			hashMap.put("evId", evId);
			hashMap.put("fileUploadUrl", updateUrl);
		} else {
			hashMap = new HashMap<String, String>();
			hashMap.put("errCode", errCode);
			hashMap.put("msg", jsonObj.get("msg").toString());
		}
		return hashMap;
	}

	/**
	 * 03 存证记录关联至指定用户
	 * 
	 * @param evId
	 * @param certificates
	 * @return
	 */
	public static Map<String, String> relevUser(String evId, String cardNumber,
			String cardType, String name) {
		JSONObject jsonObj = EviUtil
				.relevUser(evId, cardNumber, cardType, name);
		Map<String, String> hashMap = null;
		String errCode = jsonObj.get("errCode").toString();
		if ("0".equals(errCode)) {
			hashMap = new HashMap<String, String>();
			hashMap.put("errCode", jsonObj.get("errCode").toString());
			hashMap.put("msg", jsonObj.get("msg").toString());
			hashMap.put("success", jsonObj.get("success").toString());
		} else if (!"0".equals(errCode) && Utils.isObjectNotEmpty(errCode)) {
			hashMap = new HashMap<String, String>();
			hashMap.put("errCode", jsonObj.get("errCode").toString());
			hashMap.put("msg", jsonObj.get("msg").toString());
			hashMap.put("success", jsonObj.get("success").toString());
		}
		return hashMap;
	}


	public static Map<String, String> uploadEviFile(String evId,String filePath,
			String updateUrl) {
		// 文件上传 上传需要保全的文档
		return EviUtil.uploadOriginalDocumen(evId,updateUrl, filePath);
	}
	
/*	
	public static void main(String[] args) {
		Map<String,String> map = UploadEvi.getEviUrlAndEvId("D:\\workspace\\system\\webapp\\pdf\\tzjypz.pdf","投资记录保全");
		UploadEvi.relevUser("O932507619950620674","340421199004160217","ID_CARD",null);//身份证格式不正确
		//UploadEvi.uploadEviFile("D:\\workspace\\system\\webapp\\pdf\\tzjypz.pdf",map.get("fileUploadUrl"));
	}*/

	/**
	 * E签宝平台签
	 * @param srcPdfFile 签署PDF文档信息
	 * @param signPos 签章位置信息
	 * @return
	 */
	public static FileDigestSignResult doSign(SignPDFFileBean srcPdfFile, PosBean signPos) {
		SelfSignService instance = SelfSignServiceFactory.instance();
		FileDigestSignResult localSignPdf = instance.localSignPdf(srcPdfFile, signPos, 0, SignType.Single);
		return localSignPdf;
	}
	
	  public static void main(String[] args) {
		  relevUser("O934995267910922241","340421199004160216","ID_CARD","胡振亚");
	  }
	 
}
