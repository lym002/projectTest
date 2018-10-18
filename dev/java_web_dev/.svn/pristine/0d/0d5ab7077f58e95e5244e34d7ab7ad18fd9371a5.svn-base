package com.jsjf.service.claims.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.claims.DrClaimsPicDAO;
import com.jsjf.model.claims.DrClaimsPic;
import com.jsjf.service.claims.DrClaimsPicService;

@Service
@Transactional
public class DrClaimsPicServiceImpl implements DrClaimsPicService {

	@Autowired
	private DrClaimsPicDAO drClaimsPicDAO;
	
	@Override
	public List<DrClaimsPic> selectListPicByPid(Integer pid) {
		return drClaimsPicDAO.selectListPicByPid(pid);
	}

}
