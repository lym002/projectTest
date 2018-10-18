package com.jsjf.model.system;

public class SysBanner {
    /**
     * 主键  
     */
    private Long id;

    /**
     * 图片路径  
     */
    private String imgUrl;

    /**
     * 广告说明  
     */
    private String remark;

    /**
     * 广告标题  
     */
    private String title;

    /**
     * 状�? 0-删除 1-有效  
     */
    private Integer status;

    /**
     * 广告的位�?  
     */
    private String code;

    /**
     * 排序字段 1-9 1为最后面 9为最前面  
     */
    private Integer sort;

    /**
     * 跳转地址  
     */
    private String location;

    /**
     * 颜色  
     */
    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}