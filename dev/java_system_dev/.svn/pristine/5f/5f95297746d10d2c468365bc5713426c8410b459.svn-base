package com.jsjf.service.vip.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.vip.VipEquitiesDao;
import com.jsjf.model.activity.BypCommodityDetailBean;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.service.vip.VipEquitiesService;


@Service
@Transactional
public class VipEquitiesServiceImpl implements VipEquitiesService {

	@Autowired
	private VipEquitiesDao vipEquitiesDao;
	
	@Override
	public List<VipEquities> queryQy() {
		return vipEquitiesDao.queryQy();
	}

	@Override
	public BaseResult queryVipEquitiesList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<VipEquities> list = vipEquitiesDao.queryVipEquitiesList(param);
		Integer total = vipEquitiesDao.queryVipEquitiesListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult addVipEquities(VipEquities bean) {
		BaseResult br = new BaseResult();
		try{
			bean.setCreatedTime(new Date());
			vipEquitiesDao.addVipEquities(bean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateVipEquities(VipEquities bean) {
		BaseResult br = new BaseResult();
		try{
			bean.setUpdateTime(new Date());
			vipEquitiesDao.updateVipInfo(bean);
			br.setSuccess(true);
			br.setMsg("修改成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("修改失败！");
		}
		return br;
	}

	

}
