package com.jsjf.service.member;

import java.util.Map;

import com.jsjf.model.member.DrCustomerAllot;

public interface DrcustomerAllotService{

	public void insert(DrCustomerAllot customerAllot);
	
	public void delete(DrCustomerAllot customerAllot);
	
	public void insertHistroy(DrCustomerAllot customerAllot);
	
	public String freezeCustomerByUid(Map [] list,Integer userKy);
	
	public void freezeCustomer(Map<String, Object> map);
	
	public DrCustomerAllot selectCustomerByUid(Map<String, Object> map);

}
