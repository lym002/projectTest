package com.jsjf.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMemberCrush;

public interface DrMemberCrushDAO {
	/**
	 * 得到充值数据
	 * @param Map
	 * @return List<DrMemberCrush>
	 * @throws SQLException
	 */
    public List<DrMemberCrush> getDrMemberCrushList(Map<String,Object> map); 

    
 	/**
 	 * 得到充值数据总数
 	 * @param Map
 	 * @return Integer
 	 * @throws SQLException
 	 */
     public Integer getDrMemberCrushCounts(Map<String,Object> map); 
     
 	/**
 	 * 根据id修改
 	 * @param DrMemberCrush
 	 * @return void
 	 * @throws SQLException
 	 */
     public void updateMemberCrushById(DrMemberCrush drMemberCrush) throws SQLException; 
     
 	/**
 	 * 根据ID查询
 	 * @param id
 	 * @return DrMemberCrush
 	 * @throws SQLException
 	 */
     public DrMemberCrush getDrMemberCrushById(int id);
     
 	/**
 	 * 充值金额合计
 	 * @param Map<String, Object>
 	 * @return Double
 	 * @throws SQLException
 	 */
     public Double getDrMemberCrushRecordSum(Map<String, Object> map);
     
 	/**
 	 * 添加充值记录
 	 * @param DrMemberCrush
 	 * @return void
 	 * @throws SQLException
 	 */
     public void insertDrMemberCrush(DrMemberCrush drMemberCrush) throws SQLException; 
     
 	/**
 	 * 查询所有状态为未处理的记录
 	 * @return List<DrMemberCrush>
 	 */
     public List<DrMemberCrush> getDrMemberCrush(); 
     /**
      * 根据流水号查询
      * @param payNum
      * @return
      */
     public DrMemberCrush getdrMemberCrushByPaynum(String paynum);
     /**
      * 只 根据流水号查询
      * @param payNum
      * @return
      */
     public DrMemberCrush getFuioudrMemberCrushByPaynum(String paynum);
}