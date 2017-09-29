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
	 
	public Suggestion(){
		
	}
	
	public Suggestion(int id) {
		this.id = id;
	}

	public Suggestion(int id, String address, Boolean status, Date datecreated,
			String createdby) {
		super();
		this.id = id;
		this.message = message;
		this.status = status;
		this.datecreated = datecreated;
		this.createdby = createdby;
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

	
	

}
