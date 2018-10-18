package com.jsjf.service.member.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.common.PageInfo;
import com.jsjf.dao.member.DrMemberRecommendedDAO;
import com.jsjf.model.member.DrMemberRecommended;
import com.jsjf.service.member.DrMemberRecommendedService;

@Service
public class DrMemberRecommendedServiceImpl implements
		DrMemberRecommendedService {
	@Autowired
	private DrMemberRecommendedDAO  drMemberRecommendedDAO;

	@Override
	public void insertDrMemberRecommended(DrMemberRecommended drMemberRecommened) {
		drMemberRecommendedDAO.insertMemberRecommended(drMemberRecommened);
	}

	@Override
	public PageInfo getDrMemberRecommended(Map<String, Object> map) {
		PageInfo pi = new PageInfo();
		List<DrMemberRecommended> list = drMemberRecommendedDAO.getDrMemberRecommended(map);
		Integer total = drMemberRecommendedDAO.getDrMemberRecommendedCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}

	@Override
	public BigDecimal getRewardByReferrerId(Integer referrerId) {
		return drMemberRecommendedDAO.getRewardByReferrerId(referrerId);
	}

}
