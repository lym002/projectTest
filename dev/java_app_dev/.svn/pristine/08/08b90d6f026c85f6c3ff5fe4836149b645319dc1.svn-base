package com.jsjf.service.claims.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.claims.DrClaimsProjectDAO;
import com.jsjf.model.claims.DrClaimsProject;
import com.jsjf.service.claims.DrClaimsProjectService;

@Service
@Transactional
public class DrClaimsProjectServiceImpl implements DrClaimsProjectService {
	@Autowired
	private DrClaimsProjectDAO drClaimsProjectDAO;

	@Override
	public List<String> selectListProjectByPid(Integer pid) {
		return drClaimsProjectDAO.getDrClaimsProjectByPid(pid);
	}

}
