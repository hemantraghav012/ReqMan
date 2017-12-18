package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.reqman.dao.AccountInterface;
import com.reqman.daoimpl.AccountImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AccountVo;
import com.reqman.vo.AdminVo;




@ManagedBean(name="accountbean",eager = true)
@RequestScoped
@ViewScoped
public class Accountbean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6211763112586779685L;
	private AccountInterface accountInterface = new AccountImpl();
	 private List<AccountVo> accountList = new ArrayList<AccountVo>();	
	 private List<AccountVo> filteredaccountList = new ArrayList<AccountVo>();
	 
	 
	 
	 

		@PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--Account-->");
				accountList = new  ArrayList<AccountVo>();			
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				accountList = accountInterface.getaccountDetails(userName);
				
				setFilteredaccountList(accountList);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

	 
	 
	 
	 
	public List<AccountVo> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<AccountVo> accountList) {
		this.accountList = accountList;
	}
	public List<AccountVo> getFilteredaccountList() {
		return filteredaccountList;
	}
	public void setFilteredaccountList(List<AccountVo> filteredaccountList) {
		this.filteredaccountList = filteredaccountList;
	}
	 
	


	 
}
