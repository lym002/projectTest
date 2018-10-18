package com.jsjf.service.product.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.product.JsActivityProductDAO;
import com.jsjf.service.product.JsActivityProductService;
@Service
@Transactional
public class JsActivityProductServiceImpl implements JsActivityProductService {
	@Autowired
	JsActivityProductDAO jsActivityProductDAO;
	@Override
	public Map<String, Object> selectActivityProduct(int pid) {
		
		return  jsActivityProductDAO.selectActivityProduct(pid);
	}

}
