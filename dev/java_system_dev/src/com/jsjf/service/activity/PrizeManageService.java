package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.BypCommodityBean;
import com.jsjf.model.product.DrProductInfo;

public interface PrizeManageService {
	public BaseResult queryPrizeManageList(Map<String,Object> param,PageInfo pi);

	public BaseResult addDrPrizeManage(BypCommodityBean bypCommodityBean);


	public BypCommodityBean getDrPrizeManage(Integer id);

	public BaseResult updateDrPrizeManage(BypCommodityBean bypCommodityBean);

	public void updateProductUrl(String prid, MultipartFile appPicFile);

	/**
	 * 下拉款加载
	 * @return
	 */
	public List<BypCommodityBean> queryPrize();
	
	/**
	 * 红包下拉框
	 * @return
	 */
	public List<BypCommodityBean> queryHb();
}
