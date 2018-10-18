package com.jsjf.dao.product;

import java.util.List;
import java.util.Map;

import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInfoVo;

public interface SynPayProductInfoDao {

	List<DrProductInfoVo> getSynProjectInfo();

	void updateSynStatus(Integer id);

	List<DrProductInfoVo> getSynRefundProductInfo();

	void updateSynRefundStatus(Integer id);

}
