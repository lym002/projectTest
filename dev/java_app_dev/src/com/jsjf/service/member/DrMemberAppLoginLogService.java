package com.jsjf.service.member;

import com.jsjf.model.member.DrMember;

public interface DrMemberAppLoginLogService {
	
	public String insertToken(DrMember m);
	
	/**
	 * 给手机号绑定token
	 * @param phone
	 * @return
	 */
	public String getPhoneToken(String phone) ;
	/**
	 * 给手机号绑定token
	 * @param phone
	 * @return
	 */
	public void deletePhoneToken(String phone) ;

}
