package com.jsjf.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.jsjf.dao.system.SysFuiouNoticeLogDAO;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.service.system.RedisDataSource;
import com.jsjf.service.system.SysFuiouNoticeLogService;

@Service
@Transactional
public class SysFuiouNoticeLogServiceImpl implements SysFuiouNoticeLogService {

	@Autowired
	private SysFuiouNoticeLogDAO sysFuiouNoticeLogDAO;
	

	@Override
	public void insert(SysFuiouNoticeLog sysFuiouNoticeLog) {
		sysFuiouNoticeLogDAO.insert(sysFuiouNoticeLog);
	}
	@Override
	public SysFuiouNoticeLog selectObjectBy_txn_ssn(String mchnt_txn_ssn) {
		
		return sysFuiouNoticeLogDAO.selectObjectBy_txn_ssn(mchnt_txn_ssn);
	}

	@Override
	public void update(SysFuiouNoticeLog sysFuiouNoticeLog) {
		sysFuiouNoticeLogDAO.update(sysFuiouNoticeLog);
	}
}