package com.reqman.vo.zoho.subscription.hostpage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RootObject1 implements Serializable
{
  /**
	 * 
	 */
  private static final long serialVersionUID = -5989058553401272112L;
	
  private int code;

  private String message;

  private String hostedpage_id;

  private String status;

  private String url;

  private String action;

  private Date expiring_time;
  
  private Date created_time;
  
  private Data data;
  
  public int getCode() { return this.code; }

  public void setCode(int code) { this.code = code; }

  public String getMessage() { return this.message; }

  public void setMessage(String message) { this.message = message; }

  public String getHostedpageId() { return this.hostedpage_id; }

  public void setHostedpageId(String hostedpage_id) { this.hostedpage_id = hostedpage_id; }

  public String getStatus() { return this.status; }

  public void setStatus(String status) { this.status = status; }

  public String getUrl() { return this.url; }

  public void setUrl(String url) { this.url = url; }

  public String getAction() { return this.action; }

  public void setAction(String action) { this.action = action; }

  public Date getExpiringTime() { return this.expiring_time; }

  public void setExpiringTime(Date expiring_time) { this.expiring_time = expiring_time; }

  public Date getCreatedTime() { return this.created_time; }

  public void setCreatedTime(Date created_time) { this.created_time = created_time; }

  public Data getData() { return this.data; }

  public void setData(Data data) { this.data = data; }
}

