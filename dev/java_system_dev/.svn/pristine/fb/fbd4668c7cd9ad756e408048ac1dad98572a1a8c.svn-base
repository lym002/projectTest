package com.jsjf.service.member;

import java.sql.SQLException;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrCarryParam;
import com.jsjf.model.system.SysUsersVo;

public interface DrCarryParamService {

	/**
	 * 拿到提现设置信息列表数据
	 * @param DrCarryParam
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrCarryParamList(PageInfo pi);
	
	/**
	 * 根据id得到提现设置信息
	 * @param id
	 * @return DrCarryParam
	 */
    public DrCarryParam getDrCarryParamByid(Integer id); 
    
 	/**
 	 * 修改提现设置信息
 	 * @param  id
 	 * @param  SysUsersVo
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public void updateDrCarryParam(DrCarryParam drCarryParam,SysUsersVo usersVo) throws SQLException;

 	/**
 	 * 获取第一条提现设置
 	 * @return
 	 */
 	public DrCarryParam getDrCarryParam(); 

}
