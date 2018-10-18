package com.jsjf.model.activity;

import java.util.Date;

/**
 * 投即送晒单表
 *
 */
public class JsProductPrizeOrderShare {
	
	private Integer id;//
	private String pcImgUrl;// pc图片url
	private String h5ImgUrl;// h5图片url
	private Integer sort;// 排序，数值大靠前
	private Integer isShow;// 0：不显示，1：显示
	private Date addtime;// 添加时间
	private Integer uid; //用户id
	private String url; //图片
	private String describes; //他说
	private String mobilePhone; //用户手机号
	
	
	
	public String getDescribes() {
		return describes;
	}


	public void setDescribes(String describes) {
		this.describes = describes;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public Integer getUid() {
		return uid;
	}


	public void setUid(Integer uid) {
		this.uid = uid;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public JsProductPrizeOrderShare() {
		super();
	}

	
	public JsProductPrizeOrderShare(Integer id, String pcImgUrl, String h5ImgUrl, Integer sort, Integer isShow,
			Date addtime) {
		super();
		this.id = id;
		this.pcImgUrl = pcImgUrl;
		this.h5ImgUrl = h5ImgUrl;
		this.sort = sort;
		this.isShow = isShow;
		this.addtime = addtime;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPcImgUrl() {
		return pcImgUrl;
	}

	public void setPcImgUrl(String pcImgUrl) {
		this.pcImgUrl = pcImgUrl;
	}

	public String getH5ImgUrl() {
		return h5ImgUrl;
	}

	public void setH5ImgUrl(String h5ImgUrl) {
		this.h5ImgUrl = h5ImgUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
}
