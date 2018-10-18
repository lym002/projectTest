package com.jsjf.service.claims;

import java.util.List;

import com.jsjf.model.claims.DrClaimsProject;

public interface DrClaimsProjectService {

	/**
	 * 通过产品ID获取项目审核
	 * @param pid
	 * @return
	 */
	public List<String> selectListProjectByPid(Integer pid);
}
