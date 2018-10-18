package com.wechat.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.annotation.PostConstruct;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsjf.common.HttpClientUtils;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Component
public class WeixinUtil {
	private Logger log = Logger.getLogger(WeixinUtil.class);
	
	//正式微信公众号
	public final static String APPID = "wx327ee9b0f2699863";
	public final static String APPSECRET = "031a888c59f4ec96b97946fae12534e3";
	//测试
	//public final static String APPID = "wx9f027f81ebf0d635";
	//public final static String APPSECRET = "8711ccdd240a5c0d40846d7c4a6848f3";
	//回掉接口
	public final static String REDIRECT_URI = "http://www.zhigomgmt.com/zgweixin/weixin/getCode";
	/**
	 * 	微信推送消息接口
	 */
    public final static String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
    /**
     * 微信获取全局Access Token
     */
    public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
    /**
     * 微信获取code
     */
    public final static String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+REDIRECT_URI+"/oauth2.php&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    /**
     * 微信获取open id
     */
    public final static String GET_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APPSECRET+"&code={code}&grant_type=authorization_code";
    /**
     * 新增其他类型永久素材
     */
    public final static String UPLOADMEDIAURL = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token={ACCESS_TOKEN}";
	private static WeixinUtil instance;
	
	private static StringBuffer sb = null;
	
	public static String charset = "UTF-8";
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	private static WeixinUtil wxUtil;
	
	 @PostConstruct
	 private void init() {    
		 wxUtil = this;
     } 

	private WeixinUtil() {
	}

	public static synchronized WeixinUtil getInstance() {
		if (instance == null) {
			instance = new WeixinUtil();
		}
		return instance;
	}
	/**
	 * 获取全局Access Token
	 * @return
	 */
	private String getAccessToken(){
        String url = GET_ACCESS_TOKEN_URL;
        Map<String, String> params = new HashMap<String, String>();
        String result = null;
        result = HttpClientUtils.post(url, params);
        log.info("access token========" + result);
        return (String) JSONObject.fromObject(result).get("access_token");
	} 
	
	public String getTicket(){
		String result = HttpClientUtils.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+getAccessToken()+"&type=jsapi");
		return (String) JSONObject.fromObject(result).get("ticket");
	}
	public Map<String,Object> sign(String url, String ticket){
		Map<String,Object> map = new HashMap<String,Object>();
		String noncestr = "Wm3njdTPz0wzccnW";
		long timestamp=System.currentTimeMillis();
		String params = "jsapi_ticket="+ ticket +
				"&noncestr="+noncestr+
				"&timestamp="+timestamp+
				"&url="+url;
		map.put("sign", new SHA1().getDigestOfString(params.getBytes()));
		map.put("noncestr", noncestr);
		map.put("timestamp", timestamp);
		map.put("appid", APPID);
		return map;
	}

	/**
	 * 新增其他类型永久素材,这里说下，在上传视频素 
	 * 
	 * @param accessToken
	 * @param file
	 *            上传的文件
	 * @param title
	 *            上传类型为video的参数
	 * @param introduction
	 *            上传类型为video的参数
	 */
	public void uploadPermanentMedia2(String accessToken, File file,
			String title, String introduction, String type) {
		try {

			// 这块是用来处理如果上传的类型是video的类型的
			JSONObject j = new JSONObject();
			j.put("title", title);
			j.put("introduction", introduction);

			// 拼装请求地址
			String uploadMediaUrl = UPLOADMEDIAURL;
			uploadMediaUrl = uploadMediaUrl.replace("{ACCESS_TOKEN}",
					accessToken);

			URL url = new URL(uploadMediaUrl);
			String result = null;
			long filelength = file.length();
			String fileName = file.getName();
			String suffix = fileName.substring(fileName.lastIndexOf("."),
					fileName.length());
			/**
			 * 你们需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值
			 */
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			// 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议
			// boundary)，这里boundary 可以是任意的值(111,2222)都行
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：

			StringBuilder sb = new StringBuilder();
			// 这块是post提交type的值也就是文件对应的mime类型值
			sb.append("--"); // 必须多两道线
								// 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http
								// 协议头
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); // 这里是参数名，参数名和值之间要用两次
			sb.append(type + "\r\n"); // 参数的值

			// 这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");
			sb.append(j.toString() + "\r\n");

			/**
			 * 这里重点说明下，上面两个参数完全可以卸载url地址后面 就想我们平时url地址传参一样，
			 * http://api.weixin.qq.
			 * com/cgi-bin/material/add_material?access_token
			 * =##ACCESS_TOKEN##&type=""&description={} 这样，如果写成这样，上面的
			 * 那两个参数的代码就不用写了，不过media参数能否这样提交我没有试，感兴趣的可以试试
			 */
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			// 这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
					+ fileName + "\";filelength=\"" + filelength + "\" \r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			log.info(sb.toString());
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
			// 使用JSON-lib解析返回结果
			JSONObject jsonObject = JSONObject.fromObject(result);
			if (jsonObject.has("media_id")) {
				System.out.println("media_id:"
						+ jsonObject.getString("media_id"));
			} else {
				System.out.println(jsonObject.toString());
			}
			System.out.println("json:" + jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public void getTokenTimer(){
		Timer timer = new Timer(true);
		timer.schedule(new java.util.TimerTask(){
			public void run(){
				sb = null;
				sb = new StringBuffer(getAccessToken());
				wxUtil.redisClientTemplate.set("wxAccessToken", sb.toString());
			}
		},1,(2 * 60 * 60 * 1000) - 60000);
	}
	
	public static void main(String[] args){
		System.out.println("ticket==="+WeixinUtil.getInstance().getTicket());
	}

	public static StringBuffer getSb() {
		return sb;
	}
	

	
}
