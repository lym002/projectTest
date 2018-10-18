package com.jsjf.controller.activity;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.JsActivityExtendPic;
import com.jsjf.model.activity.JsActivityOffline;
import com.jsjf.service.activity.JsActivityOfflineService;

@Controller
@RequestMapping("/activityOffline")
public class JsActivityOfflineController {
	
	@Autowired
	JsActivityOfflineService jsActivityOfflineService;
	
	/**
	 * 添加修改
	 * @param req
	 * @param DrProductInfo
	 * @param productFiles
	 * @param acceptPicFile
	 * @return
	 */
	@RequestMapping(value="/addUpdateJsPublicWelfare",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addUpdateJsPublicWelfare(JsActivityOffline offline,HttpServletRequest request,Integer isAdd,
			@RequestParam MultipartFile[] extendFiles,MultipartFile pcBannerFile,
			MultipartFile h5BannerFile,MultipartFile h5ListBannerFile,
			MultipartFile imgUrlFile){
		BaseResult br = new BaseResult();
		
		try {
			if(Utils.isBlank(isAdd)){
				br.setMsg("参数错误");
				return JSONObject.fromObject(br).toString();
			}
			
			if(Utils.isObjectNotEmpty(pcBannerFile) && !validateImg(pcBannerFile)){
				br.setMsg("pcBanner:图片格式错误 或 图片大于5M");
				return JSONObject.fromObject(br).toString();
			}
			if(Utils.isObjectNotEmpty(h5BannerFile) && !validateImg(h5BannerFile)){
				br.setMsg("h5Banner:图片格式错误 或 图片大于5M");
				return JSONObject.fromObject(br).toString();
			}
			if(Utils.isObjectNotEmpty(h5ListBannerFile) && !validateImg(h5ListBannerFile)){
				br.setMsg("h5ListBanner:图片格式错误 或 图片大于5M");
				return JSONObject.fromObject(br).toString();
			}
			if(Utils.isObjectNotEmpty(imgUrlFile) && !validateImg(imgUrlFile)){
				br.setMsg("h5ListBanner:图片格式错误 或 图片大于5M");
				return JSONObject.fromObject(br).toString();
			}
			if(Utils.isObjectNotEmpty(imgUrlFile) && !validateImgs(extendFiles)){
				br.setMsg("活动现场图片:格式错误 或 图片大于5M");
				return JSONObject.fromObject(br).toString();
			}
			br = jsActivityOfflineService.addUpdateJsActivityOffline(offline, request, isAdd, extendFiles, pcBannerFile, h5BannerFile, h5ListBannerFile, imgUrlFile);
		} catch (Exception e) {
			br.setErrorMsg("系统错误");
			e.printStackTrace();
		}
		return JSONObject.fromObject(br).toString();
	}
	
	/**
	 * 跳转修改添加
	 */
	@RequestMapping("/toAddUpdateJsPublicWelfare")
	public String toAddUpdateJsPublicWelfare(Integer isAdd,Integer id,Map<String,Object> model){
		int extendSize = 0;
		if(!Utils.isBlank(id)){
			JsActivityOffline obj = jsActivityOfflineService.selectJsActivityOfflineById(id);
			List<JsActivityExtendPic> extend = jsActivityOfflineService.selectJsacExtendPicsList(obj.getId(), 1);
			model.put("obj", obj);
			model.put("extend", extend);
			extendSize = extend.size();
		}
		model.put("extendSize", extendSize);
		model.put("isAdd", isAdd);
		return "system/offlineActivity/addUpdatejsPublicWelfare";
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteJsacExtendPic")
	@ResponseBody
	public BaseResult deleteJsacExtendPic(Integer id){
		BaseResult result = new BaseResult();
		try {
			if(!Utils.isBlank(id)){
				jsActivityOfflineService.deleteJsacExtendPic(id);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setErrorMsg("系统错误");
		}
		return result;
	}
	/**
	 * 修改
	 * @param id
	 * @return
	 */
	@RequestMapping("/toUpdateJsActivityOfflineStatus")
	@ResponseBody
	public BaseResult toUpdateJsActivityOfflineStatus(Integer id,Integer status){
		BaseResult result = new BaseResult();
		try {
			if(!Utils.isBlank(id) && !Utils.isBlank(status)){
				JsActivityOffline offline = new JsActivityOffline();
				offline.setId(id);
				offline.setStatus(status);
				jsActivityOfflineService.update(offline);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setErrorMsg("系统错误");
		}
		return result;
	}
	
	@RequestMapping("/toJsPublicWelfare")
	public String toJsPublicWelfare(HttpServletRequest req) {
		return "system/offlineActivity/jsPublicWelfareList";
	}

	@RequestMapping("/jsPublicWelfareList")
	@ResponseBody
	public PageInfo jsPublicWelfareList(JsActivityOffline jsActivityOffline,
			Integer page, Integer rows, HttpServletRequest request) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo info = new PageInfo(page, rows);
		BaseResult br = jsActivityOfflineService.getJsActivityOfflineList(jsActivityOffline, info);
		return (PageInfo) br.getMap().get("page");
	}
	/**
	 * 验证图片
	 * @return
	 */
	private boolean validateImg(MultipartFile multipartFile){
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(multipartFile.getOriginalFilename().toLowerCase());
		if(!matcher.find()){
			return false;
		}		
		long fileSize = multipartFile.getSize();
		if(fileSize>1024*5000){
			return false;
		}
		return true;
	}
	/**
	 * 验证图片
	 * @return
	 */
	private boolean validateImgs(MultipartFile[] multipartFiles){		
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher ;
		for(int i=0;i<multipartFiles.length;i++){
			matcher = pattern.matcher(multipartFiles[i].getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				return false;
			}		
			long fileSize = multipartFiles[i].getSize();
			if(fileSize>1024*5000){
				return false;
			}
		}
		return true;
	}
	
}
