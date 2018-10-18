package com.jsjf.service.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.product.JsProductPrizeDAO;
import com.jsjf.model.product.JsProductPrize;
import com.jsjf.service.product.JsproductPrizeService;
@Service
@Transactional
public class JsproductPrizeServiceImpl implements JsproductPrizeService {
	@Autowired
	private JsProductPrizeDAO jsProductPrizeDAO;
	
	@Override
	public List<JsProductPrize> selectActivityIndexJsproductPrize(Map<String,Object> map) {
		return jsProductPrizeDAO.selectActivityIndexJsproductPrize(map);
	}

	@Override
	public JsProductPrize selectJsPorudctPrize(Map<String, Object> map) {
		return jsProductPrizeDAO.selectJsPorudctPrize(map);
	}

}
