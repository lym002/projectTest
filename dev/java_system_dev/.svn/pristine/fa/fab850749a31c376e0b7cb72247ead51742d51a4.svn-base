package com.jsjf.service.member.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.member.JsThreeFundSituationDAO;
import com.jsjf.service.member.JsThreeFundSituationService;

@Service
@Transactional
public class JsThreeFundSituationServiceImpl implements JsThreeFundSituationService{

	@Autowired JsThreeFundSituationDAO jsThreeFundSituationDAO;

	@Override
	public List<Map<String, Object>> getThreeFundSituation(Map<String, Object> map) {
		return jsThreeFundSituationDAO.getThreeFundSituation(map);
	}

	@Override
	public int getThreeFundSituationCount(Map<String, Object> map) {
		return jsThreeFundSituationDAO.getThreeFundSituationCount(map);
	}

	@Override
	public Map<String, Object> getThreeFundSituationSum(Map<String, Object> map) {
		return jsThreeFundSituationDAO.getThreeFundSituationSum(map);
	}

	@Override
	public void updateThreeFundSituation(Map<String, Object> map) {
		jsThreeFundSituationDAO.updateThreeFundSituation(map);		
	}
}
