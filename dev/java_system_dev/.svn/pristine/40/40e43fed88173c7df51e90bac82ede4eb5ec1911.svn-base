package com.jsjf.service.activity.impl;

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
import com.jsjf.common.PageInfo;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsActivityAggregationPageDAO;
import com.jsjf.model.activity.JsActivityAggregationPage;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.JsActivityAggregationPageService;

@Service
public class JsActivityAggregationPageServiceImpl implements JsActivityAggregationPageService {
	
	@Autowired
	private JsActivityAggregationPageDAO jsActivityAggregationPageDAO;

	@Override
	public BaseResult selectJsActivityAggregationPageList(JsActivityAggregationPage jsActivityAggregationPage, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title", jsActivityAggregationPage.getTitle());
		map.put("status", jsActivityAggregationPage.getStatus());
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<JsActivityAggregationPage> list = jsActivityAggregationPageDAO.selectJsActivityAggregationPageList(map);
		Integer total = jsActivityAggregationPageDAO.getJsActivityAggregationPageCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public void addAggregationPage(JsActivityAggregationPage jsActivityAggregationPage, SysUsersVo usersVo,MultipartFile pcPicFile,MultipartFile appPicFile) throws Exception{
		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsActivityAggregationPage.getId() + "/";
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsActivityAggregationPage.getId() + "/";
		if(pcPicFile !=null){
			jsActivityAggregationPage.setPcPic(savePath+getFilePath(pcPicFile, realPath));
		}
		if(appPicFile !=null){
			jsActivityAggregationPage.setAppPic(savePath+getFilePath(appPicFile, realPath));
		}
		jsActivityAggregationPage.setAddUser(usersVo.getUserKy().intValue());
		jsActivityAggregationPageDAO.insert(jsActivityAggregationPage);
	}

	@Override
	public JsActivityAggregationPage selectJsActivityAggregationPageById(Integer id) {
		return jsActivityAggregationPageDAO.selectJsActivityAggregationPageById(id);
	}

	@Override
	public void updateAggregationPage(JsActivityAggregationPage jsActivityAggregationPage, SysUsersVo usersVo,
			MultipartFile pcPicFile, MultipartFile appPicFile) throws Exception {
		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsActivityAggregationPage.getId() + "/";
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsActivityAggregationPage.getId() + "/";
		if(pcPicFile !=null){
			jsActivityAggregationPage.setPcPic(savePath+getFilePath(pcPicFile, realPath));
		}
		if(appPicFile !=null){
			jsActivityAggregationPage.setAppPic(savePath+getFilePath(appPicFile, realPath));
		}
		jsActivityAggregationPage.setTerminalTypeAndroid(jsActivityAggregationPage.getTerminalTypeAndroid()==null?0:jsActivityAggregationPage.getTerminalTypeAndroid());
		jsActivityAggregationPage.setTerminalTypeH5(jsActivityAggregationPage.getTerminalTypeH5()==null?0:jsActivityAggregationPage.getTerminalTypeH5());
		jsActivityAggregationPage.setTerminalTypeIOS(jsActivityAggregationPage.getTerminalTypeIOS()==null?0:jsActivityAggregationPage.getTerminalTypeIOS());
		jsActivityAggregationPage.setTerminalTypePC(jsActivityAggregationPage.getTerminalTypePC()==null?0:jsActivityAggregationPage.getTerminalTypePC());
		jsActivityAggregationPage.setUpdateUser(usersVo.getUserKy().intValue());
		jsActivityAggregationPageDAO.update(jsActivityAggregationPage);
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


}
