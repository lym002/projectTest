package com.jsjf.controller.article;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.article.SysArticle;
import com.jsjf.model.system.SysBank;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.JsOpenDayService;
import com.jsjf.service.article.SysArticleService;
import com.jsjf.service.member.DrMemberFourElementsLogService;

@Controller
@RequestMapping("/article")
public class SysArticleController {

	@Autowired
	public SysArticleService sysArticleService;
	private PrintWriter writer = null;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private DrMemberFourElementsLogService drMemberFourElementsLogService;
	@Autowired
	private JsOpenDayService jsOpenDayService;
	
	/**
	 * 查询所有银行
	 * @return BaseResult
	 */
	@RequestMapping(value= "/drAllSysBank")
	@ResponseBody
	public List<SysBank> drAllSysBank() {
		List<SysBank> sysBank = drMemberFourElementsLogService.selectSysBank();
		return sysBank;
	}

	/**
	 * 跳转到文章列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/toArticleList")
	public String toArticleList(Map<String, Object> model) {
		model.put("sysProgram", sysArticleService.getAllSysProgram());
		try {
			model.put("articleishead",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("whether")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/article/sysArticleList";
	}
	
	/**
	 * 拿到文章列表数据
	 * @param sysArticle
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/articleList")
	@ResponseBody
	public PageInfo articleList(SysArticle sysArticle, Integer page,
			Integer rows) {
		if (page == null) {
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if (rows == null) {
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page, rows);
		BaseResult result = sysArticleService.getArticleList(sysArticle, pi);

		return (PageInfo) result.getMap().get("page");
//		return pi;
	}
	
	/**
	 * 跳转到查看文章
	 * @param sysArticle
	 * @param model
	 * @return
	 */
	@RequestMapping("/showArticle")
	public String showArticle(SysArticle sysArticle, Map<String, Object> model) {
		model.put("sysProgram", sysArticleService.getAllSysProgram());
		model.put("sysArticle",sysArticleService.selectSysArticleById(sysArticle));
		model.put("createTime", Utils.getparseDate(sysArticleService
				.selectSysArticleById(sysArticle).getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		if(Utils.isObjectNotEmpty(sysArticleService.selectSysArticleById(sysArticle))){
			model.put("openDayList", jsOpenDayService.getOpenDayAll());
		}
		return "system/article/sysArticleShow";
	}

	/**
	 * 跳转到修改文章
	 * @param sysArticle
	 * @param model
	 * @return
	 */
	@RequestMapping("/toArticleUpdate")
	public String toArticleUpdate(SysArticle sysArticle,
			Map<String, Object> model) {
		model.put("sysProgram", sysArticleService.getAllSysProgram());
		model.put("sysArticle",sysArticleService.selectSysArticleById(sysArticle));
		model.put("createTime", Utils.getparseDate(sysArticleService
				.selectSysArticleById(sysArticle).getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		if(Utils.isObjectNotEmpty(sysArticleService.selectSysArticleById(sysArticle))){
			model.put("openDayList", jsOpenDayService.getOpenDayAll());
		}
		return "system/article/sysArticleUpdate";
	}

	/**
	 * 修改文章
	 * @param request
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping(value="/articleUpdate",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String articleUpdate(HttpServletRequest request,HttpServletResponse response,MultipartFile articleUpdPicFile,
			SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
        Pattern pattern = Pattern.compile(reg);
        if(Utils.isObjectNotEmpty(articleUpdPicFile)){
        	Matcher matcher = pattern.matcher(articleUpdPicFile.getOriginalFilename().toLowerCase());
        	if(!matcher.find()){
        		result.setSuccess(false);
        		result.setMsg("请上传正确的图片格式!");
        		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        		return jsonObject.toString();
        	}
        	
        	long fileSize = articleUpdPicFile.getSize();
        	if(fileSize>1024*5000){
        		result.setSuccess(false);
        		result.setMsg("图片不能大于5M！");
        		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        		return jsonObject.toString();
        	}
        }
		
		try {
			sysArticleService.updateArticle(sysArticle, usersVo,articleUpdPicFile);
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
	 * 删除文章
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping("/deleteArticle")
	@ResponseBody
	public BaseResult deleteArticle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		result = sysArticleService.deleteArticle(sysArticle);
		return result;
	}
	
	/**
	 * 恢复文章及审核通过
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping("/recoverAtricle")
	@ResponseBody
	public BaseResult recoverAtricle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		result = sysArticleService.recoverAtricle(sysArticle);
		return result;
	}

	/**
	 * 拒绝文章
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping("/refuseAtricle")
	@ResponseBody
	public BaseResult refuseAtricle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		result = sysArticleService.refuseAtricle(sysArticle);
		return result;
	}
	
	/**
	 * 是否推荐文章
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping("/isrecommendArticle")
	@ResponseBody
	public BaseResult isrecommendArticle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		result = sysArticleService.isrecommendArticle(sysArticle);
		return result;
	}

	/**
	 * 跳转到添加文章
	 * @param model
	 * @return
	 */
	@RequestMapping("/toArticleAdd")
	public String toArticleAdd(Map<String, Object> model) {
		model.put("sysProgram", sysArticleService.getAllSysProgram());
		model.put("openDayList", jsOpenDayService.getOpenDayAll());
		return "system/article/sysArticleAdd";
	}

	/**
	 * 添加文章
	 * @param request
	 * @param sysArticle
	 * @return
	 */
	@RequestMapping(value="/articleAdd",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String articleAdd(HttpServletRequest request,MultipartFile articleAddPicFile,
			SysArticle sysArticle,HttpServletResponse response) {
		BaseResult result = new BaseResult();
		SysUsersVo usersVo = (SysUsersVo) request.getSession().getAttribute(
				ConfigUtil.ADMIN_LOGIN_USER);
		
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
        Pattern pattern = Pattern.compile(reg);
        if(Utils.isObjectNotEmpty(articleAddPicFile)){
        	Matcher matcher = pattern.matcher(articleAddPicFile.getOriginalFilename().toLowerCase());
        	if(!matcher.find()){
        		result.setSuccess(false);
        		result.setMsg("请上传正确的图片格式!");
        		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        		return jsonObject.toString();
        	}
        	
        	long fileSize = articleAddPicFile.getSize();
        	if(fileSize>1024*5000){
        		result.setSuccess(false);
        		result.setMsg("图片不能大于5M！");
        		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        		return jsonObject.toString();
        	}
        }
		
		try {
			sysArticleService.addArticle(sysArticle, usersVo,articleAddPicFile);
			result.setSuccess(true);
			result.setMsg("保存成功！");
		} catch (Exception e) {
			result.setErrorMsg("保存失败！");
			e.printStackTrace();
		}

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
		return jsonObject.toString();
	}
	

	@RequestMapping(value = "/file_upload", method = RequestMethod.POST)
	public void file_upload(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String savePath = ConfigUtil.getImgFileUrl() + ConfigUtil.articleImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
		// 文件保存目录URL
		String saveUrl = ConfigUtil.getDomainname() + ConfigUtil.articleImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
		// String bigImageName =
		// ImageUtils.getServerFileName()+filename.getOriginalFilename().substring(filename.getOriginalFilename().lastIndexOf("."));

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// 最大文件大小
		long maxSize = 1024*5000;

		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		writer = response.getWriter();
		if (!ServletFileUpload.isMultipartContent(request)) {
			writer.println(objectMapper.writeValueAsString(getError("请选择文件。")));
			return;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			writer.println(objectMapper.writeValueAsString(getError("目录名不正确。")));
			return;
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			if (!item.isFormField()) {
				// 检查文件大小
				if (item.getSize() > maxSize) {
					writer.println(objectMapper
							.writeValueAsString(getError("上传文件大小超过限制。")));
					return;
				}
				// 检查扩展名
				String fileExt = fileName.substring(
						fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(","))
						.contains(fileExt)) {
					writer.println(objectMapper
							.writeValueAsString(getError("上传文件扩展名是不允许的扩展名。\n只允许"
									+ extMap.get(dirName) + "格式。")));
					return;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_"
						+ new Random().nextInt(1000) + "." + fileExt;
				try {
					SFtpUtil sftp = new SFtpUtil();
					sftp.connectServer();
					sftp.put(item.getInputStream(), savePath, newFileName);
					sftp.closeServer();
				} catch (Exception e) {
					writer.println(objectMapper
							.writeValueAsString(getError("上传文件失败。")));
				}

				Map<String, Object> msg = new HashMap<String, Object>();
				msg.put("error", 0);
				msg.put("url", saveUrl + newFileName);
				writer.println(objectMapper.writeValueAsString(msg));

				return;
			}
		}
		return;
	}

	private Map<String, Object> getError(String message) {
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 1);
		msg.put("message", message);
		return msg;
	}

	/**
	 * 表单提交日期绑定
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
}
