package com.jsjf.service.member.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberAppLoginLogDAO;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberAppLoginLog;
import com.jsjf.service.member.DrMemberAppLoginLogService;

@Service
@Transactional
public class DrMemberAppLoginLogServiceImpl implements
		DrMemberAppLoginLogService {
	
	@Autowired
	private DrMemberAppLoginLogDAO drMemberAppLoginLogDAO;

	@Override
	public String insertToken(DrMember m) {
		String token = UUID.randomUUID().toString();
//		drMemberAppLoginLogDAO.update(m.getUid());
		DrMemberAppLoginLog loginLog = new DrMemberAppLoginLog();
		loginLog.setAddTime(new Date());
		loginLog.setUid(m.getUid());
		loginLog.setStatus(1);
		loginLog.setToken(token);
		drMemberAppLoginLogDAO.insert(loginLog);
		return token;
	}
	@Override
	public String getPhoneToken(String phone) {
		String token = "";
		token = drMemberAppLoginLogDAO.getPhoneToken(phone);
		if(Utils.strIsNull(token)){
			token = UUID.randomUUID().toString();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("token", token);
			map.put("phone", phone);
			drMemberAppLoginLogDAO.setPhoneToken(map);
		}
		return token;
	}
	@Override
	public void deletePhoneToken(String phone) {
		try {
			drMemberAppLoginLogDAO.deletePhoneToken(phone);
		} catch (Exception e) {
		}
	}
	
}
