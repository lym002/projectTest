package com.jsjf.service.member.impl;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.jsjf.common.PageInfo;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.service.member.DrMemberMsgService;

@Service
@Controller
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
	public void updateMsgToRead(DrMemberMsg drMemberMsg) throws Exception {
		drMemberMsgDAO.updateMsgToRead(drMemberMsg);
	}


	@Override
	public Integer getUnReadCountByMap(Map<String, Object> map) throws Exception {
		return drMemberMsgDAO.getDrMemberListCountByParam(map);
	}


	@Override
	public void insert(DrMemberMsg msg)throws Exception {
		drMemberMsgDAO.insertDrMemberMsg(msg);
	}
	

}