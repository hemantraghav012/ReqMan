package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.SetpasswordVo;
import com.reqman.vo.UserupdateVo;



@ManagedBean(name = "setpassword", eager = true)
@SessionScoped
@RequestScoped
public class Setpasswordbean implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 2716986337360959786L;
	
	 @ManagedProperty(value = "#{param.emailid}")
private String emailid;
	 @ManagedProperty(value = "#{param.hash}")
private String hash;
private String password;
private String firstname;
private String lastname;	
private String shortname;
private String hashkey;
private UserDetailsInterface userImpl = new UserDetailsImpl();


public String setandupdate(){
	int result = 0;
	try{
		System.out.println("--emailid--"+emailid);
		System.out.println("--hashkey-"+hash);
		
    	result = userImpl.updatepasswordByHashkey(hash, emailid,password);
    	
    	if(result == 2)
    	{
    		FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the Category",
							"Problem while modifying the Category"));
			return "eregisters.xhtml";
    	}
    	
    	
    	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Problem while modifying the Category",
						"Problem while modifying the Category"));
		return "eregisters.xhtml";
	}
	return "login";
}


public String setinformationupdate(){
	int result = 0;
	try{
		System.out.println("--emailid--"+emailid);
		System.out.println("--hashkey-"+hash);
		
    	result = userImpl.updateinformationByHashkey(hash, emailid,password,firstname,lastname,shortname);
    	
    	if(result == 2)
    	{
    		FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the Category",
							"Problem while modifying the Category"));
			return "setinformation.xhtml";
    	}
    	
    	
    	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Problem while modifying the Category",
						"Problem while modifying the Category"));
		return "setinformation.xhtml";
	}
	return "login";
}


public String forgotpassword()
{
	System.out.println("--emailid--"+emailid);
	

	UserDetailsInterface userImpl = new UserDetailsImpl();
	int result = 0;
	try{
	
		result = userImpl.forgotpassword(emailid,hashkey);
		
		if (result == 1) {
			return "sendemailbutton";
		} 
		
		
		
		
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect email id",
							"Please enter correct email id"));
			return "forgotpassword";
			
		}
	}
	catch(Exception e){
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Incorrect emailid",
						"Please enter correct emailid"));
		
		return "forgotpassword";
		
	}
}



public String forgotpasswordupdate(){
	int result = 0;
	try{
		System.out.println("--emailid--"+emailid);
		System.out.println("--hashkey-"+hash);
		
    	result = userImpl.forgotpasswordwithemail(hash, emailid,password);
    	
    	if(result == 2)
    	{
    		FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the Category",
							"Problem while modifying the Category"));
			return "forgotpasswordemail.xhtml";
    	}
    	
    	
    	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Problem while modifying the Category",
						"Problem while modifying the Category"));
		return "eregisters.xhtml";
	}
	return "login";
}






public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public String getHash() {
	return hash;
}
public void setHash(String hash) {
	this.hash = hash;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
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


public String getShortname() {
	return shortname;
}


public void setShortname(String shortname) {
	this.shortname = shortname;
}


public String getHashkey() {
	return hashkey;
}


public void setHashkey(String hashkey) {
	this.hashkey = hashkey;
}

	
	
}
