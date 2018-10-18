package com.jsjf.service.store.impl;

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
import com.jsjf.dao.store.CommodityRepertoryDao;
import com.jsjf.model.store.CommodityRepertory;
import com.jsjf.model.vip.VipEquities;
import com.jsjf.service.store.CommodityRepertoryService;

@Service
@Transactional
public class CommodityRepertoryServiceImpl implements CommodityRepertoryService {

	@Autowired
	private CommodityRepertoryDao commodityRepertoryDao;
	
	@Override
	public BaseResult queryCommodityRepertoryList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit());
		List<VipEquities> list = commodityRepertoryDao.queryCommodityRepertoryList(param);
		Integer total = commodityRepertoryDao.queryCommodityRepertoryListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult addCommodityRepertory(CommodityRepertory bean,MultipartFile appPicFile) {
		BaseResult br = new BaseResult();
		try{
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.ActivityPic+ Utils.format(new Date(), "yyyy-MM") + "/" ;
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.ActivityPic + Utils.format(new Date(), "yyyy-MM") + "/";
			if(appPicFile !=null){
				bean.setImgUrl(savePath+getFilePath(appPicFile, realPath));
			}
			bean.setAddDate(new Date());
			commodityRepertoryDao.addCommodityRepertory(bean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}
	
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
	public BaseResult updateCommodityRepertory(CommodityRepertory bean,MultipartFile appPicFile) {
		BaseResult br = new BaseResult();
		try{
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.ActivityPic+ Utils.format(new Date(), "yyyy-MM") + "/" ;
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.ActivityPic + Utils.format(new Date(), "yyyy-MM") + "/";
			if(appPicFile !=null){
				bean.setImgUrl(savePath+getFilePath(appPicFile, realPath));
			}
			bean.setUpdateTime(new Date());
			commodityRepertoryDao.updateCommodityRepertory(bean);
			br.setSuccess(true);
			br.setMsg("修改成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("修改失败！");
		}
		return br;
	}

	@Override
	public BaseResult deleteCommodityClass(int id) {
		BaseResult br = new BaseResult();
		try{
			commodityRepertoryDao.deleteCommodityClass(id);
			br.setSuccess(true);
			br.setMsg("删除成功！");
		}catch(Exception e){
			System.out.println(e);
			br.setSuccess(false);
			br.setMsg("删除失败！");
		}
		return br;
	}

	@Override
	public List<CommodityRepertory> queryRep() {
		return commodityRepertoryDao.queryRep();
	}

}
