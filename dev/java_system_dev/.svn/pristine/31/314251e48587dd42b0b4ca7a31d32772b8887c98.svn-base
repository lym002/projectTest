package com.jsjf.service.system.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.system.SysRoleVoDAO;
import com.jsjf.dao.system.SysUserRoleVoDAO;
import com.jsjf.dao.system.SysUsersVoDAO;
import com.jsjf.model.system.SysRoleVo;
import com.jsjf.model.system.SysUserRoleVo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.system.SysUsersVoService;

/**
 * 用户管理
 * @author Administrator
 *
 */
@Service
@Transactional
public class SysUsersVoServiceImpl implements SysUsersVoService {
	@Autowired
	SysUsersVoDAO sysUsersVoDAO;
	
	@Autowired
	SysRoleVoDAO sysRoleVoDAO;
	
	@Autowired
	SysUserRoleVoDAO sysUserRoleVoDAO;
	
	/**
	 * 创建新用户
	 */
	@Override
	public Map<String, Object> insertUser(SysUsersVo user)throws Exception {
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loginId", user.getLoginId());
		List<SysUsersVo> userDataList = sysUsersVoDAO.selectSysUsersVoList(map);
		map.clear();
		if(userDataList != null && userDataList.size() > 0){
			map.put("message", "moreLoginId");
		}else{
			sysUsersVoDAO.insert(user);
			SysUserRoleVo roleVo = new SysUserRoleVo();
			roleVo.setRoleKy(user.getRoleKy().longValue());
			roleVo.setUserKy(user.getUserKy());
			roleVo.setStatus((short)1);
			sysUserRoleVoDAO.insert(roleVo);
			map.put("message", "ok");
		}
		return map;
	}
	
	/**
	 * 后台登录验证
	 */
	@Override
	public SysUsersVo getLoginUser(SysUsersVo usersVo) throws SQLException {
		List<SysUsersVo> list = sysUsersVoDAO.selectByLoginUser(usersVo);
		if(list.size()>0){
			SysUsersVo u=(SysUsersVo) list.get(0);
			return u;
			
		}
		return null;
	}

	/**
	 * 分页显示所有用户
	 */
	public BaseResult getUserList(PageInfo pi,SysUsersVo user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		map.put("loginId", user.getLoginId());
		map.put("name", user.getName());
		map.put("status", user.getStatus());
		map.put("deptId", user.getDeptId());
		map.put("roleKy", user.getRoleKy());
		List<SysUsersVo> list = sysUsersVoDAO.getUserList(map);
		Integer total = sysUsersVoDAO.getUserListCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public SysUsersVo selectByPrimaryKey(long key) {
		return sysUsersVoDAO.selectByPrimaryKey(key);
	}

	/**
	 * 
	 * @Description 用户部分字段更新
	 * @author 何修水
	 */
    public void updateByPrimaryKeySelective(SysUsersVo user)throws Exception {
		sysUsersVoDAO.updateByPrimaryKeySelective(user);
		if(user.getRoleKy()!=null){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("roleKy", user.getRoleKy());
			map.put("userKy", user.getUserKy());
			sysUserRoleVoDAO.updateByUserKy(map);
		}
		if(user.getStatus()!=null && user.getStatus()==0 && user.getRoleKy()!=null){//禁用
			SysRoleVo sysRoleVo= sysRoleVoDAO.queryRoleByKey(Long.parseLong(user.getRoleKy().toString()));
			if(sysRoleVo.getRoleCode().equals("10") || sysRoleVo.getRoleCode().equals("kefu")){//角色为电销或者客服禁用状态删除分机号坐席号
				Map<String, Object> map=new HashMap<>();
				map.put("userKy", user.getUserKy());
				sysUsersVoDAO.deleteUserCallNum(map);
			}
			
		}
	}
	

	public  List<SysUsersVo> selectSysUsersVoList(Map map){
		return sysUsersVoDAO.selectSysUsersVoList(map);
	}

	@Override
	public void changPwd(SysUsersVo user) {
		sysUsersVoDAO.changPwd(user);
	}
	
	@Override
	public  List<SysUsersVo> queryUserByRole(Map<String,Object> map){
		return sysUsersVoDAO.queryUserByRole(map);
	}

	@Override
	public Map<String, Object> selectJsCallNull(long userKy) {
		return sysUsersVoDAO.selectJsCallNull(userKy);
	}

	@Override
	public void updateCallNum(Map<String, Object> map) {
		sysUsersVoDAO.updateCallNum(map);		
	}

	@Override
	public List<Map<String, Object>> selectOperateByMap(Map<String, Object> map) {
		
		return sysUsersVoDAO.selectOperateByMap(map);
	}

	@Override
	public List<Map<String, Object>> selectUserCallNum(Map<String, Object> map) {
		return sysUsersVoDAO.selectUserCallNum(map);
	}

	@Override
	public void deleteUserCallNum(Map<String, Object> map) {
		sysUsersVoDAO.deleteUserCallNum(map);
	}

	@Override
	public List<Map<String, Object>> selectCallNumByUserKy(Map<String, Object> map) {
		return sysUsersVoDAO.selectCallNumByUserKy(map);
	}

	@Override
	public void addCallNum(Map<String, Object> map) {
		sysUsersVoDAO.addCallNum(map);		
	};	

}
