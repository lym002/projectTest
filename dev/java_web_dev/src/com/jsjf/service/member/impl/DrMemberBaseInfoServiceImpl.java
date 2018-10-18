package com.jsjf.service.member.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.DrMemberBaseInfoDAO;
import com.jsjf.model.member.DrMemberBaseInfo;
import com.jsjf.service.member.DrMemberBaseInfoService;

@Service
@Transactional
public class DrMemberBaseInfoServiceImpl implements DrMemberBaseInfoService {
	@Autowired
	private DrMemberBaseInfoDAO drMemberBaseInfoDAO;


	@Override
	public Boolean vertifyEmail(String email) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		List<DrMemberBaseInfo> list = drMemberBaseInfoDAO
				.queryMemberBaseInfoByMap(map);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public DrMemberBaseInfo queryMemberBaseInfoByUid(Integer uid) {
		return drMemberBaseInfoDAO.queryMemberBaseInfoByUid(uid);
	}

	@Override
	public void updateMemberBaseInfoByUid(DrMemberBaseInfo baseinfo)
			throws Exception {
		drMemberBaseInfoDAO.updateDrMemberBaseInfoById(baseinfo);

	}

	@Override
	public Integer queryMemberBaseInfoCountByMap(Map<String, Object> map)
			throws SQLException {
		
		return drMemberBaseInfoDAO.queryMemberBaseInfoCountByMap(map);
	}

}
