package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberFundsLogDAO;
import com.jsjf.model.member.DrMemberFundsLog;
import com.jsjf.service.member.DrMemberFundsLogService;


@Service
@Transactional
public class DrMemberFundsLogServiceImpl implements DrMemberFundsLogService {
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	
	@Override
	public BaseResult getMemberFundsLogList(DrMemberFundsLog drMemberFundsLog, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startDate", Utils.format(drMemberFundsLog.getStartDate(), "yyyy-MM-dd"));
		map.put("endDate", Utils.format(drMemberFundsLog.getEndDate(), "yyyy-MM-dd"));
		map.put("realName", drMemberFundsLog.getRealName());
		map.put("mobilephone", drMemberFundsLog.getMobilephone());
		map.put("recommCodes", drMemberFundsLog.getRecommCodes());
		map.put("idCards", drMemberFundsLog.getIdCards());
		map.put("type", drMemberFundsLog.getType());
		map.put("uid", drMemberFundsLog.getUid());
		if(null != drMemberFundsLog.getFundTypes() && !"".equals(drMemberFundsLog.getFundTypes())){
	        String[] fundTypes = drMemberFundsLog.getFundTypes().split(",");
			map.put("fundTypes", fundTypes);
        }	
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrMemberFundsLog> list = drMemberFundsLogDAO.getDrMemberFundsLogList(map);
		Integer total = drMemberFundsLogDAO.getDrMemberFundsLogCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public Map<String,Object> getDrMemberFundsLogSum(DrMemberFundsLog drMemberFundsLog) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		BigDecimal bdIncome = null;
		BigDecimal bdPay = null;
		map.put("startDate", Utils.format(drMemberFundsLog.getStartDate(), "yyyy-MM-dd"));
		map.put("endDate", Utils.format(drMemberFundsLog.getEndDate(), "yyyy-MM-dd"));
		map.put("realName", drMemberFundsLog.getRealName());
		map.put("mobilephone", drMemberFundsLog.getMobilephone());
		map.put("idCards", drMemberFundsLog.getIdCards());
		map.put("recommCodes", drMemberFundsLog.getRecommCodes());
		if(null != drMemberFundsLog.getFundTypes() && !"".equals(drMemberFundsLog.getFundTypes())){
	        String[] fundTypes = drMemberFundsLog.getFundTypes().split(",");
			map.put("fundTypes", fundTypes);
        }			
		map.put("type", 1);
		bdIncome = drMemberFundsLogDAO.getDrMemberFundsLogSum(map);
		map.put("type", 0);
		bdPay = drMemberFundsLogDAO.getDrMemberFundsLogSum(map);
		resultMap.put("memberFundsLogIncome", bdIncome==null?0:bdIncome);
		resultMap.put("memberFundsLogPay", bdPay==null?0:bdPay);
		return resultMap;
	}
	
	@Override
	public List<DrMemberFundsLog> getMemberFundsLogList(Map<String,Object> map){
		List<DrMemberFundsLog> list = drMemberFundsLogDAO.getDrMemberFundsLogList(map);
		return list;
	}
	
}
