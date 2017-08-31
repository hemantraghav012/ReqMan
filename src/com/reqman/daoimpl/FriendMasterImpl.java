package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;

public class FriendMasterImpl implements FriendMasterInterface {

	@Override
	public int saveFriend(String emailid, String password, String firstname,
			String lastname, String shortname) throws Exception {
		// TODO Auto-generated method stub
		
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
	
	

	 @SuppressWarnings("unchecked")
	public List <Users> AllUsers()  
	    {  
		 List <Users> userslist=null;  
		 
	        Session session = HibernateUtil.getSession();
	        try  
	        {  
	            session.beginTransaction();  
	            userslist = session.createCriteria(Users.class).list(); 	             
	         
	            session.getTransaction().commit();  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	            session.getTransaction().rollback();  
	        }  
	        session.close();  
	        return userslist;  
	    }  
	
}
