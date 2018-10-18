package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.activity.ProductRewardDao;
import com.jsjf.model.activity.ProductReward;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.activity.ProductRewardService;

@Service
public class ProductRewardServiceImpl implements ProductRewardService {

	@Autowired
	private ProductRewardDao  productRewardDao;
	
	@Override
	public List<Map<String, Object>> selProductReward(Map<String, Object> map) {
		return productRewardDao.selProductReward(map);
	}

	@Override
	public int selProductRewardCount(Map<String, Object> map) {
		return productRewardDao.selProductRewardCount(map);
	}

	@Override
	public List<DrProductInfo> selProductList() {
		return productRewardDao.selProductList();
	}

	@Override
	public List<Map<String, Object>> selParameterList(Map<String, Object> map) {
		return productRewardDao.selParameterList(map);
	}

	@Override
	public void insert(ProductReward productReward) {
		productRewardDao.insert(productReward);
	}

	@Override
	public void delete(Map<String, Object> map) {
		productRewardDao.delete(map);
	}
	
}
