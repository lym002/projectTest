package com.jsjf.dao.system;

import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysBanner;

public interface SysBannerDAO {
    
    public List<SysBanner> getIndexBanner(Map<String,Object> map);
}