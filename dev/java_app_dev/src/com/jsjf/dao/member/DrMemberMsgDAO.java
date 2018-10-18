package com.jsjf.dao.member;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMemberMsg;


public interface DrMemberMsgDAO {
    /**
	 * @Description 添加
	 * @param DrMemberCount
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberMsg(DrMemberMsg drMemberMsg) throws SQLException;
    
    public List<DrMemberMsg> getDrMemberListByParam(Map<String, Object> map);
    
    public int getDrMemberListCountByParam(Map<String, Object> map);
    
    /**
     * 修改消息为已读 
     * @param ids 当ids为空时，标记所有消息为已读
     * @param uid
     */
    public void updateMsgToReadByIds(@Param("ids") Integer[] ids, @Param("uid") Integer uid) throws SQLException; 
    
    /**
     * 删除用户选中消息
     * @param ids
     * @param uid
     * @throws SQLException
     */
    public void updateMsgToDelByIds(@Param("ids") Integer[] ids, @Param("uid") Integer uid) throws SQLException; 
    
    /**
     * 修改消息为已读
     * @param type 消息类型
     * @param uid	用户ID
     * @throws SQLException
     */
    public void updateMsgToRead(@Param("type") Integer type, @Param("uid") Integer uid) throws SQLException;
    /**
     * 根据title 和uid查询站内信是否重复
     * @param title
     * @param uid
     * @return
     */
	public Integer selectMsg(@Param("title")String title, @Param("uid") Integer uid);
    
}