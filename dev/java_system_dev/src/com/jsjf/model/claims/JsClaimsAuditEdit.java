package com.jsjf.model.claims;

import java.util.Date;

/**
 * 债权使用后编辑,审核成功前信息暂时存放表,及修审核成功后历史修改记录备份
 * @author DELL
 *
 */
public class JsClaimsAuditEdit {
		
		private Integer id;                  // '票据ID',
		private Integer lid;                  // '贷款项目基本信息ID',
		private Integer type;                 // '数据类型 0:暂存资料,1:修改后的历史资料备份',
		private Integer status;                 // 0:待审核,1:审核通过,2:驳回,
		private Date addTime;					//添加时间
		private Date updateTime;				//修改时间
		private Integer updateUserKy;			//修改人
		private Integer addUserKy;				//添加人
		private String cache;
		
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
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Date getAddTime() {
			return addTime;
		}
		public void setAddTime(Date addTime) {
			this.addTime = addTime;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public Integer getUpdateUserKy() {
			return updateUserKy;
		}
		public void setUpdateUserKy(Integer updateUserKy) {
			this.updateUserKy = updateUserKy;
		}
		public Integer getAddUserKy() {
			return addUserKy;
		}
		public void setAddUserKy(Integer addUserKy) {
			this.addUserKy = addUserKy;
		}
		public String getCache() {
			return cache;
		}
		public void setCache(String cache) {
			this.cache = cache;
		}
	
}
