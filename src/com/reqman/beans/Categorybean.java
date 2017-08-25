package com.reqman.beans;



import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import com.reqman.common.HibernateUtil;
import com.reqman.dao.CategoryMasterInterface;
import com.reqman.daoimpl.CategoryMasterImpl;
import com.reqman.pojo.Category;



@ManagedBean(name="categorybean",eager = true)
public class Categorybean implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3076255353187837257L;


	private String name;
	 private List<Category> category;	
	
	
	public List<Category> getCategory() {
		return category;
	}
	public void setCategory(List<Category> category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	 
	    
	}
