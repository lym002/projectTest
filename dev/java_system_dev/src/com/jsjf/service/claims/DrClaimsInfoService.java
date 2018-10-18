package com.jsjf.service.claims;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
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

import net.sf.json.JSONObject;

public interface DrClaimsInfoService {

	/**
	 * 拿到贷款项目基本信息列表数据
	 * 
	 * @param DrClaimsLoan
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrClaimsLoanList(DrClaimsLoan drClaimsLoan, PageInfo pi);

	/**
	 * 添加债权信息
	 * 
	 * @param DrClaimsLoan
	 * @param claimsFiles
	 * @throws Exception
	 */
	public void insertDrClaimsInfo(DrClaimsLoan drClaimsLoan, MultipartFile[] claimsFiles) throws Exception;
	/**
	 * 对接资产
	 *
	 * @throws Exception
	 */
	public void insertDrClaimsInfo(DrClaimsLoan drClaimsLoan, String[] claimsFiles) throws Exception;

	/**
	 * 代收付系统添加债权信息
	 * @param drClaimsLoan
	 * @param claimsFiles
	 * @throws Exception
	 */
	public void insertDrClaimsInfoForPay(DrClaimsLoan drClaimsLoan,
			MultipartFile[] claimsFiles) throws Exception;
	/**
	 * 修改债权信息
	 * 
	 * @param DrClaimsLoan
	 * @param claimsFiles
	 * @throws Exception
	 */
	public void updateDrClaimsInfo(DrClaimsLoan drClaimsLoan, MultipartFile[] claimsFiles) throws Exception;

	/**
	 * 查询审核意见
	 * 
	 * @param Map<String,Object>
	 * @return List<DrAuditInfo>
	 */
	public List<DrAuditInfo> getDrAuditInfo(Map<String, Object> map);

	/**
	 * 根据条件得到票据信息
	 * 
	 * @param lid
	 * @return DrClaimsBill
	 */
	public DrClaimsBill getDrClaimsBillByLid(Integer lid);

	/**
	 * 根据条件得到企业客户基本信息
	 * 
	 * @param lid
	 * @return DrClaimsCustomer
	 */
	public DrClaimsCustomer getDrClaimsCustomerByLid(Integer lid);

	/**
	 * 根据条件得到融资性担保企业信息
	 * 
	 * @param lid
	 * @return DrClaimsFinanc
	 */
	public DrClaimsFinanc getDrClaimsFinancByLid(Integer lid);

	/**
	 * 根据条件得到担保情况
	 * 
	 * @param lid
	 * @return List<DrClaimsGuarantee>
	 */
	public List<DrClaimsGuarantee> getDrClaimsGuaranteeByLid(Integer lid);

	/**
	 * 根据id得到贷款项目基本信息
	 * 
	 * @param id
	 * @return DrClaimsLoan
	 */
	public DrClaimsLoan getDrClaimsLoanByid(Integer id);

	/**
	 * 根据条件得到股东情况
	 * 
	 * @param lid
	 * @return List<DrClaimsShareholder>
	 */
	public List<DrClaimsShareholder> getDrClaimsShareholderByLid(Integer lid);

	/**
	 * 根据条件得到债权图片
	 * 
	 * @param lid
	 * @return DrClaimsPic
	 */
	public List<DrClaimsPic> getDrClaimsPicByLid(Integer lid);

	/**
	 * 添加债权审核
	 * 
	 * @param DrAuditInfo
	 * @throws Exception
	 */
	public void insertDrAuditInfo(DrAuditInfo drAuditInfo) throws Exception;

	/**
	 * 根据MAP得到贷款项目基本信息
	 * 
	 * @param no
	 * @return List<DrClaimsLoan>
	 */
	public List<DrClaimsLoan> getDrClaimsLoanByMap(String no);

	/**
	 * 得到贷款项目基本信息总金额
	 * 
	 * @param DrClaimsLoan
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getDrClaimsLoanSum(DrClaimsLoan drClaimsLoan);

	/**
	 * 导出贷款项目信息
	 * 
	 * @param DrClaimsLoan
	 * @return Map<String,Object>
	 */
	public Map<String, Object> exportLoan(DrClaimsLoan drClaimsLoan) throws Exception;

	/**
	 * 修改为已还款
	 * 
	 * @param id
	 * @return void
	 */
	public void updateRepayStatusClaimsInfo(Integer id) throws Exception;

	/**
	 * 批量修改为已还款
	 * 
	 * @param id
	 * @return void
	 */
	public void updateBatchRepayDrClaimsInfo(Integer[] ids) throws Exception;

	/**
	 * 添加标的审核
	 * 
	 * @param DrAuditInfo
	 * @throws Exception
	 */
	public void insertDrSubjectInfoAudit(DrAuditInfo drAuditInfo) throws Exception;

	/**
	 * 拿到债权统计信息列表数据
	 * 
	 * @param DrClaimsLoan
	 * @param PageInfo
	 * @return BaseResult
	 */
	public BaseResult getDrClaimsStatisList(DrClaimsLoan drClaimsLoan, PageInfo pi);

	/**
	 * 根据PID拿债权审核项目
	 * 
	 * @param lid
	 *            债权ID
	 * @return List<DrClaimsProject>
	 * @throws SQLException;
	 */
	public List<DrClaimsProject> getDrClaimsProjectByLid(int lid);

	/**
	 * 放款
	 * 
	 * @param DrClaimsLoan
	 * @return void
	 */
	public void updateDrClaimsLoan(DrClaimsLoan drClaimsLoan) throws Exception;

	/**
	 * 修改资产为关闭状态
	 * 
	 * @param id
	 */
	public void updateShowOffStatusBtn(Integer id);

	/**
	 * 修改资产为开启状态
	 * 
	 * @param id
	 */
	public void updateShowOnStatusBtn(Integer id);

	/**
	 * 查询导出债权记录
	 * 
	 * @param param
	 * @return
	 */
	public List<DrClaimsLoan> exportClaimsLoanList(Map<String, Object> param);

	public List<JsClaimsAuditEdit> selectJsClaimsAuditEditByMap(Map<String, Object> map);

	public boolean updateMaintenance(DrClaimsLoan dcl, DrAuditInfo drAuditInfo) throws Exception;

	/**
	 * 获取放款记录
	 * 
	 * @param page
	 * @param rows
	 * @param jlr
	 * @return
	 */
	public PageInfo selectJsLoanRecord(Integer page, Integer rows, JsLoanRecord jlr) throws Exception;

	public DrClaimsLoan getDrClaimsLoanBySid(@Param("sid") Integer sid);

	/**
	 * 修改企业客户基本信息
	 * 
	 * @param DrClaimsCustomer
	 * @return void
	 * @throws SQLException;
	 */
	public void updateDrClaimsCustomer(JSONObject message) throws Exception;
	
	/**
     * 存管企业开户（页面）
     * @param id
     * @return DrClaimsCustomer
     */
    public BaseResult getDrClaimsCustomerById(Map<String,Object> param); 
    /**
     * 查询企业信息
     * @param param
     * @return
     */
    public BaseResult getDrClaimsCustomerByHttp(Map<String,Object> param); 
    /**
     * 添加存管信息（直连）
     * @param map
     * @return
     */
    public BaseResult addArtifReg(Map<String,Object> map);
    
  	public List<Map<String, Object>> selectCustomerFuiou(Map<String, Object> param);
  	
  	public int selectCustomerFuiouCount(Map<String,Object> param);
  	
  	public DrClaimsCustomer selectDrClaimsCustomerById(Integer id);
  	
	public List<Map<String, Object>>getFuiouAreaCity();
	
	public List<Map<String, Object>>getFuiouAreaByCity(Integer cityCode);
	
	public List<Map<String, Object>>getFuiouBankCode();
  	public List<Map<String, Object>> selectGenerateAndupload();
  	
  	public void updateFileStatus(List<DrClaimsCustomer> list);
  	/**
  	 * 查询存管企业信息
  	 * @param map
  	 * @return
  	 */
  	public BaseResult getFuiouEnterpriseInfo(Map<String,Object> map);
  	
  	/**
	 * 根据手机号获取企业客户基本信息
	 * @param phone
	 * @return
	 */
	public DrClaimsCustomer selectCustomerByPhone(int id);

	/**
	 * 将多笔债权对应无流水号的法人信息fileStatus更新
	 */
	public void updateCompanyStatus(); 
}
