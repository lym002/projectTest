package com.jsjf.service.integral.impl;

import com.jsjf.common.PageInfo;
import com.jsjf.dao.integral.IntegralSourceDao;
import com.jsjf.model.integral.IntegralSourceBean;
import com.jsjf.service.integral.IntegralSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IntegralSourceServiceImpl implements IntegralSourceService{
	
	@Autowired
	private IntegralSourceDao integralSourceDao;

	@Override
	public PageInfo queryIntegralSourceList(PageInfo info,
			IntegralSourceBean integralSourceBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		
		//map.put("sourceType", integralSourceBean.getSourceType());
		map.put("orders", "id desc");
		
		List<IntegralSourceBean> rows = integralSourceDao.queryIntegralSourceList(map);
		int total = integralSourceDao.queryIntegralSourceListCount(map);
		
		info.setRows(rows);
		info.setTotal(total);
		
		return info;
	}

	@Override
	public void updateIntegralSource(IntegralSourceBean integralSourceBean) {
		integralSourceBean.setUpdateTime(new Date());
		integralSourceDao.updateIntegralSource(integralSourceBean);
		
	}

}
