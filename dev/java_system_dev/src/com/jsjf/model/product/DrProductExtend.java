package com.jsjf.model.product;

import java.util.Date;

public class DrProductExtend{

    private Integer id;//产品ID
    
    private Integer pid;//产品ID

    private String title;//标题
    
    private String content;//内容
    
    private Date addDate;//添加时间
    
    private Integer addUser;//添加人
    
    private Date updDate;//修改时间
    
    private Integer updUser;//修改人

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Integer getUpdUser() {
		return updUser;
	}

	public void setUpdUser(Integer updUser) {
		this.updUser = updUser;
	}
}