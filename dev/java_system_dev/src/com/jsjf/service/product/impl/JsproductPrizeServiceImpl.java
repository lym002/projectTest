package com.jsjf.service.product.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ImageUtils;
import com.jsjf.common.PageInfo;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.product.JsProductPrizeDAO;
import com.jsjf.dao.product.JsProductPrizeOrderShareDAO;
import com.jsjf.dao.product.JsProductPrizeWishDAO;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.product.JsProductPrize;
import com.jsjf.model.product.JsProductPrizeOrderShare;
import com.jsjf.service.product.JsproductPrizeService;
@Service
@Transactional
public class JsproductPrizeServiceImpl implements JsproductPrizeService {

	@Autowired
	private JsProductPrizeDAO jsProductPrizeDAO;
	@Autowired
	private JsProductPrizeOrderShareDAO jsProductPrizeOrderShareDAO;
	@Autowired
	private JsProductPrizeWishDAO jsProductPrizeWishDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	
	@Override
	public BaseResult insertJsProductPrize(JsProductPrize jsProductPrize, MultipartFile pcImgUrlV, MultipartFile pcImgUrlH, MultipartFile h5ImgUrlV, MultipartFile h5ImgUrlH,
			MultipartFile pcDetail, MultipartFile h5Detail) {
		BaseResult br = new BaseResult();
		try {
			jsProductPrizeDAO.insertJsProductPrize(jsProductPrize);
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsProductPrize.getId() + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsProductPrize.getId() + "/";
			if(pcImgUrlV !=null){
				jsProductPrize.setPcImgUrlV(savePath+getFilePath(pcImgUrlV, realPath));
			}
			if(pcImgUrlH !=null){
				jsProductPrize.setPcImgUrlH(savePath+getFilePath(pcImgUrlH, realPath));
			}
			if(h5ImgUrlV !=null){
				jsProductPrize.setH5ImgUrlV(savePath+getFilePath(h5ImgUrlV, realPath));
			}
			if(h5ImgUrlH !=null){
				jsProductPrize.setH5ImgUrlH(savePath+getFilePath(h5ImgUrlH, realPath));
			}
			if(pcDetail != null){
				jsProductPrize.setPcDetailImgUrl(savePath+getFilePath(pcDetail, realPath));
			}
			if(h5Detail != null){
				jsProductPrize.setH5DetailImgUrl(savePath+getFilePath(h5Detail, realPath));
			}
			jsProductPrizeDAO.updateJsProductPrize(jsProductPrize);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult updateJsProductPrize(JsProductPrize jsProductPrize,
			MultipartFile pcImgUrlV, MultipartFile pcImgUrlH,
			MultipartFile h5ImgUrlV, MultipartFile h5ImgUrlH,
			MultipartFile pcDetail, MultipartFile h5Detail) {
		BaseResult br = new BaseResult();
		try {
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsProductPrize.getId() + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsProductPrize.getId() + "/";
			if(pcImgUrlV !=null){
				jsProductPrize.setPcImgUrlV(savePath+getFilePath(pcImgUrlV, realPath));
			}
			if(pcImgUrlH !=null){
				jsProductPrize.setPcImgUrlH(savePath+getFilePath(pcImgUrlH, realPath));
			}
			if(h5ImgUrlV !=null){
				jsProductPrize.setH5ImgUrlV(savePath+getFilePath(h5ImgUrlV, realPath));
			}
			if(h5ImgUrlH !=null){
				jsProductPrize.setH5ImgUrlH(savePath+getFilePath(h5ImgUrlH, realPath));
			}
			if(pcDetail != null){
				jsProductPrize.setPcDetailImgUrl(savePath+getFilePath(pcDetail, realPath));
			}
			if(h5Detail != null){
				jsProductPrize.setH5DetailImgUrl(savePath+getFilePath(h5Detail, realPath));
			}
			jsProductPrizeDAO.updateJsProductPrize(jsProductPrize);
			br.setSuccess(true);
			br.setMsg("修改成功");
		} catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult getJsProductPrizeList(Map<String,Object> param,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<JsProductPrize> list = jsProductPrizeDAO.getJsProductPrizeList(param);
		Integer total = jsProductPrizeDAO.getJsProductPrizeListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	/**
	 * 上传获取名字
	 */
	public String getFilePath(MultipartFile file,String realPath){
		String imageName = null;
		try {
			SFtpUtil sftp = new SFtpUtil();
			imageName = ImageUtils.getServerFileName()
					+ file.getOriginalFilename().substring(
							file.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(file.getInputStream(), realPath, imageName);
			sftp.closeServer();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return imageName;
	}

	@Override
	public JsProductPrize getJsProductPrizeById(Integer id) {
		return jsProductPrizeDAO.getJsProductPrizeById(id);
	}

	@Override
	public List<Map<String, Object>> getJsProductPrizeforProduct(Integer status) {
		return jsProductPrizeDAO.getJsProductPrizeforProduct(status);
	}

	@Override
	public List<Map<String, Object>> getJsProductPrizeforProductUpdate(
			Integer prizeId) {
		return jsProductPrizeDAO.getJsProductPrizeforProductUpdate(prizeId);
	}

	@Override
	public void copyAddPrize(Integer id) {
		JsProductPrize jsProductPrize = jsProductPrizeDAO.getJsProductPrizeById(id);
		try {
			jsProductPrize.setId(null);
			jsProductPrize.setStatus(0);
			jsProductPrize.setAddTime(new Date());
			jsProductPrizeDAO.insertJsProductPrize(jsProductPrize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BaseResult getJsProductPrizeOrderShareList(
			Map<String, Object> param, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		param.put("orders", " isShow desc,sort desc");
		List<JsProductPrizeOrderShare> list = jsProductPrizeOrderShareDAO.selectObjectList(param);
		Integer total = jsProductPrizeOrderShareDAO.selectObjectCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult insertUpdateJsProductOrderShare(JsProductPrizeOrderShare obj,MultipartFile h5ImgFile
			,MultipartFile pcImgFile,boolean isAdd) {
		BaseResult result = new BaseResult();
		
		if(isAdd){
			jsProductPrizeOrderShareDAO.insert(obj);
		}
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		Map<String,Object> fileMap = new HashMap<String,Object>();
		List<MultipartFile> list = new ArrayList<MultipartFile>();
		if(pcImgFile != null){
			fileMap.put("pcImgFile", pcImgFile);
			list.add(pcImgFile);
		}
		if(h5ImgFile != null){
			fileMap.put("pcImgUrlH", h5ImgFile);
			list.add(h5ImgFile);
		}
		
		for(int i=0;i<list.size();i++){
			Matcher matcher = pattern.matcher(list.get(i).getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				result.setSuccess(false);
				result.setErrorMsg("请上传正确的图片格式!");
				return result;
			}
			
			long fileSize = list.get(i).getSize();
			if(fileSize>1024*5000){
				result.setSuccess(false);
				result.setErrorMsg("图片不能大于5M！");
				return result;
			}
		}
		
		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + obj.getId() + "/";
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + obj.getId() + "/";
		if(pcImgFile !=null){
			obj.setPcImgUrl(savePath+getFilePath(pcImgFile, realPath));
		}
		if(h5ImgFile !=null){
			obj.setH5ImgUrl(savePath+getFilePath(h5ImgFile, realPath));
		}
						
		jsProductPrizeOrderShareDAO.update(obj);
		result.setSuccess(true);
		result.setMsg("success");
		return result;
	}

	@Override
	public JsProductPrizeOrderShare selectJsProductOrderShare(Integer id) {
		return jsProductPrizeOrderShareDAO.selectByPrimaryKey(id);
	}

	@Override
	public void updateJsProductPrizeOrderShare(JsProductPrizeOrderShare obj) {
		jsProductPrizeOrderShareDAO.update(obj);
	}

	@Override
	public BaseResult getJsProductPrizWish(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		param.put("orders", " jw.id desc");
		List<Map<String, Object>> list = jsProductPrizeWishDAO.selectObjectList(param);
		Integer total = jsProductPrizeWishDAO.selectObjectCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<Map<String, Object>> getJsProductPrizWishExport(Map<String, Object> param) {
		param.put("orders", " jw.id desc");
		return  jsProductPrizeWishDAO.selectObjectList(param);
	}

	@Override
	public BaseResult selectOrderShare(Map<String, Object> map,PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String, Object>> list = jsProductPrizeOrderShareDAO.selectOrderShare(map);
		Integer total = jsProductPrizeOrderShareDAO.selectOrderShareCount(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult updateOrderShare(JsProductPrizeOrderShare obj) throws SQLException {
		BaseResult br = new BaseResult();
		JsProductPrizeOrderShare jsProductPrizeOrderShare=jsProductPrizeOrderShareDAO.selectByPrimaryKey(obj.getId());
		if(obj.getIsShow()==1){
			obj.setType(1);
		}
		if(obj.getIsShow()==1 && jsProductPrizeOrderShare.getType()==0){//显示
			Calendar calendar = new GregorianCalendar(); 
			calendar.setTime(new Date()); 
			calendar.add(calendar.DATE,7);
			DrMemberFavourable drMemberFavourable = new DrMemberFavourable(null,jsProductPrizeOrderShare.getUid(), 3,null,"用户晒单-体验金", new BigDecimal("5000"), null , new BigDecimal("0"), 0,calendar.getTime(),"用户晒单-体验金", 0, 1, null, 1);
			drMemberFavourableDAO.insertIntoInfo(drMemberFavourable);
		}
		
		jsProductPrizeOrderShareDAO.update(obj);
		br.setMsg("审核成功");
		br.setSuccess(true);
		return br;
	}

	@Override
	public void deleteProductPrize(JsProductPrize jsProductPrize) throws SQLException {
		jsProductPrizeDAO.updateJsProductPrize(jsProductPrize);
	}


}
