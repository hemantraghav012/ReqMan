package com.reqman.beans;

import java.io.Serializable;





import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.pojo.Users;


@ManagedBean(name="update", eager = true)
@SessionScoped
@RequestScoped
public class Update  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4113095533935368886L;

	private String firstname;
	private String lastname;
	private String shortname;
	private String emailid;
	private String msg;
	
	
	
	
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	
	
	
	
	
	public String submit()
	{
		
		

		UserDetailsInterface userImpl = new UserDetailsImpl();
		int result = 0;

    	System.out.println("done1"+emailid);
		try{
			result = userImpl.updateUsers(emailid, firstname, lastname, shortname);
		
			/*
			 * 
			 in complete code
			 * 
			 * */
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}

    	
		return null;
		
	
	}
	}