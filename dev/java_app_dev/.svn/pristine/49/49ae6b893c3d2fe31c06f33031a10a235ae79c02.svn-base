package com.RSA.result;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



import com.RSA.utils.CryptoUtils;
import com.RSA.utils.RSAHelper;
import com.RSA.utils.StringUtil;
import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;




public class ResultManager {
	private Logger log = Logger.getLogger(this.getClass());

	//替换为自己的客户端私钥
	//测试环境
	public static String CLIENT_PRIVATE_KEY= "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTcw7GjeokfMYo\n"
			+"pHoECO/IpGBfAId5THcwODf1XejKzfeoryVRyKTBwkNY8zodtAKw6Ov7rM2ugWwP\n"
			+"tYbVGVN7SGVvjWST/r+j2zxX07d3W62RP5mwAU7BjcDbkiRLMstFXpEm8oBu2Ypr\n"
			+"Y4XbrjKDzBP9gqLks3RCkGy9dzYNDmM3MtRE1pcI4k1e4ucIlTTwYQKRDjMlIwl6\n"
			+"slj9o//53KrmCFeeUXuiXt+lQkYGYq5Lo0iXgsx4IJ+mq0aa+UZ7uQp6ZTBEOrP4\n"
			+"6g2G+D3HF0aajk5GGjLaTf1BdD2vEZ3z9uZFC1OvdgP4EXzF6UwAclFpuMoksZdF\n"
			+"8FJYEp6DAgMBAAECggEBAIVFLXjjlUGuCs8u62CDqW3SfJMkKD7BfRW9OTfDGqhI\n"
			+"LOH6khD73yvoHr5/fo+eF1wdUi7r+S8Vj2BPT04ciktXk+c9PLkyhOLnmR7Z5/ao\n"
			+"p7q2VU1jZZoEmBtL/ugOeaOdOOtNSLmVDvMkZQfSwS/kfmz322c92/bgREkeS8jp\n"
			+"tHjjYeE4vdAoxbneLtD7WqOW2kWMM+TcqORdX9XIFzRCCf/URHj4fzwzbqDIzvZf\n"
			+"iSckNiS4+CSXyGK45C9Hn74fwp5eQ25EL1iVP0IVnH6CXK1jQR1sJybHDwZpQM06\n"
			+"LuIiNnSkka9aQ+eictx04aDp+x+LeHFe1tmpnLFRc6ECgYEA+lkc2xPnoRRs4esO\n"
			+"m3Qj2fiRVjESXmwlzuQDIe7g7rfvMuEoyiZ0kftU31U+g1rMcQfHCN7Z0qMBP4uw\n"
			+"+8XwcXSa1YFoWQShZG/0Q6ONQTNt1YVtVxBQcDlqKobMQuYaQ/mNo/K4vKgIyF7l\n"
			+"eRjO+nq3X67sEJKUJkRK0OSsz+kCgYEA2DkhUp6UD0XeCWbW7mKDdG87Cs/sNDWY\n"
			+"ez+eRPuZbO5Td9V8AQ8d4Aq0mtsRjtaVQ1Wmyt13mZ9555IGzlIEdnDdhg/To93d\n"
			+"Rsd3RVZUTLYtbo4xq+E1Cbjccv283t4rID0qWWvNDa1gDH8wb7horlPEaf3kSdWZ\n"
			+"dHlYUWaeA4sCgYEA8+GD/tfxOjaPq7Y1ULNfiaRg0TqUzm9ElbwCYTg439VHrKUz\n"
			+"+1pjDSNulvKQJ554s1QETVvrjwtE2AbZrywxBBW4EG40nSOpphJ6PrpBjVbwlKWa\n"
			+"0soN0oXcKvmOW02WIMondaOq2/5GMAxiOAo2+EQ8B3Z8ediRgoXc7IsY3qECgYA+\n"
			+"6rDVl6tBnnTzi2czSpsxwEQP61yx/H54LpThQcztM5Q6JHbG4B0zWioffWtoguxA\n"
			+"AaZdFYB9xTf1uDoewyLlTAZJwkU1Q5PlypjcJji1cbVanCm79/Y09APBui/BMiie\n"
			+"Yu31wzaRqkdum3v+pkxfnaRjqytIiMz0ZXLbjN6h7QKBgCTOT/oukTKsQ/tQlV+N\n"
			+"+/OiEw/3JyyPb0mIVyQU9AmFopCuBVUWyUfNvR3d5fAQdjDxu9uUVonyv3X3ZiJ5\n"
			+"lisib/k+G3mnS7hoovgC/SbMornnBiYCp2MLzAwbkMM2YZnwcCluf2eEmSvNi+W8\n"
			+"nuwtkHa9u+RSqt7MsF2Nu5IL";
	//替换匹配的服务端公钥
	//测试环境
	public static String SERVER_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwMqALo4MHQLbjjEB8Utj\n"
			+"EONx48lrUYrISJ6bmPG6rpM3hRKZ6IZ7oWCpilBsJTvbFISm9Id1Xmvd0WtES3nQ\n"
			+"4HbvSfiX7+uwr/g0icbq0lkx0TjUu/WobZ3oTk7BMu2YRWan9pzMPp71ZuP6f2+k\n"
			+"/c9eRs6e/4JRZ1x4Ul3LgRbWJuQbJsI8DSnuLvvK9xcYalwNwj5WG1YEYshQWtxW\n"
			+"vlxHW0VQDQsQ1Hk8O7AAOsFSxvyiBmoQe3fCnNdehz67t0w8ZzAg9MO8n6lv+ogp\n"
			+"kzLjp1Z50YrB/MNTW4nlQ1lM6T9R1UE7sfQLCF4cAK/5ScnSFOrLVs4OGiocmEMX\n"
			+"FQIDAQAB";
	
	private static class ResultManagerInstance{
			private static final ResultManager instance= new ResultManager();
	}
	
	public static ResultManager getInstance(){
		return ResultManagerInstance.instance;
	}
	
	public ResultManager(){}
	
	public RSAHelper rsaHelper = new RSAHelper();
	{
		try {
			rsaHelper.initKey(CLIENT_PRIVATE_KEY, SERVER_PUBLIC_KEY, 2048);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 接收返回结果
	 * @param respJsonEnc
	 * @param respKeyEnc
	 * @param respSign
	 * @return
	 * @throws Exception
	 */
	public String resultNoticeMsg(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
		String respJsonEnc = req.getParameter("json_enc");
		String respKeyEnc = req.getParameter("key_enc");
		String respSign = req.getParameter("sign");
		byte respKey[] = decryptKey(respKeyEnc) ;
		String respJson = decrytJson( respJsonEnc,respKey ) ;
		
		
		log.info("返回报文JSON:"+respJson);
		
		//验签
		boolean flag = verifyMsgSign(respJson, respSign);
		BaseResult br = new BaseResult();
		if(!flag){
			br.setSuccess(false);
			br.setErrorCode("-1");
			br.setErrorMsg("签名错误");
		}else{
			br.setSuccess(true);
			br.setMsg(respJson);
		}
		return JSON.toJSONString(br);
	}
	
	
	private String encryptJson( String json, byte[] key){
		String enc_json = CryptoUtils.desEncryptToHex(json, key) ;
		
		return enc_json;
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
