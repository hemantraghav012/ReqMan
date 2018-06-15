package com.reqman.vo;

public class AccountuserVo {
	private Integer accountuserid;
	private String username;
	private String accountname;
	private Boolean imageStatus;
	private String organizationkey;
	
	
	
	public String getOrganizationkey() {
		return organizationkey;
	}
	public void setOrganizationkey(String organizationkey) {
		this.organizationkey = organizationkey;
	}
	public Integer getAccountuserid() {
		return accountuserid;
	}
	public void setAccountuserid(Integer accountuserid) {
		this.accountuserid = accountuserid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public Boolean getImageStatus() {
		return imageStatus;
	}
	public void setImageStatus(Boolean imageStatus) {
		this.imageStatus = imageStatus;
	}
	
	
	
	
}
