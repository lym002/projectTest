package com.jsjf.service.article.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.jsjf.dao.article.SysArticleDAO;
import com.jsjf.dao.article.SysProgramDAO;
import com.jsjf.model.article.SysArticle;
import com.jsjf.model.article.SysProgram;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.article.SysArticleService;

@Service
@Transactional
public class SysArticleServiceImpl implements SysArticleService {

	@Autowired
	public SysArticleDAO sysArticleDAO;
	@Autowired
	public SysProgramDAO sysProgramDAO;

	@Override
	public BaseResult getArticleList(SysArticle sysArticle, PageInfo pi) {
		Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_status", 1);//栏目有效的文章才展示
		map.put("ishead", sysArticle.getIshead());
		map.put("proId", sysArticle.getProId());
		map.put("title", sysArticle.getTitle());
		map.put("startDate",
				Utils.format(sysArticle.getStartDate(), "yyyy-MM-dd"));
		map.put("endDate", Utils.format(sysArticle.getEndDate(), "yyyy-MM-dd"));
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<SysArticle> list = sysArticleDAO.getSysArticleList(map);
		Integer total = sysArticleDAO.getSysArticleCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public void addArticle(SysArticle sysArticle, SysUsersVo usersVo,MultipartFile articleAddPicFile) throws Exception{
		if(Utils.isObjectNotEmpty(articleAddPicFile)){
			String imageName = ImageUtils.getServerFileName()
					+ articleAddPicFile.getOriginalFilename().substring(
							articleAddPicFile.getOriginalFilename().lastIndexOf("."));

			String realPath = ConfigUtil.getImgFileUrl()+ ConfigUtil.articleImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname()+ ConfigUtil.articleImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
			SFtpUtil sftp = new SFtpUtil();
			sftp.connectServer();
			sftp.put(articleAddPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			
			sysArticle.setLitpic(savePath+imageName);
		}
		sysArticle.setType((short) 1);
		sysArticle.setStatus(1);// 默认为有效
		sysArticle.setUid(0);
		sysArticle.setCreateUser(usersVo.getUserKy().shortValue());
		sysArticleDAO.insertSysArticle(sysArticle);
	
	}

	@Override
	public SysArticle selectSysArticleById(SysArticle sysArticle) {
		SysArticle article = sysArticleDAO.selectSysArticleById(sysArticle);
		return article;
	}

	@Override
	public void updateArticle(SysArticle sysArticle, SysUsersVo usersVo,MultipartFile articleUpdPicFile)throws Exception {
		if(Utils.isObjectNotEmpty(articleUpdPicFile)){
			SFtpUtil sftp = new SFtpUtil();
			sftp.connectServer();
			String realPath = ConfigUtil.getImgFileUrl()+ ConfigUtil.articleImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname()+ ConfigUtil.articleImgPath + Utils.format(new Date(), "yyyy-MM") + "/";
			String oldFileName = sysArticleDAO.selectSysArticleById(sysArticle).getLitpic();
			if(!StringUtils.isBlank(oldFileName)){
				sftp.delete(realPath, oldFileName.substring(oldFileName.lastIndexOf("/")+1));
			}		
			
			String imageName = ImageUtils.getServerFileName()
					+ articleUpdPicFile.getOriginalFilename().substring(
							articleUpdPicFile.getOriginalFilename().lastIndexOf("."));
			sftp.put(articleUpdPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			sysArticle.setLitpic(savePath+imageName);
		}
		sysArticle.setUpdateUser(usersVo.getUserKy().shortValue());
		sysArticleDAO.updateSysArticleById(sysArticle);
	}

	@Override
	public BaseResult deleteArticle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		try {
			sysArticle.setStatus(0);
			sysArticleDAO.updateSysArticleById(sysArticle);
			result.setSuccess(true);
			result.setMsg("删除成功！");
		} catch (Exception e) {
			result.setErrorMsg("删除失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BaseResult isrecommendArticle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		try {
			sysArticle.setIsrecommend(1);
			sysArticleDAO.updateSysArticleById(sysArticle);
			result.setSuccess(true);
			result.setMsg("推荐成功！");
		} catch (Exception e) {
			result.setErrorMsg("推荐失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BaseResult recoverAtricle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		try {
			sysArticle.setStatus(1);
			sysArticleDAO.updateSysArticleById(sysArticle);
			result.setSuccess(true);
			result.setMsg("恢复成功！");
		} catch (Exception e) {
			result.setErrorMsg("恢复失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BaseResult refuseAtricle(SysArticle sysArticle) {
		BaseResult result = new BaseResult();
		try {
			sysArticle.setStatus(2);
			sysArticleDAO.updateSysArticleById(sysArticle);
			result.setSuccess(true);
			result.setMsg("拒绝成功！");
		} catch (Exception e) {
			result.setErrorMsg("拒绝失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<SysProgram> getAllSysProgram() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		return sysProgramDAO.selectSysProgramById(map);
	}
}
