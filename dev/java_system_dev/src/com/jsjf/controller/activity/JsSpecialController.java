package com.jsjf.controller.activity;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.SerializeUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.JsSpecial;
import com.jsjf.model.activity.JsSpecialPic;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.JsSpecialPicService;
import com.jsjf.service.activity.JsSpecialService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Controller
@RequestMapping("/jsSpecial")
public class JsSpecialController {

	@Autowired
	private JsSpecialService jsSpecialService;
	@Autowired
	private JsSpecialPicService jsSpecialPicService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	private PrintWriter writer = null;
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value="/addJsSpecial",produces = "text/html; charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String addJsSpecial(HttpServletRequest request,MultipartFile topPc,MultipartFile topH5,MultipartFile rightPc,
			@RequestParam MultipartFile[] files,
			JsSpecial jsSpecial,HttpServletResponse response) {
			BaseResult result = new BaseResult();
			SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
					ConfigUtil.ADMIN_LOGIN_USER);
			try {
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
	        Pattern pattern = Pattern.compile(reg);
	        List<MultipartFile> list = new ArrayList<MultipartFile>();
			if(topPc != null){
				list.add(topPc);
			}
			if(topH5 != null){
				list.add(topH5);
			}
			if(rightPc != null){
				list.add(rightPc);
			}
	        if(list.size()>0){
	        	for (int i = 0; i < list.size(); i++) {
	        		Matcher matcher = pattern.matcher(list.get(i).getOriginalFilename().toLowerCase());
	        		if(!matcher.find()){
	        			result.setSuccess(false);
	        			result.setErrorMsg("请上传正确的图片格式!");
	        			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
	        			return jsonObject.toString();
	        		}
	        		long fileSize = list.get(i).getSize();
	        		if(fileSize>1024*5000){
	        			result.setSuccess(false);
	        			result.setErrorMsg("图片不能大于5M！");
	        			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
	        			return jsonObject.toString();
	        		}
				}
	        }
	        for(int i=0;i<files.length;i++){
				Matcher matcher = pattern.matcher(files[i].getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					result.setSuccess(false);
					result.setErrorMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
					return jsonObject.toString();
				}
				
				long fileSize = files[i].getSize();
				if(fileSize>1024*5000){
					result.setSuccess(false);
					result.setErrorMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
					return jsonObject.toString();
				}
			}
	        jsSpecial.setAddUser(usersVo.getUserKy().intValue());
	        jsSpecial.setAddDate(new Date());
	        jsSpecial.setUpdateUser(usersVo.getUserKy().intValue());
	        jsSpecial.setUpdateDate(new Date());
			result = jsSpecialService.insert(jsSpecial, topPc, topH5, rightPc,files);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	
	
	@RequestMapping("/toViewJsSpecial")
	public String toViewJsSpecial(Map<String,Object> model){
		JsSpecial jsSpecial = jsSpecialService.selectList();
		if(Utils.isObjectNotEmpty(jsSpecial)){
			List<JsSpecialPic> list = jsSpecialPicService.selectList(jsSpecial.getId());
			model.put("jsSpecial", jsSpecial);
			model.put("jsSpecialPic",list);
		}
		
		return "system/openDay/jsSpecialView";
	}
	
	@RequestMapping("/toJsSpecial")
	public String toJsSpecial(Map<String,Object> model){
		JsSpecial jsSpecial = jsSpecialService.selectList();
		if(Utils.isObjectNotEmpty(jsSpecial)){
			List<JsSpecialPic> list = jsSpecialPicService.selectList(jsSpecial.getId());
			model.put("jsSpecial", jsSpecial);
			model.put("jsSpecialPic",list);
			model.put("picSize",list == null?0:list.size());
		}
		
		return "system/openDay/jsSpecialEdit";
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public String submit(){
		BaseResult br = new BaseResult();
		try {
			JsSpecial jsSpecial = jsSpecialService.selectList();
			if(Utils.isObjectEmpty(jsSpecial)){
				br.setSuccess(false);
				br.setErrorMsg("无数据！");
			}else{
				List<JsSpecialPic> list = jsSpecialPicService.selectList(jsSpecial.getId());
				jsSpecial.setJsSpecialPic(list);
				redisClientTemplate.set("jsSpecial".getBytes(),SerializeUtil.serialize(jsSpecial));
			}
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	
	
	//删除图片
	@RequestMapping(value="/deletePic")
	@ResponseBody
	public BaseResult deletePic(Integer id){
		BaseResult br = new BaseResult();
		try {
			jsSpecialPicService.deleteByid(id);
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("更改失败!");
			br.setSuccess(false);
		}
		return br;
	}
	
}
