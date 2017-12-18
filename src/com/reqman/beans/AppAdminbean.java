package com.reqman.beans;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.reqman.dao.AppAdminInterface;
import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.AppAdminImpl;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AdminVo;
import com.reqman.vo.NewrequestVo;


@ManagedBean(name="appadminbean",eager = true)
@RequestScoped
@ViewScoped
public class AppAdminbean implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 5564329292861771411L;
	private AppAdminInterface appadminInterface = new AppAdminImpl();
	 private List<AdminVo> userList = new ArrayList<AdminVo>();	
	 private List<AdminVo> filteredUserList = new ArrayList<AdminVo>();
	
	
	
	
	@PostConstruct
    public void init() 
	{
		try
		{
			 	
			System.out.println("--create request-->");
			userList = new  ArrayList<AdminVo>();			
		HttpSession session = SessionUtils.getSession();
		String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			userList = appadminInterface.getappadminDetails(userName);
			
			setFilteredUserList(userList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}




	public List<AdminVo> getUserList() {
		return userList;
	}




	public void setUserList(List<AdminVo> userList) {
		this.userList = userList;
	}




	public List<AdminVo> getFilteredUserList() {
		return filteredUserList;
	}




	public void setFilteredUserList(List<AdminVo> filteredUserList) {
		this.filteredUserList = filteredUserList;
	}




	
	
	
	

}
