package com.jsjf.dao.cpa;

import java.util.List;
import java.util.Map;

import com.jsjf.model.cpa.DrChannelInfo;

public interface DrChannelInfoDAO {
	/**
	 * 根据Map得到渠道信息
	 * 
	 * @param Map
	 * @return List<DrChannelInfo>
	 */
	public List<DrChannelInfo> getDrChannelInfoListForMap(
			Map<String, Object> map);
}
