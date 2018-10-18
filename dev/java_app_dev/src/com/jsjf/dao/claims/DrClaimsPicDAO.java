package com.jsjf.dao.claims;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.claims.DrClaimsPic;

public interface DrClaimsPicDAO {
	
	/**
	 * 通过产品ID获取债权图片
	 * @param pid
	 * @return
	 */
	public List<DrClaimsPic> selectListPicByPid(@Param("pid") Integer pid);

}
