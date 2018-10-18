package com.jsjf.dao.member;

import java.util.List;

import com.jsjf.model.member.DrCarryParam;
import org.apache.ibatis.annotations.Param;

public interface DrCarryParamDAO {
	
	/**
	 * 拿到提现设置信息
	 * @return DrCarryParam
	 */
    public DrCarryParam getDrCarryParam(); 
    
	/**
	 * 根据UID判断是否收取手续费 
	 * @param uid
	 * @param free
     * @return 0-不收手续费 1-收手续费
	 */
    public Integer getDrCarryParamIsCharge(@Param("uid") Integer uid, @Param("freeMonth") Integer free);

	/**
	 * 根据UID判断是否收取手续费
	 * @param uid
	 * @param free
     * @return 0-不收手续费 1-收手续费
	 */
    public Integer getDrCarryParamIsChargeNew(@Param("uid") Integer uid, @Param("freeMonth") Integer free);
	/**
	 * 得到提现设置信息
	 * @return List<DrCarryParam>
	 */
    public List<DrCarryParam> getDrCarryParamList(); 
    
	
}