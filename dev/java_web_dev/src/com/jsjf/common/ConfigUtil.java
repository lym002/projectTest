package com.jsjf.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {
	public static final String FRONT_LOGIN_USER = "frontLoginUser";// 前台登陆用户

	// 生产环境
	private static final String domainName = "http://192.168.1.13";
	private static final String IMG_FILE_URL = "/opt/www";

	public static final String ADMIN_LOGIN_USER = "adminLoginUser";// 后台登陆用户

	// 加载数据字典
	public static Map<String, Map<Integer, String>> dictionaryMap = new HashMap<String, Map<Integer, String>>();

	// 文章存放路劲
	public static final String articleImgPath = "/upload/atricle/";
	// 广告存放路劲
	public static final String bannerImgPath = "/upload/banner/";
	// 风控图片
	public static final String claimsPic = "/upload/claimsPic/";
	public static final String productPic = "/upload/productPic/";

	public static final String investSendPic = "/upload/investSendPic/";

	public static final String synPayProductUrl = "http://jsapi.iok.la:12004/pay-web/product/synProductInfo.do";
	public static final String synRefundUrl = "http://jsapi.iok.la:12004/pay-web/product/paymentConfirm.do";

	// e签宝的初始值
	public static final String encoding = "UTF-8";
	public static final String algorithm = "HmacSHA256";
	// 项目编号
	public static final String projectId = "1111563517";
	// 项目秘钥
	public static final String projectSecret = "95439b0863c241c63a861b87d1e647b7";
	// 文档保全测试环境地址
	public static final String eviUrl = "http://smlcunzheng.tsign.cn:8083/evi-service/evidence/v1/preservation/original/url";
	// 待保全文档 默认路径在项目下的files文件夹下
	public static final String filePath = "D://files" + File.separator
			+ "test.pdf";
	// e签宝测试环境查看地址：
	public static final String esignUrl = "https://smlcunzheng.tsign.cn/evi-web/static/certificate-info.html";
	// 兑换积分锁关键字
	public static final String REDIS_KEY_CONVERT = "Convert.";
    //关于redis 的锁
    public static final String AboutTheCash ="rechargFuioulock_";
	public static final String RedEnvelope ="RedEnvelope_";
    public static final String ABOUT_ACTIVITY="activity_";
	public static final String PWD_ERROR="Pwd.Error.Mobilephone.";
    public static final String IntegralJDCard="integralJDCard_";

	public static String getRedisKeyConvert() {
		return REDIS_KEY_CONVERT;
	}
	public static final String ToHelpFarmers="ToHelpFarmers_";

	/**
	 * 上传并返回上传路径
	 */
	public static String getFilePath(MultipartFile file, String realPath) {
		String imageName = null;
		try {
			SFtpUtil sftp = new SFtpUtil();
			imageName = ImageUtils.getServerFileName()
					+ file.getOriginalFilename().substring(
							file.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(file.getInputStream(), realPath, imageName);
			sftp.closeServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageName;
	}
    public static String getIntegralJDCard() {
        return IntegralJDCard;
    }

	public static String getSynpayproducturl() {
		return synPayProductUrl;
	}

	public static String getSynrefundurl() {
		return synRefundUrl;
	}

	public static String getImgFileUrl() {
		return IMG_FILE_URL;
	}

	public static String getDomainname() {
		return domainName;
	}

	public static String getEsignUrl() {
		return esignUrl;
	}

	public static String getProjectId() {
		return projectId;
	}

	public static String getProjectSecret() {
		return projectSecret;
	}

	public static String getEncoding() {
		return encoding;
	}

	public static String getAlgorithm() {
		return algorithm;
	}

	public static String getToHelpFarmers() {
		return ToHelpFarmers;
	}
}