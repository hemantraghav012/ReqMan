package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.reqman.dao.AccountuserInterface;
import com.reqman.dao.UserroleInterface;
import com.reqman.daoimpl.AccountuserImpl;
import com.reqman.daoimpl.UserroleImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AccountVo;
import com.reqman.vo.AccountuserVo;
import com.reqman.vo.UserroleVo;

@ManagedBean(name="accountuserbean",eager = true)
@RequestScoped
@ViewScoped
public class Accountuserbean implements Serializable {

	
	private static final long serialVersionUID = 7263007677507872195L;
	
	private AccountuserInterface accountuserInterface = new AccountuserImpl();
	 private List<AccountuserVo> accountuserList = new ArrayList<AccountuserVo>();	
	 private List<AccountuserVo> filteredaccountuserList = new ArrayList<AccountuserVo>();
	 private AccountuserVo accountuserVo = new AccountuserVo();
	 private Boolean imagestatus;
	private Integer id;
	 
	 
	 @PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--AccountUser-->");
				accountuserList = new  ArrayList<AccountuserVo>();			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			accountuserList = accountuserInterface .getaccountuserDetails(userName.toLowerCase().trim());
				
				setFilteredaccountuserList(accountuserList);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}


	
	 
	 
	 
	 
	 
	 
	 
	 
	 


	public List<AccountuserVo> getAccountuserList() {
		return accountuserList;
	}




	public void setAccountuserList(List<AccountuserVo> accountuserList) {
		this.accountuserList = accountuserList;
	}




	public List<AccountuserVo> getFilteredaccountuserList() {
		return filteredaccountuserList;
	}




	public void setFilteredaccountuserList(
			List<AccountuserVo> filteredaccountuserList) {
		this.filteredaccountuserList = filteredaccountuserList;
	}




	public Boolean getImagestatus() {
		return imagestatus;
	}




	public void setImagestatus(Boolean imagestatus) {
		this.imagestatus = imagestatus;
	}


	public AccountuserVo getAccountuserVo() {
		return accountuserVo;
	}


	public void setAccountuserVo(AccountuserVo accountuserVo) {
		this.accountuserVo = accountuserVo;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	 
	 
	 
	 

}
