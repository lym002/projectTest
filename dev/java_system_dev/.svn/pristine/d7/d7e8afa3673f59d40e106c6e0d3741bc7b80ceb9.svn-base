package com.jsjf.service.store.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.store.CommodityClassDao;
import com.jsjf.model.store.CommodityClass;
import com.jsjf.model.store.CommodityRepertory;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.service.store.CommodityClassService;

@Service
@Transactional
public class CommodityClassServiceImpl implements CommodityClassService {
	
	@Autowired
	private CommodityClassDao commodityClassDao;

	@Override
	public BaseResult queryCommodityClassList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<VipEquities> list = commodityClassDao.queryCommodityClassList(param);
		Integer total = commodityClassDao.queryCommodityClassListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult addCommodityClass(CommodityClass bean) {
		BaseResult br = new BaseResult();
		try{
			commodityClassDao.addCommodityClass(bean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateCommodityClass(CommodityClass bean) {
		BaseResult br = new BaseResult();
		try{
			commodityClassDao.updateCommodityClass(bean);
			br.setSuccess(true);
			br.setMsg("修改成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("修改失败！");
		}
		return br;
	}

	@Override
	public BaseResult deleteCommodityClass(Integer id) {
		BaseResult br = new BaseResult();
		try{
			commodityClassDao.deleteCommodityClass(id);
			br.setSuccess(true);
			br.setMsg("删除成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("删除失败！");
		}
		return br;
	}

	@Override
	public List<CommodityRepertory> queryDd() {
		return commodityClassDao.queryDd();
	}

}
