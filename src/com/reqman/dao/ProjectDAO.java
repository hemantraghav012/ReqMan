package com.reqman.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.reqman.common.HibernateSessionFactory;
import com.reqman.common.HibernateUtil;
import com.reqman.common.HibernateUtilH;
import com.reqman.pojo.Project;



@ManagedBean(name="projectdao")
@RequestScoped

public class ProjectDAO {

	//retrieve from database
		public List<Project> getlist()
		{
			
			
		List<Project> list = new ArrayList<Project>();
			  Session session; 
			
			       SessionFactory hsf = HibernateUtilH.getSessionFactory();
		        session = hsf.openSession();
		        try{
		        	session.beginTransaction();
		        	list =session.createQuery("from Project").list();
		        	session.getTransaction().commit();
		            System.out.println("done");		        			        	
		        }catch(Exception e){
		        	  System.out.println("error"+e);
		        }		
			
			return list;
			
		}
		
		public void delete(String project){
			 Session session; 
			  Transaction trns = null;
		       SessionFactory hsf = HibernateUtilH.getSessionFactory();
	        session = hsf.openSession();
	        try{     
	       trns= session.beginTransaction();    
	        	Project project1 = (Project) session.load(Project.class, new String (project));   
	        	 session.delete(project1);    
	        	 session.getTransaction().commit(); 
			
	        }catch(Exception e){
	        	  if (trns != null) {  
	        		  trns.rollback();  
	        		  } 
	        	
	        	  System.out.println("error"+e);
	        }	
			
		}
	}
