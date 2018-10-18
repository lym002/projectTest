package com.jsjf.service.app.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.app.SysAppRenewalDAO;
import com.jsjf.model.app.SysAppRenewal;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.app.SysAppRenewalService;

@Service
public class SysAppRenewalServiceImpl implements SysAppRenewalService {
	@Autowired
	private SysAppRenewalDAO sysAppRenewalDAO;
	
	@Override
	public void insertAppRenewal(SysAppRenewal sysAppRenewal, SysUsersVo usersVo)throws Exception {
		sysAppRenewal.setAddUser(usersVo.getUserKy().intValue());
		sysAppRenewal.setAddTime(new Date());
		sysAppRenewal.setStatus(1);
		sysAppRenewalDAO.insertSysAppRenewal(sysAppRenewal);
	}

	@Override
	public BaseResult getSysAppRenewal(Map<String, Object> map, PageInfo pi)throws Exception {
		BaseResult br = new BaseResult();
		map.put("limit", pi.getPageInfo().getLimit());
		map.put("offset", pi.getPageInfo().getOffset());
		List<SysAppRenewal> list = sysAppRenewalDAO.getSysAppRenewalList(map);
		Integer total = sysAppRenewalDAO.getSysAppRenewalCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		map.clear();
		map.put("page", pi);
		br.setMap(map);
		return br;
	}

	@Override
	public void updateSysAppRenewal(SysAppRenewal sysAppRenewal)throws Exception {
		sysAppRenewalDAO.updateSysAppRenewal(sysAppRenewal);
	}

	@Override
	public void updateStatus(SysAppRenewal sysAppRenewal) throws Exception {
		sysAppRenewalDAO.updateStatus(sysAppRenewal);
	}

	@Override
	public SysAppRenewal getSysAppRenewalById(Integer id) {
		return sysAppRenewalDAO.getSysAppRenewalById(id);
	}

}
