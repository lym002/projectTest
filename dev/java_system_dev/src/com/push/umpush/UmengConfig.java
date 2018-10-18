package com.push.umpush;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.push.jpush.api.report.MessagesResult.Ios;
import com.jsjf.common.Utils;
import com.push.umpush.android.AndroidBroadcast;
import com.push.umpush.android.AndroidFilecast;
import com.push.umpush.android.AndroidUnicast;
import com.push.umpush.ios.IOSBroadcast;
import com.push.umpush.ios.IOSFilecast;

public class UmengConfig {
	private static Logger logger = Logger.getLogger(UmengConfig.class);
	
	private static String appkey = "";
	private static String appMasterSecret = "";
	private static String timestamp = null;
	private static boolean productionMode = false ;//ios有效，false 测试true 生产
	private static PushClient client = new PushClient();
	private static UmengConfig UmengConfig = new UmengConfig();
	
	public static final String IOS = "ios";
	public static final String ANDRIOD = "andriod";
	public static final String BROADCAST = "broadcast";
	public static final String FILECAST = "filecast";
	
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("deviceType", ANDRIOD);
			map.put("type", BROADCAST);
			map.put("appkey", appkey);
			map.put("appMasterSecret", appMasterSecret);
			map.put("ticker", "币优铺理财");
			map.put("title", "三重好礼开始啦");
			map.put("text", "邀请好友享三重好礼!");
			map.put("deviceToken", "Ak7GOZJ4KFN6hHWVc257UyQGcRJxclr8xNawSY25CfBr");
			
			JSONObject result = sendPush(map);
			
			logger.info(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static JSONObject sendPush(Map<String,Object> map) throws Exception{
		String result = null;
		JSONObject json = new JSONObject();
		json.put("isOk", false);
		JSONObject resultJson = null;
		map.put("startTime", map.get("time"));
		if(BROADCAST.equals((String)map.get("type"))){
			result =  UmengConfig.sendBroadcast(map);
		}else if(FILECAST.equals((String)map.get("type"))){
			List<String> listTokens = (List<String>) map.get("listTokens");
			StringBuffer tokens = new StringBuffer();
			for (String s : listTokens) {
				tokens.append(s).append("\n");
			}
			map.put("tokens", tokens.toString());
			result =  UmengConfig.sendFilecast(map);
		}
		if(result !=null && Utils.isObjectNotEmpty(resultJson = JSONObject.fromObject(result))){
			if("SUCCESS".equals(resultJson.get("ret"))){
				json.put("isOk", true);
				json.put("scheduleId", resultJson.getJSONObject("data").get("task_id"));
			}else{
				logger.info(result);
			}
		}
		
		return json;
	}
	
	
	
	/**
	 * 文件播
	 * @param map{deviceType=设备类型}
	 * @return
	 * @throws Exception
	 */
	public String sendFilecast(Map<String,Object> map) throws Exception {
		UmengNotification filecast = null;
		String fileId = client.uploadContents((String)map.get("appkey"),(String)map.get("appMasterSecret"),(String)map.get("tokens"));
		if(ANDRIOD.equals(map.get("deviceType"))){
			filecast = new AndroidFilecast((String)map.get("appkey"),(String)map.get("appMasterSecret"));
			((AndroidFilecast) filecast).setFileId( fileId);
			((AndroidFilecast) filecast).setTicker((String)map.get("ticker"));
			((AndroidFilecast) filecast).setTitle( (String)map.get("title"));//"中文的title"
			if(map.get("url")!=null && !map.get("url").equals("")){
				((AndroidFilecast) filecast).goUrlAfterOpen((String)map.get("url"));//"after_open"
			}else{
				((AndroidFilecast) filecast).goAppAfterOpen();
			}
			((AndroidFilecast) filecast).setText( (String)map.get("text")  );//"Android filecast text"
			((AndroidFilecast) filecast).setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		}else if(IOS.equals(map.get("deviceType"))){
			filecast = new IOSFilecast((String)map.get("appkey"),(String)map.get("appMasterSecret"));
			((IOSFilecast) filecast).setFileId( fileId);
			((IOSFilecast) filecast).setAlert((String)map.get("text"));
			if(map.get("url")!=null && !map.get("url").equals("")){
				((IOSFilecast) filecast).setCustomizedField("url", (String)map.get("url"));
				((IOSFilecast) filecast).setCustomizedField("title", (String)map.get("title"));
			}
			((IOSFilecast) filecast).setBadge( 0);
			((IOSFilecast) filecast).setSound( "default");
		}else{
			return null;
		}
		
		filecast.setProductionMode(productionMode);//false 测试true 生产
		
		//描述
		filecast.setDescription((String)map.get("description") );
		//发送策略 非必填
		filecast.setStartTime((String)map.get("startTime") );//定时发送时间，若不填写表示立即发送
		filecast.setExpireTime((String)map.get("expire_time") );//消息过期时间,其值不可小于发送时间或者
		filecast.setMaxSendNum(100);//发送限速，每秒发送的最大条数。
		return client.sends(filecast);
	}

	
	/**
	 * 广播
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String sendBroadcast(Map<String,Object> map) throws Exception {
		UmengNotification broadcast = null;
		if(ANDRIOD.equals(map.get("deviceType"))){
			broadcast = new AndroidBroadcast((String)map.get("appkey"),(String)map.get("appMasterSecret"));
			((AndroidNotification) broadcast).setTicker((String)map.get("ticker"));                                 
			((AndroidNotification) broadcast).setTitle( (String)map.get("title"));//"中文的title"                      
			((AndroidNotification) broadcast).setText( (String)map.get("text"));//"Android filecast text"         
			if(map.get("url")!=null && !map.get("url").equals("")){
				((AndroidNotification) broadcast).goUrlAfterOpen((String)map.get("url"));//"after_open"
			}else{
				((AndroidNotification) broadcast).goAppAfterOpen();
			}

			((AndroidNotification) broadcast).setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		}else if(IOS.equals(map.get("deviceType"))){
			broadcast = new IOSBroadcast((String)map.get("appkey"),(String)map.get("appMasterSecret"));
			((IOSNotification) broadcast).setAlert((String)map.get("text"));
			((IOSNotification) broadcast).setBadge( 0);
			if(map.get("url")!=null && !map.get("url").equals("")){
				((IOSNotification) broadcast).setCustomizedField("url", (String)map.get("url"));
				((IOSNotification) broadcast).setCustomizedField("title", (String)map.get("title"));
			}
			((IOSNotification) broadcast).setSound( "default");
			broadcast.setTestMode();
		}else{
			return null;
		}
		
		broadcast.setProductionMode(productionMode);//false 测试true 生产
		//发送策略 非必填
		broadcast.setStartTime((String)map.get("startTime") );//定时发送时间，若不填写表示立即发送
		broadcast.setExpireTime((String)map.get("expire_time") );//消息过期时间,其值不可小于发送时间或者
		broadcast.setMaxSendNum(100);//发送限速，每秒发送的最大条数。
		return client.sends(broadcast);
	}
	/**
	 * 广播
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void sendIOSBroadcast() throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge( 0);
		broadcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}
	
	
	
	
	
	public  static void sendAndroidUnicast() throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( "Ak7GOZJ4KFN6hHWVc257UyQGcRJxclr8xNawSY25CfBr");
		unicast.setTicker( "币优铺理财欢迎您");
		unicast.setTitle(  "币优铺理财-邀请好友三重礼");
		unicast.setText(   "币优铺理财:邀请好友三重礼正在进行中...");
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}
	
}
