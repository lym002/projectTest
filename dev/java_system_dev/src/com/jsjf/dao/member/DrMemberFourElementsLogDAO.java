package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberFourElementsLog;

public interface DrMemberFourElementsLogDAO {
	/**
	 * 添加四要素认证日志
	 * @param DrMemberFourElementsLog
	 * @return void
	 */
	void insertMemberFourElementsLog(DrMemberFourElementsLog record);
	
	/**
	 * 修改四要素认证日志
	 * @param DrMemberFourElementsLog
	 * @return void
	 */
	void updateMemberFourElementsLog(DrMemberFourElementsLog record);

	/**
	 * 查询四要素认证次数
	 * 
	 * @param map
	 * @return
	 */
	List<DrMemberFourElementsLog> queryMemberFourElementsLogList(Integer uid);

    /**
     * 查询银行四要素认证日志列表数据
     * @param map
     * @return List<DrMemberFourElementsLog>
     */
    public List<DrMemberFourElementsLog> getMemberFourElementsLogList(Map<String,Object> map);
    
    /**
     * 查询银行四要素认证日志列表总数
     * @param map
     * @return Integer
     */
    public Integer getMemberFourElementsLogCounts(Map<String,Object> map);
    
    /**
     * 查询银行失败认证列表
     * @param map
     * @return
     */
	List<DrMemberFourElementsLog> getMemberIdentificationLogList(Map<String, Object> map);
	/**
	 * 查询银行认证失败列表总数
	 * @param map
	 * @return
	 */
	Integer getMemberIdentificationLogCounts(Map<String, Object> map);
	/**
	 * 通过手机号查询
	 * @param mobilePhone
	 * @return
	 */
	List<DrMemberFourElementsLog> queryMemberIdentificationLogList(String mobilePhone);
	
	public void updateFourelementsLog(Map<String, Object> map);
	
	public void insertFourelementsLog(Map<String, Object> map);

}