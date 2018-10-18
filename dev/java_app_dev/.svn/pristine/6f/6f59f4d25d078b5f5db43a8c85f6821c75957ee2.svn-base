package com.RSA.utils;



import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;


/**
 * 支持 DES对称加密的工具类.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 */
public class CryptoUtils {

	private static final String DES = "DES";
	private static final String DEFAULT_ENCODING = "UTF-8";
	/** 加密算法 */
	private final static String ALGORITHM = "DES/CBC/PKCS5Padding";
	

	/**
	 * 传输的关键数据加密
	 * @param keyData 需加密值
	 * @param timestamp  时间戳
	 * @param dataSecret  密钥
	 * @return String 加密数据
	 */
	public static String encryptKeyData(String keyData, Long timestamp, String dataSecret) {

		DateFormat df = new SimpleDateFormat(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
		df.setTimeZone(TimeZone.getTimeZone(DateTimeUtils.DATE_TIMEZONE));
		keyData = df.format(new Date(timestamp)) + keyData;
		return desEncryptToBase64(keyData, dataSecret.getBytes());
	}
	/**
	 * 传输的关键数据加密
	 * @param keyData 需加密值
	 * @param timestamp  时间戳
	 * @param dataSecret  密钥
	 * @return String 加密数据
	 */
	public static String encryptKeyData(String keyData, String timestamp, String dataSecret) {
		keyData = timestamp + keyData;
		return desEncryptToBase64(keyData, dataSecret.getBytes());
	}
	//-- DES function --//
	/**
	 * 使用DES加密原始字符串, 返回Hex编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToHex(String input, byte[] keyBytes) {
		byte[] encryptResult = null;
		try {
			encryptResult = des(input.getBytes(DEFAULT_ENCODING), keyBytes, Cipher.ENCRYPT_MODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new AppException("E0000008","数据编码不支持");
		}
		return EncodeUtils.hexEncode(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToBase64(String input, byte[] keyBytes) {
		try {
			byte[] encryptResult = des(input.getBytes(DEFAULT_ENCODING), keyBytes, Cipher.ENCRYPT_MODE);
			return Base64.encodeBase64String(encryptResult);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("E0000005","des解密失败！");
		}
	}

	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desDecryptFromHex(String input, byte[] keyBytes) {
		String decryptString = null;
		try {
			byte[] decryptResult = des(EncodeUtils.hexDecode(input), keyBytes, Cipher.DECRYPT_MODE);
			decryptString = new String(decryptResult, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new AppException("E0000005","des解密失败！");
		}
		return decryptString;
	}

	/**
	 * 使用DES解密Base64编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Base64编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desDecryptFromBase64(String input, byte[] keyBytes) {
		try {
			byte[] decryptResult = des(Base64.decodeBase64(input), keyBytes, Cipher.DECRYPT_MODE);
			String decryptString = new String(decryptResult, DEFAULT_ENCODING);
			return decryptString;
		} catch (Exception e) {
			throw new AppException("E0000005","des解密失败！");
		}
	}

	/**
	 * 使用DES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 通用的java .net php
	 * @param inputBytes 原始字节数组
	 * @param keyBytes 符合DES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] des(byte[] inputBytes, byte[] keyBytes, int mode) {
		try {
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// 偏移量
			IvParameterSpec iv = new IvParameterSpec(keyBytes);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(mode, secretKey, iv);
			return cipher.doFinal(inputBytes);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	/**
	 * 生成符合DES要求的密钥, 长度为64位(8字节).
	 */
	public static byte[] generateDesKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	/**
	 * 生成符合DES要求的Hex编码密钥, 长度为16字符.
	 */
	public static String generateDesHexKey() {
		return EncodeUtils.hexEncode(generateDesKey());
	}

	private static IllegalStateException convertRuntimeException(GeneralSecurityException e) {
		return new IllegalStateException("Security exception", e);
	}
	
	/**
	 * 获取关键数据的原值
	 * @param encryptKeyData
	 * @param appKey
	 * @param signV 签名版本号
	 * @param timestamp
	 * @return keyData  原始值为空,返回为空,否则取原始值
	 * @throws ApiException
	 */
	public static String getKeyData(String encryptKeyData, String dataSecret, String timestamp){
		if (encryptKeyData == null) {
			return null;
		}
		String keyData = null;
		
		keyData = CryptoUtils.desDecryptFromBase64(encryptKeyData, dataSecret.getBytes());
		
		
		//返回原始数据,去除固定的 时间戳+aop,共17位
		keyData = keyData.substring(14, keyData.length());

		return keyData;
	}
	
	public static void main(String[] args) {
		String hex = StringUtil.bytesToHexString( "abcd".getBytes()) ;
		System.out.println(hex);
		String hexKey = StringUtil.bytesToHexString( "asdfwef5".getBytes()) ;
		System.out.println(hexKey);
			
		String s = CryptoUtils.desEncryptToHex("abcd", "asdfwef5".getBytes()) ;
		System.out.println(s);
		String m = CryptoUtils.desDecryptFromHex(s, "asdfwef5".getBytes());
		System.out.println(m);
		
	}
}