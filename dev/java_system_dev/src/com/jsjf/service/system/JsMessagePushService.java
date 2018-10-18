package com.jsjf.service.system;

import java.util.List;
import java.util.Map;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.system.JsMessagePush;

public interface JsMessagePushService {
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public JsMessagePush selectObjectById (int id);
	/**
	 * 添加
	 * @param jsMessagePush
	 */
	public void insert(JsMessagePush jsMessagePush);
	/**
	 * 修改
	 * @param map
	 */
	public void update(JsMessagePush jsMessagePush);
	/**
	 * 修改
	 * @param map
	 */
	public void updateByMap(Map<String,Object> map);
	/**
	 * 查询list
	 * @param info
	 * @param jsMessagePush
	 * @return
	 */
	public PageInfo selectParamList(PageInfo info,JsMessagePush jsMessagePush);
	
	/**
	 * 执行推送
	 * @param obj
	 * @return
	 */
	public BaseResult executePush(JsMessagePush obj);
}
