package com.reqman.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.reqman.dao.NewRequestqueryInterface;
import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.query.NewRequestquery;
import com.reqman.util.Dateconverter;
import com.reqman.util.SessionUtils;
import com.reqman.vo.DatasummaryVo;
import com.reqman.vo.NewrequestVo;


@ManagedBean(name="datasummarybean",eager = true)
@ViewScoped
public class Datasummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5238592187667785323L;
	
private String request_created;
private String request_accepted;	
private String request_completed;	
private String request_modified;	
private String request_closed;
private BigInteger total_users;
private Date startDate;
private Date endDate;

private DatasummaryVo datasummaryVo = new DatasummaryVo();
private List<DatasummaryVo> requestList = new ArrayList<DatasummaryVo>();
private NewrequestInterface newrequestInterface = new NewrequestImpl();



@PostConstruct
public void init() {
	try {

		requestList = new ArrayList<DatasummaryVo>();			
		HttpSession session = SessionUtils.getSession();
		String userName = (String) session.getAttribute("username");
		System.out.println("--usersession--userName-->" + userName);
		System.out.println(startDate);
		System.out.println(userName);
		System.out.println(endDate);
		
		if (startDate == null) {
			startDate = Dateconverter.getPreToPreMonthDate(new Date());
		}
		if (endDate == null) {
			endDate = new Date();
		}
		requestList = newrequestInterface.getdatasummary(userName,startDate,endDate);
		
	} catch (Exception e) {
		e.printStackTrace();
	}

}

public String daterange() {
	int result = 0;	
	try {
		requestList = new ArrayList<DatasummaryVo>();
		System.out.println("--create new request-->");
		HttpSession session = SessionUtils.getSession();
		String userName = (String) session.getAttribute("username");		
		requestList = newrequestInterface.getdatasummary(userName,startDate,endDate);
		System.out.println(startDate);
		System.out.println(userName);
		System.out.println(endDate);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "appadminsummary";
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






public Date getStartDate() {
	return startDate;
}






public void setStartDate(Date startDate) {
	this.startDate = startDate;
}






public Date getEndDate() {
	return endDate;
}






public void setEndDate(Date endDate) {
	this.endDate = endDate;
}






public DatasummaryVo getDatasummaryVo() {
	return datasummaryVo;
}






public void setDatasummaryVo(DatasummaryVo datasummaryVo) {
	this.datasummaryVo = datasummaryVo;
}






public List<DatasummaryVo> getRequestList() {
	return requestList;
}






public void setRequestList(List<DatasummaryVo> requestList) {
	this.requestList = requestList;
}

public BigInteger getTotal_users() {
	return total_users;
}

public void setTotal_users(BigInteger total_users) {
	this.total_users = total_users;
}
















}
