package com.jsjf.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.system.SysBank;


public interface DrMemberBankService {
	
	/**
	 * 验证银行卡四要素和添加银行卡
	 * @param realName
	 * @param idCards
	 * @param bankNo
	 * @param phone
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult insertDrMemberBank(Integer uid,String realName,String idCards,String bankNo,String phone) throws Exception;
    
	/**
	 * 根据map查询银行信息
	 * @param map
	 * @return SysBank
	 */
    public SysBank selectSysBank(Map<String,Object> map);
    
	/**
	 * 根据map查询银行信息List
	 * @return List<SysBank>
	 */
	public List<SysBank> selectSysBankList() ;
    
	/**
	 * 查询认证的卡
	 * @return DrMemberBank
	 * @throws 
	 */
    DrMemberBank selectIdentificationBank(int uid);
    /**
     * 查询银行每日限额、单笔限额
     * @return
     */
    public List<SysBank> selectBank();
    
    DrMemberBank selectFuiouIdentificationBank(int uid);
    
	/**
	 * 修改
	 * @param DrMemberBank
	 * @return void
	 * @throws SQLException
	 */
    public void updateDrMemberBank(DrMemberBank drMemberBank) throws SQLException; 
    
}
