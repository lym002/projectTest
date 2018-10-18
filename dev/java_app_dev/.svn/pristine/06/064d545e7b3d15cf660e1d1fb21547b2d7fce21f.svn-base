package com.jsjf.service.member;

import java.sql.SQLException;
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
	
	/**
     * 修改消息为已读 
     * @param ids 当ids为空时，标记所有消息为已读
     * @param uid
     */
	public void updateMsgToReadByIds(Integer[] id, Integer uid)throws Exception; 
	
	/**
     * 删除用户选中消息
     * @param ids
     * @param uid
     */
    public void updateMsgToDelByIds(Integer[] ids, Integer uid)throws Exception;
    
    /**
     * 修改消息为已读
     * @param type 消息类型
     * @param uid	用户ID
     * @throws SQLException
     */
    public void updateMsgToRead(Integer type, Integer uid) throws SQLException;
    
    /**
     * 插入站内信
     * @param msg
     * @throws Exception
     */
	public void insert(DrMemberMsg msg)throws Exception;
    /**
     * 根据UID,信件名查询是否发送
     * @param title 信件名
     * @param uid 用户uid
     * @return 查询到的条数
     */
	public Integer selectMsg(String title,Integer uid);
}
