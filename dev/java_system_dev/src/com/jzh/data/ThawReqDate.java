package com.jzh.data;

import net.sf.json.JSONObject;

public class ThawReqDate extends BaseReqdata {

	private JSONObject[] acnts_amt;
	private String project_no;
	private String rem;
	public JSONObject[] getAcnts_amt() {
		return acnts_amt;
	}
	public void setAcnts_amt(JSONObject[] acnts_amt) {
		this.acnts_amt = acnts_amt;
	}
	public String getProject_no() {
		return project_no;
	}
	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	
	
}
