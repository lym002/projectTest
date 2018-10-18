package com.jsjf.service.article.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jsjf.dao.article.SysBannerDAO;
import com.jsjf.model.article.SysBanner;
import com.jsjf.service.article.SysBannerService;

@Service
@Transactional
public class SysBannerServiceImpl implements SysBannerService {

	@Autowired
	public SysBannerDAO sysBannerDAO;

	@Override
	public BaseResult getSysBannerList(SysBanner sysBanner, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("code", sysBanner.getCode());
		map.put("status", sysBanner.getStatus());
		map.put("title", sysBanner.getTitle());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<SysBanner> list = sysBannerDAO.getSysBannerList(map);
		Integer total = sysBannerDAO.getSysBannerCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public void addSysBanner(SysBanner sysBanner,MultipartFile bannerAddPicFile)throws Exception{
		if(Utils.isObjectNotEmpty(bannerAddPicFile)){
			String imageName = ImageUtils.getServerFileName()
					+ bannerAddPicFile.getOriginalFilename().substring(
							bannerAddPicFile.getOriginalFilename().lastIndexOf("."));

			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.bannerImgPath + Utils.format(new Date(), "yyyy-MM") + "/"; 
			String savePath = ConfigUtil.getDomainname()+ ConfigUtil.bannerImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
			SFtpUtil sftp = new SFtpUtil();
			sftp.connectServer();
			sftp.put(bannerAddPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			
			sysBanner.setImgUrl(savePath+imageName);
		}
		sysBannerDAO.insertSysBanner(sysBanner);
	}

	@Override
	public void updateSysBanner(SysBanner sysBanner,MultipartFile bannerUpdPicFile)throws Exception {
		if(Utils.isObjectNotEmpty(bannerUpdPicFile)){
			SFtpUtil sftp = new SFtpUtil();
			sftp.connectServer();
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.bannerImgPath + Utils.format(new Date(), "yyyy-MM") + "/"; 
			String savePath = ConfigUtil.getDomainname()+ ConfigUtil.bannerImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
			File file = new File(realPath);
			if(!file.exists()){
				file.mkdirs();
			}
			String oldFileName = sysBannerDAO.selectSysBannerById(sysBanner).getImgUrl();
			if(!Utils.strIsNull(oldFileName)){
				sftp.delete(realPath, oldFileName.substring(oldFileName.lastIndexOf("/")+1));
			}
			
			
			String imageName = ImageUtils.getServerFileName()
					+ bannerUpdPicFile.getOriginalFilename().substring(
							bannerUpdPicFile.getOriginalFilename().lastIndexOf("."));
			sftp.put(bannerUpdPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			
			sysBanner.setImgUrl(savePath+imageName);
		}
		sysBannerDAO.updateSysBannerById(sysBanner);
	}

	@Override
	public BaseResult deleteSysBanner(SysBanner sysBanner) {
		BaseResult result = new BaseResult();
		try{
			sysBanner.setStatus(0);
			sysBannerDAO.updateSysBannerById(sysBanner);
			result.setSuccess(true);
			result.setMsg("删除成功！");
		}catch (Exception e) {
			result.setErrorMsg("删除失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BaseResult recoverSysBanner(SysBanner sysBanner) {
		BaseResult result = new BaseResult();
		try{
			sysBanner.setStatus(1);
			sysBannerDAO.updateSysBannerById(sysBanner);
			result.setSuccess(true);
			result.setMsg("恢复成功！");
		}catch (Exception e) {
			result.setErrorMsg("恢复失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public SysBanner selectSysBannerById(SysBanner sysBanner) {
		return sysBannerDAO.selectSysBannerById(sysBanner);
	}

}
