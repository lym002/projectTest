package com.jsjf.service.system;

import java.sql.SQLException;
import java.util.Map;

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
	 * 查询phone今天发送type类型短信多少条
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer selectMsgLogSendCount (Map<String,Object> map) throws Exception;
	/**
	 * 短信发送 
	 * @param SysMessageLog
	 * @param type 1-短信 2-语音
	 * @return 短信-成功99，失败-999，语音-成功100，失败-100
	 */
	public Integer sendMsg(SysMessageLog logs,int type);
	
	
}
