package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import com.reqman.dao.UpdatestatusInterface;
import com.reqman.daoimpl.UpdatestatusImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.UpdatestatusVo;

@ManagedBean(name="commonproject",eager = true)
@RequestScoped
@ViewScoped
public class commonprojectbean implements Serializable {

	
	private static final long serialVersionUID = -3078473494895999044L;
	
	private List<UpdatestatusVo> commonproject = new ArrayList<UpdatestatusVo>();	
	 private UpdatestatusInterface updatestatusInterface = new UpdatestatusImpl();	
	 private UpdatestatusVo updatestatusVo = new UpdatestatusVo();
	 private List<UpdatestatusVo> filteredcommonprojectList = new ArrayList<UpdatestatusVo>();
	 
	 
	 
	 
	 @PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--create request-->");
				commonproject = new ArrayList<UpdatestatusVo>();
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);				
				commonproject = updatestatusInterface.getallProject(userName);
				setFilteredcommonprojectList(commonproject);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	public List<UpdatestatusVo> getCommonproject() {
		return commonproject;
	}
	public void setCommonproject(List<UpdatestatusVo> commonproject) {
		this.commonproject = commonproject;
	}
	public UpdatestatusVo getUpdatestatusVo() {
		return updatestatusVo;
	}
	public void setUpdatestatusVo(UpdatestatusVo updatestatusVo) {
		this.updatestatusVo = updatestatusVo;
	}
	public List<UpdatestatusVo> getFilteredcommonprojectList() {
		return filteredcommonprojectList;
	}
	public void setFilteredcommonprojectList(
			List<UpdatestatusVo> filteredcommonprojectList) {
		this.filteredcommonprojectList = filteredcommonprojectList;
	}
	
	
	 
	 
	 
	 
	 
	 
	
	
}
