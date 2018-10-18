/**
 * 
 */
package com.jsjf.service.member;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMemberBroker;
import com.jsjf.model.system.SysUsersVo;

public interface DrMemberBrokerService {
	
	/**
	 * 分页查询理财代理人申请信息
	 * @param drMemberBroker
	 * @param pi
	 * @return
	 */
	public BaseResult getMemberBrokerList(DrMemberBroker drMemberBroker,PageInfo pi);
	
	/**
	 * 根据ID更新状态和操作人
	 * @param id
	 * @param status
	 * @param userVo
	 * @return
	 */
	public BaseResult updateByPrimaryKey(int id,int status,int level,SysUsersVo usersVo);
	
	
	/**
	 * 根据ID查询理财代理人等级申请
	 * @param id
	 * @return
	 */
	public DrMemberBroker queryDrMemberBrokerById(int id);
}
