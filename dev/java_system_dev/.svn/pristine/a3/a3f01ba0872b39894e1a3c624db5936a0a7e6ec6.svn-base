package com.jsjf.service.member;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberFourElementsLog;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysUsersVo;

public interface DrMemberFourElementsLogService {

	/**
	 * 添加四要素认证日志
	 * @param record
	 * @return
	 */
	void insertMemberFourElementsLog(DrMemberFourElementsLog log);

	/**
	 * 得到 四要素认证日志列表数据
	 * @param DrMemberFourElementsLog
	 * @param PageInfo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult getMemberFourElementsLogList(DrMemberFourElementsLog log,PageInfo pi);
	
	/**
	 * 添加认证
	 * @param DrMemberFourElementsLog
	 * @param SysUsersVo
	 * @return
	 */
	void updateDrMemberFourElementsLog(DrMemberFourElementsLog log,SysUsersVo vo) throws Exception;
	
	/**
	 * 修改认证
	 * @param DrMemberFourElementsLog
	 * @return BaseResult
	 */
	BaseResult updateDrMemberFourElementsLogAgain(Integer uid) throws Exception;
	
	/**
	 * 查询银行信息
	 * @return SysBank
	 */
    public List<SysBank> selectSysBank();
    
    /**
     * 银行失败认证
     * @param drMemberFourElementsLog
     * @param pi
     * @return
     */
	BaseResult drMemberIdentificationLogList(DrMemberFourElementsLog drMemberFourElementsLog, PageInfo pi);
	/**
	 * 重新一次银行认证
	 * @param mobilePhone
	 */
	BaseResult updateDrMemberIdentificationLogAgain(String mobilePhone) throws Exception;
	
}
