package com.jsjf.service.activity.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.service.activity.DrMemberFavourableService;

@Service
@Transactional
public class DrMemberFavourableServiceImpl implements DrMemberFavourableService {
	private static Logger log = Logger.getLogger(DrMemberFavourableServiceImpl.class);
	
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	@Autowired
	private DrActivityParameterDAO drActivityParameterDAO;

	@Override
	public List<DrMemberFavourable> selectByParam(Map<String, Object> map) {
		return drMemberFavourableDAO.getMemberFavourableByParam(map);
	}

	@Override
	public Integer getParticipationActivityTotal(Map<String, Object> map) {
		return drMemberFavourableDAO.getParticipationActivityTotal(map);
	}

	@Override
	public void sendFavourable(Integer uid, Integer faId) {
		try {
			DrActivityParameter activity = drActivityParameterDAO.getActivityParameterById(faId);
			DrMemberFavourable favourable = new DrMemberFavourable(activity.getId(),uid, activity.getType(), activity.getCode(), activity.getName(), activity.getAmount(), activity.getRaisedRates(),
					activity.getEnableAmount(), 0, Utils.getDayNumOfDate(1-activity.getDeadline()), "邀请小伙伴，理财更轻松！", 0,0,activity.getProductDeadline(),activity.getMultiple());
			drMemberFavourableDAO.insertIntoInfo(favourable);
		} catch (SQLException e) {
			log.error("用户["+uid+"]邀请好友赠送加息券失败",e);
		}
		
	}

	@Override
	public List<DrMemberFavourable> selectFavourableByPid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DrMemberFavourable> selectFavourableOrderByAmountExpireDate(int uid) {
		return drMemberFavourableDAO.selectFavourableOrderByAmountExpireDate(uid);
	}

	@Override
	public DrMemberFavourable selectByPrimaryKey(Integer id) {
		
		return drMemberFavourableDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<DrMemberFavourable> selectRedMsg(Integer amount) {
		return drMemberFavourableDAO.selectRedMsg(amount);
	}
	@Override
	public int selectRedCountByUid(Integer uid) {
		return drMemberFavourableDAO.selectRedCountByUid(uid);
	}

	@Override
	public Integer selectDrMemberFavourableCountByUid(Map<String, Object> map) {
		return drMemberFavourableDAO.selectDrMemberFavourableCountByUid(map);
	}

	@Override
	public BigDecimal selectExperienceSumByUid(int uid) {
		return drMemberFavourableDAO.selectExperienceSumByUid(uid);
	}

	@Override
	public Map<String, Object> selectExperSumAmountId(int uid) {
		
		return drMemberFavourableDAO.selectExperSumAmountId(uid);
	}

	@Override
	public Map<String, Object> selectExperSumAmountIdByMap(Map<String, Object> map) {
		return drMemberFavourableDAO.selectExperSumAmountIdByMap(map);
	}

	@Override
	public int selectRegSendCount() {
		return drMemberFavourableDAO.selectRegSendCount();
	}

	@Override
	public BigDecimal selectRegSendExperienceGold(Integer uid) {
		return drMemberFavourableDAO.selectRegSendExperienceGold(uid);
	}

	@Override
	public List<DrMemberFavourable> getMemberFavourableByParam(Map<String, Object> map) {
		return drMemberFavourableDAO.getMemberFavourableByParam(map);
	}

	@Override
	public Integer selectIsShowCountByUid(Integer uid) {
		return drMemberFavourableDAO.selectIsShowCountByUid(uid);
	}
	@Override
	public Integer getMemberFavourableTotal(Integer uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", 1);
		map.put("status", 0);
		map.put("source", 3);
		map.put("uid",uid);
		return drMemberFavourableDAO.getMemberFavourableTotal(map);
	}

	@Override
	public DrMemberFavourable selectNotExpiredAndNotUseByUid(Map<String, Object> map) {
		return drMemberFavourableDAO.selectNotExpiredAndNotUseByUid(map);
	}


}