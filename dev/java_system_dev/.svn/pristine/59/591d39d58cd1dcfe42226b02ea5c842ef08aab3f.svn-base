package com.jsjf.service.vip.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.vip.VipEquitiesMemberDao;
import com.jsjf.model.vip.VipEquitiesMember;
import com.jsjf.model.vip.VipInfo;
import com.jsjf.service.vip.VipEquitiesMemberService;


@Service
@Transactional
public class VipEquitiesMemberServiceImpl implements VipEquitiesMemberService {

	@Autowired
	private VipEquitiesMemberDao vipEquitiesMemberDao;

	@Override
	public BaseResult queryVipEquitiesList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 

		List<VipEquitiesMember> list = vipEquitiesMemberDao.queryVipEquitiesList(param);
		Integer total = vipEquitiesMemberDao.queryVipEquitiesListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<VipInfo> queryVipLevel() {
		return vipEquitiesMemberDao.queryVipLevel();
	}
	

}
