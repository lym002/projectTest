package com.jsjf.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.product.DrProductExtendDAO;
import com.jsjf.model.product.DrProductExtend;
import com.jsjf.service.product.DrProductExtendService;

@Service
public class DrProductExtendServiceImpl implements DrProductExtendService {
	
	@Autowired
	private DrProductExtendDAO drProductExtendDAO;

	@Override
	public List<DrProductExtend> getDrProductExtendByPid(int pid) {
		return drProductExtendDAO.getDrProductExtendByPid(pid);
	}

}
