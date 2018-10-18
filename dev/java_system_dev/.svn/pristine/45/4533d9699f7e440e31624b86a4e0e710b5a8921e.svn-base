package com.jsjf.service.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysMenuVo;

public interface SysMenuVoService {
	
	/**
	 * 查寻个人权限集合 状态可用  升级版
	 */
	public List<SysMenuVo> findByListMenu(Long userId);
	
	//查询全部权限
	public List<SysMenuVo> getMenuList();
	/**
	 * 根据用户已分配的一级菜单ID（parent）与 该用户ID,返回该一级下所包含该用户权限的二级和三级 的集合 
	 * @author liuyong 20140215
	 */
	public List<SysMenuVo> getMapMenuList_2_3(Map map) ;
	
	//状态变更
	public void update(SysMenuVo menuVo) throws SQLException;
	
	public int insert(SysMenuVo menuVo);
}
