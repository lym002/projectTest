package com.jytpay.config;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.xml.sax.InputSource;

import com.jsjf.common.Utils;
import com.jytpay.AppException;
import com.jytpay.CryptoUtils;
import com.jytpay.DESHelper;
import com.jytpay.HttpClient431Util;
import com.jytpay.RSAHelper;
import com.jytpay.utils.DateTimeUtils;
import com.jytpay.utils.StringUtil;
import com.jytpay.vo.JYTResultData;
import com.jytpay.vo.JYTSendData;

public class MockClientMsgBase {
	
	protected static final Logger log = Logger.getLogger(MockClientMsgBase.class);
	//本地
//	public static String pfxPath = "C:\\Users\\lslnx\\Documents\\jyt-key\\merchantTest.pfx";
//	public static String certPath = "C:\\Users\\lslnx\\Documents\\jyt-key\\jytpayserver.cer";//证书存放路径
	
//	public static String pfxPath = "D:\\cert\\merchantTest.pfx";
//	public static String certPath = "D:\\cert\\jytpayserver.cer";//证书存放路径
	//测试环境
	public static String pfxPath = "/usr/local/tomcat/config/jyt-key/merchantTest.pfx";
	public static String certPath = "/usr/local/tomcat/config/jyt-key/jytpayserver.cer";//证书存放路径
	private static String certPasswd = "password";// 证书密码
	   
	public static String MERCHANT_ID = "290067600001";	//测试环境测试商户		
	public static String APP_SERVER_URL = "http://test1.jytpay.com:8080/JytCPService/tranCenter/encXmlReq.do";	//测试环境
	public static String PAY_ACCOUNT = "00120000000010000801";//	测试环境代付账号
	public static String COLLECTION_ACCOUNT = "00120000000010000802";	//测试环境代收账号

	public static String WY_MERCHANT_ID = "290067600001";	//测试环境测试网银商户号
	public static String WY_KEY = "c8cf8975a57963c8fa8bc8dbd50474e2";	//测试网银充值KEY
	public static String WY_QUERY_URL = "http://220.248.70.90:30080/JytNetpay/payment-query.do";//测试网银查询URL
	
	
	//代付代码
	public static String QUERY_BALANCE_CODE = "TC2020";
	//代付代码
	public static String PAY_TRAN_CODE = "TC1002";
	//查询代付结果代码
	public static String QUERY_PAY_TRAN_CODE = "TC2002";
	//代收代码
	public static String COLLECTION_TRAN_CODE = "TC1001";
	//查询代收代码
	public static String QUERY_COLLECTION_TRAN_CODE = "TC2001";
	//币种
	public static String CURRENCY = "CNY";
	//业务类型代码
	public static String PAY_BSN_CODE = "09400";
	//业务类型代码
	public static String COLLECTION_BSN_CODE = "11201";
	public static String RESP_MSG_PARAM_SEPARATOR = "&";
	/**返回报文merchant_id字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_MERCHANT_ID = "merchant_id=";
	
	/**返回报文xml_enc字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_XML_ENC = "xml_enc=";
	/**返回报文xml_enc字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_KEY_ENC = "key_enc=";
	
	/**返回报文sign字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_SIGN = "sign=";
	
	private static class MockClientMsgBaseInstance {
		private static final MockClientMsgBase instance = new MockClientMsgBase();
	}

	public static MockClientMsgBase getInstance() {
		return MockClientMsgBaseInstance.instance;
	}

	private MockClientMsgBase(){
	
	}

	public RSAHelper rsaHelper = new RSAHelper();
	{
		try {
			PrivateKey prikey = rsaHelper.getPrivateKeyFromPfx(pfxPath, certPasswd);// 获取证书私钥
			PublicKey pk = rsaHelper.getPublicKeyFromCer(certPath);
			rsaHelper.initKey(prikey, pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JYTResultData payClientMsg(JYTSendData sendData) throws Exception{
		String xml = getSc0001Xml(sendData);
		String mac = signMsg(xml);
        String respXml = sendMsg(xml, mac);
        JYTResultData paymentDataBean = payResultMsg(respXml);
        return paymentDataBean;
	}
	
	//结果通知信息
	public JYTResultData resultNoticeMsg(String merchantId,String respXmlEnc,String respKeyEnc,String respSign) throws Exception{
		byte respKey[] = decryptKey(respKeyEnc) ;
		String respXml = decrytXml( respXmlEnc,respKey ) ;
		log.info("返回报文XML:"+respXml);
		JYTResultData resultData = getQueryMsg(respXml);
        return resultData;
	}
	
	//代收付通知信息
	public JYTResultData payResultMsg(String respMsg) throws Exception{
		JYTResultData resultData = new JYTResultData();
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/head");
		
		Node resp_code = packageHead.selectSingleNode("resp_code");
		Node resp_desc = packageHead.selectSingleNode("resp_desc");
		
		//解析报文头
		Node packageBody = doc.selectSingleNode("/message/body");
		if(Utils.isObjectNotEmpty(packageBody)){
			Node tran_resp_code = packageBody.selectSingleNode("tran_resp_code");
			Node tran_resp_desc = packageBody.selectSingleNode("tran_resp_desc");
			Node tran_state = packageBody.selectSingleNode("tran_state");
			Node remark = packageBody.selectSingleNode("remark");
			Node balance = packageBody.selectSingleNode("balance");
			resultData.setTran_resp_code(tran_resp_code == null ? "" : tran_resp_code.getText());
			resultData.setTran_resp_desc(tran_resp_desc == null ? "" : tran_resp_desc.getText());
			resultData.setTran_state(tran_state == null ? "" : tran_state.getText());
			resultData.setRemark(remark == null ? "" : remark.getText());
			resultData.setBalance(balance == null ? new BigDecimal(0) : new BigDecimal(balance.getText()));
		}
		resultData.setResp_code(resp_code == null ? "" : resp_code.getText());
		resultData.setResp_desc(resp_desc == null ? "" : resp_desc.getText());
        return resultData;
	}
	
	public JYTResultData getQueryMsg(String respMsg) throws Exception{
		JYTResultData resultData = new JYTResultData();
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
		
		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/body");
		if(packageHead==null)
			return null;
		
		Node ori_tran_flowid = packageHead.selectSingleNode("ori_tran_flowid");
		Node account_no = packageHead.selectSingleNode("account_no");
		Node account_name = packageHead.selectSingleNode("account_name");
		Node tran_amt = packageHead.selectSingleNode("tran_amt");
		Node tran_resp_code = packageHead.selectSingleNode("tran_resp_code");
		Node tran_resp_desc = packageHead.selectSingleNode("tran_resp_desc");
		Node tran_state = packageHead.selectSingleNode("tran_state");
		Node remark = packageHead.selectSingleNode("remark");
		
		resultData.setAccount_name(account_name.getText());
		resultData.setAccount_no(account_no.getText());
		resultData.setOri_tran_flowid(ori_tran_flowid.getText());
		resultData.setRemark(remark == null ? "" : remark.getText());
		resultData.setTran_amt(new BigDecimal(tran_amt.getText()));
		resultData.setTran_resp_code(tran_resp_code.getText());
		resultData.setTran_resp_desc(tran_resp_desc.getText());
		resultData.setTran_state(tran_state.getText());
		
		return resultData;
	}
	
	/**
	 * 获得报文头字符串
	 * <p> Create Date: 2014-5-10 </p>
	 * @param tranCode
	 * @return
	 */
	public String getMsgHeadXml(String tranCode,String tranFlow){
		StringBuffer headXml = new StringBuffer();
		headXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><message><head><version>1.0.0</version>");
		headXml.append("<tran_type>01</tran_type>")
			   .append("<merchant_id>").append(MERCHANT_ID).append("</merchant_id>");
		headXml.append("<tran_date>").append(DateTimeUtils.getNowDateStr(DateTimeUtils.DATE_FORMAT_YYYYMMDD))
		       .append("</tran_date>");
		headXml.append("<tran_time>").append(DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_HHMMSS))
		       .append("</tran_time><tran_flowid>").append(tranFlow)
		       .append("</tran_flowid><tran_code>").append(tranCode).append("</tran_code>");
		headXml.append("</head>");
		
		return headXml.toString();
	}
	
	/**
	 * 获得SC0001的上送报文字符串
	 * <p> Create Date: 2014-5-10 </p>
	 * @return
	 */
	public String getSc0001Xml(JYTSendData sendData){
		
		StringBuffer xml = new StringBuffer();
		if(Utils.strIsNull(sendData.getOri_tran_flowid())){
			if(Utils.strIsNull(sendData.getMer_viral_acct())){
				xml.append(getMsgHeadXml(sendData.getTran_code(),sendData.getTran_flowid()));
				xml.append("<body>");
				xml.append("<bank_name>").append(sendData.getBank_name()).append("</bank_name><account_no>").append(sendData.getAccount_no()).append("</account_no>");
				xml.append("<account_name>").append(sendData.getAccount_name()).append("</account_name><account_type>").append(sendData.getAccount_type()).append("</account_type>");
				xml.append("<tran_amt>").append(sendData.getTran_amt()).append("</tran_amt>");
				xml.append("<currency>").append(sendData.getCurrency()).append("</currency>");
				xml.append("<bsn_code>").append(sendData.getBsn_code()).append("</bsn_code>");
				xml.append("<mobile>").append(sendData.getMobile()).append("</mobile>");
				xml.append("<remark>").append(sendData.getRemark()).append("</remark>");
			}else{
				xml.append(getMsgHeadXml(sendData.getTran_code(),createOrderNo(18)));
				xml.append("<body>");
				xml.append("<mer_viral_acct>").append(sendData.getMer_viral_acct()).append("</mer_viral_acct>");
			}
		}else{
			xml.append(getMsgHeadXml(sendData.getTran_code(),createOrderNo(18)));
			xml.append("<body>");
			xml.append("<ori_tran_flowid>").append(sendData.getOri_tran_flowid()).append("</ori_tran_flowid>");
		}
		xml.append("</body></message>");
		return xml.toString();
	}
	
	public String sendMsg(String xml,String sign) throws Exception{
		log.info("上送报文："+xml);
//		log.info("上送签名："+sign);
		
		byte[] des_key = DESHelper.generateDesKey() ;
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("merchant_id", MERCHANT_ID);
		paramMap.put("xml_enc", encryptXml(xml,des_key));
		paramMap.put("key_enc", encryptKey(des_key));
		paramMap.put("sign", sign);
	
		// 获取执行结果

		String res = HttpClient431Util.doPost(paramMap, APP_SERVER_URL);
		
		if(res  == null){
			log.error("服务器连接失败");
			
			throw new AppException("测试异常");
		}
//		else{
//			log.info("连接服务器成功,返回结果"+res);
//		}
		String []respMsg = res.split(RESP_MSG_PARAM_SEPARATOR);
		
		String merchantId = respMsg[0].substring(RESP_MSG_PARAM_PREFIX_MERCHANT_ID.length());
		String respXmlEnc = respMsg[1].substring(RESP_MSG_PARAM_PREFIX_XML_ENC.length());
		String respKeyEnc = respMsg[2].substring(RESP_MSG_PARAM_PREFIX_KEY_ENC.length());
		String respSign = respMsg[3].substring(RESP_MSG_PARAM_PREFIX_SIGN.length());
		
		byte respKey[] = decryptKey(respKeyEnc) ;
		
		String respXml = decrytXml( respXmlEnc,respKey ) ;
		
		
//		log.info("返回报文merchantId:"+merchantId);
		log.info("返回报文XML:"+respXml);
//		log.info("返回报文签名:"+respSign);
		
		Assert.assertTrue("返回报文校验失败",verifyMsgSign(respXml,respSign));
		
		return respXml;
	}
	
	public String getMsgRespCode(String respMsg) throws Exception{
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
		
		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/head");
		
		Node respCodeNode = packageHead.selectSingleNode("resp_code");
		
		return respCodeNode.getText();
	}
	
	public String getMsgState(String respMsg) throws Exception{
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
		
		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/body");
		if(packageHead==null)
			return null;
		
		Node tranStateNode = packageHead.selectSingleNode("tran_state");
		if(tranStateNode==null)
			return null;
		
		return tranStateNode.getText();
	}
	
	private String encryptKey( byte[] key){
		
		byte[] enc_key = null ;
		try {
			enc_key = rsaHelper.encryptRSA(key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return StringUtil.bytesToHexString(enc_key) ;
	}
	
	private byte[] decryptKey(String hexkey){
		byte[] key = null ;
		byte[] enc_key = StringUtil.hexStringToBytes(hexkey) ;
		
		try {
			key = rsaHelper.decryptRSA(enc_key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return key ;
	}
	
	private String encryptXml( String xml, byte[] key){
		
		String enc_xml = CryptoUtils.desEncryptToHex(xml, key) ;
		
		return enc_xml;
	}
	
	public String decrytXml(String xml_enc, byte[] key) {
		String xml = CryptoUtils.desDecryptFromHex(xml_enc, key) ;
		return xml;
	}

	public boolean verifyMsgSign(String xml, String sign)
	{
		byte[] bsign = StringUtil.hexStringToBytes(sign) ;
		
		boolean ret = false ;
		try {
			ret = rsaHelper.verifyRSA(xml.getBytes("UTF-8"), bsign, false, "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public String signMsg( String xml ){
		String hexSign = null ;
		
		try {
			byte[] sign = rsaHelper.signRSA(xml.getBytes("UTF-8"), false, "UTF-8") ;
			
			hexSign = StringUtil.bytesToHexString(sign) ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hexSign;
	}
	
	public boolean verifySign(byte[] plainBytes, byte[] signBytes){
		boolean flag = false;
		try {
			flag = rsaHelper.verifyRSA(plainBytes, signBytes, false, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
    /**
     * 创建交易流水号
     * @param req
     * @return
     */
	private String createOrderNo(int length) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buffer = new StringBuffer();
		buffer.append(sdf.format(date));
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			int tempvalue = rand.nextInt(10);
			buffer.append(tempvalue);
		}
		return buffer.toString();
	}
	
}
