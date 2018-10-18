package test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.controller.activity.ActivityController;
import com.jzh.FuiouConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../spring.xml","../spring-mvc.xml","../spring-mybatis.xml"})
public class withdrawalsTest {
	
	public final static String mchnt_user = "0003310F0352406";
	public final static String mchnt_m_user = "2062060000216700";
	
	
	@Autowired
	private ActivityController activityController;
	
	
	
	public static void main(String[] args) {

		  String name="中文";
		  //URL编码
		  String nameStr;
		try {
			nameStr = new String(java.net.URLEncoder.encode("","utf-8").getBytes());
			System.out.println(nameStr);
			
//			String cnStr = "中文";
//			String cnStr1 = "";
//			
//			cnStr1 = new String(java.net.URLEncoder.encode(cnStr, "UTF-8").getBytes(), "ISO-8859-1");
//			System.out.println(cnStr1);
//			//URL解码
//			System.out.println(java.net.URLDecoder.decode(new String(cnStr1.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8"));
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test//提现
	public void withdrawals(){		
		String str = FuiouConfig.withdrawals("15601820519",""+System.currentTimeMillis(),"100");
		System.out.println("html-json:"+str);
	}
	
	
	
//	@Test//元宵活动抽奖测试
//	public void eatGlutinousTest(){		
//		String string = activityController.againDoubleegg(1099);
//		System.out.println(string);
//	}

}
