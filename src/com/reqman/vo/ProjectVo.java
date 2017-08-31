package com.reqman.vo;

public class ProjectVo {

	
private Integer srNo;
private String name;

private String status;
private Integer userProjectId;


	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	public Integer getUserProjectId() {
		return userProjectId;
	}

	public void setUserProjectId(Integer userProjectId) {
		this.userProjectId = userProjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
