package com.jsjf.service.member.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.member.JsMemberInfoDAO;
import com.jsjf.model.member.JsMemberInfo;
import com.jsjf.service.member.JsMemberInfoService;

@Service
public class JsMemberInfoServiceImpl implements JsMemberInfoService {

	@Autowired
	private JsMemberInfoDAO jsMemberDao;
	
	@Override
	public BaseResult getJsMemberInfoList(Map<String, Object> param, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<JsMemberInfo> list = jsMemberDao.getJsMemberInfoList(param);
		Integer total = jsMemberDao.getJsMemberInfoListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<Map<String, Object>> getJsMemberInfoListForExl(
			Map<String, Object> param) {
		return  jsMemberDao.getJsMemberInfoListForExl(param);
	}

}
