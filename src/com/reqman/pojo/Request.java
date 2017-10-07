package com.reqman.pojo;

// Generated 6 Oct, 2017 6:32:56 PM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Request generated by hbm2java
 */
@Entity
@Table(name = "request")
public class Request implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7850920915635663322L;
	private int id;
	private Usercategory usercategory;
	private Userfriendlist userfriendlist;
	private Userproject userproject;
	private Userrequesttype userrequesttype;
	private String title;
	private String description;
	private Date completiondate;
	private byte[] attachment;
	private Integer requeststatus;
	private Boolean status;
	private Date datecreated;
	private String createdby;
	private Date datemodified;
	private String modifiedby;
	private String filename;
	private Float completionpercentage;
	private Date acceptdate;
	private Date updatedate;
	private Integer revisionnumber;
	private String approvedby;
	private Date approveddate;

	public Request() {
	}

	public Request(int id) {
		this.id = id;
	}

	public Request(int id, Usercategory usercategory,
			Userfriendlist userfriendlist, Userproject userproject,
			Userrequesttype userrequesttype, String title, String description,
			Date completiondate, byte[] attachment, Integer requeststatus,
			Boolean status, Date datecreated, String createdby,
			Date datemodified, String modifiedby, String filename,
			Float completionpercentage, Date acceptdate, Date updatedate,
			Integer revisionnumber, String approvedby, Date approveddate) {
		this.id = id;
		this.usercategory = usercategory;
		this.userfriendlist = userfriendlist;
		this.userproject = userproject;
		this.userrequesttype = userrequesttype;
		this.title = title;
		this.description = description;
		this.completiondate = completiondate;
		this.attachment = attachment;
		this.requeststatus = requeststatus;
		this.status = status;
		this.datecreated = datecreated;
		this.createdby = createdby;
		this.datemodified = datemodified;
		this.modifiedby = modifiedby;
		this.filename = filename;
		this.completionpercentage = completionpercentage;
		this.acceptdate = acceptdate;
		this.updatedate = updatedate;
		this.revisionnumber = revisionnumber;
		this.approvedby = approvedby;
		this.approveddate = approveddate;
	}

	@Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usercategoryid")
	public Usercategory getUsercategory() {
		return this.usercategory;
	}

	public void setUsercategory(Usercategory usercategory) {
		this.usercategory = usercategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friendid")
	public Userfriendlist getUserfriendlist() {
		return this.userfriendlist;
	}

	public void setUserfriendlist(Userfriendlist userfriendlist) {
		this.userfriendlist = userfriendlist;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userprojectid")
	public Userproject getUserproject() {
		return this.userproject;
	}

	public void setUserproject(Userproject userproject) {
		this.userproject = userproject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userrequesttypeid")
	public Userrequesttype getUserrequesttype() {
		return this.userrequesttype;
	}

	public void setUserrequesttype(Userrequesttype userrequesttype) {
		this.userrequesttype = userrequesttype;
	}

	@Column(name = "title", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completiondate", length = 29)
	public Date getCompletiondate() {
		return this.completiondate;
	}

	public void setCompletiondate(Date completiondate) {
		this.completiondate = completiondate;
	}

	@Column(name = "attachment")
	public byte[] getAttachment() {
		return this.attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	@Column(name = "requeststatus")
	public Integer getRequeststatus() {
		return this.requeststatus;
	}

	public void setRequeststatus(Integer requeststatus) {
		this.requeststatus = requeststatus;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datecreated", length = 29)
	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	@Column(name = "createdby", length = 50)
	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datemodified", length = 29)
	public Date getDatemodified() {
		return this.datemodified;
	}

	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}

	@Column(name = "modifiedby", length = 50)
	public String getModifiedby() {
		return this.modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	@Column(name = "filename", length = 50)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "completionpercentage", precision = 8, scale = 8)
	public Float getCompletionpercentage() {
		return this.completionpercentage;
	}

	public void setCompletionpercentage(Float completionpercentage) {
		this.completionpercentage = completionpercentage;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "acceptdate", length = 29)
	public Date getAcceptdate() {
		return this.acceptdate;
	}

	public void setAcceptdate(Date acceptdate) {
		this.acceptdate = acceptdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedate", length = 29)
	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Column(name = "revisionnumber")
	public Integer getRevisionnumber() {
		return this.revisionnumber;
	}

	public void setRevisionnumber(Integer revisionnumber) {
		this.revisionnumber = revisionnumber;
	}

	@Column(name = "approvedby", length = 50)
	public String getApprovedby() {
		return this.approvedby;
	}

	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approveddate", length = 29)
	public Date getApproveddate() {
		return this.approveddate;
	}

	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}

}
