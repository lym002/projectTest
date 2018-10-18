package com.jsjf.dao.member;

import java.util.List;
import java.util.Map;

import com.jsjf.model.member.DrMember;

public interface DrWinCallLogDAO {
    
	public List<Map<String, Object>> selWincallLog(Map<String, Object> param);
    
    public int selWincallLogCount(Map<String, Object> param);
    
    public void insert(Map<String, Object> param);
    
    public void update(Map<String, Object> param);
    
    
}
