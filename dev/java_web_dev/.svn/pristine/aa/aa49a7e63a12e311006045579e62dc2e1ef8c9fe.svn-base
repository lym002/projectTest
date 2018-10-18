package com.jsjf.common;

import java.util.Map;

/**
 * Created on 2015年1月16日
 * <p>Title:       币优铺理财后台管理系统_[子系统统名]_[模块名]/p>
 * <p>Description: [控制层调用DAO层消息返回基类]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     币优铺理财</p>
 * <p>Department:  信息管理部</p>
 */
public class BaseResult {

	protected boolean success = false;
	protected String errorCode;
	protected String errorMsg;
	protected Map<?, ?> map;
	protected String msg;
	protected boolean flag;

	public Map<?, ?> getMap() {
		return map;
	}
	public void setMap(Map<?, ?> map) {
		this.map = map;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "BaseResult [success=" + success + ", errorCode=" + errorCode
				+ ", errorMsg=" + errorMsg + ", msg=" + msg + ", flag=" + flag
				+ "]";
	}
}