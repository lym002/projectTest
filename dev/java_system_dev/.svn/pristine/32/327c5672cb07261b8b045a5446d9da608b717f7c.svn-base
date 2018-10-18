package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberBaseInfo;

public interface DrMemberBaseInfoDAO {

	/**
	 * 条件查询baseinfo对象
	 * @param param
	 * 		查询条件
	 * @return
	 * 		baseinfo对象
	 */
	DrMemberBaseInfo selectByParam(Map<String,Object> param);
	
	/**
	 * 通过UID字段值修改baseinfo信息
	 * @param record 要修改的对象
	 */
	void updateByUidSelective(DrMemberBaseInfo record);
	
	/**
	 * 根据id查询用户信息
	 * @param uid
	 * @return DrMemberBaseInfo
	 */
    public DrMemberBaseInfo queryMemberBaseInfoByUid(int uid); 
    
    public List<DrMemberBaseInfo> selectByCard(Map<String,Object> map);
    
    public List<Map<String, Object>> selectPersonRegBatchUpload();

    
}
