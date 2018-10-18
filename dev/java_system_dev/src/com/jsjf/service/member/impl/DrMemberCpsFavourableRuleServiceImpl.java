package com.jsjf.service.member.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.member.DrMemberCpsFavourableRuleDAO;
import com.jsjf.model.member.DrMemberCpsFavourableRule;
import com.jsjf.service.member.DrMemberCpsFavourableRuleService;

@Service
public class DrMemberCpsFavourableRuleServiceImpl implements
		DrMemberCpsFavourableRuleService {

	@Autowired
	private DrMemberCpsFavourableRuleDAO drMemberCpsFavourableRuleDAO;
	
	@Override
	public List<DrMemberCpsFavourableRule> selectByParam(
			Map<String, Object> param) {
		return drMemberCpsFavourableRuleDAO.selectByParam(param);
	}

	@Override
	public Integer selectCountByParam(Map<String, Object> param) {
		return drMemberCpsFavourableRuleDAO.selectCountByParam(param);
	}

	@Override
	public void insert(DrMemberCpsFavourableRule cpsRule) {
		drMemberCpsFavourableRuleDAO.insert(cpsRule);
	}

	@Override
	public void updateBySelective(DrMemberCpsFavourableRule cpsRule) {
		drMemberCpsFavourableRuleDAO.updateBySelective(cpsRule);
	}

}
