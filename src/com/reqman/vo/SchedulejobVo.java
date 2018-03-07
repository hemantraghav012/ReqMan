package com.reqman.vo;

public class SchedulejobVo {

	
	private Integer schedulejobId;
	private String jobname;
	private String description;
	private String day;
	private Integer hour;
	private Integer minute;
	private String status;
	private Integer date;
	
	
	
	
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getSchedulejobId() {
		return schedulejobId;
	}
	public void setSchedulejobId(Integer schedulejobId) {
		this.schedulejobId = schedulejobId;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	
	
	
}
