package com.jsjf.dao.member;

import java.sql.SQLException;

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
	 * 修改
	 * @param DrMemberBank
	 * @return void
	 * @throws SQLException
	 */
    public void updateDrMemberBank(DrMemberBank drMemberBank) throws SQLException; 
    
    /**
	 * 查询存管认证的卡
	 * @param uid
	 * @return DrMemberBank
	 * @throws 
	 */
    DrMemberBank selectFuiouIdentificationBank(int uid);
    
}