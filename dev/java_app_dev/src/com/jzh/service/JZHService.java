package com.jzh.service;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.jzh.data.BaseReqdata;
import com.jzh.data.BaseRspdata;
import com.jzh.http.WebUtils;
import com.jzh.util.RequestData;
import com.jzh.util.SecurityUtils;
import com.jzh.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 存管系统请求Service
 * @author wangxl
 */
public class JZHService {
	/**
	 * HttpClient POST方式请求存管系统
	 * 
	 * 注：商户定义任一接口请求对象时，须继承BaseReqdata基类
	 * 
	 * @param baseReqData 请求数据对象
	 * @return JSONObject 存管系统响应JSONObject对象报文
	 * @throws Exception
	 */
	public static JSONObject sendHttp(String url, BaseReqdata baseReqData) throws Exception {
		baseReqData.setSignature(RequestData.createSignValueForReg(baseReqData));//签名
		String rspJsonstring = WebUtils.sendHttp(url, baseReqData);
		if(StringUtil.isEmpty(rspJsonstring)) {
			throw new Exception("存管系统返回空报文");
		}
		return verifySignAndParse(rspJsonstring);
	}
	
	/**
	 * 获取请求存管系统的JSON格式报文
	 * 
	 * 注：商户定义任一接口请求对象时，须继承BaseReqdata基类
	 * 
	 * @param baseReqData 请求数据对象
	 * @return String 请求存管系统JSON格式字符串报文
	 */
	public static String encaJSONstring(BaseReqdata baseReqData) {
		return StringUtil.encaJSONstring(baseReqData);
	}
	
	/**
	 * 存管 2.0 -异步通知验签
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public static boolean verifySignAsynNotice(Object obj,String signature) throws Exception{
		
		return RequestData.createVerifySignAsynNotice(obj, signature);
	}
	/**
	 * 存管 2.0 报文 验签并返回json结果
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public static JSONObject verifySignAndParse(String xml) throws Exception{
		SAXReader saxReader = new SAXReader();
		Document doc;
		JSONObject str = new JSONObject();
		doc = saxReader.read(new InputSource(new StringReader(xml)));
		Element root = doc.getRootElement();
		Element plain=root.element("plain");
		Element signature=root.element("signature");
		 
		 //验签
		if(SecurityUtils.verifySign(xml.substring(xml.indexOf("<plain>"),xml.indexOf("</plain>")+8), signature.getStringValue())){
			//返回结果 转成字符串
			JSONObject obj = new JSONObject();
			listNodess(plain, str);
		}else{
			throw new Exception("验签失败！");
		}
		return str;
	}

	/** 
     * 遍历当前节点元素下面的所有(元素的)子节点 
     */  
    public  static JSONObject listNodess(Element node,JSONObject str) {
      
        // 当前节点下面子节点迭代器  
        Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();
            
            if(e.isTextOnly()){
            	str.put(e.getName(), e.getStringValue());
            }else{
            	JSONObject obj = new JSONObject();
            	str.put(e.getName(), listNodess(e,obj));
            }
        }  
    	return str;
    }  
	
	
    public static void main(String[] args) {
    	SAXReader saxReader = new SAXReader();
		Document doc;
		//测试报文
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ap><plain><resp_code>5019</resp_code><mchnt_cd>0002900F0041077</mchnt_cd><mchnt_txn_ssn>96f14200a794dbcc91cad69b50ef05</mchnt_txn_ssn></plain><signature>auK8wfMTwG8ObQ2HC0J3KnSzgSGAplpYhIzU0pksT1Zzhb22hmVllM+dgjfXy5OriA6+0xzlr0ByfFcv6EOmcxduZ0Aa84Ouui9G1zYbkiGEv/AG+0VwqYYqZUVCEbEEnIvyyqtyKB4RyBZxg8HPGaBPRls6pTH8Lc5i5m1aaEA=</signature></ap>";
		JSONObject str = new JSONObject();
		try {
			doc = saxReader.read(new InputSource(new StringReader(xml)));
			Element root = doc.getRootElement();
			Element plain=root.element("plain");
			//返回结果 转成字符串
			JSONObject obj = new JSONObject();
			str.put("message", listNodess(plain, obj));
		
			System.out.println(str);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
}