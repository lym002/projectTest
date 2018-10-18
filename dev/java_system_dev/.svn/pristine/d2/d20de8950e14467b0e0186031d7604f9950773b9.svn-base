package com.jsjf.service.member;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.jsjf.model.member.DrMemberFunds;


public interface DrMemberFundsService {

	/**
	 * 根据UID查询用户资金表
	 * @param uid
	 * @return DrMemberFunds
	 */
	public DrMemberFunds selectDrMemberFundsByUid(Integer uid);
	
	/**
	 * 修改
	 * @param DrMemberFunds
	 * @return void
	 * @throws SQLException
	 */
    public void updateDrMemberFunds(DrMemberFunds drMemberFunds) throws SQLException;

    List<DrMemberFunds> selectListByUids(String s);

    /**
     * 修复银行提现未处理问题
     * @param s 流水号
     * @param bigDecimal 提现金额
     */
    void updateRepairWithdrawal(String s, BigDecimal bigDecimal);
}
