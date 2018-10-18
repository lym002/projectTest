package com.jsjf.service.activity.impl;

import java.util.Date;
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
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsProductPrizeOrderShareDAO;
import com.jsjf.model.activity.JsProductPrizeOrderShare;
import com.jsjf.service.activity.JsProductPrizeOrderShareService;

@Service
@Transactional
public class JsProductPrizeOrderShareServiceImpl implements JsProductPrizeOrderShareService {
	
	@Autowired
	private JsProductPrizeOrderShareDAO jsProductPrizeOrderShareDAO;

	@Override
	public List<JsProductPrizeOrderShare> selectByMap(Map<String, Object> map) {
		return jsProductPrizeOrderShareDAO.selectByMap(map);
	}

	@Override
	public int selectByMapCount(Map<String, Object> map) {
			Integer count = jsProductPrizeOrderShareDAO.selectByMapCount(map);
		return count !=null?count:0;
	}

	@Override
	public BaseResult insert(int uid, String describes, MultipartFile file,BaseResult br) {
		//1.图片大小验证
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(file.getOriginalFilename().toLowerCase());
		if(matcher.find() ){
			long fileSize = file.getSize();
			if(fileSize<1024*2000){
				
					//3.添加
					String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.investSendPic + Utils.format(new Date(), "yyyy-MM") + "/" + uid + "/";
					String savePath = ConfigUtil.getDomainname() + ConfigUtil.investSendPic + Utils.format(new Date(), "yyyy-MM") + "/" + uid + "/";
					
					String url = savePath+ConfigUtil.getFilePath(file, realPath);
					
					JsProductPrizeOrderShare  obj = new JsProductPrizeOrderShare();
					obj.setDescribes(describes);
					obj.setUrl(url);
					obj.setH5ImgUrl(url);
					obj.setPcImgUrl(url);
					obj.setAddtime(new Date());
					obj.setUid(uid);
					jsProductPrizeOrderShareDAO.insert(obj);
					
					br.setSuccess(true);
			}else{
				br.setSuccess(false);
				br.setErrorMsg("图片不能大于5M！");
			}
		}else{
			br.setSuccess(false);
			br.setErrorMsg("请上传正确的图片格式!");
		}
		return br;
	}

	
}
