package com.jsjf.service.product;

import java.util.List;

import com.jsjf.model.product.DrProductPic;

public interface DrProductPicService {

	/**
	 * 根据PID查找产品相关资料图
	 * @param pid
	 * @return
	 */
	public List<DrProductPic> selectProductPicByPid(Integer pid);
	
}
