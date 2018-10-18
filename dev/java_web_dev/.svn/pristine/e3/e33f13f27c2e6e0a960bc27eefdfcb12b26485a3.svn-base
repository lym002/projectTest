package com.jsjf.service.cpa.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.cpa.DrChannelInfoDAO;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.service.cpa.DrChannelInfoService;
@Service
public class DrChannelInfoServiceImpl implements DrChannelInfoService {
	@Autowired
	private DrChannelInfoDAO drChannelInfoDAO;
	@Override
	public List<DrChannelInfo> getDrChannelInfoListForMap(
			Map<String, Object> map) {
		return drChannelInfoDAO.getDrChannelInfoListForMap(map);
	}

}
