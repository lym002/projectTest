package com.jsjf.model.product;
/**
 * 币优铺活动投资记录
 * @author DELL
 *
 */
public class JsActivityProductInvestInfo {
	
	private Integer id;//id
	private Integer piid;//投资id
	private String 	luckCodes;//幸运码
	private Integer prizeStatus;//中奖状态
	private Integer isUplod;//是否上传了中奖资料
	private String 	prizeMobile;//手机
	private String 	prizeImgUrl;//
	private String 	prizeContent;//感言
	
	public JsActivityProductInvestInfo() {
		super();
	}
	public JsActivityProductInvestInfo(Integer piid, String luckCodes,
			Integer prizeStatus, Integer isUplod, String prizeMobile,
			String prizeImgUrl, String prizeContent) {
		super();
		this.piid = piid;
		this.luckCodes = luckCodes;
		this.prizeStatus = prizeStatus;
		this.isUplod = isUplod;
		this.prizeMobile = prizeMobile;
		this.prizeImgUrl = prizeImgUrl;
		this.prizeContent = prizeContent;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPiid() {
		return piid;
	}
	public void setPiid(Integer piid) {
		this.piid = piid;
	}
	public String getLuckCodes() {
		return luckCodes;
	}
	public void setLuckCodes(String luckCodes) {
		this.luckCodes = luckCodes;
	}
	public Integer getPrizeStatus() {
		return prizeStatus;
	}
	public void setPrizeStatus(Integer prizeStatus) {
		this.prizeStatus = prizeStatus;
	}
	public Integer getIsUplod() {
		return isUplod;
	}
	public void setIsUplod(Integer isUplod) {
		this.isUplod = isUplod;
	}
	public String getPrizeMobile() {
		return prizeMobile;
	}
	public void setPrizeMobile(String prizeMobile) {
		this.prizeMobile = prizeMobile;
	}
	public String getPrizeImgUrl() {
		return prizeImgUrl;
	}
	public void setPrizeImgUrl(String prizeImgUrl) {
		this.prizeImgUrl = prizeImgUrl;
	}
	public String getPrizeContent() {
		return prizeContent;
	}
	public void setPrizeContent(String prizeContent) {
		this.prizeContent = prizeContent;
	}
	
	
	
}
