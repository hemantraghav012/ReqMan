package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.UserupdateVo;


@ManagedBean(name = "userupdate", eager = true)
@SessionScoped
public class Userupdate implements Serializable{

	
	private static final long serialVersionUID = 7586581387183790210L;

	private String userName;
	    private String emailid;
	    private String password;
		private String firstname;
		private String lastname;
		private String msg;
		private String shortname;
		private UserupdateVo userupdateVo = new UserupdateVo();
		private UserDetailsInterface userImpl = new UserDetailsImpl();
		
		
		public void modifyAction() throws IOException {
			
	        try
	        {
	        	HttpSession session = SessionUtils.getSession();
				 userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				setUserName(userName);
	           
	        	userupdateVo = userImpl.getUseremailid(userName);
	        	

	        	

	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        	
	        }
	        FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("myprofile.xhtml");
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



		public UserupdateVo getUserupdateVo() {
			return userupdateVo;
		}



		public void setUserupdateVo(UserupdateVo userupdateVo) {
			this.userupdateVo = userupdateVo;
		}



		public String getUserName() {
			return userName;
		}



		public void setUserName(String userName) {
			this.userName = userName;
		}
	
	
}
