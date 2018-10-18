package com.jsjf.dao.member;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.member.DrMemberCrush;

public interface DrMemberCrushDAO {
    
	/**
	 * 添加充值记录
	 * @param DrMemberCrush
	 * @return void
	 * @throws SQLException
	 */
    public void insertDrMemberCrush(DrMemberCrush drMemberCrush) throws SQLException; 
    
	/**
	 * 根据商户唯一订单号查询
	 * @param payNum
	 * @return DrMemberCrush
	 */
    public DrMemberCrush getDrMemberCrushByPayNum(String payNum); 
    /**
     * 只 只根据商户唯一订单号查询
     * @param payNum
     * @return DrMemberCrush
     */
    public DrMemberCrush getFuiouDrMemberCrushByPayNum(String payNum); 
    
	/**
	 * 根据商户唯一订单号查状态
	 * @param payNum
	 * @return DrMemberCrush
	 */
    public DrMemberCrush getDrMemberCrushByStatus(String payNum); 
    
	/**
	 * 根据商户订单号修改
	 * @param DrMemberCrush
	 * @return void
	 * @throws SQLException
	 */
    public void updateMemberCrushById(DrMemberCrush drMemberCrush) throws SQLException; 
    
    /**
     * 根据融宝订单号查询订单
     * @param payNum
     * @return
     */
    public DrMemberCrush getDrMemberCrushByNo(String orderNo); 
    
    /**
     * 查询充值总额
     * @param uid
     * @return
     */
    public BigDecimal getDrMemberCrushAmountByUID(@Param(value="uid")Integer uid,@Param(value="type")Integer type);
    /**
     * 根据流水号查询
     * @param payNum
     * @return
     */
    public DrMemberCrush getFuiouDrMemberCrushByPaynum(String paynum);

    List<DrMemberCrush> selectCrushByUid(Map<String, Object> properties2);
}