package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.reqman.dao.AccountteammemberInterface;

import com.reqman.daoimpl.AccountteammembeImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AccountteammemberVo;



@ManagedBean(name="adminteammemberbean",eager = true)
@ViewScoped
public class AdminTeammemberbean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4226475521687598414L;
	
	private AccountteammemberInterface accountteammemberInterface = new AccountteammembeImpl();

	 private List<AccountteammemberVo> accountteammemberList = new ArrayList<AccountteammemberVo>();	
	 private List<AccountteammemberVo> filteredaccountteammemberList = new ArrayList<AccountteammemberVo>();
		private Integer id;
		
		
		
		
		 @PostConstruct
		    public void init() 
			{
				try
				{
					 	
					System.out.println("--AccountUser-->");
					accountteammemberList = new  ArrayList<AccountteammemberVo>();			
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				accountteammemberList = accountteammemberInterface .getaccountteammemberDetails(userName.toLowerCase().trim());
					
					setFilteredaccountteammemberList(accountteammemberList);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}


		public String modifyAction(){
			int result = 0;
			System.out.println("modify action" + id);
			// addMessage("Welcome to Primefaces!!");
			
			try {
				
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				
				result = accountteammemberInterface.getuseraccountById(id);
				
				if(result == 1)
				{
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"This Team Member is deleted in your list. ",
									""));
					//return "category";
				}
				if(result == 2)
				{
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Some thing is worng  ",
									"Please try again"));
				//	return "category";
				}
				if(result == 3)
				{
					
					
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"New Category added successfully.",""));
				}
				
				accountteammemberList = accountteammemberInterface .getaccountteammemberDetails(userName.toLowerCase().trim());
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "accountadminteammember";
			
		}
		
		
		
		
		
		public List<AccountteammemberVo> getAccountteammemberList() {
			return accountteammemberList;
		}
		public void setAccountteammemberList(List<AccountteammemberVo> accountteammemberList) {
			this.accountteammemberList = accountteammemberList;
		}
		public List<AccountteammemberVo> getFilteredaccountteammemberList() {
			return filteredaccountteammemberList;
		}
		public void setFilteredaccountteammemberList(List<AccountteammemberVo> filteredaccountteammemberList) {
			this.filteredaccountteammemberList = filteredaccountteammemberList;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
	

		
		
		
}
