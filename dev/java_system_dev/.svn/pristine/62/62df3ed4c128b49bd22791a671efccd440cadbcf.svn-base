package com.jsjf.service.system.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.system.SysMenuVoDAO;
import com.jsjf.dao.system.SysRoleMenuVoDAO;
import com.jsjf.dao.system.SysRoleVoDAO;
import com.jsjf.model.system.SysMenuVo;
import com.jsjf.model.system.SysRoleMenuVo;
import com.jsjf.model.system.SysRoleVo;
import com.jsjf.service.system.SysRoleVoService;

@Service
@Transactional
public class SysRoleVoServiceImpl implements SysRoleVoService {
	@Autowired
	private SysRoleVoDAO sysRoleVoDAO;
	@Autowired
	private SysMenuVoDAO sysMenuVoDAO;
	@Autowired
	private SysRoleMenuVoDAO sysRoleMenuVoDAO;

	@SuppressWarnings("unchecked")
	@Override
	public BaseResult getRoleList(SysRoleVo role, PageInfo pi){
		
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map map=new HashMap();  
		try {
			map.put("roleName", role.getRoleName());
			map.put("status",role.getStatus());  
			map.put("offset",pi.getPageInfo().getOffset()); 
			map.put("limit",pi.getPageInfo().getLimit()); 
			List<SysRoleVo> list = sysRoleVoDAO.getRoleList(map);
			Integer total = sysRoleVoDAO.queryRoleCounts(role);
			pi.setTotal(total);
			pi.setRows(list);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<SysMenuVo> getMenuTree(int parentId) {
		List<SysMenuVo> list = null;
		try {
			list = sysMenuVoDAO.getMenuTree(parentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<SysRoleVo> queryRole(SysRoleVo roleVo){
		List<SysRoleVo> list = null;
		try {
			list = sysRoleVoDAO.queryRole(roleVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public SysRoleVo queryRoleByKey(Long roleKy){
		SysRoleVo sysRoleVo = null;
		try {
			sysRoleVo = sysRoleVoDAO.queryRoleByKey(roleKy);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sysRoleVo;
	}

	
	@Override
	public void insertRoleMenuList(SysRoleVo roleVo, String[] selectedRights){
		try {
			sysRoleVoDAO.addRole(roleVo);
			List<SysRoleMenuVo> inslist = new ArrayList<SysRoleMenuVo>();
			if (selectedRights != null) {
				for (int i = 0; i < selectedRights.length; i++) {
					SysRoleMenuVo rm = new SysRoleMenuVo();
					rm.setRoleKy((long)roleVo.getId());
					rm.setMenuKy(Long.parseLong(selectedRights[i]));
					rm.setStatus(Short.parseShort("1"));
					inslist.add(rm);
					sysRoleMenuVoDAO.addRoleMenu(rm);
				}
				selectedRights = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BaseResult updateRoleStatus(SysRoleVo roleVo) {
		BaseResult br = new BaseResult();
		try {
			if(1 == roleVo.getStatus()){
				roleVo.setStatus((short) 0);
				sysRoleVoDAO.updateRoleStatus(roleVo);
			}else{
				roleVo.setStatus((short) 1);
				sysRoleVoDAO.updateRoleStatus(roleVo);
			}
			br.setSuccess(true);
		} catch (SQLException e) {
			br.setErrorMsg("修改状态异常！");
			e.printStackTrace();
		}
		return br;
	}
	
	public void updateRoleMenu(SysRoleVo sysRoleVo, String[] selectedRights){
		try {
			sysRoleVoDAO.updateRole(sysRoleVo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<SysRoleMenuVo> inslist = new ArrayList<SysRoleMenuVo>();
		List<SysRoleMenuVo> dellist = new ArrayList<SysRoleMenuVo>();
		if (selectedRights != null) {
			for (int i = 0; i < selectedRights.length; i++) {
				SysRoleMenuVo rm = new SysRoleMenuVo();
				rm.setRoleKy(sysRoleVo.getRoleKy());
				rm.setMenuKy(Long.parseLong(selectedRights[i]));
				rm.setStatus(Short.parseShort("1"));
				inslist.add(rm);
			}
		}
		SysRoleMenuVo rm = new SysRoleMenuVo();
		rm.setRoleKy(sysRoleVo.getRoleKy());
		sysRoleMenuVoDAO.deleteRoleMenu(rm);
		if (inslist.size() > 0) {
			for (SysRoleMenuVo sysRoleMenuVo : inslist) {
				sysRoleMenuVoDAO.addRoleMenu(sysRoleMenuVo);
			}
		}
		inslist.clear();
		dellist.clear();
		selectedRights = null;
	}

	@Override
	public String[] queryRoleMenuByRoleKy(Long roleKy) {
		return sysRoleMenuVoDAO.queryRoleMenuByRoleKy(roleKy);
	}
	
}
