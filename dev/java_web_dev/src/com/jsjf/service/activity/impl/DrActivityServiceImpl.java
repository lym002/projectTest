package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.JsSignInDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsSignIn;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
public class DrActivityServiceImpl implements DrActivityService {
	
	@Autowired
	private DrActivityDAO drActivityDAO;
	@Autowired 
	private RedisClientTemplate redisClientTemplate;
	@Autowired 
	private JsSignInDAO jsSignInDAO;

	@Override
	public DrActivity selectByPrimaryKey(Integer id) {
		return drActivityDAO.selectByPrimaryKey(id);
	}

	@Override
	public DrActivity selectObjectByName(String name) {
		
		return drActivityDAO.selectObjectByName(name);
	}

	@Override
	public BaseResult allPowerfullTop(DrMember member) throws Exception {
		BaseResult resutl = new BaseResult();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//签到
		Map<String,Object> signInParam = (Map<String,Object>)SerializeUtil.unserialize((byte[]) redisClientTemplate.get("signInParam".getBytes()));
		//老用户投资排行榜
		Map<String,Object> oldMemberParam = (Map<String,Object>)SerializeUtil.unserialize((byte[]) redisClientTemplate.get("oldMemberParam".getBytes()));
		//新用户投资排行榜
		Map<String,Object> newMemberParam = (Map<String,Object>)SerializeUtil.unserialize((byte[]) redisClientTemplate.get("newMemberParam".getBytes()));
		//邀请用户投资排行榜
		Map<String,Object> recommMemberParam = (Map<String,Object>)SerializeUtil.unserialize((byte[]) redisClientTemplate.get("recommMemberParam".getBytes()));
		
		if (Utils.isObjectEmpty(signInParam)) {
			signInParam = new HashMap<String, Object>();
			//签到处理
			List<Map<String,Object>> signList = jsSignInDAO.updateSignInTop(0);
			if(!Utils.isEmptyList(signList)){
				signInParam.put("signInList", signList.size()>10?new ArrayList<Map<String,Object>>(signList.subList(0, 10)):signList);//排行榜
				redisClientTemplate.setex("signInParam".getBytes(), 7200,SerializeUtil.serialize(signInParam));
			}
		}
		if (Utils.isObjectEmpty(oldMemberParam)) {
			//老用户投资排行榜
			oldMemberParam = new HashMap<String, Object>();
			List<Map<String,Object>> oldMemberList = jsSignInDAO.updateOldMemberTop();
			
			if(!Utils.isEmptyList(oldMemberList)){			
				for (Map<String, Object> map : oldMemberList) {
					oldMemberParam.put(map.get("uid").toString(), map);//没个推荐人的 排名,手机,uid,投资年化总额
				}
				oldMemberParam.put("oldMemberList", oldMemberList.size()>20?new ArrayList<Map<String,Object>>(oldMemberList.subList(0, 20)):oldMemberList);//排行榜
				redisClientTemplate.setex("oldMemberParam".getBytes(), 7200,SerializeUtil.serialize(oldMemberParam));
			}
		}
		if (Utils.isObjectEmpty(newMemberParam)) {
			//新用户投资排行榜
			newMemberParam = new HashMap<String, Object>();
			List<Map<String,Object>> newMemberList = jsSignInDAO.updateNewMemberTop();
			
			if(!Utils.isEmptyList(newMemberList)){			
				for (Map<String, Object> map : newMemberList) {
					newMemberParam.put(map.get("uid").toString(), map);//没个推荐人的 排名,手机,uid,投资年化总额
				}
				newMemberParam.put("newMemberList", newMemberList.size()>20?new ArrayList<Map<String,Object>>(newMemberList.subList(0, 20)):newMemberList);//排行榜
				redisClientTemplate.setex("newMemberParam".getBytes(), 7200,SerializeUtil.serialize(newMemberParam));
			}
		}
		if (Utils.isObjectEmpty(recommMemberParam)) {
			//邀请用户投资排行榜
			recommMemberParam = new HashMap<String, Object>();
			List<Map<String,Object>> recommMemberList = jsSignInDAO.updateRecommMemberTop();
			
			if(!Utils.isEmptyList(recommMemberList)){			
				for (Map<String, Object> map : recommMemberList) {
					recommMemberParam.put(map.get("uid").toString(), map);//没个推荐人的 排名,手机,uid,投资年化总额
				}
				recommMemberParam.put("recommMemberList", recommMemberList.size()>10?new ArrayList<Map<String,Object>>(recommMemberList.subList(0, 10)):recommMemberList);//排行榜
				redisClientTemplate.setex("recommMemberParam".getBytes(), 7200,SerializeUtil.serialize(recommMemberParam));
			}
		}
		
		resultMap.put("signInList", signInParam.get("signInList"));
		resultMap.put("oldMemberList", oldMemberParam.get("oldMemberList"));
		resultMap.put("newMemberList", newMemberParam.get("newMemberList"));
		resultMap.put("recommMemberList", recommMemberParam.get("recommMemberList"));
		
		
		resultMap.put("isSignIn", false);//默认可签到
		resultMap.put("signInNu", 0);
		if (Utils.isObjectNotEmpty(member)) {
			String sUid = member.getUid()+"";
			resultMap.put("oldInList", false);
			resultMap.put("newInList", false);
			resultMap.put("recommInList", false);
			
			if (oldMemberParam.containsKey(sUid)){
				Map<String,Object> mapt =   ((Map<String,Object>)oldMemberParam.get(sUid));
				if (Integer.parseInt(mapt.get("rownum").toString())<=20) {
					resultMap.put("oldInList", true);
				}
				resultMap.put("oldMemberInterest", mapt.get("interest"));
			}
			if (newMemberParam.containsKey(sUid)){
				Map<String,Object> mapt =   ((Map<String,Object>)newMemberParam.get(sUid));
				if (Integer.parseInt(mapt.get("rownum").toString())<=20) {
					resultMap.put("newInList", true);
				}
				resultMap.put("newMemberInterest", mapt.get("interest"));
			}
			if (recommMemberParam.containsKey(sUid)){
				Map<String,Object> mapt =   ((Map<String,Object>)recommMemberParam.get(sUid));
				if (Integer.parseInt(mapt.get("rownum").toString())<=10) {
					resultMap.put("recommInList", true);
				}
				resultMap.put("recommMemberNu", mapt.get("recommNu"));
			}
			
			//是否已签到过
			Map<String,Object> map = new HashMap<String, Object> ();
			Date now = new Date();
			map.put("uid", member.getUid());
			map.put("type", 0);
			map.put("signInTime", now);
			
			List<JsSignIn> list = jsSignInDAO.selectObjectByMap(map);
			resultMap.put("isSignIn",true);
			if(list.size()>0) {
				resultMap.put("signInNu", list.get(0).getSignNu());
				
				if (Utils.format(now, "yyyy-MM-dd").equals(Utils.format(list.get(0).getUpdateTime(), "yyyy-MM-dd"))) {
					resultMap.put("isSignIn", false);
				}
			}
			
			if (Utils.format(now, "yyyy-MM").equals(Utils.format(member.getRegDate(), "yyyy-MM"))) {
				resultMap.put("isNewMember", true);
			}else{
				resultMap.put("isNewMember", false);
			}
			
			resultMap.put("mobile", member.getMobilephone().substring(0, 3)+"****"+member.getMobilephone().substring(member.getMobilephone().length()-4));
		}
		
		resutl.setMap(resultMap);
		resutl.setSuccess(true);
		
		return resutl;
	}
	
	@Override
	public BaseResult signIn(int uid,int type,Date now) {
		BaseResult br = new BaseResult();
		if (now !=null) {
			Map<String,Object> map = new HashMap<String, Object> ();
			
			map.put("uid", uid);
			map.put("type", type);
			map.put("signInTime", now);
			boolean flag = redisClientTemplate.tryLock("signIn_0_"+uid, 3, TimeUnit.SECONDS,false);
			try {
				if (flag) {
					List<JsSignIn> list = jsSignInDAO.selectObjectByMap(map);
					JsSignIn signIn = new JsSignIn();
					if (Utils.isEmptyList(list)) {//当月首次签到
						signIn.setAddtime(now);
						signIn.setUpdateTime(now);
						signIn.setUid(uid);
						signIn.setType(type);
						signIn.setSignNu(1);
						
						jsSignInDAO.insert(signIn);
						br.setSuccess(true);
					}else if (list.size() == 1) {//当月非首次签到
						if (!Utils.format(now, "yyyy-MM-dd").equals(Utils.format(list.get(0).getUpdateTime(), "yyyy-MM-dd"))) {
							signIn.setId(list.get(0).getId());
							signIn.setUpdateTime(now);
							signIn.setSignNu(list.get(0).getSignNu()+1);
							
							jsSignInDAO.update(signIn);
							br.setSuccess(true);
						}else{
							br.setErrorMsg("签到失败:已经签到到过");
						}
					}else{
						br.setErrorMsg("签到失败:签到数据错误");
					}
				}else{
					br.setErrorMsg("签到失败:系统繁忙");
				}
			} catch (Exception e) {
				br.setErrorMsg("签到失败:系统异常");
			}finally{
				if (flag) {
					redisClientTemplate.del("signIn_0_"+uid);
				}
			}
		}else{
			br.setErrorMsg("签到失败:时间产数错误");
		}
		return br;
	}



}
