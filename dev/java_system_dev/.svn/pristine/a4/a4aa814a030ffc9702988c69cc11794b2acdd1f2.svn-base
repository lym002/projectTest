package com.jsjf.dao.system;

import com.jsjf.model.system.SysUserRoleVo;

import java.util.List;
import java.util.Map;

public interface SysUserRoleVoDAO {
	
	public void insert(SysUserRoleVo record);

	public void deleteByUserKy(SysUserRoleVo user);

	public SysUserRoleVo selectByUserKy(Long userKy);

	public SysUserRoleVo getByUserRole(SysUserRoleVo userRoleVo);
	
	/**
	 * 
	 * @Description 通过userKy修改用户对应角色信息
	 * @author 何修水
	 */
	public void updateByUserKy(Map<String,Object> map);
	/**
	 * 根据条件查询
	 * @param userRoleVo
	 * @return
	 */
	public List<SysUserRoleVo> getMapUserRole(Map map);

}