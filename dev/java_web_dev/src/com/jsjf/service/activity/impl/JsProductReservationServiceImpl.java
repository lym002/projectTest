package com.jsjf.service.activity.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsProductReservationDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.product.JsActivityProductDAO;
import com.jsjf.model.activity.JsProductReservation;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.activity.JsProductReservationService;
@Service
@Transactional
public class JsProductReservationServiceImpl implements
		JsProductReservationService {
	@Autowired
	private JsProductReservationDAO jsProductReservationDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;
	@Autowired
	private JsActivityProductDAO jsActivityProductDAO;
	
	
	@Override
	public Map<String, Object> reservationProduct(DrProductInfo drProductInfo,Integer uid) {
		Map<String,Object> result = new HashMap<String, Object>();
		
		if(Utils.isObjectNotEmpty(drProductInfo) && (drProductInfo.getStatus() > 5 && drProductInfo.getStatus() !=7)){//募集完成可预约
			
			Map<String,Object> acMap = jsActivityProductDAO.selectActivityProduct(drProductInfo.getId());
			
			if(Utils.isObjectNotEmpty(acMap) && Utils.isObjectNotEmpty(acMap.get("activityPeriods"))){//期限不为空的
				Map<String,Object> map  = new HashMap<String, Object>();
 					map.put("periods", Integer.parseInt(acMap.get("activityPeriods").toString())+1);
 					map.put("order", " id desc ");
 					map.put("offset", 0);
 					map.put("limit", 1);
 					List<JsProductReservation> list = jsProductReservationDAO.selectJsProductReservationByMap(map);
 					if(!Utils.isEmptyList(list) //不为null
 							&& list.get(0).getStartTime().before(new Date())  // now开始时间之后
 							&&list.get(0).getEndTime().after(new Date())// now结束时间之前
 							&& list.get(0).getStatus() == 1){ //要开启: 0:待开启 1:开启 2:关闭					
 						boolean realverify = false;
 						DrMember m = null;
 						if(!Utils.isBlank(uid)){
 							m = drMemberDAO.selectByPrimaryKey(uid);
 						}
 						if(Utils.isObjectNotEmpty(m) && m.getRealVerify() == 1){
 							realverify = true;
 						}
 						result.put("isReservation", true);
 						result.put("realverify", realverify);
 						result.put("prid", list.get(0).getId());
 						result.put("name", list.get(0).getName());	
 					}else{
 						result.put("isReservation", false);
 					}
			}else{
				result.put("isReservation", false);
			}			
		}else{//不能预约
			result.put("isReservation", false);
		}
		return result;
	}


	@Override
	public JsProductReservation selectReservationProduct(int id) {
		return jsProductReservationDAO.selectByPrimaryKey(id);
	}

}
