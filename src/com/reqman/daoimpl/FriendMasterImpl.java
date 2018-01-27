package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.chart.PieChartModel;
























import com.reqman.common.HibernateUtil;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Category;
import com.reqman.pojo.Project;
import com.reqman.pojo.Request;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.util.sendEmail1;
import com.reqman.util.sendEmailonfriend;
import com.reqman.util.setinfoEmail;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.FriendVo;
import com.reqman.vo.ProjectVo;
import com.reqman.vo.UserVo;

public class FriendMasterImpl implements FriendMasterInterface {

	

	@Override
	public int savefriend(String firstname, String lastname, String emailid,String password,String shortname,String hashkey) throws Exception {
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
	        Accountusers accountusers=null;
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
	            	hashkey = new setinfoEmail().createAccount2(emailid, emailid);
	            	//hashkey = new sendEmail1().createAccount(emailid, emailid);
	     			System.out.println("-password--"+hashkey);
	            
	            	  users=new Users();
	            	  users.setEmailid(emailid);
	            	  //users.setFirstname(firstname);
	            	 // users.setLastname(lastname);		            	
	            	 // users.setShortname(shortname);	            	              
	            	  users.setCreatedby("SYSTEM");	            
	            	  users.setCreatedon(new Date());
	                  users.setHashkey(hashkey != null ? hashkey.trim() : "");
	            	  users.setStatus(true);
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

	            		accountusers = new Accountusers();
	                     accountusers.setAccount(accountDetails);
	                     accountusers.setUsers(users);
	                     session.save(accountusers);
	                	
	                	
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
	

	
	@Override
	public int savefriend(String frienduser, Boolean status,String friendfirstname, String friendlastname,
			String password,String friendshortname, String userName,String hashkey) throws Exception {
		
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	        Users users = null;
	        Userroles userrolesDetails=null;
	        Roles roles=null;
	        roles= new Roles();
	        userrolesDetails = new Userroles();
	        int result = 0;
	        String emailArr[] = {};
	        String account = "";
	        Account accountDetails = new Account();
	       
	        roles= new Roles();
	        userrolesDetails = new Userroles();
	        Accountusers accountusers=null;
	        Userfriendlist userfriendlist = null;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            String usename=users.getFirstname();
	       
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", frienduser.toLowerCase().trim()).ignoreCase())
	            	.uniqueResult();
	            
	            
	          
	            if(users != null)
	            {
	            	Userfriendlist userfriendExist = null;
	            	if(users.getUserfriendlistsForFriendid() != null && users.getUserfriendlistsForFriendid().size() != 0)
	            	{
	            		for(Userfriendlist userfriendDB : users.getUserfriendlistsForFriendid())
	            		{
	            			if(userfriendDB != null && userfriendDB.getUsersByUserid() != null && userfriendDB.getUsersByUserid().getEmailid() != null)
	            			{
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
	            		userfriendlist.setCreatedby(userName != null ? userName.trim() : "");
	            		userfriendlist.setDatecreated(new Date());
	                	session.save(userfriendlist);
	                	sendEmailonfriend sef=new sendEmailonfriend();
	                	sef.friendemail(frienduser, frienduser,usename);
	                	
	                	result = 3;
	                	tx.commit();
	            	}
	            }
	            else
	            {
	            	
	            
	            	sendEmailonfriend sef=new sendEmailonfriend();
                	sef.friendemail(frienduser, friendfirstname,usename);	            	
	            	// password = new sendEmail1().createAccount(frienduser, friendfirstname);
	 				System.out.println("-password--"+password);
	 		
	            	users = new Users();
	            	users.setEmailid(frienduser != null ? frienduser.trim() : "");
	            	//users.setFirstname(friendfirstname  != null ? friendfirstname.trim() : "");
	            	//users.setLastname(friendlastname  != null ? friendlastname.trim() : "");
	            	//users.setShortname(friendshortname  != null ? friendshortname.trim() : "");
	            	//users.setPassword(password  != null ? password .trim() : "");
	            	users.setStatus(true);
	            	users.setCreatedby(userName  != null ? userName.trim() : "");
	            	users.setCreatedon(new Date());
	            	users.setHashkey(hashkey != null ? hashkey.trim() : "");           	
	            	
	            	session.save(users);
	            	hashkey = new setinfoEmail().createAccount2(frienduser, frienduser);
	            	//hashkey = new sendEmail1().createAccount(frienduser, frienduser);
	     			System.out.println("-password--"+hashkey);
	            	emailArr = frienduser.split("@");
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

	            		accountusers = new Accountusers();
	                     accountusers.setAccount(accountDetails);
	                     accountusers.setUsers(users);
	                     session.save(accountusers);
	                	
	                	
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
            					&& userfriendDB.getUsersByFriendid().getStatus() == true)
            			{
            				friendVo = new FriendVo();
            				String firstName = "";
            				String lastName = "";
            				String name = "";
            				if(userfriendDB != null && userfriendDB.getUsersByFriendid() != null 
    								&& userfriendDB.getUsersByFriendid() != null)
            				{
    							firstName = userfriendDB.getUsersByFriendid().getFirstname() != null 
    									? userfriendDB.getUsersByFriendid().getFirstname() : "";
    									
    							lastName = userfriendDB.getUsersByFriendid().getLastname() != null 
    									? userfriendDB.getUsersByFriendid().getLastname() : "";
    									
    							if(firstName != null && !firstName.trim().equals(""))
    							{
    								name = firstName.trim();
    							}
    							
    							if(lastName != null && !lastName.trim().equals(""))
    							{
    								name = name + " " +lastName.trim();
    							}
    						}
    						
            				
            				friendVo.setName(name);
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
                				friendVo.setStatus("In-Active");
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
    				friendVo.setStatus("In-Active");
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



	@Override
	public List<FriendVo> getUsersStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<FriendVo> friendList1 = new ArrayList<FriendVo>();
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
            					&& userfriendDB.getUsersByFriendid().getStatus() == true && userfriendDB.getStatus() == true)
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
                				friendVo.setStatus("In-Active");
                			}
                			counter++;
                			friendList1.add(friendVo);
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

		return friendList1;
	}



	@Override
	public List<FriendVo> getUsersfasleStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<FriendVo> friendList1 = new ArrayList<FriendVo>();
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
            					&& userfriendDB.getUsersByFriendid().getStatus() == true && userfriendDB.getStatus() == false)
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
                				friendVo.setStatus("In-Active");
                			}
                			counter++;
                			friendList1.add(friendVo);
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

		return friendList1;
	}



	@Override
	public int updateFriend(String oldValue, String newValue,
			Integer updatefriendId) throws Exception {
		// TODO Auto-generated method stub
		Userfriendlist userfriendlistTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    int result = 0;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userfriendlistTemp = (Userfriendlist)session.createCriteria(Userfriendlist.class)
            		.add(Restrictions.eq("id", updatefriendId))
            		.uniqueResult();
            
            if(userfriendlistTemp != null)
            {   
            	if(oldValue != null && oldValue.trim().equalsIgnoreCase("In-Active") 
            			&& newValue != null && newValue.trim().equalsIgnoreCase("Active"))
            	{
            		userfriendlistTemp.setStatus(true);
            		
            	}
            	
            	if(oldValue != null && oldValue.trim().equalsIgnoreCase("Active") 
            			&& newValue != null && newValue.trim().equalsIgnoreCase("In-Active"))
            	{
            		userfriendlistTemp.setStatus(false);
            	}
            	session.update(userfriendlistTemp);
            	tx.commit();
            	result = 1;
            	
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = 2;
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return result;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<UserVo> AllUsers(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<UserVo> getfriendList=null; 
		UserVo userVo=null;
		 Users users=null;
		 Session session = null;
		    Transaction tx = null;
		    List<Integer> friendList = null;
			try
			{
	           	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid",userName.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            Integer userid=users.getId();
	            
	            Criteria crit1 = session.createCriteria(Userfriendlist.class);
	            crit1.add(Restrictions.eq("status", true));
	            crit1.add(Restrictions.eq("usersByUserid.id", users.getId()));
	            crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
	                    .setProjection(
	                            Projections.distinct(Projections.projectionList()
	                                    .add(Projections.property("usersByFriendid.id"))));
	            friendList = (List<Integer>) crit1.list();	
	            
	        
 				
				if(friendList != null && friendList.size() != 0)
   				{
					List<Users>  userList = (List<Users>) session.createCriteria(Users.class)
    							      .add(Restrictions.in("id",friendList)).list();
 				 					
    				
 					for(Users userDB : userList)
            		{
 						userVo=new UserVo();
 						
 						userVo.setEmailid(userDB.getEmailid());
 						userVo.setUserId(userDB.getId());
 						
            		}
	            
   				}
	            
	            tx.commit();  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	            session.getTransaction().rollback();  
	        }  
	        session.close();  
	        return getfriendList;  
	    }  




	
}
