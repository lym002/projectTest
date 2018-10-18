package com.jsjf.service.cpa.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.cpa.DrCpaInfoDAO;
import com.jsjf.model.cpa.DrCpaInfo;
import com.jsjf.service.cpa.DrCpaInfoService;

@Service
@Transactional
public class DrCpaInfoServiceImpl implements DrCpaInfoService{
	@Autowired
	private DrCpaInfoDAO drCpaInfoDAO;
	
	@Override
	public BaseResult getDrCpaInfoList(DrCpaInfo drCpaInfo,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code", drCpaInfo.getCode());
		map.put("activityCode", drCpaInfo.getActivityCode());
		map.put("url", drCpaInfo.getUrl());
		map.put("status", drCpaInfo.getStatus());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrCpaInfo> list = drCpaInfoDAO.getDrCpaInfoList(map);
		Integer total = drCpaInfoDAO.getDrCpaInfoCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public void insertDrCpaInfo(DrCpaInfo drCpaInfo)
			throws SQLException {
		drCpaInfoDAO.insertDrCpaInfo(drCpaInfo);
	}

	@Override
	public List<DrCpaInfo> getDrCpaInfoListForMap(Map<String, Object> map)
			throws SQLException {
		return drCpaInfoDAO.getDrCpaInfoListForMap(map);
	}
}
