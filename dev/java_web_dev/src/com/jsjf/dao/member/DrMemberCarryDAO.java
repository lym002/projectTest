package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMemberCarry;

public interface DrMemberCarryDAO {
	/**
	 * 添加提现
	 * @param DrMemberCarry
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberCarry(DrMemberCarry drMemberCarry) throws SQLException; 
    
	/**
	 * 修改状态
	 * @param DrMemberCarry
	 * @return void
	 * @throws SQLException
	 */
    public void updateStatusById(DrMemberCarry drMemberCarry) throws SQLException; 
    
    /**
     * 查询历史提现总额
     * @param uid
     * @return
     */
    public BigDecimal selectAmountByUid(@Param(value="uid")Integer uid,@Param(value="type")Integer type);
    
    /**
     * 根据流水号查询提现记录
     * @param mchntTxnSsn
     * @return
     */
    public DrMemberCarry selectDrMemberCarryByPaymentnum(String paymentnum);
}