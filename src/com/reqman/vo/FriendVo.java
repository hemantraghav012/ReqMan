package com.reqman.vo;

import org.primefaces.model.StreamedContent;

public class FriendVo {

    
	private String firstname;
	private String lastname;
	private String status;
	private String  emailid;
	private String shortname;
	private Integer userFriendId;
	private String name;
	private  Integer friendid;
	
	
	
	
	
	
	
	
	
	
	public Integer getFriendid() {
		return friendid;
	}
	public void setFriendid(Integer friendid) {
		this.friendid = friendid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserFriendId() {
		return userFriendId;
	}
	public void setUserFriendId(Integer userFriendId) {
		this.userFriendId = userFriendId;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public Integer getUserfriendId() {
		return userfriendId;
	}
	public void setUserfriendId(Integer userfriendId) {
		this.userfriendId = userfriendId;
	}
	private Integer userfriendId;
	
	
	
}
