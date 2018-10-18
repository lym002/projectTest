package com.jsjf.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.system.SysBankDAO;
import com.jsjf.model.system.SysBank;
import com.jsjf.service.system.SysBankService;

@Service
@Transactional
public class SysBankServiceImpl implements SysBankService {
	@Autowired
	public SysBankDAO sysBankDAO;
	
	@Override
	public BaseResult getSysBankList(SysBank sysBank, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("bankName", sysBank.getBankName());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<SysBank> list = sysBankDAO.getSysBankList(map);
		Integer total = sysBankDAO.getSysBankCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public SysBank getSysBankByid(int id) {
		return sysBankDAO.getSysBankByid(id);
	}

	@Override
	public void updateSysBank(SysBank sysBank) {
		sysBankDAO.updateSysBank(sysBank);	
	}
}
