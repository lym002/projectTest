package com.jsjf.dao.toutiao;

import com.jsjf.model.toutiao.TTDeviceInfo;
import java.util.Map;


public interface TouTiaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TTDeviceInfo record);

    int insertSelective(TTDeviceInfo record);

    TTDeviceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTDeviceInfo record);

    int updateByPrimaryKey(TTDeviceInfo record);

    TTDeviceInfo getUserDeviceInfoByParams(Map<String, Object> param);
}