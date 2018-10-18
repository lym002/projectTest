package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.JsOpenDayDAO;
import com.jsjf.dao.activity.JsOpenDayLogDAO;
import com.jsjf.dao.activity.JsSpecialDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.model.activity.JsSpecial;
import com.jsjf.model.activity.JsSpecialPic;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.activity.JsOpenDayService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class JsOpenDayServiceImpl implements JsOpenDayService {
	
	@Autowired
	JsOpenDayDAO jsOpenDayDAO;
	@Autowired
	JsSpecialDAO jsSpecialDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;

	@Override
	public JsSpecial selectJsSpecial() {
		Map<String,Object> map = new HashMap<String,Object>();
		JsSpecial jsSpecial = (JsSpecial) SerializeUtil.unserialize(redisClientTemplate.get("jsSpecial".getBytes()));
//	 	JsSpecial jsSpecial =jsSpecialDAO.selectJsSpecial();
//		List<JsSpecialPic> jsSpecialPic = jsSpecialDAO.selectJsSpecialPic();
		if(Utils.isObjectNotEmpty(jsSpecial)){
//			jsSpecial.setJsSpecialPic(jsSpecialPic);
			map.put("status", 1);
			List<JsOpenDay> jsOpenDay = jsOpenDayDAO.selectJsOpenDayByParam(map);
			jsSpecial.setIsAppointment(jsOpenDay.size()>0?true:false);
			jsSpecial.setOpenDayId(jsOpenDay.size()>0?jsOpenDay.get(0).getId():null);
		}
		return jsSpecial;
	}

	@Override
	public BaseResult selectJsOpenDayByParam(Map<String, Object> map) {
		BaseResult br = new BaseResult();
		PageInfo pi = new PageInfo();
		List<JsOpenDay> list = jsOpenDayDAO.selectJsOpenDayByParam(map);
		Integer total = jsOpenDayDAO.selectJsOpenDayCountByParam(map);
		pi.setRows(list);
		pi.setTotal(total);
		map.put("page", pi);
		br.setMap(map);
		return br;
	}
	
	@Override
	public List<JsOpenDay> selectJsOpenDayListByParam(Map<String, Object> map) {
		return jsOpenDayDAO.selectJsOpenDayByParam(map);
	}


}
