package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsSignInDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrMemberRecommendedDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.activity.JsSignIn;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.activity.JsSignInService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class JsSignInServiceImpl implements JsSignInService {

	@Autowired
	public JsSignInDAO jsSignInDAO;
	@Autowired
	public RedisClientTemplate redisClientTemplate;
	@Autowired
	public DrMemberRecommendedDAO drMemberRecommendedDAO;
	@Autowired
	public DrProductInvestDAO drProductInvestDAO;
	@Autowired
	public DrMemberDAO drMemberDAO;
	
	@Override
	public BaseResult chiZhaInsert(Integer uid) {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		if(Utils.isObjectEmpty(uid)){
			br.setSuccess(false);
			br.setErrorCode("9998");
			return br;
		}
		boolean flag = redisClientTemplate.tryLock("signIn_0_"+uid, 3, TimeUnit.SECONDS,false);
		try {
			if(flag){
				//判断今天是否已签到
				map.put("uid", uid);
				map.put("type", 0);
				map.put("getType", "day");
				List<JsSignIn> daySignIn = jsSignInDAO.selectSameMonthSignInCount(map);
				if(daySignIn.size()>0){
					br.setSuccess(false);
					br.setErrorCode("1001");
					br.setErrorMsg("今天已经签过了！");
					return br;
				}
				//如果当月未签到则添加else更新签到次数
				map.put("getType", "month");
				List<JsSignIn> jsSignIn = jsSignInDAO.selectSameMonthSignInCount(map);
				if(jsSignIn.size() <= 0){
					JsSignIn signIn = new JsSignIn();
					signIn.setType(0);
					signIn.setSignNu(1);
					signIn.setUid(uid);
					signIn.setAddtime(new Date());
					signIn.setUpdateTime(new Date());
					jsSignInDAO.insert(signIn);
				}else{
					jsSignIn.get(0).setSignNu(jsSignIn.get(0).getSignNu()+1);
					jsSignIn.get(0).setUpdateTime(new Date());
					jsSignInDAO.update(jsSignIn.get(0));
				}
				br.setSuccess(true);
			}else{
				br.setErrorMsg("签到失败:系统繁忙");
			}
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}finally{
			if (flag) {
				redisClientTemplate.del("signIn_0_"+uid);
			}
		}
		return br;
	}

	@Override
	public BaseResult chiZhaIndex(Integer uid) {
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			Map<String,Object> param = new HashMap<String, Object>();
			byte[] appChiZha = redisClientTemplate.get("appChiZha".getBytes());
			if(Utils.isObjectNotEmpty(appChiZha)){
				Map<String,Object> byteMap = (Map<String,Object>) SerializeUtil.unserialize(appChiZha);
				param.put("jsSignInList", byteMap.get("jsSignInList"));
				param.put("referrerList", byteMap.get("referrerList"));
				param.put("vulgarTycoonList", byteMap.get("vulgarTycoonList"));
				param.put("thisMonthList", byteMap.get("thisMonthList"));
				//获取个人的四个榜数据
				if(Utils.isObjectNotEmpty(uid)){
//					param.put("jsSignIn", byteMap.get("jsSignIn"+uid));
					param.put("referrer", byteMap.get("referrer"+uid));
					param.put("vulgarTycoon", byteMap.get("vulgarTycoon"+uid));
					param.put("thisMonth", byteMap.get("thisMonth"+uid));
				}
			}else{
				Map<String,Object> byteMap = new HashMap<String, Object>();
				//获取最活跃榜
				map.put("type", 0);
				map.put("getType", "month");
				map.put("order", " order by a.signNu desc ,a.updateTime ");
				List<JsSignIn> jsSignInList = jsSignInDAO.selectSameMonthSignInCount(map);
				for (JsSignIn jsSignIn : jsSignInList) {
					Map<String,Object> item = new HashMap<String, Object>();
					item.put("signNu", jsSignIn.getSignNu());
					item.put("mobilePhone", jsSignIn.getMobilePhone());
					byteMap.put("jsSignIn"+jsSignIn.getUid(), item);
				}
				//获得最人气榜
				map.clear();
				List<Map<String,Object>> referrerList = drMemberRecommendedDAO.selectChiZhaIndex(map);
				for (Map<String, Object> item : referrerList) {
					Integer rownum = Integer.parseInt(item.get("rownum").toString());
					item.put("isListed", rownum <= 10?true:false);
					byteMap.put("referrer"+item.get("uid"), item);
				}
				//获得最新锐奖
				map.put("thisMonth", "yes");
				List<Map<String,Object>> thisMonthList = drProductInvestDAO.selectChiZhaList(map);
				for (Map<String, Object> item : thisMonthList) {
					Integer rownum = Integer.parseInt(item.get("rownum").toString());
					item.put("isListed", rownum <= 20?true:false);
					byteMap.put("thisMonth"+item.get("uid"), item);
				}
				//获得最土豪榜
				map.clear();
				map.put("thisMonth", "no");
				List<Map<String,Object>>  vulgarTycoonList = drProductInvestDAO.selectChiZhaList(map);
				for (Map<String, Object> item : vulgarTycoonList) {
					Integer rownum = Integer.parseInt(item.get("rownum").toString());
					item.put("isListed", rownum <= 20?true:false);
					byteMap.put("vulgarTycoon"+item.get("uid"), item);
				}
				//序列化的map
				byteMap.put("jsSignInList", jsSignInList.size()>10?new ArrayList<JsSignIn>(jsSignInList.subList(0, 10)):jsSignInList);
				byteMap.put("referrerList", referrerList.size()>10?new ArrayList<Map<String,Object>>(referrerList.subList(0, 10)):referrerList);
				byteMap.put("vulgarTycoonList", vulgarTycoonList.size()>20?new ArrayList<Map<String,Object>>(vulgarTycoonList.subList(0, 20)):vulgarTycoonList);
				byteMap.put("thisMonthList", thisMonthList.size()>20?new ArrayList<Map<String,Object>>(thisMonthList.subList(0, 20)):thisMonthList);
				//返回的map
				param.put("jsSignInList", jsSignInList.size()>10?new ArrayList<JsSignIn>(jsSignInList.subList(0, 10)):jsSignInList);
				param.put("referrerList", referrerList.size()>10?new ArrayList<Map<String,Object>>(referrerList.subList(0, 10)):referrerList);
				param.put("vulgarTycoonList", vulgarTycoonList.size()>20?new ArrayList<Map<String,Object>>(vulgarTycoonList.subList(0, 20)):vulgarTycoonList);
				param.put("thisMonthList", thisMonthList.size()>20?new ArrayList<Map<String,Object>>(thisMonthList.subList(0, 20)):thisMonthList);
				//获取个人的四个榜数据
				if(Utils.isObjectNotEmpty(uid)){
//					param.put("jsSignIn", byteMap.get("jsSignIn"+uid));
					param.put("referrer", byteMap.get("referrer"+uid));
					param.put("vulgarTycoon", byteMap.get("vulgarTycoon"+uid));
					param.put("thisMonth", byteMap.get("thisMonth"+uid));
				}
				redisClientTemplate.setex("appChiZha".getBytes(),7200 ,SerializeUtil.serialize(byteMap));
			}
			//判断今天是否已签到
			boolean isSignIn = false;
			if(Utils.isObjectNotEmpty(uid)){
				map.clear();
				map.put("uid", uid);
				map.put("type", 0);
				map.put("getType", "day");
				List<JsSignIn> daySignIn = jsSignInDAO.selectSameMonthSignInCount(map);
				map.put("getType", "month");
				List<JsSignIn> monThSignIn = jsSignInDAO.selectSameMonthSignInCount(map);
				Integer signNu = 0;
				if(daySignIn.size()>0){
					isSignIn = true;
				}
				if(monThSignIn.size()>0){
					signNu = monThSignIn.get(0).getSignNu();
				}
				DrMember m = drMemberDAO.selectByPrimaryKey(uid);
				JsSignIn signIn = new JsSignIn();
				signIn.setSignNu(signNu);
				signIn.setMobilePhone(m.getMobilephone().substring(0, 3) + "****" + m.getMobilephone().substring(7, m.getMobilephone().length()));
				param.put("jsSignIn", signIn);
			}
			param.put("isSignIn", isSignIn);
			//查询是否为新用户
			param.put("isNewUser", drMemberDAO.selectDrMemberByMonth(uid)!=null?true:false);
			
			br.setMap(param);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

}
