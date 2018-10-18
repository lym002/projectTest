package com.jsjf.dao.member;

import java.util.Map;

import com.jsjf.model.member.JsMemberInfo;

public interface JsMemberInfoDAO {

	/**
	 * 修改收货地址
	 * @param jsMemberInfo
	 */
	public void updateJsMemberInfo(JsMemberInfo jsMemberInfo);
	/**
	 * 根据uid查询地址
	 * @param uid
	 * @return
	 */
	public JsMemberInfo selectMemberInfoByUid(Integer uid);
	
	/**
	 * 添加收货地址
	 * @param jsMemberInfo
	 */
	public void insertJsMemberInfo(JsMemberInfo jsMemberInfo);
	
	/**
	 * 根据map查询
	 * @param map
	 * @return
	 */
	public JsMemberInfo selectMemberInfoByMap (Map<String,Object> map);
	/**
	 * 查询老地址
	 * @param uid
	 * @return
	 */
	public JsMemberInfo selectOldAddressByUid(Integer uid);
}