package com.jsjf.dao.system;

import com.jsjf.common.Pages;
import com.jsjf.model.system.SysMenuVo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SysMenuVoDAO {
    public int insert(SysMenuVo record);

    public List<SysMenuVo> findList();
    
	/**
	 * 根据用户已分配的�?��菜单ID（parent）与 该用户ID,返回该一级下�?��含该用户权限的二级和三级 的集�?
	 * @author liuyong 20140215
	 */
	public List<SysMenuVo> getMapMenuList_2_3(Map map) ;
	
	/**
	 * 查寻个人权限集合   状�?为可用的
	 */
	public List<SysMenuVo> findByListMenu(Long userId);
	
	/**
	 * 筛选更新菜单对象
	 * @param sysMenuVo
	 * @throws SQLException
	 */
	public void updateByExampleSelective(SysMenuVo sysMenuVo) throws SQLException;
	
	/**
	 * @Description 得到所有菜单
	 * @param SysMenuVo
	 * @return List<SysMenuVo>
	 * @throws SQLException
	 */
    public List<SysMenuVo> getMenuTree(int parentId) throws SQLException; 
}