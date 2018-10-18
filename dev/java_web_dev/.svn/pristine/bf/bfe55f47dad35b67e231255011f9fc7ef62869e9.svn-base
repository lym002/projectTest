package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
	private static final Logger logs = Logger.getLogger(DrMemberFundsLogServiceImpl.class);
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	
	@Override
	public BaseResult getMemberAssetRecordList(DrMemberFundsLog log, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("uid", log.getUid());
//			map.put("varyType", log.getVaryType());
//			map.put("startDate", log.getStartDate());
//			map.put("endDate", log.getEndDate());
			map.put("offset",pi.getPageInfo().getOffset()); 
			map.put("limit",pi.getPageInfo().getLimit()); 
			List<DrMemberFundsLog> list = drMemberFundsLogDAO.getMemberAssetRecordList(map);
			Integer total = drMemberFundsLogDAO.getMemberAssetRecordListCount(map);
			pi.setTotal(total);
			pi.setRows(list);
		} catch (Exception e) {
			logs.error(e.getMessage(),e);
		}
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public List<DrMemberFundsLog> exportMemberFundsLogList(DrMemberFundsLog drMemberFundsLog) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("startDate", Utils.format(drMemberFundsLog.getStartDate(), "yyyy-MM-dd"));
//		map.put("endDate", Utils.format(drMemberFundsLog.getEndDate(), "yyyy-MM-dd"));
//		map.put("type", drMemberFundsLog.getType());
//		map.put("uid", drMemberFundsLog.getUid());
//		if(null != drMemberFundsLog.getVaryTypes() && !"".equals(drMemberFundsLog.getVaryTypes())){
//	        String[] varyTypes = drMemberFundsLog.getVaryTypes().split(",");
//			map.put("varyTypes", varyTypes);
//        }
		List<DrMemberFundsLog> list = drMemberFundsLogDAO.getDrMemberFundsLogList(map);
		return list;
	}
	
	
	@Override
	public Map<String, Object> getRegActivityFundsLogAndSum(int uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			map.put("uid", uid);
			
			//推荐注册成功人数
			Integer regRecommCount = 0;//drMemberDAO.selectRecommRegSum(uid);
			if(regRecommCount==null){//没有推荐投资
				return resultMap;
			}
			resultMap.put("regRecommCount",regRecommCount);
			
			//市场活动记录
			map.put("offset",0); 
			map.put("limit",3); 
			map.remove("varyType");
			map.put("varyTypes", new int[]{25,26,27,29});
			List<DrMemberFundsLog> list = drMemberFundsLogDAO.getDrMemberFundsLogList(map); 
			
			resultMap.put("list",list);
			
			//活动返利明细
			List<Map<String,Object>> activitySumList = drMemberFundsLogDAO.getDrMemberRegSum(map);
			
			BigDecimal amount = BigDecimal.ZERO;
			for(Map<String,Object> m:activitySumList){
				if(25 == (Integer)m.get("varyType")){//注册的返利
					resultMap.put("regSum",m.get("amounts"));
				}
				if(29 == (Integer)m.get("varyType")){//推荐注册的返利
					resultMap.put("rcommRegSum",m.get("amounts"));
				}
				if(26 == (Integer)m.get("varyType")){//用户投资返利
					resultMap.put("intvestRebateSum",m.get("amounts"));
				}
				if(27 == (Integer)m.get("varyType")){//推荐投资返利
					resultMap.put("recommRebateSum",m.get("amounts"));
				}
				amount = Utils.nwdBcadd(amount, m.get("amounts"));
			}
			//活动总返利
			resultMap.put("amount",amount);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	

	@Override
	public List<DrMemberFundsLog> getRecord(DrMemberFundsLog drMemberFundsLog) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<DrMemberFundsLog> list = null;
		//市场活动记录
		try {
			map.put("uid", drMemberFundsLog.getUid());
//			map.put("startDate", drMemberFundsLog.getStartDate());
//			map.put("endDate", drMemberFundsLog.getEndDate());
			map.put("varyTypes", new int[]{25,26,27,29});
			map.put("offset",0); 
			map.put("limit",100); 
			list = drMemberFundsLogDAO.getDrMemberFundsLogList(map);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	@Override
	public int getRegRebateSumNumber(Map<String, Object> map) {
		
		return drMemberFundsLogDAO.getRegRebateSumNumber(map);
	}

	@Override
	public List<Map<String, Object>> getRecommendActivityRebateSum(
						Map<String, Object> map) {
		
		List<Map<String, Object>> list = drMemberFundsLogDAO.getRecommendActivityRebateSum(map);
		
		return list;
	}


}
