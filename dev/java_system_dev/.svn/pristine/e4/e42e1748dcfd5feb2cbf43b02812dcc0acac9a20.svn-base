package com.jsjf.service.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.PageInfo;
import com.jsjf.dao.activity.DrGiftCardSetUpDAO;
import com.jsjf.dao.activity.DrGiftCardSetUpDetailDAO;
import com.jsjf.model.activity.DrGiftCardSetUp;
import com.jsjf.model.activity.DrGiftCardSetUpDetail;
import com.jsjf.service.activity.DrGiftCardSetUpService;

@Service
@Transactional
public class DrGiftCardSetUpServiceImpl implements DrGiftCardSetUpService {
	@Autowired
	private DrGiftCardSetUpDAO drGiftCardSetUpDAO;
	@Autowired
	private DrGiftCardSetUpDetailDAO drGiftCardSetUpDetailDAO;

	@Override
	public void insertDrGiftCardSetUp(DrGiftCardSetUp drGiftCardSetUp)throws Exception {
		drGiftCardSetUpDAO.insertDrGiftCardSetUp(drGiftCardSetUp);

	}

	@Override
	public PageInfo getDrGiftCardSetUpList(DrGiftCardSetUp drGiftCardSetUp,PageInfo pi)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelId", drGiftCardSetUp.getChannelId());
		map.put("name",drGiftCardSetUp.getName());
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrGiftCardSetUp> list = drGiftCardSetUpDAO.getDrGiftCardSetUpList(map);
		int total = drGiftCardSetUpDAO.getDrGiftCardSetUpCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}

	@Override
	public DrGiftCardSetUp getDrGiftCardSetUpById(Integer id)throws Exception {
		return drGiftCardSetUpDAO.getDrGiftCardSetUpById(id);
	}

	@Override
	public void updateDrGiftCardSetUp(DrGiftCardSetUp drGiftCardSetUp)throws Exception {
		drGiftCardSetUpDAO.updateDrGiftCardSetUp(drGiftCardSetUp);
	}

	@Override
	public void insertDrGifCardDetail(
			DrGiftCardSetUpDetail drGiftCardSetUpDetail) throws Exception {
		drGiftCardSetUpDetailDAO.insertDrGiftCardSetUpDetail(drGiftCardSetUpDetail);
		
	}

	@Override
	public void batchInsert(List<DrGiftCardSetUpDetail> list) throws Exception {
		drGiftCardSetUpDetailDAO.batchInsert(list);
		
	}

	@Override
	public PageInfo getDrGiftCardSetUpDetailList(DrGiftCardSetUpDetail detail,PageInfo pi)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", detail.getParentId());
		map.put("startDate", detail.getStartDate());
		map.put("endDate", detail.getEndDate());
		map.put("channelId", detail.getChannelId());
		map.put("name", detail.getName());
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrGiftCardSetUpDetail> list = drGiftCardSetUpDetailDAO.getDrGiftCardSetUpDetailList(map);
		int total = drGiftCardSetUpDetailDAO.getDrGiftCardSetUpDetailCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}
	
	@Override
	public PageInfo getDrGiftCardSetUpDetailListByParentId(Integer parentId,PageInfo pi)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentId);
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrGiftCardSetUpDetail> list = drGiftCardSetUpDetailDAO.getDrGiftCardSetUpDetailListByParentId(map);
		int total = drGiftCardSetUpDetailDAO.getDrGiftCardSetUpDetailCount(map);
		pi.setRows(list);
		pi.setTotal(total);
		return pi;
	}
	

	@Override
	public DrGiftCardSetUpDetail getDrGiftCardSetUpDetailById(Integer id)
			throws Exception {
		return drGiftCardSetUpDetailDAO.getDrGiftCardSetUpDetailById(id);
	}

	@Override
	public void updateDrGiftCardSetUpDetailStatus(Integer id)
			throws Exception {
		drGiftCardSetUpDetailDAO.updateDrGiftCardSetUpDetailStatus(id);
		
	}

}
