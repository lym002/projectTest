package com.jsjf.controller.product;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.JXLExcelView;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMemberCarry;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.JsProductPrize;
import com.jsjf.model.product.JsProductPrizeOrderShare;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.product.JsProductPrizeOrderShare;
import com.jsjf.model.product.JsProductPrizeOrderShare;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.JsproductPrizeService;

@Controller
@RequestMapping("/productPrize")
public class JsProductPrizeController{
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private JsproductPrizeService jsproductPrizeService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	
	
	/**
	 * 投即送心愿
	 * @return
	 */
	@RequestMapping("/toJsProductPrizeWish")
	public String toJsProductPrizeWish(){
	
		return "system/product/jsProductPrizeWishList";
	}
	/**
	 * 显示投即送心愿列表
	 */
	@RequestMapping("/jsProductPrizeWishList")
	@ResponseBody
	public PageInfo jsProductPrizeWishList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsproductPrizeService.getJsProductPrizWish(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 显示投即送心愿列表
	 */
	@RequestMapping("/jsProductPrizeWishListExport")
	@ResponseBody
	public ModelAndView jsProductPrizeWishListExport(@RequestParam Map<String,Object> param){
		
		List<Map<String, Object>> list =  jsproductPrizeService.getJsProductPrizWishExport(param);
		
		String[] title = new String[]{"姓名","联系方式 ","推荐码","提交时间 ","礼品链接 ","备注"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:list){
			lc = new ArrayList<Object>();
			lc.add(map.get("realname"));
			lc.add(map.get("mobilePhone"));
			lc.add(map.get("recommCodes"));
			lc.add(map.get("addtime"));
			lc.add(map.get("url"));
			lc.add(map.get("remark"));
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "wish_prize_"+Utils.format(new Date(), "yyyy-MM-dd")+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
	//*****************************投即送晒单*****************
	/**
	 * 跳转到晒单修改添加
	 */
	@RequestMapping("/toAddUpdateJsProductOrderShare")
	public String toAddUpdateJsProductOrderShare(Integer isAdd,Integer id,Map<String,Object> model){
		
		if(!Utils.isBlank(id)){
			model.put("obj", jsproductPrizeService.selectJsProductOrderShare(id));
		}
		model.put("isAdd", isAdd);
		return "system/product/addUpdateJsProductPrizeOrderShare";
	}
	
	/**
	 * 晒单修改添加
	 */
	@RequestMapping(value="/addUpdateJsProductOrderShare",produces = "text/html; charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String addUpdateJsProductOrderShare(JsProductPrizeOrderShare share,Integer isAdd,HttpServletRequest request,
			MultipartFile h5ImgFile,MultipartFile pcImgFile){
		BaseResult result = new BaseResult();
		try {
			if(isAdd !=null && Utils.isObjectNotEmpty(share)){
				result = jsproductPrizeService.insertUpdateJsProductOrderShare(share,h5ImgFile,pcImgFile, isAdd==1?true:false);
			}
		} catch (Exception e) {
			result.setErrorMsg("系统错误");
			e.printStackTrace();
		}
		return JSONObject.toJSONString(result);
	}
	
	/**
	 * 跳转到晒单
	 */
	@RequestMapping("/toJsProductOrderShare")
	public String toJsProductOrderShare(){
		return "system/product/jsProductPrizeOrderShareList";
	}
	
	/**
	 * 显示晒单列表
	 */
	@RequestMapping("/jsProductOrderShareList")
	@ResponseBody
	public PageInfo jsProductOrderShareList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsproductPrizeService.getJsProductPrizeOrderShareList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	/**
	 * 显示晒单列表
	 */
	@RequestMapping("/deleteJsProductOrderShare")
	@ResponseBody
	public BaseResult deleteJsProductOrderShare(Integer id){
		BaseResult result = new BaseResult();
		try {
			if(!Utils.isBlank(id)){
				JsProductPrizeOrderShare obj = new JsProductPrizeOrderShare();
				obj.setId(id);
				obj.setIsShow(0);
				jsproductPrizeService.updateJsProductPrizeOrderShare(obj);
				result.setSuccess(true);
				result.setMsg("成功");
			}
		} catch (Exception e) {
			result.setErrorMsg("失败:系统错误!");
		}
		return result;
	}
	//*****************************投即送晒单*****************
	
	/**
	 * 跳转到放款管理页面
	 */
	@RequestMapping("/toJsProductPrizeList")
	public String toDrProductLoanList(Map<String,Object> model) {
		/*try {
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productStatus")));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return "system/product/jsProductPrizeList";
	}
	/**
	 * 显示列表
	 */
	@RequestMapping("/jsProductPrizeList")
	@ResponseBody
	public PageInfo drProductLoanList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = jsproductPrizeService.getJsProductPrizeList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * 复制并新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/copyAddPrizeLog" ,method=RequestMethod.POST)
	@ResponseBody
	public String copyAddPrize(@RequestParam Map<String,Object> model){
		BaseResult br = new BaseResult();
		try {
			jsproductPrizeService.copyAddPrize(Integer.parseInt(model.get("id").toString()));
			br.setSuccess(true);
			br.setMsg("复制并新增成功！");
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorMsg("复制并新增失败!");
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	/**
	 * 添加产品信息
	 * @param req
	 * @param DrCarryParameter
	 * @param name
	 * @param claimsFiles
	 * @return
	 */
	@RequestMapping(value="/addJsProductPrize",produces = "text/html; charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String addJsProductPrize(JsProductPrize jsProductPrize,HttpServletRequest request,
			MultipartFile pcV,MultipartFile pcH,MultipartFile h5V,MultipartFile h5H,MultipartFile pcDetail,MultipartFile h5Detail){
		BaseResult br = new BaseResult();
		SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		try {
			
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
			Pattern pattern = Pattern.compile(reg);
			Map<String,Object> fileMap = new HashMap<String,Object>();
			List<MultipartFile> list = new ArrayList<MultipartFile>();
			if(pcV != null){
				fileMap.put("pcImgUrlV", pcV);
				list.add(pcV);
			}
			if(pcH != null){
				fileMap.put("pcImgUrlH", pcH);
				list.add(pcH);
			}
			if(h5V != null){
				fileMap.put("h5ImgUrlV", h5V);
				list.add(h5V);
			}
			if(h5H != null){
				fileMap.put("h5ImgUrlH", h5H);
				list.add(h5H);
			}
			if(pcDetail != null){
				list.add(pcDetail);
			}
			if(h5Detail != null){
				list.add(h5Detail);
			}
			
			for(int i=0;i<list.size();i++){
				Matcher matcher = pattern.matcher(list.get(i).getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = list.get(i).getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			jsProductPrize.setAddUser(usersVo.getUserKy().intValue());
//			jsProductPrize.setAddUser(12);
			jsProductPrize.setAddTime(new Date());
			br = jsproductPrizeService.insertJsProductPrize(jsProductPrize, pcV,pcH,h5V,h5H,pcDetail,h5Detail);
		} catch (Exception e) {
			log.error("添加失败："+e);
			br.setErrorMsg("添加失败!");
			br.setSuccess(false);
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 跳转到新增
	 */
	@RequestMapping("/toAddDrProductPrize")
	public String toAddDrProductPrize(Map<String,Object> model,HttpServletRequest request) {
		try {
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productPrizeStatus")));
			model.put("category", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("category")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "system/product/addJsProductPrize";
	}
	

	/**
	 * 跳转到更新
	 */
	@RequestMapping("/toUpdateDrProductPrize")
	public String toUpdateDrProductPrize(Map<String,Object> model,HttpServletRequest request,Integer id) {
		try {
			@SuppressWarnings("unused")
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			model.put("status", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("productPrizeStatus")));
			model.put("category", ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("category")));
			model.put("drProductPrize", jsproductPrizeService.getJsProductPrizeById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/product/updateJsProductPrize";
	}
	/**
	 * 更新
	 */
	@RequestMapping(value="/updateDrProductPrize",produces = "text/html; charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String updateDrProductPrize(JsProductPrize jsProductPrize,HttpServletRequest request,
			MultipartFile pcV,MultipartFile pcH,MultipartFile h5V,MultipartFile h5H,MultipartFile pcDetail,MultipartFile h5Detail) {
		BaseResult br = new BaseResult();
		try {
			/**
			 * 对奖品更新的时候，必须是产品不是募集中的情况下
			 */
			DrProductInfo drProductInfo = drProductInfoService.getDrProductInfoByPrizeId(jsProductPrize.getId());
//			if(Utils.isObjectNotEmpty(drProductInfo) && drProductInfo.getStatus() == 5 ){
//				br.setSuccess(false);
//				br.setErrorMsg("该奖品已和募集中的产品绑定，无法修改");
//				JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
//				return jsonObject.toString();
//			}
			
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
			Pattern pattern = Pattern.compile(reg);
			List<MultipartFile> list = new ArrayList<MultipartFile>();
			if(pcV != null){
				list.add(pcV);
			}
			if(pcH != null){
				list.add(pcH);
			}
			if(h5V != null){
				list.add(h5V);
			}
			if(h5H != null){
				list.add(h5H);
			}
			if(pcDetail != null){
				list.add(pcDetail);
			}
			if(h5Detail != null){
				list.add(h5Detail);
			}
			
			for(int i=0;i<list.size();i++){
				Matcher matcher = pattern.matcher(list.get(i).getOriginalFilename().toLowerCase());
				if(!matcher.find()){
					br.setSuccess(false);
					br.setErrorMsg("请上传正确的图片格式!");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
				
				long fileSize = list.get(i).getSize();
				if(fileSize>1024*5000){
					br.setSuccess(false);
					br.setErrorMsg("图片不能大于5M！");
					JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
					return jsonObject.toString();
				}
			}
			SysUsersVo usersVo= (SysUsersVo) request.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
			jsProductPrize.setUpdateTime(new Date());
			jsProductPrize.setUpdateUser(usersVo.getUserKy().intValue());
			br = jsproductPrizeService.updateJsProductPrize(jsProductPrize, pcV,pcH,h5V,h5H,pcDetail,h5Detail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	/**
	 * 显示晒单列表
	 */
	@RequestMapping("/selectOrderShare")
	@ResponseBody
	public PageInfo selectOrderShare(Integer page,Integer rows,
			String isShow,String mobilePhone,String startAddtime,String endAddtime){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		Map<String, Object> param=new HashMap<>();
		param.put("isShow", isShow);
		param.put("mobilePhone", mobilePhone);
		param.put("startAddtime", startAddtime);
		param.put("endAddtime", endAddtime);
		BaseResult result = jsproductPrizeService.selectOrderShare(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	
	/**
	 * 跳转到晒单
	 */
	@RequestMapping("/toOrderShare")
	public String toOrderShare(){
		return "system/product/orderShareList";
	}
	
	
	@RequestMapping("/updateOrderShare")
	@ResponseBody
	public BaseResult updateOrderShare(Integer id,Integer sort,Integer isShow,String remark) throws SQLException{
		JsProductPrizeOrderShare obj=new JsProductPrizeOrderShare();
		obj.setSort(sort);
		obj.setRemark(remark);
		obj.setIsShow(isShow);
		obj.setId(id);
		BaseResult br=jsproductPrizeService.updateOrderShare(obj);
		return br;
	}
	
	@RequestMapping("/exportJsProductPrize")
	public ModelAndView exportJsProductPrize(String isShow,String mobilePhone,String startAddtime,String endAddtime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isShow", isShow);
		param.put("mobilePhone", mobilePhone);
		param.put("startAddtime", startAddtime);
		param.put("endAddtime", endAddtime);
		
		PageInfo pi = new PageInfo(1,Integer.MAX_VALUE);
		BaseResult result = jsproductPrizeService.selectOrderShare(param, pi);
		List<Map<String, Object>> rowsList = (List<Map<String, Object>>)pi.getRows();
		String[] title = new String[]{"用户名","TA说","审核状态","时间"};
		List<List<Object>> tableList = new ArrayList<List<Object>>();
		List<Object> lc = null;
		for(Map<String, Object> map:rowsList){
			lc = new ArrayList<Object>();
			lc.add(map.get("mobilePhone"));
			lc.add(map.get("describes"));
			/*lc.add(map.get("url"));*/
			lc.add(map.get("isShow").equals(0)?"不显示":map.get("isShow").equals(1)?"显示":map.get("isShow").equals(2)?"待审核":map.get("isShow"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			lc.add(formatter.format(map.get("addtime")));
			tableList.add(lc);
		}
		param.clear();
		param.put("excelName", "product_prize_order_share"+System.currentTimeMillis()+".xls");
		param.put("titles", title);
		param.put("list", tableList);
		return new ModelAndView(new JXLExcelView(), param);
	}
	
	/**
	 * 根据商品ID查询募集中的产品条数
	 * @param id
	 * @return
	 */
	@RequestMapping("/getProductPrize")
	@ResponseBody
	public BaseResult getProductPrize(Integer id){
		BaseResult br=new BaseResult();
		int count=drProductInfoService.getProductPrize(id);
		if(count==0){//没有募集中的产品 商品可以下架
			br.setSuccess(true);
		}else{
			br.setSuccess(false);
		}
		return br;
	}
	
	/**
	 * 下架产品
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteProductPrize")
	@ResponseBody
	public BaseResult updateProductPrize(Integer id)throws SQLException{
		BaseResult br=new BaseResult();
		JsProductPrize jsProductPrize=new JsProductPrize();
		jsProductPrize.setStatus(2);
		jsProductPrize.setId(id);
		jsproductPrizeService.deleteProductPrize(jsProductPrize);
		br.setSuccess(true);
		return br;
	}
	
}