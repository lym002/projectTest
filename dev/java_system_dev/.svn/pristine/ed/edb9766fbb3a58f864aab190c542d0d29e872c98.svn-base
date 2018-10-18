package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ImageUtils;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.ActivityTemplateDAO;
import com.jsjf.dao.member.DrMemberMsgDAO;
import com.jsjf.model.activity.ActivityPrize;
import com.jsjf.model.activity.ActivityTemplate;
import com.jsjf.model.member.DrMemberMsg;
import com.jsjf.service.activity.ActivityTemplateService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
public class ActivityTemplateServiceImpl implements ActivityTemplateService {

	@Autowired 
	private ActivityTemplateDAO activityTemplateDAO;
	@Autowired 
	private DrMemberMsgDAO drMemberMsg;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Override
	public List<Map<String, Object>> selActivityTemplate(
			Map<String, Object> param) {
		return activityTemplateDAO.selActivityTemplate(param);
	}

	@Override
	public int selActivityTemplateCount(Map<String, Object> param) {
		return activityTemplateDAO.selActivityTemplateCount(param);
	}

	@Override
	public void insert(ActivityTemplate activityTemplate) {
		 activityTemplateDAO.insert(activityTemplate);
	}

	@Override
	public void insertPrize(ActivityPrize activityPrize) {
		activityTemplateDAO.insertPrize(activityPrize);
	}

	@Override
	public List<Map<String, Object>> selActivityPrize(Map<String, Object> param) {
		return activityTemplateDAO.selActivityPrize(param);
	}

	@Override
	public void delActivityPrize(Map<String, Object> param) {
		activityTemplateDAO.delActivityPrize(param);
	}

	@Override
	public void updateActivityTemplate(ActivityTemplate activityTemplate) {
		activityTemplateDAO.updateActivityTemplate(activityTemplate);
	}

	@Override
	public List<Map<String, Object>> selActivityTemplateAll() {
		return activityTemplateDAO.selActivityTemplateAll();
	}

	@Override
	public List<Map<String, Object>> selActivityProductAll(
			Map<String, Object> param) {
		return activityTemplateDAO.selActivityProductAll(param);
	}

	@Override
	public int selActivityProductAllCount(Map<String, Object> param) {
		return activityTemplateDAO.selActivityProductAllCount(param);
	}

	@Override
	public BaseResult addActivityProduct(MultipartFile acceptPicFile,String prizeCode,String id,String prizeUrl)
			throws Exception {
		BaseResult br = new BaseResult();
		Map<String,Object> map=new HashMap();
		SFtpUtil sftp = new SFtpUtil();
		if(Utils.isObjectNotEmpty(acceptPicFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ acceptPicFile.getOriginalFilename().substring(
							acceptPicFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(acceptPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			map.put("prizeImgUrl", savePath+imageName);
		}
		map.put("prizeCode", prizeCode);
		map.put("id", id);
		map.put("prizeUrl", prizeUrl);
		activityTemplateDAO.updateActivityProduct(map);
		
		List<Map<String, Object>> list=activityTemplateDAO.selActivityProduct(map);
		
		String[] ary = prizeCode.split(",");//调用API方法按照逗号分隔字符串 
		for(String item: ary){
			Map<String,Object> m=new HashMap();
			m.put("pid", list.get(0).get("pid"));//获取产品id
			m.put("prizeCode", item);//中奖code
			activityTemplateDAO.updateActivityStatusOne(m);	//未中奖
			activityTemplateDAO.updateActivityStatusTwo(m); //中奖啦
		}
		
		//给中奖人发站内信
		map.clear();
		map.put("id", Integer.parseInt(id));
		List<Map<String, Object>> listMap =  activityTemplateDAO.selectPrizeMember(map);
		if(!Utils.isEmptyList(listMap)){
			DrMemberMsg dms = new DrMemberMsg();
			String msg = PropertyUtil.getProperties("iphone7msg").replace("${activityPeriods}", listMap.get(0).get("activityPeriods").toString())
							.replace("${prizeCode}",prizeCode);
			for(Map<String,Object> m : listMap){
				dms = new DrMemberMsg(Integer.parseInt(m.get("uid").toString()), 0, 2, "投资白送iphone7（第"+m.get("activityPeriods").toString()+"期）抽奖结果公布", new Date(), 0, 0, msg);
				drMemberMsg.insertDrMemberMsg(dms);
			}
		}
		
		br.setMsg("提交成功!");
		br.setSuccess(true);
		return br;
	}

	@Override
	public List<Map<String, Object>> selActivityProductById(
			Map<String, Object> param) {
		return activityTemplateDAO.selActivityProductById(param);
	}

	@Override
	public List<Map<String, Object>> selActivityPrizeById(
			Map<String, Object> param) {
		return activityTemplateDAO.selActivityPrizeById(param);
	}

	@Override
	public List<Map<String, Object>> selProductInvestById(
			Map<String, Object> param) {
		return activityTemplateDAO.selProductInvestById(param);
	}

	@Override
	public int selProductInvestByIdCount(Map<String, Object> param) {
		return activityTemplateDAO.selProductInvestByIdCount(param);
	}

	
	@Override
	public BaseResult addInvest(MultipartFile acceptPicFile,String prizeContent,String id,String prizeMobile,String prizeVideoLink,String prizeVideoUrl,MultipartFile headFile)
			throws Exception {
		BaseResult br = new BaseResult();
		Map<String,Object> map=new HashMap();
		SFtpUtil sftp = new SFtpUtil();
		if(Utils.isObjectNotEmpty(acceptPicFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ acceptPicFile.getOriginalFilename().substring(
							acceptPicFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(acceptPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			map.put("prizeImgUrl", savePath+imageName);
		}
		if(Utils.isObjectNotEmpty(headFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ headFile.getOriginalFilename().substring(
							headFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(headFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			map.put("prizeHeadPhoto", savePath+imageName);
		}
		map.put("prizeContent", prizeContent);
		map.put("id", id);
		map.put("isUplod", 1);
		map.put("prizeMobile", prizeMobile);
		map.put("prizeVideoLink", prizeVideoLink);
		map.put("prizeVideoUrl", prizeVideoUrl);
		activityTemplateDAO.addInvest(map);
		br.setMsg("修改成功!");
		br.setSuccess(true);
		return br;
	}

	@Override
	public List<Map<String, Object>> selSumCount(Map<String, Object> param) {
		return activityTemplateDAO.selSumCount(param);
	}

	@Override
	public int selInvestByCode(Map<String, Object> param) {
		return activityTemplateDAO.selInvestByCode(param);
	}

	@Override
	public List<Map<String, Object>> exportData(Map<String, Object> param) {
		List<Map<String, Object>> list =activityTemplateDAO.exportData(param);
		return list;
	}

	@Override
	public void updateActivityProductAppTitle(String appTitle, String productId) {
		Map<String,Object> map=new HashMap();
		map.put("id", productId);
		map.put("appTitle", appTitle);
		activityTemplateDAO.updateActivityProductAppTitle(map);
	}

	@Override
	public BaseResult updateActivityProductDetailImg(MultipartFile detailFile, String productId) throws Exception {
		BaseResult br = new BaseResult();
		Map<String,Object> map=new HashMap<String,Object>();
		SFtpUtil sftp = new SFtpUtil();
		String path = "";
		if(Utils.isObjectNotEmpty(detailFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ detailFile.getOriginalFilename().substring(
							detailFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(detailFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			map.put("pcDetailImg", savePath+imageName);
			path = savePath+imageName;
		}else{
			Map<String,Object> activityTemplate = activityTemplateDAO.getActivityProductByPid(Integer.valueOf(productId));
			if(Utils.isObjectEmpty(activityTemplate.get("pcDetailImg"))){
				map.put("pcDetailImg", redisClientTemplate.getProperties("activityImg"));
			}
			path = redisClientTemplate.getProperties("activityImg");
		}
		map.put("id", productId);
		activityTemplateDAO.updateActivityProductDetailImg(map);
		br.setMsg(path);
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult updateActivityDetailBanner(MultipartFile pcBannerFile, MultipartFile h5BannerFile, String productId)
			throws Exception {
		BaseResult br = new BaseResult();
		Map<String,Object> map=new HashMap<String,Object>();
		SFtpUtil sftp = new SFtpUtil();
		String pcBannerUrl = "";
		String h5BannerUrl = "";
		Map<String, Object> resultMap = new HashMap<>();
		if(Utils.isObjectNotEmpty(pcBannerFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ pcBannerFile.getOriginalFilename().substring(
							pcBannerFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(pcBannerFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			map.put("pcBannerUrl", savePath+imageName);
			pcBannerUrl = savePath+imageName;
			resultMap.put("pcBannerUrl", pcBannerUrl);
		}else{
			Map<String,Object> activityTemplate = activityTemplateDAO.getActivityProductByPid(Integer.valueOf(productId));
			if(Utils.isObjectEmpty(activityTemplate.get("pcBannerUrl"))){
				map.put("pcBannerUrl", redisClientTemplate.getProperties("activityPCBannerImg"));
			}
			pcBannerUrl = redisClientTemplate.getProperties("activityPCBannerImg");
		}
		if(Utils.isObjectNotEmpty(h5BannerFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ h5BannerFile.getOriginalFilename().substring(
							h5BannerFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(h5BannerFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			map.put("h5BannerUrl", savePath+imageName);
			h5BannerUrl = savePath+imageName;
			resultMap.put("h5BannerUrl", h5BannerUrl);
		}else{
			Map<String,Object> activityTemplate = activityTemplateDAO.getActivityProductByPid(Integer.valueOf(productId));
			if(Utils.isObjectEmpty(activityTemplate.get("h5BannerUrl"))){
				map.put("h5BannerUrl", redisClientTemplate.getProperties("activityH5BannerImg"));
			}
			h5BannerUrl =redisClientTemplate.getProperties("activityH5BannerImg");
		}
		map.put("id", productId);
		activityTemplateDAO.updateActivityDetailBanner(map);
		br.setSuccess(true);
		br.setMap(resultMap);
		return br;
	}
}
