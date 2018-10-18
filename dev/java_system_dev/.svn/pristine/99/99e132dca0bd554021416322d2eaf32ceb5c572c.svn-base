package com.jsjf.service.member.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrWinCallLogDAO;
import com.jsjf.service.member.DrWinCallLogService;

@Service
@Transactional
public class DrWinCallLogServiceImpl implements DrWinCallLogService {
	@Autowired DrWinCallLogDAO drWinCallLogDAO;
	@Autowired DrMemberDAO drMemberDAO;
	
	@Override
	public List<Map<String, Object>> selWincallLog(Map<String, Object> param) {
		return drWinCallLogDAO.selWincallLog(param);
	}

	@Override
	public int selWincallLogCount(Map<String, Object> param) {
		return drWinCallLogDAO.selWincallLogCount(param);
	}

	@Override
    public void insert(Map<String, Object> param){
		drWinCallLogDAO.insert(param);
		drMemberDAO.updatelastCallTime(param);
	}

	@Override
	public void update(Map<String, Object> param) {
		drWinCallLogDAO.update(param);		
	}

	
}
