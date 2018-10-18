package com.jsjf.controller.activity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ImageUtils;
import com.jsjf.common.PageInfo;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.ActivityFriend;
import com.jsjf.model.activity.ActivityFriendDetail;
import com.jsjf.service.activity.ActivityFriendDetailService;
import com.jsjf.service.activity.ActivityFriendService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/activityFriend")
public class ActivityFriendController {

	@Autowired
	private ActivityFriendService activityFriendService;
	
	@Autowired
	private ActivityFriendDetailService activityFriendDetailService;
	
	/**
	 * 邀请返现管理
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/toInviteCashbackManager")
	public String toInviteCashbackManager(HttpServletRequest req, Map<String, Object> model) {
		return "system/activity/inviteCashbackManager";
	}
	
	/**
	 * 查询活动模板
	 * @param map
	 * @return
	 */
	@RequestMapping("/findInviteCashback")
	@ResponseBody
	public String findInviteCashback(HttpServletRequest req,Integer page,Integer rows) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String,Object>> list= activityFriendService.findInviteCashback(map);
		int count =activityFriendService.findInviteCashbackCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		return JSON.toJSONString(resultMap);
//		JSONObject jsonObject = JSONObject.fromObject(resultMap);
//		return jsonObject.toString();
	}
	
	@RequestMapping("/findInviteCashbackDetail")
	@ResponseBody
	public String findInviteCashbackDetail(@RequestParam("fid")String fid){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("fid", fid);
		List<Map<String,Object>> list= activityFriendDetailService.findActivityFriendDetails(param);
		resultMap.put("rows", list);
		return JSON.toJSONString(resultMap);
//		JSONObject jsonObject = JSONObject.fromObject(resultMap);
//		return jsonObject.toString();
	}
	
	@RequestMapping("/addInviteCashback")
	@ResponseBody
	public BaseResult addInviteCashback(HttpServletRequest request,@RequestParam(value="name",required=false)String name,@RequestParam(value="conditionType",required=false)Integer conditionType,@RequestParam(value="periods",required=false)Integer periods,
			@RequestParam(value="startDate",required=false)String startDate,@RequestParam(value="endDate",required=false)String endDate,@RequestParam(value="conData",required=false) String conData,@RequestParam(value="isPut",required=false)Integer isPut,
			@RequestParam(value = "pcPutImg", required = false)MultipartFile pcPutImg ,@RequestParam(value = "appPutImg", required = false)MultipartFile appPutImg,@RequestParam(value="pcPutUrl",required=false)String pcPutUrl,@RequestParam(value="appPutUrl",required=false)String appPutUrl,@RequestParam(value="putContent",required=false)String putContent) throws Exception{
		BaseResult br = new BaseResult();
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		if(Utils.isObjectNotEmpty(pcPutImg)&&pcPutImg.getSize()>0){
			Matcher matcher = pattern.matcher(pcPutImg.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setErrorMsg("请上传正确图片的格式!");
				return br;
//				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
//				return jsonObject.toString();
			}
			
			long fileSize = pcPutImg.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setErrorMsg("图片不能大于5M！");
				System.out.print(JSON.toJSONString(br));
				return br;
//				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
//				return jsonObject.toString();
			}
		}
		if(Utils.isObjectNotEmpty(appPutImg)&&appPutImg.getSize()>0){
			Matcher matcher = pattern.matcher(appPutImg.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setErrorMsg("请上传正确图片的格式!");
				return br;
//				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
//				return jsonObject.toString();
			}
			
			long fileSize = appPutImg.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setErrorMsg("图片不能大于5M！");
				return br;
//				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
//				return jsonObject.toString();
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		int count = activityFriendService.findExistsInPeriod(startDate, endDate);
		int count2 = activityFriendService.findExistsByNow();
		if(count>0){
			br.setSuccess(false);
			br.setMsg("exists");
			return br;
//			com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
//			return jsonObject.toString();
		}else if(count2>0){
			br.setSuccess(false);
			br.setMsg("exists2");
			return br;
//			com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
//			return jsonObject.toString();
		}else{
			ActivityFriend friend = new ActivityFriend();
			SFtpUtil sftp = new SFtpUtil();
			if(Utils.isObjectNotEmpty(pcPutImg)&&pcPutImg.getSize()>0){
				String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
				String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
				String imageName = ImageUtils.getServerFileName()
						+ pcPutImg.getOriginalFilename().substring(
								pcPutImg.getOriginalFilename().lastIndexOf("."));
				sftp.connectServer();
				sftp.put(pcPutImg.getInputStream(), realPath, imageName);
				sftp.closeServer();
//				map.put("prizeImgUrl", savePath+imageName);
				friend.setPcPutImg(savePath+imageName);
			}
			if(Utils.isObjectNotEmpty(appPutImg)&&appPutImg.getSize()>0){
				String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
				String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
				String imageName = ImageUtils.getServerFileName()
						+ appPutImg.getOriginalFilename().substring(
								appPutImg.getOriginalFilename().lastIndexOf("."));
				sftp.connectServer();
				sftp.put(appPutImg.getInputStream(), realPath, imageName);
				sftp.closeServer();
//				map.put("prizeHeadPhoto", savePath+imageName);
				friend.setAppPutImg(savePath+imageName);
			}
			friend.setAddDate(new Date());
			friend.setName(name);
			friend.setConditionType(conditionType);
			friend.setStartDate(sdf.parse(startDate));
			friend.setEndDate(sdf.parse(endDate));
			friend.setPeriods(periods);
			friend.setStatus(1);
			friend.setIsPut(isPut==null?0:1);
			friend.setPcPutUrl(StringUtils.isEmpty(pcPutUrl)?null:pcPutUrl);
			friend.setAppPutUrl(StringUtils.isEmpty(appPutUrl)?null:appPutUrl);
			friend.setPutContent(StringUtils.isEmpty(putContent)?null:putContent);
			activityFriendService.insert(friend);
			ActivityFriendDetail[] list = new ObjectMapper().readValue(conData,ActivityFriendDetail[].class);
			for(ActivityFriendDetail detail : list){
				detail.setFid(friend.getId());
				detail.setAddDate(new Date());
				activityFriendDetailService.insert(detail);
			}
			br.setSuccess(true);
			return br;
//			com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
//			return jsonObject.toString();
		}
	}
}
