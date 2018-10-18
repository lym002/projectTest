/**
 * 
 */
package com.jsjf.service.member.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberBrokerDAO;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBroker;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberBrokerService;

@Service
@Transactional
public class DrMemberBrokerServiceImpl implements DrMemberBrokerService {

	@Autowired
	private DrMemberBrokerDAO drMemberBrokerDAO;
	
	@Autowired
	private DrMemberDAO drMemberDAO;
	
	@Override
	public BaseResult getMemberBrokerList(DrMemberBroker drMemberBroker,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map.put("startDate", Utils.format(drMemberBroker.getStartDate(), "yyyy-MM-dd"));
			map.put("endDate", Utils.format(drMemberBroker.getEndDate(), "yyyy-MM-dd"));
			map.put("realname", drMemberBroker.getRealname());
			map.put("username", drMemberBroker.getUsername());
			map.put("status", drMemberBroker.getStatus());
			map.put("level", drMemberBroker.getLevel());
			map.put("offset",pi.getPageInfo().getOffset()); 
			map.put("limit",pi.getPageInfo().getLimit()); 
			List<DrMemberBroker> list = drMemberBrokerDAO.getDrMemberBrokerList(map);
			Integer total = drMemberBrokerDAO.getDrMemberBrokerCounts(map);
			pi.setTotal(total);
			pi.setRows(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult updateByPrimaryKey(int id,int status,int level,SysUsersVo userVo) {
		BaseResult br = new BaseResult();
		try {
			DrMemberBroker drMemberBroker = drMemberBrokerDAO.selectByPrimaryKey(id);
			drMemberBroker.setStatus(status);
			drMemberBroker.setAuditTime(new Date());
			drMemberBroker.setAuditUser(userVo.getUserKy().intValue());
			if(status==3){
				drMemberBroker.setLevel(null);
				DrMember drMember =drMemberDAO.selectByPrimaryKey(drMemberBroker.getUid());
				drMember.setLevel(null);
				drMemberDAO.updateDrMemberByUid(drMember);
			}else if(status==1){
				DrMember drMember =drMemberDAO.selectByPrimaryKey(drMemberBroker.getUid());
				if(level ==0){
					drMember.setLevel(drMemberBroker.getLevel());
				}else{
					drMember.setLevel(level);
				}
				drMemberDAO.updateDrMemberByUid(drMember);
			}
			drMemberBrokerDAO.updateDrMemberBrokerStatus(drMemberBroker);
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return br;
	}
	
	@Override
	public DrMemberBroker queryDrMemberBrokerById(int id) {
		DrMemberBroker drMemberBroker = null;
		try {
			drMemberBroker = drMemberBrokerDAO.selectByPrimaryKey(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drMemberBroker;
	}

}
