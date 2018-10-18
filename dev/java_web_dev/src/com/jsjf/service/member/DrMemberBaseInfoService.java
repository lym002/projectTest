package com.jsjf.service.member;


import java.sql.SQLException;
import java.util.Map;

import com.jsjf.model.member.DrMemberBaseInfo;

public interface DrMemberBaseInfoService {
	
	
	/**
	 * 判断邮箱是否存在
	 * @param email
	 * @return true 存在    false不存在
	 * @throws Exception
	 */
	public Boolean vertifyEmail(String email) throws Exception;
	
	/**
	 * 通过uid获取用户详细信息
	 * @param uid
	 * @return
	 */
	public DrMemberBaseInfo queryMemberBaseInfoByUid(Integer uid);
    /**
	 * @Description 根据（Id，uid，cid）修改会员基本信息
	 * @param DrMemberBaseInfo
	 * @return void
	 * @throws SQLException
	 */
	public void updateMemberBaseInfoByUid(DrMemberBaseInfo baseinfo)throws Exception;
	
    
	/**
	 * 根据条件查询总数
	 * @param Map<String,Object>
	 * @return Integer
	 * @throws SQLException
	 */
	public Integer queryMemberBaseInfoCountByMap(Map<String,Object> map) throws SQLException; 
	
}
