package com.RSA.send;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;


import com.RSA.utils.AppException;
import com.RSA.utils.CryptoUtils;
import com.RSA.utils.DESHelper;
import com.RSA.utils.HttpClient431Util;
import com.RSA.utils.RSAHelper;
import com.RSA.utils.StringUtil;



public class SendManager {
	private Logger log = Logger.getLogger(this.getClass());
	
	public static String RESP_MSG_PARAM_SEPARATOR = "&";
	
	/**返回报文json_enc字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_XML_ENC = "json_enc=";
	/**返回报文key_enc字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_KEY_ENC = "key_enc=";
	
	/**返回报文sign字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_SIGN = "sign=";
	
	//client私钥
	public static String CLIENT_PRIVATE_KEY= "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDAyoAujgwdAtuO\n"
			+"MQHxS2MQ43HjyWtRishInpuY8bqukzeFEpnohnuhYKmKUGwlO9sUhKb0h3Vea93R\n"
			+"a0RLedDgdu9J+Jfv67Cv+DSJxurSWTHRONS79ahtnehOTsEy7ZhFZqf2nMw+nvVm\n"
			+"4/p/b6T9z15Gzp7/glFnXHhSXcuBFtYm5BsmwjwNKe4u+8r3FxhqXA3CPlYbVgRi\n"
			+"yFBa3Fa+XEdbRVANCxDUeTw7sAA6wVLG/KIGahB7d8Kc116HPru3TDxnMCD0w7yf\n"
			+"qW/6iCmTMuOnVnnRisH8w1NbieVDWUzpP1HVQTux9AsIXhwAr/lJydIU6stWzg4a\n"
			+"KhyYQxcVAgMBAAECggEAAq4N8Be7ZV/Xy3c07rS8kMyIlcEEA+Mo+5Fw2kRp8ulK\n"
			+"+UeT6h6Mc2bTlH+fsTjOx888PuPvJrvbHoEeNupfk+8zTlTtT8F3+Eo7+LpSI1Bo\n"
			+"F99xXZeBmtx6ETSZDOsulbBin1P4ptow0RhUMBdONNUAG33dyfF2zjgD2ClDney8\n"
			+"H/ZyGxooSCs3fuVEwkZla7/x9GlXfRAU457Zc83cZgQ79hRI2ikarLUM+Q0/2pTO\n"
			+"9sedhN1ZmJ2udXnRISh2+hmMX30iy1rB7KEHPzivcnrCjfaBI3uvilI2n1V0VV+4\n"
			+"TUGmQcRan04Jcf99UQ7e4DkT3Me6ZlSW/hpyZAGiIQKBgQD46vUinQ1HBxE+h77m\n"
			+"WnouR3NpdKP5F1MZErszOO1NTVRMCnIi80K0IIlFyiIqkEvorNBscI5qptjVcX6E\n"
			+"S/+gw+hUXTIbhAI+7c4MO2553fVHnEYsAVQxZ6g6Ls6Z1MfABZLXWOuZmKeCHeEX\n"
			+"XUABt3SosJojx1kn1SgntmRwqQKBgQDGRruG2yAx6UsUyQZ5ZO3uT3yJOdlWcFIt\n"
			+"bwkowFXUXXa0sbRIy6yWpNcK7bKsZ6xW1AnXl9ykb2EVTz0ktMG8p163IgQft7I8\n"
			+"9DBKGrqEYti3qPmDfV1WaVZoRAiqdiQMMYb1u2Pa9Wzr0lb+TWVOqIunUwHr1E6X\n"
			+"ZFt1vQz6jQKBgQCwByh34+yh0COnvsOHj3BcDfdWGkBjRNVlI9Pys01Nn2P/qltw\n"
			+"z+raCzBIhFuJEiY8UwnJxgdQ8NDikLJRyJTsEd7Y2lc3EjGsYuuVxumnkQmGLMOL\n"
			+"Fx19Grri+nrsWocwCkD/vUhPhJ7g61Rk+3LqQ9xh9xHqP22TN7B/9QIukQKBgH1N\n"
			+"rCtghesk/T8If9/t6fPGJfTvKyvWVgjCeC5ZxqQr4N32w/GRTePyDrioWVddOWuy\n"
			+"Gvo1zcnZXahC6GgvF5SqzU+83pBQ3gJpa68ZTzBk64K99dmkO31lmqbQAA4CFWvj\n"
			+"TOA06bkxiwFJb5LMxK4+E+vNqJQqGvTQBoDU1cPtAoGBAIdCsAuO+1nlHxF5GyZJ\n"
			+"OE9F/sdK7No9jyHO1dRyWbeTcLr03g8H7eI5n9/kLCwwI/KmZaquIJlZcXqpjYQn\n"
			+"MXlbqgJW8g/ALuXCSWUTNhXhPTxN0Eu5iHNW1iZDjbYuGPy1pNq0au3zGV1axKE6\n"
			+"busSI9H6FBvfcW7Q0rEA/tg/\n";
			
	
	//server公钥
	public static String SERVER_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA03MOxo3qJHzGKKR6BAjv\n"
			+"yKRgXwCHeUx3MDg39V3oys33qK8lUcikwcJDWPM6HbQCsOjr+6zNroFsD7WG1RlT\n"
			+"e0hlb41kk/6/o9s8V9O3d1utkT+ZsAFOwY3A25IkSzLLRV6RJvKAbtmKa2OF264y\n"
			+"g8wT/YKi5LN0QpBsvXc2DQ5jNzLURNaXCOJNXuLnCJU08GECkQ4zJSMJerJY/aP/\n"
			+"+dyq5ghXnlF7ol7fpUJGBmKuS6NIl4LMeCCfpqtGmvlGe7kKemUwRDqz+OoNhvg9\n"
			+"xxdGmo5ORhoy2k39QXQ9rxGd8/bmRQtTr3YD+BF8xelMAHJRabjKJLGXRfBSWBKe\n"
			+"gwIDAQAB";

	private static class SendManagerInstance{
		private static final SendManager instance = new SendManager(); 
	}
	
	public static SendManager getInstance(){
		return SendManagerInstance.instance;
	} 
	
	private SendManager(){}
	
	public RSAHelper rsaHelper = new RSAHelper();
	{
		try {
			rsaHelper.initKey(CLIENT_PRIVATE_KEY, SERVER_PUBLIC_KEY, 2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param jsonData  封装请求的JSON参数
	 * @param URL  请求地址
	 * @return
	 * @throws Exception
	 */
	public String sendData(String jsonData, String URL) throws Exception{
		String mac = signMsg(jsonData);
        String respJSON = sendDataMsg(jsonData, mac, URL );
        return respJSON;
	}
	/**
	 * 
	 * @param json  JSON 明文
	 * @param sign sign域
	 * @param URL
	 * @return
	 * @throws Exception
	 */
	public String sendDataMsg(String json,String sign, String URL) throws Exception{
		System.out.println("上送报文："+json);
		System.out.println("上送签名："+sign);
		log.info("上送报文："+json);
		log.info("上送签名："+sign);
		
		byte[] des_key = DESHelper.generateDesKey() ;
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("json_enc", encryptJson(json,des_key));
		paramMap.put("key_enc", encryptKey(des_key));
		paramMap.put("sign", sign);
		System.out.println(paramMap.get("key_enc"));
		// 获取执行结果

		String res = HttpClient431Util.doPost(paramMap, URL);
		
		if(res  == null){
			log.error("服务器连接失败");
			
			throw new AppException("测试异常");
		}else{
			log.info("连接服务器成功,返回结果"+res);
		}
		String []respMsg = res.split(RESP_MSG_PARAM_SEPARATOR);
		
		String respJsonEnc = respMsg[0].substring(RESP_MSG_PARAM_PREFIX_XML_ENC.length());
		String respKeyEnc = respMsg[1].substring(RESP_MSG_PARAM_PREFIX_KEY_ENC.length());
		String respSign = respMsg[2].substring(RESP_MSG_PARAM_PREFIX_SIGN.length());
		
		byte respKey[] = decryptKey(respKeyEnc) ;
		
		String respJson = decrytJson( respJsonEnc,respKey ) ;
		
		
		log.info("返回报文XML:"+respJson);
		log.info("返回报文签名:"+respSign);
		
		Assert.assertTrue("返回报文校验失败",verifyMsgSign(respJson,respSign));
		
		return respJson;
	}
	
	/**
	 * 加密Hex
	 * @param xml
	 * @return
	 */
	public String signMsg( String jsonStr ){
		String hexSign = null ;
		
		try {
			byte[] sign = rsaHelper.signRSA(jsonStr.getBytes("UTF-8"), false, "UTF-8") ;
			
			hexSign = StringUtil.bytesToHexString(sign) ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hexSign;
	}
	
	private String encryptJson( String json, byte[] key){
		
		String enc_xml = CryptoUtils.desEncryptToHex(json, key) ;
		
		return enc_xml;
	}
	
	private byte[] decryptKey(String hexkey){
		byte[] key = null ;
		byte[] enc_key = StringUtil.hexStringToBytes(hexkey) ;
		
		try {
			key = rsaHelper.decryptRSA(enc_key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return key ;
	}
	
	private String encryptKey( byte[] key){
		
		byte[] enc_key = null ;
		try {
			enc_key = rsaHelper.encryptRSA(key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return StringUtil.bytesToHexString(enc_key) ;
	}
	
	public String decrytJson(String json_enc, byte[] key) {
		String jsonStr = CryptoUtils.desDecryptFromHex(json_enc, key) ;
		return jsonStr;
	}
	
	/**
	 * 验签
	 * @param 加签的JSON字符串
	 * @param sign
	 * @return
	 */
	public boolean verifyMsgSign(String json, String sign)
	{
		byte[] bsign = StringUtil.hexStringToBytes(sign) ;
		
		boolean ret = false ;
		try {
			ret = rsaHelper.verifyRSA(json.getBytes("UTF-8"), bsign, false, "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
