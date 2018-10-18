package com.jsjf.service.activity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ImageUtils;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsSpecialDao;
import com.jsjf.dao.activity.JsSpecialPicDao;
import com.jsjf.model.activity.JsSpecial;
import com.jsjf.model.activity.JsSpecialPic;
import com.jsjf.model.product.DrProductPic;
import com.jsjf.service.activity.JsSpecialService;

@Service
@Transactional
public class JsSpecialServiceImpl implements JsSpecialService {
	
	@Autowired
	private JsSpecialDao jsSpecialDao;
	@Autowired
	private JsSpecialPicDao jsSpecialPicDao;

	@Override
	public JsSpecial selectList() {
		return jsSpecialDao.selectList();
	}

	@Override
	public BaseResult insert(JsSpecial vo, MultipartFile topPc, MultipartFile topH5, MultipartFile rightPc,MultipartFile[] files) {
		BaseResult br = new BaseResult();
		try {
			if(vo.getId() ==null){
				jsSpecialDao.insert(vo);
			}
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + vo.getId() + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/" + vo.getId() + "/";
			if(topPc !=null){
				vo.setPCTopBanner(savePath+getFilePath(topPc, realPath));
			}
			if(topH5 !=null){
				vo.setH5TopBanner(savePath+getFilePath(topH5, realPath));
			}
			if(rightPc !=null){
				vo.setPCRightBanner(savePath+getFilePath(rightPc, realPath));
			}
			
			List<JsSpecialPic> list = vo.getJsSpecialPic();
			List<JsSpecialPic> picList = jsSpecialPicDao.selectBySpecialId(vo.getId());
			if(Utils.isEmptyList(list)){
				jsSpecialPicDao.deletePicByJsSpecial(vo.getId());
			}else{
				List<JsSpecialPic> useSpecialPic = new ArrayList<>();
				for(JsSpecialPic pic : list){
					if("use".equals(pic.getImgUrl())){
						useSpecialPic.add(pic);
					}
				}
				if(files.length>0){
				for(int i = 0;i<files.length;i++){
					
					useSpecialPic.get(i).setAddDate(new Date());
					if(useSpecialPic.get(i).getId() != null){
						useSpecialPic.get(i).setImgUrl(savePath+getFilePath(files[i], realPath));
						jsSpecialPicDao.update(useSpecialPic.get(i));
					}else{
						useSpecialPic.get(i).setAddDate(new Date());
						useSpecialPic.get(i).setImgUrl(savePath+getFilePath(files[i], realPath));
						useSpecialPic.get(i).setSpecialId(vo.getId());
						jsSpecialPicDao.insert(useSpecialPic.get(i));
					}
				}
			  }else{
					for(JsSpecialPic pic : list){
						if(pic.getId() != null){
							jsSpecialPicDao.update(pic);
						}
					}
			  }
			
			}
			jsSpecialDao.update(vo);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch (Exception e) {
			br.setErrorCode("9999");
			br.setSuccess(false);
			br.setMsg("修改失败");
			e.printStackTrace();
		}
		return br;
	}

	@Override
	public BaseResult update(JsSpecial vo) {
		BaseResult br = new BaseResult();
		try {
			jsSpecialDao.update(vo);
			br.setSuccess(true);
			br.setMsg("修改成功");
		}catch (Exception e) {
			br.setErrorCode("9999");
			br.setErrorMsg("修改失败");
			br.setSuccess(false);
			e.printStackTrace();
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

}
