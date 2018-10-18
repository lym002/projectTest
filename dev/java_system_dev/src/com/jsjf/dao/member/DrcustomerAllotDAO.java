package com.jsjf.dao.member;

import java.util.Map;

import com.jsjf.model.member.DrCustomerAllot;

public interface DrcustomerAllotDAO {
	public void insert(DrCustomerAllot customerAllot);
	
	public void delete(DrCustomerAllot customerAllot);
	
	public void insertHistroy(DrCustomerAllot customerAllot);
	
	public void freezeCustomerByUid(Map<String, Object> map);
	
	public DrCustomerAllot selectCustomerByUid(Map<String, Object> map);
}
