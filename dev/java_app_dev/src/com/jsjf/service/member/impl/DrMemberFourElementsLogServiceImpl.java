package com.jsjf.service.member.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.member.DrMemberFourElementsLogDAO;
import com.jsjf.model.member.DrMemberFourElementsLog;
import com.jsjf.service.member.DrMemberFourElementsLogService;

@Service
public class DrMemberFourElementsLogServiceImpl implements
		DrMemberFourElementsLogService {

	@Autowired
	public DrMemberFourElementsLogDAO drMemberFourElementsLogDAO;

	@Override
	public void insertMemberFourElementsLog(DrMemberFourElementsLog record) {
		drMemberFourElementsLogDAO.insertMemberFourElementsLog(record);
	}

	@Override
	public List<DrMemberFourElementsLog> queryMemberFourElementsLogList(
			Integer uid) {
		return drMemberFourElementsLogDAO.queryMemberFourElementsLogList(uid);
	}

}
