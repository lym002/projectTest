package com.jsjf.service.member;

import com.jsjf.model.member.JsCompanyAccountLog;

import java.util.List;
import java.util.Map;

public interface JsCompanyAccountLogService {
	/**
	 * 查看TOP20 快如风活动获奖用户列表
	 * @return
	 */
	List<JsCompanyAccountLog> selectBidPrize();
	/**
	 * 快如风活动  我的中奖纪录
	 * @return
	 */
	List<JsCompanyAccountLog> selectBidPrizeByUid(Map<String, Object> map);

    List<JsCompanyAccountLog> selectBidFullByUid(Map<String, Object> map);
}
