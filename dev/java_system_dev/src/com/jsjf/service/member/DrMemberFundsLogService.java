package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberFundsLog;

public interface DrMemberFundsLogService {
	
	/**
	 * 得到 客户收支记录列表数据
	 * @param DrMemberFundsLog
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getMemberFundsLogList(DrMemberFundsLog drMemberFundsLog,PageInfo pi);
	
	/**
	 * 统计收入支出
	 * @param DrMemberFundsLog
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getDrMemberFundsLogSum(DrMemberFundsLog drMemberFundsLog);
	
	/**
	 * 获取用户最近的资金记录
	 * @param map
	 * @return
	 */
	public List<DrMemberFundsLog> getMemberFundsLogList(Map<String,Object> map);
}