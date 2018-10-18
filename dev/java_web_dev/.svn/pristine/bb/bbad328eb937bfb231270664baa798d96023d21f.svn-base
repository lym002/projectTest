package com.jsjf.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsjf.model.member.DrCarryParam;
import com.jsjf.service.member.DrCarryParamService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Component
public class SmsSendUtil {
	private static Logger log = Logger.getLogger(SmsSendUtil.class);
	//正式环境配置，后台访问地址www.10690221.com
	private static final String url = "http://210.5.158.31/hy/";
	
	private static final String companyCode = "hxcf";//企业代码

	private static final String username = "90298";//短信账号
	
	private static final String pwd = "m6aA#w";//密码
	
	/**
	 * 即时发送
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回99，失败返回-1
	 * @throws Exception
	 */
	public static int sendMsg(String mobile,String content, int type) throws Exception{
		return sendMsgByXiAo(mobile, content);
	}
	/**
	 * 即时发送-希奥
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回99，失败返回-99
	 * @throws Exception
	 */
	public static int sendMsgByXiAo(String mobile,String content) throws Exception{
		/*HttpClient httpClient = new HttpClient();
        content=java.net.URLEncoder.encode(content, "gbk");
        String auth = SecurityUtils.MD5(companyCode+pwd);
		PostMethod postMethod = new PostMethod(url);
		
		NameValuePair[] data = {
					new NameValuePair("uid", username),
					new NameValuePair("auth", auth),
					new NameValuePair("mobile", mobile),
					new NameValuePair("expid", "0"),
					new NameValuePair("msg",content ) };
		postMethod.setRequestBody(data);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);  
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
		int statusCode = httpClient.executeMethod(postMethod);
		
		if (statusCode == HttpStatus.SC_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str= "";
			while((str = br.readLine()) != null){
				stringBuffer .append(str );
			}
			String[] array = stringBuffer.toString().split(",");
			return Integer.parseInt(array[0])==0?99:Integer.parseInt(array[0]);
		}
		return -99;*/
		return 99;
	}
	
	/**
	 * 定时短信发送
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @param time 短信发送时间
	 * @return 发送成功返回88，失败返回-88
	 * @throws Exception
	 */
	public static int sendTimeMsg(String mobile,String content,String time) throws Exception{
		/*HttpClient httpClient = new HttpClient();
        content=java.net.URLEncoder.encode(content, "gbk");
        String auth = SecurityUtils.MD5(companyCode+pwd);
		PostMethod postMethod = new PostMethod(url);
		
		NameValuePair[] data = {
					new NameValuePair("uid", username),
					new NameValuePair("auth", auth),
					new NameValuePair("mobile", mobile),
					new NameValuePair("expid", "0"),
					new NameValuePair("msg",content ),
					new NameValuePair("time", time ) };
		postMethod.setRequestBody(data);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);  
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
		int statusCode = httpClient.executeMethod(postMethod);
		
		if (statusCode == HttpStatus.SC_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str= "";
			while((str = br.readLine()) != null){
				stringBuffer .append(str );
			}
			String[] array = stringBuffer.toString().split(",");
			return Integer.parseInt(array[0])==0?88:Integer.parseInt(array[0]);
		}
		return -88;*/
		return 88;
	}
	/**
	 * 即时发送-企信通
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回77，失败返回-77
	 * @throws Exception
	 */
	public static int sendMsgByQxt(String mobile,String content) throws Exception{
	/*	String username="";
        String password = SecurityUtils.MD5("");
		String msg=URLEncoder.encode(content,"GBK");
		String post="http://58.83.147.85:8080/qxt/smssenderv2?";
		String url="";
		url=post+"user="+username+"&password="+password+"&tele="+mobile+"&msg="+msg+"&extno=4455";
		System.clearProperty("java.classpath");
		String output = null;
		try {
			output = Http.httpGet(url,"GBK");
			if(null != output){
				String[]result = output.split(":");
				log.info("qxt msg return："+output);
				if(result.length > 0){
					if("ok".equals(result[0])){
						return 77;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -77;*/
		return 77;
	}
	
	public static void main(String[] args){
//		System.out.println(Utils.parseDate(Utils.format(new Date(), "yyyy-MM-dd 10:00:00"),"yyyy-MM-dd HH:mm:ss"));
//		sendMsg("15801868241", "测试及时");
		try {
			int result = sendMsgByQxt("15801868241", "恭喜您，已经通过认证！");
			System.out.println("result:" + result);
//			sendMsgByXiAo("15801868241", "测试及时，验证码：2383930");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
