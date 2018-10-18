package com.jytpay.utils;
/**
 * xml报文常量
 * <p> Copyright: Copyright (c) 2014 </p>
 * <p> Create Date: 2014-6-25 </p>
 * @version $Id: XmlMsgConstant.java,v 1.0 $
 */
public class XmlMsgConstant {
	/**
	 * 报文处理成功响应码-成功
	 */
	public static final String MSG_RES_CODE_SUCCESS = "S0000000";
	/**
	 * 报文处理中(未知错误)
	 */
	public static final String MSG_RES_CODE_PROCESSING = "E0000000";
	/**
	 * 记录不存在
	 */
	public static final String MSG_RES_CODE_NOT_EXIST = "E8000001";
	
	
	/**
	 * 记录重复操作
	 */
	public static final String MSG_RES_CODE_OPERATE_REPEAT = "E8000003";
	/**
	 * 系统错误，未明确的错误
	 */
	public static final String MSG_RES_CODE_SYS_ERROR = "E9999999";
	
	/**
	 * 报文格式错误
	 */
	public static final String MSG_RES_CODE_MSG_FORMAT_ERROR = "E0000004";
	
	/**
	 * 报文字段非法
	 */
	public static final String MSG_RES_CODE_MSG_FIELD_ERROR = "E0000003";
}
