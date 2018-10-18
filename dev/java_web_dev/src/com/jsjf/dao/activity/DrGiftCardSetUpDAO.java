package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import com.jsjf.model.activity.DrGiftCardSetUp;

public interface DrGiftCardSetUpDAO {
	/**
	 * 获取有效
	 * @return
	 */
	public List<DrGiftCardSetUp> getEfficaciousDrGiftCardSetUpList(Map<String,Object> map);
	 

}
