package com.jsjf.model.claims;

public class DrClaimsProject{

    private Integer id;//审核项目ID  

    private Integer lid;//贷款项目基本信息ID
    
    private String name;//内容

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}