package com.reqman.vo;

import java.util.Date;

import org.primefaces.model.UploadedFile;

public class AccountVo {

	private Integer id;
	private  String name;
	private String status;
	private Date  datecreated;
	private String ceatedby;
	private String imagename;
	 
	
	
	
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public String getCeatedby() {
		return ceatedby;
	}
	public void setCeatedby(String ceatedby) {
		this.ceatedby = ceatedby;
	}
	
	
	
}
