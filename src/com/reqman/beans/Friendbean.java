package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.daoimpl.FriendMasterImpl;
import com.reqman.pojo.Users;
import com.reqman.util.SessionUtils;
import com.reqman.vo.EmailUtility;



@ManagedBean(name="friendbean",eager = true)
public class Friendbean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5614461673505707743L;
	private String emailid;
	private String password;
	private String firstname;
	private String lastname;
	private String msg;
	private String shortname;
	private  List<Users> usersList ;
	private String message="You are invited for Reqman Project. Please go to this url www.reqman.com .";
	
	
	
	 public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	private String host="smtp.gmail.com";
		private String port="587";
		private String user1="hemantraghav012@gmail.com";
		private String pass="s93139879";
	
	
	
	public List<Users> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	private FriendMasterInterface friendMasterInterface = new FriendMasterImpl();	


	
       
        
        public List < Users > getUsers() throws Exception  
        {   
        	 
            usersList = friendMasterInterface.AllUsers(); 
            
            return usersList;  
        }  
        
        
	
	
	
	
	public String submit()
	{
		System.out.println("--emailid--"+emailid);
		

		FriendMasterInterface friendMasterInterface = new FriendMasterImpl();
		int result = 0;
		
		try{
			EmailUtility.sendEmail(host, port, user1, pass, emailid, firstname, message);
			result = friendMasterInterface.saveFriend(emailid, password, firstname, lastname, shortname);				
				
			//	EmailUtility.sendEmail(host, port, user1, pass, emailid, subject, message);			
			
			
			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Friend Exit",
								"Friend already created, please login with your credentials"));
				return "addfriend";
			}
			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Friend Inactive",
								"Friend already created, please activate the user credentials before login the sytem"));
				return "addfriend";
			}
			if (result == 4) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Internal Error",
								"Please check the server logs"));
				return "addfriend";
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Internal Error",
							"Please check the server logs"));
			return "addfriend";
		}
	
		return "friend";
	}
	
}
