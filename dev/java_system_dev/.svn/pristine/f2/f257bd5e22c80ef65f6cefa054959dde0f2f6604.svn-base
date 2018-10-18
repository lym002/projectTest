package com.jsjf.service.member.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.Utils;
import com.jsjf.dao.member.DrMemberDAO;
import com.jsjf.dao.member.DrcustomerAllotDAO;
import com.jsjf.model.member.DrCustomerAllot;
import com.jsjf.service.member.DrcustomerAllotService;

@Service
@Transactional
public class DrCustomerAllotServiceImpl implements  DrcustomerAllotService{

	@Autowired
	DrcustomerAllotDAO drcustomerAllotDAO;
	@Autowired
	DrMemberDAO drMemberDAO;
	
	@Override
	public void insert(DrCustomerAllot customerAllot) {
		drcustomerAllotDAO.insert(customerAllot);
	}
	@Override
	public void delete(DrCustomerAllot customerAllot) {
		drcustomerAllotDAO.delete(customerAllot);
		
	}
	@Override
	public void insertHistroy(DrCustomerAllot customerAllot) {
		drcustomerAllotDAO.insertHistroy(customerAllot);
	}
	@Override
	public String freezeCustomerByUid(Map [] list,Integer userKy) {
		 List<Integer> uids = new ArrayList<Integer>();
		 for (Map m : list) {
			 if(!m.get("uid").equals("0")){
				 uids.add(Integer.parseInt(m.get("uid").toString()));
			 }else{
				 return "0";                                                         
			 }
			 
			 if(m.get("id")!=null){
					DrCustomerAllot drCustomerAllot=new DrCustomerAllot();
					drCustomerAllot.setId(Integer.parseInt(m.get("id").toString()));
					drCustomerAllot.setCreateUserKy(userKy);
					drCustomerAllot.setUid(Integer.parseInt(m.get("uid").toString()));
					drcustomerAllotDAO.insertHistroy(drCustomerAllot);
					drcustomerAllotDAO.delete(drCustomerAllot);
			}
		 }
		 if(!Utils.isEmptyList(uids)){
			 Map<String,Object> map = new HashMap<String, Object>();
			 map.put("uids", uids);	 
			 map.put("allot", 100);//冻结
			 drcustomerAllotDAO.freezeCustomerByUid(map);	
			 drMemberDAO.updateMemberAllot(map);
		 }
		return "success";	 	
	}
	
	@Override
	public void freezeCustomer(Map<String, Object> param) {
		 if(param.get("uids")!=null){
			 Map<String,Object> map = new HashMap<String, Object>();
			 drcustomerAllotDAO.freezeCustomerByUid(param);	
		 }
	}
	@Override
	public DrCustomerAllot selectCustomerByUid(Map<String, Object> map) {
		return drcustomerAllotDAO.selectCustomerByUid(map);
	}

}
