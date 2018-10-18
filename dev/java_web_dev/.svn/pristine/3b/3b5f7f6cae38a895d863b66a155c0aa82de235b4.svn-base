package com.jsjf.service.activity;

import java.util.Date;

import com.jsjf.common.BaseResult;
import com.jsjf.model.activity.DrActivity;
import com.jsjf.model.member.DrMember;

public interface DrActivityService {

	/**
	 * 通过主键获取活动信息
	 * @param id
	 * @return
	 */
	public DrActivity selectByPrimaryKey(Integer id);
	/**
	 * 通过活动名字查询
	 * @param id
	 * @return
	 */
	public DrActivity selectObjectByName(String name);
	
	
	/**
	 * 叱咤风云榜
	 * @param member
	 * @return
	 */
	public BaseResult allPowerfullTop(DrMember member) throws Exception;
	
	/**
	 * 用户签到
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public BaseResult signIn(int uid,int type,Date now) ;
	
	
}
