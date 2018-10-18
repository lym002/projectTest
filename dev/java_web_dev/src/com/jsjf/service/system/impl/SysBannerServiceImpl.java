package com.jsjf.service.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.system.SysBannerDAO;
import com.jsjf.model.system.SysBanner;
import com.jsjf.service.system.SysBannerService;

@Service
@Transactional
public class SysBannerServiceImpl implements SysBannerService {

	@Autowired
	public SysBannerDAO sysBannerDAO;

	@Override
	public List<SysBanner> indexBanner(Map<String,Object> map) {
		List<SysBanner> list = sysBannerDAO.getIndexBanner(map);
		return list;
	}


}
