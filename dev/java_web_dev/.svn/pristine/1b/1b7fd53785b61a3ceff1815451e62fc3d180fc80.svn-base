package com.jsjf.service.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.JsMemberInfoDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.dao.product.JsProductPrizeLogDAO;
import com.jsjf.model.member.JsMemberInfo;
import com.jsjf.model.product.DrProductInvest;
import com.jsjf.model.product.JsProductPrizeLog;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.Utils;
import com.jsjf.dao.product.JsProductPrizeLogDAO;
import com.jsjf.model.product.JsProductPrizeLog;
import com.jsjf.service.product.JsProductPrizeLogService;
@Service
@Transactional
public class JsProductPrizeLogServiceImpl implements JsProductPrizeLogService {
	@Autowired
	private JsProductPrizeLogDAO jsProductPrizeLogDAO;
	@Autowired
	private JsMemberInfoDAO jsMemberInfoDAO;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	
	@Override
	public List<Map<String, Object>> selectListMap(Map<String, Object> map) {		
		return jsProductPrizeLogDAO.selectListMap(map);
	}

	@Override
	public int selectListMapCount(Map<String, Object> map) {		
		return jsProductPrizeLogDAO.selectListMapCount(map);
	}

	@Override
	public BaseResult selectPrizeLogByUid(Map<String, Object> map) {
		BaseResult br = new BaseResult();
//		//更新app2.0以前投即送的收货地址
//		//查询用户老地址，如果有，则为以前的投资记录更新上该地址
//		JsMemberInfo jsMemberInfo = jsMemberInfoDAO.selectOldAddressByUid(Integer.parseInt(map.get("uid").toString()));
//		if(Utils.isObjectNotEmpty(jsMemberInfo)){
//			Date addTime = jsMemberInfo.getUpdateTime() == null?jsMemberInfo.getAddTime():jsMemberInfo.getUpdateTime();
//			//查询老投资id
//			map.put("addTime", addTime);
//			List<DrProductInvest> investIdList = drProductInvestDAO.selectOldInvest(map);
//			for (int i = 0; i < investIdList.size(); i++) {
//				JsMemberInfo info = new JsMemberInfo();
//				//先把老地址更新了，剩下别的投资插入新地址
//				if(i == 0){
//					info.setInvestId(investIdList.get(i).getId());
//					info.setId(jsMemberInfo.getId());
//					jsMemberInfoDAO.updateJsMemberInfo(info);
//				}else{
//					info.setUid(Integer.parseInt(map.get("uid").toString()));
//					info.setName(jsMemberInfo.getName());
//					info.setPhone(jsMemberInfo.getPhone());
//					info.setAddress(jsMemberInfo.getAddress());
//					info.setInvestId(investIdList.get(i).getId());
//					jsMemberInfoDAO.insertJsMemberInfo(info);
//				}
//			}
//		}
		List<JsProductPrizeLog> logsList = jsProductPrizeLogDAO.selectPrizeLogByUid(map);
		map.clear();
		map.put("logsList", logsList);
		br.setMap(map);
		return br;
	}

	@Override
	public void insert(JsProductPrizeLog jsProductPrizeLog) {
		jsProductPrizeLogDAO.insert(jsProductPrizeLog);
	}


	@Override
	public List<JsProductPrizeLog> selectLogByUid(Integer uid) {
		Map<String ,Object> map= new HashMap<>();
		map.put("uid",uid);
		List<JsProductPrizeLog> logsList = jsProductPrizeLogDAO.selectLogByUid(map);
		return logsList;
	}

	@Override
	public List<JsProductPrizeLog> selectLogByPid(Integer pid) {
		Map<String ,Object> map= new HashMap<>();
		map.put("pid",pid);
		List<JsProductPrizeLog> logsList = jsProductPrizeLogDAO.selectLogByPid(map);
		return logsList;
	}
}
