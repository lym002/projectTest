package com.test;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../../spring.xml","../../spring-mvc.xml","../../spring-mybatis.xml"})
@WebAppConfiguration
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class CaseTest {

	 @Autowired
	 protected WebApplicationContext wac;
	 
	 private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();             //整个mvc环境
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		String result = mockMvc.perform(MockMvcRequestBuilders.post("/register/openAccount.do")        //传入的URL，进入对应的Controller        MockMvcRequestBuilders.get(url)
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
		System.out.println("--------返回的json = " + result);
	}

}
