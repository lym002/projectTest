package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityDAO;
import com.jsjf.dao.activity.JsActivityExtendPicDAO;
import com.jsjf.dao.activity.JsActivityOfflineDAO;
import com.jsjf.dao.activity.JsOpenDayDAO;
import com.jsjf.dao.activity.JsOpenDayLogDAO;
import com.jsjf.dao.activity.JsSpecialDAO;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.activity.JsActivityExtendPic;
import com.jsjf.model.activity.JsActivityOffline;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.model.activity.JsSpecial;
import com.jsjf.model.activity.JsSpecialPic;
import com.jsjf.service.activity.DrActivityService;
import com.jsjf.service.activity.JsActivityOfflineService;
import com.jsjf.service.activity.JsOpenDayService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class JsActivityOfflineServiceImpl implements JsActivityOfflineService {

	@Autowired
	private JsActivityOfflineDAO jsActivityOfflineDAO; 
	@Autowired
	private JsActivityExtendPicDAO jsActivityExtendPicDAO; 
	
	@Override
	public BaseResult selectJsActivityOfflineListByMap(Map<String,Object> map) {
		BaseResult br  = new BaseResult();
		try {
			PageInfo pi = new PageInfo();
			if(map.containsKey("pageOn")&&map.containsKey("pageSize")){
				pi.setPageOn((Integer)map.get("pageOn"));
				pi.setPageSize((Integer)map.get("pageSize"));
			}
			map.put("type", 0);
			map.put("status", 1);
			map.put("offset", pi.getPageInfo().getOffset());
			map.put("limit", pi.getPageInfo().getLimit());
			List<JsActivityOffline> list = jsActivityOfflineDAO.selectJsActivityOfflineListByMap(map);
			Integer total = jsActivityOfflineDAO.selectJsActivityOfflineListByMapCount(map);
			map.clear();
			pi.setRows(list);
			pi.setTotal(total == null?0:total);
			map.put("page", pi);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return br;
	}
	@Override
	public BaseResult selectJsActivityOfflineDetailById(Map<String,Object> map) {
		BaseResult br  = new BaseResult();
		try {
			if(Utils.isObjectEmpty(map.get("id"))){
				br.setSuccess(false);
				br.setErrorCode("9998");
				return br;
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("type", 0);
			param.put("id", map.get("id"));
			param.put("status", 1);
			JsActivityOffline jsActivityOffline = jsActivityOfflineDAO.selectJsActivityOfflineDetailByMap(param);
			List<JsActivityExtendPic> picList = new ArrayList<JsActivityExtendPic>();
			if(Utils.isObjectNotEmpty(jsActivityOffline)){
				param.clear();
				param.put("type", 1);
				param.put("status", 1);
				param.put("extendId", map.get("id"));
				picList = jsActivityExtendPicDAO.selectJsActivityExtendPicListByMap(param);
			}
			param.clear();
			param.put("jsActivityOffline", jsActivityOffline);
			param.put("picList", picList);
			br.setMap(param);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return br;
	}
	
}
