package com.jsjf.service.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.system.SysMessageLog;

public interface SysMessageLogService {
	
	/**
	 * 插入手机短信记录，并返回插入后ID
	 * @param sysMessageLog
	 * @return
	 */
	public Integer insert(SysMessageLog sysMessageLog) throws SQLException ;
	
	/**
	 * 根据短信属性查找最后一条记录
	 * @param sysMessageLog
	 * @return
	 */
	public SysMessageLog selectByProperty(SysMessageLog sysMessageLog);
	
	/**
	 * 短信发送 
	 * @param log 短信对象  对象senttime为空为及时发送
	 * @return 发送成功返回999，失败返回-1
	 */
	public Integer sendMsg(SysMessageLog log);
	
	/**
	 * 查询注册短信日志
	 * @param sysMessageLog
	 * @return
	 */
	public BaseResult selectByType(SysMessageLog sysMessageLog,PageInfo pi);
	/**
	 * 短信发送 
	 * @param SysMessageLog
	 * @param type 1-短信 2-语音
	 * @return 短信-成功99，失败-999，语音-成功100，失败-100
	 */
	public Integer sendMsg(SysMessageLog logs,int type);
	/**
	 * 按条件查询
	 * @param mpa
	 * @return
	 */
	public List<SysMessageLog> selectSysMessageLogList(Map<String,Object> map);
	/**
	 * 处理定时短信发送失败的短信
	 * @param mpa
	 * @return
	 */
	public void sendSmsHandle();
	
	/**
	 * 压岁钱领取提醒短信
	 */
	public void luckyMoneySms();
	
	/**
	 * 体验金过期通知
	 */
	public void tiYanJinGuoQiSms();
	
	/**
	 * 查询fuiou 返回码描述
	 */
	public void getFuiouRspCode();
}
