package com.jsjf.dao.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.product.DrProductInfoRepayDetail;

public interface DrProductInfoRepayDetailDAO {

	/**
	 * 批量插入回款记录
	 * @param list
	 */
	public void batchInsert(@Param("list") List<DrProductInfoRepayDetail> list);
	
	/**
	 * 修改还款状态
	 * @param repayinfo
	 * @throws Exception
	 */
	public void updateById(DrProductInfoRepayDetail productRepayinfoDetail)throws Exception;
	/**
	 * 按月付息产品
	 * @param pid
	 * @return
	 */
	public List<DrProductInfoRepayDetail> selectByPid(@Param("pid")Integer pid);
	/**
	 * 查询按月付息产品当前未还款的期数
	 * @param pid
	 * @return
	 */
	public DrProductInfoRepayDetail selectEarliestUnreimbursement(Integer pid);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public DrProductInfoRepayDetail selectById(@Param("pid")Integer id);

}
