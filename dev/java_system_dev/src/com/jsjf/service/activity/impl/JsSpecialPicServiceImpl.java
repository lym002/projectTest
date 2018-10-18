package com.jsjf.service.activity.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.activity.JsSpecialPicDao;
import com.jsjf.model.activity.JsSpecialPic;
import com.jsjf.service.activity.JsSpecialPicService;

@Service
@Transactional
public class JsSpecialPicServiceImpl implements JsSpecialPicService {
	
	@Autowired
	private JsSpecialPicDao jsSpecialPicDao;

	@Override
	public List<JsSpecialPic> selectList(Integer specialId) {
		return jsSpecialPicDao.selectBySpecialId(specialId);
	}

	@Override
	public void deleteByid(Integer id) {
		jsSpecialPicDao.delete(id);
	} 

	
}

