package com.jsjf.service.activity;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.JsOpenDay;

public interface JsOpenDayService {

	public BaseResult insert(JsOpenDay jsOpenDay,MultipartFile img);
	
	public BaseResult update(JsOpenDay jsOpenDay,MultipartFile img);
	
	public BaseResult getOpenDayList(JsOpenDay jsOpenDay,PageInfo pi);
	
	public JsOpenDay selectByPrimaryKey(Integer id);
	
	public List<JsOpenDay> getOpenDayAll();
}
