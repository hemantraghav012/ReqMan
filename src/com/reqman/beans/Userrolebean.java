package com.reqman.beans;

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

import org.primefaces.event.CellEditEvent;

import com.reqman.dao.AccountInterface;
import com.reqman.dao.UserroleInterface;
import com.reqman.daoimpl.AccountImpl;
import com.reqman.daoimpl.UserroleImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AccountVo;
import com.reqman.vo.UserroleVo;


@ManagedBean(name="userrolebean",eager = true)
@ViewScoped
public class Userrolebean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8915459114992031617L;
	
	private UserroleInterface userroleInterface = new UserroleImpl();
	 private List<UserroleVo> userroleList = new ArrayList<UserroleVo>();	
	 private List<UserroleVo> filtereduserroleList = new ArrayList<UserroleVo>();
	 
	 
	 

		@PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--Userrole-->");
				 userroleList = new  ArrayList<UserroleVo>();			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
				 userroleList = userroleInterface.getuserroleDetails(userName);
				
				setFiltereduserroleList(userroleList);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

	 
		public void onCellEdit(CellEditEvent event) {
		     int result = 0;
		     String oldValue = "";
		     String newValue = "";
		     Integer updateUserroleid = 0;
			 try
			 {
				 oldValue = (String)event.getOldValue();
			     newValue = (String)event.getNewValue();
			     updateUserroleid = (Integer) event.getComponent().getAttributes().get("updateUserroleid");
		         System.out.println("updateUserroleid"+updateUserroleid);
		        if(newValue != null && !newValue.equals(oldValue)) 
		        {
		        	result = userroleInterface.updateUserrole(oldValue, newValue, updateUserroleid);
		            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
		            FacesContext.getCurrentInstance().addMessage(null, msg);
		        }
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
				 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error", e.getMessage().toString());
		            FacesContext.getCurrentInstance().addMessage(null, msg);
			 }
		       
		    }
		
		
	 
	 
	 
	 
	public List<UserroleVo> getUserroleList() {
		return userroleList;
	}
	public void setUserroleList(List<UserroleVo> userroleList) {
		this.userroleList = userroleList;
	}
	public List<UserroleVo> getFiltereduserroleList() {
		return filtereduserroleList;
	}
	public void setFiltereduserroleList(List<UserroleVo> filtereduserroleList) {
		this.filtereduserroleList = filtereduserroleList;
	}
	 
	 
	 
	 
	 
	 
	
	
}
