package com.jsjf.service.system;

import java.sql.SQLException;
import java.util.List;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.system.SysMenuVo;
import com.jsjf.model.system.SysRoleVo;
public interface SysRoleVoService {
	
	/**
	 * @Description 得到角色列表数据
	 * @param SysRoleVo
	 * @param PageInfo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult getRoleList(SysRoleVo role,PageInfo pi);
	
	/**
	 * @Description 得到角色列表数据
	 * @param SysRoleVo
	 * @return List<SysMenuVo>
	 * @throws SQLException
	 */
	public List<SysMenuVo> getMenuTree(int parentId);
	
	/**
	 * @Description 根据条件查询角色
	 * @param SysRoleVo
	 * @return List<SysRoleVo>
	 * @throws SQLException
	 */
	public List<SysRoleVo> queryRole(SysRoleVo roleVo);
	
	/**
	 * @Description 根据主键查询角色
	 * @param SysRoleVo
	 * @return SysRoleVo
	 * @throws SQLException
	 */
	public SysRoleVo queryRoleByKey(Long roleKy);
	
	/**
	 * @Description 添加角色和权限
	 * @param SysRoleVo
	 * @param selectedRights
	 * @return void
	 * @throws SQLException
	 */
	public void insertRoleMenuList(SysRoleVo roleVo,String[] selectedRights);
	
	/**
	 * @Description 修改角色状态
	 * @param SysRoleVo
	 * @return BaseResult
	 * @throws SQLException
	 */
	public BaseResult updateRoleStatus(SysRoleVo roleVo);
	
	/**
	 * @Description 添加角色和权限
	 * @param SysRoleVo
	 * @param selectedRights
	 * @return void
	 * @throws SQLException
	 */
	public void updateRoleMenu(SysRoleVo roleVo,String[] selectedRights);
	
	/**
	 * @Description 根据角色ID查询菜单ID
	 * @param roleKy
	 * @return String[]
	 * @throws SQLException
	 */
	public String[] queryRoleMenuByRoleKy(Long roleKy);
	
}