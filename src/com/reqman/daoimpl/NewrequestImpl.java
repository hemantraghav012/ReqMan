package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.NewrequestInterface;
import com.reqman.pojo.Request;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Users;
import com.reqman.vo.NewrequestVo;

public class NewrequestImpl implements NewrequestInterface {

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public int save(String title, String description, Integer usercategory,
			Integer userproject, Integer userrequesttype,
			UploadedFile attachment, String userName, Date completiondate,
			Integer[] userfriendlist) throws Exception {

		Session session = null;
		Transaction tx = null;
		int result = 0;
		Request requestNew = null;
		Request requestDB = null;
		List<Request> requestList = new ArrayList<Request>();
		try {
			
			session = HibernateUtil.getSession();
			
			//prepare the request list object
			requestList = getRequestDetails(title, description, usercategory,
					userproject, userrequesttype, attachment, userName,
					completiondate, userfriendlist, session);
			
			if(requestList != null && requestList.size() != 0)
			{
				tx = session.beginTransaction();

				for(Request request : requestList)
				{
					//checking the request object
					Criteria crit = session.createCriteria(Request.class);
					if(request != null && request.getUsercategory() != null)
					{
						crit.add(Restrictions.eq("usercategory", request.getUsercategory()));
					}
					if(request != null && request.getUserproject() != null)
					{
						crit.add(Restrictions.eq("userproject", request.getUserproject()));
					}
					if(request != null && request.getUserrequesttype() != null)
					{
						crit.add(Restrictions.eq("userrequesttype", request.getUserrequesttype()));
					}
					
					if(request != null && request.getUserfriendlist() != null)
					{
						crit.add(Restrictions.eq("userfriendlist", request.getUserfriendlist()));
					}
					
					crit.add(Restrictions.eq("title", title.trim().toLowerCase()).ignoreCase());
					
					crit.add(Restrictions.eq("createdby", userName.trim().toLowerCase()).ignoreCase());
					
					requestDB = (Request)crit.uniqueResult();
					
					// if request exist then update the request object
					if(requestDB != null)
					{
						result = 2;
					}
					else if(requestDB != null && requestDB.getStatus().booleanValue() == false)
					{
						result = 2;
					}
					// if request not exist then insert the request object
					else
					{
						session.save(request);
						result = 3;
						
					}
				}
				
				tx.commit();
			}


		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result = 4;
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}
	
	
	private List<Request> getRequestDetails(String title, String description, Integer usercategory,
			Integer userproject, Integer userrequesttype,
			UploadedFile attachment, String userName, Date completiondate,
			Integer[] userfriendlist, Session session) throws Exception
	{
		
		Users users = null;
		Userproject userproject1 = null;
		Usercategory usercategory1 = null;
		Userrequesttype userrequesttype1 = null;
		Userfriendlist userfriendlistTemp = null;
		Request request = null;
		List<Request> requestList = new ArrayList<Request>();

		
		try
		{
			
			if(userfriendlist != null && userfriendlist.length != 0)
			{
				for (int friendlist : userfriendlist) 
				{

					request = new Request();
					
					if(userName != null && !userName.trim().equals(""))
					{
						users = (Users) session
								.createCriteria(Users.class)
								.add(Restrictions.eq("emailid",userName.toLowerCase().trim()).ignoreCase())
								.uniqueResult();
						
						request.setCreatedby(userName.trim());
					}
					
					if(userproject != null)
					{
						userproject1 = (Userproject)session.createCriteria(Userproject.class)
								.add(Restrictions.eq("id", userproject))
								.uniqueResult();
						
						request.setUserproject(userproject1);
					}
		
					if(userrequesttype != null)
					{
						userrequesttype1 = (Userrequesttype)session.createCriteria(Userrequesttype.class)
								.add(Restrictions.eq("id", userrequesttype))
								.uniqueResult();
						
						request.setUserrequesttype(userrequesttype1);
					}
		
					if(usercategory != null)
					{
						usercategory1 = (Usercategory)session.createCriteria(Usercategory.class)
								.add(Restrictions.eq("id", usercategory))
								.uniqueResult();
						
						request.setUsercategory(usercategory1);
					}
		
					request.setTitle(title != null ? title.trim() : "");
					
					request.setRequeststatus(1);
					request.setStatus(true);
					request.setDatecreated(new Date());
					request.setDescription(description != null ? description.trim() : "");
					
					if(attachment != null && attachment.getContents() != null)
					{
						request.setAttachment(attachment.getContents());
					}
					
					if(attachment != null && attachment.getFileName() != null)
					{
						request.setFilename(attachment.getFileName());
					}
					
					if(completiondate != null)
					{
						request.setCompletiondate(completiondate);
					}
					
					userfriendlistTemp = (Userfriendlist)session.createCriteria(Userfriendlist.class)
							.add(Restrictions.eq("id", friendlist))
							.uniqueResult();
					
					request.setUserfriendlist(userfriendlistTemp);
					requestList.add(request);
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e);
		}
		return requestList;
	}

	
	@SuppressWarnings("unchecked")
	public List<NewrequestVo> getNewrequestDetails(String userName)
			throws Exception {
		List<NewrequestVo> requestList = new ArrayList<NewrequestVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		NewrequestVo newrequestVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {


				List<Request> requesPojoList = (List<Request>) session
						.createCriteria(Request.class)
						.add(Restrictions.eq("createdby",
								userName.toLowerCase().trim()).ignoreCase())
						.list();
				
			    String userCategory = "";
				String userProject = "";
				String userRequestType= "";
				String firstName = "";
				String lastName = "";
				String name = "";
				if (requesPojoList != null && requesPojoList.size() != 0) {
					for (Request requestDB : requesPojoList) {
						userCategory = "";
						userProject = "";
						userRequestType= "";
						newrequestVo = new NewrequestVo();
						firstName = "";
						lastName = "";
						name = "";


						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						
						if(requestDB != null && requestDB.getUsercategory() != null && requestDB.getUsercategory().getCategory() != null)
						{
							userCategory = requestDB.getUsercategory().getCategory().getName() != null 
									? requestDB.getUsercategory().getCategory().getName() : "" ;
						}
						
						if(requestDB != null && requestDB.getUserproject() != null && requestDB.getUserproject().getProject() != null)
						{
							userProject = requestDB.getUserproject().getProject().getName() != null 
									? requestDB.getUserproject().getProject().getName() : "" ;
						}
						
						if(requestDB != null && requestDB.getUserrequesttype() != null && requestDB.getUserrequesttype().getRequesttype() != null)
						{
							userRequestType = requestDB.getUserrequesttype().getRequesttype() != null 
									? requestDB.getUserrequesttype().getRequesttype().getName() : "" ;
						}
						
						if(requestDB != null && requestDB.getUserfriendlist() != null 
								&& requestDB.getUserfriendlist().getUsersByFriendid() != null)
						{
							firstName = requestDB.getUserfriendlist().getUsersByFriendid().getFirstname() != null 
									? requestDB.getUserfriendlist().getUsersByFriendid().getFirstname() : "";
									
							lastName = requestDB.getUserfriendlist().getUsersByFriendid().getLastname() != null 
									? requestDB.getUserfriendlist().getUsersByFriendid().getLastname() : "";
									
							if(firstName != null && !firstName.trim().equals(""))
							{
								name = firstName.trim();
							}
							
							if(lastName != null && !lastName.trim().equals(""))
							{
								name = name + " " +lastName.trim();
							}
						}
						
						newrequestVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
						newrequestVo.setDescription(requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
						newrequestVo.setCompletiondate(requestDB.getCompletiondate() != null ? requestDB.getCompletiondate().toString() : "");
						newrequestVo.setFriendName(name);
						newrequestVo.setUsercategory(userCategory);
						newrequestVo.setUserproject(userProject);
						newrequestVo.setUserrequesttype(userRequestType);

						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true)
						{
							newrequestVo.setStatus("Active");
						}
						else
						{
							newrequestVo.setStatus("In Active");
						}
						
						newrequestVo.setNewRequestId(requestDB.getId());
									
						requestList.add(newrequestVo);
					}
								
				}

				tx.commit();
			}
		} catch (Exception e) 
		{
			if(tx != null)
			{
				tx.rollback();
			}

			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return requestList;
	}
	
	
	
	@Override
	public NewrequestVo getRequestById(String requestId) throws Exception{
		// TODO Auto-generated method stub
		
		    Session session = null;
		    Transaction tx = null;
		   NewrequestVo  newrequestVo = new  NewrequestVo();
		   Request request = null;
			try
			{
	           	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            request = (Request)
	            			session.createCriteria(Request.class)
	            			.add(Restrictions.eq("id", Integer.valueOf(requestId)))
	            			.uniqueResult();
	            
	            String userCategory = "";
				String userProject = "";
				String userRequestType= "";            
	          String status="";
	            Hibernate.initialize(request);
	            if(request != null){
	            	if(request.getAttachment() != null && request.getAttachment().length != 0)
	            	{	            		
	            		newrequestVo.setFile(request.getAttachment());
	            	}
	            	

					if(request != null && request.getUsercategory() != null && request.getUsercategory().getCategory() != null)
					{
						userCategory = request.getUsercategory().getCategory().getName() != null 
								? request.getUsercategory().getCategory().getName() : "" ;
					}
					
					if(request != null && request.getUserproject() != null && request.getUserproject().getProject() != null)
					{
						userProject = request.getUserproject().getProject().getName() != null 
								? request.getUserproject().getProject().getName() : "" ;
					}
					
					if(request != null && request.getUserrequesttype() != null && request.getUserrequesttype().getRequesttype() != null)
					{
						userRequestType = request.getUserrequesttype().getRequesttype() != null 
								? request.getUserrequesttype().getRequesttype().getName() : "" ;
					}
	            	if(request.getFilename() != null 
	            			&& !request.getFilename().trim().equals(""))
	            	{
	            		newrequestVo.setFileName(request.getFilename().trim());
	            	}
	            	newrequestVo.setTitle(request.getTitle());
	            	newrequestVo.setNewRequestId(request.getId());
	            	newrequestVo.setUsercategory(userCategory);
					newrequestVo.setUserproject(userProject);
					newrequestVo.setUserrequesttype(userRequestType);
					newrequestVo.setDescription(request.getDescription());
					newrequestVo.setCompletiondate(request.getCompletiondate() != null ? request.getCompletiondate().toString() : "");
					//newrequestVo.setAttachment((UploadedFile) (requestworkflow.getAttachment() !=null ? requestworkflow.getAttachment() :""));
					newrequestVo.setFileName(request.getFilename());
			
					if(request.getStatus() == true)
					{
						newrequestVo.setStatus("Active");
					}
					else
					{
						newrequestVo.setStatus("In Active");
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

			return newrequestVo;
		
		}
	
	
	
	
	
	

	@Override
	public int updateRequestById(String requestId, Boolean status,
			String description, Date completiondate, UploadedFile attachment)
			throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
		    Transaction tx = null;
		   NewrequestVo  newrequestVo = new  NewrequestVo();
		   Request requestworkflow = null;
		   Request request=null;
		   int result = 0;
			
				try
				{
					session = HibernateUtil.getSession();
		            tx = session.beginTransaction();
		            requestworkflow = (Request)
		            			session.createCriteria(Request.class)
		            			.add(Restrictions.eq("id", Integer.valueOf(requestId)))
		            			.uniqueResult();
		          
		           
		            if(requestworkflow != null ){
		            	requestworkflow.setStatus(status);
		            	requestworkflow.setDescription(description);
		            	//requestworkflow.setAttachment(attachment.getContents());
		            	//requestworkflow.setCompletiondate(completiondate);
		            	//requestworkflow.setFilename(attachment.getFileName());
		            	
		            	session.update(requestworkflow);
		    			tx.commit();;
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
