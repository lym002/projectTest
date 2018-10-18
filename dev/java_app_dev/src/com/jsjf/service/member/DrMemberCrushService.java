package com.jsjf.service.member;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jsjf.common.BaseResult;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.system.SysBank;

public interface DrMemberCrushService {
	/**
	 * 添加充值记录
	 * @param DrMember 会员信息
	 * @return BaseResult;
	 * @throws Exception
	 */
	public DrMemberCrush insertDrMemberCrush(DrMember member,String amount,DrMemberBank drMemberBank,Integer channel)throws Exception;
	
	/**
	 * 金运通充值
	 * @param DrMember 会员信息
	 * @return BaseResult;
	 * @throws Exception
	 */
	public BaseResult saveJYTPay(DrMember member,String amount,DrMemberBank drMemberBank,DrMemberCrush drMemberCrush)throws Exception;
	
	/**
	 * 添加充值记录
	 * @param HttpServletRequest
	 * @param DrMember 会员信息
	 * @param amount 金额
	 * @param DrMemberBank 会员银行信息
	 * @param SysBank 银行基本信息，判断支付渠道
	 * @param channel 渠道
	 * @return BaseResult
	 * @throws Exception
	 */
	public BaseResult insertPayOrder(HttpServletRequest req,DrMember member,String amount,DrMemberBank drMemberBank,SysBank sysBank,Integer channel)throws Exception;
	
	/**
	 * 认证充值
	 * @param HttpServletRequest
	 * @param DrMember 会员信息
	 * @param DrMemberBank 会员银行信息
	 * @param DrMemberCrush 充值信息
	 * @param smsCode 验证码
	 * @return BaseResult;
	 * @throws Exception
	 */
	public BaseResult savePay(HttpServletRequest req,DrMember member,DrMemberBank drMemberBank,DrMemberCrush drMemberCrush,String smsCode)throws Exception;
	
	/**
	 * 根据商户唯一订单号查询
	 * @param payNum
	 * @return DrMemberCrush
	 */
    public DrMemberCrush getDrMemberCrushByPayNum(String payNum); 
    
	/**
	 * 根据商户订单号修改
	 * @param DrMemberCrush
	 * @return void
	 * @throws SQLException
	 */
    public void updateMemberCrushById(DrMemberCrush drMemberCrush) throws SQLException; 
    
    public BaseResult insertPayRB(HttpServletRequest req,DrMember member, String amount,
			DrMemberBank drMemberBank,SysBank sysBank,DrMemberCrush drMemberCrush) throws Exception;
	
    
    /**
     *  恒丰充值记录
     * @param order
     * @param Type 5.恒丰快捷 6.恒丰网银
     * @return
     * @throws Exception
     */
    public void insertFuiouOrder(String order,int type,int uid,BigDecimal amount,Integer channel)throws Exception;
    
	/**
	 * fuiou充值
	 * @param message
	 */
	public void depositsRecharge(DrMemberCrush dmc,DrMember member) throws Exception;

    /**
     * 查询是否是首冲
     * @param properties2
     * @return
     */
    List<DrMemberCrush> selectCrushByUid(Map<String, Object> properties2);
}