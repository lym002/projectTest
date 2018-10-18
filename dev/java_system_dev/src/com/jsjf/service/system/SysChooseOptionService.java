package com.jsjf.service.system;

import java.util.List;

import com.jsjf.model.system.SysChooseOption;

public interface SysChooseOptionService {
	
	/**
	 * 查询数据字典
	 * @param category 类型
	 * @return List<SysChooseOption>
	 */
	List<SysChooseOption> selectByCategory(String category);
	
    List<SysChooseOption> select();

    /**
     * 查询数据字典
     */
    List<SysChooseOption> queryByCategory(String category);
	
}
