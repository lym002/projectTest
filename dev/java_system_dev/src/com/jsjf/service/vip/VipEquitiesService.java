package com.jsjf.service.vip;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.vip.VipEquities;

public interface VipEquitiesService {
	
	List<VipEquities> queryQy();


	BaseResult queryVipEquitiesList(Map<String, Object> param, PageInfo pi);

	BaseResult addVipEquities(VipEquities bean);


	BaseResult updateVipEquities(VipEquities bean);

}