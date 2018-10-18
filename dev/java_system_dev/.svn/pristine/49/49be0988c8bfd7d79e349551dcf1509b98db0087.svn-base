package com.jsjf.controller.store;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.FileUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.store.CommodityExchange;
import com.jsjf.model.store.CommodityRepertory;
import com.jsjf.service.store.CommodityExchangeServer;
import com.jsjf.service.store.CommodityRepertoryService;

/**
 * 商品兑换管理
 * @author cece
 *
 */
@Controller
@RequestMapping("/commodity")
public class CommodityExchangeController {
	
	@Autowired
	private CommodityExchangeServer commodityExchangeServer;
	
	@Autowired
	private CommodityRepertoryService commodityRepertoryService;
	
	private Logger log = Logger.getLogger(CommodityExchangeController.class);
	
	@RequestMapping("/exchangeView")
	public String commodityClassView(Map<String,Object> model){
		List<CommodityRepertory> list = commodityRepertoryService.queryRep();
		model.put("rep", list);
		return "system/store/commodityExchangeView";
	}
	
	@RequestMapping(value="/commodityExchange",method=RequestMethod.GET)
	@ResponseBody
	public PageInfo queryCommodityExchangeList(@RequestParam Map<String,Object> param,Integer page,Integer rows){
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = commodityExchangeServer.queryCommodityExchangeList(param, pi);
		return (PageInfo)result.getMap().get("page");
	}
	
	@RequestMapping(value="/commodityExchange",method=RequestMethod.POST)
	@ResponseBody
	public BaseResult addCommodityExchange(HttpServletRequest request,CommodityExchange bean){
		BaseResult br = new BaseResult();
		br = commodityExchangeServer.addCommodityExchange(bean);
		return br;
	}
	
	@RequestMapping(value="/commodityExchange",method=RequestMethod.PUT)
	@ResponseBody
	public BaseResult updateCommodityExchange(CommodityExchange bean){
		BaseResult br = new BaseResult();
		br = commodityExchangeServer.updateCommodityExchange(bean);
		return br;
	}
	
	@RequestMapping(value="/commodityExchange",method=RequestMethod.DELETE)
	@ResponseBody
	public BaseResult deleteCommodityClass(HttpServletRequest req,int id) {
		BaseResult br = new BaseResult();
		br = commodityExchangeServer.deleteCommodityExchange(id);
		return br;
	}
	
	@RequestMapping(value="/commodityExchange/imp",method=RequestMethod.POST,produces = "text/html; charset=utf-8")
	@ResponseBody
	public String importCommodity(@RequestParam(value="filename") MultipartFile bannerAddPicFile) {
		BaseResult br = new BaseResult();
		String reg = ".+(.xlsx)$";
        Pattern pattern = Pattern.compile(reg);
        if(Utils.isObjectNotEmpty(bannerAddPicFile)){
        	Matcher matcher = pattern.matcher(bannerAddPicFile.getOriginalFilename().toLowerCase());
        	if(!matcher.find()){
        		br.setSuccess(false);
        		br.setErrorCode("9999");
        		br.setMsg("请上传正确的07版及以上的excel文件!");
        		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
        		return jsonObject.toString();
        	}
            String name=bannerAddPicFile.getOriginalFilename();
            //批量导入
            try {
            	br = commodityExchangeServer.addImportCommodity(name,bannerAddPicFile);
			}catch(DuplicateKeyException e){
				br.setSuccess(false);
				br.setMsg("卡密已存在："+e.getMessage());
				br.setErrorCode("9999");
				log.error("卡密已存在", e);
			}catch (Exception e) {
				br.setSuccess(false);
				br.setMsg("请选择上传文件");
				br.setErrorCode("9999");
				log.error("上传失败", e);
			}
        }else{
        	br.setSuccess(false);
			br.setMsg("请选择上传文件");
			br.setErrorCode("9999");
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(br);
		return jsonObject.toString();
	}
	
	
	@RequestMapping("/downloadModel")
	@ResponseBody
	public void downloadModel(HttpServletResponse response,HttpServletRequest request){
		try {
			String filePath = this.getClass().getResource("/").getPath();
			String filename = "/resources/template/commodity.xlsx";
			File file = new File(filePath +"/"+ filename);
			
			File fileTemp = new File(filePath + "/" + "/resources/template/commodity_temp.xlsx");
			FileUtils.copyFile(file, fileTemp);
			//添加部分用户数据
			FileUtil.download(fileTemp,response);
			if(fileTemp.exists()){
				fileTemp.delete();
			}
		}catch (Exception e) {
			log.error("模板下载失败",e);
		}
	}
	
}
