package com.reqman.daoimpl;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.beans.Update;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.pojo.Users;

public class UserDetailsImpl implements UserDetailsInterface {

	public int validate(String userName, String password) throws Exception
    {
        Session session = null;
        SessionFactory hsf = null;
        Transaction tx = null;
        Users users = null;
        int result = 0;
        try {
            if(userName != null && !userName.trim().equals(""))
            {
            	
            	//hsf = HibernateSessionFactory.getSessionFactory();
                //session = hsf.openSession();
            	session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                users = (Users)session.createCriteria(Users.class)
                		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
                		.add(Restrictions.eq("password", password.toLowerCase().trim()).ignoreCase())
                		.uniqueResult();
                
                if(users != null){
                	
                	tx.commit();
                	result = 1;
                }
                else
                {
                	result = 2;
                }
		        
            }
            
        } catch (Exception e) {
        	if(tx != null)
            tx.rollback();
            e.printStackTrace();
            result = 3;
            throw new Exception(e);
        } finally {
        	if(session != null)
            session.close();
        }
		
		return result;
    	
    }
	
	public int saveUser(String emailid, String password, String firstname, String lastname, String shortname) throws Exception
	{
        Session session = null;
        SessionFactory hsf = null;
        Transaction tx = null;
        Users users = null;
        int result = 0;
        try {
        	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            users = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(users != null && users.getStatus() != null && users.getStatus().booleanValue() == true){
            	result = 1;
            }
            else if(users != null && users.getStatus() != null && users.getStatus().booleanValue() == false){
            	result = 2;
            }
            else
            {
            	users = new Users();
            	users.setEmailid(emailid);
            	users.setFirstname(firstname != null ? firstname.trim() : "");
            	users.setLastname(lastname != null ? lastname.trim() : "");
            	users.setPassword(password);
            	users.setShortname(shortname != null ? shortname : "");
            	users.setCreatedby("SYSTEM");
            	users.setCreatedon(new Date());
            	users.setStatus(true);
            	session.save(users);
            	tx.commit();
            	result = 3;
            }
        } catch (Exception e) {
        	if(tx != null)
            tx.rollback();
            e.printStackTrace();
            result = 4;
            throw new Exception(e);
        } finally {
        	if(session != null)
            session.close();
        }
		
		return result;
 	}


	public int updateUsers(String emailid, String firstname, String lastname, String shortname) throws Exception{
	 Session session = null; 
    Transaction tx = null;
    Users users = null;
    int result = 0;
    
    
   
    try {
    	session = HibernateUtil.getSession();
    
        users = (Users)session.createCriteria(Users.class,emailid)
        		.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase())
        		.uniqueResult();
        
       
  
        	
        	
        	session.update(users);
        	tx.commit();
        	result = 3;
        
        
    } catch (Exception e) {
    	if(tx != null)
        tx.rollback();
        e.printStackTrace();
        result = 3;
        throw new Exception(e);
    } finally {
    	if(session != null)
        session.close();
    }
	return result;
	
	
	
}
	}