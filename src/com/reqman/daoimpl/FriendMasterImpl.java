package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.common.HibernateUtilH;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.pojo.Project;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.FriendVo;
import com.reqman.vo.ProjectVo;

public class FriendMasterImpl implements FriendMasterInterface {

	

	@Override
	public int savefriend(String firstname, String lastname, String emailid,String shortname) throws Exception {
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
	            
	            if(users != null &&   users.getStatus() != null &&   users.getStatus().booleanValue() == true){
	            	result = 1;
	            }
	            else if(  users != null &&   users.getStatus() != null &&   users.getStatus().booleanValue() == false){
	            	result = 2;
	            }
	            else
	            {
	            	  users=new Users();
	            	  users.setFirstname(firstname);
	            	  users.setLastname(lastname);
	            	  users.setEmailid(emailid);
	            	  users.setShortname(shortname);	            	              
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
	

	
	@Override
	public int savefriend(String frienduser, Boolean status,String friendfirstname, String friendlastname,
			String friendshortname, String userName) throws Exception {
		
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	        Users users = null;
	        int result = 0;
	     
	        Userfriendlist userfriendlist = null;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	       
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", frienduser.toLowerCase().trim()).ignoreCase())
	            	.uniqueResult();
	            
	            
	          
	            if(users != null)
	            {
	            	Userfriendlist userfriendExist = null;
	            	if(users.getUserfriendlistsForFriendid() != null && users.getUserfriendlistsForFriendid().size() != 0){
	            		for(Userfriendlist userfriendDB : users.getUserfriendlistsForFriendid())
	            		{
	            			if(userfriendDB != null && userfriendDB.getUsersByUserid() != null && userfriendDB.getUsersByUserid().getEmailid() != null){
	            				if(userfriendDB.getUsersByUserid().getEmailid().trim().equalsIgnoreCase(userName))
	            				{
	            					userfriendExist = userfriendDB;
	            					break;
	            				}
	            			}
	            		}
	            	}
	            	
	            	if(userfriendExist != null && userfriendExist.getStatus().equals(true))
	            	{
	            		result = 1;
	            	}
	            	else if(userfriendExist != null && userfriendExist.getStatus().equals(false))
	            	{
	            		result = 2;
	            	}
	            	else
	            	{
	            		
	            		userfriendlist = new Userfriendlist();
	            		userfriendlist.setUsersByFriendid(users);
	            users = (Users)session.createCriteria(Users.class)
	     	            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
	     	            		.uniqueResult();
	            		userfriendlist.setUsersByUserid(users);         		
	            		userfriendlist.setStatus(true);
	            		userfriendlist.setCreatedby(userName);
	            		userfriendlist.setDatecreated(new Date());
	                	session.save(userfriendlist);
	                	
	                	result = 3;
	                	tx.commit();
	            	}
	            }
	            else
	            {
	            	users = new Users();
	            	users.setEmailid(frienduser.trim());
	            	users.setFirstname(friendfirstname);
	            	users.setLastname(friendlastname);
	            	users.setShortname(friendshortname);
	            	users.setStatus(true);
	            	users.setCreatedby(userName);
	            	users.setCreatedon(new Date());
	            	
	            	
	            	session.save(users);
	            	
	            	userfriendlist = new Userfriendlist();
	            	
	            	userfriendlist.setUsersByFriendid(users);	
	            	
	            	   users = (Users)session.createCriteria(Users.class)
	     	            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
	     	            		.uniqueResult();
	            	userfriendlist.setUsersByUserid(users);
	            	    
	            	userfriendlist.setStatus(true);
	            	userfriendlist.setCreatedby(userName);
            		userfriendlist.setDatecreated(new Date());
	            	
	            	session.save(userfriendlist);
	            	
	            	result = 3;
	            	tx.commit();
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
	public List<FriendVo> getUsersDetails(String userName) {
		// TODO Auto-generated method stub
		List<FriendVo> friendList = new ArrayList<FriendVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	 FriendVo friendVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUserfriendlistsForUserid() != null && usersTemp.getUserfriendlistsForUserid().size() != 0)
            	{
            		for(Userfriendlist userfriendDB : usersTemp.getUserfriendlistsForUserid())
            		{
            			if(userfriendDB != null && userfriendDB.getUsersByFriendid() != null 
            					&& userfriendDB.getUsersByFriendid() .getStatus() == true)
            			{
            				friendVo = new FriendVo();
            			
            				friendVo.setFirstname(userfriendDB.getUsersByFriendid() .getFirstname());
            				friendVo.setLastname(userfriendDB.getUsersByFriendid().getLastname());
            				friendVo.setEmailid(userfriendDB.getUsersByFriendid().getEmailid());
            				friendVo.setShortname(userfriendDB.getUsersByFriendid().getShortname());
            				friendVo.setUserFriendId(userfriendDB.getId());
                			if(userfriendDB.getStatus().equals(true))
                			{
                				friendVo.setStatus("Active");
                			}
                			else
                			{
                				friendVo.setStatus("InActive");
                			}
                			counter++;
                			friendList.add(friendVo);
            			}
            		}
            	}
            	tx.commit();
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return friendList;
	}


	public FriendVo getUserFriendById(String friendId) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
	    Transaction tx = null;
	  FriendVo friendVo = new FriendVo();
	    Userfriendlist userfriendlist = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userfriendlist = (Userfriendlist)
            			session.createCriteria(Userfriendlist.class)
            			.add(Restrictions.eq("id", Integer.valueOf(friendId)))
            			.uniqueResult();
            
            int counter = 1;
            if(userfriendlist != null){
     
      friendVo.setEmailid(userfriendlist.getUsersByFriendid().getEmailid());
    
      friendVo.setUserFriendId(userfriendlist.getId());
      
      

            	
    			if(userfriendlist.getStatus() == true)
    			{
    				friendVo.setStatus("Active");
    			}
    			else
    			{
    				friendVo.setStatus("InActive");
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

		return friendVo;
	
	}
	
	@Override

	public int updateUserFriendById(String friendId, boolean status) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
	    Transaction tx = null;
	    Userfriendlist userfriendlist = null;
	    int result = 0;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userfriendlist = (Userfriendlist)
            			session.createCriteria(Userfriendlist.class)
            			.add(Restrictions.eq("id", Integer.valueOf(friendId)))
            			.uniqueResult();
            
            if(userfriendlist != null){
            	userfriendlist.setStatus(status);
            	session.update(userfriendlist);
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
