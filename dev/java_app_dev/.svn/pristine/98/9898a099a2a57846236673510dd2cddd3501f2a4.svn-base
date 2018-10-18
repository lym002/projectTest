package com.jsjf.service.member.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.member.DrMemberCreditDAO;
import com.jsjf.model.member.DrMemberCredit;
import com.jsjf.service.member.DrMemberCreditService;

@Service
public class DrMemberCreditServiceImpl implements DrMemberCreditService {
	
	@Autowired
	private DrMemberCreditDAO drMemberCreditDAO;

	@Override
	public DrMemberCredit selectByPrimaryKey(Integer uid) {
		return drMemberCreditDAO.selectByPrimaryKey(uid);
	}

}
