package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.jsjf.dao.activity.JsActivityOfflineDAO;
import com.jsjf.model.activity.JsActivityExtendPic;
import com.jsjf.model.activity.JsActivityOffline;
import com.jsjf.model.product.DrProductPic;
import com.jsjf.service.activity.JsActivityOfflineService;
@Service
@Transactional
public class JsActivityOfflineServiceImpl implements JsActivityOfflineService {
	@Autowired
	JsActivityOfflineDAO jsActivityOfflineDAO;
	
	
	@Override
	public BaseResult getJsActivityOfflineList(JsActivityOffline jsActivityOffline, PageInfo pi) {
		BaseResult br = new BaseResult();
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> param = new HashMap<String,Object>(); 
		if(Utils.isObjectNotEmpty(jsActivityOffline.getTitle())){
			param.put("title", jsActivityOffline.getTitle());
		}
		if(Utils.isObjectNotEmpty(jsActivityOffline.getStatus())){
			param.put("status", jsActivityOffline.getStatus());
		}
		if(Utils.isObjectNotEmpty(jsActivityOffline.getStartDate())){
			param.put("startDate", jsActivityOffline.getStartDate());
		}
		if(Utils.isObjectNotEmpty(jsActivityOffline.getEndDate())){
			param.put("endDate", jsActivityOffline.getEndDate());
		}
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<JsActivityOffline> list = jsActivityOfflineDAO.getJsActivityOfflineList(param);
		int count = jsActivityOfflineDAO.getJsActivityOfflineCount(param);
		pi.setTotal(count);
		pi.setRows(list);
		resultMap.put("page", pi);
		br.setMap(resultMap);
		return br;
	}


	@Override
	public List<JsActivityExtendPic> selectJsacExtendPicsList(int extendId,
			int type) {
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("extendId", extendId);
		map.put("type", type);
		map.put("status", 1);
		return jsActivityOfflineDAO.selectJsActivityExtendPicsList(map);
	}


	@Override
	public JsActivityOffline selectJsActivityOfflineById(int id) {
		
		return jsActivityOfflineDAO.selectJsActivityOfflineById(id);
	}


	@Override
	public BaseResult addUpdateJsActivityOffline(JsActivityOffline offline,
			HttpServletRequest request, int isAdd, MultipartFile[] extend,
			MultipartFile pcBannerFile, MultipartFile h5BannerFile,
			MultipartFile h5ListBannerFile, MultipartFile imgUrlFile) throws Exception {
		
		BaseResult result = new BaseResult();
		SFtpUtil sftp = new SFtpUtil();
		
		//上传路径和访问路径
		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
		
		//上传图片并保存访问路径
		if(Utils.isObjectNotEmpty(pcBannerFile)){//
			offline.setPcBanner(savePath+upLoadFile(sftp, pcBannerFile, realPath, savePath));
		}
		if(Utils.isObjectNotEmpty(h5BannerFile)){
			offline.setH5Banner(savePath+upLoadFile(sftp, h5BannerFile, realPath, savePath));
		}
		if(Utils.isObjectNotEmpty(h5ListBannerFile)){
			offline.setH5ListBanner(savePath+upLoadFile(sftp, h5ListBannerFile, realPath, savePath));
		}
		if(Utils.isObjectNotEmpty(imgUrlFile)){
			offline.setImgUrl(savePath+upLoadFile(sftp, imgUrlFile, realPath, savePath));
		}
		
		//添加or修改
		if(isAdd == 1){
			jsActivityOfflineDAO.insertJsActivityOffline(offline);
		}else{
			jsActivityOfflineDAO.updateJsActivityOffline(offline);
		}
		List<JsActivityExtendPic> list =  offline.getJsActivityExtendPic();
		
		//添加or修改 扩展图片
		if(Utils.isEmptyList(list)){
			if( isAdd !=1){
				jsActivityOfflineDAO.deleteJsActivityExtendPicByExtendIdAndType(offline.getId(),1);
			}
		}else{
			List<JsActivityExtendPic> useExtendPic = new ArrayList<JsActivityExtendPic>();
			for(JsActivityExtendPic pic : list){
				if("use".equals(pic.getImgUrl())){
					useExtendPic.add(pic);
				}
			}
			if(extend.length > 0){
				for(int i=0;i<extend.length;i++){					
					if(useExtendPic.get(i).getId() != null){
						useExtendPic.get(i).setImgUrl(savePath + offline.getId() + "/"+upLoadFile(sftp, extend[i], realPath + offline.getId() + "/", savePath));
						
						jsActivityOfflineDAO.updateJsActivityExtendPic(useExtendPic.get(i));
					}else{
						useExtendPic.get(i).setStatus(1);
						useExtendPic.get(i).setExtendId(offline.getId());
						useExtendPic.get(i).setType(1);
						useExtendPic.get(i).setImgUrl(savePath + offline.getId() + "/"+upLoadFile(sftp, extend[i], realPath + offline.getId() + "/", savePath));
						jsActivityOfflineDAO.insertJsActivityExtendPic(useExtendPic.get(i));
					}
				}
			}else if(isAdd ==0){
				for(JsActivityExtendPic extendPic : list){
					if(extendPic.getId() != null){
						jsActivityOfflineDAO.updateJsActivityExtendPic(extendPic);
					}
				}
			}
		}
		result.setMsg("成功");
		result.setSuccess(true);
		return result;
	}
	/**
	 * 上传图片并返回地址
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	private String upLoadFile(SFtpUtil sftp,MultipartFile multipartFile,String realPath,String savePath) throws Exception{
		String imageName = ImageUtils.getServerFileName()
				+ multipartFile.getOriginalFilename().substring(
						multipartFile.getOriginalFilename().lastIndexOf("."));
		sftp.connectServer();
		sftp.put(multipartFile.getInputStream(), realPath, imageName);
		sftp.closeServer();		
		return imageName;
	}


	@Override
	public void deleteJsacExtendPic(int id) {
		jsActivityOfflineDAO.deleteJsActivityExtendPicById(id);
	}


	@Override
	public void update(JsActivityOffline offline) {
		jsActivityOfflineDAO.updateJsActivityOffline(offline);
	}

}
