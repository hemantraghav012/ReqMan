package com.reqman.pojo;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.reqman.common.HibernateSessionFactory;



@ManagedBean
@SessionScoped
@Entity
@Table(name="Request_Project")
public class Project implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="project",length = 80)
	private String project;
	@Column(name="status",length = 45)
	private String status;
	@Column(name="email", length = 100)
	private String email;	
	
	
	
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String submit(){	
		  Session session; 
	    
	       SessionFactory hsf = HibernateSessionFactory.getSessionFactory();
        session = hsf.openSession();	
		try{
			//create a student object			
		this.setEmail(email);
		this.setProject(project);
		this.setStatus(status);	
			
			//start a transaction 
			session.beginTransaction();
			//save the student object			
			session.save(this);
			//commit transaction
			session.getTransaction().commit();
			System.out.println("done");
			
		}	catch(Exception e){
			System.out.print(e);
		}
		

	return "new_type.xhtml";  
		
        }
	}
	
	


