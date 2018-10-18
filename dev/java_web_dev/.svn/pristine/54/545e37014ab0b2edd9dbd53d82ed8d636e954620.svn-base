package com.jsjf.service.toutiao;

import com.jsjf.model.toutiao.TTDeviceInfo;

import java.util.Map;


public interface TouTiaoService {
    int deleteByPrimaryKey(Integer id);

    int insert(TTDeviceInfo record);

    int insertSelective(TTDeviceInfo record);

    TTDeviceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTDeviceInfo record);

    int updateByPrimaryKey(TTDeviceInfo record);

    void callback(Map<String, Object> param) throws Exception;

}