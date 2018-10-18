package com.jsjf.service.operate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.operate.DrUserAnalysisDAO;
import com.jsjf.model.operate.DrUserAnalysis;
import com.jsjf.service.operate.DrUserAnalysisService;

@Service
@Transactional
public class DrUserAnalysisServiceImpl implements DrUserAnalysisService {
	@Autowired
	private DrUserAnalysisDAO drUserAnalysisDAO;

	@Override
	public BaseResult getDrUserAnalysisList(DrUserAnalysis drUserAnalysis,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startDate", Utils.format(drUserAnalysis.getStartDate(), "yyyy-MM-dd"));
		map.put("endDate", Utils.format(drUserAnalysis.getEndDate(), "yyyy-MM-dd"));
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<DrUserAnalysis> list = drUserAnalysisDAO.getDrUserAnalysisList(map);
		Integer total = drUserAnalysisDAO.getDrUserAnalysisCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
}
