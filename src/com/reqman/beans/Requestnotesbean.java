package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.NewrequestVo;
@ManagedBean(name="requestnotesbean",eager = true)
@ViewScoped
@RequestScoped
public class Requestnotesbean implements Serializable {
	 /**
	 * 
	 */
	/*
	private static final long serialVersionUID = 5047237928136055515L;
	private String requestId;	
	private NewrequestVo newrequestVo = new NewrequestVo();
	 private NewrequestInterface newrequestInterface = new NewrequestImpl();
	 private String title;
	 private String description;

	 public void writeAction() {
	
		
	try
    {
    	System.out.println("modify action"+requestId);
        //addMessage("Welcome to Primefaces!!");
    	setRequestId(requestId);
    	newrequestVo = newrequestInterface.getRequestById(requestId);      	
		
    	if(newrequestVo != null && newrequestVo.getStatus().trim().equalsIgnoreCase("Active")){
    		setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle(): "");
    		setDescription(newrequestVo.getDescription() != null ? newrequestVo.getDescription() : "");
    		//Dateconverter.convertDateToStringDDMMDDYYYY(completiondate);
  
    		
    	}
    	else
    	{
    		setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle(): "");
    		setDescription(newrequestVo.getDescription() != null ? newrequestVo.getDescription() : "");
    		
    		
    	}
    	
    	FacesContext.getCurrentInstance()
        .getExternalContext().dispatch("modifyrequest.xhtml");

    }
    catch(Exception e){
    	e.printStackTrace();
    }
    
}
	

public String getRequestId() {
	return requestId;
}


public void setRequestId(String requestId) {
	this.requestId = requestId;
}


public NewrequestVo getNewrequestVo() {
	return newrequestVo;
}


public void setNewrequestVo(NewrequestVo newrequestVo) {
	this.newrequestVo = newrequestVo;
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


*/
	



}
