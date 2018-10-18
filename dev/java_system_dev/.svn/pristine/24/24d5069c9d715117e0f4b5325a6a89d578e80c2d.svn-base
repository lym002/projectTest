package com.test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.jsjf.common.SecurityUtils;
import com.jsjf.controller.task.ActivityReversalTask;
import com.jsjf.controller.task.ActivityTask;
import com.jsjf.controller.task.ProducTask;
import com.jsjf.model.activity.BypCommodityDetailBean;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.activity.ActivityReversalService;
import com.jsjf.service.activity.FestivaiActivityService;
import com.jsjf.service.product.DrProductInfoService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../../spring.xml","../../spring-mvc.xml","../../spring-mybatis.xml"})
@WebAppConfiguration
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class CaseTest {

	 @Autowired
	 protected WebApplicationContext wac;
	 @Autowired
	private DrProductInfoService drProductInfoService;
	 
	 @Autowired
	 private ProducTask pk;
	 
	 @Autowired
	 private ActivityTask at;
	 
	 @Autowired
	 private ActivityReversalTask art;
	 
	 private MockMvc mockMvc;
	 
	 @Autowired
	public ActivityReversalService activityReversalService;
	 
	@Autowired
	private FestivaiActivityService festivaiActivityService;
	
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();             //整个mvc环境
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception { 
		//双十二活动短信测试
		/*List<BypCommodityDetailBean> idList = festivaiActivityService.updateSendPrizeSms();
		festivaiActivityService.sendPrizeSms(idList);*/
		
		
		//双十二奖励统计测试
	/*	Map<String,Object> map = new HashMap<String,Object>();
		map.put("tjStartTime", "2016-12-12");
		map.put("tjEndTime", "2017-12-22");
		map.put("deadline", 30);
		map.put("amount", 10000);
		map.put("tjType", 1);
		festivaiActivityService.prizeStatistics(map);*/
		//计息的定时任务
		//pk.productRaiseEnd();
		
		//回款
		//pk.productRepay();
		//满标短信
		pk.productFullRemind();
		//客户分配
		//at.allotCustomer();
		
		//红包返现
		//art.insertFriendMemberActivityAmount();
		
		//红包定时任务
		/*activityReversalService.reversal();
		
		//落地页：定时查询好友邀请返现总额的排名
		activityReversalService.selectReversal();*/
		/*String result = mockMvc.perform(MockMvcRequestBuilders.post("/register/openAccount.do")        //传入的URL，进入对应的Controller        MockMvcRequestBuilders.get(url)
		.param("uid", "37")                        //URL中携带的参数     key-value
		.param("channel", "3")
		.param("cust_nm", "胡振亚")
		.param("certif_id", "3")
		.param("city_id", "0102")
		.param("parent_bank_id", "16")
		.param("capAcntNo", "62000000000000000000")
		.param("password", "123456"))
		.andDo(MockMvcResultHandlers.print())
		.andReturn().getResponse().getContentAsString();
		//Assert.assertNotNull(result.getModelAndView().getModel().get("user"));
		//Assert.assertEquals(expected, result.getModelAndView().getModel().get("user"));
		System.out.println("--------返回的json = " + result);*/

		//pk.generateAndUploadEsign();

		//计息
		//pk.productRaiseEnd();

		//pk.productRaiseEnd();
	}
	
	public static void main(String[] args) throws InterruptedException {
		//企业开户后的密码
		/*String str = "310102199001014412";
		System.out.println(SecurityUtils.MD5AndSHA256(str.substring(str.length()-6,str.length())));*/
		/* Calendar cal = Calendar.getInstance();
		 String smsList[] = {"13162327996","15800774477","18077447788","13162327996","13055774488"};
		 Set<String> set = new HashSet<String>();
			//String phoneStr[] = new String[smsList.length];
			for (int i = 0; i < smsList.length; i++) {
				if(set.contains(smsList[i])){
					cal.setTime(new Date());
					cal.add(Calendar.MINUTE, 5);
					System.out.println(cal.getTime());
				}else{
					set.add(smsList[i]);
				}
			}*/
		
		
		/*List<SysMessageLog> smsList2 = new ArrayList<SysMessageLog>();
		String smsList[] = {"13162327996","15800774477","18077447788","13162327996","13055774488","13162327996","18077447788","15566774455"};
		for (int i = 0; i < smsList.length; i++) {
			SysMessageLog sm = new SysMessageLog();
			sm.setSendTime(new Date());
			sm.setPhone(smsList[i]);
			smsList2.add(i,sm);
		}
	    Calendar cal = Calendar.getInstance();
	    Set<String> set = new HashSet<String>();
	    Map<String,Integer> uniqueMap = new HashMap<String,Integer>();  
		//for (int i = 0; i < smsList2.size(); i++) {
	    int i = 0;
		for (SysMessageLog temp : smsList2) {//判断该手机号有多少条定时短信
			if(set.contains(smsList2.get(i).getPhone())){//同个手机号多条定时短信
				Integer count = uniqueMap.get(temp.getPhone());  
				uniqueMap.put(temp.getPhone(), (count == null) ? 1 : count + 1);  
				cal.setTime(smsList2.get(i).getSendTime());
				cal.add(Calendar.MINUTE, (uniqueMap.get(temp.getPhone()) * 4));
				smsList2.get(i).setSendTime(cal.getTime());
			}else{
				set.add(smsList2.get(i).getPhone());
			}
			i++;
        }  
			
	//}
		for (int j = 0; j < smsList2.size(); j++) {
			System.out.println(smsList2.get(j).getPhone()+":"+smsList2.get(j).getSendTime());
		}*/
		
	
	}

}
