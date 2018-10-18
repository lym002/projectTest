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
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Override
	public BaseResult selectJsActivityOfflineListByMap(PageInfo pi) {
		BaseResult br  = new BaseResult();
		try {
			PageInfo page = new PageInfo(pi.getPageOn(), pi.getPageSize());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", 0);
			map.put("status", 1);
			map.put("offset", page.getPageInfo().getOffset());
			map.put("limit", page.getPageInfo().getLimit());
			List<JsActivityOffline> list = jsActivityOfflineDAO.selectJsActivityOfflineListByMap(map);
			Integer total = jsActivityOfflineDAO.selectJsActivityOfflineListByMapCount(map);
			map.clear();
			page.setRows(list);
			page.setTotal(total == null?0:total);
			map.put("page", page);
			map.put("openDayPicUrl", redisClientTemplate.getProperties("openDayPicUrl"));
			map.put("openDayLabel", redisClientTemplate.getProperties("openDayLabel"));
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
	public BaseResult selectJsActivityOfflineDetailById(Integer id) {
		BaseResult br  = new BaseResult();
		try {
			if(Utils.isObjectEmpty(id)){
				br.setSuccess(false);
				br.setErrorCode("9998");
				return br;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", 0);
			map.put("id", id);
			map.put("status", 1);
			JsActivityOffline jsActivityOffline = jsActivityOfflineDAO.selectJsActivityOfflineDetailByMap(map);
			List<JsActivityExtendPic> picList = new ArrayList<JsActivityExtendPic>();
			if(Utils.isObjectNotEmpty(jsActivityOffline)){
				map.clear();
				map.put("type", 1);
				map.put("status", 1);
				map.put("extendId", id);
				picList = jsActivityExtendPicDAO.selectJsActivityExtendPicListByMap(map);
			}
			map.clear();
			map.put("jsActivityOffline", jsActivityOffline);
			map.put("picList", picList);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return br;
	}
	
}
