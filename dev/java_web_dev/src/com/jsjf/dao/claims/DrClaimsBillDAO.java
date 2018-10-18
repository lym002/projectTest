package com.jsjf.dao.claims;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.claims.DrClaimsBill;


public interface DrClaimsBillDAO {
	
	/**
	 * 通过标的ID获取票据信息
	 * @param sid
	 * @return
	 */
	public DrClaimsBill getDrClaimsBillBySid(@Param(value="sid") Integer sid);
}
