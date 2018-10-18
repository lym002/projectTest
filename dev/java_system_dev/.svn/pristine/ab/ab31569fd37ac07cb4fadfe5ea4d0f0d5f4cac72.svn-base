package com.jsjf.controller.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjf.common.BaseResult;
import com.jsjf.common.FileUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.activity.ActivityPrize;
import com.jsjf.model.activity.ActivityTemplate;
import com.jsjf.service.activity.ActivityTemplateService;
import com.jsjf.service.system.impl.RedisClientTemplate;

import java.util.regex.Pattern;

@Controller
@RequestMapping("activityTemplate/")
public class ActivityTemplateController {
	
	@Autowired 
	private ActivityTemplateService activityTemplateService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	/**
	 * 跳转到活动模板管理
	 * @return
	 */
	@RequestMapping("/toActivityTemplateList")
	public String toRedEnvelopeList(){
		return "system/activityTemplate/activityTemplateList";
	}
	
	/**
	 * 查询活动模板
	 * @param map
	 * @return
	 */
	@RequestMapping("/selActivityTemplate")
	@ResponseBody
	public String selActivityTemplate(HttpServletRequest req,Integer page,Integer rows,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime) {
		Map<String,Object> map=new HashMap();
		if(name!=null && !name.equals("")){
			map.put("name",name);
		}
		if(startTime!=null && !startTime.equals("")){
			map.put("startTime",startTime);
		}
		if(endTime!=null && !endTime.equals("")){
			map.put("endTime",endTime);
		}
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String,Object>> list= activityTemplateService.selActivityTemplate(map);
		int count =activityTemplateService.selActivityTemplateCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 新增活动模板
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/addActivityTemplate")
	@ResponseBody
	public String addActivityTemplate(HttpServletRequest req,
			@RequestParam(value="conData",required=false) String conData,
			@RequestParam(value="activityname",required=false) String activityname,
			@RequestParam(value="codeFixation",required=false) String codeFixation,
			@RequestParam(value="digit",required=false) Integer digit) throws JsonParseException, JsonMappingException, IOException {
		ActivityPrize[] list = new ObjectMapper().readValue(conData,ActivityPrize[].class);
		Map<String,Object> map=new HashMap();
		ActivityTemplate activityTemplate=new ActivityTemplate();
		activityTemplate.setCodeFixation(codeFixation);
		activityTemplate.setDigit(digit);
		activityTemplate.setName(activityname);
		activityTemplateService.insert(activityTemplate);
		for (ActivityPrize activityPrize : list) {
			activityPrize.setAtid(activityTemplate.getId());
			activityTemplateService.insertPrize(activityPrize);
		}
		return "success";
	}
	
	/**
	 * 根据atid查询奖品信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/selActivityPrize")
	@ResponseBody
	public String selActivityPrize(@RequestParam(value="atid",required=false) String atid) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map=new HashMap();
		map.put("atid", atid);
		List<Map<String,Object>> list= activityTemplateService.selActivityPrize(map);
		resultMap.put("rows", list);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 修改活动模板
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/updateActivityTemplate")
	@ResponseBody
	public String updateActivityTemplate(HttpServletRequest req,
			@RequestParam(value="conData",required=false) String conData,
			@RequestParam(value="activityname",required=false) String activityname,
			@RequestParam(value="codeFixation",required=false) String codeFixation,
			@RequestParam(value="digit",required=false) Integer digit,
			@RequestParam(value="atid",required=false) Integer atid) throws JsonParseException, JsonMappingException, IOException {
		ActivityPrize[] list = new ObjectMapper().readValue(conData,ActivityPrize[].class);
		Map<String,Object> map=new HashMap();//奖品明细map
		map.put("atid", atid);
		activityTemplateService.delActivityPrize(map);//删除奖品明细
		
		ActivityTemplate activityTemplate=new ActivityTemplate();
		activityTemplate.setCodeFixation(codeFixation);
		activityTemplate.setDigit(digit);
		activityTemplate.setName(activityname);
		activityTemplate.setId(atid);
		activityTemplateService.updateActivityTemplate(activityTemplate);
		for (ActivityPrize activityPrize : list) {
			activityPrize.setAtid(atid);
			activityTemplateService.insertPrize(activityPrize);
		}
		return "success";
	}
	
	/**
	 * 跳转到活动列表管理
	 * @return
	 */
	@RequestMapping("/toActivityProductList")
	public String toActivityProductList(){
		return "system/activityTemplate/activityProductList";
	}
	
	/**
	 * 查询活动列表
	 * @param map
	 * @return
	 */
	@RequestMapping("/selActivityProductAll")
	@ResponseBody
	public String selActivityProductAll(HttpServletRequest req,Integer page,Integer rows,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="productname",required=false) String productname,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime) {
		Map<String,Object> map=new HashMap();
		if(name!=null && !name.equals("")){
			map.put("name",name);
		}
		if(productname!=null && !productname.equals("")){
			map.put("productname",productname);
		}
		if(status!=null && !status.equals("")){
			map.put("status",status);
		}
		if(startTime!=null && !startTime.equals("")){
			map.put("startTime",startTime);
		}
		if(endTime!=null && !endTime.equals("")){
			map.put("endTime",endTime);
		}
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String,Object>> list= activityTemplateService.selActivityProductAll(map);
		int count =activityTemplateService.selActivityProductAllCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 发布开奖信息
	 * @param drProductInfo
	 * @param request
	 * @param productFiles
	 * @param acceptPicFile
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addActivityProduct",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addActivityProduct(HttpServletRequest request,String prizeCode,String id,String prizeUrl,
			MultipartFile acceptPicFile) throws Exception{
		BaseResult br = new BaseResult();
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		if(Utils.isObjectNotEmpty(acceptPicFile)){
			Matcher matcher = pattern.matcher(acceptPicFile.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setMsg("请上传正确抽奖图片的格式!");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			
			long fileSize = acceptPicFile.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setMsg("抽奖图片不能大于5M！");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
		}
		Map<String,Object> map=new HashMap();
		map.put("prizeCode", prizeCode);
		map.put("id", id);
		int count=activityTemplateService.selInvestByCode(map);
		if(count==0){
			br.setMsg("幸运码不存在!");
			br.setSuccess(false);
		}else{
				br = activityTemplateService.addActivityProduct(acceptPicFile,prizeCode,id,prizeUrl);
		}
		com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 跳转到活动详情页面
	 */
	@RequestMapping("/toActivityDetail")
	public String toActivityDetail(Map<String,Object> model,String id) {
		try {
		Map<String,Object> map=new HashMap();
		map.put("id", id);
		List<Map<String, Object>> list=activityTemplateService.selActivityProductById(map);  //活动信息
		List<Map<String, Object>> prizelist=activityTemplateService.selActivityPrizeById(map); //产品信息
		List<Map<String, Object>> count=activityTemplateService.selSumCount(map); //产品信息
		model.put("activityProduct", list.get(0));
		model.put("activityPrize", prizelist.get(0));
		model.put("count", count.get(0));
		model.put("id", id);
		
		String detailImg = (String) list.get(0).get("pcDetailImg");
		if(!StringUtils.isEmpty(detailImg))
		model.put("detailImg",detailImg.substring(detailImg.lastIndexOf("/")+1, detailImg.length()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/activityTemplate/activityDetail";
	}
	
	/**
	 * 查询投资人信息
	 * @param map
	 * @return
	 */
	@RequestMapping("/selProductInvestById")
	@ResponseBody
	public String selProductInvestById(HttpServletRequest req,Integer page,Integer rows,String id,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="status",required=false) String status) {
		Map<String,Object> map=new HashMap();
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		if(code != null && !code.equals("")){
			map.put("luckCodes",code); 
		}
		if(status != null && !status.equals("")){
			map.put("prizeStatus",status); 
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit());
		map.put("id", id);
		List<Map<String,Object>> list= activityTemplateService.selProductInvestById(map);
		int count =activityTemplateService.selProductInvestByIdCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 上传抽奖人信息
	 */
	@RequestMapping("/addInvest")
	@ResponseBody
	public String addInvest(HttpServletRequest request,String prizeContent,String id,String prizeMobile,String prizeVideoLink,String prizeVideoUrl,MultipartFile headFile,
			MultipartFile acceptPicFile) throws Exception{
		BaseResult br = new BaseResult();
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		if(Utils.isObjectNotEmpty(acceptPicFile)){
			Matcher matcher = pattern.matcher(acceptPicFile.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setMsg("请上传正确中奖图片的格式!");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			
			long fileSize = acceptPicFile.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setMsg("中奖图片不能大于5M！");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
		}
		if(Utils.isObjectNotEmpty(headFile)){
			Matcher matcher = pattern.matcher(headFile.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setMsg("请上传正确中奖图片的格式!");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			
			long fileSize = headFile.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setMsg("中奖图片不能大于5M！");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
		}
				br = activityTemplateService.addInvest(acceptPicFile,prizeContent,id,prizeMobile,prizeVideoLink,prizeVideoUrl,headFile);
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
	}
	
	/**
	 * 导出幸运码
	 */
	@RequestMapping("/exportInvest")
	@ResponseBody
	public String exportInvest(HttpServletRequest request,HttpServletResponse response,String id) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String,Object> map=new HashMap();
		map.put("id", id);
		List<Map<String,Object>> list= activityTemplateService.exportData(map);
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
		String filename=sdf1.format(new Date());
		File file =  new File(  request.getRealPath( "/" ) + filename +".xls");
		FileOutputStream os = new FileOutputStream(file);
		export(os,list);//创建excel工作簿
		os.flush(); 
		os.close();
		FileUtil.download(file,response);
		if(file.exists()){
			file.delete();
		}
		return "";
	}
	
	
	private void export(FileOutputStream osw2,List<Map<String,Object>> list) throws Exception{
		String[] col={"姓名","手机号","推荐码","投资时间","投资金额","幸运码"};
		HSSFWorkbook wb = new HSSFWorkbook();  
	    
	    // 创建字体样式  
	    HSSFFont font = wb.createFont();  
	    font.setBoldweight((short) 300);  
	    font.setFontHeight((short) 250);  
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
	    
	    // 创建单元格样式  
	    HSSFCellStyle style = wb.createCellStyle();  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
	    style.setFont(font);
	    
	    String title = "用户幸运码";
	    // 创建Excel的工作sheet,对应到一个excel文档的tab  
	    HSSFSheet sheet = wb.createSheet(title); 

	    for (int i = 0; i < col.length; i++) {
		    sheet.setColumnWidth(i, 4000);    
		}
	    HSSFRow row = sheet.createRow(0); 
	    HSSFCell cell = row.createCell(0);
	    cell.setCellStyle(style);
	    HSSFCellStyle style1 = wb.createCellStyle(); 
	    style1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);  
	    style1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);  
	    style1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);  
	    style1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); 
	    style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
	    style1.setWrapText(true);
	    for (int i = 0; i < col.length; i++) {
	    	cell=row.createCell(i);
	    	cell.setCellValue(col[i]);
	    	cell.setCellStyle(style1);
		}
	    int rowcount=1;
	    for (Map<String, Object> map : list) {
	    	HSSFRow rowq = sheet.createRow(rowcount);
	    	rowq.createCell(0).setCellValue(map.get("realname").toString() == null ?"":map.get("realname").toString());//客户姓名
	    	rowq.createCell(1).setCellValue(map.get("mobilePhone").toString() == null ?"":map.get("mobilePhone").toString());//手机号
	    	rowq.createCell(2).setCellValue(map.get("recommCodes").toString() == null ?"":map.get("recommCodes").toString());//幸运码
	    	rowq.createCell(3).setCellValue(map.get("investTime").toString() == null ?"":map.get("investTime").toString());//投资时间
	    	rowq.createCell(4).setCellValue(map.get("amount").toString() == null ?"":map.get("amount").toString());//投资金额
	    	rowq.createCell(5).setCellValue(map.get("luckCodes").toString() == null ?"":map.get("luckCodes").toString());//幸运码
	    	rowcount++;	
		} 
	    wb.write(osw2);
		}

	@RequestMapping("/updateAppTitle")
	@ResponseBody
	public String updateAppTitle(@RequestParam(value="appTitle",required=false)String appTitle,@RequestParam(value="productId",required=false)String productId){
		activityTemplateService.updateActivityProductAppTitle(appTitle, productId);
		return "success";
	}
	
	@RequestMapping("/updateDetailImg")
	@ResponseBody
	public String updateDetailImg(MultipartFile detailFile,@RequestParam(value="productId",required=false)String productId) throws Exception{
		BaseResult br = new BaseResult();
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		if(Utils.isObjectNotEmpty(detailFile)){
			Matcher matcher = pattern.matcher(detailFile.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setMsg("请上传正确中奖图片的格式!");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			
			long fileSize = detailFile.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setMsg("中奖图片不能大于5M！");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
		}
		br = activityTemplateService.updateActivityProductDetailImg(detailFile,productId);
		com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	@RequestMapping("/updateActivityDetailBanner")
	@ResponseBody
	public String updateActivityDetailBanner(MultipartFile pcBannerFile,MultipartFile h5BannerFile,@RequestParam(value="productId",required=false)String productId) throws Exception{
		BaseResult br = new BaseResult();
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		if(Utils.isObjectNotEmpty(pcBannerFile)){
			Matcher matcher = pattern.matcher(pcBannerFile.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setMsg("请上传正确中奖图片的格式!");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			
			long fileSize = pcBannerFile.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setMsg("中奖图片不能大于5M！");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
		}
		if(Utils.isObjectNotEmpty(h5BannerFile)){
			Matcher matcher = pattern.matcher(h5BannerFile.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setMsg("请上传正确中奖图片的格式!");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
			
			long fileSize = h5BannerFile.getSize();
			if(fileSize>1024*5000){
				br.setSuccess(false);
				br.setMsg("中奖图片不能大于5M！");
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
				return jsonObject.toString();
			}
		}
		br = activityTemplateService.updateActivityDetailBanner(pcBannerFile, h5BannerFile, productId);
		com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(br);
		return jsonObject.toString();
	}
}
