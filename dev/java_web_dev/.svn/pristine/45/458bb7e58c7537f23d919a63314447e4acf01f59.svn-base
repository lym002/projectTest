package com.jsjf.dao.member;

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
    
    public void updateMsgToRead(DrMemberMsg drMemberMsg)throws SQLException; 
    
}