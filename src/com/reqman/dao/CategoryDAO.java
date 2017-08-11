package com.reqman.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.reqman.common.HibernateSessionFactory;
import com.reqman.common.HibernateUtilH;
import com.reqman.pojo.Category;
import com.reqman.pojo.Project;

@ManagedBean(name="categorydao")
@RequestScoped
public class CategoryDAO {

	//retrieve from database
		public List<Category> getlist()
		{
			
			
		List<Category> list = new ArrayList<Category>();
			  Session session; 
			    String result=null;
			       SessionFactory hsf = HibernateUtilH.getSessionFactory();
		        session = hsf.openSession();
		        try{
		        	session.beginTransaction();
		        	list =session.createQuery("from Category").list();
		        	session.getTransaction().commit();
		            System.out.println("done");
		        	
		        	
		        	
		        }catch(Exception e){
		        	  System.out.println("error"+e);
		        }		
			
			return list;
			
		}
		public void delete(String category){
			 Session session; 
			  Transaction trns = null;
		       SessionFactory hsf = HibernateUtilH.getSessionFactory();
	        session = hsf.openSession();
	        try{     
	       trns= session.beginTransaction();    
	        	Category category1 = (Category) session.load(Category.class, new String (category));   
	        	 session.delete(category1);    
	        	 session.getTransaction().commit(); 
			
	        }catch(Exception e){
	        	  if (trns != null) {  
	        		  trns.rollback();  
	        		  } 
	        	
	        	  System.out.println("error"+e);
	        }	
	}
}
