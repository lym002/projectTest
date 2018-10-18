package com.jsjf.service.system;

import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysUserRoleVo;
import com.jsjf.model.system.SysUsersVo;

public interface SysUserRoleVoService {
	/**
	 * 获取所有用户分页列表
	 * @return
	 */
//	public Pages<SysUsersVo> getUserList(Map  conditionMap,Pages<SysUsersVo> page)throws SQLException;
   /**
    * 根据UserID查询
    * @param userRoleVo
    * @return
    */
	public SysUserRoleVo getByUserRole(SysUserRoleVo userRoleVo) ;
	/**
	 * 根据条件查询
	 * @param userRoleVo
	 * @return
	 */
	public List<SysUserRoleVo> getMapUserRole(Map map) ;
	public SysUserRoleVo selectByUserKy(Long userky);
	/**
	 * 修改分配权限
	 * @param userRole
	 */
	public void updateCastd(SysUsersVo user,String[] rolekycheckall,String[] buttonIdUrlList)throws Exception;
}
