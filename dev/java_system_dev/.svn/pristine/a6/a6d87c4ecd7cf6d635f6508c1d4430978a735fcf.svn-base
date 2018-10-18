package com.jsjf.service.cpa.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.cpa.DrChannelKeyWordsDAO;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.cpa.DrChannelKeyWords;
import com.jsjf.service.cpa.DrChannelKeyWordsService;

@Transactional
@Service
public class DrChannelKeyWordsServiceImpl implements DrChannelKeyWordsService {

	@Autowired
	private DrChannelKeyWordsDAO drChannelKeyWordsDAO;
	
	@Override
	public void saveOrUpdate(DrChannelKeyWords drChannelKeyWords) {
		drChannelKeyWordsDAO.saveOrUpdate(drChannelKeyWords);
	}

	@Override
	public PageInfo selectKeywordListByParam(Map<String, Object> param,
			PageInfo pi) {
		param.put("limit", pi.getPageInfo().getLimit());
		param.put("offset", pi.getPageInfo().getOffset());
		List<DrChannelKeyWords> list = drChannelKeyWordsDAO.selectKeywordListByParam(param);
		Integer total = drChannelKeyWordsDAO.selectKeywordListCountByParam(param);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}

	@Override
	public Integer updateKeyWordStatusById(Integer id, Integer status) {
		return drChannelKeyWordsDAO.updateKeyWordStatusById(id, status);
	}

	@Override
	public Map<String, Object> getKeyWordUrlByCpaId(Integer id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("excelName", "channel_keyWord_Url"+System.currentTimeMillis()+".xls");

		
		List<DrChannelKeyWords> list = drChannelKeyWordsDAO.getKeyWordUrlByCpaId(id);
		String[] title = new String[]{"序号","渠道","渠道号","活动","活动编号","关键字","关键字编号","URL"};
		Integer[] columnWidth = new Integer[]{5,20,20,20,20,20,20,80};
		
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		int i=1;
		for(DrChannelKeyWords kw:list){
			lc = new ArrayList<Object>();
			lc.add(i++);
			lc.add(kw.getName() == null ? "":kw.getName());
			lc.add(kw.getCode() == null ? "":kw.getCode());
			lc.add(kw.getActivityName() == null ? "":kw.getActivityName());
			lc.add(kw.getActivityCode() == null ? "":kw.getActivityCode());
			lc.add(kw.getKeyword() == null ? "":kw.getKeyword());
			lc.add(kw.getkCode() == null ? "":kw.getkCode());
			lc.add(kw.getUrl() == null ? "":kw.getUrl());
			tableList.add(lc);
		}
		
		map.put("titles", title);
		map.put("columnWidth", columnWidth);
		map.put("list", tableList);
		return map;

	}

}
