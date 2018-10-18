package com.jsjf.service.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.member.DrCarryParam;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.member.DrMemberFunds;
import com.jzh.data.WtRechargeAndWtWithdrawalRspData;

public interface DrMemberCarryService {
	
	/**
	 * 添加提现
	 * @param DrMember
	 * @param drMemberCarry
	 * @param drMemberBank
	 * @param drMemberFunds
	 * @param drCarryParam
	 * @return Map<String, Object>
	 * @throws SQLException
	 */
    public Map<String, Object> insertDrMemberCarry(DrMember member,DrMemberCarry drMemberCarry,DrMemberBank drMemberBank,DrMemberFunds drMemberFunds,DrCarryParam drCarryParam) throws Exception; 
    
	/**
	 * 发送到金运通
	 * @param DrMember
	 * @param Map<String, Object>
	 * @param drMemberBank
	 * @param drCarryParam
	 * @return BaseResult
	 * @throws SQLException
	 */
    public BaseResult saveJYTpayment(DrMember member,Map<String, Object> map,DrMemberBank drMemberBank,DrCarryParam drCarryParam) throws Exception; 
	
	/**
	 * 根据UID判断是否收取手续费 
	 * @param uid
	 * @param free
	 * @return  0-不收手续费 1-收手续费
	 */
    public Integer getDrCarryParamIsCharge(Integer uid, Integer free);

	/**
	 * 根据UID判断是否收取手续费
	 * @param uid
	 * @param free
	 * @return  0-不收手续费 1-收手续费
	 */
    public Integer getDrCarryParamIsChargeNew(Integer uid, Integer free);
    
	/**
	 * 拿到提现设置信息
	 * @return DrCarryParam
	 */
    public DrCarryParam getDrCarryParam(); 
    
    /**
     * 提现前判断判断方法
     * @param uid
     * @return
     */
    public boolean checkDrMemberCarryAmount(Integer uid,BigDecimal amount,Integer type);
    
    /**
	 * 添加提现
	 * @param DrMember
	 * @param drMemberCarry
	 * @param drMemberBank
	 * @param drMemberFunds
	 * @param drCarryParam
	 * @return Map<String, Object>
	 * @throws SQLException
	 */
    public Map<String, Object> insertFuiouDrMemberCarry(DrMember member,DrMemberCarry drMemberCarry,DrMemberBank drMemberBank,DrMemberFunds drMemberFunds,DrCarryParam drCarryParam) throws Exception;
    
    
    /**
     * 根据流水号查询提现记录
     * @param mchntTxnSsn
     * @return
     */
    public String selectDrMemberCarryByPaymentnum(WtRechargeAndWtWithdrawalRspData rspData);
}