package com.jsjf.service.system.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.system.SysMenuVoDAO;
import com.jsjf.model.system.SysMenuVo;
import com.jsjf.service.system.SysMenuVoService;

/**
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysMenuVoServiceImpl implements SysMenuVoService {
	
	@Autowired
	SysMenuVoDAO sysMenuVoDAO;
	
	public SysMenuVoDAO getSysMenuVoDAO() {
		return sysMenuVoDAO;
	}

	public void setSysMenuVoDAO(SysMenuVoDAO sysMenuVoDAO) {
		this.sysMenuVoDAO = sysMenuVoDAO;
	}


	/**
	 * 查寻个人权限集合 状态可用 升级版
	 */
	public List<SysMenuVo> findByListMenu(Long userId) {
		return sysMenuVoDAO.findByListMenu(userId);
	}

	/**
	 * 根据用户已分配的一级菜单ID（parent）与 该用户ID,返回该一级下所包含该用户权限的二级和三级 的集合
	 * @author liuyong 20140215
	 */
	public List<SysMenuVo> getMapMenuList_2_3(Map map) {
		return sysMenuVoDAO.getMapMenuList_2_3(map);
	}

	@Override
	public List<SysMenuVo> getMenuList() {
		return sysMenuVoDAO.findList();
	}


	@Override
	public void update(SysMenuVo menuVo) throws SQLException {
		 sysMenuVoDAO.updateByExampleSelective(menuVo);
	}
	
	@Override
	public int insert(SysMenuVo menuVo){
		return sysMenuVoDAO.insert(menuVo);
		
	}

}
