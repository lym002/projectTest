package com.jsjf.dao.system;

import com.jsjf.model.system.SysRoleMenuVo;

import java.sql.SQLException;

public interface SysRoleMenuVoDAO {

	/**
	 * @Description 添加权限
	 * @param SysRoleMenuVo
	 * @return void
	 * @throws SQLException
	 */
	public void addRoleMenu(SysRoleMenuVo roleMenuVo);
	
	/**
	 * @Description 删除权限
	 * @param SysRoleMenuVo
	 * @return void
	 * @throws SQLException
	 */
	public void deleteRoleMenu(SysRoleMenuVo roleMenuVo);
	
	/**
	 * @Description 根据角色ID查询菜单ID
	 * @param roleKy
	 * @return String[]
	 * @throws SQLException
	 */
	public String[] queryRoleMenuByRoleKy(Long roleKy);
}