package com.reqman.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.util.sendEmail1;


@ManagedBean(name = "register", eager = true)
@SessionScoped
public class Register implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4003590909487243150L;

	private String emailid;
   private String password;
	private String firstname;
	private String lastname;
	private String msg;
	private String shortname;
	private String hashkey;
	private String emailstatus;
	
	public String submit()
	{
		System.out.println("--emailid--"+emailid);
		

		UserDetailsInterface userImpl = new UserDetailsImpl();
		int result = 0;
		try{
		
			result = userImpl.saveUser(emailid, password, firstname, lastname, shortname,hashkey);
			
			//boolean valid = LoginDAO.validate(user, pwd);
			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Exit",
								"User already created, please login with your credentials"));
				return "register";
			}
			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Inactive",
								"User already created, please activate the user credentials before login the sytem"));
				return "register";
			}
			if (result == 4) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Internal Error",
								"Please check the server logs"));
				return "register";
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Internal Error",
							"Please check the server logs"));
			return "register";
		}
	
		return "sendemailbutton";
	}
	
	
	
	
	
	public String linksubmit()
	{
		System.out.println("--emailid--"+emailid);
		

		UserDetailsInterface userImpl = new UserDetailsImpl();
		int result = 0;
		try{
		
			result = userImpl.savesocialUser(emailid, password, firstname, lastname, shortname,hashkey);
			
			//boolean valid = LoginDAO.validate(user, pwd);
			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Exit",
								"User already created, please login with your credentials"));
				return "registeraddlink";
			}
			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Inactive",
								"User already created, please activate the user credentials before login the sytem"));
				return "registeraddlink";
			}
			if (result == 4) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Internal Error",
								"Please check the server logs"));
				return "registeraddlink";
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Internal Error",
							"Please check the server logs"));
			return "registeraddlink";
		}
	
		return "sendemailbutton";
	}
	
	
	
	
	
	
	
	
	
	
	
	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
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


	public String getEmailstatus() {
		return emailstatus;
	}


	public void setEmailstatus(String emailstatus) {
		this.emailstatus = emailstatus;
	}
	
	
	
	}
	
	




