package com.jsjf.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberFundsLog;

public interface DrMemberFundsLogService {
	
	
	/**
	 * 查询推荐活动返利总收益及用户信息
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getRecommendActivityRebateSum(Map<String,Object> map);
	
	/**
	 * 查询送出注册返利红包的总数
	 * @param map
	 * @return
	 */
	public int getRegRebateSumNumber(Map<String,Object> map);
	/**
	 * @Description 用户注册活动 收益 统计
	 * @param 	int
	 * @return Map<String,Object>
	 * @throws SQLException
	 * @author 
	 */
	public Map<String,Object> getRegActivityFundsLogAndSum(int uid);
	
	
	/**
	 * 用户资产记录
	 * @param DrMemberFundsLog
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getMemberAssetRecordList(DrMemberFundsLog log, PageInfo pi);
	
	/**
	 * 导出查询到的数据
	 * @param drMemberFundsLog
	 * @return
	 */
	public List<DrMemberFundsLog> exportMemberFundsLogList(DrMemberFundsLog drMemberFundsLog)throws Exception;
	
	
	/**
	 * 用户注册活动记录查询
	 * @param 	int
	 * @return Map<String,Object>
	 */
	public List<DrMemberFundsLog> getRecord(DrMemberFundsLog drMemberFundsLog);
	
}