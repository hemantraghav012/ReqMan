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

import com.reqman.dao.CategoryMasterInterface;
import com.reqman.dao.RoleMasterInterface;
import com.reqman.daoimpl.CategoryMasterImpl;
import com.reqman.daoimpl.RoleMasterImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.RoleVo;

@ManagedBean(name="rolebean",eager = true)
@ViewScoped
public class Rolebean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2328692513345145007L;

	private List<RoleVo> roleList = new ArrayList<RoleVo>();	
	private RoleMasterInterface roleMasterInterface = new RoleMasterImpl();	
	private Integer id;
	private String name;
	
	
	
	@PostConstruct
    public void init() 
	{
		try
		{
			roleList = new ArrayList<RoleVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			roleList  = roleMasterInterface .getroleDetails(userName);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String saveRole()
	{
		int result = 0;
			try
		{
			roleList = new ArrayList<RoleVo>();
			
			result = roleMasterInterface.savecategory(id,name);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Category already exist",
								"Category already exist"));
				return "createroles";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Category already exist and in active, please activate by using modify category ",
								"Category already exist and in active, please activate by using modify category"));
				return "createroles";
			}
			if(result == 3)
			{
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				roleList  = roleMasterInterface .getroleDetails(userName);
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Category created  successfully.",
								"Category created  successfully."));
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),
							"Server Error "+e.getMessage()));
			return "createroles";
		}
		return "createroles";
	}
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public List<RoleVo> getRoleList() {
		return roleList;
	}


	public void setRoleList(List<RoleVo> roleList) {
		this.roleList = roleList;
	}

	
	
	
	
}
