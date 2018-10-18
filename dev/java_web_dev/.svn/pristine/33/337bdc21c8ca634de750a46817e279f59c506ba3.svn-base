package com.jsjf.service.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.product.DrProductInvestTransferDAO;
import com.jsjf.service.product.DrProductInvestTransferService;

@Service
public class DrProductInvestTransferServiceImpl implements DrProductInvestTransferService {
	
	@Autowired
	private DrProductInvestTransferDAO drProductInvestTransferDAO;

	@Override
	public List<Map<String, String>> selectTransferInfoByAssigneeUid(Integer pid,
			Integer assigneeUid,Integer investId) {
		return drProductInvestTransferDAO.selectTransferInfoByAssingneeUid(pid,assigneeUid,investId);
	}

}
