package com.jsjf.service.activity.impl;

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
import com.jsjf.dao.activity.PrizeManageDao;
import com.jsjf.model.activity.BypCommodityBean;
import com.jsjf.service.activity.PrizeManageService;
@Service
@Transactional
public class PrizeManageServiceImpl implements PrizeManageService{
	
	@Autowired
	private PrizeManageDao prizeManageDao;

	@Override
	public BaseResult queryPrizeManageList(Map<String, Object> param,
			PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<BypCommodityBean> list = prizeManageDao.queryPrizeManageList(param);
		Integer total = prizeManageDao.queryPrizeManageListCount(param);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public BaseResult addDrPrizeManage(BypCommodityBean bypCommodityBean) {
		BaseResult br = new BaseResult();
		prizeManageDao.addDrPrizeManage(bypCommodityBean);
		br.setSuccess(true);
		br.setMsg("添加成功");
		return br;
	}

	@Override
	public BypCommodityBean getDrPrizeManage(Integer id) {
		return prizeManageDao.getDrPrizeManage(id);
	}

	@Override
	public BaseResult updateDrPrizeManage(BypCommodityBean bypCommodityBean) {
		BaseResult br = new BaseResult();
		prizeManageDao.updateDrPrizeManage(bypCommodityBean);
		br.setSuccess(true);
		br.setMsg("修改成功");
		return br;
	}

	@Override
	public void updateProductUrl(String prid, MultipartFile appPicFile) {
		BypCommodityBean bean = new BypCommodityBean();
		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.ActivityPic+ Utils.format(new Date(), "yyyy-MM") + "/" ;
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.ActivityPic + Utils.format(new Date(), "yyyy-MM") + "/";
		if(appPicFile !=null){
			bean.setProductUrl(savePath+getFilePath(appPicFile, realPath));
		}
		bean.setPrId(Integer.parseInt(prid));
		prizeManageDao.updateProductUrl(bean);
	}

	/**
	 * 获取名字
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
	public List<BypCommodityBean> queryPrize() {
		return prizeManageDao.queryPrize();
	}

	@Override
	public List<BypCommodityBean> queryHb() {
		return prizeManageDao.queryHb();
	}

}