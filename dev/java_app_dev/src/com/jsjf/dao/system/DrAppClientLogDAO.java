package com.jsjf.dao.system;

import java.util.Map;

import com.jsjf.model.system.DrAppClientLog;

public interface DrAppClientLogDAO {
	
	public void insert(DrAppClientLog log);
	
	public int getTotal(Map<String,String> map);
	
}
