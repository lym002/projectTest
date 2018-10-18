package com.jsjf.dao.system;

import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysUsersVo;

public interface SysUsersVoDAO {
	/**
	 * 添加用户
	 * @param record
	 */
	public void insert(SysUsersVo record);
	
	//用户修改密码
	public void changPwd(SysUsersVo user);
	
	public List<SysUsersVo> getUserList(Map<String, Object> map);
	
	public Integer getUserListCount(Map<String, Object> map);
	
	public List<SysUsersVo> selectByLoginUser(SysUsersVo example);

	public int updateByPrimaryKeySelective(SysUsersVo record);

	public  SysUsersVo selectByPrimaryKey(Long userKy);
	
	public  SysUsersVo 	getUserByPhone(String phone);
	
	//查询�?��用户_条件
	public  List<SysUsersVo> selectSysUsersVoList(Map map);
	
	/**
	 * 
	 * @Description: 查询属于部门下的管理者
	 * @param  Map<String,Object> map
	 * @return List<SysUsersVo>  
	 * @throws
	 * @date 2015-1-27 
	 */
	public  List<SysUsersVo> queryDeptManager(Map<String,Object> map);	
	
	/**
	 * 查询角色为电销的用户
	 * @param map
	 * @return
	 */
	public  List<SysUsersVo> queryUserByRole(Map<String,Object> map);	
	 
    /**
     * 后台用户查询自己的座机号与分机号
     */
    public Map<String,Object> selectJsCallNull(long userKy);
    
    /**
     * 修改分机号
     */
    public void updateCallNum(Map<String,Object> map);
    /**
     * 获取操作人
     * @param map
     * @return
     */
    public List<Map<String, Object>> selectOperateByMap(Map<String,Object> map);
    /**
     * 查询电销各个部门在职人数
     * @return
     */
    public Map<String,Object> selectDeptUserCount();
    
    public List<Map<String, Object>>selectUserCallNum(Map<String, Object> map);
    
    public void deleteUserCallNum(Map<String, Object> map);
    
    public List<Map<String, Object>>selectCallNumByUserKy(Map<String, Object> map);
    
    public void addCallNum(Map<String, Object> map);
}