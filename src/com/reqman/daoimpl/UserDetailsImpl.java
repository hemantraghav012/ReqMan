package com.reqman.daoimpl;

import java.util.Date;

import javax.management.relation.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.util.forgotpasswordemail;
import com.reqman.util.sendEmail1;
import com.reqman.util.setinfoEmail;
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
	
	public int saveUser(String emailid, String password, String firstname, String lastname, String shortname,String hashkey ,UploadedFile photo) throws Exception
	{
        Session session = null;
        SessionFactory hsf = null;
        Transaction tx = null;
        Users users = null;
        int result = 0;
        String emailArr[] = {};
        String account = "";
        Account accountDetails = new Account();
        Userroles userrolesDetails=null;
        Roles roles=null;
        roles= new Roles();
        userrolesDetails = new Userroles();
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
            	users.setHashkey(hashkey != null ? hashkey.trim() : "");
            	users.setPhoto(photo.getContents());
            	session.save(users);
            	
            	emailArr = emailid.split("@");
            	if(emailArr != null && emailArr.length >= 2)
            	{
                	accountDetails = (Account)session.createCriteria(Account.class)
            		.add(Restrictions.eq("name", emailArr[1].toLowerCase().trim()).ignoreCase())
            		.add(Restrictions.eq("status", true))
            		.uniqueResult();
                	
                	if(accountDetails == null)
                	{
                		accountDetails = new Account();
                		accountDetails.setName(emailArr[1].trim());
                		accountDetails.setStatus(true);
                		accountDetails.setCreatedby("SYSTEM");
                		accountDetails.setDatecreated(new Date());
                		session.save(accountDetails);
                	}

            	}
            	if(roles != null && userrolesDetails != null) {
            		roles=(Roles)session.createCriteria(Roles.class)
                    		.add(Restrictions.eq("id", 3))
                    		.uniqueResult();
            	
            		userrolesDetails = new Userroles();            		
            		userrolesDetails.setRoles(roles);
            		userrolesDetails.setUsers(users);
            		session.save(userrolesDetails);
            	}
            	
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

	
	
	
	private void SetRoles(int i) {
		// TODO Auto-generated method stub
		
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
			String shortname, String password,UploadedFile photo) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null; 
		    Transaction tx = null;
		    Users users = null;
		    int result = 0;    
		   
		    try {
		    	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
		        users = (Users)session.createCriteria(Users.class)
		        		.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase())
		        		.uniqueResult();
		        	
		        
	            if(users != null){
	            users.setFirstname(firstname);
	            users.setLastname(lastname);
	            users.setShortname(shortname);
	            users.setPassword(password);
	            
				users.setPhoto(photo.getContents());
			
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

	@Override
	public int updateinformationByHashkey(String hash, String emailid,
			String password, String firstname, String lastname, String shortname)
			throws Exception {
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
	            	users.setFirstname(firstname);
	            	users.setLastname(lastname);
	            	users.setShortname(shortname);	            	
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

	@Override
	public int savesocialUser(String emailid, String password,
			String firstname, String lastname, String shortname, String hashkey)
			throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	        Users users = null;
	        int result = 0;
	        String emailArr[] = {};
	        String account = "";
	        Account accountDetails = new Account();
	        Userroles userrolesDetails=null;
	        Roles roles=null;
	        roles= new Roles();
	        userrolesDetails = new Userroles();
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
	            	hashkey = new setinfoEmail().createAccount2(emailid, emailid);
	            	//hashkey = new sendEmail1().createAddinformation(emailid, emailid);
	     			System.out.println("-password--"+hashkey);
	            
	            	
	     			System.out.println("-password--"+password);
	            	users = new Users();
	            	users.setEmailid(emailid);
	            	
	            	users.setCreatedby("SYSTEM");
	            	users.setCreatedon(new Date());
	            	users.setStatus(true);
	            	users.setHashkey(hashkey != null ? hashkey.trim() : "");
	            	session.save(users);
	            	
	                  	emailArr = emailid.split("@");
	            	if(emailArr != null && emailArr.length >= 2)
	            	{
	                	accountDetails = (Account)session.createCriteria(Account.class)
	            		.add(Restrictions.eq("name", emailArr[1].toLowerCase().trim()).ignoreCase())
	            		.add(Restrictions.eq("status", true))
	            		.uniqueResult();
	                	
	                	if(accountDetails == null)
	                	{
	                		accountDetails = new Account();
	                		accountDetails.setName(emailArr[1].trim());
	                		accountDetails.setStatus(true);
	                		accountDetails.setCreatedby("SYSTEM");
	                		accountDetails.setDatecreated(new Date());
	                		session.save(accountDetails);
	                	}

	            	}
		if(roles != null && userrolesDetails != null) {
	                    
	            		
	            		
	            		roles=(Roles)session.createCriteria(Roles.class)
	                    		.add(Restrictions.eq("id", 3))
	                    		.uniqueResult();
	            	
	            		userrolesDetails = new Userroles();            		
	            		userrolesDetails.setRoles(roles);
	            		userrolesDetails.setUsers(users);
	            		session.save(userrolesDetails);
	            	}
	            	
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


	
	public byte[] getImageDetails(String userName)  throws Exception
	{

		// TODO Auto-generated method stub
	    Session session = null;
	    Transaction tx = null;
	    UserupdateVo userupdateVo = new UserupdateVo();
	    Users users = null;
	    byte[] imageBytes = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            users = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            if(users != null){
            	if(users.getPhoto() != null)
            	{
            		imageBytes = users.getPhoto();
            	}
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

		return imageBytes;
	
	
	}

	@Override
	public int forgotpassword(String emailid,String hashkey) throws Exception {
		// TODO Auto-generated method stub
		  Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	        Users users = null;
	        int result = 0;
	        try {
	            if(emailid != null && !emailid.trim().equals(""))
	            {
	             	session = HibernateUtil.getSession();
	                tx = session.beginTransaction();
	                users = (Users)session.createCriteria(Users.class)
	                		.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase())
	                		.uniqueResult();
	                
	                if(users != null){
	                	//hashkey = new sendEmail1().createAccount(emailid, emailid);
	                	hashkey=new forgotpasswordemail().createAccount(emailid, emailid);
	                //	hashkey = new setinfoEmail().createAccount2(emailid, emailid);
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

	@Override
	public int forgotpasswordwithemail(String hash, String emailid,
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
	            			.add(Restrictions.eq("emailid", emailid))	            			
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

	

	@Override
	public int saveUserthrowgoogle(String googleemail) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	        Users users = null;
	        int result = 0;
	        String emailArr[] = {};
	        String account = "";
	        Account accountDetails = new Account();
	        Userroles userrolesDetails=null;
	        Roles roles=null;
	        roles= new Roles();
	        userrolesDetails = new Userroles();
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", googleemail.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
                  if(users != null){
                	
                	tx.commit();
                	result = 1;
	            }
	           
	            else
	            {
	            	
	     			System.out.println("-googleemail--"+googleemail);
	            	users = new Users();
	            	users.setEmailid(googleemail);
	            	users.setCreatedby("SYSTEM");
	            	users.setCreatedon(new Date());
	            	users.setStatus(true);	            	
	            	session.save(users);
	            	
	            	emailArr = googleemail.split("@");
	            	if(emailArr != null && emailArr.length >= 2)
	            	{
	                	accountDetails = (Account)session.createCriteria(Account.class)
	            		.add(Restrictions.eq("name", emailArr[1].toLowerCase().trim()).ignoreCase())
	            		.add(Restrictions.eq("status", true))
	            		.uniqueResult();
	                	
	                	if(accountDetails == null)
	                	{
	                		accountDetails = new Account();
	                		accountDetails.setName(emailArr[1].trim());
	                		accountDetails.setStatus(true);
	                		accountDetails.setCreatedby("SYSTEM");
	                		accountDetails.setDatecreated(new Date());
	                		session.save(accountDetails);
	                	}

	            	}
	            	if(roles != null && userrolesDetails != null) {
	            		roles=(Roles)session.createCriteria(Roles.class)
	                    		.add(Restrictions.eq("id", 3))
	                    		.uniqueResult();
	            	
	            		userrolesDetails = new Userroles();            		
	            		userrolesDetails.setRoles(roles);
	            		userrolesDetails.setUsers(users);
	            		session.save(userrolesDetails);
	            	}
	            	
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

		


	

	
	}