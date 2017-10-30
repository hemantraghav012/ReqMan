package com.reqman.daoimpl;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;














import com.reqman.common.HibernateUtil;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Users;
import com.reqman.util.sendEmail1;
import com.reqman.util.sendEmailonfriend;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.UserupdateVo;

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
	
	public int saveUser(String emailid, String password, String firstname, String lastname, String shortname,String hashkey) throws Exception
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
            	
            	hashkey = new sendEmail1().createAccount(emailid, firstname);
     			System.out.println("-password--"+hashkey);
            
            	
     			System.out.println("-password--"+password);
            	users = new Users();
            	users.setEmailid(emailid);
            	users.setFirstname(firstname != null ? firstname.trim() : "");
            	users.setLastname(lastname != null ? lastname.trim() : "");
            	users.setPassword(password);
            	users.setShortname(shortname != null ? shortname : "");
            	users.setCreatedby("SYSTEM");
            	users.setCreatedon(new Date());
            	users.setStatus(true);
            	users.setHashkey(hashkey);
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

	
	
	
	@Override
	public UserupdateVo getUseremailid(String userName) throws Exception {
		// TODO Auto-generated method stub
	    Session session = null;
	    Transaction tx = null;
	    UserupdateVo userupdateVo = new UserupdateVo();
	    Users users = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            users = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            if(users != null){
            userupdateVo.setEmailid(users.getEmailid());
            userupdateVo.setFirstname(users.getFirstname());
            userupdateVo.setLastname(users.getLastname());
            userupdateVo.setShortname(users.getShortname());
            userupdateVo.setPassword(users.getPassword());
    			tx.commit();
            }
 		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(tx != null){
				tx.rollback();
			}
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return userupdateVo;
	
	}
	
	

	
	@Override
	public int updateUsers(String emailid, String firstname, String lastname,
			String shortname, String password) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null; 
		    Transaction tx = null;
		    Users users = null;
		    int result = 0;    
		   
		    try {
		    	session = HibernateUtil.getSession();
		    
		        users = (Users)session.createCriteria(Users.class)
		        		.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase())
		        		.uniqueResult();
		        	
		        
	            if(users != null){
	            users.setFirstname(firstname);
	            users.setLastname(lastname);
	            users.setShortname(shortname);
	            users.setPassword(password);
	            
	            	session.update(users);
	    			tx.commit();
	    			result = 1;
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

	@Override
	public int updatepasswordByHashkey(String hash, String emailid,
			String password) throws Exception {
		// TODO Auto-generated method stub
		
		 Session session = null;
		    Transaction tx = null;
		    Users users = null;
		    int result = 0;
			try
			{
	           	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            users = (Users)
	            			session.createCriteria(Users.class)
	            			.add(Restrictions.eq("hashkey", hash))
	            			.uniqueResult();
	            
	            if(users != null){
	            	users.setPassword(password);
	            	
	            	session.update(users);
	    			tx.commit();
	    			result = 1;
	            }
	 		}
			catch(Exception e)
			{
				e.printStackTrace();
				if(tx != null){
					tx.rollback();
				}
				result = 2;
			}
			finally 
			{
	        	if(session != null)
	            session.close();
		    }

			return result;
		
		
		}


	
	

	

	

	

	
	}