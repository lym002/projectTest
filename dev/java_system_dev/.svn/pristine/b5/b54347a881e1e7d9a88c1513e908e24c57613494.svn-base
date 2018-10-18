package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.service.system.impl.RedisClientTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../../spring.xml","../../spring-mvc.xml","../../spring-mybatis.xml"})
@WebAppConfiguration
public class QueryRedisTest {
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Autowired
	protected WebApplicationContext wac;
	 
	private MockMvc mockMvc;
	 
	public void getListMap(){
		/*byte[] bb= redisClientTemplate.lindex("regAndVerifySendRedUidList_bak".getBytes(), 0);
		Map<String, Object> param = new HashMap<String, Object>();
		param = (Map<String, Object>) SerializeUtil.unserialize(bb);*/
		byte[] map = redisClientTemplate.rpop("regAndVerifySendRedUidList_bak".getBytes());
		redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), map);
	}
	 
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();             //整个mvc环境
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() throws Exception { 
		getListMap();
	}

	
	public static void main(String[] args) {
		String code = "";
		String[] beforeShuffle = new String[] {
				"A", "B", "C", "D", "E", "F", "G", "H", "J",  
               "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",  
               "W", "X", "Y", "Z" }; 
		Random rd = new Random();
		for (int i = 0; i < 1000; i++) {
			code = beforeShuffle[rd.nextInt(24)]+Utils.getRandomNumber();
			code = code.substring(0,3)+beforeShuffle[rd.nextInt(24)]+code.substring(3,5);
			System.out.println(i+":         "+code);
		}
	}
}
