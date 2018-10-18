package com.jsjf.dao.member;

import com.jsjf.model.member.DrMemberCredit;

public interface DrMemberCreditDAO {

	/**
	 * 通过UID获取会员征信信息
	 * @param uid
	 * @return
	 */
	DrMemberCredit selectByPrimaryKey(Integer uid);
	
	/**
	 *  插入会员征信信息
	 * @param record
	 * @return
	 */
	int insertSelective(DrMemberCredit record);
	
	/**
     * 通过uid修改会员征信信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(DrMemberCredit record);
	
}
