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
import com.jsjf.dao.vip.VipMemberInfoDao;
import com.jsjf.model.vip.VipMemberInfo;
import com.jsjf.service.vip.VipMemberInfoService;

@Service
@Transactional
public class VipMemberInfoServiceImpl implements VipMemberInfoService{

	@Autowired
	private VipMemberInfoDao vipMemberInfoDao;
	
	@Override
	public BaseResult queryVipMemberInfoList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 

		List<VipMemberInfo> list = vipMemberInfoDao.queryVipMemberInfoList(param);
		Integer total = vipMemberInfoDao.queryVipMemberInfoListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult addVipMemberInfo(VipMemberInfo bean) {
		BaseResult br = new BaseResult();
		try{
			bean.setAddTime(new Date());
			vipMemberInfoDao.insertSelective(bean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateVipMemberInfo(VipMemberInfo bean) {
		BaseResult br = new BaseResult();
		try{
			bean.setUpdateTime(new Date());
			vipMemberInfoDao.updateByPrimaryKeySelective(bean);
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
