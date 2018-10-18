package com.jsjf.service.activity;


import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.JsActivityAggregationPage;
import com.jsjf.model.system.SysUsersVo;

public interface JsActivityAggregationPageService {

	/**
	 * 获取活动列表
	 * @return
	 */
	public BaseResult selectJsActivityAggregationPageList(JsActivityAggregationPage jsActivityAggregationPage, PageInfo pi);

	public void addAggregationPage(JsActivityAggregationPage jsActivityAggregationPage, SysUsersVo usersVo,MultipartFile pcPicFile,MultipartFile appPicFile) throws Exception;
	
	public JsActivityAggregationPage selectJsActivityAggregationPageById(Integer id);
	
	public void updateAggregationPage(JsActivityAggregationPage jsActivityAggregationPage, SysUsersVo usersVo,MultipartFile pcPicFile,MultipartFile appPicFile) throws Exception;
}
