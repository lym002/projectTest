package com.jsjf.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsjf.model.member.DrCarryParam;
import com.jsjf.service.member.DrCarryParamService;
import com.mysql.fabric.xmlrpc.base.Data;

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
	public static int sendMsg(String mobile,String content) throws Exception{
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
		/*String username="";
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
	/**
	 * 创蓝营销短信
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 发送成功返回666，失败返回-666
	 * @throws Exception
	 */
	public static String sendMsgByMarketing(String mobile,String content) throws Exception{
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://zapi.253.com/msg/HttpBatchSendSM");
		
		try {
			NameValuePair[] data = {
					new NameValuePair("account", ""),
					new NameValuePair("pswd", ""),
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(false)),
					new NameValuePair("msg",URLEncoder.encode(content,"UTF-8") ),
					new NameValuePair("extno", "5415" ) };
			postMethod.setRequestBody(data);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);  
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
			int result = httpClient.executeMethod(postMethod);
			if (result == HttpStatus.SC_OK) {
				InputStream in = postMethod.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + postMethod.getStatusCode() + ":" + postMethod.getStatusText());
			}
		} finally {
			postMethod.releaseConnection();
		}
//		return null;
	}
	
	/**
	 * 创蓝营销短信
	 * 营销短信批量发送
	 */
	public static void  batchSMSMarketing(String[] arrayMobile,String content) throws Exception{
		StringBuffer mobile = new StringBuffer();
		for(int i=0;i<arrayMobile.length;i++){
			mobile.append(arrayMobile[i]).append(",");
			if(i!=0 && i%100==0){
				SmsSendUtil.sendMsgByMarketing(mobile.deleteCharAt(mobile.length()-1).toString(), content);
				mobile = new StringBuffer();
			}
		}
		if(mobile.length()>0){
			SmsSendUtil.sendMsgByMarketing(mobile.deleteCharAt(mobile.length()-1).toString(), content);
		}
		
	}
	
	public static void main(String[] args){
//		System.out.println(Utils.parseDate(Utils.format(new Date(), "yyyy-MM-dd 10:00:00"),"yyyy-MM-dd HH:mm:ss"));
//		sendMsg("15801868241", "测试及时");
		String str[] = {"18989302212",
				"18986731125",
				"18980720428",
				"18962173551",
				"18961605350",
				"18960268282",
				"18959103828",
				"18957062289",
				"18952036600",
				"18910401056",
				"18868102013",
				"18868024268",
				"18852116811",
				"18710073340",
				"18691407818",
				"18666079424",
				"18659539398",
				"18616016263",
				"18615059887",
				"18588931787",
				"18571038570",
				"18503931870",
				"18341887088",
				"18321434327",
				"18319075273",
				"18270295516",
				"18257104686",
				"18250793650",
				"18186485231",
				"18185052288",
				"18099596980",
				"18092055117",
				"18029314688",
				"18025108834",
				"18020515529",
				"18017463385",
				"18015025661",
				"17868885209",
				"17778060196",
				"17778010391",
				"17720585231",
				"17621110851",
				"17371406635",
				"15996325170",
				"15988107712",
				"15973386160",
				"15968101437",
				"15946846090",
				"15941672011",
				"15939815800",
				"15932751393",
				"15914841245",
				"15894685386",
				"15861801138",
				"15846420077",
				"15835541466",
				"15827522017",
				"15821764237",
				"15821192656",
				"15808965985",
				"15805194075",
				"15805167305",
				"15801809847",
				"15709919957",
				"15692109272",
				"15643992568",
				"15641707255",
				"15618011811",
				"15583160607",
				"15399555992",
				"15396576767",
				"15369140482",
				"15353709391",
				"15342402387",
				"15337280958",
				"15337112339",
				"15327485953",
				"15305770950",
				"15273173158",
				"15266535946",
				"15222899652",
				"15208480093",
				"15169060708",
				"15153242081",
				"15143970311",
				"15128971000",
				"15090797630",
				"15090638116",
				"15072348671",
				"15059318666",
				"15033099879",
				"15029413299",
				"15021338050",
				"15012695471",
				"15010348638",
				"15004595404",
				"15002156841",
				"15000829048",
				"13999198129",
				"13988471054",
				"13978288653",
				"13977280969",
				"13972304785",
				"13971148479",
				"13957785456",
				"13956471904",
				"13952238725",
				"13932697509",
				"13928624685",
				"13926192177",
				"13917401593",
				"13916785501",
				"13911703490",
				"13893882929",
				"13856103420",
				"13851728872",
				"13843938968",
				"13824515828",
				"13818245025",
				"13817960758",
				"13810590265",
				"13801792607",
				"13793226399",
				"13786795818",
				"13772883001",
				"13763928628",
				"13761331500",
				"13761264205",
				"13759055853",
				"13715522544",
				"13705942975",
				"13676529172",
				"13671168727",
				"13665612612",
				"13661003076",
				"13659928217",
				"13651701978",
				"13651612605",
				"13641780031",
				"13637244656",
				"13636871547",
				"13631316661",
				"13627282611",
				"13601830495",
				"13601681844",
				"13575312521",
				"13573860369",
				"13568906942",
				"13567952899",
				"13565834867",
				"13557789789",
				"13547807512",
				"13543326292",
				"13538457125",
				"13524243640",
				"13521360287",
				"13487424870",
				"13476571513",
				"13466210966",
				"13418949545",
				"13407175939",
				"13390187571",
				"13372633606",
				"13343458007",
				"13341031507",
				"13328011586",
				"13317502491",
				"13317191751",
				"13305632501",
				"13188691558",
				"13168090996",
				"13129019242",
				"13114942821",
				"13113783992"};
		try {
			for (int i = 0; i < str.length; i++) {
				int result = sendMsg(str[i], "以上信息为测试通道短信，请忽略。奖品将在活动结束后统一发放，详情致电：400-066-8969，官方微信：Byoupu");
				System.out.println("result:" + result);
				//定时发送
				//sendTimeMsg("13162327996","测试","2017-11-27 15:35:00"); 
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
