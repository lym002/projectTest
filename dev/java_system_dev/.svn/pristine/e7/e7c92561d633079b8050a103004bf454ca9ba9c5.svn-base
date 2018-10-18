package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.JsActivityExtendPic;
import com.jsjf.model.activity.JsActivityOffline;

public interface JsActivityOfflineService {
	
	public BaseResult getJsActivityOfflineList(JsActivityOffline jsActivityOffline,PageInfo pi);
	
	/**
	 * 查询activityExtendPic
	 * @param map
	 * @return
	 */
	public List<JsActivityExtendPic>  selectJsacExtendPicsList(int extend,int type);
	
	/**
	 * 查询JsActivityOffline
	 * @param map
	 * @return
	 */
	public JsActivityOffline selectJsActivityOfflineById(int id);
	
	/**
	 * 删除 扩展图
	 * @param id
	 */
	public void deleteJsacExtendPic(int id);
	
	/**
	 * 修改线下活动状态
	 */
	public void update(JsActivityOffline offline);
	
	/**
	 * 添加修改
	 * @param offline
	 * @param request
	 * @param isAdd
	 * @param extend
	 * @param pcBannerFile
	 * @param h5BannerFile
	 * @param h5ListBannerFile
	 * @param imgUrlFile
	 * @return
	 * @throws Exception
	 */
	public BaseResult addUpdateJsActivityOffline(JsActivityOffline offline,HttpServletRequest request,int isAdd,
			MultipartFile[] extend,MultipartFile pcBannerFile,
			MultipartFile h5BannerFile,MultipartFile h5ListBannerFile,
			MultipartFile imgUrlFile) throws Exception;
	
}
