package com.jsjf.dao.system;

import java.util.List;

import com.jsjf.model.system.SysChooseOption;

public interface SysChooseOptionDAO {
	
	/**
	 * 查询数据字典
	 * @param category 类型
	 * @return List<SysChooseOption>
	 */
    List<SysChooseOption> selectByCategory(String category);
    
    List<SysChooseOption> select();
    
}