package com.jsjf.dao.system;

import com.jsjf.model.system.SysRoleVo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SysRoleVoDAO {
	/**
	 * @Description 得到角色列表数据
	 * @param Map
	 * @return BaseResult
	 * @throws SQLException
	 */
    public List<SysRoleVo> getRoleList(Map map) throws SQLException; 
   
	/**
	 * @Description 得到角色列表数据总数
	 * @param SysRoleVo
	 * @return Integer
	 * @throws SQLException
	 */
    public Integer queryRoleCounts(SysRoleVo role) throws SQLException; 
    

	/**
	 * @Description 根据条件查询角色
	 * @param SysRoleVo
	 * @return List<SysRoleVo>
	 * @throws SQLException
	 */
	public List<SysRoleVo> queryRole(SysRoleVo sysRoleVo) throws SQLException;
	
	/**
	 * @Description 根据主键查询角色
	 * @param roleKy
	 * @return SysRoleVo
	 * @throws SQLException
	 */
	public SysRoleVo queryRoleByKey(Long roleKy) throws SQLException;
	
	/**
	 * @Description 添加角色
	 * @param sysRoleVo
	 * @return int
	 * @throws SQLException
	 */
    public int addRole(SysRoleVo sysRoleVo) throws SQLException;

    /**
	 * @Description 修改角色状态
	 * @param SysRoleVo
	 * @return void
	 * @throws SQLException
	 */
	public void updateRoleStatus(SysRoleVo sysRoleVo) throws SQLException;
	
	  /**
		 * @Description 修改角色
		 * @param SysRoleVo
		 * @return void
		 * @throws SQLException
		 */
	public void updateRole(SysRoleVo sysRoleVo) throws SQLException;
	
}