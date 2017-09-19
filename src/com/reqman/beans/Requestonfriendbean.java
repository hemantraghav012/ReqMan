package com.reqman.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import com.reqman.dao.NewrequestInterface;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.util.SessionUtils;


@ManagedBean(name="requestonfriendbean",eager = true)
@RequestScoped
public class Requestonfriendbean implements Serializable {

	private static final long serialVersionUID = 539423210497243436L;
	
	
 private String title;
 private String description;
 private String  usercategory;
 private String userproject;
 private String  userrequesttype;
 private String  userfriendlist;
 private UploadedFile attachment;
 
 public String submit()
	{
	 
	
		int result = 0;
		try{
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			
		
			//boolean valid = LoginDAO.validate(user, pwd);
			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Exit",
								"User already created, please login with your credentials"));
				return "newrequestfriend";
			}
			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Inactive",
								"User already created, please activate the user credentials before login the sytem"));
				return "newrequestfriend";
			}
			if (result == 4) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Internal Error",
								"Please check the server logs"));
				return "newrequestfriend";
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Internal Error",
							"Please check the server logs"));
			return "newrequestfriend";
		}
	
	 
	 
	 
	 
	 return "request";
	}
 
 
 
 

	public void addMessage(String summary) {
     FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
     FacesContext.getCurrentInstance().addMessage(null, message);
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
public String getUserfriendlist() {
	return userfriendlist;
}
public void setUserfriendlist(String userfriendlist) {
	this.userfriendlist = userfriendlist;
}
public UploadedFile getAttachment() {
	return attachment;
}
public void setAttachment(UploadedFile attachment) {
	this.attachment = attachment;
}
	
	
	
	
}
