package com.jsjf.service.app;

import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.app.SysAppRenewal;
import com.jsjf.model.system.SysUsersVo;

public interface SysAppRenewalService {
	/**
	 * 插入app更新
	 * @param sysAppRenewal
	 */
	public void insertAppRenewal(SysAppRenewal sysAppRenewal,SysUsersVo usersVo)throws Exception;

	/**
	 * 获取app更新列表
	 * @param map
	 * @param pi
	 * @return
	 */
	public BaseResult getSysAppRenewal(Map<String, Object> map, PageInfo pi)throws Exception;
	
	/**
	 * 修改app更新记录
	 * @param sysAppRenewal
	 */
	public void updateSysAppRenewal(SysAppRenewal sysAppRenewal)throws Exception;
	
	/**
	 * 更新状态
	 */
	public void updateStatus(SysAppRenewal sysAppRenewal)throws Exception;
	
	/**
	 * 根据id获取app更新详情
	 * @param id
	 * @return
	 */
	public SysAppRenewal getSysAppRenewalById(Integer id);
	
	
}
