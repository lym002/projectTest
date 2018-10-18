package com.push.jpush.api;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;

import com.push.jpush.api.device.DeviceClient;
import com.push.jpush.api.device.TagAliasResult;
import com.push.jpush.api.device.TagListResult;
import com.push.jpush.api.push.PushResult;
import com.push.jpush.api.push.model.Message;
import com.push.jpush.api.push.model.Options;
import com.push.jpush.api.push.model.Platform;
import com.push.jpush.api.push.model.PushPayload;
import com.push.jpush.api.push.model.audience.Audience;
import com.push.jpush.api.push.model.audience.AudienceTarget;
import com.push.jpush.api.push.model.notification.AndroidNotification;
import com.push.jpush.api.push.model.notification.IosNotification;
import com.push.jpush.api.push.model.notification.Notification;
import com.push.jpush.api.schedule.ScheduleListResult;
import com.push.jpush.api.schedule.ScheduleResult;



public class JSpush {  
     protected static final Logger log = Logger.getLogger(JSpush.class);  
  
     // demo App defined in resources/jpush-api.conf   

 	
    public static final String TITLE = "币优铺理财";  
    public static final String ALERT = "祝大家新春快乐";  
    public static final String MSG_CONTENT = "币优铺理财祝新老客户新春快乐";  
    public static final String REGISTRATION_ID = "0900e8d85ef";  
    public static final String TAG = "tag_api";  
      
   //生产
   //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
// 	private static final String appKey = "58c0efcfe604c287091b7e34";
// 	private static final String masterSecret = "ce6476b13d87431011cbe252";
    
// 	private static final String appKey_pro = "1d5af8af2629784ae8ae8f5c";
// 	private static final String masterSecret_pro = "1847b4c7549a35857bb4d83e";
    
// 	private static final String appKey_elite = "79bc9844e3c1a8cc2c4770fb";
// 	private static final String masterSecret_elite = "b5add353890eda115366eb5d";
    
//  public static final boolean APNS_PRODUCTION = true;  //正式
  
// 	
    //+++++++++++++++++--- 分  ---- 割 --- 线++++++++++++++++++++++++++++ 
   
    //测试
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	private static final String appKey = "2601d404b0c6ee92f0821c991";
	private static final String masterSecret = "e83601a51c98f2676c50dbec";
	
	//pro
	private static final String appKey_pro = "9a99e7b1f8d21f1df7888f38";
	private static final String masterSecret_pro = "1f7ce9b16bb3db791a3fb048";
	//精英
	private static final String appKey_elite = "c466234cf0451bc5955449f9";
	private static final String masterSecret_elite = "36c84859b8ac430e69127188"; 
	public static final boolean APNS_PRODUCTION = false;  
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	 
     
    
    //shuchang jushengceshi
// 	private static final String appKey = "2c1abeb446e8ab4d4e1a0e06";
// 	private static final String masterSecret = "676a24887cb23050f851dd25";
     
// 	private static final String appKey = "9fb58dc14e4c8b2bc6642f90";
// 	private static final String masterSecret = " 6ae194960345f299c8a1baf0";
     

     

    
    public static void main(String[] args) {
    	
    	
    	
    	//=========删除tag
    	
    	
    	
//    	DeviceClient dc = new DeviceClient(masterSecret, appKey);    	
//    	
//    	try {
//    		DefaultResult dr = dc.deleteTag("tag_1", null);
//    		System.out.println(dr);
//    	} catch (APIConnectionException e) {
//    		e.printStackTrace();
//    	} catch (APIRequestException e) {
//    		e.printStackTrace();
//    	}
    	
    	
    	//================获取 registerId 下关联的标签别名     	
    	
//    	DeviceClient dc = new DeviceClient(masterSecret, appKey);    	
//    	
//    	try {
//    		TagAliasResult dr = dc.getDeviceTagAlias("1a0018970a945f2ba08");
//    		System.out.println(dr);
//    	} catch (APIConnectionException e) {
//    		e.printStackTrace();
//    	} catch (APIRequestException e) {
//    		e.printStackTrace();
//    	}
    	
    	
    	//================获取应用下的 tag list
    	
    	
//    	DeviceClient dc = new DeviceClient(masterSecret, appKey);    	
//    	
//    	try {
//    		TagListResult dr = dc.getTagList();
//			System.out.println(dr);
//		} catch (APIConnectionException e) {
//			e.printStackTrace();
//		} catch (APIRequestException e) {
//			e.printStackTrace();
//		}
//    	
    	
    	
    	
    	//================给tag 添加 社备
    	
//    	DeviceClient dc = new DeviceClient(masterSecret, appKey);
//    	
//    	Set<String> addUser = new HashSet<String>();
//    	addUser.add("160a3797c836747d4ca");
//    	addUser.add("1a0018970a945f2ba081");
//    	
//    	try {
//			DefaultResult dr = dc.addRemoveDevicesFromTag("tag_1",addUser,null);
//			System.out.println(dr);
//		} catch (APIConnectionException e) {
//			e.printStackTrace();
//		} catch (APIRequestException e) {
//			e.printStackTrace();
//		}
//    	
    	
    	
    	
    	
    	
    	//==================================

//    	PushPayload payload = PushPayload.alertAll("币优铺理财-邀请好有投资享最高2%返现");
//    	
//    	pushMessageAll(payload);
//   	timedPushMessageAll("币优铺理财", "2017-03-10 15:42:00", payload);
//    	timedPushMessageAll("币优铺理财", "2017-03-10 15:50:00", payload);
//    	
    	testGetScheduleList();
    	
    //=========================================	
    	
    	
    	
	}
    
    /**
     * 添加删除 用户
     */
    public static boolean addRemoveDevicesFromTag(String tag,Set<String> addUser,Set<String> removeUser){
    	List<DeviceClient> list = getDeviceClientList();
    	boolean flag = false;
    	for(int i=0;i<list.size();i++){
    		try {
    			list.get(i).addRemoveDevicesFromTag(tag,addUser,null);
    			flag = true;
    		} catch (Exception e) {
    			log.error("没有对像或系统错误listIndex:"+i+"-->"+e.getMessage(), e);
    		}
    	}
    	return true;
    }
    
    /**
     * 查询设备客户端
     * @return
     */
    public static List<DeviceClient> getDeviceClientList(){
    	List<DeviceClient> list = new ArrayList<DeviceClient>();
    	list.add(new DeviceClient(masterSecret, appKey));
    	list.add(new DeviceClient(masterSecret_pro, appKey_pro));
    	list.add(new DeviceClient(masterSecret_elite, appKey_elite));
    	return list;
    }
    
    
    //查询所有定时任务
    public static void testGetScheduleList() {
        int page = 1;
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        try {
            ScheduleListResult list = jpushClient.getScheduleList(page);
            log.info("total " + list.getTotal_count()); 
            for(ScheduleResult s : list.getSchedules()) {
            	log.info(s.toString());
            }
        } catch (APIConnectionException e) {
        	log.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
        	log.error("Error response from JPush server. Should review and fix it. ", e);
        	log.info("HTTP Status: " + e.getStatus());
        	log.info("Error Code: " + e.getErrorCode());
        	log.info("Error Message: " + e.getErrorMessage());
        }
    }
    //删除定时任务
    public static void testDeleteSchedule() {
        String scheduleId = "95bbd066-3a88-11e5-8e62-0021f652c102";
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        try {
            jpushClient.deleteSchedule(scheduleId);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
        	log.error("Error response from JPush server. Should review and fix it. ", e);
        	log.info("HTTP Status: " + e.getStatus());
        	log.info("Error Code: " + e.getErrorCode());
        	log.info("Error Message: " + e.getErrorMessage());
        }
    }
    
    
    /**
     * 获取JPushClient
     * @return
     */
    public static JPushClient getCustomClient(){
    	ClientConfig config = ClientConfig.getInstance();
		config.setMaxRetryTimes(5);
		config.setConnectionTimeout(10 * 1000);	// 10 seconds
		config.setSSLVersion("TLSv1.1");		// JPush server supports SSLv3, TLSv1, TLSv1.1, TLSv1.2

		return new JPushClient(masterSecret, appKey, null, config);
    }
    /**
     * 及时推送任务广播
     * @param alert
     * @return
     */
    public static Map<String,Object> pushMessageAll(PushPayload payload){
    	JPushClient jpushClient = JSpush.getCustomClient();     
    	Map<String,Object> map = new HashMap<String, Object>();
    	try {
    		PushResult result = jpushClient.sendPush(payload);
    		map.put("msgId", result.msg_id);
    		map.put("sendno", result.sendno);
    		map.put("statusCode", result.statusCode);
    		map.put("success", true);
    	} catch (APIConnectionException e) {
    		log.error(e.getMessage(), e);
    		map.put("success", false);
    	} catch (APIRequestException e) {
    		map.put("success", false);
    		log.error(e.getMessage(), e);
    	}
    	return map;
    }
    /**
     * 定期推送任务广播
     * @param name
     * @param start
     * @param end
     * @param time
     * @param alert
     * @return
     */
    public static Map<String,Object> timedRegularpushMessageAll(String name,String start,String end,String time,PushPayload payload){
    	JPushClient jpushClient = JSpush.getCustomClient();     
    	Map<String,Object> map = new HashMap<String, Object>();
    	try {
    		ScheduleResult result = jpushClient.createDailySchedule(name, start, end, time, payload);
    		map.put("scheduleId", result.getSchedule_id());
    		map.put("scheduleName", result.getName());
    		map.put("success", true);
    	} catch (APIConnectionException e) {
    		log.error(e.getMessage(), e);
    		map.put("success", false);
    	} catch (APIRequestException e) {
    		map.put("success", false);
    		log.error(e.getMessage(), e);
    	}
    	return map;
    }
    /**
     * 定时推送任务广播
     * @param name
     * @param time
     * @param alert
     * @return
     */
    public static Map<String,Object> timedPushMessageAll(String name,String time,PushPayload payload){
    	JPushClient jpushClient = JSpush.getCustomClient();     
    	Map<String,Object> map = new HashMap<String, Object>();
    	try {
    		ScheduleResult result = jpushClient.createSingleSchedule(name, time, payload);
    		map.put("scheduleId", result.getSchedule_id());
    		map.put("scheduleName", result.getName());
    		map.put("success", true);
    	} catch (APIConnectionException e) {
    		log.error(e.getMessage(), e);
    		map.put("success", false);
    	} catch (APIRequestException e) {
    		map.put("success", false);
    		log.error(e.getMessage(), e);
    	}
    	return map;
    }
    
    /**
     * 获取JPushClients :android,winphone,iphone的三个版本本
     * @return
     */
    public static List<JPushClient> getCustomClients(){
    	ClientConfig config = ClientConfig.getInstance();
		config.setMaxRetryTimes(5);
		config.setConnectionTimeout(10 * 1000);	// 10 seconds
		config.setSSLVersion("TLSv1.1");		// JPush server supports SSLv3, TLSv1, TLSv1.1, TLSv1.2
		List<JPushClient> list = new ArrayList<JPushClient>();
		
		list.add(new JPushClient(masterSecret, appKey, null, config));
		list.add(new JPushClient(masterSecret_elite, appKey_elite, null, config));
		list.add(new JPushClient(masterSecret_pro, appKey_pro, null, config));
		
		return list;
    }
    /**
     * 及时推送任务广播:android,winphone,iphone的三个版本本
     * @param alert
     * @return
     */
    public static Map<String,Object> pushMessageAlls(PushPayload payload){
    	List<JPushClient> list = JSpush.getCustomClients();     
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("success", false);
    	String msgId = "";
    	String sendno = "";
    	for(int i=0;i<list.size();i++){
	    	try {
	    		PushResult result = list.get(i).sendPush(payload);
	    		msgId += result.msg_id+",";
	    		sendno += result.sendno+",";
	    		map.put("success", true);
	    	} catch (APIConnectionException e) {
	    		log.error("listIndex:"+i+"-->"+e.getMessage(), e);
//	    		System.out.println("listIndex:"+i+"-->"+e.getMessage());
	    	} catch (APIRequestException e) {
	    		log.error("listIndex:"+i+"-->"+e.getMessage(), e);
//	    		System.out.println("listIndex:"+i+"-->"+e.getMessage());
	    	}
    	}
    	map.put("msgId", msgId);
		map.put("sendno", sendno);
    	return map;
    }
    /**
     * 定期推送任务广播:android,winphone,iphone的三个版本
     * @param name
     * @param start
     * @param end
     * @param time
     * @param alert
     * @return
     */
    public static Map<String,Object> timedRegularpushMessageAlls(String name,String start,String end,String time,PushPayload payload){
    	List<JPushClient> list = JSpush.getCustomClients();      
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("success", false);
    	String scheduleId = "";
    	String sendno = "";
    	for(int i=0;i<list.size();i++){
	    	try {
	    		ScheduleResult result = list.get(i).createDailySchedule(name, start, end, time, payload);
	    		scheduleId += result.getSchedule_id()+",";
	    		map.put("success", true);
	    	} catch (APIConnectionException e) {
	    		log.error("listIndex:"+i+"-->"+e.getMessage(), e);
	    	} catch (APIRequestException e) {
	    		log.error("listIndex:"+i+"-->"+e.getMessage(), e);
	    	}
    	}
    	map.put("scheduleId", scheduleId);
    	return map;
    }
    /**
     * 定时推送任务广播:android,winphone,iphone的三个版本
     * @param name
     * @param time
     * @param alert
     * @return
     */
    public static Map<String,Object> timedPushMessageAlls(String name,String time,PushPayload payload){
    	List<JPushClient> list = JSpush.getCustomClients();      
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("success", false);
    	String scheduleId = "";
    	String sendno = "";
    	for(int i=0;i<list.size();i++){
	    	try {
	    		ScheduleResult result = list.get(i).createSingleSchedule(name, time, payload);
	    		scheduleId += result.getSchedule_id()+",";
	    		map.put("success", true);
	    	} catch (APIConnectionException e) {
	    		log.error("listIndex:"+i+"-->"+e.getMessage(), e);
	    	} catch (APIRequestException e) {
	    		log.error("listIndex:"+i+"-->"+e.getMessage(), e);
	    	}
    	}
    	map.put("scheduleId", scheduleId);
    	return map;
    }
    
    
    
      
    public static PushPayload buildPushObject_all_all_alert() {  
        return PushPayload.alertAll(ALERT);  
    }  
      
    public static PushPayload buildPushObject_all_alias_alert(String alert) {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.all())//设置接受的平台  
                .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到  
                .setNotification(Notification.alert(alert))  
                .build();  
    }  
      
    public static PushPayload buildPushObject_android_tag_alertWithTitle(String alert,String msgContent) {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.android())  
                .setAudience(Audience.all())  
                .setNotification(Notification.android(alert, msgContent, null))  
                .build();  
    }  
      
    public static PushPayload buildPushObject_android_and_ios() {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.android_ios())  
                .setAudience(Audience.tag("tag1"))  
                .setNotification(Notification.newBuilder()  
                        .setAlert("alert content")  
                        .addPlatformNotification(AndroidNotification.newBuilder()  
                                .setTitle("Android Title").build())  
                        .addPlatformNotification(IosNotification.newBuilder()  
                                .incrBadge(1)  
                                .addExtra("extra_key", "extra_value").build())  
                        .build())  
                .build();  
    }  
      
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String alert,String msgContent) {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.ios())  
                .setAudience(Audience.tag_and("tag1", "tag_all"))  
                .setNotification(Notification.newBuilder()  
                        .addPlatformNotification(IosNotification.newBuilder()  
                                .setAlert(alert)  
                                .setBadge(5)  
                                .setSound("happy")  
                                .addExtra("from", "JPush")  
                                .build())  
                        .build())  
                 .setMessage(Message.content(msgContent))  
                 .setOptions(Options.newBuilder()  
                         .setApnsProduction(true)  
                         .build())  
                 .build();  
    }  
      
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String msgContent) {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.android_ios())  
                .setAudience(Audience.newBuilder()  
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))  
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))  
                        .build())  
                .setMessage(Message.newBuilder()  
                        .setMsgContent(msgContent)  
                        .addExtra("from", "JPush")  
                        .build())  
                .build();  
    }  
}  
