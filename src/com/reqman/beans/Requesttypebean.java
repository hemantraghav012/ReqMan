package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.reqman.dao.CategoryMasterInterface;
import com.reqman.dao.requesttypeMasterInterface;
import com.reqman.daoimpl.CategoryMasterImpl;
import com.reqman.daoimpl.RequesttypeMasterImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.RequesttypeVo;

@ManagedBean(name="requesttypebean",eager = true)
public class Requesttypebean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 428748461879170255L;
	

private  List<RequesttypeVo> requesttypeList = new ArrayList<RequesttypeVo>();
	
	private requesttypeMasterInterface requesttypeMasterInterface = new RequesttypeMasterImpl();
	
	
	
	private String requesttypeName;
	
	private Boolean status;
	
	private String requesttypeId;
	
	
	@PostConstruct
    public void init() {
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String requesttypePage()
	{
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
		requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "requesttype";
	}
	
	
	public String newRequesttype()
	{
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "createrequesttype";
	}
	
	
	public String saveRequesttype()
	{
		int result = 0;
		UserSession usersession = new UserSession();
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
			System.out.println("--requesttypeName-->"+requesttypeName);
			System.out.println("--status-->"+status);
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			result = requesttypeMasterInterface.saverequesttype(requesttypeName, status, userName);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"requesttype already exist",
								"requesttype already exist"));
				return "createrequesttype";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"requesttype already exist and in active, please activate by using modify category ",
								"requesttypeList already exist and in active, please activate by using modify category"));
				return "createrequesttype";
			}
			if(result == 3)
			{
				
				requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"requesttype created  successfully.",
								"requesttype created  successfully."));
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),
							"Server Error "+e.getMessage()));
			return "createrequesttype";
		}
		return "requesttype";
	}
	
	
	public void modifyAction(String requesttypeIdOne) {
		
		RequesttypeVo requesttypeVo = new RequesttypeVo();
        
        try{
        	System.out.println("modify action"+requesttypeIdOne);
            //addMessage("Welcome to Primefaces!!");
        	setRequesttypeId(requesttypeIdOne);
        	requesttypeVo = requesttypeMasterInterface.getUserRequesttypeById(requesttypeIdOne);
        	if(requesttypeVo != null && requesttypeVo.getStatus().trim().equalsIgnoreCase("Active")){
        		setRequesttypeName(requesttypeVo.getName() != null ? requesttypeVo.getName() : "");
        		setStatus(true);
        	}
        	else
        	{
        		setRequesttypeName(requesttypeVo.getName() != null ? requesttypeVo.getName() : "");
        		setStatus(false);
        	}
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifyrequesttype.xhtml");

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
	public String updateRequesttype(){
		int result = 0;
		try{
			System.out.println("--updateRequesttype-status-"+status);
			System.out.println("--updateRequesttypeCategory-requesttypeId-"+requesttypeId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			
        	result = requesttypeMasterInterface.updateUserRequesttypeById(requesttypeId, status);
        	
        	if(result == 2){
        		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the requesttype",
								"Problem while modifying the requesttype"));
				return "modifyrequesttype.xhtml";
        	}
        	
        	if(result == 1){
        		requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
        	}
        	
        	
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the requesttype",
							"Problem while modifying the requesttype"));
			return "modifyrequesttype.xhtml";
		}
		return "requesttype";
	}

	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public List<RequesttypeVo> getRequesttypeList() {
		return requesttypeList;
	}

	public void setRequesttypeList(List<RequesttypeVo> requesttypeList) {
		this.requesttypeList = requesttypeList;
	}

	public String getRequesttypeName() {
		return requesttypeName;
	}

	public void setRequesttypeName(String requesttypeName) {
		this.requesttypeName = requesttypeName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getRequesttypeId() {
		return requesttypeId;
	}

	public void setRequesttypeId(String requesttypeId) {
		this.requesttypeId = requesttypeId;
	}
	
	
}
