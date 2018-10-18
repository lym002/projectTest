package com.jsjf.model.system;

public class SysArea {
    /**
     * 主键ID  
     */
    private Integer id;

    /**
     * 区域名称  
     */
    private String name;

    /**
     * 父ID  
     */
    private Integer reid;

    /**
     * 排序  
     */
    private Integer disorder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReid() {
        return reid;
    }

    public void setReid(Integer reid) {
        this.reid = reid;
    }

    public Integer getDisorder() {
        return disorder;
    }

    public void setDisorder(Integer disorder) {
        this.disorder = disorder;
    }
}