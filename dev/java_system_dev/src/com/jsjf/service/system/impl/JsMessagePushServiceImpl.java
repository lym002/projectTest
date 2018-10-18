package com.jsjf.service.system.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;

import com.push.jpush.api.JPushClient;
import com.push.jpush.api.JSpush;
import com.push.jpush.api.JiGuangConfig;
import com.push.jpush.api.push.PushResult;
import com.push.jpush.api.push.model.Options;
import com.push.jpush.api.push.model.Platform;
import com.push.jpush.api.push.model.PushPayload;
import com.push.jpush.api.push.model.PushPayload.Builder;
import com.push.jpush.api.push.model.audience.Audience;
import com.push.jpush.api.push.model.notification.Notification;
import com.push.jpush.api.schedule.ScheduleResult;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.system.JsMessagePushDAO;
import com.jsjf.dao.system.JsPushDeviceDAO;
import com.jsjf.model.system.JsMessagePush;
import com.jsjf.model.system.JsPushApp;
import com.jsjf.service.system.JsMessagePushService;
import com.push.umpush.UmengConfig;
@Service
@Transactional
public class JsMessagePushServiceImpl implements JsMessagePushService {
	private static Logger log = Logger.getLogger(JsMessagePushServiceImpl.class);
 	private static final String appKey = "7cf98762cf143e0ab34bc672";
 	private static final String masterSecret = "30ddab92b9c807c1f786bd9c";
	@Autowired
	JsMessagePushDAO jsMessagePushDAO;
	@Autowired
	JsPushDeviceDAO jsPushDeviceDAO;
	
	@Override
	public JsMessagePush selectObjectById(int id) {
		
		return jsMessagePushDAO.selectObjectById(id);
	}
	@Override
	public void insert(JsMessagePush jsMessagePush) {		
		jsMessagePushDAO.insert(jsMessagePush);
	}
	@Override
	public void update(JsMessagePush jsMessagePush) {
		
		jsMessagePushDAO.update(jsMessagePush);
	}
	@Override
	public PageInfo selectParamList(PageInfo info, JsMessagePush jsMessagePush) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("title", jsMessagePush.getTitle());
		map.put("startDate", jsMessagePush.getStartDate());
		map.put("endDate", jsMessagePush.getEndDate());
		map.put("status", jsMessagePush.getStatus());
		map.put("platform", jsMessagePush.getPlatform());
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit", info.getPageInfo().getLimit());
		map.put("orders", " status, id desc ");
		
		List<JsMessagePush> list = jsMessagePushDAO.selectObjectList(map);
		int count = jsMessagePushDAO.selectObjectListCount(map);
		
		info.setTotal(count);
		info.setRows(list);
		
		return info;
	}
	
	@Override
	public void updateByMap(Map<String, Object> map) {
		jsMessagePushDAO.updateByMap(map);
	}
	
	@Override
	public BaseResult executePush(JsMessagePush obj) {
		BaseResult result = new BaseResult();
		List<Integer> uids = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> pushMap = new HashMap<String, Object>();
		Integer audienceId = null;
		map.put("status", 1);
		map.put("device", obj.getPlatform());
		map.put("type", obj.getChannel());//push渠道 //0极光1友盟
		List<JsPushApp> jsPushAppList = jsPushDeviceDAO.selectAppList(map);

		pushMap.put("ticker", obj.getTitle());
		pushMap.put("title", obj.getTitle());
		pushMap.put("text", obj.getContent());
		if(obj.getUrl()!=null){
			pushMap.put("url", obj.getUrl());
		}
		if(obj.getType() == 0){//定期
			obj.setSendTime(new Time(new Date().getTime()));
			pushMap.put("promptly", JiGuangConfig.PROMPTLY_1);
		}else if(obj.getType() == 1){//定时
			pushMap.put("name", "jsPush_"+obj.getId());
			pushMap.put("time", Utils.format(obj.getSendStartDate(), "yyyy-MM-dd")+" "+Utils.format(obj.getSendTime(), "HH:mm:ss"));
			pushMap.put("promptly", JiGuangConfig.PROMPTLY_2);
		}else if(obj.getType() == 2 && obj.getChannel()==0){//只有极光才有定期推送
			pushMap.put("promptly", JiGuangConfig.PROMPTLY_3);
			pushMap.put("name", "jsPush_"+obj.getId());
			pushMap.put("time", Utils.format(obj.getSendTime(), "HH:mm:ss"));
			pushMap.put("start", Utils.format(obj.getSendStartDate(), "yyyy-MM-dd HH:mm:ss"));
			pushMap.put("end", Utils.format(obj.getSendEndDate(), "yyyy-MM-dd HH:mm:ss"));
		}else{
			result.setErrorMsg("失败:友盟没有定期任务");
			return result;
		}

		//判断是否按条件
		if(obj.isAudience()){//
			pushMap.put("type", JiGuangConfig.FILECAST);// 以指定人推送
			result = selectPush(obj);
			if(!result.isSuccess()){
				if(result.getErrorMsg() == null)
					result.setErrorMsg("失败");
				return result; 
			}
			uids = (List<Integer>) result.getMap().get("uids");
		}else{//全平台推送
			pushMap.put("type", JiGuangConfig.BROADCAST);// 广播
		}
		JSONObject json =null;
		int fail = 0;//失败次数
		StringBuffer msgId = new StringBuffer();
		StringBuffer scheduleId = new StringBuffer();
		int success = 0;
		
		long l1 = System.currentTimeMillis();
		for(JsPushApp app:jsPushAppList){
			json = null;
			pushMap.put("appkey", app.getAppKey());
			pushMap.put("appMasterSecret", app.getAppMasterSecret());
			pushMap.put("deviceType", app.getDevice()==2?JiGuangConfig.ANDRIOD:JiGuangConfig.IOS);//设备类型
		
			try {
				List<String> listTokens = null;
				List<Integer> listTokensId = null;
				if(JiGuangConfig.FILECAST.equals(pushMap.get("type")) && !Utils.isEmptyList(uids)){//非广播
					map.clear();
					map.put("uids", uids);
					map.put("appId", app.getId());
					
					listTokens = jsPushDeviceDAO.selectPushDevice(map);
					if(!Utils.isEmptyList(listTokens)){
						pushMap.put("listTokens", listTokens);					
						
						if(0 == app.getType()){//极光
							json = JiGuangConfig.sendPush(pushMap);
						}else if(1 == app.getType()){//友盟
							json = UmengConfig.sendPush(pushMap);
						}
					}else{
						continue;
					}
				}else if(JiGuangConfig.BROADCAST.equals(pushMap.get("type"))){//广播
					try {
						if(0 == app.getType()){//极光
							json = JiGuangConfig.sendPush(pushMap);
						}else if(1 == app.getType()){//友盟
							json = UmengConfig.sendPush(pushMap);
						}else{
							continue;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					continue;
				}
				
				if(json != null  ){
					if(json.getBoolean("isOk")){
						success++;
						if(json.containsKey("msgId")) msgId.append(json.get("msgId")+",");
						if(json.containsKey("scheduleId")) scheduleId.append(json.get("scheduleId")+",");
						
						//记录按条件push日志 
						if(listTokens !=null && listTokens.size()>0){
							listTokensId = jsPushDeviceDAO.selectPushDeviceId(map);
							
							Map<String,Object> deviceMap = new HashMap<String, Object>();
							deviceMap.put("appId", app.getId());
							deviceMap.put("pushId", obj.getId());
							deviceMap.put("ids", listTokensId);
							jsPushDeviceDAO.insertJsPushLog(deviceMap);
						}
					}else{
						fail++;
					}
				}
				log.info(json);
			} catch (Exception e) {
				fail++;
				log.error("-----push失败:[pushId="+obj.getId()+",appId="+app.getId()+",appName="+app.getAppName());
				e.printStackTrace();
			}
			
		}

		long l2 = System.currentTimeMillis();
		log.info("循环推送总时间"+(l2-l1));
		String resultMsg = "";
		if(success>0){
			resultMsg = "执行成功";
			result.setSuccess(true);
			obj.setStatus(1);
			obj.setScheduleId(scheduleId.append(msgId).toString());
			jsMessagePushDAO.update(obj);
			
			if(fail>0){
				resultMsg += " 失败:app-"+fail;
			}
		}else{
			resultMsg += "失败";
		}
		
		result.setMsg(resultMsg);
	
		return result;
	}

	public BaseResult selectPush(JsMessagePush jsMessagePush){
		long l1 = System.currentTimeMillis();
		BaseResult result = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<String, Object>();			
			List<Integer> list = new ArrayList<Integer>();
			
			map.put("channel", jsMessagePush.getChannel());
			
			//投资情况
			if(null != jsMessagePush.getIsInvest() ){
				map.put("isInvest", jsMessagePush.getIsInvest());
			}		
			//投资峰值
			if(jsMessagePush.getIsInvest() !=new Integer(0) && ( jsMessagePush.getInvestMax() != null  || jsMessagePush.getInvestMin() !=null)){
				map.put("investMax", jsMessagePush.getInvestMax());
				map.put("investMin", jsMessagePush.getInvestMin());				
			}			
			//账户福利
			if(null != jsMessagePush.getWeal()){
				map.put("isWeal", jsMessagePush.getWeal());				
			}			
			//账户余额
			if ((null != jsMessagePush.getBalanceMax() || null != jsMessagePush.getBalanceMin())) {
				map.put("balanceMax", jsMessagePush.getBalanceMax());
				map.put("balanceMin", jsMessagePush.getBalanceMin());				
			}
			
			//回款情况,近N天内有回款
			if(null != jsMessagePush.getPayment()){
				map.put("payment", jsMessagePush.getPayment());
			}
			
			//活跃情况
			if (null != jsMessagePush.getLiveness() && null != jsMessagePush.getLivenessType()){
				map.put("liveness", jsMessagePush.getLiveness());
				map.put("livenessType", jsMessagePush.getLivenessType());
			}

			list = jsMessagePushDAO.selectPushMember(map);
			if( list.size()>0){
				Map<String,Object> reMap = new HashMap<String, Object>();
				reMap.put("uids", list);//要推送的uid
				result.setMap(reMap);
				result.setSuccess(true);
				
			}else{
				result.setErrorMsg("无推送目标");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setErrorMsg("系统错误");
			e.printStackTrace();
		}
		log.info("总耗时时间"+(System.currentTimeMillis()-l1));
		return result;
	}
	

}
