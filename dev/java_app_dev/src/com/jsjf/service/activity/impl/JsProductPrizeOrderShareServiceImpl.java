package com.jsjf.service.activity.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.ImageUtils;
import com.jsjf.common.PageInfo;
import com.jsjf.common.SFtpUtil;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.JsProductPrizeOrderShareDAO;
import com.jsjf.dao.product.DrProductInvestDAO;
import com.jsjf.model.activity.JsActivityFriend;
import com.jsjf.model.activity.JsProductPrizeOrderShare;
import com.jsjf.service.activity.JsProductPrizeOrderShareService;
import com.jsjf.service.product.DrProductInvestService;

@Service
@Transactional
public class JsProductPrizeOrderShareServiceImpl implements JsProductPrizeOrderShareService {
	
	@Autowired
	private JsProductPrizeOrderShareDAO jsProductPrizeOrderShareDAO;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;

	@Override
	public List<JsProductPrizeOrderShare> selectByMap(Map<String, Object> map) {
		return jsProductPrizeOrderShareDAO.selectByMap(map);
	}

	@Override
	public BaseResult insert(JsProductPrizeOrderShare obj,MultipartFile picUrl) {
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//判断晒单内容
			if(StringUtils.isBlank(obj.getDescribes())){
				br.setSuccess(false);
				br.setErrorMsg("晒单内容不能为空！");
				return br;
			}
			if(obj.getDescribes().trim().length()>144){
				br.setSuccess(false);
				br.setErrorMsg("晒单内容限制144字符！");
				return br;
			}
			if(Utils.isBlank(obj.getUid())){
				br.setSuccess(false);
				br.setErrorMsg("uid不能为空!");
				return br;
			}
			//投资投即送count,查询除过失败和未处理的数量
			Integer investSendCount = drProductInvestDAO.selectInvstSendCountByUid(obj.getUid());
			//已晒单count,查询除过失败的，显示和非显示的数量
			Integer orderShareCount = jsProductPrizeOrderShareDAO.selectOrderShareCountByUid(obj.getUid());
			if(orderShareCount >= investSendCount || investSendCount == 0){
				br.setSuccess(false);
				br.setErrorMsg("晒单次数不能大于投资投即送的次数！");
				return br;
			}
			//上传图片
			if(Utils.isObjectEmpty(picUrl)){
				br.setSuccess(false);
				br.setErrorMsg("图片不能为空！");
				return br;
			}
			String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.BMP|.bmp|.PNG|.png)$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(picUrl.getOriginalFilename().toLowerCase());
			if(!matcher.find()){
				br.setSuccess(false);
				br.setErrorMsg("请上传正确的图片格式!");
				return br;
			}
			long fileSize = picUrl.getSize();
			if(fileSize>1024*2000){
				br.setSuccess(false);
				br.setErrorMsg("图片不能大于2M！");
				return br;
			}
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.investSendPic + Utils.format(new Date(), "yyyy-MM") + "/" + obj.getId() + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.investSendPic + Utils.format(new Date(), "yyyy-MM") + "/" + obj.getId() + "/";
			String url = savePath+getFilePath(picUrl, realPath);
			obj.setH5ImgUrl(url);
			obj.setPcImgUrl(url);
			obj.setUrl(url);
			obj.setIsShow(2);
			jsProductPrizeOrderShareDAO.insert(obj);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return br;
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
	public BaseResult selectOrderShareByMap(PageInfo pageInfo,Integer uid) {
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("offset", pageInfo.getPageInfo().getOffset());
			map.put("limit", pageInfo.getPageInfo().getLimit());
			map.put("isShow", 1);
			map.put("order", " ORDER BY a.addtime DESC");
			List<JsProductPrizeOrderShare> list = jsProductPrizeOrderShareDAO.selectByMap(map);
			int total = jsProductPrizeOrderShareDAO.selectOrderShareCount(map);
			pageInfo.setRows(list);
			pageInfo.setTotal(total);
			map.clear();
			//投资投即送count,查询除过失败和未处理的数量
			Integer investSendCount = drProductInvestDAO.selectInvstSendCountByUid(uid);
			//已晒单count,查询除过失败的，显示和非显示的数量
			Integer orderShareCount = jsProductPrizeOrderShareDAO.selectOrderShareCountByUid(uid);
			map.put("pageInfo", pageInfo);
			map.put("orderShareCount", investSendCount - orderShareCount);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			br.setSuccess(false);
			br.setErrorCode("9999");
			e.printStackTrace();
		}
		return br;
	}
	
}
