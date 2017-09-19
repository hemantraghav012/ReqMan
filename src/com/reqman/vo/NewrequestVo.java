package com.reqman.vo;

import java.util.Date;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;

public class NewrequestVo {
	
	private Integer newRequestId;	
	private String title;
	 private String description;
	 private Usercategory  usercategory;
	 private Userproject userproject;
	 private Userrequesttype userrequesttype;
	// private Integer  userfriendlist;
	private StreamedContent attachment;
	 public StreamedContent getAttachment() {
		return attachment;
	}
	public void setAttachment(StreamedContent attachment) {
		this.attachment = attachment;
	}
	//private UploadedFile attachment;
	 private Date completiondate;
	 private String status;
	 
	 
	 
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getNewRequestId() {
		return newRequestId;
	}
	public void setNewRequestId(Integer newRequestId) {
		this.newRequestId = newRequestId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Usercategory getUsercategory() {
		return usercategory;
	}
	public void setUsercategory(Usercategory usercategory) {
		this.usercategory = usercategory;
	}
	
	public Userproject getUserproject() {
		return userproject;
	}
	public void setUserproject(Userproject userproject) {
		this.userproject = userproject;
	}
	public Userrequesttype getUserrequesttype() {
		return userrequesttype;
	}
	public void setUserrequesttype(Userrequesttype userrequesttype) {
		this.userrequesttype = userrequesttype;
	}
	public Date getCompletiondate() {
		return completiondate;
	}
	public void setCompletiondate(Date completiondate) {
		this.completiondate = completiondate;
	}
	
	
	
}
