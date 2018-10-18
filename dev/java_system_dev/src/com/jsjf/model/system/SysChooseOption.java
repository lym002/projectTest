package com.jsjf.model.system;

public class SysChooseOption {
    private Integer id;

    private String category;

    private String cnvalue;

    private Integer code;

    private Integer sortno;

    private Boolean valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getCnvalue() {
        return cnvalue;
    }

    public void setCnvalue(String cnvalue) {
        this.cnvalue = cnvalue == null ? null : cnvalue.trim();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}