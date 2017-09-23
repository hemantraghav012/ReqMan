package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.NewrequestInterface;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestworkflow;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
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
		Request request = null;
		Request requestDB = null;
		try {
			
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			//prepare the request object
			request = getRequestDetails(title, description, usercategory,
					userproject, userrequesttype, attachment, userName,
					completiondate, userfriendlist, session);

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
			
			crit.add(Restrictions.eq("title", title.trim().toLowerCase()).ignoreCase());
			
			crit.add(Restrictions.eq("createdby", userName.trim().toLowerCase()).ignoreCase());
			
			requestDB = (Request)crit.uniqueResult();
			
			
			// if request exist then update the request object
			if(requestDB != null)
			{
				Hibernate.initialize(requestDB.getRequestworkflows());
				
				result = updateRequest(requestDB, request, session);
				tx.commit();
			}
			else if(requestDB != null && requestDB.getStatus().booleanValue() == false)
			{
				result = 2;
			}
			// if request not exist then insert the request object
			else
			{
				session.save(request);
				if(request != null && request.getRequestworkflows() != null && request.getRequestworkflows().size() != 0)
				{
					for(Requestworkflow requestworkflowNew : request.getRequestworkflows())
					{
						session.save(requestworkflowNew);
					}
				}
				
				result = 3;
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
	
	private int updateRequest(Request requestDB, Request request, Session session) throws Exception
	
	{
		int result = 1;
		
		try
		{
			String emailIdNew = "";
			String emailIdDB = "";
			Requestworkflow requestworkflowTemp = null;
			Requestworkflow requestworkflowNewTemp = null;
			
			if(request != null && request.getRequestworkflows() != null && request.getRequestworkflows().size() != 0)
			{
				for(Requestworkflow requestworkflowNew : request.getRequestworkflows() )
				{
					requestworkflowTemp = null;
					if(requestDB != null && requestDB.getRequestworkflows() != null && requestDB.getRequestworkflows().size() != 0)
					{
						for(Requestworkflow  requestworkflowDB : requestDB.getRequestworkflows())
						{
							if(requestworkflowDB != null && requestworkflowNew != null 
									&& requestworkflowDB.getUserfriendlist() != null 
									&& requestworkflowNew.getUserfriendlist() != null)
							{
								emailIdDB = requestworkflowDB.getUserfriendlist().getUsersByFriendid() != null 
										? requestworkflowDB.getUserfriendlist().getUsersByFriendid().getEmailid() : "";
								emailIdNew = requestworkflowNew.getUserfriendlist().getUsersByFriendid() != null 
										? requestworkflowNew.getUserfriendlist().getUsersByFriendid().getEmailid() : "";
								if(emailIdDB.trim().equalsIgnoreCase(emailIdNew.trim()))
								{
									requestworkflowTemp = requestworkflowDB;
									break;
								}
							}
						}
						
						//save the friend id else skip the update 
						if(requestworkflowTemp == null){
							requestworkflowNew.setRequest(requestDB);
							session.save(requestworkflowNew);
						}
					}
				}
			}
	    }
		catch(Exception e)
		{
			result = 4;
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return result;
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

						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getRequestworkflows());
						
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
						
						
						
						if(requestDB != null && requestDB.getRequestworkflows() != null && requestDB.getRequestworkflows().size() != 0)
						{
							for(Requestworkflow requestworkflowDB : requestDB.getRequestworkflows())
							{
								newrequestVo = new NewrequestVo();
								firstName = "";
								lastName = "";
								name = "";

								if(requestworkflowDB != null && requestworkflowDB.getUserfriendlist() != null 
										&& requestworkflowDB.getUserfriendlist().getUsersByFriendid() != null)
								{
									
									firstName = requestworkflowDB.getUserfriendlist().getUsersByFriendid().getFirstname() != null 
											? requestworkflowDB.getUserfriendlist().getUsersByFriendid().getFirstname() : "";
											
									lastName = requestworkflowDB.getUserfriendlist().getUsersByFriendid().getLastname() != null 
											? requestworkflowDB.getUserfriendlist().getUsersByFriendid().getLastname() : "";
											
									if(firstName != null && !firstName.trim().equals(""))
									{
										name = firstName.trim();
									}
									
									if(lastName != null && !lastName.trim().equals(""))
									{
										name = name + " " +lastName.trim();
									}
									
									newrequestVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
									newrequestVo.setDescription(requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
									newrequestVo.setCompletiondate(requestDB.getCompletiondate() != null ? requestDB.getCompletiondate().toString() : "");
									newrequestVo.setFriendName(name);
									newrequestVo.setUsercategory(userCategory);
									newrequestVo.setUserproject(userProject);
									newrequestVo.setUserrequesttype(userRequestType);
									
									if(requestworkflowDB != null && requestworkflowDB.getStatus() != null 
											&& requestworkflowDB.getStatus().booleanValue() == true)
									{
										newrequestVo.setStatus("Active");
									}
									else
									{
										newrequestVo.setStatus("In Active");
									}
									
									
									
									requestList.add(newrequestVo);
								}
								
							}
						}

						
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
	
	
	private Request getRequestDetails(String title, String description, Integer usercategory,
			Integer userproject, Integer userrequesttype,
			UploadedFile attachment, String userName, Date completiondate,
			Integer[] userfriendlist, Session session) throws Exception
	{
		
		Transaction tx = null;
		Users users = null;
		int result = 0;
		Userproject userproject1 = null;
		Usercategory usercategory1 = null;
		Userrequesttype userrequesttype1 = null;
		Userfriendlist userfriendlistTemp = null;
		Requestworkflow requestworkflow = null;
		Request request = null;
		Set<Requestworkflow> requestworkflows = new HashSet<Requestworkflow>(0);
		Requestworkflow requestworkflowTemp = null;

		
		try
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
			
			request.setRequeststatus(1);
			request.setStatus(true);
			request.setDatecreated(new Date());


			
			if(userfriendlist != null && userfriendlist.length != 0)
			{
				for (int friendlist : userfriendlist) {
					requestworkflowTemp = new Requestworkflow();
					
					userfriendlistTemp = (Userfriendlist)session.createCriteria(Userfriendlist.class)
							.add(Restrictions.eq("id", friendlist))
							.uniqueResult();

					requestworkflowTemp.setRequest(request);
					requestworkflowTemp.setUserfriendlist(userfriendlistTemp);
					requestworkflowTemp.setRequeststatus(1);
					requestworkflowTemp.setCreatedby(userName.trim());
					requestworkflowTemp.setDatecreated(new Date());
					requestworkflowTemp.setStatus(true);
					requestworkflows.add(requestworkflowTemp);
				}
				request.setRequestworkflows(requestworkflows);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e);
		}
		return request;
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
	            request = ( Request)
	            			session.createCriteria( Request.class)
	            			.add(Restrictions.eq("id", Integer.valueOf(requestId)))
	            			.uniqueResult();
	            
	            int counter = 1;
	            if(request != null){
	       
	            	newrequestVo.setTitle(request.getTitle());
	            	newrequestVo.setNewRequestId(request.getId());
	            	
	            	
	    			if(request.getStatus() == true)
	    			{
	    				newrequestVo.setStatus("Active");
	    			}
	    			else
	    			{
	    				newrequestVo.setStatus("InActive");
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
			String description) throws Exception{
		
		    Session session = null;
		    Transaction tx = null;
		    Request request = null;
		    int result = 0;
			try
			{
	           	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            request = (  Request)
	            			session.createCriteria(  Request.class)
	            			.add(Restrictions.eq("id", Integer.valueOf(requestId)))
	            			.uniqueResult();
	            
	            if(  request != null){
	            	request.setStatus(status);
	            	//request.setDescription(description);
	            	session.update(request);
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
