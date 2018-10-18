package com.jsjf.service.member;

import java.sql.SQLException;

import net.sf.json.JSONObject;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberCrush;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysUsersVo;

public interface DrMemberCrushService {
	
	/**
	 * 得到充值待审列表数据
	 * @param DrMemberCrush
	 * @param PageInfo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult getMemberCrushList(DrMemberCrush drMemberCrush,PageInfo pi);
	
	/**
	 * 拒绝
	 * @param DrMemberCrush
	 * @param SysUsersVo
	 * @return void
	 * @throws SQLException
	 */
	public void updateMemberCrushRefuse(DrMemberCrush drMemberCrush,SysUsersVo usersVo) throws Exception;
	
	/**
	 * 成功
	 * @param id
	 * @param SysUsersVo
	 * @return void
	 * @throws SQLException
	 */
	public void updateMemberCrushAudit(int id,SysUsersVo usersVo) throws Exception;
	
	/**
	 * 得到充值记录列表数据
	 * @param DrMemberCrush
	 * @param PageInfo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult getMemberCrushRecordList(DrMemberCrush drMemberCrush,PageInfo pi);
	
	/**
	 * 充值金额合计
	 * @param Map<String, Object>
	 * @return Double
	 * @throws SQLException
	 */
    public Double getDrMemberCrushRecordSum(DrMemberCrush drMemberCrush); 
    
    /**
	 * 添加充值记录
	 * @param DrMemberCrush
	 * @return BaseResult
	 * @throws SQLException
	 */
    public BaseResult addMemberCrush(DrMemberCrush drMemberCrush,SysUsersVo usersVo); 
    
	/**
	 * 根据ID查询
	 * @param id
	 * @return DrMemberCrush
	 * @throws SQLException
	 */
    public DrMemberCrush getDrMemberCrushById(int id) ; 
    
	/**
	 * 每10分钟去连连支付查询支付结果
	 * @return void
	 * @throws Exception
	 */
	public void updatePayResult()throws Exception;
	
	public void updatePayResult(String paynum)throws Exception;
	
	public void updateFuiouCrush(String paynum,BaseResult result);
	
	/**
	 * 恒丰充值
	 * @param message
	 */
	public void depositsRecharge(JSONObject message,Integer type,SysFuiouNoticeLog log) throws Exception;
	
}