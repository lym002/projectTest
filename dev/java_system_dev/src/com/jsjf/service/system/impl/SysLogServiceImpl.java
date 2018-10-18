package com.jsjf.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.system.SysLogDAO;
import com.jsjf.model.system.SysLog;
import com.jsjf.service.system.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	SysLogDAO sysLogDAO;
	
	@Override
	public void saveSysLog(SysLog log) {
		sysLogDAO.insert(log);
	}
}
