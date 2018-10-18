package com.jsjf.dao.integral;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.JsChannelCouponPut;
import com.jsjf.model.integral.IntegralSourceBean;

public interface IntegralSourceDao {

	List<IntegralSourceBean> queryIntegralSourceList(Map<String, Object> map);

	int queryIntegralSourceListCount(Map<String, Object> map);

	void updateIntegralSource(IntegralSourceBean integralSourceBean);

}
