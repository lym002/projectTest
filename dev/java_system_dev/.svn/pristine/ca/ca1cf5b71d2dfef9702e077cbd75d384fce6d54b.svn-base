package com.jsjf.service.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.product.SynPayProductInfoDao;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInfoVo;
import com.jsjf.service.product.SynPayProductInfoService;

@Service
@Transactional
public class SynPayProductInfoServiceImpl implements SynPayProductInfoService {

	@Autowired
	private SynPayProductInfoDao synPayProductInfoDao;
		
	@Override
	public List<DrProductInfoVo> getSynProjectInfo() {
		List<DrProductInfoVo> productList =  synPayProductInfoDao.getSynProjectInfo();
		return productList;
	}

	@Override
	public void updateSynStatus(Integer id) {
		synPayProductInfoDao.updateSynStatus(id);
	}

	@Override
	public List<DrProductInfoVo> getSynRefundProductInfo() {
		List<DrProductInfoVo> projectList = synPayProductInfoDao.getSynRefundProductInfo();
		
		return projectList;
	}

	@Override
	public void updateSynRefundStatus(Integer id) {
		synPayProductInfoDao.updateSynRefundStatus(id);
	}
	
}
