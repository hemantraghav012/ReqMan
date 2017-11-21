package com.reqman.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.util.SessionUtils;

@ManagedBean(name = "price", eager = true)
@SessionScoped
@RequestScoped
public class Price implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private String pwd;
	private String msg;
	private String user;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	//validate login
	public String validateUsernamePassword() {
		UserDetailsInterface userImpl = new UserDetailsImpl();
		int result = 0;
		
		try{
			result = userImpl.validate(user, pwd);
			//boolean valid = LoginDAO.validate(user, pwd);
			if (result == 1) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", user);
				//FacesContext context=FacesContext.getCurrentInstance();
				//Update update=context.getApplication().evaluateExpressionGet(context, "#{update}", Update.class);
				//update.setEmailid(this.user);
				return "price";
			} 
			
			else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "price";
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			
			return "price";
			
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "price";
	}
}
