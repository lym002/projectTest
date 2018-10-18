package com.jsjf.dao.system;

import java.util.List;
import java.util.Map;

import com.jsjf.model.system.SysFuiouMessageLog;

public interface SysFuiouMessageLogDAO {
	
	public void insert(SysFuiouMessageLog sysFuiouMessageLog);

    public void updateOvercheck(List<SysFuiouMessageLog>list);
    
    public List<SysFuiouMessageLog> getSysFuiouMessageLogByParam(Map<String, Object>map);
    
    public void batchUpdateJzhMessageResult(List list);
    
    public void batchUpdateInvestJzhMessageResult(List list);

}
