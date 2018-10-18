package com.jsjf.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jsjf.model.activity.JsActivityExtendPic;
import com.jsjf.model.activity.JsActivityOffline;

public interface JsActivityOfflineDAO {
	
	/**
	 * 根据name，活动时间查询
	 * @param param
	 * @return
	 */
	public List<JsActivityOffline> getJsActivityOfflineList(Map<String,Object> param);
	
	public int getJsActivityOfflineCount(Map<String,Object> param);
	
	/**
	 * 查询activityExtendPic
	 * @param map
	 * @return
	 */
	public List<JsActivityExtendPic> selectJsActivityExtendPicsList(Map<String,Object> map);
	
	
	/**
	 * 查询JsActivityOffline
	 * @param map
	 * @return
	 */
	public JsActivityOffline selectJsActivityOfflineById(Integer id);
	
	/**
	 * 添加
	 * @param offline
	 */
	public void insertJsActivityExtendPic(JsActivityExtendPic pic);
	/**
	 * 修改
	 * @param offline
	 */
	public void updateJsActivityExtendPic(JsActivityExtendPic pic);
	
	/**
	 * 删除pic
	 */
	public void deleteJsActivityExtendPicByExtendIdAndType(@Param("extendId") Integer extendId,@Param("type") Integer type);
	
	/**
	 * 删除picBy id
	 * @param id
	 */
	public void deleteJsActivityExtendPicById(int id);
	
	
	/**
	 * 添加
	 * @param offline
	 */
	public void insertJsActivityOffline(JsActivityOffline offline);
	/**
	 * 修改
	 * @param offline
	 */
	public void updateJsActivityOffline(JsActivityOffline offline);
	
	
}
