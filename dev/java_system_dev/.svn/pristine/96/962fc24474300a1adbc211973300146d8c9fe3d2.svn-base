/**
 * 
 */
package com.jsjf.service.member.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.PageInfo;
import com.jsjf.dao.member.DrMemberRecommendedDAO;
import com.jsjf.model.member.DrMemberRecommended;
import com.jsjf.service.member.DrMemberRecommendedService;

/**
 * @author Gerald
 *
 */
@Service
public class DrMemberRecommendedServiceImpl implements DrMemberRecommendedService {
	@Autowired
	private DrMemberRecommendedDAO drMemberRecommendedDAO;

	/*
	 * (non-Javadoc)
	 * @see com.jsjf.service.member.DrMemberRecommendedService#insertDrMemberRecommended(com.jsjf.model.member.DrMemberRecommended)
	 */
	@Override
	public void insertDrMemberRecommended(
			DrMemberRecommended drMemberRecommended) {
		drMemberRecommendedDAO.insertMemberRecommended(drMemberRecommended);
	}

	/* (non-Javadoc)
	 * @see com.jsjf.service.member.DrMemberRecommendedService#getDrMemberRecommmended(com.jsjf.model.member.DrMemberRecommended, com.jsjf.common.PageInfo)
	 */
	 	@Override
	public PageInfo getDrMemberRecommmended(DrMemberRecommended drMemberRecommended,PageInfo pi) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", drMemberRecommended.getUid());
		map.put("referrerId", drMemberRecommended.getReferrerId());
		map.put("mobilephone", drMemberRecommended.getMobilePhone());
		
		List<DrMemberRecommended> list = drMemberRecommendedDAO.getDrMemberRecommended(map);
		int total = drMemberRecommendedDAO.getDrMemberRecommendedCount(map);
		pi.setRows(list);
		pi.setTotal(total);

		return pi;
	}

	@Override
	public List<DrMemberRecommended> getRecommendInfo(Map<String,Object> map) {
		List<DrMemberRecommended> list = drMemberRecommendedDAO.getDrMemberRecommendedStat(map);
		return list;
	}
	
	

}
