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
import com.jsjf.dao.activity.JsOpenDayDao;
import com.jsjf.model.activity.JsOpenDay;
import com.jsjf.service.activity.JsOpenDayService;

@Transactional
@Service
public class JsOpenDayServiceImpl implements JsOpenDayService {
	
	@Autowired
	private JsOpenDayDao jsOpenDayDao; 

	@Override
	public BaseResult insert(JsOpenDay jsOpenDay,MultipartFile img) {
		BaseResult br = new BaseResult();
		try {
			List<JsOpenDay> list = jsOpenDayDao.getOpenDayByStatus(1);//预约中的个数
			if(list.size() >0 && jsOpenDay.getStatus() == 1){
				br.setSuccess(false);
				br.setErrorMsg("预约中的记录只能有一个条存在！");
			}else{
				jsOpenDayDao.insert(jsOpenDay);
				if(img !=null){
					String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsOpenDay.getId() + "/";
					String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsOpenDay.getId() + "/";
					jsOpenDay.setImgUrl(savePath+getFilePath(img, realPath));
				}
				jsOpenDayDao.update(jsOpenDay);
				br.setSuccess(true);
				br.setMsg("添加成功");
			}
		}catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			br.setErrorMsg("添加失败");
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult update(JsOpenDay jsOpenDay,MultipartFile img) {
		BaseResult br = new BaseResult();
		try {
			JsOpenDay vo = jsOpenDayDao.selectByPrimaryKey(jsOpenDay.getId());
			if(vo.getStatus() == 2){
				jsOpenDay.setStatus(vo.getStatus());
			}
			List<JsOpenDay> list  = jsOpenDayDao.getOpenDayByStatus(1);//预约中的个数
			if(list.size()>0 && jsOpenDay.getStatus() == 1 && list.get(0).getId() != jsOpenDay.getId()){
				br.setSuccess(false);
				br.setErrorMsg("预约中的记录只能有一个条存在！");
			}else{
				if(img !=null){
					String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsOpenDay.getId() + "/";
					String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + jsOpenDay.getId() + "/";
					jsOpenDay.setImgUrl(savePath+getFilePath(img, realPath));
				}
				
				jsOpenDayDao.update(jsOpenDay);
				br.setSuccess(true);
				br.setMsg("修改成功");
			}
		}catch (Exception e) {
			br.setErrorCode("9999");
			br.setErrorMsg("修改失败");
			br.setSuccess(false);
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult getOpenDayList(JsOpenDay jsOpenDay, PageInfo pi) {
		BaseResult br = new BaseResult();
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> param = new HashMap<String,Object>(); 
		if(Utils.isObjectNotEmpty(jsOpenDay.getOpenDayName())){
			param.put("openDayName", jsOpenDay.getOpenDayName());
		}
		if(Utils.isObjectNotEmpty(jsOpenDay.getStartDate())){
			param.put("startDate", jsOpenDay.getStartDate());
		}
		if(Utils.isObjectNotEmpty(jsOpenDay.getEndDate())){
			param.put("endDate", jsOpenDay.getEndDate());
		}
		param.put("offset",pi.getPageInfo().getOffset()); 
		param.put("limit",pi.getPageInfo().getLimit()); 
		List<JsOpenDay> list = jsOpenDayDao.getOpenDayList(param);
		int count = jsOpenDayDao.getOpenDayCount(param);
		pi.setTotal(count);
		pi.setRows(list);
		resultMap.put("page", pi);
		br.setMap(resultMap);
		return br;
	}

	@Override
	public JsOpenDay selectByPrimaryKey(Integer id) {
		return jsOpenDayDao.selectByPrimaryKey(id);
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
	public List<JsOpenDay> getOpenDayAll() {
		Map<String,Object> param = new HashMap<String,Object>(); 
		param.put("openDayName",null);
		param.put("startDate", null);
		param.put("endDate", null);
		return jsOpenDayDao.getOpenDayList(param);
	}
}
