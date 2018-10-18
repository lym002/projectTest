package com.jsjf.dao.system;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsjf.model.system.JsMessagePush;

public interface JsMessagePushDAO {
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public JsMessagePush selectObjectById (int id);
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public List<JsMessagePush> selectObjectList(Map<String,Object> map);
	/**
	 * 查询总条数
	 * @param map
	 * @return
	 */
	public int selectObjectListCount(Map<String,Object> map);
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
	 * 查询符合推送条件的uid
	 * @param map
	 * @return
	 */
	public List<Integer> selectPushMember(Map<String,Object> map);
	
	
}                        
