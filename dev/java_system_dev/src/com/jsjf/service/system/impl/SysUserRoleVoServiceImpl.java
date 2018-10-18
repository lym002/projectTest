package com.jsjf.service.system.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.Pages;
import com.jsjf.dao.system.SysUserRoleVoDAO;
import com.jsjf.dao.system.SysUsersVoDAO;
import com.jsjf.model.system.SysUserRoleVo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.system.SysUserRoleVoService;
import com.jsjf.service.system.SysUsersVoService;

/**
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class SysUserRoleVoServiceImpl implements  SysUserRoleVoService {
	
	@Autowired
	SysUserRoleVoDAO sysUserRoleVoDAO;
	
	@Autowired
	SysUsersVoDAO sysUserVoDAO;
//	private SysUserButtonDao sysUserButtonDao;
	



	/**
	 * 按用户ky查找对应关系
	 */
	public SysUserRoleVo selectByUserKy(Long userky) {
		return sysUserRoleVoDAO.selectByUserKy(userky);
	}
	/**
	 * 根据条件查询
	 * @param userRoleVo
	 * @return
	 */
	public List<SysUserRoleVo> getMapUserRole(Map map){
		return sysUserRoleVoDAO.getMapUserRole(map);
	}

	/**
	 * 修改分配权限
	 */
	public void updateCastd(SysUsersVo user,String[] rolekycheckall,String[] buttonIdUrlList)throws Exception{
		
		SysUserRoleVo userRoleVo=new SysUserRoleVo();
		userRoleVo.setUserKy(user.getUserKy());
		sysUserRoleVoDAO.deleteByUserKy(userRoleVo);
//		SysUserButton userButton=new SysUserButton();
//		userButton.setUserKy(user.getUserKy());
//		sysUserButtonDao.deleteSysUserButton(userButton);
		SysUserRoleVo userRole=new SysUserRoleVo();
		if(rolekycheckall!=null){
			for (int i = 0; i < rolekycheckall.length; i++) {
				userRole.setUserKy(user.getUserKy());
				userRole.setRoleKy(Long.parseLong(rolekycheckall[i]));
				userRole.setStatus((short)1);
				sysUserRoleVoDAO.insert(userRole);
			}
			
		}
//		if(buttonIdUrlList!=null){
//			for (int i = 0; i < buttonIdUrlList.length; i++) {
//				userButton.setUserKy(user.getUserKy());
//				userButton.setBtnId(Integer.parseInt(buttonIdUrlList[i]));
//				sysUserButtonDao.insert(userButton);
//			}
//			
//		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	public void setSysUserVoDAO(SysUsersVoDAO sysUserVoDAO) {
		this.sysUserVoDAO = sysUserVoDAO;
	}
	
	
//	public void setSysUserButtonDao(SysUserButtonDao sysUserButtonDao) {
//		this.sysUserButtonDao = sysUserButtonDao;
//	}
	
	
	public void setSysUserRoleVoDAO(SysUserRoleVoDAO sysUserRoleVoDAO) {
		this.sysUserRoleVoDAO = sysUserRoleVoDAO;
	}
	
	
	@Override
	public SysUserRoleVo getByUserRole(SysUserRoleVo userRoleVo) {
		return sysUserRoleVoDAO.getByUserRole(userRoleVo);
	}

}
