package com.jsjf.service.member;

import java.util.Map;

import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberMsg;


public interface DrMemberMsgService {
	/**
	 * 查询未读消息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getUnReadCountByMap(Map<String,Object> map)throws Exception;
	
	/**
	 * 分页查询数据
	 * @param uid
	 * @return
	 */
	public PageInfo getDrMemberParam(PageInfo pi,Map<String, Object> model)throws Exception;
	
	
	public void updateMsgToRead(DrMemberMsg drMemberMsg)throws Exception; 
	
	
	public void insert(DrMemberMsg msg)throws Exception;
}
