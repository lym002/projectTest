package com.jsjf.service.cpa;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.cpa.DrChannelInfoAndroidTailBean;

public interface DrChannelInfoAndroidInvestTailService {


	BaseResult queryAndroidInvestTailList(Map<String, Object> param, PageInfo pi);

}
