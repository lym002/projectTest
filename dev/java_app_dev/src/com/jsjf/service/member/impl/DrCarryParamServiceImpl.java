package com.jsjf.service.member.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.DrCarryParamDAO;
import com.jsjf.model.member.DrCarryParam;
import com.jsjf.service.member.DrCarryParamService;

@Service
@Transactional
public class DrCarryParamServiceImpl implements DrCarryParamService {
	@Autowired
	private DrCarryParamDAO drCarryParamDAO;
	
	
	@Override
	public DrCarryParam getDrCarryParam(){
		List<DrCarryParam> list = drCarryParamDAO.getDrCarryParamList();
		if(null != list && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

}
