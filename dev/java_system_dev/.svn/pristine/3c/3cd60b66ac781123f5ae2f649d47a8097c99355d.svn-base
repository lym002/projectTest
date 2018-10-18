package com.jsjf.model.activity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class DrRecommendedSettings {
	
	private Integer id;//主键
	private Integer norm;//奖励标准
	private Integer period;//累计周期
	private Integer modality; //奖励形式
	private Integer standard;//奖励基准
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Integer status;//状态	
	private Integer addUser;//添加人
	private Date addTime;//添加时间
	private String products;//适用产品
	
	private String normName; //标准名称
	private String modalityName;//形式名称
	private String standardName;//基准名称
	private String productName;//产品名称
	private String addName;//添加人姓名
	private String details;
	
	private List<DrRecommendedSettingsDetail> detailList;
	
	
	public DrRecommendedSettings() {
		super();
	}
	
	public DrRecommendedSettings(Integer id, Integer norm, Integer period,
			Integer modality, Integer standard, Date startTime, Date endTime,
			Integer status, Integer addUser, Date addTime) {
		super();
		this.id = id;
		this.norm = norm;
		this.period = period;
		this.modality = modality;
		this.standard = standard;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.addUser = addUser;
		this.addTime = addTime;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the norm
	 */
	public Integer getNorm() {
		return norm;
	}
	/**
	 * @param norm the norm to set
	 */
	public void setNorm(Integer norm) {
		this.norm = norm;
	}
	/**
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	/**
	 * @return the modality
	 */
	public Integer getModality() {
		return modality;
	}
	/**
	 * @param modality the modality to set
	 */
	public void setModality(Integer modality) {
		this.modality = modality;
	}
	/**
	 * @return the standard
	 */
	public Integer getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Integer standard) {
		this.standard = standard;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the addUser
	 */
	public Integer getAddUser() {
		return addUser;
	}
	/**
	 * @param addUser the addUser to set
	 */
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * @return the normName
	 */
	public String getNormName() {
		try {
			normName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("norm")).get(norm);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return normName;
	}

	/**
	 * @param normName the normName to set
	 */
	public void setNormName(String normName) {
		this.normName = normName;
	}

	/**
	 * @return the modalityName
	 */
	public String getModalityName() {
		try {
			modalityName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("modality")).get(modality);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modalityName;
	}

	/**
	 * @param modalityName the modalityName to set
	 */
	public void setModalityName(String modalityName) {
		this.modalityName = modalityName;
	}

	/**
	 * @return the standardName
	 */
	public String getStandardName() {
		try {
			standardName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("standard")).get(standard);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return standardName;
	}

	/**
	 * @param standardName the standardName to set
	 */
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	/**
	 * @return the addName
	 */
	public String getAddName() {
		return addName;
	}

	/**
	 * @param addName the addName to set
	 */
	public void setAddName(String addName) {
		this.addName = addName;
	}


	/**
	 * @return the products
	 */
	public String getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(String products) {
		this.products = products;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		String [] str = products.split(",");
		productName="";
		try {
			for(int i = 0 ; i <str.length ; i++){
				productName +=ConfigUtil.dictionaryMap.get(
						PropertyUtil.getProperties("productType")).get(Integer.parseInt(str[i].toString()))+"/";
			}
			productName = productName.substring(0, productName.length()-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the detailList
	 */
	public List<DrRecommendedSettingsDetail> getDetailList() {
		return detailList;
	}

	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(List<DrRecommendedSettingsDetail> detailList) {
		this.detailList = detailList;
	}


}
