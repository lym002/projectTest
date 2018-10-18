package com.jsjf.dao.app;

import java.util.List;
import java.util.Map;

import com.jsjf.model.app.SysAppRenewal;

public interface SysAppRenewalDAO {
	/**
	 * 新增APP更新
	 * @param sysAppRenewal
	 */
	public void insertSysAppRenewal(SysAppRenewal sysAppRenewal);
	/**
	 * 获取App更新列表
	 * @param map
	 * @return
	 */
	public List<SysAppRenewal> getSysAppRenewalList(Map<String, Object> map);
	/**
	 * 获取APP更新条数
	 * @param map
	 * @return
	 */
	public Integer getSysAppRenewalCount(Map<String,Object> map);
	/**
	 * 修改APP更新
	 * @param sysappRenewal
	 */
	public void updateSysAppRenewal(SysAppRenewal sysappRenewal);
	
	/**
	 * 更新状态
	 */
	public void updateStatus(SysAppRenewal sysappRenewal);
	
	/**
	 * 根据id获取app更新详情
	 * @param id
	 * @return
	 */
	public SysAppRenewal getSysAppRenewalById(Integer id);
}
