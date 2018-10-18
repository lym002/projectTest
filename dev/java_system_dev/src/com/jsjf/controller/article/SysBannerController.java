package com.jsjf.controller.article;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.article.SysBanner;
import com.jsjf.service.article.SysBannerService;

@Controller
@RequestMapping("/banner")
public class SysBannerController {
	
	@Autowired
	public SysBannerService sysBannerService;
	
	/**
	 * 跳转到栏目列表
	 * @return
	 */
	@RequestMapping("/toSysBannerList")
	public String toSysBannerList(Map<String, Object> model) {
		try {
			model.put("bannerstatus",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("bannerstatus")));
			model.put("bannercode",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("bannercode")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/article/sysBannerList";
	}
	
	/**
	 * 拿到广告列表数据
	 * @param sysBanner
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value= "/sysBannerList")
	@ResponseBody
	public PageInfo sysBannerList(SysBanner sysBanner,Integer page,Integer rows) {
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = sysBannerService.getSysBannerList(sysBanner, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 跳转到栏目添加列表
	 * @return
	 */
	@RequestMapping("/toSysBannerAdd")
	public String toSysBannerAdd(Map<String, Object> model) {
		try {
			model.put("bannercode",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("bannercode")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/article/sysBannerAdd";
	}
	
	/**
	 * 跳转到栏目修改列表
	 * @param sysBanner
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSysBannerUpdate")
	public String toSysBannerUpdate(SysBanner sysBanner,Map<String,Object> model) {
		model.put("sysBanner", sysBannerService.selectSysBannerById(sysBanner));
		try {
			model.put("bannercode",ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("bannercode")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/article/sysBannerUpdate";
	}
	
	/**
	 * 添加广告
	 * @param sysBanner
	 * @return
	 */
	@RequestMapping(value="/addSysBanner",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addSysBanner(MultipartFile bannerAddPicFile,SysBanner sysBanner){
		BaseResult result = new BaseResult();
		
		try {
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
	        Pattern pattern = Pattern.compile(reg);
	        if(Utils.isObjectNotEmpty(bannerAddPicFile)){
	        	Matcher matcher = pattern.matcher(bannerAddPicFile.getOriginalFilename().toLowerCase());
	        	if(!matcher.find()){
	        		result.setSuccess(false);
	        		result.setMsg("请上传正确的图片格式!");
	        		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
	        		return jsonObject.toString();
	        	}
	        	
	        	long fileSize = bannerAddPicFile.getSize();
	        	if(fileSize>1024*5000){
	        		result.setSuccess(false);
	        		result.setMsg("图片不能大于5M！");
	        		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
	        		return jsonObject.toString();
	        	}
	        }
			sysBannerService.addSysBanner(sysBanner,bannerAddPicFile);
			result.setSuccess(true);
			result.setMsg("保存成功！");
		} catch (Exception e) {
			result.setErrorMsg("保存失败！");
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	
	/**
	 * 修改广告
	 * @param sysBanner
	 * @return
	 */
	@RequestMapping(value="/updateSysBanner",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateSysBanner(MultipartFile bannerUpdPicFile,SysBanner sysBanner){
		BaseResult result = new BaseResult();
		try {
			if(Utils.isObjectNotEmpty(bannerUpdPicFile)){
				String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		        Pattern pattern = Pattern.compile(reg);
		        Matcher matcher = pattern.matcher(bannerUpdPicFile.getOriginalFilename().toLowerCase());
		        if(!matcher.find()){
					result.setSuccess(false);
					result.setMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
					return jsonObject.toString();
		        }
		        
				long fileSize = bannerUpdPicFile.getSize();
				if(fileSize>1024*5000){
					result.setSuccess(false);
					result.setMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
					return jsonObject.toString();
				}
			}
			
			sysBannerService.updateSysBanner(sysBanner,bannerUpdPicFile);
			result.setSuccess(true);
			result.setMsg("修改成功！");
		} catch (Exception e) {
			result.setErrorMsg("修改失败！");
			e.printStackTrace();
		}
		
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	
	/**
	 * 删除广告
	 * @param sysBanner
	 * @return
	 */
	@RequestMapping("/deleteSysBanner")
	@ResponseBody
	public BaseResult deleteSysBanner(SysBanner sysBanner){
		BaseResult result = new BaseResult();
		result = sysBannerService.deleteSysBanner(sysBanner);
		return result;
	}
	
	/**
	 * 恢复广告
	 * @param sysBanner
	 * @return
	 */
	@RequestMapping("/recoverSysBanner")
	@ResponseBody
	public BaseResult recoverSysBanner(SysBanner sysBanner){
		BaseResult result = new BaseResult();
		result = sysBannerService.recoverSysBanner(sysBanner);
		return result;
	}
}
