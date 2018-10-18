package com.jsjf.model.activity;

import java.util.Date;

/**
 * 投即送晒单表
 *
 */
public class JsProductPrizeWish {
	
	private Integer id;//
	private Integer uid;// uid
	private String url;// 心愿url
	private String remark;// 备注
	private Date addtime;// 添加时间
	
	public JsProductPrizeWish() {
		super();
	}

	public JsProductPrizeWish(Integer uid, String url, String remark) {
		super();
		this.uid = uid;
		this.url = url;
		this.remark = remark;
	}
    public JsProductPrizeWish(Integer uid, String remark) {
        this.uid = uid;
        this.remark = remark;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
}
