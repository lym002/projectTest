package com.jsjf.service.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.system.SysUsersVo;

public interface SysUsersVoService {
	
	public SysUsersVo getLoginUser(SysUsersVo usersVo)throws SQLException;
	
	/**
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public BaseResult getUserList(PageInfo pi,SysUsersVo user);
	
	public Map<String, Object> insertUser(SysUsersVo user)throws Exception;
	//按主見查詢
    public SysUsersVo selectByPrimaryKey(long key);

    //修改用户修改状态
    public void updateByPrimaryKeySelective(SysUsersVo user)throws Exception;
  	
  	public  List<SysUsersVo> selectSysUsersVoList(Map map);
  	
  	public void changPwd(SysUsersVo user);
  	
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
    public List<Map<String,Object>> selectOperateByMap(Map<String,Object> map);

    
    public List<Map<String, Object>>selectUserCallNum(Map<String, Object> map);
    
    public void deleteUserCallNum(Map<String, Object> map);
    
    public List<Map<String, Object>>selectCallNumByUserKy(Map<String, Object> map);
    
    public void addCallNum(Map<String, Object> map);
}
