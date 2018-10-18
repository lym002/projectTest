package com.push.jpush.api;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.BaseResult;

import com.push.jpush.api.push.model.Options;
import com.push.jpush.api.push.model.Platform;
import com.push.jpush.api.push.model.PushPayload;
import com.push.jpush.api.push.model.PushPayload.Builder;
import com.push.jpush.api.push.model.audience.Audience;
import com.push.jpush.api.push.model.notification.Notification;



public class JiGuangConfig {  
     protected static final Logger log = Logger.getLogger(JiGuangConfig.class);  
  
  
 	
    public static final String TITLE = "币优铺理财";  
    public static final String ALERT = "祝大家新春快乐";  
    public static final String MSG_CONTENT = "币优铺理财祝新老客户新春快乐";  
    public static final String REGISTRATION_ID = "";  
    public static final String TAG = "tag_api";     
    //测试
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	private static final String appKey = "";
	private static final String masterSecret = "";
	
	//pro
	private static final String appKey_pro = "";
	private static final String masterSecret_pro = "";
	//精英
	private static final String appKey_elite = "";
	private static final String masterSecret_elite = ""; 
	public static final boolean APNS_PRODUCTION = true;  
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	 
	
	public static final String PROMPTLY_1 = "0";//及时
	public static final String PROMPTLY_2 = "1";//定时
	public static final String PROMPTLY_3 = "2";//定期
	public static final String IOS = "ios";
	public static final String ANDRIOD = "andriod";
	public static final String BROADCAST = "broadcast";
	public static final String FILECAST = "filecast";// 表示以 regids 推送
	
    
    public static void main(String[] args) {
    	
    
    	
	}
   
 
    /**
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public static JSONObject sendPush(Map<String,Object> map) throws Exception{
    	Platform platform = Platform.all();
    	Audience audience = null;
    	BaseResult result = null;
    	if(ANDRIOD.equals(map.get("deviceType"))){
    		platform = Platform.android();
    	}else if(IOS.equals(map.get("deviceType"))){
    		platform = Platform.ios();
    	}
    	
    	if(BROADCAST.equals((String)map.get("type"))){
    		audience = Audience.all();
    	}else if(FILECAST.equals((String)map.get("type"))){
    		List<String> listTokens = (List<String>) map.get("listTokens");
    		audience = Audience.registrationId(JSONArray.fromObject(listTokens));
    	}
    	
    	PushPayload payload = new Builder()
    	.setPlatform(platform)//所有用平台
    	.setAudience(audience)//指定群体
    	.setNotification(Notification.alert((String)map.get("text")))//推送类容
    	.build();
    	
    	JPushClient jpushClient = getCustomClient((String)map.get("appkey"),(String)map.get("appMasterSecret"));
    	if(PROMPTLY_1.equals((String)map.get("promptly"))){
    		result = pushMessage(jpushClient, payload);
    	}if(PROMPTLY_2.equals((String)map.get("promptly"))){
    		result = timedPushMessage(jpushClient, map, payload);
    	}if(PROMPTLY_3.equals((String)map.get("promptly"))){
    		result = timedRegularpushMessage(jpushClient, map, payload);
    	}
    	
    	JSONObject json = new JSONObject();
    	json.put("isOk", false);
    	if(result !=null){
    		if(200==result.getResponseCode()){
    			json.put("isOk", true);
    			JSONObject original = JSONObject.fromObject(result.getOriginalContent());
    			json.put("msgId", original.get("msg_id"));
    			json.put("scheduleId", original.get("schedule_id"));
    		}else{
    			System.out.println("极光推送失败:"+JSONObject.fromObject(result));
    		}
    	}
    	return json;
    }
    
  
    /**
     * 获取JPushClient
     * @return
     */
    public static JPushClient getCustomClient(String appKey,String masterSecret){
    	ClientConfig config = ClientConfig.getInstance();
		config.setMaxRetryTimes(5);
		config.setConnectionTimeout(10 * 1000);	// 10 seconds
		config.setSSLVersion("TLSv1.1");		// JPush server supports SSLv3, TLSv1, TLSv1.1, TLSv1.2

		return new JPushClient(masterSecret, appKey, null, config);
    }
    /**
     * 及时推送任务广播    
     * @throws Exception 
     * @throws APIConnectionException 
     */
    public static BaseResult pushMessage(JPushClient jpushClient,PushPayload payload) throws Exception{
//    	PushResult = jpushClient.sendPush(payload);
    	return jpushClient.sendPush(payload);
    }
    /**
     * 定期推送任务广播    
     * @throws Exception 
     * @throws APIConnectionException 
     */
    public static BaseResult timedRegularpushMessage(JPushClient jpushClient,Map<String,Object> map,PushPayload payload) throws Exception{
//    	ScheduleResult result = jpushClient.createDailySchedule(name, start, end, time, payload);
    	return jpushClient.createDailySchedule((String)map.get("name"),(String)map.get("start") , (String)map.get("end"), (String)map.get("time"), payload);
    
    }
    /**
     * 定时推送任务广播   
     * @throws Exception 
     * @throws APIConnectionException 
     */
    public static BaseResult timedPushMessage(JPushClient jpushClient,Map<String,Object> map,PushPayload payload) throws Exception{
//    	ScheduleResult result = jpushClient.createSingleSchedule(name, time, payload);
    	return jpushClient.createSingleSchedule((String)map.get("name"), (String)map.get("time"), payload);
    }
   
}  
