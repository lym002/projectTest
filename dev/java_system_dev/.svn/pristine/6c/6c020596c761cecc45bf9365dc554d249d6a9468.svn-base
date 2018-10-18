package com.jsjf.model.article;

import java.io.IOException;
import java.util.Date;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;

public class SysArticle {
	
    /**
     * 主键id 自增  
     */
    private Integer artiId;

    /**
     * 所属主栏目id 
     */
    private Integer proId;
    /**
     * 内容概述  
     */
    private String summaryContents;
    
    /**
     * 排序等级(0-9等级越高优先级越低) 
     */
    private Short sortRank;

    /**
     * 是否头条（0否1是）
     */
    private Short ishead;
    private String isHeadName;

    /**
     * 点击次数  
     */
    private Integer clickCount;

    /**
     * 文章标题  
     */
    private String title;

    /**
     * 简略标题
     */
    private String shortTitle;

    /**
     * 文章作者
     */
    private String writer;

    /**
     * 文章来源  
     */
    private String source;

    /**
     * 缩略图
     */
    private String litpic;

    /**
     * 是否活动  
     */
    private Short active;

    /**
     * 关键字mate  
     */
    private String keywords;

    /**
     * 文章描述mate  
     */
    private String description;

    /**
     * 备注  
     */
    private String remark;

    /**
     * 创建时间  
     */
    private Date createTime;

    /**
     * 0资讯,1文章  
     */
    private Short type;

    /**
     * 创建人
     */
    private Short createUser;

    /**
     * 更改人 
     */
    private Short updateUser;

    /**
     * 更新时间  
     */
    private Date updateTime;

    /**
     * 单个文章的baidu 关键字
     */
    private String metakyword;

    /**
     * 单个文t章itle上面显示的 
     */
    private String metatitle;

    /**
     * 0代表审核中 1代表已审核 2已拒绝 
     */
    private Integer status;
    private String statusName;

    /**
     * 用户Id 后台发布则为0  
     */
    private Integer uid;

    /**
     * 1为推�?0为不推荐  
     */
    private Integer isrecommend;

    /**
     * 文章内容  
     */
    private String content;
    
    private String createName;
    private String updateName;
    private Date startDate;
	private Date endDate;   //查询条件  结束时间
	private String proName;
	private String bankName;
	private Integer openDayId;
    public Integer getArtiId() {
        return artiId;
    }

    public void setArtiId(Integer artiId) {
        this.artiId = artiId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }


	public String getSummaryContents() {
		return summaryContents;
	}

	public void setSummaryContents(String summaryContents) {
		this.summaryContents = summaryContents;
	}

	public Short getSortRank() {
        return sortRank;
    }

    public void setSortRank(Short sortRank) {
        this.sortRank = sortRank;
    }

    public Short getIshead() {
        return ishead;
    }

    public void setIshead(Short ishead) {
        this.ishead = ishead;
    }

    public String getIsHeadName() {
		try {
			if(ishead != null){
				isHeadName = ConfigUtil.dictionaryMap.get(
						PropertyUtil.getProperties("whether")).get(ishead.intValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isHeadName;
	}

	public void setIsHeadName(String isHeadName) {
		this.isHeadName = isHeadName;
	}

	public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Short createUser) {
        this.createUser = createUser;
    }

    public Short getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Short updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMetakyword() {
        return metakyword;
    }

    public void setMetakyword(String metakyword) {
        this.metakyword = metakyword;
    }

    public String getMetatitle() {
        return metatitle;
    }

    public void setMetatitle(String metatitle) {
        this.metatitle = metatitle;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		try {
			if(status != null){
				statusName = ConfigUtil.dictionaryMap.get(
						PropertyUtil.getProperties("articlestatus")).get(status);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getIsrecommend() {
        return isrecommend;
    }

    public void setIsrecommend(Integer isrecommend) {
        this.isrecommend = isrecommend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getOpenDayId() {
		return openDayId;
	}

	public void setOpenDayId(Integer openDayId) {
		this.openDayId = openDayId;
	}
	
	
}