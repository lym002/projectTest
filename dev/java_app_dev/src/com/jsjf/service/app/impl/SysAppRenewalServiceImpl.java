/**
 * 
 */
package com.jsjf.service.app.impl;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.app.SysAppRenewalDAO;
import com.jsjf.model.app.SysAppRenewal;
import com.jsjf.service.app.SysAppRenewalService;

/**
 * @author Gerald
 *
 */
@Service
public class SysAppRenewalServiceImpl implements SysAppRenewalService {
	@Autowired
	private SysAppRenewalDAO sysAppRenewalDAO;

	/* (non-Javadoc)
	 * @see com.jsjf.service.app.SysAppRenewalService#getAppRenewal(java.util.Map)
	 */
	@Override
	public SysAppRenewal getAppRenewalList(Map<String, Object> map) {
		return sysAppRenewalDAO.getSysAppRenewalList(map);
	}

}
