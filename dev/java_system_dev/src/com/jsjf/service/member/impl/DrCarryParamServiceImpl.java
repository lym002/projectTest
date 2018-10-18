package com.jsjf.service.member.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.member.DrCarryParamDAO;
import com.jsjf.model.member.DrCarryParam;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrCarryParamService;

@Service
@Transactional
public class DrCarryParamServiceImpl implements DrCarryParamService {
	@Autowired
	private DrCarryParamDAO drCarryParamDAO;
	
	@Override
	public BaseResult getDrCarryParamList(PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		List<DrCarryParam> list = drCarryParamDAO.getDrCarryParamList();
		Integer total = drCarryParamDAO.getDrCarryParamCounts();
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public DrCarryParam getDrCarryParamByid(Integer id) {
		return drCarryParamDAO.getDrCarryParamByid(id);
	}

	@Override
	public void updateDrCarryParam(DrCarryParam drCarryParam,SysUsersVo usersVo)
			throws SQLException {
		drCarryParam.setUpdUser(usersVo.getUserKy().intValue());
		drCarryParamDAO.updateDrCarryParam(drCarryParam);	
	}
	@Override
	public DrCarryParam getDrCarryParam(){
		List<DrCarryParam> list = drCarryParamDAO.getDrCarryParamList();
		if(null != list && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

}
