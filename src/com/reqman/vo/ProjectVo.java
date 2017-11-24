package com.reqman.vo;

public class ProjectVo {

	

private String name;

private String status;
private Integer userProjectId;
private String projectaccess;

	

	public String getProjectaccess() {
	return projectaccess;
}

public void setProjectaccess(String projectaccess) {
	this.projectaccess = projectaccess;
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
