package com.reqman.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.sendEmail1;


@ManagedBean(name = "register", eager = true)
@SessionScoped
@RequestScoped
public class Register implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4003590909487243150L;
	private UploadedFile photo;
	private String emailid;
    private String password;
	private String firstname;
	private String lastname;
	private String msg;
	private String shortname;
	private String hashkey;
	private String emailstatus;
	
	 @ManagedProperty(value = "#{param.email}")
	private String googleemail;
	private	UserDetailsInterface userImpl = new UserDetailsImpl();
	
	public String submit()
	{
		
		//is declared as type UserDetailsInterface(interface) and the object it references is of type UserDetailsImpl(class).
		UserDetailsInterface userImpl = new UserDetailsImpl();
		
		int result = 0;
		try{
		//Pass all value through registerbean class to userImpl Interface and call saveUser() method
			result = userImpl.saveUser(emailid, password, firstname, lastname, shortname,hashkey,photo);
			
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
	
	
	
	
	public String googlesubmit()
	{
		System.out.println("--emailid--"+emailid);
		
		UserDetailsInterface userImpl = new UserDetailsImpl();
	
		int result = 0;
		try{
			System.out.println("--emailid--"+googleemail);
			result = userImpl.saveUserthrowgoogle(googleemail);
			System.out.println("--emailid--"+googleemail);
			//boolean valid = LoginDAO.validate(user, pwd);
			
				
				if (result == 1) {
					HttpSession session = SessionUtils.getSession();
					session.setAttribute("username", googleemail);
					session.setAttribute("userroleset", result);
					//For App admin 
					return "home";
				} 
				
				else if (result == 2) {
					HttpSession session = SessionUtils.getSession();
					session.setAttribute("username",googleemail);
					session.setAttribute("userroleset", result);
					//For Account Admin 
					return "home";
				} 
				
				else if (result ==3) {
					HttpSession session = SessionUtils.getSession();
					session.setAttribute("username",googleemail);
					session.setAttribute("userroleset", result);
					//for Requestor
					return "home";
				} 
				
				else if (result == 4) {
					HttpSession session = SessionUtils.getSession();
					session.setAttribute("username",googleemail);
					session.setAttribute("userroleset", result);
					//For Team member
					return "home";
				} 
			
			
		else if (result == 5) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Inactive",
								"User already created, please activate the user credentials before login the sytem"));
				return "googlesignin";
			}
		else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Internal Error",
								"Please check the server logs"));
				return "googlesignin";
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Internal Error",
							"Please check the server logs"));
			return "googlesignin";
		}
	
		
		
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

	public UploadedFile getPhoto() {
		return photo;
	}

	public void setPhoto(UploadedFile photo) {
		this.photo = photo;
	}



	public String getGoogleemail() {
		return googleemail;
	}



	public void setGoogleemail(String googleemail) {
		this.googleemail = googleemail;
	}


	
	
	}
	