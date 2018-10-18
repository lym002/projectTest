package com.jsjf.model.product;

import java.util.Date;

public class JsProductPrizeOrderShare {
	private Integer  id;//
	private String  pcImgUrl;//pcurl
	private String  h5ImgUrl;//
	private Integer  sort;//排序
	private Integer  isShow;//是否显示
	private Date addtime;
	private String url; //晒单图片
	private Integer uid; //晒单人
	private String mobilePhone; //用户名
	private String describes; //TA说
	private String remark; //审核备注
	private Integer type;//是否已发放体验金
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
