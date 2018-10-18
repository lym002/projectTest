package com.jsjf.controller.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ExcelUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.DrGiftCardSetUp;
import com.jsjf.model.activity.DrGiftCardSetUpDetail;
import com.jsjf.model.cpa.DrChannelInfo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.DrGiftCardSetUpService;
import com.jsjf.service.cpa.DrChannelInfoService;

@Controller
@RequestMapping("/giftCardSetUp")
public class DrGiftCardSetUpController{
	private Logger log = Logger.getLogger(DrGiftCardSetUpController.class);
	@Autowired
	private DrGiftCardSetUpService drGiftCardSetUpService;
	@Autowired
	private DrChannelInfoService drChannelInfoService;

	// 跳转
	@RequestMapping("/toGiftCardSetUpList")
	public String toGiftCardSetUpList(HttpServletRequest req,
			Map<String, Object> model) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DrChannelInfo> list = drChannelInfoService
				.getDrChannelInfoListForMap(map);
		model.put("channelList", list);
		return "system/activity/drGiftCardSetUpList";
	}

	@RequestMapping("/drGiftCardSetUpList")
	@ResponseBody
	public PageInfo drGiftCardSetUpList(DrGiftCardSetUp drGiftCardSetUp,
			Integer page, Integer rows, HttpServletRequest request) {
		PageInfo info = new PageInfo(page, rows);
		try {
			info = drGiftCardSetUpService.getDrGiftCardSetUpList(
					drGiftCardSetUp, info);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}

	@RequestMapping("/addGiftCardSetUp")
	@ResponseBody
	public BaseResult addGiftCardSetUp(HttpServletRequest req,
			DrGiftCardSetUp drGiftCardSetUp) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		try {
			if(Utils.isObjectNotEmpty(drGiftCardSetUp.getName())){
				drGiftCardSetUp.setCode("GCS" + Utils.getTime());
				drGiftCardSetUp.setAddUser(usersVo.getUserKy().intValue());
				drGiftCardSetUp.setAddTime(new Date());
				drGiftCardSetUp.setStatus(1);
				drGiftCardSetUpService.insertDrGiftCardSetUp(drGiftCardSetUp);
				result.setSuccess(true);
				result.setMsg("添加成功");
			}else{
				result.setErrorMsg("信息不完整！");
			}
		} catch (Exception e) {
			result.setErrorMsg("保存失败！");
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 根据ID查询提现设置信息
	 * @param id
	 * @return BaseResult
	 */
	@RequestMapping(value= "/queryGiftCardSetUp")
	@ResponseBody
	public BaseResult queryGiftCardSetUp(Integer id) {
		BaseResult result = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		DrGiftCardSetUp drGiftCardSetUp;
		try {
			drGiftCardSetUp = drGiftCardSetUpService.getDrGiftCardSetUpById(id);
			map.put("drGiftCardSetUp",drGiftCardSetUp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setMap(map);
		result.setSuccess(true);
		return result;
	}
	
	
	@RequestMapping("/updateGiftCardSetUp")
	@ResponseBody
	public BaseResult updateGiftCardSetUp(HttpServletRequest req,DrGiftCardSetUp drGiftCardSetUp) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			if(Utils.isObjectNotEmpty(drGiftCardSetUp.getName())){
				drGiftCardSetUp.setUpdateUser(usersVo.getUserKy().intValue());
				drGiftCardSetUpService.updateDrGiftCardSetUp(drGiftCardSetUp);
				result.setSuccess(true);
				result.setMsg("修改成功");
			}else{
				result.setErrorMsg("信息不完整，修改失败！");
			}
		} catch (Exception e) {
			result.setErrorMsg("修改失败！");
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	@RequestMapping("/updateGiftCardSetUpStatus")
	@ResponseBody
	public BaseResult updateGiftCardSetUpStatus(Integer id,Integer status,HttpServletRequest req){
		BaseResult br = new BaseResult();
		try {
			SysUsersVo usersVo= (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			DrGiftCardSetUp drGCS = drGiftCardSetUpService.getDrGiftCardSetUpById(id);
			if(drGCS.getStatus()==status){
				if(1==status){
					drGCS.setStatus(0);
					drGCS.setUpdateUser(usersVo.getUserKy().intValue());
				}
			}
			drGiftCardSetUpService.updateDrGiftCardSetUp(drGCS);
			br.setSuccess(true);
			br.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("修改失败");
			return br;
		}
		return br;
	}
	
	
	@RequestMapping("/uploadGiftCardDetail")
	@ResponseBody
	public BaseResult uploadGiftCardDetail(HttpServletRequest request,
			@RequestParam(value="file", required=true) MultipartFile multipartFile, Integer parentId){
		
		BaseResult br = new BaseResult();
		SysUsersVo user = (SysUsersVo)request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		File uploadfile = null;
		try {
			String base = request.getSession().getServletContext().getRealPath("/")+"uploadExcel/";
			File file = new File(base);
			if(!file.exists()){
				file.mkdirs();
			}
			String path = base+"/"+System.currentTimeMillis()
					+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."),multipartFile.getOriginalFilename().length());
			uploadfile = new File(path);
			multipartFile.transferTo(uploadfile);
			File read = new File(path);
			InputStream inputStream = new FileInputStream(read);
			LinkedHashMap<String,String> fieldMap = new LinkedHashMap<String, String>();
			
			fieldMap.put("兑换券号", "giftCard");
			
			String[] uniqueFields = new String[]{"兑换券号"};
			List<DrGiftCardSetUpDetail> list = ExcelUtil.excelToList(inputStream, 0, DrGiftCardSetUpDetail.class, fieldMap, uniqueFields);
			DrGiftCardSetUp dDrGiftCardSetUp = drGiftCardSetUpService.getDrGiftCardSetUpById(parentId);
			List<DrGiftCardSetUpDetail> insertList = new ArrayList<DrGiftCardSetUpDetail>();
			if(list.size()>0&&dDrGiftCardSetUp!=null){
				for (DrGiftCardSetUpDetail drGiftCardSetUpDetail : list) {
					drGiftCardSetUpDetail.setParentId(parentId);
					drGiftCardSetUpDetail.setAddUser(user.getUserKy().intValue());
					insertList.add(drGiftCardSetUpDetail);
				}
				
				drGiftCardSetUpService.batchInsert(insertList);
				br.setMsg("导入成功");
				br.setSuccess(true);
				
			}else{
				br.setErrorMsg("导入数据为空！");
				br.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("导入失败",e);
			br.setErrorMsg("导入失败"+e.getMessage());
			br.setSuccess(false);
		}
		return br;
	}
	
	@RequestMapping("/queryGiftCardDetail")
	@ResponseBody
	public PageInfo queryGiftCardDetail(HttpServletRequest request, DrGiftCardSetUpDetail detail,
			Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		try {
			pi = drGiftCardSetUpService.getDrGiftCardSetUpDetailListByParentId(detail.getParentId(), pi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	@RequestMapping("/updateDrGiftCardDetailStatus")
	@ResponseBody
	public BaseResult updateDrGiftCardDetailStatus(Integer id,Integer status,HttpServletRequest req){
		BaseResult br= new BaseResult();
		try {
			DrGiftCardSetUpDetail detail = drGiftCardSetUpService.getDrGiftCardSetUpDetailById(id);
			if(detail.getStatus()==status){
				if(1==status){
					drGiftCardSetUpService.updateDrGiftCardSetUpDetailStatus(id);
				}
			}
			br.setSuccess(true);
			br.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			br.setErrorMsg("修改失败");
			return br;
		}
		return br;
	}
	
	// 跳转到兑换券明细页面
	@RequestMapping("/toDrGiftCardDetailList")
	public String toDrGiftCardDetailList(HttpServletRequest req,
			Map<String, Object> model) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DrChannelInfo> list = drChannelInfoService
				.getDrChannelInfoListForMap(map);
		model.put("channelList", list);
		return "system/activity/drGiftCardDistribution";
	}

	//查询兑换券
	@RequestMapping("/drGiftCardDetailList")
	@ResponseBody
	public PageInfo drGiftCardDetailList(DrGiftCardSetUpDetail detail,
			Integer page, Integer rows, HttpServletRequest request) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		try {
			pi = drGiftCardSetUpService.getDrGiftCardSetUpDetailList(detail, pi);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pi;
	}
	
	
	/**
	 * 表单提交日期绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
