package com.jsjf.service.claims.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jsjf.common.SecurityUtils;
import com.jsjf.common.Utils;
import com.jsjf.dao.claims.DrAuditInfoDAO;
import com.jsjf.dao.claims.DrClaimsBillDAO;
import com.jsjf.dao.claims.DrClaimsCustomerDAO;
import com.jsjf.dao.claims.DrClaimsFinancDAO;
import com.jsjf.dao.claims.DrClaimsGuaranteeDAO;
import com.jsjf.dao.claims.DrClaimsLoanDAO;
import com.jsjf.dao.claims.DrClaimsPicDAO;
import com.jsjf.dao.claims.DrClaimsProjectDAO;
import com.jsjf.dao.claims.DrClaimsShareholderDAO;
import com.jsjf.dao.claims.JsClaimsAuditEditDAO;
import com.jsjf.dao.claims.JsLoanRecordDAO;
import com.jsjf.dao.subject.DrSubjectInfoDAO;
import com.jsjf.dao.system.SysRoleVoDAO;
import com.jsjf.dao.system.SysUserRoleVoDAO;
import com.jsjf.dao.system.SysUsersVoDAO;
import com.jsjf.model.claims.DrAuditInfo;
import com.jsjf.model.claims.DrClaimsBill;
import com.jsjf.model.claims.DrClaimsCustomer;
import com.jsjf.model.claims.DrClaimsFinanc;
import com.jsjf.model.claims.DrClaimsGuarantee;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.claims.DrClaimsPic;
import com.jsjf.model.claims.DrClaimsProject;
import com.jsjf.model.claims.DrClaimsShareholder;
import com.jsjf.model.claims.JsClaimsAuditEdit;
import com.jsjf.model.claims.JsLoanRecord;
import com.jsjf.model.subject.DrSubjectInfo;
import com.jsjf.model.system.SysRoleVo;
import com.jsjf.model.system.SysUserRoleVo;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.claims.DrClaimsInfoService;
import com.jzh.FuiouConfig;
import com.jzh.data.WithdrawalsRspData;
import com.jzh.data.claimsRspData;
import com.jzh.service.JZHService;
import com.reapal.utils.Md5Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class DrClaimsInfoServiceImpl implements DrClaimsInfoService {
    @Autowired
    private DrClaimsBillDAO drClaimsBillDAO;
    @Autowired
    private DrClaimsPicDAO drClaimsPicDAO;
    @Autowired
    private DrClaimsLoanDAO drClaimsLoanDAO;
    @Autowired
    private DrClaimsCustomerDAO drClaimsCustomerDAO;
    @Autowired
    private DrClaimsFinancDAO drClaimsFinancDAO;
    @Autowired
    private DrClaimsGuaranteeDAO drClaimsGuaranteeDAO;
    @Autowired
    private DrClaimsShareholderDAO drClaimsShareholderDAO;
    @Autowired
    private DrAuditInfoDAO drAuditInfoDAO;
    @Autowired
    private DrSubjectInfoDAO drSubjectInfoDAO;
    @Autowired
    private DrClaimsProjectDAO drClaimsProjectDAO;
    @Autowired
    private JsClaimsAuditEditDAO jsClaimsAuditEditDAO;
    @Autowired
    private JsLoanRecordDAO jsLoanRecordDAO;
    @Autowired
    private SysUsersVoDAO sysUsersVoDAO;
    @Autowired
    private SysUserRoleVoDAO sysUserRoleVoDAO;
    @Autowired
    private SysRoleVoDAO sysRoleVoDAO;

    @Override
    public BaseResult getDrClaimsLoanList(DrClaimsLoan drClaimsLoan, PageInfo pi) {
        Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("no", drClaimsLoan.getNo());
        map.put("name", drClaimsLoan.getName());
        map.put("companyName", drClaimsLoan.getCompanyName());
        map.put("startDate", Utils.format(drClaimsLoan.getStartDate(), "yyyy-MM-dd"));
        map.put("endDate", Utils.format(drClaimsLoan.getEndDate(), "yyyy-MM-dd"));

        map.put("dueStartDate", Utils.format(drClaimsLoan.getDueStartDate(), "yyyy-MM-dd"));
        map.put("dueEndDate", Utils.format(drClaimsLoan.getDueEndDate(), "yyyy-MM-dd"));


        if (drClaimsLoan.getStatus() == null) {
            map.put("status", drClaimsLoan.getStatus());
        } else if (drClaimsLoan.getStatus() == 101) {
            map.put("isAuditEdit", 1);
        } else {
            if (drClaimsLoan.getStatus() == 100) {//显示审核页面
                map.put("status", new Integer[]{1});
                map.put("isAuditEdit", 100);
            } else if (drClaimsLoan.getStatus() == 200) {//显示债权催收和债权还款
                map.put("status", new Integer[]{5});
            } else if (drClaimsLoan.getStatus() == 300) {//显示标的新增
                map.put("status", new Integer[]{2});
            } else {
                map.put("status", new Integer[]{drClaimsLoan.getStatus()});
            }
        }

        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<DrClaimsLoan> list = drClaimsLoanDAO.getDrClaimsLoanList(map);
        Integer total = drClaimsLoanDAO.getDrClaimsLoanCounts(map);
        pi.setTotal(total);
        pi.setRows(list);
        resultMap.put("page", pi);
        BaseResult br = new BaseResult();
        br.setMap(resultMap);
        return br;
    }

    @Override
    public void insertDrClaimsInfo(DrClaimsLoan drClaimsLoan, MultipartFile[] claimsFiles) throws Exception {
        if (Utils.isObjectNotEmpty(drClaimsLoan.getLoanAmount())) {
            drClaimsLoan.setLoanAmount(drClaimsLoan.getLoanAmount().multiply(new BigDecimal(10000)));
        }

        drClaimsLoanDAO.insertDrClaimsLoan(drClaimsLoan);

        DrClaimsBill drClaimsBill = drClaimsLoan.getDrClaimsBill();
        if (drClaimsBill != null) {
            if (Utils.isObjectNotEmpty(drClaimsBill.getAmount())) {
                drClaimsBill.setAmount(drClaimsBill.getAmount().multiply(new BigDecimal(10000)));
            }
        } else {
            drClaimsBill = new DrClaimsBill();
        }
        drClaimsBill.setLid(drClaimsLoan.getId());
        drClaimsBillDAO.insertDrClaimsBill(drClaimsBill);

        DrClaimsCustomer drClaimsCustomer = drClaimsLoan.getDrClaimsCustomer();
        if (!Utils.strIsNull(drClaimsCustomer.getCompanyName())) {
            drClaimsCustomer.setLid(drClaimsLoan.getId());
            drClaimsCustomerDAO.insertDrClaimsCustomer(drClaimsCustomer);
        }

        DrClaimsFinanc drClaimsFinanc = drClaimsLoan.getDrClaimsFinanc();
        if (drClaimsFinanc != null) {
            if (!Utils.strIsNull(drClaimsFinanc.getCompanyName())) {

            }
        } else {
            drClaimsFinanc = new DrClaimsFinanc();
        }
        drClaimsFinanc.setLid(drClaimsLoan.getId());
        drClaimsFinancDAO.insertDrClaimsFinanc(drClaimsFinanc);

        DrClaimsGuarantee[] drClaimsGuarantee = drClaimsLoan.getDrClaimsGuarantee();
        if (drClaimsGuarantee != null) {
            for (DrClaimsGuarantee guarantee : drClaimsGuarantee) {
                if (guarantee.getAssessAmount() != null || guarantee.getGuaranteeAmount() != null ||
                        !Utils.strIsNull(guarantee.getGuarantee()) || !Utils.strIsNull(guarantee.getGuarantor())) {
                    if (Utils.isObjectNotEmpty(guarantee.getAssessAmount())) {
                        guarantee.setAssessAmount(guarantee.getAssessAmount().multiply(new BigDecimal(10000)));
                    }
                    guarantee.setLid(drClaimsLoan.getId());
                    drClaimsGuaranteeDAO.insertDrClaimsGuarantee(guarantee);
                }
            }
        }

        DrClaimsShareholder[] drClaimsShareholder = drClaimsLoan.getDrClaimsShareholder();
        if (drClaimsShareholder != null) {
            for (DrClaimsShareholder shareholder : drClaimsShareholder) {
                if (!Utils.strIsNull(shareholder.getName())) {
                    shareholder.setLid(drClaimsLoan.getId());
                    drClaimsShareholderDAO.insertDrClaimsShareholder(shareholder);
                }
            }
        }

        List<DrClaimsProject> drClaimsProjectList = drClaimsLoan.getDrClaimsProject();

        if (!Utils.isEmptyList(drClaimsProjectList)) {
            for (DrClaimsProject drClaimsProject : drClaimsProjectList) {
                if (!Utils.strIsNull(drClaimsProject.getName())) {
                    drClaimsProject.setLid(drClaimsLoan.getId());
                    drClaimsProjectDAO.insertDrClaimsProject(drClaimsProject);
                }
            }
        }

        String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.claimsPic + Utils.format(new Date(), "yyyy-MM") + "/" + drClaimsLoan.getId() + "/";
        String savePath = ConfigUtil.getDomainname() + ConfigUtil.claimsPic + Utils.format(new Date(), "yyyy-MM") + "/" + drClaimsLoan.getId() + "/";
        SFtpUtil sftp = new SFtpUtil();

        List<DrClaimsPic> list = drClaimsLoan.getDrClaimsPic();
        List<DrClaimsPic> useClaimsPic = new ArrayList<DrClaimsPic>();
        if (!Utils.isEmptyList(list)) {
            for (DrClaimsPic pic : list) {
                if ("use".equals(pic.getBigUrl())) {
                    useClaimsPic.add(pic);
                }
            }
        }
        if (claimsFiles != null) {
            for (int i = 0; i < claimsFiles.length; i++) {
                String imageName = ImageUtils.getServerFileName()
                        + claimsFiles[i].getOriginalFilename().substring(
                        claimsFiles[i].getOriginalFilename().lastIndexOf("."));

                DrClaimsPic drClaimsPic = new DrClaimsPic();
                drClaimsPic.setLid(drClaimsLoan.getId());
                drClaimsPic.setBigUrl(savePath + imageName);
                drClaimsPic.setName(useClaimsPic.get(i).getName());
                drClaimsPic.setStatus(1);
                drClaimsPicDAO.insertDrClaimsPic(drClaimsPic);

                sftp.connectServer();
                sftp.put(claimsFiles[i].getInputStream(), realPath, imageName);
                sftp.closeServer();
            }

        }
    }

    @Override
    public void insertDrClaimsInfo(DrClaimsLoan drClaimsLoan, String[] claimsFiles) throws Exception {
        drClaimsLoanDAO.insertDrClaimsLoan(drClaimsLoan);
        DrClaimsBill drClaimsBill = drClaimsLoan.getDrClaimsBill();
        if (drClaimsBill != null) {
            if (Utils.isObjectNotEmpty(drClaimsBill.getAmount())) {
                drClaimsBill.setAmount(drClaimsBill.getAmount().multiply(new BigDecimal(10000)));
            }
        } else {
            drClaimsBill = new DrClaimsBill();
        }
        drClaimsBill.setLid(drClaimsLoan.getId());
        drClaimsBillDAO.insertDrClaimsBill(drClaimsBill);
        DrClaimsCustomer drClaimsCustomer = drClaimsLoan.getDrClaimsCustomer();
        if (!Utils.strIsNull(drClaimsCustomer.getCompanyName())) {
            drClaimsCustomer.setLid(drClaimsLoan.getId());
            drClaimsCustomerDAO.insertDrClaimsCustomer(drClaimsCustomer);
        }

        DrClaimsFinanc drClaimsFinanc = drClaimsLoan.getDrClaimsFinanc();
        if (drClaimsFinanc != null) {
            if (!Utils.strIsNull(drClaimsFinanc.getCompanyName())) {

            }
        } else {
            drClaimsFinanc = new DrClaimsFinanc();
        }
        drClaimsFinanc.setLid(drClaimsLoan.getId());
        drClaimsFinancDAO.insertDrClaimsFinanc(drClaimsFinanc);

        DrClaimsGuarantee[] drClaimsGuarantee = drClaimsLoan.getDrClaimsGuarantee();
        if (drClaimsGuarantee != null) {
            for (DrClaimsGuarantee guarantee : drClaimsGuarantee) {
                if (guarantee.getAssessAmount() != null || guarantee.getGuaranteeAmount() != null ||
                        !Utils.strIsNull(guarantee.getGuarantee()) || !Utils.strIsNull(guarantee.getGuarantor())) {
                    guarantee.setLid(drClaimsLoan.getId());
                    drClaimsGuaranteeDAO.insertDrClaimsGuarantee(guarantee);
                }
            }
        }

        DrClaimsShareholder[] drClaimsShareholder = drClaimsLoan.getDrClaimsShareholder();
        if (drClaimsShareholder != null) {
            for (DrClaimsShareholder shareholder : drClaimsShareholder) {
                if (!Utils.strIsNull(shareholder.getName())) {
                    shareholder.setLid(drClaimsLoan.getId());
                    drClaimsShareholderDAO.insertDrClaimsShareholder(shareholder);
                }
            }
        }

        if (claimsFiles != null) {
            for (int i = 0; i < claimsFiles.length; i++) {
                DrClaimsPic drClaimsPic = new DrClaimsPic();
                drClaimsPic.setLid(drClaimsLoan.getId());
                drClaimsPic.setBigUrl(claimsFiles[i]);
                drClaimsPic.setStatus(1);
                drClaimsPicDAO.insertDrClaimsPic(drClaimsPic);
            }
        }
    }

    @Override
    public void insertDrClaimsInfoForPay(DrClaimsLoan drClaimsLoan, MultipartFile[] claimsFiles) throws Exception {
        this.insertDrClaimsInfo(drClaimsLoan, claimsFiles);
        List<DrClaimsPic> list = drClaimsLoan.getDrClaimsPic();
        for (DrClaimsPic pic : list) {
            DrClaimsPic drClaimsPic = new DrClaimsPic();
            drClaimsPic.setLid(drClaimsLoan.getId());
            drClaimsPic.setBigUrl(pic.getBigUrl());
            drClaimsPic.setName(pic.getName());
            drClaimsPic.setStatus(1);
            drClaimsPicDAO.insertDrClaimsPic(drClaimsPic);
        }
    }


    @Override
    public void updateDrClaimsInfo(DrClaimsLoan drClaimsLoan, MultipartFile[] claimsFiles) throws Exception {
//		DrClaimsLoan dcl = drClaimsLoanDAO.getDrClaimsLoanByid(drClaimsLoan.getId());	
//		if(dcl.getStatus() !=1 && dcl.getStatus() !=3){//审核通过后 部分修改
//			auditAfterUpdate(drClaimsLoan);
//			
//			dcl.setIsAuditEdit(1);
//			dcl.setLoanAmount(dcl.getLoanAmount().multiply(new BigDecimal("10000")));
//			drClaimsLoanDAO.updateDrClaimsLoan(dcl);
//			//审核后更改企业法人，企业手机号
//			DrClaimsCustomer queryClaimsCustomer = drClaimsCustomerDAO.getDrClaimsCustomerByLid(drClaimsLoan.getId());
//			if(Utils.isObjectNotEmpty(queryClaimsCustomer)){
//				queryClaimsCustomer.setPhone(drClaimsLoan.getDrClaimsCustomer().getPhone());
//				queryClaimsCustomer.setName(drClaimsLoan.getDrClaimsCustomer().getName());
//				drClaimsCustomerDAO.updateDrClaimsCustomer(queryClaimsCustomer);
//			}
//		}else{//审核通过前 修改
        beforAuditUpdate(drClaimsLoan, claimsFiles);
//		}

    }

    //审核通过后 部分修改
    private void auditAfterUpdate(DrClaimsLoan drClaimsLoan) throws Exception {
        //这里做修改不是真的修改债权信息,只是把要审核的信息暂存到 jsclaimsauditedit
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", 0);//数据类型 0:暂存资料
        map.put("statuses", new int[]{0, 2});//0:待审核,1:审核通过,2:驳回,
        map.put("lid", drClaimsLoan.getId());
        map.put("offset", 0);
        map.put("limit", 1);
        map.put("orders", "id desc");
        JsClaimsAuditEdit bean = null;
        List<JsClaimsAuditEdit> list = jsClaimsAuditEditDAO.selectByMap(map);
        boolean flag = false;

        if (Utils.isEmptyList(list)) {
            bean = new JsClaimsAuditEdit();
            bean.setAddUserKy(drClaimsLoan.getUpdUser());
            bean.setLid(drClaimsLoan.getId());

        } else {
            bean = list.get(0);
            bean.setUpdateUserKy(drClaimsLoan.getUpdUser());
            flag = true;
        }
        bean.setStatus(0);
        bean.setType(0);
        bean.setCache(JSON.toJSONString(drClaimsLoan));

        if (flag) {
            jsClaimsAuditEditDAO.update(bean);
        } else {
            jsClaimsAuditEditDAO.insert(bean);
        }
    }

    //审核通过前 修改
    private void beforAuditUpdate(DrClaimsLoan drClaimsLoan,
                                  MultipartFile[] claimsFiles) throws SQLException, Exception,
            IOException {
        if (Utils.isObjectNotEmpty(drClaimsLoan.getLoanAmount())) {
            drClaimsLoan.setLoanAmount(drClaimsLoan.getLoanAmount().multiply(new BigDecimal(10000)));
        }
        drClaimsLoan.setStatus(1);
        drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);

        DrClaimsBill drClaimsBill = drClaimsLoan.getDrClaimsBill();
        if (Utils.isObjectNotEmpty(drClaimsBill.getAmount())) {
            drClaimsBill.setAmount(drClaimsBill.getAmount().multiply(new BigDecimal(10000)));
        }
        drClaimsBill.setLid(drClaimsLoan.getId());
        drClaimsBillDAO.updateDrClaimsBill(drClaimsBill);

        DrClaimsCustomer drClaimsCustomer = drClaimsLoan.getDrClaimsCustomer();
        DrClaimsCustomer queryClaimsCustomer = drClaimsCustomerDAO.getDrClaimsCustomerByLid(drClaimsLoan.getId());

        if (Utils.strIsNull(drClaimsCustomer.getCompanyName())) {
            if (Utils.isObjectNotEmpty(queryClaimsCustomer)) {
                drClaimsCustomerDAO.deleteDrClaimsCustomer(drClaimsLoan.getId());
            }
        } else {
            drClaimsCustomer.setLid(drClaimsLoan.getId());
            if (Utils.isObjectNotEmpty(queryClaimsCustomer)) {
                drClaimsCustomer.setCompanyNameProtocolShow(drClaimsLoan.getCompanyNameProtocolShow());
                drClaimsCustomerDAO.updateDrClaimsCustomer(drClaimsCustomer);
            } else {
                drClaimsCustomerDAO.insertDrClaimsCustomer(drClaimsCustomer);
            }
        }

        DrClaimsFinanc drClaimsFinanc = drClaimsLoan.getDrClaimsFinanc();
        DrClaimsFinanc queryClaimsFinanc = drClaimsFinancDAO.getDrClaimsFinancByLid(drClaimsLoan.getId());

        if (Utils.strIsNull(drClaimsFinanc.getCompanyName())) {
            if (Utils.isObjectNotEmpty(queryClaimsFinanc)) {
                drClaimsFinancDAO.deleteDrClaimsFinanc(drClaimsLoan.getId());
            }
        } else {
            drClaimsFinanc.setLid(drClaimsLoan.getId());
            if (Utils.isObjectNotEmpty(queryClaimsFinanc)) {
                drClaimsFinancDAO.updateDrClaimsFinanc(drClaimsFinanc);
            } else {
                drClaimsFinancDAO.insertDrClaimsFinanc(drClaimsFinanc);
            }
        }

        DrClaimsGuarantee[] drClaimsGuarantee = drClaimsLoan.getDrClaimsGuarantee();
        if (drClaimsGuarantee != null) {
            drClaimsGuaranteeDAO.deleteDrClaimsGuarantee(drClaimsLoan.getId());
            for (DrClaimsGuarantee guarantee : drClaimsGuarantee) {
                if (guarantee.getAssessAmount() != null || guarantee.getGuaranteeAmount() != null ||
                        !Utils.strIsNull(guarantee.getGuarantee()) || !Utils.strIsNull(guarantee.getGuarantor())) {
                    if (Utils.isObjectNotEmpty(guarantee.getAssessAmount())) {
                        guarantee.setAssessAmount(guarantee.getAssessAmount().multiply(new BigDecimal(10000)));
                    }
                    guarantee.setLid(drClaimsLoan.getId());
                    drClaimsGuaranteeDAO.insertDrClaimsGuarantee(guarantee);
                }
            }
        }

        DrClaimsShareholder[] drClaimsShareholder = drClaimsLoan.getDrClaimsShareholder();
        if (drClaimsShareholder != null) {
            drClaimsShareholderDAO.deleteDrClaimsShareholder(drClaimsLoan.getId());
            for (DrClaimsShareholder shareholder : drClaimsShareholder) {
                if (!Utils.strIsNull(shareholder.getName())) {
                    shareholder.setLid(drClaimsLoan.getId());
                    drClaimsShareholderDAO.insertDrClaimsShareholder(shareholder);
                }
            }
        }

        List<DrClaimsProject> drClaimsProjectList = drClaimsLoan.getDrClaimsProject();
        drClaimsProjectDAO.deleteDrClaimsProjectByLid(drClaimsLoan.getId());
        if (!Utils.isEmptyList(drClaimsProjectList)) {
            for (DrClaimsProject drClaimsProject : drClaimsProjectList) {
                if (!Utils.strIsNull(drClaimsProject.getName())) {
                    drClaimsProject.setLid(drClaimsLoan.getId());
                    drClaimsProjectDAO.insertDrClaimsProject(drClaimsProject);
                }
            }
        }

        List<DrClaimsPic> drClaimsPic = drClaimsLoan.getDrClaimsPic();
        List<DrClaimsPic> useClaimsPic = new ArrayList<DrClaimsPic>();
        SFtpUtil sftp = new SFtpUtil();

        if (drClaimsPic != null) {
            String realPath = ConfigUtil.getImgFileUrl() + ConfigUtil.claimsPic + Utils.format(new Date(), "yyyy-MM") + "/" + drClaimsLoan.getId() + "/";
            String savePath = ConfigUtil.getDomainname() + ConfigUtil.claimsPic + Utils.format(new Date(), "yyyy-MM") + "/" + drClaimsLoan.getId() + "/";

            drClaimsPicDAO.deleteDrClaimsPicByLid(drClaimsLoan.getId());

            for (DrClaimsPic pic : drClaimsPic) {
                if ("use".equals(pic.getBigUrl())) {
                    useClaimsPic.add(pic);
                }
            }
            for (DrClaimsPic oldPic : drClaimsPic) {
                if (oldPic.getId() != null && !"use".equals(oldPic.getBigUrl())) {
                    oldPic.setLid(drClaimsLoan.getId());
                    drClaimsPicDAO.insertDrClaimsPic(oldPic);
                }
            }
            if (claimsFiles.length > 0) {
                for (int i = 0; i < claimsFiles.length; i++) {
                    String imageName = ImageUtils.getServerFileName()
                            + claimsFiles[i].getOriginalFilename().substring(
                            claimsFiles[i].getOriginalFilename().lastIndexOf("."));
                    sftp.connectServer();
                    sftp.put(claimsFiles[i].getInputStream(), realPath, imageName);
                    sftp.closeServer();
                    useClaimsPic.get(i).setBigUrl(savePath + imageName);
                    useClaimsPic.get(i).setLid(drClaimsLoan.getId());
                    drClaimsPicDAO.insertDrClaimsPic(useClaimsPic.get(i));
                }
            }
        } else {
            drClaimsPicDAO.deleteDrClaimsPicByLid(drClaimsLoan.getId());
        }
    }

    @Override
    public boolean updateMaintenance(DrClaimsLoan dcl, DrAuditInfo drAuditInfo) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", 0);//数据类型 0:暂存资料
        map.put("statuses", new int[]{0, 2});//0:待审核,1:审核通过,2:驳回,
        map.put("lid", dcl.getId());
        map.put("offset", 0);
        map.put("limit", 1);
        map.put("orders", "id desc");
        JsClaimsAuditEdit bean = null;
        List<JsClaimsAuditEdit> list = jsClaimsAuditEditDAO.selectByMap(map);
        if (drAuditInfo.getStatus() == 1) {
            if (Utils.isObjectNotEmpty(dcl) && 1 == dcl.getIsAuditEdit()) {
                if (!Utils.isEmptyList(list)) {
                    net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(list.get(0).getCache());
                    DrClaimsLoan dcls = (DrClaimsLoan) net.sf.json.JSONObject.toBean(object, DrClaimsLoan.class);
                    net.sf.json.JSONObject dcfJsonObject = net.sf.json.JSONObject.fromObject(object.get("drClaimsFinanc"));

                    DrClaimsFinanc dcf = drClaimsFinancDAO.getDrClaimsFinancByLid(dcl.getId());
                    DrClaimsCustomer dcc = drClaimsCustomerDAO.getDrClaimsCustomerByLid(dcl.getId());
                    List<DrClaimsGuarantee> dcgList = drClaimsGuaranteeDAO.getDrClaimsGuaranteeByLid(dcl.getId());
                    List<DrClaimsShareholder> dcsList = drClaimsShareholderDAO.getDrClaimsShareholderByLid(dcl.getId());

                    //备份
                    DrClaimsLoan bak = drClaimsLoanDAO.getDrClaimsLoanByid(dcl.getId());
                    bak.setDrClaimsFinanc(dcf);
                    bak.setDrClaimsCustomer(dcc);
                    bak.setDrClaimsGuarantee((DrClaimsGuarantee[]) dcgList.toArray(new DrClaimsGuarantee[dcgList.size()]));
                    bak.setDrClaimsShareholder((DrClaimsShareholder[]) dcsList.toArray(new DrClaimsShareholder[dcsList.size()]));
                    String cache = JSON.toJSONString(bak);


                    //1.
                    dcl.setName(dcls.getName());
                    dcl.setNo(dcls.getNo());
                    dcl.setLoanUse(dcls.getLoanUse());
                    dcl.setCardFlag(dcls.getCardFlag());
                    dcl.setLoanName(dcls.getLoanName());
                    dcl.setBankName(dcls.getBankName());
                    dcl.setBankNo(dcls.getBankNo());
                    dcl.setBankAddress(dcls.getBankAddress());
                    dcl.setIsAuditEdit(0);
                    dcl.setLoanAmount(dcl.getLoanAmount().multiply(new BigDecimal("10000")));
                    drClaimsLoanDAO.updateDrClaimsLoan(dcl);

                    //2.
                    DrClaimsFinanc dcfs = dcls.getDrClaimsFinanc();
                    if (Utils.isObjectNotEmpty(dcfJsonObject.get("establishDate")))
                        dcfs.setEstablishDate(new Date(Long.parseLong(dcfJsonObject.get("establishDate").toString())));

                    if (Utils.isObjectEmpty(dcf)) {
                        dcfs.setLid(dcl.getId());
                        drClaimsFinancDAO.insertDrClaimsFinanc(dcfs);
                    } else {
                        dcfs.setLid(dcf.getLid());
                        dcfs.setId(dcf.getId());
                        drClaimsFinancDAO.updateDrClaimsFinanc(dcfs);
                    }

                    //3
                    DrClaimsCustomer dccs = dcls.getDrClaimsCustomer();
                    if (Utils.isObjectNotEmpty(dcc)) {
                        dcc.setName(dccs.getName());
                        dcc.setSex(dccs.getSex());
                        dcc.setCertificateNo(dccs.getCertificateNo());
                        dcc.setAddress(dccs.getAddress());
                        dcc.setBusinessNo(dccs.getBusinessNo());
                        dcc.setCompanyNameProtocolShow(dccs.getCompanyNameProtocolShow());
                        drClaimsCustomerDAO.updateDrClaimsCustomer(dcc);
                    } else {
                        dccs.setLid(dcl.getId());
                        drClaimsCustomerDAO.insertDrClaimsCustomer(dccs);
                    }

                    //4
                    DrClaimsGuarantee[] dcgArray = dcls.getDrClaimsGuarantee();
                    for (DrClaimsGuarantee dcgn : dcgArray) {
                        if (!Utils.isEmptyList(dcgList)) {
                            for (DrClaimsGuarantee dcg : dcgList) {
                                if (dcgn.getId().intValue() == dcg.getId()) { //理论上没有都不相等
                                    dcg.setIsPawn(dcgn.getIsPawn());
                                    dcg.setPawnType(dcgn.getPawnType());
                                    if (Utils.isObjectNotEmpty(dcgn.getAssessAmount()))
                                        dcg.setAssessAmount(dcgn.getAssessAmount().multiply(new BigDecimal("10000")));
                                    else
                                        dcg.setAssessAmount(dcgn.getAssessAmount());
                                    dcg.setIsAcceptance(dcgn.getIsAcceptance());
                                    dcg.setGuarantor(dcgn.getGuarantor());
                                    dcg.setIsGuarantee(dcgn.getIsGuarantee());
                                    dcg.setGuaranteeType(dcgn.getGuaranteeType());
                                    dcg.setGuarantee(dcgn.getGuarantee());
                                    dcg.setIsFinanc(dcgn.getIsFinanc());
                                    dcg.setFinancType(dcgn.getFinancType());
                                    dcg.setGuaranteeAmount(dcgn.getGuaranteeAmount());
                                    drClaimsGuaranteeDAO.updateDrClaimsGuarantee(dcg);
                                    break;
                                }
                            }
                        } else {
                            dcgn.setLid(dcl.getId());
                            drClaimsGuaranteeDAO.insertDrClaimsGuarantee(dcgn);
                        }
                    }
                    //5.
                    DrClaimsShareholder[] dcsArray = dcls.getDrClaimsShareholder();
                    for (DrClaimsShareholder dcsn : dcsArray) {
                        if (!Utils.isEmptyList(dcsList)) {
                            for (DrClaimsShareholder dcs : dcsList) {
                                if (dcsn.getId().intValue() == dcs.getId()) {
                                    dcs.setName(dcsn.getName());
                                    dcs.setSex(dcsn.getSex());
                                    dcs.setCertificateNo(dcsn.getCertificateNo());
                                    drClaimsShareholderDAO.updateDrClaimsShareholder(dcs);
                                    break;
                                }
                            }
                        } else {
                            dcsn.setLid(dcl.getId());
                            drClaimsShareholderDAO.insertDrClaimsShareholder(dcsn);
                        }
                    }
                    //备份
                    list.get(0).setCache(cache);
                    list.get(0).setType(1);
                    list.get(0).setStatus(1);
                    list.get(0).setUpdateUserKy(dcl.getUpdUser());
                    list.get(0).setUpdateTime(new Date());
                    jsClaimsAuditEditDAO.update(list.get(0));

                    return true;
                }
            }
        } else if (drAuditInfo.getStatus() == 2) {
            list.get(0).setStatus(2);
            list.get(0).setUpdateUserKy(dcl.getUpdUser());
            jsClaimsAuditEditDAO.update(list.get(0));
            dcl.setIsAuditEdit(0);
            dcl.setLoanAmount(dcl.getLoanAmount().multiply(new BigDecimal("10000")));
            drClaimsLoanDAO.updateDrClaimsLoan(dcl);
            return true;
        }
        return false;
    }

    @Override
    public List<DrAuditInfo> getDrAuditInfo(Map<String, Object> map) {
        return drAuditInfoDAO.getDrAuditInfo(map);
    }

    @Override
    public DrClaimsBill getDrClaimsBillByLid(Integer lid) {
        return drClaimsBillDAO.getDrClaimsBillByLid(lid);
    }

    @Override
    public DrClaimsCustomer getDrClaimsCustomerByLid(Integer lid) {
        return drClaimsCustomerDAO.getDrClaimsCustomerByLid(lid);
    }

    @Override
    public DrClaimsFinanc getDrClaimsFinancByLid(Integer lid) {
        return drClaimsFinancDAO.getDrClaimsFinancByLid(lid);
    }

    @Override
    public List<DrClaimsGuarantee> getDrClaimsGuaranteeByLid(Integer lid) {
        return drClaimsGuaranteeDAO.getDrClaimsGuaranteeByLid(lid);
    }

    @Override
    public DrClaimsLoan getDrClaimsLoanByid(Integer id) {
        return drClaimsLoanDAO.getDrClaimsLoanByid(id);
    }

    @Override
    public List<DrClaimsShareholder> getDrClaimsShareholderByLid(Integer lid) {
        return drClaimsShareholderDAO.getDrClaimsShareholderByLid(lid);
    }

    @Override
    public List<DrClaimsPic> getDrClaimsPicByLid(Integer lid) {
        return drClaimsPicDAO.getDrClaimsPicByLid(lid);
    }

    @Override
    public void insertDrAuditInfo(DrAuditInfo drAuditInfo) throws Exception {
        drAuditInfoDAO.insertDrAuditInfo(drAuditInfo);
        DrClaimsLoan drClaimsLoan = new DrClaimsLoan();
        drClaimsLoan.setId(drAuditInfo.getFid());
        if (drAuditInfo.getStatus() == 1) {
            drClaimsLoan.setStatus(2);
        }
        if (drAuditInfo.getStatus() == 2) {
            drClaimsLoan.setStatus(3);
        }
        if (drAuditInfo.getStatus() == 3) {
            drClaimsLoan.setStatus(7);

            DrClaimsPic drClaimsPic = new DrClaimsPic();
            drClaimsPic.setLid(drAuditInfo.getFid());
            drClaimsPic.setStatus(0);
            drClaimsPicDAO.updateDrClaimsPic(drClaimsPic);
        }
        drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
    }

    @Override
    public List<DrClaimsLoan> getDrClaimsLoanByMap(String no) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("no", no);
        List<DrClaimsLoan> list = drClaimsLoanDAO.getDrClaimsLoanByMap(map);
        return list;
    }

    @Override
    public Map<String, Object> getDrClaimsLoanSum(DrClaimsLoan drClaimsLoan) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> result = new HashMap<String, Object>();
        map.put("dueStartDate", Utils.format(drClaimsLoan.getDueStartDate(), "yyyy-MM-dd"));
        map.put("dueEndDate", Utils.format(drClaimsLoan.getDueEndDate(), "yyyy-MM-dd"));
        map.put("status", new Integer[]{5});

        BigDecimal pendRepayAmount = drClaimsLoanDAO.getDrClaimsLoanSum(map);
        Integer total = drClaimsLoanDAO.getDrClaimsLoanCounts(map);
        result.put("pendRepayAmount", pendRepayAmount == null ? 0 : pendRepayAmount);
        result.put("total", total);
        return result;
    }

    @Override
    public Map<String, Object> exportLoan(DrClaimsLoan drClaimsLoan) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("excelName", "loan_record" + System.currentTimeMillis() + ".xls");
        map.put("dueStartDate", Utils.format(drClaimsLoan.getEndDate(), "yyyy-MM-dd"));
        map.put("dueEndDate", Utils.format(drClaimsLoan.getEndDate(), "yyyy-MM-dd"));
        if (drClaimsLoan.getStatus() == null) {
            map.put("status", drClaimsLoan.getStatus());
        } else {
            if (drClaimsLoan.getStatus() == 200) {
                map.put("status", new Integer[]{5});
            }
        }
        List<DrClaimsLoan> list = drClaimsLoanDAO.getDrClaimsLoanList(map);
        String[] title = new String[]{"序号", "借款合同编号", "借款产品名称", "借款企业名称", "借款金额(元)", "到期日期", "借款利率", "待还款金额(元)", "当前状态"};
        Integer[] columnWidth = new Integer[]{5, 20, 20, 20, 20, 50, 20, 20, 20};

        List<List<Object>> tableList = new ArrayList<List<Object>>();
        List<Object> lc = null;
        int i = 1;
        for (DrClaimsLoan loan : list) {
            lc = new ArrayList<Object>();
            lc.add(i++);
            lc.add(loan.getNo());
            lc.add(loan.getName());
            lc.add(loan.getCompanyName() == null ? "" : loan.getCompanyName());
            lc.add(loan.getLoanAmount());
            lc.add(Utils.getparseDate(loan.getEndDate(), "yyyy-MM-dd hh:mm"));
            lc.add(loan.getRate());
            lc.add(loan.getLoanAmount());
            lc.add(loan.getStatusName());
            tableList.add(lc);
        }

        map.put("titles", title);
        map.put("columnWidth", columnWidth);
        map.put("list", tableList);
        return map;
    }

    @Override
    public void updateRepayStatusClaimsInfo(Integer id) throws Exception {
        DrClaimsLoan drClaimsLoan = new DrClaimsLoan();
        drClaimsLoan.setId(id);
        drClaimsLoan.setStatus(6);
        drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
    }

    @Override
    public void updateBatchRepayDrClaimsInfo(Integer[] ids) throws Exception {
        for (Integer id : ids) {
            DrClaimsLoan drClaimsLoan = new DrClaimsLoan();
            drClaimsLoan.setId(id);
            drClaimsLoan.setStatus(6);
            drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
        }
    }

    @Override
    public void insertDrSubjectInfoAudit(DrAuditInfo drAuditInfo) throws Exception {
        drAuditInfoDAO.insertDrAuditInfo(drAuditInfo);
        DrClaimsLoan drClaimsLoan = new DrClaimsLoan();
        drClaimsLoan.setId(drAuditInfo.getFid());
        //复核通过，生成标的
        if (drAuditInfo.getStatus() == 4) {
            drClaimsLoan.setStatus(4);
            DrClaimsLoan loan = drClaimsLoanDAO.getDrClaimsLoanByid(drAuditInfo.getFid());
            DrClaimsBill drClaimsBill = drClaimsBillDAO.getDrClaimsBillByLid(drAuditInfo.getFid());
            DrSubjectInfo drSubjectInfo = new DrSubjectInfo();

            drSubjectInfo.setCode(Utils.createOrderNo(2, drAuditInfo.getAddUser(), "BD-"));
            drSubjectInfo.setLid(loan.getId());
            drSubjectInfo.setStatus(1);
            drSubjectInfo.setType(drClaimsBill.getType());
            drSubjectInfo.setAmount(loan.getLoanAmount().multiply(new BigDecimal(10000)));
            drSubjectInfo.setSurplusAmount(loan.getLoanAmount().multiply(new BigDecimal(10000)));
            drSubjectInfo.setStartDate(loan.getStartDate());
            drSubjectInfo.setEndDate(loan.getEndDate());
            drSubjectInfo.setAddUser(drAuditInfo.getAddUser());
            drSubjectInfoDAO.insertDrSubjectInfo(drSubjectInfo);
        }
        //复核驳回
        if (drAuditInfo.getStatus() == 2) {
            drClaimsLoan.setStatus(10);
        }
        //复核拒绝
        if (drAuditInfo.getStatus() == 3) {
            drClaimsLoan.setStatus(11);
        }
        drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
    }

    @Override
    public BaseResult getDrClaimsStatisList(DrClaimsLoan drClaimsLoan, PageInfo pi) {
        Map<String, PageInfo> resultMap = new HashMap<String, PageInfo>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("no", drClaimsLoan.getNo());
        map.put("name", drClaimsLoan.getName());
        map.put("dueStartDate", Utils.format(drClaimsLoan.getDueStartDate(), "yyyy-MM-dd"));
        map.put("dueEndDate", Utils.format(drClaimsLoan.getDueEndDate(), "yyyy-MM-dd"));
        map.put("status", new Integer[]{2, 4, 5, 6});
        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());
        List<DrClaimsLoan> list = drClaimsLoanDAO.getDrClaimsStatisList(map);
        Integer total = drClaimsLoanDAO.getDrClaimsStatisCounts(map);
        pi.setTotal(total);
        pi.setRows(list);
        resultMap.put("page", pi);
        BaseResult br = new BaseResult();
        br.setMap(resultMap);
        return br;
    }

    @Override
    public List<DrClaimsProject> getDrClaimsProjectByLid(int lid) {
        return drClaimsProjectDAO.getDrClaimsProjectByLid(lid);
    }

    @Override
    public void updateDrClaimsLoan(DrClaimsLoan drClaimsLoan)
            throws Exception {
        drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);

    }

    @Override
    public void updateShowOffStatusBtn(Integer id) {
        drClaimsLoanDAO.updateShowOffStatusBtn(id);
    }

    @Override
    public void updateShowOnStatusBtn(Integer id) {
        drClaimsLoanDAO.updateShowOnStatusBtn(id);
    }

    @Override
    public List<DrClaimsLoan> exportClaimsLoanList(Map<String, Object> param) {
        return drClaimsLoanDAO.exportClaimsLoanList(param);
    }

    @Override
    public List<JsClaimsAuditEdit> selectJsClaimsAuditEditByMap(
            Map<String, Object> map) {

        return jsClaimsAuditEditDAO.selectByMap(map);
    }

    @Override
    public PageInfo selectJsLoanRecord(Integer page, Integer rows, JsLoanRecord jlr) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (page == null) {
            page = PageInfo.DEFAULT_PAGE_ON;
        }
        if (rows == null) {
            rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
        }
        PageInfo pi = new PageInfo(page, rows);

        map.put("offset", pi.getPageInfo().getOffset());
        map.put("limit", pi.getPageInfo().getLimit());

        map.put("claimsName", jlr.getClaimsName());
        map.put("contractCode", jlr.getContractCode());
        map.put("productName", jlr.getProductName());
        map.put("company", jlr.getCompany());
        map.put("orders", " jlr.id desc ");

        List<JsLoanRecord> list = jsLoanRecordDAO.selectJsLoanRecordByMap(map);
        int total = jsLoanRecordDAO.selectJsLoanRecordCountByMap(map);

        pi.setRows(list);
        pi.setTotal(total);

        return pi;
    }

    @Override
    public DrClaimsLoan getDrClaimsLoanBySid(Integer sid) {
        return drClaimsLoanDAO.getDrClaimsLoanBySid(sid);
    }

    @Override
    public void updateDrClaimsCustomer(JSONObject json) throws Exception {
        JSONObject message = json.getJSONObject("message");
        if (JZHService.verifySignAsynNotice(new claimsRspData(message), json.getString("signature"))) {

            if ("0000".equals((String) message.get("resp_code"))) {
                DrClaimsCustomer customer = drClaimsCustomerDAO.selectCustomerByMchnt_txn_ssn((String) message.get("mchnt_txn_ssn"));
                if (Utils.isObjectNotEmpty(customer) && Utils.strIsNull(customer.getUser_id())) {

                    String phone = (String) message.get("mobile_no");
                    String certif_id = (String) message.get("certif_id");
                    String artif_nm = (String) message.get("artif_nm");
                    DrClaimsCustomer drClaimsCustomer = new DrClaimsCustomer();
                    //				drClaimsCustomer.setUser_id((String)message.get("user_id"));
                    //				drClaimsCustomer.setFuiou_acnt((String)message.get("fuiou_acnt"));
                    drClaimsCustomer.setMchnt_txn_ssn((String) message.get("mchnt_txn_ssn"));
                    drClaimsCustomer.setUser_id(phone);
                    drClaimsCustomer.setPhone(phone);
                    drClaimsCustomer.setCertificateNo(certif_id);
                    drClaimsCustomer.setName(artif_nm);
                    drClaimsCustomer.setCompanyName((String) message.get("cust_nm"));
                    drClaimsCustomer.setUpdataIdAndAcnt(1);
                    drClaimsCustomerDAO.updateDrClaimsCustomer(drClaimsCustomer);
                    //更新债权信息
                    DrClaimsLoan drClaimsLoan = new DrClaimsLoan();
                    drClaimsLoan.setBankNo((String) message.get("capAcntNo"));
                    drClaimsLoan.setBankName((String) message.get("bank_nm"));
                    drClaimsLoan.setId(customer.getLid());
                    drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
                    //生成系统用户
                    SysUsersVo users = sysUsersVoDAO.getUserByPhone(phone);
                    if (Utils.isObjectEmpty(users)) {
                        SysUsersVo sysUsersVo = new SysUsersVo();
                        sysUsersVo.setLoginId(phone);
                        sysUsersVo.setName((String) message.get("artif_nm"));
                        sysUsersVo.setMobile(phone);
                        sysUsersVo.setStatus((short) 1);
                        sysUsersVo.setRegistertime(new Date());
                        sysUsersVo.setPassword(SecurityUtils.MD5AndSHA256(certif_id.substring(certif_id.length() - 6, certif_id.length())));
                        sysUsersVo.setLastLoginIp("");
                        sysUsersVoDAO.insert(sysUsersVo);
                        SysUserRoleVo roleVo = new SysUserRoleVo();
                        SysRoleVo sysRoleVo = new SysRoleVo();
                        Long roleKy = null;
                        //默认查找coke为qykh的角色
                        sysRoleVo.setRoleCode("Enterprise-User");
                        List<SysRoleVo> list = sysRoleVoDAO.queryRole(sysRoleVo);
                        if (list.size() > 0) {
                            roleKy = list.get(0).getRoleKy();
                        }
                        roleVo.setRoleKy(roleKy);
                        roleVo.setUserKy(sysUsersVo.getUserKy());
                        roleVo.setStatus((short) 1);
                        sysUserRoleVoDAO.insert(roleVo);
                    }
                }


            }
        } else {
            System.out.println("[" + Utils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "]企业开户验签失败---------------------");
        }
    }

    @Override
    public BaseResult getDrClaimsCustomerById(Map<String, Object> param) {
        BaseResult br = new BaseResult();
        DrClaimsCustomer drClaimsCustomer = drClaimsCustomerDAO.getDrClaimsCustomerById(Integer.valueOf(param.get("id").toString()));
        if (StringUtils.isBlank(drClaimsCustomer.getPhone())) {
            br.setSuccess(false);
            br.setErrorMsg("手机号码为空，请先去维护手机号码");
            return br;
        }
        if (StringUtils.isBlank(drClaimsCustomer.getName())) {
            br.setSuccess(false);
            br.setErrorMsg("法人姓名为空，请先去维护法人姓名");
            return br;
        }
        if (StringUtils.isBlank(drClaimsCustomer.getLoanUse())) {
            br.setSuccess(false);
            br.setErrorMsg("贷款用途为空，请先去维护贷款用途");
            return br;
        }
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", drClaimsCustomer.getPhone());
            br = FuiouConfig.queryUserInfs(map);
            if (br.isSuccess()) {
                JSONObject json = (JSONObject) br.getMap();
                JSONObject result = json.getJSONObject("results").getJSONObject("result");
                String contract_st = (String) result.get("contract_st");
                String user_st = (String) result.get("user_st");
                String login_id = (String) result.get("login_id");
                String User_tp = (String) result.get("user_tp");
                if ("1".equals(user_st) && "0".equals(User_tp)) {
                    br.setSuccess(false);
                    br.setErrorMsg("该企业已开户！");
                    //校验开户企业和当前企业是否为同一个企业
                    if (drClaimsCustomer.getCompanyName().trim().equals((String) result.get("cust_nm"))) {
                        //更新企业账户信息
                        DrClaimsCustomer customer = new DrClaimsCustomer();
//						//查询首次企业开户时的流水号
//						DrClaimsCustomer ssnCustomer = drClaimsCustomerDAO.selectCustmoerByUserId(login_id);
//						customer.setMchnt_txn_ssn(ssnCustomer.getMchnt_txn_ssn());
                        customer.setUser_id(login_id);
                        customer.setId(Integer.valueOf(param.get("id").toString()));
                        customer.setPhone((String) result.get("mobile_no"));
                        String certif_id = (String) result.get("certif_id");
                        customer.setCertificateNo(certif_id);
//						customer.setName((String)message.get("artif_nm"));
                        customer.setCompanyName((String) result.get("cust_nm"));
                        drClaimsCustomerDAO.updateDrClaimsCustomer(customer);
                        //更新债权信息
                        DrClaimsLoan drClaimsLoan = new DrClaimsLoan();
                        drClaimsLoan.setId(Integer.valueOf(param.get("lid").toString()));
                        drClaimsLoan.setBankNo((String) result.get("capAcntNo"));
                        drClaimsLoan.setBankName((String) result.get("bank_nm"));
                        drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
                        //生成系统用户 ceces
                        SysUsersVo users = sysUsersVoDAO.getUserByPhone(drClaimsCustomer.getPhone());
                        if (Utils.isObjectEmpty(users)) {
                            SysUsersVo sysUsersVo = new SysUsersVo();
                            sysUsersVo.setLoginId(drClaimsCustomer.getPhone());
                            sysUsersVo.setName((String) result.get("artif_nm"));
                            sysUsersVo.setMobile(drClaimsCustomer.getPhone());
                            sysUsersVo.setStatus((short) 1);
                            sysUsersVo.setRegistertime(new Date());
                            sysUsersVo.setPassword(SecurityUtils.MD5AndSHA256(certif_id.substring(certif_id.length() - 6, certif_id.length())));
                            sysUsersVo.setLastLoginIp("");
                            sysUsersVoDAO.insert(sysUsersVo);
                            SysUserRoleVo roleVo = new SysUserRoleVo();
                            SysRoleVo sysRoleVo = new SysRoleVo();
                            Long roleKy = null;
                            //默认查找coke为qykh的角色
                            sysRoleVo.setRoleCode("Enterprise-User");
                            List<SysRoleVo> list = sysRoleVoDAO.queryRole(sysRoleVo);
                            if (list.size() > 0) {
                                roleKy = list.get(0).getRoleKy();
                            }
                            roleVo.setRoleKy(roleKy);
                            roleVo.setUserKy(sysUsersVo.getUserKy());
                            roleVo.setStatus((short) 1);
                            sysUserRoleVoDAO.insert(roleVo);
                        }
                        return br;
                    } else {
                        br.setSuccess(false);
                        br.setErrorMsg("手机号[" + drClaimsCustomer.getPhone() + "],已被企业[" + result.get("cust_nm") + "]开户,不能重复使用!");
                        return br;
                    }
                } else if ("1".equals(user_st) && "1".equals(User_tp)) {
                    br.setSuccess(false);
                    br.setErrorMsg("该手机号已经开通了个人账号！");
                    return br;
                }
            } else {
                br.setSuccess(false);
                br.setErrorMsg("查询企业信息失败！");
                return br;
            }
            String resultMessage = FuiouConfig.enterpriseAccountData(drClaimsCustomer);
            JSONObject jsonObject = JSONObject.fromObject(resultMessage);
            JSONObject message = jsonObject.getJSONObject("message");
            DrClaimsCustomer customer = new DrClaimsCustomer();
            customer.setMchnt_txn_ssn((String) message.get("mchnt_txn_ssn"));
            customer.setLid(drClaimsCustomer.getLid());
            customer.setId(drClaimsCustomer.getId());
            drClaimsCustomerDAO.updateDrClaimsCustomer(customer);
            param.clear();
            param.put("message", message);
            param.put("signature", resultMessage);
            param.put("fuiouUrl", FuiouConfig.WEBARTIFREGURL);
            br.setMap(param);
            br.setSuccess(true);
        } catch (SQLException e) {
            e.printStackTrace();
            br.setSuccess(false);
        }
        return br;
    }


    @Override
    public BaseResult getDrClaimsCustomerByHttp(Map<String, Object> param) {
        BaseResult br = new BaseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DrClaimsCustomer drCustomer = drClaimsCustomerDAO.getDrClaimsCustomerById(Integer.valueOf(param.get("id").toString()));
            if (StringUtils.isBlank(drCustomer.getPhone())) {
                br.setSuccess(false);
                br.setErrorMsg("手机号码为空，请先去维护手机号码");
                return br;
            }
            map.put("userId", drCustomer.getPhone());
            br = FuiouConfig.queryUserInfs(map);
            if (br.isSuccess()) {
                JSONObject json = (JSONObject) br.getMap();
                JSONObject result = json.getJSONObject("results").getJSONObject("result");
                String contract_st = (String) result.get("contract_st");
                String user_st = (String) result.get("user_st");
                String login_id = (String) result.get("login_id");
                String User_tp = (String) result.get("user_tp");
                if ("1".equals(user_st) && "0".equals(User_tp)) {
                    br.setSuccess(false);
                    br.setErrorMsg("该企业已开户！");
                    //更新企业账户信息
                    DrClaimsCustomer customer = new DrClaimsCustomer();
                    customer.setUser_id(login_id);
                    customer.setId(Integer.valueOf(param.get("id").toString()));
                    customer.setPhone((String) result.get("mobile_no"));
                    customer.setCertificateNo((String) result.get("certif_id"));
                    customer.setCompanyName((String) result.get("cust_nm"));
                    drClaimsCustomerDAO.updateDrClaimsCustomer(customer);
                    //更新债权信息
                    DrClaimsLoan drClaimsLoan = drClaimsLoanDAO.getDrClaimsLoanByid(Integer.valueOf(param.get("lid").toString()));
                    drClaimsLoan.setBankNo((String) result.get("capAcntNo"));
                    drClaimsLoan.setBankName((String) result.get("bank_nm"));
                    drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
                    return br;
                } else if ("1".equals(user_st) && "1".equals(User_tp)) {
                    br.setSuccess(false);
                    br.setErrorMsg("该手机号已经开通了个人账号！");
                    return br;
                }
            } else {
                br.setSuccess(false);
                br.setErrorMsg("查询企业信息失败！");
                return br;
            }
            DrClaimsCustomer drClaimsCustomer = drClaimsCustomerDAO.getDrClaimsCustomerById(Integer.valueOf(param.get("id").toString()));
            map.put("customer", drClaimsCustomer);
            br.setMap(map);
            br.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            br.setSuccess(false);
            br.setErrorMsg("系統异常");
        }
        return br;
    }

    @Override
    public List<Map<String, Object>> selectCustomerFuiou(Map<String, Object> param) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> customers = drClaimsCustomerDAO.selectCustomerFuiou(param);
        if (customers.size() > 0) {
            for (Map<String, Object> map : customers) {
                BaseResult br = new BaseResult();
                br = FuiouConfig.queryUserInfs(map);

                if (br.isSuccess()) {
                    JSONObject results = (JSONObject) br.getMap().get("results");
                    JSONObject result = (JSONObject) results.get("result");
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("bankNm", result.get("bank_nm"));//开户行
                    resultMap.put("cardNo", result.get("capAcntNo"));//卡号
                    /*resultMap.put("custNm", map.get("name"));//名称*/
                    resultMap.put("mobileNo", result.get("mobile_no"));//手机号
                    resultMap.put("certifId", result.get("certif_id"));//身份证号码
                    resultMap.put("custNm", result.get("cust_nm"));//企业名称
                    resultMap.put("id", map.get("id"));//企业id

                    BaseResult account = new BaseResult();
                    Map<String, String> m = new HashMap<>();
                    m.put("cust_no", map.get("userId").toString());
                    account = FuiouConfig.balanceAction(m);
                    System.out.print(account.getMap());
                    if (account.isSuccess()) {
                        JSONObject accountresults = (JSONObject) account.getMap().get("results");
                        JSONObject accountresult = (JSONObject) accountresults.get("result");
                        resultMap.put("ct_balance", Utils.setScale(Utils.nwdDivide(Utils.isObjectEmpty(accountresult.get("ct_balance")) ? 0 : accountresult.get("ct_balance"), 100)));//账户余额
                        resultMap.put("ca_balance", Utils.setScale(Utils.nwdDivide(Utils.isObjectEmpty(accountresult.get("ca_balance")) ? 0 : accountresult.get("ca_balance"), 100)));//可用余额
                        resultMap.put("cf_balance", Utils.setScale(Utils.nwdDivide(Utils.isObjectEmpty(accountresult.get("cf_balance")) ? 0 : accountresult.get("cf_balance"), 100)));//冻结余额
                    }
                    if (Utils.isObjectNotEmpty(result.get("mobile_no")))
                        resultMap.put("mobileNo", result.get("mobile_no").toString().substring(0, 3) + "****" + result.get("mobile_no").toString().substring(result.get("mobile_no").toString().length() - 4));//手机号
                    if (Utils.isObjectNotEmpty(result.get("certif_id")))
                        resultMap.put("certifId", result.get("certif_id").toString().substring(0, 4) + "****" + result.get("certif_id").toString().substring(result.get("certif_id").toString().length() - 4));//手机号
                    if (Utils.isObjectNotEmpty(result.get("capAcntNo")))
                        resultMap.put("cardNo", result.get("capAcntNo").toString().substring(0, 4) + "****" + result.get("capAcntNo").toString().substring(result.get("capAcntNo").toString().length() - 4));//手机号

                    list.add(resultMap);
                }
            }
        }
        return list;
    }

    @Override
    public int selectCustomerFuiouCount(Map<String, Object> param) {
        return drClaimsCustomerDAO.selectCustomerFuiouCount(param);
    }

    @Override
    public DrClaimsCustomer selectDrClaimsCustomerById(Integer id) {
        DrClaimsCustomer drClaimsCustomer = drClaimsCustomerDAO.getDrClaimsCustomerById(id);
        return drClaimsCustomer;
    }

    @Override
    public List<Map<String, Object>> getFuiouAreaCity() {
        return drClaimsLoanDAO.getFuiouAreaCity();
    }

    @Override
    public List<Map<String, Object>> getFuiouAreaByCity(Integer cityCode) {
        return drClaimsLoanDAO.getFuiouAreaByCity(cityCode);
    }

    @Override
    public List<Map<String, Object>> getFuiouBankCode() {
        return drClaimsLoanDAO.getFuiouBankCode();
    }

    public List<Map<String, Object>> selectGenerateAndupload() {
        List<Map<String, Object>> customers = drClaimsCustomerDAO.selectGenerateAndupload();
        return customers;
    }

    @Override
    public void updateFileStatus(List<DrClaimsCustomer> param) {
        drClaimsCustomerDAO.updateFileStatus(param);

    }

    @Override
    public BaseResult getFuiouEnterpriseInfo(Map<String, Object> map) {
        BaseResult br = new BaseResult();
        br = FuiouConfig.queryUserInfs(map);
        if (br.isSuccess()) {
            Map<String, Object> json = (Map<String, Object>) br.getMap();
            JSONObject results = (JSONObject) json.get("results");
            JSONObject result = (JSONObject) results.get("result");
            br.setMap(result);
        }
        return br;
    }

    @Override
    public DrClaimsCustomer selectCustomerByPhone(int id) {
        // TODO Auto-generated method stub
        return drClaimsCustomerDAO.selectCustomerByPhone(id);
    }

    @Override
    public BaseResult addArtifReg(Map<String, Object> param) {
        BaseResult br = new BaseResult();
        String artif_nm = null;
        try {
            String cust_nm = new String(param.get("cust_nm").toString().toLowerCase().getBytes("ISO-8859-1"), "UTF-8");
            artif_nm = new String(param.get("artif_nm").toString().toLowerCase().getBytes("ISO-8859-1"), "UTF-8");
            String bank_nm = new String(param.get("bank_nm").toString().toLowerCase().getBytes("ISO-8859-1"), "UTF-8");
            String password = Md5Utils.md5(param.get("password").toString(), "UTF-8");
            String lpassword = Md5Utils.md5(param.get("lpassword").toString(), "UTF-8");
            param.put("cust_nm", cust_nm);
            param.put("artif_nm", artif_nm);
            param.put("bank_nm", bank_nm);
            param.put("password", password);
            param.put("lpassword", lpassword);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        br = FuiouConfig.artifReg(param);
        if (!br.isSuccess()) {
            return br;
        } else {
            String resp_code = (String) br.getMap().get("resp_code");
            if (!resp_code.equals("0000")) {
                br.setErrorCode((String) br.getMap().get("resp_code"));
                br.setErrorMsg("存管返回:" + (String) br.getMap().get("resp_desc"));
                br.setMap(null);
                br.setSuccess(false);
                return br;
            }
        }
        try {
            //更新企业信息
            DrClaimsCustomer drClaimsCustomer = new DrClaimsCustomer();
            // drClaimsCustomer.setUser_id((String)message.get("user_id"));
            // drClaimsCustomer.setFuiou_acnt((String)message.get("fuiou_acnt"));
            drClaimsCustomer.setMchnt_txn_ssn((String) br.getMap().get("mchnt_txn_ssn"));
            drClaimsCustomer.setUser_id((String) param.get("mobile_no"));
            drClaimsCustomer.setPhone((String) param.get("mobile_no"));
            drClaimsCustomer.setCertificateNo((String) param.get("certif_id"));
            drClaimsCustomer.setName((String) param.get("artif_nm"));
            drClaimsCustomer.setCompanyName((String) param.get("cust_nm"));
//			drClaimsCustomer.setUpdataIdAndAcnt(1);
            drClaimsCustomer.setId(Integer.parseInt(param.get("customerId").toString()));
            drClaimsCustomerDAO.updateDrClaimsCustomer(drClaimsCustomer);
            // 更新债权信息
            DrClaimsCustomer customer = drClaimsCustomerDAO.selectCustomerByMchnt_txn_ssn((String) br.getMap().get("mchnt_txn_ssn"));
            DrClaimsLoan drClaimsLoan = new DrClaimsLoan();
            drClaimsLoan.setBankNo((String) param.get("capAcntNo"));
            drClaimsLoan.setBankName((String) param.get("bank_nm"));
            drClaimsLoan.setId(customer.getLid());
            drClaimsLoanDAO.updateDrClaimsLoan(drClaimsLoan);
            //生成系统用户
            String phone = param.get("mobile_no").toString();
            SysUsersVo sysUsersVo = new SysUsersVo();
            sysUsersVo.setLoginId(phone);
            sysUsersVo.setName(artif_nm);
            sysUsersVo.setMobile(phone);
            sysUsersVo.setStatus((short) 1);
            sysUsersVo.setRegistertime(new Date());
            sysUsersVo.setPassword(SecurityUtils.MD5AndSHA256(phone.substring(phone.length() - 6, phone.length())));
            sysUsersVo.setLastLoginIp("");
            sysUsersVoDAO.insert(sysUsersVo);
            SysUserRoleVo roleVo = new SysUserRoleVo();
            SysRoleVo sysRoleVo = new SysRoleVo();
            Long roleKy = null;
            //默认查找coke为qykh的角色
            sysRoleVo.setRoleCode("qykh");
            List<SysRoleVo> list = sysRoleVoDAO.queryRole(sysRoleVo);
            if (list.size() > 0) {
                roleKy = list.get(0).getRoleKy();
            }
            roleVo.setRoleKy(roleKy);
            roleVo.setUserKy(sysUsersVo.getUserKy());
            roleVo.setStatus((short) 1);
            sysUserRoleVoDAO.insert(roleVo);
            br.setSuccess(true);
            br.setMsg("开户成功!");
        } catch (Exception e) {
            e.printStackTrace();
            br.setSuccess(false);
            br.setErrorMsg("更新债权信息失败");
        }
        return br;
    }

    @Override
    public void updateCompanyStatus() {
        drClaimsCustomerDAO.updateCompanyStatus();
    }
}
