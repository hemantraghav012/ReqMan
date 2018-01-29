package com.reqman.daoimpl;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.NewrequestInterface;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.util.RequestConstants;
import com.reqman.util.requestemail;
import com.reqman.util.sendEmail1;
import com.reqman.util.sendEmailonfriend;
import com.reqman.vo.BarchartVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.requestNoteVo;


public class NewrequestImpl implements NewrequestInterface {

	private static final String Good = null;



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
					request.setCompletionpercentage(0);
					request.setRequeststatus(1);
					request.setStatus(true);
					request.setDatecreated(new Date());
					request.setDatemodified(new Date());
					request.setModifiedby(userName);			
					
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
							.add(Restrictions.eq("usersByUserid.id", users.getId()))
							.add(Restrictions.eq("usersByFriendid.id", friendlist))
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
	public List<NewrequestVo> getNewrequestDetails(String userName,	Date startDate, Date endDate)
			throws Exception {
		List<NewrequestVo> requestList = new ArrayList<NewrequestVo>();

		List<requestNoteVo> requestnoteList =null;
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request= null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo=null;
		//RequesttypeMasterImpl reinf = new RequesttypeMasterImpl();
		GetRolequery reinf = new GetRolequery();
		List<Integer> requestIdList  = new ArrayList<Integer>();
		String roleName = "";
		try {
			
			roleName = reinf.getRoleNameByLoginId(userName);
			
			requestIdList = reinf.getRequestListByRole(roleName, userName);
			
			if (requestIdList != null && requestIdList.size() != 0) 
			{
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				
				Criteria crit = session.createCriteria(Request.class);
				crit.add(Restrictions.in("id", requestIdList));
				//search date range
				if(startDate != null && endDate !=null)
				{
					crit.add(Restrictions.ge("datecreated", Dateconverter.getMinTimeByDate(startDate))); 
				    crit.add(Restrictions.lt("datecreated", Dateconverter.getMaxTimeByDate(endDate)));
			
				}
				List<Request> requesPojoList = (List<Request>)crit.list();
				
				
			    String userCategory = "";
				String userProject = "";
				String userRequestType= "";
				String firstName = "";
				String lastName = "";
				String name = "";
				if (requesPojoList != null && requesPojoList.size() != 0) 
				{
					
					for (Request requestDB : requesPojoList) 
					{

						if(requestDB.getRequeststatus()==1 || requestDB.getRequeststatus()==2 || requestDB.getRequeststatus()==3 || 
							requestDB.getRequeststatus()==4 || requestDB.getRequeststatus()==5 || 
							 requestDB.getRequeststatus()==7 ){
					
						userCategory = "";
						userProject = "";
						userRequestType= "";
						newrequestVo = new NewrequestVo();
						firstName = "";
						lastName = "";
						name = "";
						requestnoteList=new ArrayList<requestNoteVo>();
						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						Hibernate.initialize(requestDB.getRequestnoteses());
						Hibernate.initialize(request);
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
						newrequestVo.setChangedate(requestDB.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
						newrequestVo.setFriendName(name);
						newrequestVo.setUsercategory(userCategory);
						newrequestVo.setUserproject(userProject);
						newrequestVo.setUserrequesttype(userRequestType);
						newrequestVo.setCompletionpercentage(requestDB.getCompletionpercentage());
						//newrequestVo.setNoteList(requestDB.getRequestnoteses());
						
						if(requestDB.getRequeststatus()==2)
						{
							newrequestVo.setStage("Accepted");
						}
					 else if(requestDB.getRequeststatus()==3)
						{
						 newrequestVo.setStage("Returned");
						}
					 else if(requestDB.getRequeststatus()==1){
						 newrequestVo.setStage("Requested");
					 }
					 else if(requestDB.getRequeststatus()==4){
						 newrequestVo.setStage("In-progress");
					 }
						
					 else if(requestDB.getRequeststatus()==5){
						 newrequestVo.setStage("Completed");
					 }
					 else if(requestDB.getRequeststatus()==6){
						 newrequestVo.setStage("Cancel");
					 }
					 else if(requestDB.getRequeststatus()==7){
						 newrequestVo.setStage("Hold");
					 }
					 else if(requestDB.getRequeststatus()==8){
						 newrequestVo.setStage("Close");
					 }
						
						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true)
						{
							newrequestVo.setStatus("Active");
						}
						else
						{
							newrequestVo.setStatus("In-Active");
						}
						
						newrequestVo.setNewRequestId(requestDB.getId());
						
						
						if(requestDB.getRequestnoteses() !=null && requestDB.getRequestnoteses().size() !=0){ 
							
						for(Requestnotes requestnotes : requestDB.getRequestnoteses()){
						 firstName="";
						 lastName="";
						 name="";
			
						 
						 if(usersTemp != null && requestnotes.getCreatedby() != null)
							{
								usersTemp = (Users)session.createCriteria(Users.class)
										.add(Restrictions.eq("emailid",  requestnotes.getCreatedby()))
										.uniqueResult();
								
								
								firstName = usersTemp.getFirstname() != null 
										? usersTemp.getFirstname() : "";
										
								lastName = usersTemp.getLastname() != null 
										? usersTemp.getLastname() : "";
										
								if(firstName != null && !firstName.trim().equals(""))
								{
									name = firstName.trim();
								}
								
								if(lastName != null && !lastName.trim().equals(""))
								{
									name = name + " " +lastName.trim();
								}
								
							}
						 
						
							requestnoteVo=new requestNoteVo();
						
							requestnoteVo.setCreatedby(name);
							//requestnoteVo.setCreatedby(requestnotes.getCreatedby() !=null ? requestnotes.getCreatedby().trim() : "" );
							requestnoteVo.setNoteId(requestnotes.getId());
							requestnoteVo.setMessage(requestnotes.getMessage() != null ? requestnotes.getMessage().trim() : "");
							requestnoteVo.setCreatedon(requestnotes.getCreatedon()!= null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestnotes.getCreatedon()) : "");
							requestnoteVo.setTime(requestnotes.getCreatedon()!= null ?  Dateconverter.convertTimeToStringhhmmss(requestnotes.getCreatedon()) : "");
							
							
							requestnoteList.add(requestnoteVo);
							Collections.sort(requestnoteList,requestNoteVo.NoteIdComparator );
						}
						}
					
						 
						newrequestVo.setNoteList(requestnoteList);			
						requestList.add(newrequestVo);
						
					/*	System.out.println("title--"+requestDB.getTitle());
						System.out.println("dec--"+requestDB.getDescription());
						System.out.println("friend--"+name);
						System.out.println("project--"+userProject);
						
						title=requestDB.getTitle();
						description=requestDB.getDescription();
						requestemail rm= new requestemail();					
						rm.friendemail(userName,userName, title, description);*/
					
					}
					
					}
				tx.commit();
			}
				
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
	          String firstName = "";
				String lastName = "";
				String name = "";
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
	            	if(request != null && request.getUserfriendlist() != null 
							&& request.getUserfriendlist().getUsersByFriendid() != null)
					{
						firstName = request.getUserfriendlist().getUsersByFriendid().getFirstname() != null 
								? request.getUserfriendlist().getUsersByFriendid().getFirstname() : "";
								
						lastName = request.getUserfriendlist().getUsersByFriendid().getLastname() != null 
								? request.getUserfriendlist().getUsersByFriendid().getLastname() : "";
								
						if(firstName != null && !firstName.trim().equals(""))
						{
							name = firstName.trim();
						}
						
						if(lastName != null && !lastName.trim().equals(""))
						{
							name = name + " " +lastName.trim();
						}
					}
	            	newrequestVo.setTitle(request.getTitle());
	            	newrequestVo.setNewRequestId(request.getId());
	            	newrequestVo.setUsercategory(userCategory);
					newrequestVo.setUserproject(userProject);
					newrequestVo.setUserrequesttype(userRequestType);
					newrequestVo.setDescription(request.getDescription());
					newrequestVo.setCompletiondate(request.getCompletiondate() );
					//newrequestVo.setAttachment((UploadedFile) (requestworkflow.getAttachment() !=null ? requestworkflow.getAttachment() :""));
					newrequestVo.setFileName(request.getFilename());
					newrequestVo.setFriendName(name);
					newrequestVo.setCompletionpercentage(request.getCompletionpercentage());
					newrequestVo.setRating(request.getRating());
					newrequestVo.setFeedback(request.getFeedback());
					
					if(request.getRequeststatus()==1){
						newrequestVo.setStage("Requested");
					}
					else if(request.getRequeststatus()==2){
						newrequestVo.setStage("Accepted");
					}
					else if(request.getRequeststatus()==3){
						newrequestVo.setStage("Returned");
					}
					else if(request.getRequeststatus()==4){
						newrequestVo.setStage("In-progress");
					}
					else if(request.getRequeststatus()==5){
						newrequestVo.setStage("Completed");
					}
					else if(request.getRequeststatus()==6){
						newrequestVo.setStage("Cancel");
					}
					else if(request.getRequeststatus()==7){
						newrequestVo.setStage("Hold");
					}
					else if(request.getRequeststatus()==8){
						newrequestVo.setStage("Close");
					}
					
					
					if(request.getStatus() == true)
					{
						newrequestVo.setStatus("Active");
					}
					else
					{
						newrequestVo.setStatus("In-Active");
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
			String description, Date completiondate, UploadedFile attachment,
			Float completionpercentage, Integer stage, String message,
			String userName, Integer userproject, Integer usercategory,
			Integer userrequesttype, Integer userfriend) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
		    Transaction tx = null;
		    Userproject userproject1=null;
		    Usercategory usercategory1=null;
		    Userrequesttype userrequesttype1=null;
		    Userfriendlist userfriend1=null;
		    NewrequestVo  newrequestVo = new  NewrequestVo();
		    Request requestworkflow = null;
		    Request request=null;
		    int result = 0;
		    Requestnotes  requestnotes=null;
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
		            	
		            	
		            	if(completiondate != null){
				           	requestworkflow.setCompletiondate(completiondate);
				            
				        		}
		           
		            		
		            	
		        	if(userproject != null)
						{
							userproject1 = (Userproject)session.createCriteria(Userproject.class)
									.add(Restrictions.eq("id", userproject))
									.uniqueResult();
							
							requestworkflow.setUserproject(userproject1);
						}
		            	
		            	if(userrequesttype != null)
						{
							userrequesttype1 = (Userrequesttype)session.createCriteria(Userrequesttype.class)
									.add(Restrictions.eq("id", userrequesttype))
									.uniqueResult();
							
							requestworkflow.setUserrequesttype(userrequesttype1);;
						}
		            	
		            	if(usercategory != null)
						{
							usercategory1 = (Usercategory)session.createCriteria(Usercategory.class)
									.add(Restrictions.eq("id", usercategory))
									.uniqueResult();
							
							requestworkflow.setUsercategory(usercategory1);
						}
		         
		            	if(userfriend != null)
						{
							userfriend1 = (Userfriendlist)session.createCriteria(Userfriendlist.class)
									.add(Restrictions.eq("id", userfriend))
									.uniqueResult();
							
							requestworkflow.setUserfriendlist(userfriend1);
						}
		            	 if(stage !=null && stage==8){
		            	requestworkflow.setRequeststatus(stage);
		            	requestworkflow.setApproveddate(new Date());
		            	requestworkflow.setApprovedby(userName);
		            	 }
		            	 else{
		            		 requestworkflow.setRequeststatus(stage); 
		            	 }
		            	 
		            		requestworkflow.setDatemodified(new Date());
				        	requestworkflow.setModifiedby(userName);
		            	 
		            	session.update(requestworkflow);
		            	
		            if(message !=null && ! message.trim().equals("")){	
		           	requestnotes=new Requestnotes();
	            		requestnotes.setRequest(requestworkflow);
		            	requestnotes.setMessage(message);	            	
		            	requestnotes.setCreatedby(userName);
		            	requestnotes.setCreatedon(new Date());
		            	session.save(requestnotes);
		            	
		            }
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



	
	

	private Date parse(String getDate) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<NewrequestVo> getallproject(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<NewrequestVo> requestList = new ArrayList<NewrequestVo>();

		List<requestNoteVo> requestnoteList =null;
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request= null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {


				@SuppressWarnings("unchecked")
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
						if(requestDB.getUserproject() != null && requestDB.getUserproject().getProjectaccess()==true){
						
						
						userCategory = "";
						userProject = "";
						userRequestType= "";
						newrequestVo = new NewrequestVo();
						firstName = "";
						lastName = "";
						name = "";
						requestnoteList=new ArrayList<requestNoteVo>();

						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						Hibernate.initialize(requestDB.getRequestnoteses());
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
						newrequestVo.setChangedate(requestDB.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
						newrequestVo.setFriendName(name);
						newrequestVo.setUsercategory(userCategory);
						newrequestVo.setUserproject(userProject);
						newrequestVo.setUserrequesttype(userRequestType);
						newrequestVo.setCompletionpercentage(requestDB.getCompletionpercentage());
						//newrequestVo.setNoteList(requestDB.getRequestnoteses());
						
						if(requestDB.getRequeststatus()==2)
						{
							newrequestVo.setStage("Accepted");
						}
					 else if(requestDB.getRequeststatus()==3)
						{
						 newrequestVo.setStage("Returned");
						}
					 else if(requestDB.getRequeststatus()==1){
						 newrequestVo.setStage("Requested");
					 }
					 else if(requestDB.getRequeststatus()==4){
						 newrequestVo.setStage("In-progress");
					 }
						
					 else if(requestDB.getRequeststatus()==5){
						 newrequestVo.setStage("Complete");
					 }
					 else if(requestDB.getRequeststatus()==6){
						 newrequestVo.setStage("Cancel");
					 }
					 else if(requestDB.getRequeststatus()==7){
						 newrequestVo.setStage("Hold");
					 }
					 else if(requestDB.getRequeststatus()==8){
						 newrequestVo.setStage("Close");
					 }
						
						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true)
						{
							newrequestVo.setStatus("Active");
						}
						else
						{
							newrequestVo.setStatus("In-Active");
						}
						
						newrequestVo.setNewRequestId(requestDB.getId());
						
						
						if(requestDB.getRequestnoteses() !=null && requestDB.getRequestnoteses().size() !=0){ 
							
						for(Requestnotes requestnotes : requestDB.getRequestnoteses()){
						 firstName="";
						 lastName="";
						 name="";
			
						 
						 if(usersTemp != null && requestnotes.getCreatedby() != null)
							{
								usersTemp = (Users)session.createCriteria(Users.class)
										.add(Restrictions.eq("emailid",  requestnotes.getCreatedby()))
										.uniqueResult();
								
								
								firstName = usersTemp.getFirstname() != null 
										? usersTemp.getFirstname() : "";
										
								lastName = usersTemp.getLastname() != null 
										? usersTemp.getLastname() : "";
										
								if(firstName != null && !firstName.trim().equals(""))
								{
									name = firstName.trim();
								}
								
								if(lastName != null && !lastName.trim().equals(""))
								{
									name = name + " " +lastName.trim();
								}
								
							}
						 
						
							requestnoteVo=new requestNoteVo();
							
							
						//	Collections.sort(requestnoteList,requestNoteVo.NoteIdComparator );
							requestnoteVo.setCreatedby(name);
							//requestnoteVo.setCreatedby(requestnotes.getCreatedby() !=null ? requestnotes.getCreatedby().trim() : "" );
							requestnoteVo.setNoteId(requestnotes.getId());
							requestnoteVo.setMessage(requestnotes.getMessage() != null ? requestnotes.getMessage().trim() : "");
							requestnoteVo.setCreatedon(requestnotes.getCreatedon()!= null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestnotes.getCreatedon()) : "");
							requestnoteVo.setTime(requestnotes.getCreatedon()!= null ?  Dateconverter.convertTimeToStringhhmmss(requestnotes.getCreatedon()) : "");
							
							
							requestnoteList.add(requestnoteVo);
							Collections.sort(requestnoteList,requestNoteVo.NoteIdComparator );
						}
						}
					
						 
						newrequestVo.setNoteList(requestnoteList);			
						requestList.add(newrequestVo);
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


	
	
	

	@Override
	public List<NewrequestVo> getNewrequestDetailsforemail(String userName,
			String title, String description, String userproject,
			String usercategory, String userrequesttype, String friendname,
			String changedate, Float completionpercentage, Integer stage)
			throws Exception {
		// TODO Auto-generated method stub
		List<NewrequestVo> requestList = new ArrayList<NewrequestVo>();

		List<requestNoteVo> requestnoteList =null;
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request= null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {
				
			
				
/*
				List<Request> requesPojoList = (List<Request>) session
						.createCriteria(Request.class)						
						.add(Restrictions.eq("createdby",
						userName.toLowerCase().trim()).ignoreCase())
						.list();
						*/
				Criteria crit = session.createCriteria(Request.class);
				crit.add(Restrictions.eq("createdby", userName.toLowerCase().trim()).ignoreCase());
				//search stage in progress=4
				crit.add(Restrictions.eq("requeststatus", 4));
				
					List<Request> requesPojoList = (List<Request>)crit.list();
				
				
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
						requestnoteList=new ArrayList<requestNoteVo>();
						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						Hibernate.initialize(requestDB.getRequestnoteses());
						Hibernate.initialize(request);
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
						newrequestVo.setChangedate(requestDB.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
						newrequestVo.setFriendName(name);
						newrequestVo.setUsercategory(userCategory);
						newrequestVo.setUserproject(userProject);
						newrequestVo.setUserrequesttype(userRequestType);
						newrequestVo.setCompletionpercentage(requestDB.getCompletionpercentage());
						//newrequestVo.setNoteList(requestDB.getRequestnoteses());
						
						if(requestDB.getRequeststatus()==2)
						{
							newrequestVo.setStage("Accepted");
						}
					 else if(requestDB.getRequeststatus()==3)
						{
						 newrequestVo.setStage("Returned");
						}
					 else if(requestDB.getRequeststatus()==1){
						 newrequestVo.setStage("Requested");
					 }
					 else if(requestDB.getRequeststatus()==4){
						 newrequestVo.setStage("In-progress");
					 }
						
					 else if(requestDB.getRequeststatus()==5){
						 newrequestVo.setStage("Completed");
					 }
					 else if(requestDB.getRequeststatus()==6){
						 newrequestVo.setStage("Cancel");
					 }
					 else if(requestDB.getRequeststatus()==7){
						 newrequestVo.setStage("Hold");
					 }
					 else if(requestDB.getRequeststatus()==8){
						 newrequestVo.setStage("Close");
					 }
						
						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true)
						{
							newrequestVo.setStatus("Active");
						}
						else
						{
							newrequestVo.setStatus("In-Active");
						}
						
						newrequestVo.setNewRequestId(requestDB.getId());
						
						
							
						requestList.add(newrequestVo);
						
				
					
					}
					
					
				tx.commit();
			}
				
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
	public List<NewrequestVo> getColserequestDetails(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<NewrequestVo> requestList = new ArrayList<NewrequestVo>();

		List<requestNoteVo> requestnoteList =null;
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request= null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {
				
			//	requestemail rm= new requestemail();					
			//	rm.friendemail(userName,userName);
				

				@SuppressWarnings("unchecked")
				List<Request> requesPojoList = (List<Request>) session
						.createCriteria(Request.class)						
						.add(Restrictions.eq("createdby",
						userName.toLowerCase().trim()).ignoreCase())						
						.list();
						
				
				

				int project=0;
				int category=0;
				int requesttype=0;
				int userfriend=0;
			    String userCategory = "";
				String userProject = "";
				String userRequestType= "";
				String firstName = "";
				String lastName = "";
				String name = "";
				if (requesPojoList != null && requesPojoList.size() != 0) {
					
					for (Request requestDB : requesPojoList) {
						
						if(requestDB.getRequeststatus()==6 || requestDB.getRequeststatus()==8 ){
						
						
						project=0;
						category=0;
						requesttype=0;
						userfriend=0;
						userCategory = "";
						userProject = "";
						userRequestType= "";
						newrequestVo = new NewrequestVo();
						firstName = "";
						lastName = "";
						name = "";
						requestnoteList=new ArrayList<requestNoteVo>();
						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						Hibernate.initialize(requestDB.getRequestnoteses());
						Hibernate.initialize(request);
						if(requestDB != null && requestDB.getUsercategory() != null && requestDB.getUsercategory().getCategory() != null)
						{
							userCategory = requestDB.getUsercategory().getCategory().getName() != null 
									? requestDB.getUsercategory().getCategory().getName() : "" ;
									
							category = requestDB.getUsercategory().getCategory().getId();		
						}
						
						if(requestDB != null && requestDB.getUserproject() != null && requestDB.getUserproject().getProject() != null)
						{
							userProject = requestDB.getUserproject().getProject().getName() != null 
									? requestDB.getUserproject().getProject().getName() : "" ;
									
							project=requestDB.getUserproject().getProject().getId();
						}
						
						if(requestDB != null && requestDB.getUserrequesttype() != null && requestDB.getUserrequesttype().getRequesttype() != null)
						{
							userRequestType = requestDB.getUserrequesttype().getRequesttype().getName() != null 
									? requestDB.getUserrequesttype().getRequesttype().getName() : "" ;
									
							requesttype = requestDB.getUserrequesttype().getRequesttype().getId();		
						}
						
						if(requestDB != null && requestDB.getUserfriendlist() != null 
								&& requestDB.getUserfriendlist().getUsersByFriendid() != null)
						{
							userfriend=requestDB.getUserfriendlist().getUsersByFriendid().getId();
							
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
						newrequestVo.setChangedate(requestDB.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
						newrequestVo.setFriendName(name);
						newrequestVo.setUsercategory(userCategory);
						newrequestVo.setUserproject(userProject);
						newrequestVo.setUserrequesttype(userRequestType);
						newrequestVo.setCompletionpercentage(requestDB.getCompletionpercentage());
						newrequestVo.setProject(project);
						newrequestVo.setCategory(category);
						newrequestVo.setRequesttype(requesttype);
						newrequestVo.setUserfriend(userfriend);
						//newrequestVo.setNoteList(requestDB.getRequestnoteses());
						
						if(requestDB.getRequeststatus()==2)
						{
							newrequestVo.setStage("Accepted");
						}
					 else if(requestDB.getRequeststatus()==3)
						{
						 newrequestVo.setStage("Returned");
						}
					 else if(requestDB.getRequeststatus()==1){
						 newrequestVo.setStage("Requested");
					 }
					 else if(requestDB.getRequeststatus()==4){
						 newrequestVo.setStage("In-progress");
					 }
						
					 else if(requestDB.getRequeststatus()==5){
						 newrequestVo.setStage("Completed");
					 }
					 else if(requestDB.getRequeststatus()==6){
						 newrequestVo.setStage("Cancel");
					 }
					 else if(requestDB.getRequeststatus()==7){
						 newrequestVo.setStage("Hold");
					 }
					 else if(requestDB.getRequeststatus()==8){
						 newrequestVo.setStage("Close");
					 }
						
						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true)
						{
							newrequestVo.setStatus("Active");
						}
						else
						{
							newrequestVo.setStatus("In-Active");
						}
						
						newrequestVo.setNewRequestId(requestDB.getId());
						
						
						if(requestDB.getRequestnoteses() !=null && requestDB.getRequestnoteses().size() !=0){ 
							
						for(Requestnotes requestnotes : requestDB.getRequestnoteses()){
						 firstName="";
						 lastName="";
						 name="";
			
						 
						 if(usersTemp != null && requestnotes.getCreatedby() != null)
							{
								usersTemp = (Users)session.createCriteria(Users.class)
										.add(Restrictions.eq("emailid",  requestnotes.getCreatedby()))
										.uniqueResult();
								
								
								firstName = usersTemp.getFirstname() != null 
										? usersTemp.getFirstname() : "";
										
								lastName = usersTemp.getLastname() != null 
										? usersTemp.getLastname() : "";
										
								if(firstName != null && !firstName.trim().equals(""))
								{
									name = firstName.trim();
								}
								
								if(lastName != null && !lastName.trim().equals(""))
								{
									name = name + " " +lastName.trim();
								}
								
							}
						 
						
							requestnoteVo=new requestNoteVo();
						
							requestnoteVo.setCreatedby(name);
							//requestnoteVo.setCreatedby(requestnotes.getCreatedby() !=null ? requestnotes.getCreatedby().trim() : "" );
							requestnoteVo.setNoteId(requestnotes.getId());
							requestnoteVo.setMessage(requestnotes.getMessage() != null ? requestnotes.getMessage().trim() : "");
							requestnoteVo.setCreatedon(requestnotes.getCreatedon()!= null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestnotes.getCreatedon()) : "");
							requestnoteVo.setTime(requestnotes.getCreatedon()!= null ?  Dateconverter.convertTimeToStringhhmmss(requestnotes.getCreatedon()) : "");
							
							
							requestnoteList.add(requestnoteVo);
							Collections.sort(requestnoteList,requestNoteVo.NoteIdComparator );
						}
						}
					
						 
						newrequestVo.setNoteList(requestnoteList);			
						requestList.add(newrequestVo);
					
						
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
	
	
	
	
	@Override
	public int savefeedbackratingById(String requestId, String userName,
			Integer rating, String feedback,Integer stage) throws Exception {
		// TODO Auto-generated method stub
		
		
		    Session session = null;
		    Transaction tx = null;	   
		    Request requestworkflow = null;
		    Request request=null;
		    int result = 0;
		    Requestnotes  requestnotes=null;
				try
				{
					session = HibernateUtil.getSession();
		            tx = session.beginTransaction();
		            requestworkflow = (Request)
		            			session.createCriteria(Request.class)
		            			.add(Restrictions.eq("id", Integer.valueOf(requestId)))
		            			.uniqueResult();
		           
		            if(requestworkflow != null ){
		            	
		            	
		            		requestworkflow.setRating(rating);
		            		requestworkflow.setFeedback(feedback);
		            		requestworkflow.setRequeststatus(stage);
		            	session.update(requestworkflow);
		            	
		           
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


	
	
	public static void main(String args[]) throws Exception
	{
	//	NewrequestImpl reinf = new NewrequestImpl();
	//	String userName="hemantraghav012@gmail.com";
		//List<Integer> requestIdList = reinf.getRequestListByUser();
		//reinf.barchart(userName);
//		Map<String,Integer> requestbarmap=reinf.barchart("hemantraghav012@gmail.com");
//	System.out.println("-Email id->"+requestbarmap);
		
	
	/*	SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
		String inputString1 = "23 01 1997";
		String inputString2 = "27 01 1997";

		try {
		    Date date1 = myFormat.parse(inputString1);
		    Date date2 = myFormat.parse(inputString2);
		    long diff = date2.getTime() - date1.getTime();
		    System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		} catch (ParseException e) {
		    e.printStackTrace();
		}*/
		
		
	}


	

}
