package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrCompanyFundsLogDAO;
import com.jsjf.model.member.DrCompanyFundsLog;
import com.jsjf.service.member.DrCompanyFundsLogService;


@Service
@Transactional
public class DrCompanyFundsLogServiceImpl implements DrCompanyFundsLogService {
	@Autowired
	private DrCompanyFundsLogDAO drCompanyFundsLogDAO;

	@Override
	public BaseResult getCompanyFundsLogList(DrCompanyFundsLog drCompanyFundsLog, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startDate", Utils.format(drCompanyFundsLog.getStartDate(), "yyyy-MM-dd"));
		map.put("endDate", Utils.format(drCompanyFundsLog.getEndDate(), "yyyy-MM-dd"));
		map.put("pcode", drCompanyFundsLog.getPcode());
		map.put("type", drCompanyFundsLog.getType());
		if(null != drCompanyFundsLog.getFundTypes() && !"".equals(drCompanyFundsLog.getFundTypes())){
	        String[] fundTypes = drCompanyFundsLog.getFundTypes().split(",");
			map.put("fundTypes", fundTypes);
        }
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrCompanyFundsLog> list = drCompanyFundsLogDAO.getDrCompanyFundsLogList(map);
		Integer total = drCompanyFundsLogDAO.getDrCompanyFundsLogCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}


	@Override
	public Map<String,Object> getDrCompanyFundsLogSum(DrCompanyFundsLog drCompanyFundsLog) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		BigDecimal bdIncome = null;
		BigDecimal bdPay = null;
		try {
			map.put("startDate", Utils.format(drCompanyFundsLog.getStartDate(), "yyyy-MM-dd"));
			map.put("endDate", Utils.format(drCompanyFundsLog.getEndDate(), "yyyy-MM-dd"));
			map.put("pcode", drCompanyFundsLog.getPcode());
			if(null != drCompanyFundsLog.getFundTypes() && !"".equals(drCompanyFundsLog.getFundTypes())){
		        String[] fundTypes = drCompanyFundsLog.getFundTypes().split(",");
				map.put("fundTypes", fundTypes);
	        }			
			map.put("type", 1);
			bdIncome = drCompanyFundsLogDAO.getDrCompanyFundsLogSum(map);
			map.put("type", 0);
			bdPay = drCompanyFundsLogDAO.getDrCompanyFundsLogSum(map);
			resultMap.put("companyFundsLogIncome", bdIncome==null?0:bdIncome);
			resultMap.put("companyFundsLogPay", bdPay==null?0:bdPay);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultMap;
	}


	@Override
	public void insertDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog)
			throws SQLException {
		drCompanyFundsLogDAO.insertDrCompanyFundsLog(drCompanyFundsLog);
		
	}


	@Override
	public BaseResult upDateDrCompanyFundsLog(DrCompanyFundsLog drCompanyFundsLog) {
		BaseResult br = new BaseResult();
		try {
			drCompanyFundsLogDAO.updateDrCompanyFundsLog(drCompanyFundsLog);
			br.setSuccess(true);
			br.setMsg("修改成功");
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorMsg("修改失败");
			e.printStackTrace();
		}
		return br;
	}


	@Override
	public void insertDrCompanyFundsLogFK(DrCompanyFundsLog drCompanyFundsLog)
			throws SQLException {
		drCompanyFundsLogDAO.insertDrCompanyFundsLogFK(drCompanyFundsLog);
		
	}

}
