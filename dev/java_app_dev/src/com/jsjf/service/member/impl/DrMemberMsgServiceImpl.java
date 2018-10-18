package com.jsjf.service.member.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.PageInfo;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.service.member.DrMemberMsgService;

@Service
@Transactional
public class DrMemberMsgServiceImpl implements DrMemberMsgService {
	
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;

	@Override
	public PageInfo getDrMemberParam(PageInfo pi,Map<String, Object> map) throws Exception {	
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());		
		List<DrMemberMsg> list= drMemberMsgDAO.getDrMemberListByParam(map);
		int total = drMemberMsgDAO.getDrMemberListCountByParam(map);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}


	@Override
	public void updateMsgToReadByIds(Integer[] ids, Integer uid) throws Exception {
		drMemberMsgDAO.updateMsgToReadByIds(ids,uid);
	}


	@Override
	public Integer getUnReadCountByMap(Map<String, Object> map) throws Exception {
		return drMemberMsgDAO.getDrMemberListCountByParam(map);
	}


	@Override
	public void updateMsgToDelByIds(Integer[] ids, Integer uid)throws Exception {
		drMemberMsgDAO.updateMsgToDelByIds(ids, uid);
		
	}


	@Override
	public void updateMsgToRead(Integer type, Integer uid) throws SQLException {
		drMemberMsgDAO.updateMsgToRead(type, uid);
	}
	@Override
	public void insert(DrMemberMsg msg)throws Exception {
		drMemberMsgDAO.insertDrMemberMsg(msg);
	}


	@Override
	public Integer selectMsg(String title, Integer uid) {
		return drMemberMsgDAO.selectMsg(title,uid);
	}
	

}
