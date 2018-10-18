package com.jsjf.service.member;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.jsjf.common.BaseResult;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.member.DrMemberBank;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jzh.data.RechargeAndWithdrawalNoticeData;

public interface DrMemberCrushService {
	
	/**
	 * 添加充值记录
	 * @param HttpServletRequest
	 * @param DrMember 会员信息
	 * @param amount 金额
	 * @param DrMemberBank 会员银行信息
	 * @param SysBank 银行基本信息，判断支付渠道
	 * @return BaseResult
	 * @throws Exception
	 */
	public BaseResult insertPayOrder(HttpServletRequest req,DrMember member,String amount,DrMemberBank drMemberBank,SysBank sysBank)throws Exception;
	
	/**
	 * 添加网银充值记录
	 * @param DrMember 会员信息
	 * @param amount 金额
	 * @param bankCode 银行编号
	 * @param DrMemberBank 会员银行信息
	 * @return Map<String,String>
	 * @throws Exception
	 */
	public Map<String,String> insertWYDrMemberCrush(DrMember member,String amount,String bankCode)throws Exception;
	
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
	 * 金运通网银充值
	 * @param HttpServletRequest
	 * @return BaseResult;
	 * @throws Exception
	 */
	public BaseResult saveJYTWYPay(HttpServletRequest req)throws Exception;
	
	/**
	 * 金运通认证充值异步通知
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws Exception
	 */
	public void receiveNotifyJYT(HttpServletRequest req, HttpServletResponse resp)throws Exception;
	
	/**
	 * 盛付通认证充值异步通知
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws Exception
	 */
	public String receiveNotifySFT(HttpServletRequest req, HttpServletResponse resp)throws Exception;
	
	/**
	 * 根据商户唯一订单号查询
	 * @param payNum
	 * @return DrMemberCrush
	 */
    public DrMemberCrush getDrMemberCrushByPayNum(String payNum); 
    /**
     * 只 只根据商户唯一订单号查询
     * @param payNum
     * @return DrMemberCrush
     */
    public DrMemberCrush getFuiouDrMemberCrushByPayNum(String payNum); 
    
	/**
	 * 根据商户订单号修改
	 * @param DrMemberCrush
	 * @return void
	 * @throws SQLException
	 */
    public void updateMemberCrushById(DrMemberCrush drMemberCrush) throws SQLException;

    /**
     * 融宝快捷充值异步通知
     * @param data
     * @param merchant_id
     * @return
     */
    public String receiveNotifyRB(String data,String merchant_id) throws Exception;
    
    /**
     *  恒丰充值记录
     * @param order
     * @paramType 5.恒丰快捷 6.恒丰网银
     * @return
     * @throws Exception
     */
     
    public void insertFuiouOrder(String order,int type,int uid,BigDecimal amount)throws Exception;
    
	/**
	 * fuiou充值
	 * @param message
	 */
	public void depositsRecharge(DrMemberCrush dmc,DrMember member) throws Exception;
	
	public String updateReFundDrProductLoanStatus(SysFuiouNoticeLog noticeLog) throws SQLException;


    List<DrMemberCrush> selectCrushByUid(Map<String, Object> properties2);
}