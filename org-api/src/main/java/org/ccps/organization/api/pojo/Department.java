package org.ccps.organization.api.pojo;

import org.springframework.hateoas.ResourceSupport;

public class Department {
	/* deptcode in varchar2,

	    parentdeptcode in varchar2,
	   deptname in varchar2,
	   detpshortname in varchar2,

	   depttype   in number,
	   deptorgsublevel in number,
	   statuscode  in number --1,2*/
	private String deptCode;
	private String parentDeptCode;
	private String deptName;
	private String deptShortName;
	private int orgSubLevel;
	private int statusCode;
	
	private int deptType;

	

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

	

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	public int getOrgSubLevel() {
		return orgSubLevel;
	}

	public void setOrgSubLevel(int orgSubLevel) {
		this.orgSubLevel = orgSubLevel;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getDeptType() {
		return deptType;
	}

	public void setDeptType(int deptType) {
		this.deptType = deptType;
	}
	
	
}
