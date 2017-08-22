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
	
	
	public String submit()
	{
	System.out.println("--category--"+name);

	CategoryMasterInterface categoryImpl =  new CategoryMasterImpl();
	int result = 0;
		try{
		result = categoryImpl.savecategory(name);
		
		if (result == 1) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Category Name Exit",
					"Category Name already created"));
				return "category";
			}
			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Category  Inactive",
								"Category Name  already created"));
				return "category";
			}
			if (result == 4) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Internal Error",
								"Please check the server logs"));
				return "category";
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Internal Error",
							"Please check the server logs"));
			return "category";
		}
	
		return "newrequestfriend";
	}


		 
	@PostConstruct
    public void init(){
        //init code
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        category = session.createCriteria(Category.class).list();
        
        session.close();
    }
    
	 
	    
	}
