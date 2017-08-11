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

import com.reqman.common.HibernateSessionFactory;

@ManagedBean
@SessionScoped
@Entity
@Table(name="type")
public class type implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="type",length = 80)
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	@Column(name="status",length = 45)
	private String status;
	@Column(name="email", length = 100)
	private String email;	
	
	public String submit(){	
		  Session session; 
	    String result=null;
	       SessionFactory hsf = HibernateSessionFactory.getSessionFactory();
    session = hsf.openSession();	
		try{
			//create a student object			
		this.setEmail(email);
        this.setType(type);
		this.setStatus("yes");	
			
			//start a transaction 
			session.beginTransaction();
			//save the student object			
	result=(String)	session.save(this);
			//commit transaction
			session.getTransaction().commit();
			System.out.println("done");
			if(result!=null){
				return "new_category";
			}else{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"AlreadyExist Type_name",
								"Please Enter a anotherType_name"));
				return "new_category";
			}			
		}	catch(Exception e){
			System.out.print(e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"AlreadyExist Type_name",
							"Please Enter a anotherType_name"));
			return "new_type";
		}
			
    }

	
	}
	
	


