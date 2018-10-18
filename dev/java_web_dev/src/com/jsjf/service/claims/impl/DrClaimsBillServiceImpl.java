package com.jsjf.service.claims.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.claims.DrClaimsBillDAO;
import com.jsjf.model.claims.DrClaimsBill;
import com.jsjf.service.claims.DrClaimsBillService;

@Service
public class DrClaimsBillServiceImpl implements DrClaimsBillService {
	
	@Autowired
	private DrClaimsBillDAO drClaimsBillDAO;

	@Override
	public DrClaimsBill getDrClaimsBillBySid(Integer sid) {
		return drClaimsBillDAO.getDrClaimsBillBySid(sid);
	}

}
