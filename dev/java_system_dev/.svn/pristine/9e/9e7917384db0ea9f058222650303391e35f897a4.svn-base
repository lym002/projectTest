package com.jsjf.controller.partner.rrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.SecurityUtils;
import com.jsjf.common.Utils;

/**
 * 人人利
 * @author DELL
 *
 */
public class RrlBase {
	
	private static Logger log = Logger.getLogger(RrlBase.class);
	/**
	 * 测试
	 */
	  //商务编号验证
	  public static String cust_id_url = "http://openapi.amoydao.com/zlo/Getp2pinfo/custid/";
	  //商务编号
	  public static String rrl_cust_id = "ed49e5224168dc779b052b80c8164b";
	  //约定key
	  public static String rrl_key = "duorongrrladn";
	  public static String rrl_type = "MD5";
	  public static String push_url = "http://openapi.amoydao.com/zlo/getp2pinfo/getsubscribe/";
	  //商务账号
	  public static String rrl_username = "zlo_4K4bt";
	  //商务密码
	  public static String rrl_password = "zlopwd_xkMdzttn";
	  
	  /**
	   * 
	   * 正式
	   *
	   */
	  //商务编号验证
//	  public static String cust_id_url = "http://openapi.renrenlee.com/zlo/Getp2pinfo/custid/";
//	  //商务编号
//	  public static String rrl_cust_id = "66cf880b4fcd3bfafb9c86b8713afb";
//	  //约定key
//	  public static String rrl_key = "duorongandrrl";
//	  public static String rrl_type = "MD5";
//	  public static String push_url = "http://openapi.renrenlee.com/zlo/getp2pinfo/getsubscribe/";
//	  //商务账号
//	  public static String rrl_username = "zlo_nSrvB";
//	  //商务密码
//	  public static String rrl_password = "zlopwd_MymW2Y4K";
	  
	  public static class innerClass{
		  private static RrlBase  rrlBase= new RrlBase();
	  }
	  
	  public static RrlBase getInstance(){
		  return innerClass.rrlBase;
	  }
	  /**
	   * 验证商务编号
	   * @param custId 商务编号
	   * @return
	 * @throws IOException 
	   */
	  public boolean validCustId(String custId) throws IOException{
		  boolean flag = false;
		  HttpURLConnection connection = (HttpURLConnection)new URL(cust_id_url).openConnection();
	      connection.setRequestProperty("accept", "*/*");
	      connection.setRequestProperty("connection", "Keep-Alive");
	      connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	      connection.setConnectTimeout(10000);
	      connection.setDoOutput(true);
	      connection.setDoInput(true);
	      PrintWriter out = new PrintWriter(connection.getOutputStream());
	      out.print("Cust_id="+custId);
	      out.flush();
	      String result = "";
	      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      String line;
	      while ((line = in.readLine()) != null)
	      {
	        result = result + line;
	      }
	      JSONObject json = JSONObject.parseObject(result);
	      log.info("人人利校验商户号返回数据："+result);
	      flag = (Boolean)json.get("Data");
	      return flag;
	  }
	  
	  public void pushInvestData(List<Map<String, Object>> list) throws ClientProtocolException,SocketTimeoutException, IOException{
		  HttpClient httpClient = new HttpClient();
		  PostMethod postMethod = new PostMethod(push_url);
			
			NameValuePair[] data = {
					new NameValuePair("Cust_id", RrlBase.rrl_cust_id),
					new NameValuePair("Sign_type", RrlBase.rrl_type),
					new NameValuePair("Sign", SecurityUtils.MD5(RrlBase.rrl_username+RrlBase.rrl_password+RrlBase.rrl_cust_id+RrlBase.rrl_key)),
					new NameValuePair("Data", JSON.toJSONString(list) ) };
			postMethod.setRequestBody(data);
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
			int statusCode = httpClient.executeMethod(postMethod);
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(),"UTF-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String str= "";
			while((str = br.readLine()) != null){
				stringBuffer.append(str );
			}
	      log.info("推送人人利投资记录返回结果集"+stringBuffer.toString());  
	  }
	  
	  /**
	   * 验证签名
	   * @param custId 商户编号
	   * @param phone 注册手机号
	   * @param sign 加密验证参数
	   * @return
	   * @throws IOException
	   */
	  public boolean validSign(String custId, String phone, String sign) throws IOException{
		  String signKey = SecurityUtils.MD5(custId+phone+rrl_key);
		  if(sign.equals(signKey)){
			  return true;
		  }else{
			  return false;
		  }
		  
	  }
	  
}
