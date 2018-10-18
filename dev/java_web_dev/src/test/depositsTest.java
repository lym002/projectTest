package test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsjf.common.Utils;
import com.jsjf.controller.activity.ActivityController;
import com.jsjf.model.member.DrMember;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "../spring.xml", "../spring-mvc.xml",
		"../spring-mybatis.xml" })
public class depositsTest {

	@Autowired
	private RedisClientTemplate redis;
	@Autowired
	DrMemberService drMemberService;
	@Autowired
	DrProductInvestService drProductInvestService;
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired
	ActivityController activityController;

	// @Test
	public void test123() {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer uid = null;
		Integer type = null;
		Integer deadline = null;
		BigDecimal amount = BigDecimal.ZERO;
		byte[] uidMap = null;
		try {
			// uidMap =
			// redisClientTemplate.rpop("regAndVerifySendRedUidList".getBytes());
			// param = (Map<String,Object>)SerializeUtil.unserialize(uidMap);
			// drProductInvestService.toAutoReleaseProduct(param);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		/*
		 * String name="中文"; //URL编码 String nameStr; try { nameStr = new
		 * String(java.net.URLEncoder.encode("","utf-8").getBytes());
		 * System.out.println(nameStr);
		 * 
		 * // String cnStr = "中文"; // String cnStr1 = ""; // // cnStr1 = new
		 * String(java.net.URLEncoder.encode(cnStr, "UTF-8").getBytes(),
		 * "ISO-8859-1"); // System.out.println(cnStr1); // //URL解码 //
		 * System.out.println(java.net.URLDecoder.decode(new
		 * String(cnStr1.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8")); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		// String s = "";

		String investBanTime = "15,15";

		try {
			System.out.println(Utils.nearDawnMinutes(
					Utils.parse("2017-06-22 00:00:00", "YYYY-MM-dd HH:mm:ss"),
					investBanTime.split(",")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 网银充值
	 * 
	 * @param fuiou_acnt
	 * @param amt
	 * @param user_id
	 * @return
	 */
	// @Test
	public void onlineBankingRechargeData() {

	}

	// @Test//充值
	public void testRecharge() {

		Map<String, Object> param = new HashMap<String, Object>();

		DrMember member = drMemberService.selectByPrimaryKey(15);

		String order = Utils.createOrderNo(6, member.getUid(), "");

		param.put("amt", "100000");
		param.put("login_id", member.getMobilephone());
		param.put("mchnt_txn_ssn", order);
		param.put("icd", FuiouConfig.PCQRECHARGE500405);

		String str = FuiouConfig.rechargeFist(param);

		System.out.println("html-json:" + str);
	}

	// @Test//开户
	public void testOPenAccount() {
		Map<String, Object> param = new HashMap<String, Object>();

		DrMember member = drMemberService.selectByPrimaryKey(1000);

		param.put("mobile_no", member.getMobilephone());
		param.put("cust_nm", member.getRealName());
		param.put("certif_id", member.getIdCards());
		param.put("id", member.getUid());

		String str = FuiouConfig.webReg(param);

		System.out.println("html-json:" + str);

	}

	 @Test
	public void testduoble() {
		 BigDecimal bigDecimal = new BigDecimal(12);
		 BigDecimal bigDecimal1 = new BigDecimal("-" + bigDecimal.toString());
		 System.out.println("获取负值"+bigDecimal1);
	 }

}
