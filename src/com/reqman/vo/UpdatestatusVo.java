package com.reqman.vo;

import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

public class UpdatestatusVo {
	
	private Integer newRequestId;
	private String stage;
	private String title;
	private String description;
	private String usercategory;
	private String userproject;
	private String userrequesttype;	
	private String completiondate;
	private String status;
	private String friendName;
	private byte[] file;
	private String fileName;
	private UploadedFile attachment;
	private int completionpercentage;
	private String createdby;
	private Date changedate;
	private List<requestNoteVo> noteList;
	private Date modifydate;
	private String actualeffort;
	private String estimatedeffort;
	private String priority;
	private Integer weightage;
	private Integer requeststage;
	
	
	
	public Integer getRequeststage() {
		return requeststage;
	}
	public void setRequeststage(Integer requeststage) {
		this.requeststage = requeststage;
	}
	public String getActualeffort() {
		return actualeffort;
	}
	public void setActualeffort(String actualeffort) {
		this.actualeffort = actualeffort;
	}
	public String getEstimatedeffort() {
		return estimatedeffort;
	}
	public void setEstimatedeffort(String estimatedeffort) {
		this.estimatedeffort = estimatedeffort;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Integer getWeightage() {
		return weightage;
	}
	public void setWeightage(Integer weightage) {
		this.weightage = weightage;
	}
	public Date getModifydate() {
		return modifydate;
	}
	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}
	public List<requestNoteVo> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<requestNoteVo> noteList) {
		this.noteList = noteList;
	}
	public Integer getNewRequestId() {
		return newRequestId;
	}
	public void setNewRequestId(Integer newRequestId) {
		this.newRequestId = newRequestId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
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
	public String getCompletiondate() {
		return completiondate;
	}
	public void setCompletiondate(String completiondate) {
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
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getChangedate() {
		return changedate;
	}
	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}
	
	

}
