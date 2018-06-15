package com.reqman.vo;

import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

public class AdminRequestVo {
	private int newRequestId;
	private String title;
	private String description;
	private String usercategory;
	private String userproject;
	private String userrequesttype;
	private Date completiondate;
	private String status;
	private String friendName;
	private byte[] file;
	private String fileName;
	private UploadedFile attachment;
	private int completionpercentage;
	private String stage;
	private String changedate;
	private String createddate;
	private List<requestNoteVo> noteList;
	private Integer project;
	private Integer category;
	private Integer requesttype;
	private Integer userfriend;
	private Integer expectedcompletion;
	private Integer attachmentstatus;
	
	
	
	
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public int getNewRequestId() {
		return newRequestId;
	}
	public void setNewRequestId(int newRequestId) {
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
	public Date getCompletiondate() {
		return completiondate;
	}
	public void setCompletiondate(Date completiondate) {
		this.completiondate = completiondate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public UploadedFile getAttachment() {
		return attachment;
	}
	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}
	public int getCompletionpercentage() {
		return completionpercentage;
	}
	public void setCompletionpercentage(int completionpercentage) {
		this.completionpercentage = completionpercentage;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}
	public List<requestNoteVo> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<requestNoteVo> noteList) {
		this.noteList = noteList;
	}
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Integer getRequesttype() {
		return requesttype;
	}
	public void setRequesttype(Integer requesttype) {
		this.requesttype = requesttype;
	}
	public Integer getUserfriend() {
		return userfriend;
	}
	public void setUserfriend(Integer userfriend) {
		this.userfriend = userfriend;
	}
	public Integer getExpectedcompletion() {
		return expectedcompletion;
	}
	public void setExpectedcompletion(Integer expectedcompletion) {
		this.expectedcompletion = expectedcompletion;
	}
	public Integer getAttachmentstatus() {
		return attachmentstatus;
	}
	public void setAttachmentstatus(Integer attachmentstatus) {
		this.attachmentstatus = attachmentstatus;
	}
	
	

}
