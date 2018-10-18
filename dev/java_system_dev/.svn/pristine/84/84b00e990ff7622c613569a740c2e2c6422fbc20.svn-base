package com.jsjf.service.product.impl;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.*;
import com.jsjf.dao.activity.ActivityTemplateDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.dao.claims.DrAuditInfoDAO;
import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.dao.claims.DrClaimsPicDAO;
import com.jsjf.dao.claims.JsLoanRecordDAO;
import com.jsjf.dao.member.*;
import com.jsjf.dao.product.*;
import com.jsjf.dao.subject.DrSubjectInfoDAO;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.claims.*;
import com.jsjf.model.member.*;
import com.jsjf.model.product.*;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysFuiouNoticeLog;
import com.jsjf.model.system.SysMessageLog;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jsjf.service.member.DrCompanyFundsLogService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.subject.DrSubjectInfoService;
import com.jsjf.service.system.SysFuiouNoticeLogService;
import com.jsjf.service.system.SysMessageLogService;
import com.jsjf.service.system.impl.RedisClientTemplate;
import com.jzh.FuiouConfig;
import com.sensorsdata.SensorsAnalytics;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class DrProductInfoServiceImpl implements DrProductInfoService {
	private static Logger log = Logger.getLogger(DrProductInfoServiceImpl.class);
	@Autowired
	private DrProductInfoDAO drProductInfoDAO;
	@Autowired
	private DrClaimsPicDAO drClaimsPicDAO;
	@Autowired
	private DrSubjectInfoDAO drSubjectInfoDAO;
	@Autowired
	private DrAuditInfoDAO drAuditInfoDAO;
	@Autowired
	private DrProductInvestDAO drProductInvestDAO;
	@Autowired
	private DrProductInvestRepayInfoDAO drProductInvestRepayInfoDAO;
	@Autowired
	private DrClaimsLoanDAO drClaimsLoanDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;
	@Autowired
	private DrMemberFundsDAO drMemberFundsDAO;
	@Autowired
	private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
	@Autowired
	private DrMemberFundsLogDAO drMemberFundsLogDAO;
	@Autowired
	private DrCompanyFundsLogDAO drCompanyFundsLogDAO;
	@Autowired
	private DrProductPicDAO drProductPicDAO;
	@Autowired
	private DrMemberMsgDAO drMemberMsgDAO;
	@Autowired
	private DrMemberFavourableDAO drMemberFavourableDAO;
	@Autowired
	private SysMessageLogService sysMessageLogService;
	@Autowired
	private DrProductExtendDAO drProductExtendDAO;
	@Autowired
	private DrProductInfoRepayDetailDAO productRepayDetailDAO;
	@Autowired
	private ActivityTemplateDAO activityTemplateDAO;
	@Autowired
	private JsProductPrizeDAO jsProductPrizeDAO;
	@Autowired
	private JsLoanRecordDAO jsLoanRecordDAO;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private DrClaimsInfoService drClaimsInfoService;
	@Autowired
	private JsCoverChargeDAO jsCoverChargeDAO;
	@Autowired
	private JsInvoiceDAO jsInvoiceDAO;
	@Autowired
	private SysFuiouNoticeLogService sysFuiouNoticeLogService;
	@Autowired
	private DrCompanyFundsLogService drCompanyFundsLogService;
	@Autowired
	private DrSubjectInfoService drSubjectInfoService;
	@Autowired
	private JsCompanyAccountLogDAO jsCompanyAccountLogDAO;
	
	
	@Override
	public BaseResult getDrProductInfoList(DrProductInfo drProductInfo, PageInfo pi) {
		Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("simpleName", drProductInfo.getSimpleName());
		map.put("code", drProductInfo.getCode());
		if (drProductInfo.getType() != null) {
			map.put("type", drProductInfo.getType());
		}
		if(drProductInfo.getRepayType()!=null){
			map.put("repayType", drProductInfo.getRepayType());
		}
		if (drProductInfo.getStatus() == null) {
			map.put("status", drProductInfo.getStatus());
		} else {
			if (drProductInfo.getStatus() == 100) {// 显示审核页面
				map.put("status", new Integer[] { 1, 3 });
			} else {
				map.put("status", new Integer[] { drProductInfo.getStatus() });
			}
		}
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrProductInfo> list = drProductInfoDAO.getDrProductInfoList(map);
		Integer total = drProductInfoDAO.getDrProductInfoCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public DrProductInfo getDrProductInfoByid(Integer id) {
		DrProductInfo drProductInfo = drProductInfoDAO.getDrProductInfoByid(id);
		List<DrProductPic> drProductPic = drProductPicDAO.getDrProductPicByPid(id);
		drProductInfo.setDrProductPic(drProductPic);
		return drProductInfo;
	}

	@Override
	public BaseResult updateDrProductInfo(DrProductInfo drProductInfo,MultipartFile[] productFiles,@RequestParam MultipartFile acceptPicFile
			,MultipartFile principlePcFile, MultipartFile principleAppFile)throws Exception {
		BaseResult br = new BaseResult();
/*		if (1 == drProductInfo.getType() || 3 == drProductInfo.getType()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 1);
			//存管新手标也只能添加一条  cece
			map.put("typeCg", 3);
			map.put("status", 5);
			DrProductInfo queryProductInfo = drProductInfoDAO.getDrProductInfoByMap(map);
			if (Utils.isObjectNotEmpty(queryProductInfo)) {
				br.setErrorMsg("已发过新手标！");
				br.setSuccess(false);
				return br;
			}
		}*/

		DrProductInfo queryProductInfo = drProductInfoDAO.getDrProductInfoByid(drProductInfo.getId());
		if (queryProductInfo.getStatus() != 1 && queryProductInfo.getStatus() != 3
				&& queryProductInfo.getStatus() != 2) {
			br.setErrorMsg("该产品不可修改！");
			br.setSuccess(false);
			return br;
		}

		drProductInfo.setSurplusAmount(drProductInfo.getAmount());
		drProductInfo.setAlreadyRaiseAmount(new BigDecimal(0));

		drProductInfo.setIsHot(drProductInfo.getIsHot() == null ? 0 : drProductInfo.getIsHot());
		drProductInfo.setIsDeductible(drProductInfo.getIsDeductible() == null ? 0 : drProductInfo.getIsDeductible());
		drProductInfo.setIsInterest(drProductInfo.getIsInterest() == null ? 0 : drProductInfo.getIsInterest());
		drProductInfo.setIsCash(drProductInfo.getIsCash() == null ? 0 : drProductInfo.getIsCash());
		drProductInfo.setIsDouble(drProductInfo.getIsDouble() == null ? 0 : drProductInfo.getIsDouble());
		DrSubjectInfo drSubjectInfo = null;
		if (queryProductInfo.getType() != 1) {
			drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(queryProductInfo.getSid());
			drSubjectInfo.setSurplusAmount(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)));
			drSubjectInfo.setAmount(drSubjectInfo.getAmount().multiply(new BigDecimal(10000)));

			drSubjectInfo
					.setSurplusAmount(Utils.nwdBcadd(drSubjectInfo.getSurplusAmount(), queryProductInfo.getAmount()));
		}
		if (drProductInfo.getType() != 1) {
			if (Utils.isObjectNotEmpty(drProductInfo.getSid())) {
				if (queryProductInfo.getSid().intValue() != drProductInfo.getSid().intValue()) {
					DrSubjectInfo subjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(drProductInfo.getSid());
					subjectInfo.setSurplusAmount(subjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)));
					subjectInfo.setAmount(subjectInfo.getAmount().multiply(new BigDecimal(10000)));
					int counts = Utils.daysBetween(drProductInfo.getDeadline() + drProductInfo.getRaiseDeadline() - 1,
							subjectInfo.getEndDate(), null);
					if (counts > 0) {
						br.setErrorMsg("产品到期日不可大于标的到期日【" + Utils.format(subjectInfo.getEndDate(), "yyyy-MM-dd") + "】！");
						br.setSuccess(false);
						return br;
					}

					if (drProductInfo.getAmount().compareTo(subjectInfo.getSurplusAmount()) <= 0) {
						subjectInfo.setSurplusAmount(
								Utils.nwdBcsub(subjectInfo.getSurplusAmount(), drProductInfo.getAmount()));
						subjectInfo.setStatus(2);
						drSubjectInfoDAO.updateDrSubjectInfo(subjectInfo);
					} else {
						br.setErrorMsg("产品金额不能大于标的剩余金额【" + subjectInfo.getSurplusAmount() + "】！");
						br.setSuccess(false);
						return br;
					}

				} else {
					if (drProductInfo.getAmount().compareTo(drSubjectInfo.getSurplusAmount()) <= 0) {
						drSubjectInfo.setSurplusAmount(
								Utils.nwdBcsub(drSubjectInfo.getSurplusAmount(), drProductInfo.getAmount()));
					} else {
						br.setErrorMsg("产品金额不能大于标的剩余金额【" + drSubjectInfo.getSurplusAmount() + "】！");
						br.setSuccess(false);
						return br;
					}
					int counts = Utils.daysBetween(drProductInfo.getDeadline() + drProductInfo.getRaiseDeadline() - 1,
							drSubjectInfo.getEndDate(), null);
					if (counts > 0) {
						br.setErrorMsg(
								"产品到期日不可大于标的到期日【" + Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd") + "】！");
						br.setSuccess(false);
						return br;
					}
				}
			} else {
				br.setErrorMsg("请选择标的！");
				br.setSuccess(false);
				return br;
			}
		} else {
			drProductInfo.setIsSid(0);
			drProductInfo.setSid(null);
		}
		if (Utils.isObjectNotEmpty(drSubjectInfo)) {
			drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);
		}
		drProductInfo.setStatus(1);

		SFtpUtil sftp = new SFtpUtil();

		if (Utils.isObjectNotEmpty(acceptPicFile)) {
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/";
			String imageName = ImageUtils.getServerFileName() + acceptPicFile.getOriginalFilename()
					.substring(acceptPicFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(acceptPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setAcceptPic(savePath + imageName);
		}

		//pc
		if(Utils.isObjectNotEmpty(principlePcFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principlePcFile.getOriginalFilename().substring(
							principlePcFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principlePcFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrinciplePC(savePath+imageName);
		}
		//h5
		if(Utils.isObjectNotEmpty(principleAppFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principleAppFile.getOriginalFilename().substring(
							principleAppFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principleAppFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrincipleH5(savePath+imageName);
		}
		
		//pc
		if(Utils.isObjectNotEmpty(principlePcFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principlePcFile.getOriginalFilename().substring(
							principlePcFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principlePcFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrinciplePC(savePath+imageName);
		}
		//h5
		if(Utils.isObjectNotEmpty(principleAppFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principleAppFile.getOriginalFilename().substring(
							principleAppFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principleAppFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrincipleH5(savePath+imageName);
		}
		
		//pc
		if(Utils.isObjectNotEmpty(principlePcFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principlePcFile.getOriginalFilename().substring(
							principlePcFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principlePcFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrinciplePC(savePath+imageName);
		}
		//h5
		if(Utils.isObjectNotEmpty(principleAppFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principleAppFile.getOriginalFilename().substring(
							principleAppFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principleAppFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrincipleH5(savePath+imageName);
		}
		
		drProductInfoDAO.updateDrProductInfo(drProductInfo);
		Map<String, Object> map = new HashMap();
		map.put("pid", drProductInfo.getId());// 产品id
		activityTemplateDAO.deleteActivityProduct(map);// 先清除活动产品关联表的数据
		// 判断此次修改是否关联活动
		if (drProductInfo.getAtid() != null && drProductInfo.getAtid().intValue() > 0) {
			map.put("atid", drProductInfo.getAtid());
			map.put("appTitle", redisClientTemplate.getProperties("activityTitle"));
			map.put("pcDetailImg", redisClientTemplate.getProperties("activityImg"));
			map.put("pcBannerUrl", redisClientTemplate.getProperties("activityPCBannerImg"));
			map.put("h5BannerUrl", redisClientTemplate.getProperties("activityH5BannerImg"));
			activityTemplateDAO.insertActivityProduct(map);
		}

		List<DrProductExtend> drProductExtendList = drProductInfo.getDrProductExtend();
		drProductExtendDAO.deleteDrProductExtendByPid(drProductInfo.getId());
		if (!Utils.isEmptyList(drProductExtendList)) {
			for (DrProductExtend drProductExtend : drProductExtendList) {
				if (!Utils.strIsNull(drProductExtend.getTitle())) {
					drProductExtend.setPid(drProductInfo.getId());
					drProductExtend.setAddUser(drProductInfo.getAddUser());
					drProductExtendDAO.insertDrProductExtend(drProductExtend);
				}
			}
		}

		if (drProductInfo.getType() == 3 || drProductInfo.getType() == 2) {
			List<DrClaimsPic> drClaimsPicList = drProductInfo.getDrClaimsPic();
			if (!Utils.isEmptyList(drClaimsPicList)) {
				for (DrClaimsPic drClaimsPic : drClaimsPicList) {
					drClaimsPic.setIsShow(drClaimsPic.getIsShow() == null ? 0 : drClaimsPic.getIsShow());
					drClaimsPicDAO.updateDrClaimsPic(drClaimsPic);
				}
			}
		}
		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/"
				+ drProductInfo.getId() + "/";
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/"
				+ drProductInfo.getId() + "/";

		List<DrProductPic> list = drProductInfo.getDrProductPic();

		if (Utils.isEmptyList(list)) {
			drProductPicDAO.deleteDrProductPicByPid(drProductInfo.getId());
		} else {
			List<DrProductPic> useProductPic = new ArrayList<DrProductPic>();
			for (DrProductPic pic : list) {
				if ("use".equals(pic.getBigUrl())) {
					useProductPic.add(pic);
				}
			}

			if (productFiles.length > 0) {
				for (int i = 0; i < productFiles.length; i++) {
					String imageName = ImageUtils.getServerFileName() + productFiles[i].getOriginalFilename()
							.substring(productFiles[i].getOriginalFilename().lastIndexOf("."));
					if (useProductPic.get(i).getId() != null) {
						useProductPic.get(i).setBigUrl(savePath + imageName);
						sftp.connectServer();
						sftp.put(productFiles[i].getInputStream(), realPath, imageName);
						sftp.closeServer();
						drProductPicDAO.updateDrProductPic(useProductPic.get(i));
					} else {
						useProductPic.get(i).setStatus(1);
						useProductPic.get(i).setPid(drProductInfo.getId());
						useProductPic.get(i).setBigUrl(savePath + imageName);
						drProductPicDAO.insertDrProductPic(useProductPic.get(i));
						sftp.connectServer();
						sftp.put(productFiles[i].getInputStream(), realPath, imageName);
						sftp.closeServer();
					}
				}
			} else {
				for (DrProductPic drProductPic : list) {
					if (drProductPic.getId() != null) {
						drProductPicDAO.updateDrProductPic(drProductPic);
					}
				}
			}
		}
		br.setMsg("修改成功!");
		br.setSuccess(true);
		return br;

	}

	@Override
	public BaseResult insertDrProductInfo(DrProductInfo drProductInfo,
			@RequestParam MultipartFile[] productFiles,@RequestParam MultipartFile acceptPicFile
			,MultipartFile principlePcFile, MultipartFile principleAppFile)throws Exception {
		BaseResult br = new BaseResult();
		/*if (1 == drProductInfo.getType() || 3 == drProductInfo.getType()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 1);
			//存管新手标也只能添加一条  cece
			map.put("typeCg", 3);
			map.put("status", 5);
			DrProductInfo queryProductInfo = drProductInfoDAO.getDrProductInfoByMap(map);
			if (Utils.isObjectNotEmpty(queryProductInfo)) {
				br.setErrorMsg("已发过新手标！");
				br.setSuccess(false);
				return br;
			}
		}*/

		drProductInfo.setSurplusAmount(drProductInfo.getAmount());
		drProductInfo.setAlreadyRaiseAmount(new BigDecimal(0));

		drProductInfo.setIsShow(1);
		drProductInfo.setIsHot(drProductInfo.getIsHot() == null ? 0 : drProductInfo.getIsHot());
		drProductInfo.setIsDeductible(drProductInfo.getIsDeductible() == null ? 0 : drProductInfo.getIsDeductible());
		drProductInfo.setIsInterest(drProductInfo.getIsInterest() == null ? 0 : drProductInfo.getIsInterest());
		drProductInfo.setIsCash(drProductInfo.getIsCash() == null ? 0 : drProductInfo.getIsCash());
		drProductInfo.setIsDouble(drProductInfo.getIsDouble() == null ? 0 : drProductInfo.getIsDouble());
		if (drProductInfo.getType() != 1) {
			if (Utils.isObjectNotEmpty(drProductInfo.getSid())) {
				DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(drProductInfo.getSid());
				drSubjectInfo.setSurplusAmount(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)));
				drSubjectInfo.setAmount(drSubjectInfo.getAmount().multiply(new BigDecimal(10000)));

				int counts = Utils.daysBetween(drProductInfo.getDeadline() + drProductInfo.getRaiseDeadline() - 1,
						drSubjectInfo.getEndDate(), null);
				if (counts > 0) {
					br.setErrorMsg("产品到期日不可大于标的到期日【" + Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd") + "】！");
					br.setSuccess(false);
					return br;
				}

				if (drProductInfo.getAmount().compareTo(drSubjectInfo.getSurplusAmount()) <= 0) {
					drSubjectInfo.setSurplusAmount(
							Utils.nwdBcsub(drSubjectInfo.getSurplusAmount(), drProductInfo.getAmount()));
					drSubjectInfo.setStatus(2);
					drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);
				} else {
					br.setErrorMsg("产品金额不能大于标的剩余金额【" + drSubjectInfo.getSurplusAmount() + "】！");
					br.setSuccess(false);
					return br;
				}
			} else {
				br.setErrorMsg("请关联标的！");
				br.setSuccess(false);
				return br;
			}
		} else {
			drProductInfo.setIsSid(0);
			drProductInfo.setSid(null);
		}

		SFtpUtil sftp = new SFtpUtil();

		if (Utils.isObjectNotEmpty(acceptPicFile)) {
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/";
			String imageName = ImageUtils.getServerFileName() + acceptPicFile.getOriginalFilename()
					.substring(acceptPicFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(acceptPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setAcceptPic(savePath + imageName);
		}
		//pc
		if(Utils.isObjectNotEmpty(principlePcFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principlePcFile.getOriginalFilename().substring(
							principlePcFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principlePcFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrinciplePC(savePath+imageName);
		}
		//pc
		if(Utils.isObjectNotEmpty(principlePcFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principlePcFile.getOriginalFilename().substring(
							principlePcFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principlePcFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrinciplePC(savePath+imageName);
		}
		//h5
		if(Utils.isObjectNotEmpty(principleAppFile)){
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/";
			String imageName = ImageUtils.getServerFileName()
					+ principleAppFile.getOriginalFilename().substring(
							principleAppFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(principleAppFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setPrincipleH5(savePath+imageName);
		}
		drProductInfoDAO.insertDrProductInfo(drProductInfo);

		// 判断是否关联活动
		if (drProductInfo.getAtid() != null && drProductInfo.getAtid().intValue() > 0) {
			Map<String, Object> map = new HashMap();
			map.put("atid", drProductInfo.getAtid());
			map.put("pid", drProductInfo.getId());
			map.put("appTitle", redisClientTemplate.getProperties("activityTitle"));
			map.put("pcDetailImg", redisClientTemplate.getProperties("activityImg"));
			map.put("pcBannerUrl", redisClientTemplate.getProperties("activityPCBannerImg"));
			map.put("h5BannerUrl", redisClientTemplate.getProperties("activityH5BannerImg"));
			activityTemplateDAO.insertActivityProduct(map);
		}
		// 判断是否关联奖品,并且把关联的奖品的status改为上线
		/*
		 * if(Utils.isObjectNotEmpty(drProductInfo.getPrizeId())){
		 * JsProductPrize jsProductPrize =
		 * jsProductPrizeDAO.getJsProductPrizeById(drProductInfo.getPrizeId());
		 * if(Utils.isObjectNotEmpty(jsProductPrize)){
		 * jsProductPrize.setStatus(1);
		 * jsProductPrizeDAO.updateJsProductPrize(jsProductPrize); } }
		 */
		List<DrProductExtend> drProductExtendList = drProductInfo.getDrProductExtend();

		if (!Utils.isEmptyList(drProductExtendList)) {
			for (DrProductExtend drProductExtend : drProductExtendList) {
				if (!Utils.strIsNull(drProductExtend.getTitle())) {
					drProductExtend.setPid(drProductInfo.getId());
					drProductExtend.setAddUser(drProductInfo.getAddUser());
					drProductExtendDAO.insertDrProductExtend(drProductExtend);
				}
			}
		}

		if (drProductInfo.getType() == 3 || drProductInfo.getType() == 2) {
			List<DrClaimsPic> drClaimsPicList = drProductInfo.getDrClaimsPic();
			if (!Utils.isEmptyList(drClaimsPicList)) {
				for (DrClaimsPic drClaimsPic : drClaimsPicList) {
					drClaimsPic.setIsShow(drClaimsPic.getIsShow() == null ? 0 : drClaimsPic.getIsShow());
					drClaimsPicDAO.updateDrClaimsPic(drClaimsPic);
				}
			}
		}

		List<DrProductPic> list = drProductInfo.getDrProductPic();
		List<DrProductPic> useProductPic = new ArrayList<DrProductPic>();
		if (!Utils.isEmptyList(list)) {
			for (DrProductPic pic : list) {
				if ("use".equals(pic.getBigUrl())) {
					useProductPic.add(pic);
				}
			}
		}

		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/"
				+ drProductInfo.getId() + "/";
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/"
				+ drProductInfo.getId() + "/";

		for (int i = 0; i < productFiles.length; i++) {
			String imageName = ImageUtils.getServerFileName() + productFiles[i].getOriginalFilename()
					.substring(productFiles[i].getOriginalFilename().lastIndexOf("."));

			DrProductPic drProductPic = new DrProductPic();
			drProductPic.setPid(drProductInfo.getId());
			drProductPic.setBigUrl(savePath + imageName);
			drProductPic.setName(useProductPic.get(i).getName());
			drProductPic.setIsShow(useProductPic.get(i).getIsShow() == null ? 0 : useProductPic.get(i).getIsShow());
			drProductPic.setShowSort(useProductPic.get(i).getShowSort());
			drProductPic.setStatus(1);
			drProductPic.setType(useProductPic.get(i).getType());
			drProductPicDAO.insertDrProductPic(drProductPic);

			sftp.connectServer();
			sftp.put(productFiles[i].getInputStream(), realPath, imageName);
			sftp.closeServer();
		}
		br.setMsg("添加成功!");
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult insertDrExperienceProductInfo(DrProductInfo drProductInfo) throws Exception {
		BaseResult br = new BaseResult();
		if (5 == drProductInfo.getType()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 5);
			map.put("type", 5);
			DrProductInfo queryProductInfo = drProductInfoDAO.getDrProductInfoByMap(map);
			if (drProductInfo.getStatus() == 5) {
				if (Utils.isObjectNotEmpty(queryProductInfo)) {
					br.setErrorMsg("已存在一个开启中体验标，请先将开启中的体验标关闭！");
					br.setSuccess(false);
					return br;
				} else {
					drProductInfo.setStartDate(new Date());
				}
			}
		}
		drProductInfo.setRaiseDeadline(1);
		drProductInfo.setRepayType(1);
		drProductInfo.setAmount(new BigDecimal(0));
		drProductInfo.setAlreadyRaiseAmount(new BigDecimal(0));
		drProductInfo.setSurplusAmount(new BigDecimal(0));
		drProductInfo.setLeastaAmount(new BigDecimal(0));
		drProductInfo.setIncreasAmount(new BigDecimal(0));
		drProductInfo.setMaxAmount(new BigDecimal(0));
		drProductInfo.setIntermediary(0);
		drProductInfo.setIsSid(0);
		drProductInfo.setIsShow(1);
		drProductInfo.setIsHot(0);
		drProductInfo.setIsDeductible(0);
		drProductInfo.setIsInterest(0);
		drProductInfo.setIsCash(0);
		drProductInfo.setMappingStatus(0);
		drProductInfo.setIsDouble(0);
		drProductInfo.setLoanStatus(0);
		drProductInfoDAO.insertDrProductInfo(drProductInfo);
		br.setMsg("新增体验标成功!");
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult insertDrProductInfoRenewal(DrProductInfo drProductInfo,
			@RequestParam MultipartFile[] productFiles, @RequestParam MultipartFile acceptPicFile) throws Exception {
		BaseResult br = new BaseResult();
		if (2 == drProductInfo.getType()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fid", drProductInfo.getFid());
			map.put("noStatus", 4);
			DrProductInfo queryProductInfo = drProductInfoDAO.getDrProductInfoByMap(map);
			if (Utils.isObjectNotEmpty(queryProductInfo)) {
				br.setErrorMsg("该产品已续发！");
				br.setSuccess(false);
				return br;
			}
		}

		drProductInfo.setSurplusAmount(drProductInfo.getAmount());
		drProductInfo.setAlreadyRaiseAmount(new BigDecimal(0));

		drProductInfo.setIsShow(1);
		drProductInfo.setIsHot(drProductInfo.getIsHot() == null ? 0 : drProductInfo.getIsHot());
		drProductInfo.setIsDeductible(drProductInfo.getIsDeductible() == null ? 0 : drProductInfo.getIsDeductible());
		drProductInfo.setIsInterest(drProductInfo.getIsInterest() == null ? 0 : drProductInfo.getIsInterest());
		drProductInfo.setIsCash(drProductInfo.getIsCash() == null ? 0 : drProductInfo.getIsCash());
		drProductInfo.setIsDouble(drProductInfo.getIsDouble() == null ? 0 : drProductInfo.getIsDouble());
		if (drProductInfo.getType() != 1) {
			if (Utils.isObjectNotEmpty(drProductInfo.getSid())) {
				DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(drProductInfo.getSid());

				int counts = Utils.daysBetween(drProductInfo.getDeadline() + drProductInfo.getRaiseDeadline() - 1,
						drSubjectInfo.getEndDate(), null);
				if (counts > 0) {
					br.setErrorMsg("产品到期日不可大于标的到期日【" + Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd") + "】！");
					br.setSuccess(false);
					return br;
				}
			} else {
				br.setErrorMsg("请关联标的！");
				br.setSuccess(false);
				return br;
			}
		} else {
			drProductInfo.setIsSid(0);
			drProductInfo.setSid(null);
		}

		SFtpUtil sftp = new SFtpUtil();

		if (Utils.isObjectNotEmpty(acceptPicFile)) {
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/";
			String imageName = ImageUtils.getServerFileName() + acceptPicFile.getOriginalFilename()
					.substring(acceptPicFile.getOriginalFilename().lastIndexOf("."));
			sftp.connectServer();
			sftp.put(acceptPicFile.getInputStream(), realPath, imageName);
			sftp.closeServer();
			drProductInfo.setAcceptPic(savePath + imageName);
		}
		drProductInfoDAO.insertDrProductInfo(drProductInfo);

		List<DrProductExtend> drProductExtendList = drProductInfo.getDrProductExtend();

		if (!Utils.isEmptyList(drProductExtendList)) {
			for (DrProductExtend drProductExtend : drProductExtendList) {
				if (!Utils.strIsNull(drProductExtend.getTitle())) {
					drProductExtend.setPid(drProductInfo.getId());
					drProductExtend.setAddUser(drProductInfo.getAddUser());
					drProductExtendDAO.insertDrProductExtend(drProductExtend);
				}
			}
		}

		if (drProductInfo.getType() != 1) {
			List<DrClaimsPic> drClaimsPicList = drProductInfo.getDrClaimsPic();
			if (!Utils.isEmptyList(drClaimsPicList)) {
				for (DrClaimsPic drClaimsPic : drClaimsPicList) {
					drClaimsPic.setIsShow(drClaimsPic.getIsShow() == null ? 0 : drClaimsPic.getIsShow());
					drClaimsPicDAO.updateDrClaimsPic(drClaimsPic);
				}
			}
		}

		String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/"
				+ drProductInfo.getId() + "/";
		String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM") + "/"
				+ drProductInfo.getId() + "/";

		List<DrProductPic> list = drProductInfo.getDrProductPic();

		if (!Utils.isEmptyList(list)) {
			List<DrProductPic> useProductPic = new ArrayList<DrProductPic>();
			for (DrProductPic pic : list) {
				pic.setPid(drProductInfo.getId());
				if ("use".equals(pic.getBigUrl())) {
					useProductPic.add(pic);
				}
			}

			if (productFiles.length > 0) {
				for (int i = 0; i < productFiles.length; i++) {
					String imageName = ImageUtils.getServerFileName() + productFiles[i].getOriginalFilename()
							.substring(productFiles[i].getOriginalFilename().lastIndexOf("."));
					if (useProductPic.get(i).getId() != null) {
						useProductPic.get(i).setBigUrl(savePath + imageName);
						sftp.connectServer();
						sftp.put(productFiles[i].getInputStream(), realPath, imageName);
						sftp.closeServer();
						drProductPicDAO.insertDrProductPic(useProductPic.get(i));
					} else {
						useProductPic.get(i).setStatus(1);
						useProductPic.get(i).setPid(drProductInfo.getId());
						useProductPic.get(i).setBigUrl(savePath + imageName);
						drProductPicDAO.insertDrProductPic(useProductPic.get(i));
						sftp.connectServer();
						sftp.put(productFiles[i].getInputStream(), realPath, imageName);
						sftp.closeServer();
					}
				}
			} else {
				for (DrProductPic drProductPic : list) {
					if (drProductPic.getId() != null) {
						drProductPicDAO.insertDrProductPic(drProductPic);
					}
				}
			}
		}

		br.setMsg("续发成功!");
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult insertDrAuditInfo(DrAuditInfo drAuditInfo) throws Exception {
		BaseResult br = new BaseResult();
		DrProductInfo drProductInfo = new DrProductInfo();
		drProductInfo.setId(drAuditInfo.getFid());
		if (drAuditInfo.getStatus() == 1) {
			drProductInfo.setStatus(2);
		}
		if (drAuditInfo.getStatus() == 2) {
			drProductInfo.setStatus(3);
		}
		if (drAuditInfo.getStatus() == 3) {
			DrProductInfo queryDrProductInfo = drProductInfoDAO.getDrProductInfoByid(drAuditInfo.getFid());
			if (1 != queryDrProductInfo.getType()) {
				DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(queryDrProductInfo.getSid());
				drSubjectInfo.setSurplusAmount(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)));
				drSubjectInfo.setAmount(drSubjectInfo.getAmount().multiply(new BigDecimal(10000)));
				drSubjectInfo.setSurplusAmount(
						Utils.nwdBcadd(drSubjectInfo.getSurplusAmount(), queryDrProductInfo.getAmount()));

				int counts = Utils.daysBetween(
						queryDrProductInfo.getDeadline() + queryDrProductInfo.getRaiseDeadline() - 1,
						drSubjectInfo.getEndDate(), null);
				if (counts > 0) {
					br.setErrorMsg("产品到期日不可大于标的到期日【" + Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd") + "】！");
					br.setSuccess(false);
					return br;
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sid", queryDrProductInfo.getSid());
				map.put("status", 4);
				List<DrProductInfo> list = drProductInfoDAO.getDrProductInfoListByMap(map);
				if (4 != drSubjectInfo.getStatus()) {
					if (list.size() <= 1) {
						drSubjectInfo.setStatus(1);
					}
				}
				drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);
			}
			drProductInfo.setStatus(4);

			DrProductPic drProductPic = new DrProductPic();
			drProductPic.setPid(drAuditInfo.getFid());
			drProductPic.setStatus(0);
			drProductPicDAO.updateDrProductPic(drProductPic);
		}
		drAuditInfoDAO.insertDrAuditInfo(drAuditInfo);
		drProductInfoDAO.updateDrProductInfo(drProductInfo);
		br.setMsg("审核成功!");
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult updateDrProductStatus(DrProductInfo drProductInfo, @RequestParam MultipartFile[] productFiles,
			String validatorSid) throws Exception {
		BaseResult br = new BaseResult();
		if ("validatorSid".equals(validatorSid)) {
			DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(drProductInfo.getSid());
			if (drProductInfo.getIsSid() == 1) {
				if (drProductInfo.getStatus() == 4) {
					drSubjectInfo.setSurplusAmount(drSubjectInfo.getSurplusAmount().multiply(new BigDecimal(10000)));
					drSubjectInfo.setAmount(drSubjectInfo.getAmount().multiply(new BigDecimal(10000)));
					drSubjectInfo.setSurplusAmount(
							Utils.nwdBcadd(drSubjectInfo.getSurplusAmount(), drProductInfo.getAmount()));

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sid", drProductInfo.getSid());
					map.put("status", 4);
					List<DrProductInfo> list = drProductInfoDAO.getDrProductInfoListByMap(map);
					if (4 != drSubjectInfo.getStatus()) {
						if (list.size() <= 1) {
							drSubjectInfo.setStatus(1);
						}
					}
					drSubjectInfoDAO.updateDrSubjectInfo(drSubjectInfo);
				}
				if (drProductInfo.getStatus() == 5) {
					int counts = Utils.daysBetween(drProductInfo.getDeadline() + drProductInfo.getRaiseDeadline() - 1,
							drSubjectInfo.getEndDate(), Utils.parse(drProductInfo.getStartDate(), "yyyy-MM-dd"));
					if (counts > 0) {
						br.setErrorMsg(
								"产品到期日不可大于标的到期日【" + Utils.format(drSubjectInfo.getEndDate(), "yyyy-MM-dd") + "】！");
						br.setSuccess(false);
						return br;
					}
				}
			}
		}
		drProductInfoDAO.updateDrProductInfo(drProductInfo);
		if (validatorSid == null && productFiles != null) {
			// 更新产品图片
			SFtpUtil sftp = new SFtpUtil();
			String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/" + drProductInfo.getId() + "/";
			String savePath = ConfigUtil.getDomainname() + ConfigUtil.productPic + Utils.format(new Date(), "yyyy-MM")
					+ "/" + drProductInfo.getId() + "/";
			List<DrProductPic> list = drProductInfo.getDrProductPic();

			if (Utils.isEmptyList(list)) {
				drProductPicDAO.deleteDrProductPicByPid(drProductInfo.getId());
			} else {
				List<DrProductPic> useProductPic = new ArrayList<DrProductPic>();
				for (DrProductPic pic : list) {
					if ("use".equals(pic.getBigUrl())) {
						useProductPic.add(pic);
					}
				}

				if (productFiles.length > 0) {
					for (int i = 0; i < productFiles.length; i++) {
						String imageName = ImageUtils.getServerFileName() + productFiles[i].getOriginalFilename()
								.substring(productFiles[i].getOriginalFilename().lastIndexOf("."));
						if (useProductPic.get(i).getId() != null) {
							useProductPic.get(i).setBigUrl(savePath + imageName);
							sftp.connectServer();
							sftp.put(productFiles[i].getInputStream(), realPath, imageName);
							sftp.closeServer();
							drProductPicDAO.updateDrProductPic(useProductPic.get(i));
						} else {
							useProductPic.get(i).setStatus(1);
							useProductPic.get(i).setPid(drProductInfo.getId());
							useProductPic.get(i).setBigUrl(savePath + imageName);
							drProductPicDAO.insertDrProductPic(useProductPic.get(i));
							sftp.connectServer();
							sftp.put(productFiles[i].getInputStream(), realPath, imageName);
							sftp.closeServer();
						}
					}
				} else {
					for (DrProductPic drProductPic : list) {
						if (drProductPic.getId() != null) {
							drProductPicDAO.updateDrProductPic(drProductPic);
						}
					}
				}
			}
		}
		br.setMsg("操作成功!");
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult updateDrExperienceProductInfo(DrProductInfo drProductInfo) throws Exception {
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 5);
		map.put("type", 5);
		DrProductInfo queryProductInfo = drProductInfoDAO.getDrProductInfoByMap(map);
		DrProductInfo product = drProductInfoDAO.getDrProductInfoByid(drProductInfo.getId());
		if (drProductInfo.getStatus() == 5) {
			if (Utils.isObjectEmpty(queryProductInfo)) {
				if (product.getStartDate() == null) {
					drProductInfo.setStartDate(new Date());
				}
				drProductInfoDAO.updateDrProductInfo(drProductInfo);
			} else {
				br.setErrorMsg("已存在一个开启的体验标，请先关闭！");
				br.setSuccess(false);
				return br;
			}
		}
		drProductInfoDAO.updateDrProductInfo(drProductInfo);
		br.setMsg("操作成功!");
		br.setSuccess(true);
		return br;

	}

	@Override
	public void updateDrProductCancelBespoke(DrProductInfo drProductInfo) throws Exception {
		drProductInfoDAO.updateDrProductCancelBespoke(drProductInfo);
	}

	@Override
	public DrProductInfo getDrProductInfoByMap(Map<String, Object> map) {
		return drProductInfoDAO.getDrProductInfoByMap(map);
	}
	/**
	 * 生成体验标回款信息
	 * @throws SQLException 
	 */
	@Override
	public void updateProductToEnd2(DrProductInfo info, char[] ary2 , Integer nums,List<DrProductInvest> investList) throws Exception {
		List<DrProductInvestRepayInfo> insertRepayInfoList = new ArrayList<DrProductInvestRepayInfo>();//保存投资成功的记录的回款信息
		List<DrProductInfoRepayDetail> productRepayDetailList = new ArrayList<DrProductInfoRepayDetail>();//按月付息产品每期回款明细信息
		List<DrProductInvest> successInvestList = new ArrayList<DrProductInvest>();//保存投资成功的记录
		StringBuffer failId = new StringBuffer();//保存投资失败的记录ID
		List<DrMemberMsg> msgList = new ArrayList<DrMemberMsg>();//需要发送的站内信
		List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
		Map<Integer, DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();//投资用户资金信息
		List<DrMemberFundsLog> fundsLogsList = new ArrayList<DrMemberFundsLog>();//交易日志
		List<DrMemberFundsRecord> fundsRecordList = new ArrayList<DrMemberFundsRecord>();//交易记录
		Date now = new Date();
		Integer dateType = 1; //1=30/360 2=30/365 日期模式
		BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(info.getRate().add(info.getActivityRate()),100), dateType==1?360:365);//日息
		
//		List<DrProductInvest> investList = drProductInvestDAO.getDrProductInvestListByPid(info.getId());//查询未处理的投资记录
		BigDecimal totalAmount = BigDecimal.ZERO; //已投资金额
		int periods = 0;
		for (int j = 0; j < investList.size(); j++) {
			nums++;
			DrProductInvest invest = investList.get(j);
			DrMember member = drMemberDAO.selectByPrimaryKey(invest.getUid());
			
			if(totalAmount.compareTo(info.getAmount()) < 0 
					|| info.getType() == 1 
					|| info.getType()==4 ||info.getType()==5){//
				totalAmount = totalAmount.add(invest.getAmount());
				
				if(totalAmount.compareTo(info.getAmount())>0 && info.getType() != 1 && info.getType() != 5){//投资总额大于产品金额，此笔投资只能部分成功
					invest.setFactAmount(info.getAmount().subtract(totalAmount.subtract(invest.getAmount())));//实际投资金额 = 产品金额-(已投资总额-本次投资金额)
					totalAmount = totalAmount.subtract(invest.getAmount()).add(invest.getFactAmount());
				}else{
					invest.setFactAmount(invest.getAmount());
				}
				JSONObject profitJson = null;
				if(info.getRepayType() == 1){
					//到期一次性还本付息
					//计算收益
					profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),info.getRate());
					
					DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(), invest.getId(), info.getId(),invest.getFactAmount(), BigDecimal.ZERO,
							profitJson.getBigDecimal("interestProfit"), BigDecimal.ZERO, BigDecimal.ZERO, 0,
							Utils.getDayNumOfAppointDate(now, -info.getDeadline()),null);
					
					insertRepayInfoList.add(repayinfo);
				}else if (info.getRepayType() == 2){
					//按月付息到期还本
					periods = info.getDeadline()/30;
					profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),info.getRate());
					BigDecimal interestProfit = profitJson.getBigDecimal("interestProfit");//总利息
					BigDecimal interestSubsidy = profitJson.getBigDecimal("interestSubsidy");//贴息
					BigDecimal interest = interestProfit.subtract(interestSubsidy).divide(new BigDecimal(periods), 2, BigDecimal.ROUND_FLOOR);//每月利息
					BigDecimal shouldPrincipal = BigDecimal.ZERO;//应收本金
					Date shouldTime = now;
					for(int i = 1; i <= periods; i ++){
						BigDecimal interestTemp = interest;
						if(i == 1){
							//差息
							BigDecimal interestDiff = interestProfit.subtract(interestSubsidy).subtract(interest.multiply(new BigDecimal(periods)));
							//贴息和差息放到第一个月
							interestTemp = interest.add(interestSubsidy).add(interestDiff);
						}
						if(i == periods){
							shouldPrincipal = invest.getFactAmount();
						}
						shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
						DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(), invest.getId(), info.getId(),shouldPrincipal, BigDecimal.ZERO,
								interestTemp, BigDecimal.ZERO, BigDecimal.ZERO, 0,
								shouldTime,null);
						insertRepayInfoList.add(repayinfo);
					}
				}
				invest.setFactInterest(profitJson.getBigDecimal("interestProfit"));
				invest.setStatus(1);
				//协议编号 通过当日计息次数生成计息编号invest.setAgreementNo("BYP"+Utils.format(new Date(), "yyMMdd")+info.getType()+new String(ary2));
				System.arraycopy((nums+"").toCharArray(), 0, ary2, ary2.length-(nums+"").toCharArray().length, (nums+"").toCharArray().length);
				invest.setAgreementNo("BYP"+Utils.format(new Date(), "yyMMdd")+info.getType()+new String(ary2));
				
				successInvestList.add(invest);
				
				//用户资金
				DrMemberFunds funds = null;
				if(fundsMap.containsKey(invest.getUid())){
					funds = fundsMap.get(invest.getUid());
				}else{
					funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
				}
				DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParamForProductInterest(invest.getId(), 4);
				//投资解冻
				DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getAmount(), 4, 1,
						"投资【"+info.getFullName()+"】产品计息，资金解冻");
				funds.setBalance(funds.getBalance().add(invest.getAmount()));
				funds.setFreeze(funds.getFreeze().subtract(invest.getAmount()));
				fundsLogsList.add(log);
				//投资成功
				DrMemberFundsLog log1 = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getFactAmount(), 7, 0, "投资【"+info.getFullName()+"】产品成功");
				funds.setBalance(funds.getBalance().subtract(invest.getFactAmount()));
				funds.setWprincipal(funds.getWprincipal().add(invest.getFactAmount()));
				funds.setWinterest(funds.getWinterest().add(invest.getFactInterest()));
				funds.setInvestAmount(funds.getInvestAmount().add(invest.getFactAmount()));
				fundsLogsList.add(log1);
				
				fundsRecord.setStatus(3);//投资成功
				fundsRecord.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
				fundsRecord.setAmount(invest.getAmount());
			
				fundsMap.put(funds.getUid(), funds);
				fundsRecordList.add(fundsRecord);
				DrMemberMsg msg = new DrMemberMsg(invest.getUid(), 0, 3, "计息成功", now, 0, 0,
						PropertyUtil.getProperties("tiyanbiaointerestSuccessMsg")
						.replace("${realName}", Utils.isObjectNotEmpty(member.getRealName())?member.getRealName():member.getMobilephone())
						.replace("${interest}", invest.getFactInterest().toString())
						.replace("${date}", Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")));
				msgList.add(msg);
				SysMessageLog smslog = new SysMessageLog(invest.getUid(), PropertyUtil.getProperties("tiyanbiaointerestSuccessSms")
						.replace("${realName}", Utils.isObjectNotEmpty(member.getRealName())?member.getRealName():member.getMobilephone())
						.replace("${interest}", invest.getFactInterest().toString())
						.replace("${date}", Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")),
						17, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:00:00"),"yyyy-MM-dd HH:mm:ss"), member.getMobilephone());
				smsList.add(smslog);
				
			}else{
				invest.setFactAmount(BigDecimal.ZERO);
				invest.setFactInterest(BigDecimal.ZERO);
				invest.setStatus(2);
				failId.append(invest.getId()+",");
				//用户资金
				DrMemberFunds funds = null;
				if(fundsMap.containsKey(invest.getUid())){
					funds = fundsMap.get(invest.getUid());
				}else{
					funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
				}
				DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParam(invest.getId(), null);
				//投资解冻
				DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getAmount(), 4, 1, "投资解冻");
				funds.setBalance(funds.getBalance().add(invest.getAmount()));
				funds.setFreeze(funds.getFreeze().subtract(invest.getAmount()));
				fundsLogsList.add(log);
				
				fundsRecord.setStatus(2);//投资失败
				fundsRecord.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
				fundsRecord.setAmount(invest.getAmount());
				
				fundsMap.put(funds.getUid(), funds);
				fundsRecordList.add(fundsRecord);
			}
			
		}
		if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4 && periods > 0) {
			Date shouldTime = now;
			for (int i = 1; i <= periods; i++) {
				if(info.getRepayType() == 2 || info.getRepayType() == 4){//按月
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
				}else if(info.getRepayType() == 3){
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -7);//按周
				}		
				// 添加每期回款信息
				DrProductInfoRepayDetail productRepayDetail = new DrProductInfoRepayDetail(info.getId(), i, shouldTime);
				productRepayDetailList.add(productRepayDetail);
			}

		}
		if(info.getType()!=1 && info.getType()!=4  && info.getType()!=5){
			DrProductInfo p = new DrProductInfo();
			p.setId(info.getId());
			p.setStatus(8);//待还款
			p.setIsHot(0);//已完成的产品取消热销
			drProductInfoDAO.updateDrProductInfo(p);//把产品状态修改成待还款
		}
		if(Utils.isObjectNotEmpty(info.getSid())){//绑定标的的产品募集成功后修改债权状态
			DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(info.getSid());//标的以及部分贷款项目信息
			drClaimsLoanDAO.updateClaimsLoanStatusById(5, drSubjectInfo.getLid());//修改债权状态为待还款
		}
		if(totalAmount.compareTo(BigDecimal.ZERO)!=0){
			DrCompanyFundsLog clog = new DrCompanyFundsLog(1, 0, info.getId(), totalAmount, 1, "产品【"+info.getFullName()+"】投资款",null);
			drCompanyFundsLogDAO.insertDrCompanyFundsLog(clog);
		}
		if(nums>0 && !Utils.isEmptyList(investList)){
			drProductInvestRepayInfoDAO.batchInsert(insertRepayInfoList);
			if(!productRepayDetailList.isEmpty()){
				List<DrProductInfoRepayDetail> list=productRepayDetailDAO.selectByPid(info.getId());
				if(list.size()<1){
					productRepayDetailDAO.batchInsert(productRepayDetailList);
				}
				drProductInvestRepayInfoDAO.updateRepayDetailIdByPid(info);//按月付息产品，产品明细ID与回款记录关联
			}
			drProductInvestDAO.batchUpdate(successInvestList);
			drMemberFundsRecordDAO.batchUpdateMemberFundsRecord(fundsRecordList);
			drMemberFundsDAO.batchUpdateDrMemberFunds((new ArrayList<DrMemberFunds>(fundsMap.values())));
			drMemberFundsLogDAO.batchInsert(fundsLogsList);
			drMemberMsgDAO.batchInsert(msgList);
		}
		if(StringUtils.isNotBlank(failId.toString())){
			String[] ids = failId.toString().split(",");
			drProductInvestDAO.updateStatusByIds("2", ids);
		}
		//短信发送
		for (int i = 0; i < smsList.size(); i++) {
			sysMessageLogService.sendMsg(smsList.get(i));
		}
	}
	/**
	 * 生成体验标回款信息-存管
	 * 
	 * @throws SQLException
	 */
	@Override
	public void updateProductToEnd3(DrProductInfo info, char[] ary2, Integer nums, List<DrProductInvest> investList)
			throws Exception {
		List<DrProductInvestRepayInfo> insertRepayInfoList = new ArrayList<DrProductInvestRepayInfo>();// 保存投资成功的记录的回款信息
		List<DrProductInfoRepayDetail> productRepayDetailList = new ArrayList<DrProductInfoRepayDetail>();// 按月付息产品每期回款明细信息
		List<DrProductInvest> successInvestList = new ArrayList<DrProductInvest>();// 保存投资成功的记录
		StringBuffer failId = new StringBuffer();// 保存投资失败的记录ID
		List<DrMemberMsg> msgList = new ArrayList<DrMemberMsg>();// 需要发送的站内信
		List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
		Map<Integer, DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();// 投资用户资金信息
		List<DrMemberFundsLog> fundsLogsList = new ArrayList<DrMemberFundsLog>();// 交易日志
		List<DrMemberFundsRecord> fundsRecordList = new ArrayList<DrMemberFundsRecord>();// 交易记录
		Date now = new Date();
		Integer dateType = 1; // 1=30/360 2=30/365 日期模式
		BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(info.getRate().add(info.getActivityRate()), 100),
				dateType == 1 ? 360 : 365);// 日息
		BigDecimal rate = Utils.nwdDivide(Utils.nwdDivide(info.getRate(), 100),
				dateType == 1 ? 360 : 365);// 基本日息
		// List<DrProductInvest> investList =
		// drProductInvestDAO.getDrProductInvestListByPid(info.getId());//查询未处理的投资记录
		BigDecimal totalAmount = BigDecimal.ZERO; // 已投资金额
		int periods = 0;
		for (int j = 0; j < investList.size(); j++) {
			nums++;
			DrProductInvest invest = investList.get(j);
			DrMember member = drMemberDAO.selectByPrimaryKey(invest.getUid());

			if (totalAmount.compareTo(info.getAmount()) < 0 || info.getType() == 1 || info.getType() == 4
					|| info.getType() == 5) {//
				totalAmount = totalAmount.add(invest.getAmount());

				if (totalAmount.compareTo(info.getAmount()) > 0 && info.getType() != 1 && info.getType() != 5) {// 投资总额大于产品金额，此笔投资只能部分成功
					invest.setFactAmount(info.getAmount().subtract(totalAmount.subtract(invest.getAmount())));// 实际投资金额
																												// =
																												// 产品金额-(已投资总额-本次投资金额)
					totalAmount = totalAmount.subtract(invest.getAmount()).add(invest.getFactAmount());
				} else {
					invest.setFactAmount(invest.getAmount());
				}
				JSONObject profitJson = null;
				if(info.getRepayType() == 1){
					//到期一次性还本付息
					//计算收益
					profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),info.getRate());
					
					BigDecimal profit = invest.getFactAmount().multiply(rate).multiply(new BigDecimal(info.getDeadline())).setScale(2,
							BigDecimal.ROUND_FLOOR);//总的基本利息
					
					DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(), invest.getId(), info.getId(),invest.getFactAmount(), BigDecimal.ZERO,
							profitJson.getBigDecimal("interestProfit"), BigDecimal.ZERO, BigDecimal.ZERO, 0,
							Utils.getDayNumOfAppointDate(now, -info.getDeadline()),profit);
					
					insertRepayInfoList.add(repayinfo);
				}else if (info.getRepayType() == 2){
					//按月付息到期还本
					periods = info.getDeadline()/30;
					profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),info.getRate());
					BigDecimal interestProfit = profitJson.getBigDecimal("interestProfit");//总利息
					BigDecimal interestSubsidy = profitJson.getBigDecimal("interestSubsidy");//贴息
					BigDecimal interest = interestProfit.subtract(interestSubsidy).divide(new BigDecimal(periods), 2, BigDecimal.ROUND_FLOOR);//每月利息
					BigDecimal shouldPrincipal = BigDecimal.ZERO;//应收本金
					
					BigDecimal profit = invest.getFactAmount().multiply(rate).multiply(new BigDecimal(info.getDeadline())).setScale(2,
							BigDecimal.ROUND_FLOOR);//总的基本利息
					
					BigDecimal basicprofit=profit.divide(new BigDecimal(periods), 2,
							BigDecimal.ROUND_FLOOR);// 每月基本利息
					
					BigDecimal subsidy = interestSubsidy.divide(new BigDecimal(periods), 2,
							BigDecimal.ROUND_FLOOR);//每月贴息
					
					Date shouldTime = now;
					for(int i = 1; i <= periods; i ++){
						BigDecimal interestTemp = interest;
						BigDecimal periodinterest=basicprofit;
						BigDecimal periodsubsidy=subsidy;
						if(i == periods){
							//差息
							BigDecimal interestDiff = interestProfit.subtract(interestSubsidy).subtract(interest.multiply(new BigDecimal(periods)));
							//基本利息差
							BigDecimal periodprofitDiff = profit.subtract(basicprofit.multiply(new BigDecimal(periods)));
							//贴息差
							BigDecimal subsidyDiff=interestSubsidy.subtract(subsidy.multiply(new BigDecimal(periods)));
							// 贴息和差息放到第一个月
							interestTemp = interest.add(interestDiff).add(periodsubsidy).add(subsidyDiff);
							periodinterest=periodinterest.add(periodprofitDiff);
							shouldPrincipal = invest.getFactAmount();
						}
						shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
						DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(), invest.getId(), info.getId(),shouldPrincipal, BigDecimal.ZERO,
								interestTemp, BigDecimal.ZERO, BigDecimal.ZERO, 0,
								shouldTime,periodinterest);
						insertRepayInfoList.add(repayinfo);
					}
				}else if(info.getRepayType() == 3 || info.getRepayType() == 4){//等本等息
					if(info.getRepayType() == 3 ){
						periods = info.getDeadline() / 7;//按周回款
					}else{
						periods = info.getDeadline() / 30;//按月回款
					}
					
					profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),
							info.getRate());
					BigDecimal interestProfit = profitJson.getBigDecimal("interestProfit");// 总利息
					BigDecimal interestSubsidy = profitJson.getBigDecimal("interestSubsidy");// 贴息
					BigDecimal interest = interestProfit.subtract(interestSubsidy).divide(new BigDecimal(periods),
							2, BigDecimal.ROUND_FLOOR);//每期利息
					BigDecimal shouldPrincipal = invest.getFactAmount().divide(new BigDecimal(periods),
							2, BigDecimal.ROUND_FLOOR);//每期本金
					
					BigDecimal profit = invest.getFactAmount().multiply(rate).multiply(new BigDecimal(info.getDeadline())).setScale(2,
							BigDecimal.ROUND_FLOOR);//总的基本利息
					
					BigDecimal basicprofit=profit.divide(new BigDecimal(periods), 2,
							BigDecimal.ROUND_FLOOR);// 每周基本利息
					
					BigDecimal subsidy = interestSubsidy.divide(new BigDecimal(periods), 2,
							BigDecimal.ROUND_FLOOR);//每月贴息
					Date shouldTime = now;
					for (int i = 1; i <= periods; i++) {
						BigDecimal interestTemp = interest;
						BigDecimal factprincipal = shouldPrincipal;
						BigDecimal periodinterest=basicprofit;
						BigDecimal periodsubsidy=subsidy;
						if (i == periods) {
							// 差息
							BigDecimal interestDiff = interestProfit.subtract(interestSubsidy)
									.subtract(interest.multiply(new BigDecimal(periods)));
							//本金差
							BigDecimal principal=invest.getFactAmount().subtract(shouldPrincipal.multiply(new BigDecimal(periods)));
							//基本利息差
							BigDecimal periodprofitDiff = profit.subtract(basicprofit.multiply(new BigDecimal(periods)));
							
							//贴息差
							BigDecimal subsidyDiff=interestSubsidy.subtract(subsidy.multiply(new BigDecimal(periods)));
							// 贴息和差息放到第一周
							interestTemp = interest.add(interestDiff).add(periodsubsidy).add(subsidyDiff);
							factprincipal=shouldPrincipal.add(principal);
							periodinterest=periodinterest.add(periodprofitDiff);
						}
						if(info.getRepayType() == 3 ){
							shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -7);//按周回款
						}else{
							shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);//按月回款
						}
						DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(),
								invest.getId(), info.getId(), factprincipal, BigDecimal.ZERO, interestTemp,
								BigDecimal.ZERO, BigDecimal.ZERO, 0, shouldTime,periodinterest);
						insertRepayInfoList.add(repayinfo);
					}
					
				}
				invest.setFactInterest(profitJson.getBigDecimal("interestProfit"));
				invest.setStatus(1);
				// 协议编号
				// 通过当日计息次数生成计息编号invest.setAgreementNo("BYP"+Utils.format(new
				// Date(), "yyMMdd")+info.getType()+new String(ary2));
				System.arraycopy((nums + "").toCharArray(), 0, ary2, ary2.length - (nums + "").toCharArray().length,
						(nums + "").toCharArray().length);
				invest.setAgreementNo("BYP" + Utils.format(new Date(), "yyMMdd") + info.getType() + new String(ary2));

				successInvestList.add(invest);

				// 用户资金
				DrMemberFunds funds = null;
				if (fundsMap.containsKey(invest.getUid())) {
					funds = fundsMap.get(invest.getUid());
				} else {
					funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
				}
				DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParamForProductInterest(invest.getId(),
						4);
				// 投资解冻
				DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getAmount(), 4,
						1, "投资【" + info.getFullName() + "】产品计息，资金解冻");
				funds.setFuiou_balance(funds.getFuiou_balance().add(invest.getAmount()));
				funds.setFuiou_freeze(funds.getFuiou_freeze().subtract(invest.getAmount()));
				fundsLogsList.add(log);
				// 投资成功
				DrMemberFundsLog log1 = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(),
						invest.getFactAmount(), 7, 0, "投资【" + info.getFullName() + "】产品成功");
				funds.setFuiou_balance(funds.getFuiou_balance().subtract(invest.getFactAmount()));
				funds.setFuiou_wprincipal(funds.getFuiou_wprincipal().add(invest.getFactAmount()));
				funds.setFuiou_winterest(funds.getFuiou_winterest().add(invest.getFactInterest()));
				funds.setFuiou_investAmount(funds.getFuiou_investAmount().add(invest.getFactAmount()));
				fundsLogsList.add(log1);

				fundsRecord.setStatus(3);// 投资成功
				fundsRecord
						.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
				fundsRecord.setAmount(invest.getAmount());

				/*
				 * if(profitJson.getBigDecimal("balanceProfit").compareTo(
				 * BigDecimal.ZERO)>0){ DrMemberFundsRecord record = new
				 * DrMemberFundsRecord(info.getId(), invest.getId(),
				 * invest.getUid(), 4, 1,
				 * profitJson.getBigDecimal("balanceProfit"),
				 * funds.getBalance().add(profitJson.getBigDecimal(
				 * "balanceProfit")), 3,
				 * "投资产品【"+info.getFullName()+"】返现，投资金额："+invest.getFactAmount()
				 * , null); drMemberFundsRecordDAO.insert(record);
				 * DrMemberFundsLog logs = new DrMemberFundsLog(invest.getUid(),
				 * fundsRecord.getId(),
				 * profitJson.getBigDecimal("balanceProfit"), 20, 1,
				 * "投资产品【"+info.getFullName()+"】返现，投资金额："+invest.getFactAmount()
				 * ); drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
				 * 
				 * DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11,
				 * invest.getUid(), info.getId(),
				 * profitJson.getBigDecimal("balanceProfit"), 0,
				 * "投资产品【"+info.getFullName()+"】返现，投资金额："+invest.getFactAmount()
				 * , 0);
				 * drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
				 * funds.setBalance(funds.getBalance().add(profitJson.
				 * getBigDecimal("balanceProfit")));
				 * funds.setInvestProfit(funds.getInvestProfit().add(profitJson.
				 * getBigDecimal("balanceProfit"))); }
				 */

				fundsMap.put(funds.getUid(), funds);
				fundsRecordList.add(fundsRecord);
				DrMemberMsg msg = new DrMemberMsg(invest.getUid(), 0, 3, "计息成功", now, 0, 0,
						PropertyUtil.getProperties("tiyanbiaointerestSuccessMsg")
								.replace("${realName}",
										Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName()
												: member.getMobilephone())
								.replace("${interest}", invest.getFactInterest().toString()).replace("${date}",
										Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")));
				msgList.add(msg);
				SysMessageLog smslog = new SysMessageLog(invest.getUid(),
						PropertyUtil.getProperties("tiyanbiaointerestSuccessSms")
								.replace("${realName}",
										Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName()
												: member.getMobilephone())
								.replace("${interest}", invest.getFactInterest().toString()).replace("${date}",
										Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")),
						17, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:00:00"), "yyyy-MM-dd HH:mm:ss"),
						member.getMobilephone());
				smsList.add(smslog);

			} else {
				invest.setFactAmount(BigDecimal.ZERO);
				invest.setFactInterest(BigDecimal.ZERO);
				invest.setStatus(2);
				failId.append(invest.getId() + ",");
				// 用户资金
				DrMemberFunds funds = null;
				if (fundsMap.containsKey(invest.getUid())) {
					funds = fundsMap.get(invest.getUid());
				} else {
					funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
				}
				DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParam(invest.getId(), null);
				// 投资解冻
				DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getAmount(), 4,
						1, "投资解冻");
				funds.setFuiou_balance(funds.getFuiou_balance().add(invest.getAmount()));
				funds.setFuiou_freeze(funds.getFuiou_freeze().subtract(invest.getAmount()));
				fundsLogsList.add(log);

				fundsRecord.setStatus(2);// 投资失败
				fundsRecord
						.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
				fundsRecord.setAmount(invest.getAmount());

				fundsMap.put(funds.getUid(), funds);
				fundsRecordList.add(fundsRecord);
			}

		}
		if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4 && periods > 0) {
			Date shouldTime = now;
			for (int i = 1; i <= periods; i++) {
				if(info.getRepayType() == 2 || info.getRepayType() == 4){//按月
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
				}else if(info.getRepayType() == 3){
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -7);//按周
				}		
				// 添加每期回款信息
				DrProductInfoRepayDetail productRepayDetail = new DrProductInfoRepayDetail(info.getId(), i, shouldTime);
				productRepayDetailList.add(productRepayDetail);
			}

		}
		if (info.getType() != 1 && info.getType() != 4 && info.getType() != 5) {
			DrProductInfo p = new DrProductInfo();
			p.setId(info.getId());
			p.setStatus(8);// 待还款
			p.setIsHot(0);// 已完成的产品取消热销
			drProductInfoDAO.updateDrProductInfo(p);// 把产品状态修改成待还款
		}
		if (Utils.isObjectNotEmpty(info.getSid())) {// 绑定标的的产品募集成功后修改债权状态
			DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(info.getSid());// 标的以及部分贷款项目信息
			drClaimsLoanDAO.updateClaimsLoanStatusById(5, drSubjectInfo.getLid());// 修改债权状态为待还款
		}
		if (totalAmount.compareTo(BigDecimal.ZERO) != 0) {
			DrCompanyFundsLog clog = new DrCompanyFundsLog(1, 0, info.getId(), totalAmount, 1,
					"产品【" + info.getFullName() + "】投资款", null);
			drCompanyFundsLogDAO.insertDrCompanyFundsLog(clog);
		}
		if (nums > 0 && !Utils.isEmptyList(investList)) {
			drProductInvestRepayInfoDAO.batchInsert(insertRepayInfoList);
			if (!productRepayDetailList.isEmpty()) {
				List<DrProductInfoRepayDetail> list=productRepayDetailDAO.selectByPid(info.getId());
				if(list.size()<1){
					productRepayDetailDAO.batchInsert(productRepayDetailList);
				}
				drProductInvestRepayInfoDAO.updateRepayDetailIdByPid(info);// 按月付息产品，产品明细ID与回款记录关联
			}
			drProductInvestDAO.batchUpdate(successInvestList);
			drMemberFundsRecordDAO.batchUpdateMemberFundsRecord(fundsRecordList);
			drMemberFundsDAO.batchUpdateDrMemberFunds((new ArrayList<DrMemberFunds>(fundsMap.values())));
			drMemberFundsLogDAO.batchInsert(fundsLogsList);
			drMemberMsgDAO.batchInsert(msgList);
		}
		if (StringUtils.isNotBlank(failId.toString())) {
			String[] ids = failId.toString().split(",");
			drProductInvestDAO.updateStatusByIds("2", ids);
		}
		// 短信发送
		for (int i = 0; i < smsList.size(); i++) {
			sysMessageLogService.sendMsg(smsList.get(i));
		}
	}

	/**
	 * 生成优选回款信息
	 * 
	 * @throws SQLException
	 */
	@Override
	public void updateProductToEnd(DrProductInfo info, char[] ary2, Integer nums) throws Exception {
		SensorsAnalytics sau = SensorsAnalyticsUtils.getInstance();
    	Map<String, Object> properties = new HashMap<String, Object>();
		List<DrProductInvestRepayInfo> insertRepayInfoList = new ArrayList<DrProductInvestRepayInfo>();// 保存投资成功的记录的回款信息
		List<DrProductInfoRepayDetail> productRepayDetailList = new ArrayList<DrProductInfoRepayDetail>();// 按月付息产品每期回款明细信息
		List<DrProductInvest> successInvestList = new ArrayList<DrProductInvest>();// 保存投资成功的记录
		StringBuffer failId = new StringBuffer();// 保存投资失败的记录ID
		List<DrMemberMsg> msgList = new ArrayList<DrMemberMsg>();// 需要发送的站内信
		List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
		Map<Integer, DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();// 投资用户资金信息
		List<DrMemberFundsLog> fundsLogsList = new ArrayList<DrMemberFundsLog>();// 交易日志
		List<DrMemberFundsRecord> fundsRecordList = new ArrayList<DrMemberFundsRecord>();// 交易记录
		Date now = new Date();
		log.info("产品[" + info.getFullName() + "]开始计息");
		Integer dateType = 1; // 1=30/360 2=30/365 日期模式
		BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(info.getRate().add(info.getActivityRate()), 100),
				dateType == 1 ? 360 : 365);// 日息

		List<DrProductInvest> investList = drProductInvestDAO.getDrProductInvestListByPid(info.getId());// 查询未处理的投资记录
		BigDecimal totalAmount = BigDecimal.ZERO; // 已投资金额
		int periods = 0;
		for (int j = 0; j < investList.size(); j++) {
			nums++;
			DrProductInvest invest = investList.get(j);
			DrMember member = drMemberDAO.selectByPrimaryKey(invest.getUid());

			if (totalAmount.compareTo(info.getAmount()) < 0 || info.getType() == 1 || info.getType() == 4
					|| info.getType() == 5) {//
				totalAmount = totalAmount.add(invest.getAmount());

				if (totalAmount.compareTo(info.getAmount()) > 0 && info.getType() != 1 && info.getType() != 5) {// 投资总额大于产品金额，此笔投资只能部分成功
					invest.setFactAmount(info.getAmount().subtract(totalAmount.subtract(invest.getAmount())));// 实际投资金额
																												// =
																												// 产品金额-(已投资总额-本次投资金额)
					totalAmount = totalAmount.subtract(invest.getAmount()).add(invest.getFactAmount());
				} else {
					invest.setFactAmount(invest.getAmount());
				}
				JSONObject profitJson = null;
				if (info.getRepayType() == 1) {
					// 到期一次性还本付息
					// 计算收益
					profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),
							info.getRate());

					DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(), invest.getId(),
							info.getId(), invest.getFactAmount(), BigDecimal.ZERO,
							profitJson.getBigDecimal("interestProfit"), BigDecimal.ZERO, BigDecimal.ZERO, 0,
							Utils.getDayNumOfAppointDate(now, -info.getDeadline()),null);

					insertRepayInfoList.add(repayinfo);
				} else if (info.getRepayType() == 2) {
					// 按月付息到期还本
					periods = info.getDeadline() / 30;
					profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),
							info.getRate());
					BigDecimal interestProfit = profitJson.getBigDecimal("interestProfit");// 总利息
					BigDecimal interestSubsidy = profitJson.getBigDecimal("interestSubsidy");// 贴息
					BigDecimal interest = interestProfit.subtract(interestSubsidy).divide(new BigDecimal(periods), 2,
							BigDecimal.ROUND_FLOOR);// 每月利息
					BigDecimal shouldPrincipal = BigDecimal.ZERO;// 应收本金
					Date shouldTime = now;
					for (int i = 1; i <= periods; i++) {
						BigDecimal interestTemp = interest;
						if (i == 1) {
							// 差息
							BigDecimal interestDiff = interestProfit.subtract(interestSubsidy)
									.subtract(interest.multiply(new BigDecimal(periods)));
							// 贴息和差息放到第一个月
							interestTemp = interest.add(interestSubsidy).add(interestDiff);
						}
						if (i == periods) {
							shouldPrincipal = invest.getFactAmount();
						}
						shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
						DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(),
								invest.getId(), info.getId(), shouldPrincipal, BigDecimal.ZERO, interestTemp,
								BigDecimal.ZERO, BigDecimal.ZERO, 0, shouldTime,null);
						insertRepayInfoList.add(repayinfo);
					}
				}
				invest.setFactInterest(profitJson.getBigDecimal("interestProfit"));
				invest.setStatus(1);
				// 协议编号
				// 通过当日计息次数生成计息编号invest.setAgreementNo("BYP"+Utils.format(new
				// Date(), "yyMMdd")+info.getType()+new String(ary2));
				System.arraycopy((nums + "").toCharArray(), 0, ary2, ary2.length - (nums + "").toCharArray().length,
						(nums + "").toCharArray().length);
				invest.setAgreementNo("BYP" + Utils.format(new Date(), "yyMMdd") + info.getType() + new String(ary2));

				successInvestList.add(invest);

				// 用户资金
				DrMemberFunds funds = null;
				if (fundsMap.containsKey(invest.getUid())) {
					funds = fundsMap.get(invest.getUid());
				} else {
					funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
				}
				DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParamForProductInterest(invest.getId(),
						4);
				// 投资解冻
				DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getAmount(), 4,
						1, "投资【" + info.getFullName() + "】产品计息，资金解冻");
				funds.setBalance(funds.getBalance().add(invest.getAmount()));
				funds.setFreeze(funds.getFreeze().subtract(invest.getAmount()));
				fundsLogsList.add(log);
				// 投资成功
				DrMemberFundsLog log1 = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(),
						invest.getFactAmount(), 7, 0, "投资【" + info.getFullName() + "】产品成功");
				funds.setBalance(funds.getBalance().subtract(invest.getFactAmount()));
				funds.setWprincipal(funds.getWprincipal().add(invest.getFactAmount()));
				funds.setWinterest(funds.getWinterest().add(invest.getFactInterest()));
				funds.setInvestAmount(funds.getInvestAmount().add(invest.getFactAmount()));
				fundsLogsList.add(log1);

				fundsRecord.setStatus(3);// 投资成功
				fundsRecord
						.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
				fundsRecord.setAmount(invest.getAmount());

				/*
				 * if(profitJson.getBigDecimal("balanceProfit").compareTo(
				 * BigDecimal.ZERO)>0){ DrMemberFundsRecord record = new
				 * DrMemberFundsRecord(info.getId(), invest.getId(),
				 * invest.getUid(), 4, 1,
				 * profitJson.getBigDecimal("balanceProfit"),
				 * funds.getBalance().add(profitJson.getBigDecimal(
				 * "balanceProfit")), 3,
				 * "投资产品【"+info.getFullName()+"】返现，投资金额："+invest.getFactAmount()
				 * , null); drMemberFundsRecordDAO.insert(record);
				 * DrMemberFundsLog logs = new DrMemberFundsLog(invest.getUid(),
				 * fundsRecord.getId(),
				 * profitJson.getBigDecimal("balanceProfit"), 20, 1,
				 * "投资产品【"+info.getFullName()+"】返现，投资金额："+invest.getFactAmount()
				 * ); drMemberFundsLogDAO.insertDrMemberFundsLog(logs);
				 * 
				 * DrCompanyFundsLog cfundsLog = new DrCompanyFundsLog(11,
				 * invest.getUid(), info.getId(),
				 * profitJson.getBigDecimal("balanceProfit"), 0,
				 * "投资产品【"+info.getFullName()+"】返现，投资金额："+invest.getFactAmount()
				 * , 0);
				 * drCompanyFundsLogDAO.insertDrCompanyFundsLog(cfundsLog);
				 * funds.setBalance(funds.getBalance().add(profitJson.
				 * getBigDecimal("balanceProfit")));
				 * funds.setInvestProfit(funds.getInvestProfit().add(profitJson.
				 * getBigDecimal("balanceProfit"))); }
				 */

				fundsMap.put(funds.getUid(), funds);
				fundsRecordList.add(fundsRecord);
				DrMemberMsg msg = new DrMemberMsg(invest.getUid(), 0, 3, "计息成功", now, 0, 0,
						PropertyUtil.getProperties("interestSuccessMsg").replace("${fullName}", info.getFullName())
								.replace("${amount}", invest.getFactAmount().toString())
								.replace("${interest}", invest.getFactInterest().toString()).replace("${date}",
										Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")));
				msgList.add(msg);
				SysMessageLog smslog = new SysMessageLog(invest.getUid(),
						PropertyUtil.getProperties("interestSuccessSms")
								.replace("${realName}",
										Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName()
												: member.getMobilephone())
								.replace("${fullName}", info.getFullName())
								.replace("${amount}", invest.getFactAmount().toString())
								.replace("${interest}", invest.getFactInterest().toString()).replace("${date}",
										Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")),
						17, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:00:00"), "yyyy-MM-dd HH:mm:ss"),
						member.getMobilephone());
				smsList.add(smslog);

			} else {
				invest.setFactAmount(BigDecimal.ZERO);
				invest.setFactInterest(BigDecimal.ZERO);
				invest.setStatus(2);
				failId.append(invest.getId() + ",");
				// 用户资金
				DrMemberFunds funds = null;
				if (fundsMap.containsKey(invest.getUid())) {
					funds = fundsMap.get(invest.getUid());
				} else {
					funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
				}
				DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParam(invest.getId(), null);
				// 投资解冻
				DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getAmount(), 4,
						1, "投资解冻");
				funds.setBalance(funds.getBalance().add(invest.getAmount()));
				funds.setFreeze(funds.getFreeze().subtract(invest.getAmount()));
				fundsLogsList.add(log);

				fundsRecord.setStatus(2);// 投资失败
				fundsRecord
						.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
				fundsRecord.setAmount(invest.getAmount());

				fundsMap.put(funds.getUid(), funds);
				fundsRecordList.add(fundsRecord);
			}
			 getType(properties, info);
			 if(Utils.isObjectNotEmpty(info.getCode())){
				 properties.put("LoanId", info.getCode()); 
			 }
			 DrClaimsLoan drClaimsLoan = drClaimsLoanDAO.selectByPrimaryKey1(info.getId());
			 if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(drClaimsLoan.getLoanUse())){
           	  properties.put("ProjectType",drClaimsLoan.getLoanUse());
           }
           if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(info.getFullName())){
           	properties.put("ProjectName", info.getFullName()+"");
           }
             properties.put("ProjectDeadline", info.getDeadline()+"");
             if(Utils.isObjectNotEmpty(info.getRate())){
            	 properties.put("Rate", info.getRate());
             }
             if(Utils.isObjectNotEmpty(info.getRepayTypeName())){
            	 properties.put("IncomeType", info.getRepayTypeName()+"");
             }
             if(Utils.isObjectNotEmpty(info.getStartDate())){
            	 properties.put("ReleaseTime", info.getStartDate());
             }
             if(Utils.isObjectNotEmpty(invest.getInvestTime())){
            	 properties.put("ValueDate", invest.getInvestTime());
             }
             
             if(Utils.isObjectNotEmpty(info.getExpireDate())){
            	 properties.put("DueDate", info.getExpireDate());
             }
             if(Utils.isObjectNotEmpty(invest.getAmount())){
            	 properties.put("AmountOfInvestment", invest.getAmount());
             }
             if(Utils.isObjectNotEmpty(invest.getFactInterest())){
            	 properties.put("IncomeOfInvestment", invest.getFactInterest());
             }
             
			sau.track(String.valueOf(invest.getUid()), true, "InvestValue" ,properties);
		}
		if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4 && periods > 0) {
			Date shouldTime = now;
			for (int i = 1; i <= periods; i++) {
				if(info.getRepayType() == 2 || info.getRepayType() == 4){//按月
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
				}else if(info.getRepayType() == 3){
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -7);//按周
				}		
				// 添加每期回款信息
				DrProductInfoRepayDetail productRepayDetail = new DrProductInfoRepayDetail(info.getId(), i, shouldTime);
				productRepayDetailList.add(productRepayDetail);
			}

		}
		if (info.getType() != 1 && info.getType() != 4 && info.getType() != 5) {
			DrProductInfo p = new DrProductInfo();
			p.setId(info.getId());
			p.setStatus(8);// 待还款
			p.setIsHot(0);// 已完成的产品取消热销
			drProductInfoDAO.updateDrProductInfo(p);// 把产品状态修改成待还款
		}
		if (Utils.isObjectNotEmpty(info.getSid())) {// 绑定标的的产品募集成功后修改债权状态
			DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(info.getSid());// 标的以及部分贷款项目信息
			drClaimsLoanDAO.updateClaimsLoanStatusById(5, drSubjectInfo.getLid());// 修改债权状态为待还款
		}
		if (totalAmount.compareTo(BigDecimal.ZERO) != 0) {
			DrCompanyFundsLog clog = new DrCompanyFundsLog(1, 0, info.getId(), totalAmount, 1,
					"产品【" + info.getFullName() + "】投资款", null);
			drCompanyFundsLogDAO.insertDrCompanyFundsLog(clog);
		}
		if (nums > 0 && !Utils.isEmptyList(investList)) {
			if (!productRepayDetailList.isEmpty()) {
				List<DrProductInfoRepayDetail> list=productRepayDetailDAO.selectByPid(info.getId());
				if(list.size()<1){
					productRepayDetailDAO.batchInsert(productRepayDetailList);
				}
				drProductInvestRepayInfoDAO.updateRepayDetailIdByPid(info);// 按月付息产品，产品明细ID与回款记录关联
			}
			drProductInvestRepayInfoDAO.batchInsert(insertRepayInfoList);
			drProductInvestDAO.batchUpdate(successInvestList);
			drMemberFundsRecordDAO.batchUpdateMemberFundsRecord(fundsRecordList);
			drMemberFundsDAO.batchUpdateDrMemberFunds((new ArrayList<DrMemberFunds>(fundsMap.values())));
			drMemberFundsLogDAO.batchInsert(fundsLogsList);
			drMemberMsgDAO.batchInsert(msgList);
		}
		if (StringUtils.isNotBlank(failId.toString())) {
			String[] ids = failId.toString().split(",");
			drProductInvestDAO.updateStatusByIds("2", ids);
		}
		// 短信发送
		for (int i = 0; i < smsList.size(); i++) {
			sysMessageLogService.sendMsg(smsList.get(i));
		}
		sau.flush();
	}

	  private void getType(Map<String, Object> properties1, DrProductInfo info1) {
	        if (3==info1.getType()){
	            properties1.put("ProductType","存管新手标");
	        }else if (5==info1.getType()){
	            properties1.put("ProductType","体验标");
	        }else if (Utils.isObjectNotEmpty(info1.getPrizeId())||Utils.isObjectNotEmpty(info1.getAtid())) {
	            properties1.put("ProductType", "活动标");
	        }else {
	            properties1.put("ProductType","普通标");
	        }
	    }
	/**
	 * 计算投资金额以及优惠券可获得收益
	 * 
	 * @param deadline
	 *            产品期限
	 * @param invest
	 *            投资记录
	 * @param dayRate
	 *            计息天数 360/365
	 * @param productType
	 *            产品类型 1=新手，2=安选，3=优选
	 * @return balanceProfit 返还到账户余额的收益，interestProfit 利息收益
	 * @throws SQLException
	 */
	public JSONObject getInvestProfit(Integer deadline, DrProductInvest invest, BigDecimal dayRate, Integer productType,
			BigDecimal rate) throws Exception {
		JSONObject obj = new JSONObject();
		BigDecimal balanceProfit = BigDecimal.ZERO;// 返现金额
		BigDecimal interestProfit = BigDecimal.ZERO;// 应得总利息
		BigDecimal interestSubsidy = BigDecimal.ZERO;// 贴息
		// 双旦活动范围内判断投资记录上是否有双旦活动利率
		if (invest.getInvestTime()
				.after(Utils.parse(PropertyUtil.getProperties("doubleEggStartTime"), "yyyy-MM-dd HH:mm:ss"))
				&& invest.getInvestTime()
						.before(Utils.parse(PropertyUtil.getProperties("doubleEggEndTime"), "yyyy-MM-dd HH:mm:ss"))
				&& Utils.isObjectNotEmpty(invest.getSpecialRate())) {
			dayRate = Utils.nwdBcadd(dayRate, Utils.nwdDivide(Utils.nwdDivide(invest.getSpecialRate(), 100), 360));
		}
		int interestSubsidyDays = Integer.parseInt(Long.toString(Utils.getQuot(Utils.format(new Date(), "yyyy-MM-dd"),
				Utils.format(invest.getInvestTime(), "yyyy-MM-dd")))) - 1;// 贴息天数
		if (productType == 2) {
			deadline = deadline + interestSubsidyDays;
		}
		if (invest.getFid() != null) {// 使用优惠券
			DrMemberFavourable dmf = drMemberFavourableDAO.selectByPrimaryKey(invest.getFid());
			if (dmf.getType() == 1) {// 红包
				balanceProfit = dmf.getAmount();
				interestProfit = invest.getFactAmount().multiply(dayRate).multiply(new BigDecimal(deadline)).setScale(2,
						BigDecimal.ROUND_FLOOR);
				interestSubsidy = invest.getFactAmount().multiply(dayRate).multiply(new BigDecimal(interestSubsidyDays))
						.setScale(2, BigDecimal.ROUND_FLOOR);
				dmf.setProfitAmount(dmf.getAmount());

			} else if (dmf.getType() == 2) {// 加息券
				BigDecimal activityRate = Utils.nwdDivide(Utils.nwdDivide(dmf.getRaisedRates(), 100), 360);// 日息
				interestProfit = invest.getFactAmount().multiply(activityRate.add(dayRate))
						.multiply(new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
				interestSubsidy = invest.getFactAmount().multiply(activityRate.add(dayRate))
						.multiply(new BigDecimal(interestSubsidyDays)).setScale(2, BigDecimal.ROUND_FLOOR);
				dmf.setProfitAmount(interestProfit.subtract(invest.getFactAmount().multiply(dayRate)
						.multiply(new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR)));
			} else if (dmf.getType() == 3) {// 体验金
				interestProfit = dmf.getAmount().add(invest.getFactAmount()).multiply(dayRate)
						.multiply(new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR);
				interestSubsidy = dmf.getAmount().add(invest.getFactAmount()).multiply(dayRate)
						.multiply(new BigDecimal(interestSubsidyDays)).setScale(2, BigDecimal.ROUND_FLOOR);
				dmf.setProfitAmount(interestProfit.subtract(invest.getFactAmount().multiply(dayRate)
						.multiply(new BigDecimal(deadline)).setScale(2, BigDecimal.ROUND_FLOOR)));
			} else if (dmf.getType() == 4) {// 翻倍券
				/*
				 * interestProfit
				 * =invest.getFactAmount().multiply(dmf.getMultiple())
				 * .multiply(dayRate) .multiply(new BigDecimal(deadline))
				 * .setScale(2, BigDecimal.ROUND_FLOOR); interestSubsidy
				 * =invest.getFactAmount().multiply(dmf.getMultiple())
				 * .multiply(dayRate) .multiply(new
				 * BigDecimal(interestSubsidyDays)) .setScale(2,
				 * BigDecimal.ROUND_FLOOR);
				 * dmf.setProfitAmount(interestProfit.subtract(invest.
				 * getFactAmount().multiply(dayRate).multiply(new
				 * BigDecimal(deadline)) .setScale(2, BigDecimal.ROUND_FLOOR)));
				 */

				// 针对基础利率翻倍
				BigDecimal dRate = Utils.nwdDivide(Utils.nwdDivide(rate, 100), 360);// 基础日息
				BigDecimal sourceProfit = invest.getFactAmount().multiply(dayRate)
						.multiply(new BigDecimal(deadline).setScale(2, BigDecimal.ROUND_FLOOR));// 基础收益

				dayRate = Utils.nwdBcadd(Utils.nwdBcsub(dayRate, dRate), Utils.nwdMultiply(dRate, dmf.getMultiple()));// 基础日息*翻倍+其他活动日息

				interestProfit = invest.getFactAmount().multiply(dayRate).multiply(new BigDecimal(deadline)).setScale(2,
						BigDecimal.ROUND_FLOOR);
				interestSubsidy = invest.getFactAmount().multiply(dayRate).multiply(new BigDecimal(interestSubsidyDays))
						.setScale(2, BigDecimal.ROUND_FLOOR);
				dmf.setProfitAmount(interestProfit.subtract(sourceProfit));
			}
			dmf.setStatus(1);
			drMemberFavourableDAO.updateByPrimaryKey(dmf);
		} else if (Utils.isObjectNotEmpty(invest.getExperience())) {
			Map<String, Object> map = new HashMap<>();
			map.put("experience", invest.getExperience());
			map.put("uid", invest.getUid());

			BigDecimal experienceAmount = drMemberFavourableDAO.getExperienceAmount(map);
			interestProfit = experienceAmount.multiply(dayRate).multiply(new BigDecimal(deadline)).setScale(2,
					BigDecimal.ROUND_FLOOR);
			map.put("deadline", deadline);
			map.put("dayRate", dayRate);
			drMemberFavourableDAO.updateByExperience(map);
		} else {
			interestProfit = invest.getFactAmount().multiply(dayRate).multiply(new BigDecimal(deadline)).setScale(2,
					BigDecimal.ROUND_FLOOR);
			interestSubsidy = invest.getFactAmount().multiply(dayRate).multiply(new BigDecimal(interestSubsidyDays))
					.setScale(2, BigDecimal.ROUND_FLOOR);
		}
		obj.put("balanceProfit", balanceProfit);
		obj.put("interestProfit", interestProfit);
		obj.put("interestSubsidy", interestSubsidy);
		return obj;
	}

	@Override
	public List<DrProductPic> getDrProductPicByPid(Integer pid) {
		return drProductPicDAO.getDrProductPicByPid(pid);
	}

	@Override
	public void deleteDrProductPicById(Integer id) throws SQLException {
		drProductPicDAO.deleteDrProductPicById(id);
	}

	@Override
	public BaseResult getInvestmentPoolList(DrProductInfo drProductInfo, PageInfo pi) {
		Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		map.put("code", drProductInfo.getCode());
		map.put("simpleName", drProductInfo.getSimpleName());
		map.put("type", 2);
		map.put("status", new Integer[] { 6, 8, 9 });// 募集完成，还款中，还款完成
		map.put("searchStartDate", drProductInfo.getSearchStartDate());
		map.put("searchEndDate", drProductInfo.getSearchEndDate());
		map.put("repayType", drProductInfo.getRepayType());

		List<DrProductInfo> list = drProductInfoDAO.getNeedMatchingProductList(map);
		Integer total = drProductInfoDAO.getDrProductInfoCounts(map);
		pi.setRows(list);
		pi.setTotal(total);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public List<DrProductExtend> getDrProductExtendByPid(int pid) {
		return drProductExtendDAO.getDrProductExtendByPid(pid);
	}

	@Override
	public List<DrProductExtend> getDrProductExtendBySid(int sid) {
		return drProductExtendDAO.getDrProductExtendBySid(sid);
	}

	@Override
	public List<DrProductInfo> selectRaiseSuccesProductInfo() {
		return drProductInfoDAO.selectRaiseSuccesProductInfo();
	}

	@Override
	public List<Map<String, Object>> selectDrProductInfoList(Map<String, Object> map) {
		return drProductInfoDAO.selectDrProductInfoList(map);
	}

	@Override
	public PageInfo getSubjectProductList(Map<String, Object> map, PageInfo pi) {
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<Map<String, Object>> list = drProductInvestDAO.selectInvestMemberInfoListByParam(map);
		Map<String, Object> m = drProductInvestDAO.selectInvestMemberInfoListCountByParam(map);
		List<Map<String, Object>> footer = drProductInvestDAO.selectInvestPageCountByParam(map);
		pi.setRows(list);
		pi.setTotal(Integer.parseInt(m.get("total").toString()));
		Map<String, Object> ss = new HashMap<String, Object>();
		footer.add(m);
		pi.setFooter(footer);
		return pi;
	}

	@Override
	public BaseResult getProductLoanList(DrProductInfo drProductInfo,String fullStartDate,String fullEndDate,PageInfo pi) {
		Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map = getExtractMap(drProductInfo);
		if(StringUtils.isNotEmpty(fullStartDate)){
			map.put("fullStartDate", fullStartDate);// 募集成功时间
		}
		if(StringUtils.isNotEmpty(fullEndDate)){
			map.put("fullEndDate", fullEndDate);// 募集成功时间
		}
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrProductInfo> list = drProductInfoDAO.getDrProductLoanList(map);
		Integer total = drProductInfoDAO.getDrProductLoanCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	

	@Override
	public void updateDrProductLoanStatus(Map<String, Object> map) {
		drProductInfoDAO.updateDrProductLoanStatus(map);
	}

	@Override
	public void updateReFundDrProductLoanStatus(Integer id) {
		drProductInfoDAO.updateReFundDrProductLoanStatus(id);
	}

	@Override
	public List<DrProductInfo> getDrProductLoanListByParam(DrProductInfo drProductInfo,String fullStartDate,String fullEndDate) {
		Map<String, Object> map = getExtractMap(drProductInfo);
		if(StringUtils.isNotEmpty(fullStartDate)){
			map.put("fullStartDate", fullStartDate);// 募集成功时间
		}
		if(StringUtils.isNotEmpty(fullEndDate)){
			map.put("fullEndDate", fullEndDate);// 募集成功时间
		}
		return drProductInfoDAO.getDrProductLoanList(map);
	}

	/**
	 * 抽取查询放款列表功能的较通用部分
	 * 
	 * @param drProductInfo
	 */
	public Map<String, Object> getExtractMap(DrProductInfo drProductInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("simpleName", drProductInfo.getSimpleName());
		map.put("loanStatus", drProductInfo.getLoanStatus());
		map.put("loanName", drProductInfo.getLoanName());
		map.put("refundStartDate", Utils.format(drProductInfo.getRefundStartDate(), "yyyy-MM-dd"));// 回款时间
		map.put("refundEndDate", Utils.format(drProductInfo.getRefundEndDate(), "yyyy-MM-dd"));
		map.put("fullStartDate", Utils.format(drProductInfo.getFullStartDate(), "yyyy-MM-dd"));// 募集成功时间
		map.put("fullEndDate", Utils.format(drProductInfo.getFullEndDate(), "yyyy-MM-dd"));
		map.put("startactLoanTime", Utils.format(drProductInfo.getStartactLoanTime(), "yyyy-MM-dd"));// 实际放款时间
		map.put("endactLoanTime", Utils.format(drProductInfo.getEndactLoanTime(), "yyyy-MM-dd"));
		map.put("repayType", drProductInfo.getRepayType());
		map.put("project_no", drProductInfo.getProject_no());
		map.put("status", drProductInfo.getStatus());
		map.put("type", drProductInfo.getType());
		return map;
	}

	/**
	 * 币优铺金服项目导出回款表导出
	 */
	@Override
	public List<Map<String, Object>> getReturnNoticeList(Integer id) {
		return drProductInfoDAO.getReturnNoticeList(id);
	}

	@Override
	public List<Map<String, Object>> getReturnNoticeRecordList(Map<String, Object> map) {
		return drProductInfoDAO.getReturnNoticeRecordList(map);
	}

	@Override
	public BaseResult getDrExperienceProductInfoList(DrProductInfo drProductInfo, PageInfo pi) {
		Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("simpleName", drProductInfo.getSimpleName());
		map.put("code", drProductInfo.getCode());
		if (drProductInfo.getType() != null) {
			map.put("type", drProductInfo.getType());
		}
		if (drProductInfo.getStatus() == null) {
			map.put("status", drProductInfo.getStatus());
		} else {
			if (drProductInfo.getStatus() == 100) {// 显示审核页面
				map.put("status", new Integer[] { 1, 3 });
			} else {
				map.put("status", new Integer[] { drProductInfo.getStatus() });
			}
		}
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrProductInfo> list = drProductInfoDAO.getDrProductInfoList(map);
		for (DrProductInfo it : list) {
			if (it.getStatus() == 5) {
				it.setExperienceName("开启");
			} else if (it.getStatus() == 6) {
				it.setExperienceName("停用");
			}
		}
		Integer total = drProductInfoDAO.getDrProductInfoCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public void updateDrProductLoanStatusSQ(Map<String, Object> map) {
		drProductInfoDAO.updateDrProductLoanStatusSQ(map);
	}

	@Override
	public BaseResult getProductLoanListSQ(DrProductInfo drProductInfo, PageInfo pi) {
		Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map = getExtractMap(drProductInfo);
		map.put("offset", pi.getPageInfo().getOffset());
		map.put("limit", pi.getPageInfo().getLimit());
		List<DrProductInfo> list = drProductInfoDAO.getDrProductLoanListSQ(map);
		Integer total = drProductInfoDAO.getDrProductLoanCountsSQ(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}

	@Override
	public DrProductInfo getDrProductInfoByidSQ(Integer id) {
		return drProductInfoDAO.getDrProductInfoByidSQ(id);
	}

	@Override
	public DrProductInfo getDrProductInfoByPrizeId(Integer PrizeId) {
		return drProductInfoDAO.getDrProductInfoByPrizeId(PrizeId);
	}

	/**
	 * 1.查询出来type=2的并且当前时间>=成立期限 并且以募集金额=0 的产品list
	 * 2.把list遍历，每个产品的状态改为7(募集失败)，并且把每个募集金额回滚到对应标的的未使用金额中去，同时还要吧关联的商品下架
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public void getDrProductInfoByType(Integer type) {
		try {
			List<DrProductInfo> productList = drProductInfoDAO.getDrProductInfoByType(type);
			if (productList.size() > 0) {
				for (DrProductInfo drproductInfo : productList) {
					// 更新状态
					drproductInfo.setStatus(7);
					drproductInfo.setUpdDate(new Date());
					drProductInfoDAO.updateDrProductInfo(drproductInfo);
					DrSubjectInfo subjectInfo = new DrSubjectInfo();
					subjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid1(drproductInfo.getSid());
					// 标的金额回滚
					BigDecimal SurplusAmount = subjectInfo.getSurplusAmount();// 标的剩余金额
					subjectInfo.setSurplusAmount(SurplusAmount.add(drproductInfo.getAmount()));
					subjectInfo.setUpdDate(new Date());
					drSubjectInfoDAO.updateDrSubjectInfo(subjectInfo);
					// 下架关联商品
					if (!Utils.isObjectEmpty(drproductInfo.getPrizeId())) {
						JsProductPrize jsProductPrize = new JsProductPrize();
						jsProductPrize = jsProductPrizeDAO.getJsProductPrizeById(drproductInfo.getPrizeId());
						jsProductPrize.setStatus(2);// 2 下架
						jsProductPrize.setUpdateTime(new Date());
						jsProductPrizeDAO.updateJsProductPrize(jsProductPrize);
					}
				}
			} else {
				log.info("未查询到成立时间已到，并且以募集金额等于0的记录");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void productFullRemind(String mobliePhone) throws Exception {
		if (Utils.isObjectEmpty(mobliePhone))
			return;
		List<DrProductInfo> list = drProductInfoDAO.productFullRemind();
		if (Utils.isEmptyList(list))
			return;

		StringBuffer content = new StringBuffer("今天必须要满标的产品有:");

		for (DrProductInfo info : list) {
			content.append("  编号:" + info.getCode() + ",名称:" + info.getFullName() + ",总金额:" + info.getAmount()
					+ ",剩余募集金额:" + info.getSurplusAmount());
		}
		SysMessageLog logs = new SysMessageLog(0, content.toString(), null, null, mobliePhone);
		int result = sysMessageLogService.sendMsg(logs);
		if (result < 0)
			log.info("满标提醒短信发送失败:" + content.toString());
	}

	@Override
	public BaseResult updateLoanStatus(int pid, int useKy, String actLoanTime,String tran_flowid) throws Exception {
		BaseResult result = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断放款记录已存在
		JsLoanRecord jlr = jsLoanRecordDAO.selectByPrimaryKey(pid);
		if (Utils.isObjectNotEmpty(jlr)) {
			result.setErrorMsg("放款记录已存在");
		} else {
			// 修改为已放款
			if(tran_flowid!=null){
				map.put("mchnt_txn_ssn", tran_flowid);
				}
			map.put("id", pid);
			map.put("actLoanTime", actLoanTime == null ? new Date() : actLoanTime);
			drProductInfoDAO.updateDrProductLoanStatus(map);
			// 记录放款记录
			map.clear();
			map.put("pid", pid);
			map.put("useKy", useKy);
			jsLoanRecordDAO.insertByPid(map);

			result.setSuccess(true);
			result.setMsg("放款成功");
		}
		return result;
	}

	@Override
	public int getDrProductInfoListCountByMap(Map<String, Object> map) {
		return drProductInfoDAO.getDrProductInfoListCountByMap(map);
	}

	@Override
	public List<DrProductInfo> getDrProductInfoExperience() {
		List<DrProductInfo> list = drProductInfoDAO.getDrProductInfoExperience();
		if (!Utils.isEmptyList(list)) {
			for (DrProductInfo info : list) {
				List<DrProductPic> drProductPic = drProductPicDAO.getDrProductPicByPid(info.getId());
				info.setDrProductPic(drProductPic);
			}
		}
		return list;
	}

	@Override
	public void updateDrProductInfo(DrProductInfo drProductInfo) throws SQLException {
		drProductInfoDAO.updateDrProductInfo(drProductInfo);
	}

	@Override
	public boolean productUpdateToDeposits(DrProductInfo drProductInfo) {
		boolean flag = false;
		String errorMsg = "";
		try {

			String project_st = null;
			
			if(drProductInfo.getStatus() == 6 &&  "00".equals(drProductInfo.getProject_st())){
				project_st = "01";
			}else if(drProductInfo.getStatus() == 8 &&  "01".equals(drProductInfo.getProject_st())){
				project_st = "02";
			}
			
			if(project_st != null){
				drProductInfo.setProject_st(project_st);
				BaseResult result= new BaseResult();
//				BaseResult result= FuiouConfig.productUpdate(drProductInfo);
				if (result.isSuccess()) {
					drProductInfo.setProject_st(project_st);
					drProductInfoDAO.updateDrProductInfo(drProductInfo);
					flag=true;
				} else {// 项目更新失败
					errorMsg = result.getErrorMsg();
					flag = false;
				}					
			} else {// 项目更新失败
				flag = false;
				errorMsg = "项目[存管更新]执行失败:项目属性不匹配";
			}
			} catch (Exception e) {
				flag = false;
				log.error(e.getMessage(), e);
			} finally {
				if (!flag) {
					try {
						SmsSendUtil.sendMsg("15800784479,15221219118,15221219118", drProductInfo.getFullName()+errorMsg);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		return flag;
	}

	@Override
	public List<DrProductInfo>  selectDepositsRepayFail(Map<String, Object> map) {
		
		return drProductInfoDAO.selectDepositsRepayFail(map);
	}


	/**
	 * 生成优选回款信息
	 * 
	 * @throws SQLException
	 */
	@Override
	public void updateFuiouProductToEnd(DrProductInfo info, char[] ary2, Integer nums) throws Exception {
		SensorsAnalytics sau = SensorsAnalyticsUtils.getInstance();
    	Map<String, Object> properties = new HashMap<String, Object>();
		List<DrProductInvestRepayInfo> insertRepayInfoList = new ArrayList<DrProductInvestRepayInfo>();// 保存投资成功的记录的回款信息
		List<DrProductInfoRepayDetail> productRepayDetailList = new ArrayList<DrProductInfoRepayDetail>();// 按月付息产品每期回款明细信息
		List<DrProductInvest> successInvestList = new ArrayList<DrProductInvest>();// 保存投资成功的记录
		StringBuffer failId = new StringBuffer();// 保存投资失败的记录ID
		List<DrMemberMsg> msgList = new ArrayList<DrMemberMsg>();// 需要发送的站内信
		List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
		Map<Integer, DrMemberFunds> fundsMap = new HashMap<Integer, DrMemberFunds>();// 投资用户资金信息
		List<DrMemberFundsLog> fundsLogsList = new ArrayList<DrMemberFundsLog>();// 交易日志
		List<DrMemberFundsRecord> fundsRecordList = new ArrayList<DrMemberFundsRecord>();// 交易记录
		Date now = new Date();
		log.info("产品[" + info.getFullName() + "]开始计息");
		Integer dateType = 1; // 1=30/360 2=30/365 日期模式
		BigDecimal dayRate = Utils.nwdDivide(Utils.nwdDivide(info.getRate().add(info.getActivityRate()), 100),
				dateType == 1 ? 360 : 365);// 日息
		
		BigDecimal rate = Utils.nwdDivide(Utils.nwdDivide(info.getRate(), 100),
				dateType == 1 ? 360 : 365);// 日息不加活动利率

		List<DrProductInvest> investList = drProductInvestDAO.getFuiouDrProductInvestListByPid(info.getId());// 查询所有的投资记录
		
		
		DrClaimsLoan loan = drClaimsInfoService.getDrClaimsLoanBySid(info.getSid());
		DrClaimsCustomer customer = drClaimsInfoService.getDrClaimsCustomerByLid(loan.getId());// 借款人
		
		int total = investList.size();// 产品下总的投资笔数
		int successTotal = 0;// 投资成功笔数
		BigDecimal totalAmount = BigDecimal.ZERO; // 已投资金额
		int periods = 0;
		for (int j = 0; j < investList.size(); j++) {
			nums++;
			DrProductInvest invest = investList.get(j);
			DrMember member = drMemberDAO.selectByPrimaryKey(invest.getUid());

			if (totalAmount.compareTo(info.getAmount()) < 0 || info.getType() == 1 || info.getType() == 4
					|| info.getType() == 5) {//
				totalAmount = totalAmount.add(invest.getAmount());
				if (totalAmount.compareTo(info.getAmount()) > 0 && info.getType() != 1 && info.getType() != 5) {// 投资总额大于产品金额，此笔投资只能部分成功
					invest.setFactAmount(info.getAmount().subtract(totalAmount.subtract(invest.getAmount())));// 实际投资金额
																												// =
																												// 产品金额-(已投资总额-本次投资金额)
					totalAmount = totalAmount.subtract(invest.getAmount()).add(invest.getFactAmount());
				} else {
					invest.setFactAmount(invest.getAmount());
				}

				// 对于状态是为未处理或者失败数据进行冻结到冻结
				if (invest.getStatus() == 0 /*|| invest.getStatus() == 4*/) {
					
					/*Map<String, String> params = new HashMap<String, String>();
					String remitMchntTxnSsn = Utils.createOrderNo(6, invest.getId(), "");// 流水号
					params.put("mchnt_txn_ssn", remitMchntTxnSsn);
					params.put("out_cust_no", member.getMobilephone());
					params.put("in_cust_no", customer.getPhone());
					params.put("rem", "");
					params.put("amt", "" + invest.getFactAmount());// 精确到分
					BaseResult br = FuiouConfig.transferBuAndFreeze2Freeze(params);
					if (!br.isSuccess()) {
						invest.setStatus(4);// 失败
						if(br.getErrorMsg().length()>250){
							invest.setMessage(br.getMsg().substring(0, 250));// 失败原因
						}else{
							invest.setMessage(br.getMsg());// 失败原因
						}
						invest.setRemitMchntTxnSsn(remitMchntTxnSsn);//流水号
						drProductInvestDAO.updateFuiouProductInvest(invest);
						continue;
					}else{
						
						invest.setRemitMchntTxnSsn(remitMchntTxnSsn);//流水号
						drProductInvestDAO.updateFuiouProductInvest(invest);
					}*/
					JSONObject profitJson = null;
					if (info.getRepayType() == 1) {
						// 到期一次性还本付息
						// 计算收益
						profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),
								info.getRate());
						
						BigDecimal profit = invest.getFactAmount().multiply(rate).multiply(new BigDecimal(info.getDeadline())).setScale(2,
								BigDecimal.ROUND_FLOOR);//总的基本利息

						DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(),
								invest.getId(), info.getId(), invest.getFactAmount(), BigDecimal.ZERO,
								profitJson.getBigDecimal("interestProfit"), BigDecimal.ZERO, BigDecimal.ZERO, 0,
								Utils.getDayNumOfAppointDate(now, -info.getDeadline()),profit);

						insertRepayInfoList.add(repayinfo);
					} else if (info.getRepayType() == 2) {
						// 按月付息到期还本
						periods = info.getDeadline() / 30;
						profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),
								info.getRate());
						BigDecimal interestProfit = profitJson.getBigDecimal("interestProfit");// 总利息
						BigDecimal interestSubsidy = profitJson.getBigDecimal("interestSubsidy");// 贴息
						BigDecimal interest = interestProfit.subtract(interestSubsidy).divide(new BigDecimal(periods),
								2, BigDecimal.ROUND_FLOOR);// 每月利息
						BigDecimal shouldPrincipal = BigDecimal.ZERO;// 应收本金
						
						BigDecimal profit = invest.getFactAmount().multiply(rate).multiply(new BigDecimal(info.getDeadline())).setScale(2,
								BigDecimal.ROUND_FLOOR);//总的基本利息
						
						BigDecimal basicprofit=profit.divide(new BigDecimal(periods), 2,
								BigDecimal.ROUND_FLOOR);// 每月基本利息
						
						BigDecimal subsidy = interestSubsidy.divide(new BigDecimal(periods), 2,
								BigDecimal.ROUND_FLOOR);//每月贴息
						
						Date shouldTime = now;
						
						for (int i = 1; i <= periods; i++) {
							BigDecimal interestTemp = interest;
							BigDecimal periodinterest=basicprofit;
							BigDecimal periodsubsidy=subsidy;
							if (i == periods) {
								// 差息
								BigDecimal interestDiff = interestProfit.subtract(interestSubsidy)
										.subtract(interest.multiply(new BigDecimal(periods)));
								//基本利息差
								BigDecimal periodprofitDiff = profit.subtract(basicprofit.multiply(new BigDecimal(periods)));
								
								//贴息差
								BigDecimal subsidyDiff=interestSubsidy.subtract(subsidy.multiply(new BigDecimal(periods)));

								// 贴息和差息放到第一个月
								interestTemp = interest.add(interestDiff).add(periodsubsidy).add(subsidyDiff);
								periodinterest=periodinterest.add(periodprofitDiff);
								
								shouldPrincipal = invest.getFactAmount();
							}
							shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
							DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(),
									invest.getId(), info.getId(), shouldPrincipal, BigDecimal.ZERO, interestTemp,
									BigDecimal.ZERO, BigDecimal.ZERO, 0, shouldTime,periodinterest);
							insertRepayInfoList.add(repayinfo);
						}
					}else if(info.getRepayType() == 3 || info.getRepayType() == 4){//等本等息
						if(info.getRepayType() == 3 ){
							periods = info.getDeadline() / 7;//按周回款
						}else{
							periods = info.getDeadline() / 30;//按月回款
						}
						
						profitJson = this.getInvestProfit(info.getDeadline(), invest, dayRate, info.getType(),
								info.getRate());
						BigDecimal interestProfit = profitJson.getBigDecimal("interestProfit");// 总利息
						BigDecimal interestSubsidy = profitJson.getBigDecimal("interestSubsidy");// 贴息
						BigDecimal interest = interestProfit.subtract(interestSubsidy).divide(new BigDecimal(periods),
								2, BigDecimal.ROUND_FLOOR);//每期利息
						BigDecimal shouldPrincipal = invest.getFactAmount().divide(new BigDecimal(periods),
								2, BigDecimal.ROUND_FLOOR);//每期本金
						
						BigDecimal profit = invest.getFactAmount().multiply(rate).multiply(new BigDecimal(info.getDeadline())).setScale(2,
								BigDecimal.ROUND_FLOOR);//总的基本利息
						
						BigDecimal basicprofit=profit.divide(new BigDecimal(periods), 2,
								BigDecimal.ROUND_FLOOR);// 每周基本利息
						
						BigDecimal subsidy = interestSubsidy.divide(new BigDecimal(periods), 2,
								BigDecimal.ROUND_FLOOR);//每月贴息
						Date shouldTime = now;
						for (int i = 1; i <= periods; i++) {
							BigDecimal interestTemp = interest;
							BigDecimal factprincipal = shouldPrincipal;
							BigDecimal periodinterest=basicprofit;
							BigDecimal periodsubsidy=subsidy;
							if (i == periods) {
								// 差息
								BigDecimal interestDiff = interestProfit.subtract(interestSubsidy)
										.subtract(interest.multiply(new BigDecimal(periods)));
								//本金差
								BigDecimal principal=invest.getFactAmount().subtract(shouldPrincipal.multiply(new BigDecimal(periods)));
								//基本利息差
								BigDecimal periodprofitDiff = profit.subtract(basicprofit.multiply(new BigDecimal(periods)));
								
								//贴息差
								BigDecimal subsidyDiff=interestSubsidy.subtract(subsidy.multiply(new BigDecimal(periods)));
								// 贴息和差息放到第一周
								interestTemp = interest.add(interestDiff).add(periodsubsidy).add(subsidyDiff);
								factprincipal=shouldPrincipal.add(principal);
								periodinterest=periodinterest.add(periodprofitDiff);
							}
							if(info.getRepayType() == 3 ){
								shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -7);//按周回款
							}else{
								shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);//按月回款
							}
							
							DrProductInvestRepayInfo repayinfo = new DrProductInvestRepayInfo(invest.getUid(),
									invest.getId(), info.getId(), factprincipal, BigDecimal.ZERO, interestTemp,
									BigDecimal.ZERO, BigDecimal.ZERO, 0, shouldTime,periodinterest);
							insertRepayInfoList.add(repayinfo);
						}
						
					}
					invest.setFactInterest(profitJson.getBigDecimal("interestProfit"));
					invest.setStatus(1);
					// 协议编号
					// 通过当日计息次数生成计息编号invest.setAgreementNo("BYP"+Utils.format(new
					// Date(), "yyMMdd")+info.getType()+new String(ary2));
					System.arraycopy((nums + "").toCharArray(), 0, ary2, ary2.length - (nums + "").toCharArray().length,
							(nums + "").toCharArray().length);
					invest.setAgreementNo(
							"BYP" + Utils.format(new Date(), "yyMMdd") + info.getType() + new String(ary2));

					successInvestList.add(invest);

					// 用户资金
					DrMemberFunds funds = null;
					if (fundsMap.containsKey(invest.getUid())) {
						funds = fundsMap.get(invest.getUid());
					} else {
						funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
					}
					DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO
							.selectByParamForProductInterest(invest.getId(), 4);
					// 投资解冻
					DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(),
							invest.getAmount(), 4, 1, "投资【" + info.getFullName() + "】产品计息，资金解冻");
					funds.setFuiou_balance(funds.getFuiou_balance().add(invest.getAmount()));
					funds.setFuiou_freeze(funds.getFuiou_freeze().subtract(invest.getAmount()));
					fundsLogsList.add(log);
					// 投资成功
					DrMemberFundsLog log1 = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(),
							invest.getFactAmount(), 7, 0, "投资【" + info.getFullName() + "】产品成功");
					funds.setFuiou_balance(funds.getFuiou_balance().subtract(invest.getFactAmount()));
					funds.setFuiou_wprincipal(funds.getFuiou_wprincipal().add(invest.getFactAmount()));
					funds.setFuiou_winterest(funds.getFuiou_winterest().add(invest.getFactInterest()));
					funds.setFuiou_investAmount(funds.getFuiou_investAmount().add(invest.getFactAmount()));
					fundsLogsList.add(log1);

					fundsRecord.setStatus(3);// 投资成功
					fundsRecord.setBalance(
							fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
					fundsRecord.setAmount(invest.getAmount());

					fundsMap.put(funds.getUid(), funds);
					fundsRecordList.add(fundsRecord);
					DrMemberMsg msg = new DrMemberMsg(invest.getUid(), 0, 3, "计息成功", now, 0, 0,
							PropertyUtil.getProperties("interestSuccessMsg").replace("${fullName}", info.getFullName())
									.replace("${amount}", invest.getFactAmount().toString())
									.replace("${interest}", invest.getFactInterest().toString()).replace("${date}",
											Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")));
					msgList.add(msg);
					SysMessageLog smslog = new SysMessageLog(invest.getUid(),
							PropertyUtil.getProperties("interestSuccessSms")
									.replace("${realName}",
											Utils.isObjectNotEmpty(member.getRealName()) ? member.getRealName()
													: member.getMobilephone())
									.replace("${fullName}", info.getFullName())
									.replace("${amount}", invest.getFactAmount().toString())
									.replace("${interest}", invest.getFactInterest().toString()).replace("${date}",
											Utils.getDayNumOfAppointDate(now, -info.getDeadline(), "yyyy年MM月dd日")),
							17, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:00:00"), "yyyy-MM-dd HH:mm:ss"),
							member.getMobilephone());
					smsList.add(smslog);	
					successTotal++;
				}
			} else {
				invest.setFactAmount(BigDecimal.ZERO);
				invest.setFactInterest(BigDecimal.ZERO);
				invest.setStatus(2);
				failId.append(invest.getId() + ",");
				// 用户资金
				DrMemberFunds funds = null;
				if (fundsMap.containsKey(invest.getUid())) {
					funds = fundsMap.get(invest.getUid());
				} else {
					funds = drMemberFundsDAO.queryDrMemberFundsByUid(invest.getUid());
				}
				DrMemberFundsRecord fundsRecord = drMemberFundsRecordDAO.selectByParam(invest.getId(), null);
				// 投资解冻
				DrMemberFundsLog log = new DrMemberFundsLog(invest.getUid(), fundsRecord.getId(), invest.getAmount(), 4,
						1, "投资解冻");
				funds.setFuiou_balance(funds.getFuiou_balance().add(invest.getAmount()));
				funds.setFuiou_freeze(funds.getFuiou_freeze().subtract(invest.getAmount()));
				fundsLogsList.add(log);

				fundsRecord.setStatus(2);// 投资失败
				fundsRecord
						.setBalance(fundsRecord.getBalance().add(invest.getAmount().subtract(invest.getFactAmount())));
				fundsRecord.setAmount(invest.getAmount());

				fundsMap.put(funds.getUid(), funds);
				fundsRecordList.add(fundsRecord);
			}
			 getType(properties, info);
			 if(Utils.isObjectNotEmpty(info.getCode())){
				 properties.put("LoanId", info.getCode()); 
			 }
			 DrClaimsLoan drClaimsLoan = drClaimsLoanDAO.selectByPrimaryKey1(info.getId());
			 if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(drClaimsLoan.getLoanUse())){
           	  properties.put("ProjectType",drClaimsLoan.getLoanUse());
            }
            if(Utils.isObjectNotEmpty(drClaimsLoan) && Utils.isObjectNotEmpty(info.getFullName())){
           	 properties.put("ProjectName", info.getFullName()+"");
            }
             properties.put("ProjectDeadline", info.getDeadline()+"");
             if(Utils.isObjectNotEmpty(info.getRate())){
            	 properties.put("Rate", info.getRate());
             }
             if(Utils.isObjectNotEmpty(info.getRepayTypeName())){
            	 properties.put("IncomeType", info.getRepayTypeName()+"");
             }
             if(Utils.isObjectNotEmpty(info.getStartDate())){
            	 properties.put("ReleaseTime", info.getStartDate());
             }
             if(Utils.isObjectNotEmpty(invest.getInvestTime())){
            	 properties.put("ValueDate", invest.getInvestTime());
             }
             
             if(Utils.isObjectNotEmpty(info.getExpireDate())){
            	 properties.put("DueDate", info.getExpireDate());
             }
             if(Utils.isObjectNotEmpty(invest.getAmount())){
            	 properties.put("AmountOfInvestment", invest.getAmount());
             }
             if(Utils.isObjectNotEmpty(invest.getFactInterest())){
            	 properties.put("IncomeOfInvestment", invest.getFactInterest());
             }
			sau.track(String.valueOf(invest.getUid()), true, "InvestValue" ,properties);
		}
		if (info.getRepayType() == 2 || info.getRepayType() == 3 || info.getRepayType() == 4 && periods > 0) {
			Date shouldTime = now;
			for (int i = 1; i <= periods; i++) {
				if(info.getRepayType() == 2 || info.getRepayType() == 4){//按月
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -30);
				}else if(info.getRepayType() == 3){
					shouldTime = Utils.getDayNumOfAppointDate(shouldTime, -7);//按周
				}		
				// 添加每期回款信息
				DrProductInfoRepayDetail productRepayDetail = new DrProductInfoRepayDetail(info.getId(), i, shouldTime);
				productRepayDetailList.add(productRepayDetail);
			}

		}
		if (successTotal == total && successTotal!=0) {
			DrProductInfo p = new DrProductInfo();
			// 解冻
			/*Map<String, String> thawparams = new HashMap<String, String>();
			String mchnt_txn_ssn=Utils.createOrderNo(6, info.getId(), "");
			thawparams.put("mchnt_txn_ssn", mchnt_txn_ssn);
			thawparams.put("project_no", info.getProject_no());
			thawparams.put("user_id", customer.getUser_id());
			thawparams.put("fuiou_acnt", customer.getFuiou_acnt());
			thawparams.put("amt",  "" + totalAmount.multiply(new BigDecimal(100)).intValue());
			BaseResult re = FuiouConfig.thaw(thawparams);
			if (!re.isSuccess()) {//失败
				p.setThaw(2);
			}else{
				p.setThaw(1);
			}*/
			p.setThaw(1);//待解冻

			if (info.getType() != 1 && info.getType() != 4 && info.getType() != 5) {
				p.setId(info.getId());
				p.setStatus(8);// 待还款
				p.setIsHot(0);// 已完成的产品取消热销
				/*p.setMchntTxnSsn(mchnt_txn_ssn);*/
				drProductInfoDAO.updateDrProductInfo(p);// 把产品状态修改成待还款
			}
			if (Utils.isObjectNotEmpty(info.getSid())) {// 绑定标的的产品募集成功后修改债权状态
				DrSubjectInfo drSubjectInfo = drSubjectInfoDAO.getDrSubjectInfoByid(info.getSid());// 标的以及部分贷款项目信息
				drClaimsLoanDAO.updateClaimsLoanStatusById(5, drSubjectInfo.getLid());// 修改债权状态为待还款
			}
			if (totalAmount.compareTo(BigDecimal.ZERO) != 0) {
				DrCompanyFundsLog clog = new DrCompanyFundsLog(1, 0, info.getId(), totalAmount, 1,
						"产品【" + info.getFullName() + "】投资款", null);
				drCompanyFundsLogDAO.insertDrCompanyFundsLog(clog);
			}
		}
		sau.flush();
		//更新产品的状态
		Map<String, Object>infoMap = new HashMap<>();
		infoMap.put("interest_success_num", successTotal);
		infoMap.put("interest_fail_num", total - successTotal);
		infoMap.put("interest_status", successTotal == total ? 2 : 3);//计息失败,1-未计息，2-计息成功，3-计息失败
		drProductInfoDAO.updateDrProductInfoInterestRepay(infoMap);
			
		
		if (nums > 0 && !Utils.isEmptyList(investList)) {
			if(!Utils.isEmptyList(insertRepayInfoList))
				drProductInvestRepayInfoDAO.batchInsert(insertRepayInfoList);
			
			if (!productRepayDetailList.isEmpty()) {
				List<DrProductInfoRepayDetail> list=productRepayDetailDAO.selectByPid(info.getId());
				if(list.size()<1){
					productRepayDetailDAO.batchInsert(productRepayDetailList);
				}
				drProductInvestRepayInfoDAO.updateRepayDetailIdByPid(info);// 按月付息产品，产品明细ID与回款记录关联
			}
			if(!Utils.isEmptyList(successInvestList))
				drProductInvestDAO.batchUpdate(successInvestList);
			if(!Utils.isEmptyList(fundsRecordList))
				drMemberFundsRecordDAO.batchUpdateMemberFundsRecord(fundsRecordList);
			if(Utils.isObjectNotEmpty(fundsMap)){
				List<DrMemberFunds> fundsValuesList  = new ArrayList<DrMemberFunds>(fundsMap.values());
				if(!Utils.isEmptyList(fundsValuesList))
					drMemberFundsDAO.batchUpdateDrMemberFunds(fundsValuesList);
			}
			if(!Utils.isEmptyList(fundsLogsList))
				drMemberFundsLogDAO.batchInsert(fundsLogsList);
			if(!Utils.isEmptyList(msgList))
				drMemberMsgDAO.batchInsert(msgList);
		}
		if (StringUtils.isNotBlank(failId.toString())) {
			String[] ids = failId.toString().split(",");
			drProductInvestDAO.updateStatusByIds("2", ids);
		}
		// 短信发送
	    Calendar cal = Calendar.getInstance();
	    Set<String> set = new HashSet<String>();
	    Map<String,Integer> uniqueMap = new HashMap<String,Integer>();  
		for (int i = 0; i < smsList.size() ;i++) {//判断该手机号有多少条定时短信 cece
			SysMessageLog temp = smsList.get(i);
			if(set.contains(smsList.get(i).getPhone())){//同个手机号多条定时短信
				Integer count = uniqueMap.get(temp.getPhone());  
				uniqueMap.put(temp.getPhone(), (count == null) ? 1 : count + 1);  
				cal.setTime(smsList.get(i).getSendTime());
				cal.add(Calendar.MINUTE, (uniqueMap.get(temp.getPhone()) * 4));
				smsList.get(i).setSendTime(cal.getTime());
			}else{
				set.add(smsList.get(i).getPhone());
			}
			sysMessageLogService.sendMsg(smsList.get(i));
        }  
	}

	@Override
	public List<Map<String, Object>> getProductByThaw(Map<String, Object> map) {
		return drProductInfoDAO.getProductByThaw(map);
	}

	@Override
	public List<Map<String, Object>> selectProjectInformation() {
		List<Map<String, Object>> customers=drProductInfoDAO.selectProjectInformation();
		return customers;
	}

	@Override
	public void updateFileStatus(List<DrProductInfo> param) {
		drProductInfoDAO.updateFileStatus(param);
	}

	@Override
	public List<Map<String, Object>> getProductByProjectNo(Map<String, Object>map) {
		return drProductInfoDAO.getProductByProjectNo(map);
	}

	@Override
	public List<Map<String, Object>> getProductInvestRepayInfoByProjectNo() {
		return drProductInfoDAO.getProductInvestRepayInfoByProjectNo();
	}

	@Override
	public BaseResult updateDrProductLoanByMchntTxnSsn(String mchnt_txn_ssn)
			throws Exception {
		BaseResult br=new BaseResult();
		Map<String, Object> map=new HashMap<>();
		map.put("mchnt_txn_ssn", mchnt_txn_ssn);
		DrProductInfo drProductInfo=drProductInfoDAO.getDrProductLoanByMchntTxnSsn(map);//查询产品信息
		if(drProductInfo!=null){
			map.put("id", drProductInfo.getId());
			drProductInfoDAO.updateDrProductLoanByMchntTxnSsn(map);//回滚放款状态
			jsLoanRecordDAO.deleteLoanRecord(map);//删除放款记录
			
			DrCompanyFundsLog drCompanyFundsLog=new DrCompanyFundsLog();
			drCompanyFundsLog.setPid(drProductInfo.getId());
			drCompanyFundsLog.setFundsTypeId(15);//放款
			drCompanyFundsLog.setStatus(0);//状态改为失败
			drCompanyFundsLogDAO.updateDrCompanyFundsLogByPid(drCompanyFundsLog);
		}
		br.setSuccess(true);
		return br;
	}



	@Override
	public BaseResult queryTransferBmu() {
		BaseResult br = new BaseResult();
		List<Map<String,Object>> othreInterestCache = new ArrayList<Map<String,Object>>();
		List<List<Map<String,Object>>> toList = new ArrayList<List<Map<String,Object>>>();
		Map<String,Object>  map = new HashMap<String, Object>();
		map.put("transfer_status", 3);
		List<DrProductInvestRepayInfo> investRepayInfoList = drProductInvestRepayInfoDAO.selectTransferFailDrProductInvestRepayInfo();
		Integer successCount = 0;
		Map<String,Object> othreInterestMap = new HashMap<String, Object>();
		int pid = 0;
		DrProductInvestRepayInfo repayInfo;
		for (int i = 0; i < investRepayInfoList.size(); i++) {
			
			try {
				repayInfo =  investRepayInfoList.get(i);
				//基本收益  					
				BigDecimal normalInterest = repayInfo.getBasicprofit();
				//其他收益 --> 走营销存管账户=应收收益-产品年化收益
				BigDecimal otherInterest = repayInfo.getShouldInterest().subtract(normalInterest);				
		
								
				//发放  其他收益  走队列发放						
				othreInterestMap =new HashMap<String, Object>();						
				othreInterestMap.put("uid", repayInfo.getUid());
				othreInterestMap.put("repayInfoId", repayInfo.getId());//回款id可用 作验证
				othreInterestMap.put("otherInterest", otherInterest);//其他收益额度
				othreInterestMap.put("project_id", repayInfo.getPid());//产品ID
								
				if(pid != repayInfo.getPid()){
					pid = repayInfo.getPid();
					if(i>0){
						toList.add(othreInterestCache);
					}
					othreInterestCache = new ArrayList<Map<String,Object>>();
				}
				othreInterestCache.add(othreInterestMap);				
				
				
				if(i==investRepayInfoList.size() - 1){
					toList.add(othreInterestCache);
				}
				
				successCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
 		
 		if(!Utils.isEmptyList(toList) ){
 			for (List<Map<String,Object>> listCache : toList) {
 				//其他收益放到redis里
 				map.clear();
 				map.put("list", listCache);
 				map.put("type", 51);//发放其他收益
 				redisClientTemplate.lpush("regAndVerifySendRedUidList".getBytes(), SerializeUtil.serialize(map));	
			}
		}
 		
 		map.clear();
 		br.setMsg("激活转账："+investRepayInfoList.size()+"个，成功："+successCount+"个");
 		br.setMap(map);
		return br;
	}
	@Override
	public List<Map<String, Object>> getProductInfoDetail(Map<String, Object> map) {
		return drProductInfoDAO.getProductInfoDetail(map);
	}

	@Override
	public int getProductInfoDetailCount(Map<String, Object> map) {
		return drProductInfoDAO.getProductInfoDetailCount(map);
	}

	@Override
	public List<Map<String, Object>> getProductServiceManagement(Map<String, Object> map) {
		return drProductInfoDAO.getProductServiceManagement(map);
	}

	@Override
	public int getProductServiceManagementCount(Map<String, Object> map) {
		return drProductInfoDAO.getProductServiceManagementCount(map);
	}

	@Override
	public BaseResult importCovercharge(List<JsCoverCharge> list,Long userKy) {
		BaseResult br = new BaseResult();
		String errorcode="";//标记产品不存在的code
		String errormsg="";//标记导入过的服务费
		for (JsCoverCharge jsCoverCharge : list) {
			Map<String, Object>map=new HashMap<>();
			map.put("code", jsCoverCharge.getCode());
			
			DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
			if(drProductInfo==null){
				errorcode=errorcode+"["+jsCoverCharge.getCode()+"]";
			}else{
				JsCoverCharge coverCharge=jsCoverChargeDAO.selectCoverChargeByPid(drProductInfo.getId());
				if(coverCharge!=null){//服务费存在
					errormsg=errormsg+"["+jsCoverCharge.getCode()+"]";
				}
			}
		}
		if(!errorcode.equals("") || !errormsg.equals("")){
			br.setSuccess(false);
			String error="";
			if(!errorcode.equals("")){
				error=error+errorcode+"产品编号不存在!";
			}
			if(!errormsg.equals("")){
				error=error+errormsg+"服务费数据已存在!";
			}
			br.setErrorMsg(error);
		}else{
			for (JsCoverCharge jsCoverCharge : list) {
				Map<String, Object>map=new HashMap<>();
				map.put("code", jsCoverCharge.getCode());
				DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
				jsCoverCharge.setServiceStatus(1);//待复核
				jsCoverCharge.setUserKy(userKy);
				jsCoverCharge.setPid(drProductInfo.getId());
				jsCoverChargeDAO.insertCoverCharge(jsCoverCharge);
			}
			br.setSuccess(true);
			br.setMsg("导入成功!");
		}
		return br;
	}

	@Override
	public BaseResult importInvoice(List<JsInvoice> list,Long userKy) {
		BaseResult br = new BaseResult();
		String errorcode="";//标记产品不存在的code
		String errormsg="";//标记导入过的票据
		for (JsInvoice jsInvoice : list) {
			Map<String, Object>map=new HashMap<>();
			map.put("code", jsInvoice.getCode());
			
			DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
			if(drProductInfo==null){
				errorcode=errorcode+"["+jsInvoice.getCode()+"]";
			}else{
				JsInvoice invoice=jsInvoiceDAO.selectInvoiceByPid(drProductInfo.getId());
				if(invoice!=null){//票据存在
					errormsg=errormsg+"["+jsInvoice.getCode()+"]";
				}
			}
		}
		if(!errorcode.equals("") || !errormsg.equals("")){
			br.setSuccess(false);
			String error="";
			if(!errorcode.equals("")){
				error=error+errorcode+"产品编号不存在!";
			}
			if(!errormsg.equals("")){
				error=error+errormsg+"开票信息已存在!";
			}
			br.setErrorMsg(error);
		}else{
			for (JsInvoice jsInvoice : list) {
				Map<String, Object>map=new HashMap<>();
				map.put("code", jsInvoice.getCode());
				DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
				jsInvoice.setInvoiceStatus(1);//待复核
				jsInvoice.setUserKy(userKy);
				jsInvoice.setPid(drProductInfo.getId());
				jsInvoiceDAO.insertInvoice(jsInvoice);
			}
			br.setSuccess(true);
			br.setMsg("导入成功!");
		}
		return br;	
	}

	@Override
	public BaseResult insertCharge(Map<String, Object> map) throws ParseException {
		BaseResult br = new BaseResult();
		DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
		if(drProductInfo==null){
			br.setSuccess(false);
			br.setErrorMsg("产品编号不存在");
			return br;
		}
		JsCoverCharge coverCharge=jsCoverChargeDAO.selectCoverChargeByPid(drProductInfo.getId());
		JsCoverCharge charge=new JsCoverCharge();
		if(coverCharge==null){//新增	
			charge.setPid(drProductInfo.getId());
			charge.setUserKy(Long.parseLong(map.get("userKy").toString()));
			charge.setServiceCharge(new BigDecimal(map.get("serviceCharge").toString()));
			charge.setReceivedGuarantee(new BigDecimal(map.get("receivedGuarantee").toString()));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			charge.setServiceTime(format.parse(map.get("serviceTime").toString()));
			charge.setServiceStatus(1);
			if(map.get("serviceRemerk")!=null){
				charge.setServiceRemerk(map.get("serviceRemerk").toString());
			}
			jsCoverChargeDAO.insertCoverCharge(charge);
			br.setSuccess(true);
			br.setMsg("操作成功");
		}{//修改
			charge.setPid(drProductInfo.getId());
			charge.setUserKy(Long.parseLong(map.get("userKy").toString()));
			charge.setServiceCharge(new BigDecimal(map.get("serviceCharge").toString()));
			charge.setReceivedGuarantee(new BigDecimal(map.get("receivedGuarantee").toString()));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			charge.setServiceTime(format.parse(map.get("serviceTime").toString()));
			charge.setServiceStatus(1);
			if(map.get("serviceRemerk")!=null){
				charge.setServiceRemerk(map.get("serviceRemerk").toString());
			}
			jsCoverChargeDAO.updateCoverCharge(charge);
			br.setSuccess(true);
			br.setMsg("操作成功");
		}
		return br;
	}

	@Override
	public void deleteCoverCharge(String code) {
		Map<String, Object>map=new HashMap<>();
		map.put("code", code);
		DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
		jsCoverChargeDAO.deleteCoverCharge(drProductInfo.getId());		
	}

	@Override
	public BaseResult insertInvotice(Map<String, Object> map) throws ParseException {
		BaseResult br = new BaseResult();
		DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
		if(drProductInfo==null){
			br.setSuccess(false);
			br.setErrorMsg("产品编号不存在");
			return br;
		}
		JsInvoice invoice=jsInvoiceDAO.selectInvoiceByPid(drProductInfo.getId());
		JsInvoice jsInvoice=new JsInvoice();
		if(invoice==null){//新增	
			jsInvoice.setPid(drProductInfo.getId());
			jsInvoice.setUserKy(Long.parseLong(map.get("userKy").toString()));
			jsInvoice.setInvoiceAmount(new BigDecimal(map.get("invoiceAmount").toString()));
			jsInvoice.setInvoiceNumber(map.get("invoiceNumber").toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			jsInvoice.setInvoiceTime(format.parse(map.get("invoiceTime").toString()));
			jsInvoice.setInvoiceStatus(1);
			if(map.get("invoiceRemerk")!=null){
				jsInvoice.setInvoiceRemerk(map.get("invoiceRemerk").toString());
			}
			jsInvoiceDAO.insertInvoice(jsInvoice);
			br.setSuccess(true);
			br.setMsg("操作成功");
		}{//修改
			jsInvoice.setPid(drProductInfo.getId());
			jsInvoice.setUserKy(Long.parseLong(map.get("userKy").toString()));
			jsInvoice.setInvoiceAmount(new BigDecimal(map.get("invoiceAmount").toString()));
			jsInvoice.setInvoiceNumber(map.get("invoiceNumber").toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			jsInvoice.setInvoiceTime(format.parse(map.get("invoiceTime").toString()));
			jsInvoice.setInvoiceStatus(1);
			if(map.get("invoiceRemerk")!=null){
				jsInvoice.setInvoiceRemerk(map.get("invoiceRemerk").toString());
			}
			jsInvoiceDAO.updateInvoice(jsInvoice);
			br.setSuccess(true);
			br.setMsg("操作成功");
		}
		return br;
	}

	@Override
	public void deleteInvoice(String code) {
		Map<String, Object>map=new HashMap<>();
		map.put("code", code);
		DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
		jsInvoiceDAO.deleteInvoice(drProductInfo.getId());	
	}

	@Override
	public BaseResult updateCharge(Map<String, Object> map) throws ParseException {
		BaseResult br = new BaseResult();
		DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
		if(drProductInfo==null){
			br.setSuccess(false);
			br.setErrorMsg("产品编号不存在");
			return br;
		}
		JsCoverCharge charge=new JsCoverCharge();
		charge.setPid(drProductInfo.getId());
		charge.setServiceStatus(Integer.parseInt(map.get("serviceStatus").toString()));
		if(map.get("serviceRemerk")!=null){
			charge.setServiceRemerk(map.get("serviceRemerk").toString());
		}
		jsCoverChargeDAO.updateCoverCharge(charge);
		br.setMsg("操作成功");
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult updateInvoice(Map<String, Object> map) throws ParseException {
		BaseResult br = new BaseResult();
		DrProductInfo drProductInfo=drProductInfoDAO.getProductInfoByCode(map);
		if(drProductInfo==null){
			br.setSuccess(false);
			br.setErrorMsg("产品编号不存在");
			return br;
		}
		JsInvoice jsInvoice=new JsInvoice();
		jsInvoice.setPid(drProductInfo.getId());
		jsInvoice.setInvoiceStatus(Integer.parseInt(map.get("invoiceStatus").toString()));
		if(map.get("serviceRemerk")!=null){
			jsInvoice.setInvoiceRemerk(map.get("invoiceRemerk").toString());
		}
		jsInvoiceDAO.updateInvoice(jsInvoice);
		br.setMsg("操作成功");
		br.setSuccess(true);
		return br;
	}

	@Override
	public BaseResult getDepositReturnLoanList(DrProductInfo drProductInfo,PageInfo pi) {
		BaseResult br = new BaseResult();
		try {
			Map<String,PageInfo> result = new HashMap<String,PageInfo>();
			Map<String,Object> param = new HashMap<String,Object>();
			param = getExtractMap(drProductInfo);
			param.put("loginId", drProductInfo.getLoginId());
			param.put("offset", pi.getPageInfo().getOffset());
			param.put("limit", pi.getPageInfo().getLimit());
			List<DrProductInfo> list = drProductInfoDAO.getDepositReturnLoanList(param);
			int count = drProductInfoDAO.getDepositReturnLoanCounts(param);
			pi.setRows(list);
			pi.setTotal(count);
			result.put("page", pi);
			br.setMap(result);
			br.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			br.setSuccess(false);
			br.setErrorMsg("系统错误");
		}
		return br;
	}
	
	@Override
	public void productFullfreezeToFreeze(Map<String,Object> map) {
		
		DrProductInfo info = drProductInfoDAO.getDrProductInfoByid(Integer.valueOf(map.get("pid").toString()));
		
		if(Utils.isObjectNotEmpty(info) && (info.getStatus() ==6 || info.getStatus() ==8)){	
			List<DrProductInvest> investList = drProductInvestDAO.getFuiouDrProductInvestListByPid(info.getId());// 查询所有的投资记录
			
			DrClaimsLoan loan = drClaimsInfoService.getDrClaimsLoanBySid(info.getSid());
			DrClaimsCustomer customer = drClaimsInfoService.getDrClaimsCustomerByLid(loan.getId());// 借款人
			List<DrProductInvest> updateList = new ArrayList<DrProductInvest>();
			DrMember member;
			DrProductInvest invest;
			int failNum = 0; 
			for (int j = 0; j < investList.size(); j++) {
				try {
					 invest = investList.get(j);
					// 对于状态是为未处理或者失败数据进行冻结到冻结
					if (Utils.strIsNull(invest.getRemitMchntTxnSsn()) || invest.getStatus() ==4 ) {
						member = drMemberDAO.selectByPrimaryKey(invest.getUid());
						
						Map<String, String> params = new HashMap<String, String>();
						String remitMchntTxnSsn = Utils.createOrderNo(6, invest.getId(), "");// 流水号
						params.put("mchnt_txn_ssn", remitMchntTxnSsn);
						params.put("out_cust_no", member.getMobilephone());
						params.put("in_cust_no", customer.getPhone());
						params.put("rem", info.getFullName()+":"+info.getId());
						params.put("amt", "" + invest.getAmount());// 精确到分
						BaseResult br = FuiouConfig.transferBuAndFreeze2Freeze(params);
						if (!br.isSuccess()) {
							invest.setStatus(4);// 失败
							if(br.getErrorMsg().length()>250){
								invest.setMessage(br.getMsg().substring(0, 250));// 失败原因
							}else{
								invest.setMessage(br.getMsg());// 失败原因
							}
							failNum++;
						}
						invest.setRemitMchntTxnSsn(remitMchntTxnSsn);//流水号
						updateList.add(invest);
						
					}
				} catch (Exception e) {
					failNum++;
                    log.error("冻结转冻结失败:"+e.getMessage());
				}
			}
			//批量修改
			if(!Utils.isEmptyList(updateList)){
				drProductInvestDAO.updateFuiouProductInvestBatch(updateList);
			}
			if((investList.size() >0 && failNum >0) ||investList.size()==0){//有失败的,param 保存到redis 里,并发短信
				
				try {
					SmsSendUtil.sendMsg("15800784479,15221219118", "产品满标-冻结到冻结["+info.getFullName()+","+info.getId()+"] 投资数["+investList.size()+"] 失败数["+failNum+"]");
				} catch (Exception e) {
					log.info("产品满标-冻结到冻结-发短信失败");
                    log.error("冻结转冻结失败:"+e.getMessage());
				}
				//		
				redisClientTemplate.lpush("regAndVerifySendRedUidList_bak".getBytes(), SerializeUtil.serialize(map));
			}
		}
		
	}

	@Override
	public Map<String, Object> selectDrProductLoanAmountSum(DrProductInfo drProductInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = getExtractMap(drProductInfo);
			BigDecimal amountSum = drProductInfoDAO.selectDrProductLoanAmountSum(map);
			resultMap.put("amountSum", amountSum == null?0.00:amountSum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	
	@Override
	public int getNewFinancingEnd() {
		int count = drProductInfoDAO.getNewFinancingEnd();
		return count;
	}

	@Override
	public void sendBalanceDeficiencyMsg(int dayNum) {
			try {
				Map<String, Object> param = new HashMap<>();
				param.put("dayNum", dayNum);
				param.put("type", 1);
				List<Map<String, Object>> companyList = drProductInfoDAO.getReimbursementInfo(param);
				if (!companyList.isEmpty()) {
					Map<String, String> map = new HashMap<String, String>();
					StringBuffer companyNames = new StringBuffer();
					String shouldTime = "";
					for (Map<String, Object> companyMap : companyList) {
						map.put("cust_no", companyMap.get("userId").toString());
						BaseResult br = FuiouConfig.balanceAction(map);// 调用存管对应企业需还金额
						Map<?, ?> cunGuanMap = br.getMap();
						net.sf.json.JSONObject results = (net.sf.json.JSONObject) cunGuanMap.get("results");
						net.sf.json.JSONObject result = (net.sf.json.JSONObject) results.get("result");
						String ca_balance = Utils.isObjectEmpty(result.get("ca_balance"))?"0":(String)result.get("ca_balance");// 存管对应企业需还金额
						BigDecimal caBalance = Utils.nwdDivide(new BigDecimal(ca_balance), 100).setScale(2,
								BigDecimal.ROUND_DOWN);
						BigDecimal shoudReturnAmount = (BigDecimal) companyMap.get("shoudReturnAmount") ;// 平台借款企业需还款金额
						
						 shouldTime = (String) companyMap.get("shouldTime");
						if (shoudReturnAmount != null) {
							if (shoudReturnAmount.compareTo(caBalance) == 1) {
								companyNames.append("【"+companyMap.get("companyName")+"】");
							}
						}
					}
					if(companyNames.length() != 0){
						String subject = shouldTime+"借款企业账户余额资金不足提醒";
						String content = "您好，系统扫描到" + companyNames
								+ "的存管账户余额于未来"+dayNum+"天内已小于下期还款金额，请留意该企业的资金动态，以防出现逾期风险，谢谢。";
						MailUtil.sendBatch(content, PropertyUtil.getProperties("mailtos"), subject);
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	@Override
	public void sendMsgForRepayment(int dayNum) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("dayNum", dayNum);
			param.put("type", 2);
			List<Map<String, Object>> companyList = drProductInfoDAO.getReimbursementInfo(param);
			String msg = "";
			Date now = new Date();//还款时间
			List<SysMessageLog> smsList = new ArrayList<SysMessageLog>();
			if(!Utils.isEmptyList(companyList)){
				for (Map<String, Object> companyMap : companyList) {
					msg = PropertyUtil.getProperties("companyRepaymentMsg").replace("${companyName}", companyMap.get("companyName").toString())
							.replace("${date}", Utils.format(Utils.format(companyMap.get("shouldTime").toString(), "yyyy-MM-dd"), "yyyy年MM月dd日"))
							.replace("${shoudReturnAmount}", Utils.covertToString(companyMap.get("shoudReturnAmount").toString()));
					SysMessageLog smslog = new SysMessageLog(null,msg ,
							12, Utils.parseDate(Utils.format(now, "yyyy-MM-dd 10:mm:ss"),"yyyy-MM-dd HH:mm:ss"), companyMap.get("phone").toString());
					smsList.add(smslog);
				}
				
				//短信发送
				for (int i = 0; i < smsList.size(); i++) {
					sysMessageLogService.sendMsg(smsList.get(i));
//					System.out.println(smsList.get(i).getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getIn_cust_noByProductId(Integer id) {
		return drProductInfoDAO.getIn_cust_noByProductId(id);
	}

	@Override
	public int getProductPrize(Integer prizeId) {
		return drProductInfoDAO.getProductPrize(prizeId);
	}

	/**
	 * 企业网银充值（回款）手动同步
	 * @throws SQLException 
	 */
	@Override
	public BaseResult queryRecharge(String mchnt_txn_ssn) throws SQLException {
		SysFuiouNoticeLog syslog = sysFuiouNoticeLogService.selectObjectBy_txn_ssn(mchnt_txn_ssn);
		BaseResult result = new BaseResult();
		if(Utils.isObjectNotEmpty(syslog)){
			if(syslog.getIcd().equals("000015") && syslog.getProject_no()!=null && syslog.getStatus()!=2){//订单是企业网银充值并且log日志状态未成功
				Map<String, String> m=new HashMap<>();
				m.put("txn_ssn", syslog.getMchnt_txn_ssn());
				m.put("busi_tp", "PW11");
				m.put("start_time", Utils.format(Utils.getDayNumOfAppointDate(syslog.getAddtime(), 1), "yyyy-MM-dd")+" 00:00:00");
				m.put("end_time",  Utils.format(Utils.getDayNumOfAppointDate(syslog.getAddtime(), -1), "yyyy-MM-dd")+" 23:59:59");
				m.put("mchnt_txn_ssn", Utils.createOrderNo(6, 1, ""));
				BaseResult br=FuiouConfig.QueryCzTx(m);
				if(br.isSuccess()){
					if(br.getMsg().contains("0000|成功")){

						Map<String, Object>map=new HashMap<>();
						map.put("code", syslog.getProject_no());
			    		DrProductInfo drProductInfo = getDrProductInfoByMap(map);
			    		BigDecimal amount=new BigDecimal(0);
			    		if(Utils.isObjectNotEmpty(syslog.getAmt())){
			    			 amount = new BigDecimal(syslog.getAmt());
			    		}
			    		String loanNo;//合同号
			    		DrSubjectInfo drSubjectInfo;
			    		if(Utils.isObjectEmpty(drProductInfo.getSid())){
			    			loanNo = "xxxxxxx";
			    		}
						drSubjectInfo = drSubjectInfoService.getDrSubjectInfoByid(drProductInfo.getSid());
						if(Utils.isObjectEmpty(drSubjectInfo)){
							loanNo = "xxxxxxx";
						}
						DrClaimsLoan drClaimsLoan = drClaimsInfoService.getDrClaimsLoanByid(drSubjectInfo.getLid());
						if(Utils.isObjectEmpty(drClaimsLoan)){
							loanNo = "xxxxxxx";
						}else{
							loanNo = drClaimsLoan.getNo();
						}
			    		
			    		String tran_flowid = Utils.createOrderNo(6,drProductInfo.getId(),"");
			    		updateReFundDrProductLoanStatus(drProductInfo.getId());
			    		DrCompanyFundsLog drCompanyFundsLog = new DrCompanyFundsLog(16, null, 
			    				drProductInfo.getId(), amount, 1, 
			    						"债券合同["+loanNo+"]回款"+amount+"元,流水号["+tran_flowid+"]",null);
			    		drCompanyFundsLogService.insertDrCompanyFundsLog(drCompanyFundsLog);
			    		
			    		//记公司账户日志 收取手续费
			    		BigDecimal money = amount.multiply(new BigDecimal(1.5)).divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN);
						BigDecimal poundage = new BigDecimal("2");			
						poundage = poundage.compareTo(money) == 1? poundage : money;
						
						JsCompanyAccountLog companyAccountLog=new JsCompanyAccountLog();
						companyAccountLog.setCompanyfunds(17);//资金类型
						companyAccountLog.setType(0);//支出
						companyAccountLog.setAmount(poundage);//金额
						
						/*companyAccountLog.setBalance(jsCompanyAccountLogDAO.getBlanceByFuiou().subtract(new BigDecimal(3)));*/
						companyAccountLog.setStatus(3);//成功
						companyAccountLog.setRemark("商户回款网银充值手续费");
						companyAccountLog.setAddTime(new Date());
						companyAccountLog.setChannelType(2);//存管
						/*companyAccountLog.setUid(member.getUid());//用户id
		*/				jsCompanyAccountLogDAO.insertCompanyAccountLog(companyAccountLog);
					
						syslog.setStatus(2);//2成功
						syslog.setResp_code("0000");
						syslog.setResp_desc(br.getMsg());
						sysFuiouNoticeLogService.update(syslog);
						result.setMsg("同步成功");
						result.setSuccess(true);
					}else{
						result.setErrorMsg("订单不存在或充值失败");
						result.setSuccess(false);
					}
				}else{
					result.setErrorMsg("调用存管查询接口失败");
					result.setSuccess(false);
				}
			}else{
				result.setErrorMsg("该流水号不是企业网银充值或订单已成功不需要再次同步");
				result.setSuccess(false);
			}
		}else{
			result.setErrorMsg("流水号不存在");
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public int getDealRecord(Integer uid) {
		return drProductInfoDAO.getDealRecord(uid);
	}
	@Override
	public List<Map<String, Object>> selectProductInfoStatus() {
		return drProductInfoDAO.selectProductInfoStatus();
	}

    @Override
    public List<Map<String, Object>> selectSyncProductInfo() {
        return drProductInfoDAO.selectSyncProductInfo();
    }
}
