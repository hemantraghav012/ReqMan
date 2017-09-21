package com.reqman.vo;

import java.io.Serializable;

import org.primefaces.model.StreamedContent;

public class NewrequestVo implements Serializable
 {

	private static final long serialVersionUID = 102031607437292830L;
	private Integer newRequestId;
	private String title;
	private String description;
	private String usercategory;
	private String userproject;
	private String userrequesttype;
	private StreamedContent attachment;
	private String completiondate;
	private String status;
	private String friendName;
	

	public StreamedContent getAttachment() {
		return attachment;
	}

	public void setAttachment(StreamedContent attachment) {
		this.attachment = attachment;
	}

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

	public String getUsercategory() {
		return usercategory;
	}

	public void setUsercategory(String usercategory) {
		this.usercategory = usercategory;
	}

	public String getUserproject() {
		return userproject;
	}

	public void setUserproject(String userproject) {
		this.userproject = userproject;
	}

	public String getUserrequesttype() {
		return userrequesttype;
	}

	public void setUserrequesttype(String userrequesttype) {
		this.userrequesttype = userrequesttype;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getCompletiondate() {
		return completiondate;
	}

	public void setCompletiondate(String completiondate) {
		this.completiondate = completiondate;
	}

}
