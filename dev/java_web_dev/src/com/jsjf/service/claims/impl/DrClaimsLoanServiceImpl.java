package com.jsjf.service.claims.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.service.claims.DrClaimsLoanService;

@Service
public class DrClaimsLoanServiceImpl implements DrClaimsLoanService {
	
	@Autowired
	private DrClaimsLoanDAO drClaimsLoanDAO;

	@Override
	public DrClaimsLoan selectByPrimaryKey(Integer id) {
		return drClaimsLoanDAO.selectByPrimaryKey(id);
	}

}
