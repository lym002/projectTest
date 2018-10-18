package com.jsjf.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.ConfigUtil;
import com.jsjf.dao.system.SysChooseOptionDAO;
import com.jsjf.model.system.SysChooseOption;
import com.jsjf.service.system.SysChooseOptionService;

@Service
@Transactional
public class SysChooseOptionServiceImpl implements SysChooseOptionService {

	@Autowired
	public SysChooseOptionDAO sysChooseOptionDAO;
	
	@Override

	public List<SysChooseOption> selectByCategory(String category) {
		return sysChooseOptionDAO.selectByCategory(category);
	}
	
	@PostConstruct 
	public void getDictionary() {
		List<SysChooseOption> list = sysChooseOptionDAO.selectByCategory(null);
		for (int i = 0; i < list.size(); i++) {
			if(ConfigUtil.dictionaryMap.containsKey(list.get(i).getCategory())){
				ConfigUtil.dictionaryMap.get(list.get(i).getCategory()).put(list.get(i).getCode(), list.get(i).getCnvalue());
			}else{
				Map<Integer,String> submap = new HashMap<Integer, String>();
				submap.put(list.get(i).getCode(), list.get(i).getCnvalue());
				ConfigUtil.dictionaryMap.put(list.get(i).getCategory(), submap);
			}
		}	
	}

	@Override
	public List<SysChooseOption> select() {
		return sysChooseOptionDAO.select();
	}

	@Override
	public List<SysChooseOption> queryByCategory(String category) {
		return sysChooseOptionDAO.selectByCategory(category);
	}
}
