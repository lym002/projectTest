package com.jsjf.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.product.DrProductPicDAO;
import com.jsjf.model.product.DrProductPic;
import com.jsjf.service.product.DrProductPicService;

@Service
@Transactional
public class DrProductPicServiceImpl implements DrProductPicService{
	
	@Autowired
	private DrProductPicDAO drProductPicDAO;

	@Override
	public List<DrProductPic> getDrProductPicByPid(Integer pid) {
		return drProductPicDAO.getDrProductPicByPid(pid);
	}

}