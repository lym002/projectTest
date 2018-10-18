package com.jsjf.service.claims;

import java.util.List;

import com.jsjf.model.claims.DrClaimsPic;

public interface DrClaimsPicService {
	
	/**
	 * 通过产品ID获取债权图片
	 * @param pid
	 * @return
	 */
	public List<DrClaimsPic> selectListPicByPid(Integer pid);

}
