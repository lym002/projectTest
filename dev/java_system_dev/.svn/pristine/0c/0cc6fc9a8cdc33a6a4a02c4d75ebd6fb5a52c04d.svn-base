package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.Map;

import com.jsjf.model.member.DrMemberBank;


public interface DrMemberBankDAO {
	
	/**
	 * 添加银行卡
	 * @param DrMemberBank
	 * @return void
	 * @throws SQLException
	 */
    void insertDrMemberBank(DrMemberBank drMemberBank) throws SQLException;
    
    
	/**
	 * 查询认证的卡
	 * @param uid
	 * @return DrMemberBank
	 * @throws 
	 */
    DrMemberBank selectIdentificationBank(int uid);
    
	/**
	 * @Description 根据bankId查询
	 * @param bankId
	 * @return DrMemberBank
	 * @throws 
	 */
    DrMemberBank selectDrMemberBankById(int bankId);
    
	/**
	 * 根据用户ID修改银行卡状态
	 * @param DrMemberBank
	 * @return void
	 * @throws SQLException
	 */
    void updateDrMemberBankByStatus(DrMemberBank drMemberBank) throws SQLException;
    
    public void updateMemberBank(Map<String, Object>map);
    
    public void insertMemberBank(Map<String, Object>map);
}