package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

import com.reqman.dao.HomeInterface;
import com.reqman.daoimpl.HomeImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.HomeVo;

@ManagedBean(name="homebean",eager = true)
@ViewScoped
public class Homebean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1800747467349449126L;
	
	private List<HomeVo> homeList = new ArrayList<HomeVo>();	
	private HomeInterface homeInterface = new HomeImpl();
	
	
	@PostConstruct
	   public void init() 
		{
			try
			{
				homeList = new ArrayList<HomeVo>();
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				homeList = homeInterface.getrequestDetailsinhomepage(userName.toLowerCase().trim());
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	
	
	
	
	
	
	
	public List<HomeVo> getHomeList() {
		return homeList;
	}
	public void setHomeList(List<HomeVo> homeList) {
		this.homeList = homeList;
	}	
	

 
	
	
	
	
	
	

	
}
