package com.reqman.vo;

import java.math.BigInteger;

public class DatasummaryVo {

	private String request_created;
	private String request_accepted;	
	private String request_completed;	
	private String request_modified;	
	private String request_closed;
	private BigInteger  users;
	private BigInteger  subscription_created;
	
	
	
	
	
	public BigInteger getUsers() {
		return users;
	}
	public void setUsers(BigInteger users) {
		this.users = users;
	}
	public BigInteger getSubscription_created() {
		return subscription_created;
	}
	public void setSubscription_created(BigInteger subscription_created) {
		this.subscription_created = subscription_created;
	}
	public String getRequest_created() {
		return request_created;
	}
	public void setRequest_created(String request_created) {
		this.request_created = request_created;
	}
	public String getRequest_accepted() {
		return request_accepted;
	}
	public void setRequest_accepted(String request_accepted) {
		this.request_accepted = request_accepted;
	}
	public String getRequest_completed() {
		return request_completed;
	}
	public void setRequest_completed(String request_completed) {
		this.request_completed = request_completed;
	}
	public String getRequest_modified() {
		return request_modified;
	}
	public void setRequest_modified(String request_modified) {
		this.request_modified = request_modified;
	}
	public String getRequest_closed() {
		return request_closed;
	}
	public void setRequest_closed(String request_closed) {
		this.request_closed = request_closed;
	}
	
	
}
