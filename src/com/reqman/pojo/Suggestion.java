package com.reqman.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "suggestion")


public class Suggestion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6169956352892726614L;

	private int id;
	private String message;
	private Boolean status;
	private Date datecreated;
	private String createdby;
	private String messagetype;
	private Integer requeststatus;
	private String adminremarks;
	private String actionowner;
	 
	public Suggestion(){
		
	}
	
	public Suggestion(int id) {
		this.id = id;
	}

	
	public Suggestion(int id, String message, Boolean status, Date datecreated, String createdby, String messagetype,
			Integer requeststatus, String adminremarks, String actionowner) {
		super();
		this.id = id;
		this.message = message;
		this.status = status;
		this.datecreated = datecreated;
		this.createdby = createdby;
		this.messagetype = messagetype;
		this.requeststatus = requeststatus;
		this.adminremarks = adminremarks;
		this.actionowner = actionowner;
	}

	@Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@Column(name = "message",  length = 500)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datecreated", length = 29)
	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	@Column(name = "createdby", length = 50)
	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	
	@Column(name = "messagetype", length = 100)
	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	
	
	
	@Column(name = "adminremarks",  length = 100)
	public String getAdminremarks() {
		return adminremarks;
	}

	public void setAdminremarks(String adminremarks) {
		this.adminremarks = adminremarks;
	}

	@Column(name = "actionowner",  length = 100)
	public String getActionowner() {
		return actionowner;
	}

	public void setActionowner(String actionowner) {
		this.actionowner = actionowner;
	}
	@Column(name = "requeststatus",  length = 100)
	public Integer getRequeststatus() {
		return requeststatus;
	}

	public void setRequeststatus(Integer requeststatus) {
		this.requeststatus = requeststatus;
	}

	
	
	

}
