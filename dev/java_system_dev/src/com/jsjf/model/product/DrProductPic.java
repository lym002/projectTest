package com.jsjf.model.product;

import org.springframework.web.multipart.MultipartFile;

public class DrProductPic {
	private Integer id;//产品图片ID  

    private Integer pid;//产品ID

    private String smallUrl;//缩略图地址

    private String bigUrl;//原图地址

    private String name;//图片名称
    
    private Integer status;//状态
    
    private Integer isShow;//是否显示
    
    private Integer showSort;//显示顺序
    
    private MultipartFile productFiles;
    private Integer type;//图片类型
    
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public MultipartFile getProductFiles() {
		return productFiles;
	}

	public void setProductFiles(MultipartFile productFiles) {
		this.productFiles = productFiles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public String getBigUrl() {
		return bigUrl;
	}

	public void setBigUrl(String bigUrl) {
		this.bigUrl = bigUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getShowSort() {
		return showSort;
	}

	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}
}
