package com.reqman.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.pojo.Users;
import com.reqman.util.SessionUtils;

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
	
	
	public String submit()
	{
		System.out.println("--emailid--"+emailid);
		

		UserDetailsInterface userImpl = new UserDetailsImpl();
		int result = 0;
		try{
			result = userImpl.saveUser(emailid, password, firstname, lastname, shortname);
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
	
		return "login";
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
	
	}
	
	




