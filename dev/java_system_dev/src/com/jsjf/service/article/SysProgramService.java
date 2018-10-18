package com.jsjf.service.article;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.article.SysProgram;
import com.jsjf.model.system.SysUsersVo;


public interface SysProgramService {

	/**
	 * 拿到栏目列表数据
	 * @param SysProgram
	 * @param  PageInfo
	 * @return BaseResult  
	 */
	public BaseResult getSysProgramList(SysProgram sysProgram,PageInfo pi);
	
	/**
	 * 添加栏目
	 * @param SysProgram
	 * @param  SysUsersVo
	 * @return BaseResult  
	 */
	public BaseResult addSysProgram(SysProgram sysProgram,SysUsersVo usersVo);
	
	/**
	 * 修改栏目
	 * @param SysProgram
	 * @param  SysUsersVo
	 * @return BaseResult  
	 */
	public BaseResult updateSysProgram(SysProgram sysProgram,SysUsersVo usersVo);
	
	/**
	 * 删除栏目
	 * @param SysProgram
	 * @param  SysUsersVo
	 * @return BaseResult  
	 */
	public BaseResult deleteSysProgram(SysProgram sysProgram);
	/**
	 * 恢复栏目
	 * @param SysProgram
	 * @param  SysUsersVo
	 * @return BaseResult  
	 */
	public BaseResult recoverSysProgram(SysProgram sysProgram);
}
