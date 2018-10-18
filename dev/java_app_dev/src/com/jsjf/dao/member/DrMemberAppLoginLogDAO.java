package com.jsjf.dao.member;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMemberAppLoginLog;

public interface DrMemberAppLoginLogDAO {

	/**
	 * 查询有效的登录TOKEN
	 * @param uid
	 * @return
	 */
	public DrMemberAppLoginLog selectValidLog(@Param("uid") Integer uid);
	
	public void insert(DrMemberAppLoginLog loginLog);
	
	public void update(@Param("uid") Integer uid);
	
	/**
	 *从 js_phone_token 表里 根据 phone 获得token
	 * @return
	 */
	public String getPhoneToken(@Param("phone") String phone);
	/**
	 *	在js_phone_token 表里 添加 phone 的token
	 * @return
	 */
	public void setPhoneToken(Map<String,Object> map);
	
	/**
	 * 在js_phone_token 表里删除phone 的token
	 * @param phone
	 */
	public void deletePhoneToken(String phone) ;
	
}
