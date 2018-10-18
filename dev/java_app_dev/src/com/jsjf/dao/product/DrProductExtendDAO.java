package com.jsjf.dao.product;

import java.util.List;

import com.jsjf.model.product.DrProductExtend;

public interface DrProductExtendDAO {
	
	/**
	 * 根据PID拿产品扩展信息
	 * @param  pid 产品ID
	 * @return List<DrProductExtend>
	 */
	public List<DrProductExtend> getDrProductExtendByPid(int pid);
	
}