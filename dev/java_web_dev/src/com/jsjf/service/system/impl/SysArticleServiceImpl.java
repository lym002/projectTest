package com.jsjf.service.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.system.SysArticleDAO;
import com.jsjf.dao.system.SysProgramDAO;
import com.jsjf.model.system.SysArticle;
import com.jsjf.service.system.SysArticleService;

@Service
@Transactional
public class SysArticleServiceImpl implements SysArticleService {

	@Autowired
	public SysArticleDAO sysArticleDAO;
	@Autowired
	public SysProgramDAO sysProgramDAO;

	@Override
	public SysArticle selectSysArticleById(Integer id)throws Exception {
		SysArticle article = sysArticleDAO.selectSysArticleById(id);
		return article;
	}
	
	@Override
	public BaseResult getArticleByParam(Map<String,Object> map,PageInfo info) {
		BaseResult br = new BaseResult();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit", info.getPageInfo().getLimit());
		List<SysArticle>  list = sysArticleDAO.getArticleByParam(map);
		int total = sysArticleDAO.getSysArticleCount(map);
		info.setRows(list);
		info.setTotal(total);
		map.clear();
		map.put("page", info);
		br.setMap(map);
		return br;
	}

	@Override
	public List<SysArticle> getArticleByParam(Map<String, Object> map) {
		return sysArticleDAO.getArticleByParam(map);
	}

	@Override
	public List<Map<String,Object>> getIndexArticle(Map<String, Object> map) {		
		return sysArticleDAO.getIndexArticle(map);
	}

	@Override
	public List<SysArticle> getArticleByUid(Map<String, Object> map) {
		return sysArticleDAO.getArticleByUid(map);
	}
}
