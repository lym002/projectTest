package com.jsjf.service.activity.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsActivityHitIceLogDAO;
import com.jsjf.model.activity.JsActivityHitIceLog;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.activity.JsActivityHitIceLogService;
@Service
@Transactional
public class JsActivityHitIceLogServiceImpl implements
		JsActivityHitIceLogService {
	
	@Autowired
	JsActivityHitIceLogDAO jsActivityHitIceLogDAO;
	
	
	@Override
	public void insertHitIceChanceByRule(int uid, Date start, Date end) {
		Map<String,Object> map = new HashMap<String, Object>();
		Date now = new Date();
		
		if(now.before(end) && now.after(start) ){//活动期间
			List<JsActivityHitIceLog> chanceList = new ArrayList<JsActivityHitIceLog>();
			JsActivityHitIceLog chance;
			//1 登录
			map.put("uid", uid);
			map.put("addTime", now);
			map.put("start",start);
			map.put("end", end);
			map.put("type", 1);//登录
			
			int count  = jsActivityHitIceLogDAO.selectObjectCountByMap(map);
			
			if(count ==0){
				chance = new JsActivityHitIceLog(uid, 1, 1, null, now);
				chanceList.add(chance);
			}			
			
			//好友分享
			map.put("type", 2);//好友分享
			map.remove("addTime");
			count  = jsActivityHitIceLogDAO.selectObjectCountByMap(map);			
			int referrer = jsActivityHitIceLogDAO.selectActivityTimeInviteFriendsCount(map);
			
			if(referrer > count){
				if( referrer >0 && count<5 ){
					for (int i = 0; i < (referrer>5?5:referrer)-count; i++) {
						chance = new JsActivityHitIceLog(uid, 2, 10, null, now);
						chanceList.add(chance);
					}
				}
				if(referrer >5 && count<10){
					for (int i = 0; i < (referrer>10?10:referrer)-(count<5?5:count); i++) {
						chance = new JsActivityHitIceLog(uid, 2, 30, null, now);
						chanceList.add(chance);
					}
				}
				if(referrer >10 ){
					for (int i = 0; i < referrer-(count<10?10:count); i++) {
						chance = new JsActivityHitIceLog(uid, 2, 50, null, now);
						chanceList.add(chance);
					}
				}
			}		
			
			//投资
			map.put("type", 3);//好友分享
			List<Map<String,Object>> list = jsActivityHitIceLogDAO.selectActivityTimeNewInvest(map);
			
			if(!Utils.isEmptyList(list)){
				int amount;
				for (Map<String, Object> investMap : list) {
					int goldNum = 0;
					amount =((BigDecimal)investMap.get("amount")).intValue();
					if(amount > 100000){
						goldNum = 200;
					}else if(amount > 50000){
						goldNum = 120;
					}else if(amount > 20000){
						goldNum = 60;
					}else if(amount > 10000){
						goldNum = 40;
					}else if(amount > 1){
						goldNum = 20;
					}
					if(goldNum > 0){
						chance = new JsActivityHitIceLog(uid, 3, goldNum,Integer.parseInt( investMap.get("investId").toString()), now);
						chanceList.add(chance);
					}
				}
			}
			
			//批量添加
			if(!Utils.isEmptyList(chanceList)){
				jsActivityHitIceLogDAO.insertBatch(chanceList);
			}
						
		}
		
	}


	@Override
	public BaseResult selectHitIceParam(DrMember member, Date start, Date end) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("start",start);
		param.put("end", end);
		//活动期间参与人数
		map.put("partakeCount", jsActivityHitIceLogDAO.selectActivityTimePartakeCount(param));
		//用户数据初始化 0
		map.put("goldNum", 0);
		map.put("hitNum", 0);
		map.put("hitList", null);
		
		if(Utils.isObjectNotEmpty(member)){
			param.put("uid", member.getUid());
			
			map.put("goldNum", jsActivityHitIceLogDAO.selectActivityTimeGoldCount(param));
			
			param.put("noReceive", 1);
			map.put("hitNum", jsActivityHitIceLogDAO.selectObjectCountByMap(param));
			
			param.put("isReceive", 1);
			param.remove("noReceive");
			
			map.put("hitList", jsActivityHitIceLogDAO.selectObjectByMap(param));
		}
		
		result.setSuccess(true);
		result.setMap(map);
		return result;
	}
	@Override
	public BaseResult hitIce(DrMember member, Date start, Date end) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("start",start);
		param.put("end", end);
		param.put("noReceive", 1);
		param.put("uid", member.getUid());
		param.put("offset", 0);
		param.put("limit", 1);
		
		List<JsActivityHitIceLog> list = jsActivityHitIceLogDAO.selectObjectByMap(param);
		
		if(!Utils.isEmptyList(list)){
			map.put("goldNum", list.get(0).getGoldNum());
			jsActivityHitIceLogDAO.updateReceiveTime(list.get(0).getId());
			result.setMap(map);
			result.setSuccess(true);
		}else{
			result.setErrorMsg("没有机会");
		}
		
		return result;
	}

}
