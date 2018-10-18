package com.jsjf.service.activity.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.controller.activity.JsActivityRewardController;
import com.jsjf.dao.activity.JsActivityRewardDao;
import com.jsjf.model.activity.JsActivityReward;
import com.jsjf.service.activity.JsActivityRewardService;

@Service
@Transactional
public class JsActivityRewardServiceImpl implements JsActivityRewardService{
	
	private Logger log = Logger.getLogger(JsActivityRewardServiceImpl.class);

	@Autowired
	private JsActivityRewardDao jsActivityRewardDao;
	
	@Override
	public BaseResult queryJsActivityRewardList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<JsActivityReward> list =  jsActivityRewardDao.queryJsActivityRewardList(param);
		Integer total = jsActivityRewardDao.queryJsActivityRewardListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<JsActivityReward> queryCouponIdList() {
		return jsActivityRewardDao.queryCouponIdList();
	}

	@Override
	public BaseResult addActivityReward(JsActivityReward jsActivityReward) {
		BaseResult br = new BaseResult();
		try{
			jsActivityReward.setAddTime(new Date());
			jsActivityRewardDao.addActivityReward(jsActivityReward);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			log.error("添加失败",e);
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateActivityReward(JsActivityReward jsActivityReward) {
		BaseResult br = new BaseResult();
		try{
			jsActivityReward.setUpdateTime(new Date());
			jsActivityRewardDao.updateActivityReward(jsActivityReward);
			br.setSuccess(true);
			br.setMsg("修改成功");
		}catch(Exception e){
			log.error("修改失败",e);
			br.setSuccess(false);
			br.setMsg("修改失败");
		}
		return br;
	}

	@Override
	public BaseResult deleteActivityReward(Integer id) {
		BaseResult br = new BaseResult();
		try{
			jsActivityRewardDao.deleteActivityReward(id);
			br.setSuccess(true);
			br.setMsg("删除成功");
		}catch(Exception e){
			log.error("删除失败",e);
			br.setSuccess(false);
			br.setMsg("删除失败");
		}
		return br;
	}

	

}
