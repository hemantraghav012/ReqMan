package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.responseInterface;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.ResponseVo;
import com.reqman.vo.requestNoteVo;

public class ResponseImpl implements responseInterface {

	

	@SuppressWarnings({ "unchecked", "null" })
	public List<ResponseVo> getresponseDetails(String userName)
			throws Exception {
		List<ResponseVo> requestList = new ArrayList<ResponseVo>();
		Users usersTemp = null;
		Users usersDB = null;
		Session session = null;
		Transaction tx = null;
		Request request=null;
		List<Integer> friendList = null;
		List<requestNoteVo> requestnoteList =null;
		requestNoteVo requestnoteVo=null;
		ResponseVo responseVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();
		if (usersTemp != null) {
			
						
			Criteria crit1 = session.createCriteria(Userfriendlist.class);
            crit1.add(Restrictions.eq("status", true));
            crit1.add(Restrictions.eq("usersByFriendid.id", usersTemp.getId()));
            crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setProjection(
                            Projections.distinct(Projections.projectionList()
                                    .add(Projections.property("id"))));
            friendList = (List<Integer>) crit1.list();
	
            List<Request> requesPojoList =  null;
            			if(friendList != null && friendList.size() != 0)
            		{
            				requesPojoList = (List<Request>) session.createCriteria(Request.class)
             						.add(Restrictions.in("userfriendlist.id",friendList)).list();
            
            		}
            	String emailid="";
			    String userCategory = "";
				String userProject = "";
				String userRequestType= "";
				String firstName = "";
				String lastName = "";
				String name = "";
				if (requesPojoList != null && requesPojoList.size() != 0) {
					for (Request requestDB : requesPojoList) {
						emailid="";
						userCategory = "";
						userProject = "";
						userRequestType= "";
						responseVo = new ResponseVo();
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
						
						if(requestDB != null)
						{
							
							usersTemp = (Users) session
									.createCriteria(Users.class)
									.add(Restrictions.eq("emailid",
											requestDB.getCreatedby().trim()).ignoreCase())
									.uniqueResult();
							if(usersTemp !=null ){
							emailid= usersTemp.getEmailid();
							firstName=usersTemp.getFirstname();
							lastName= usersTemp.getLastname();
								
							if(firstName != null && !firstName.trim().equals(""))
							{
								name = firstName.trim();
							}
							
							if(lastName != null && !lastName.trim().equals(""))
							{
								name = name + " " +lastName.trim();
							}
						}
						}
						if(requestDB.getStatus()==true && requestDB.getRequeststatus()==1){
						responseVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
						responseVo.setDescription(requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
						responseVo.setChangedate(requestDB.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
						
						if(!name. equalsIgnoreCase("")){
							responseVo.setFriendName(name);
							}else{
								responseVo.setFriendName(emailid);	
							}
						if(userCategory.equalsIgnoreCase("")){
							responseVo.setUsercategory("General");
						}else{
						responseVo.setUsercategory(userCategory);
						}
						if(userProject.equalsIgnoreCase("")){
							responseVo.setUserproject("General");
						}else{
						responseVo.setUserproject(userProject);
						}
						if(userRequestType.equalsIgnoreCase("")){
							responseVo.setUserrequesttype("General");	
						}else{
						responseVo.setUserrequesttype(userRequestType);
						}
						
						responseVo.setCreatedby(requestDB.getCreatedby());
				     	responseVo.setCreatedate(requestDB.getDatecreated());
						
						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true )
						{
							responseVo.setStatus("Active");
						}
						else
						{
							responseVo.setStatus("In-Active");
						}
						
						responseVo.setNewRequestId(requestDB.getId());
									

						String createdbyfirstname = "";
						String	createdbylastname = "";
						String	createdbyname = "";
						String	createdbyemailid="";
						if(requestDB.getRequestnoteses() !=null && requestDB.getRequestnoteses().size() !=0){ 
							
							for(Requestnotes requestnotes : requestDB.getRequestnoteses()){
								createdbyfirstname = "";
								createdbylastname = "";
								createdbyname = "";
								createdbyemailid="";
				
							 
								if (requestnotes.getCreatedby() != null) {
									
									usersDB = (Users) session.createCriteria(Users.class)
											.add(Restrictions.eq("emailid",	requestnotes.getCreatedby()))
											.uniqueResult();
							createdbyemailid = usersDB.getEmailid() != null ? usersDB
									.getEmailid() : "";
                               createdbyfirstname = usersDB.getFirstname() != null ? usersDB
											.getFirstname() : "";

											createdbylastname = usersDB.getLastname() != null ? usersDB
											.getLastname() : "";

									if (createdbyfirstname != null
											&& !createdbyfirstname.trim().equals("")) {
										createdbyname = createdbyfirstname.trim();
									}

									if (createdbylastname != null
											&& !createdbylastname.trim().equals("")) {
										createdbyname = createdbyname + " " + createdbylastname.trim();
									}
									else{
										createdbyname = createdbyemailid;
									}
									
								}

								requestnoteVo=new requestNoteVo();
							
								requestnoteVo.setCreatedby(createdbyname);
								//requestnoteVo.setCreatedby(requestnotes.getCreatedby() !=null ? requestnotes.getCreatedby().trim() : "" );
								requestnoteVo.setNoteId(requestnotes.getId());
								requestnoteVo.setMessage(requestnotes.getMessage() != null ? requestnotes.getMessage().trim() : "");
								requestnoteVo.setCreatedon(requestnotes.getCreatedon()!= null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestnotes.getCreatedon()) : "");
								requestnoteVo.setTime(requestnotes.getCreatedon()!= null ?  Dateconverter.convertTimeToStringhhmmss(requestnotes.getCreatedon()) : "");
								
								
								requestnoteList.add(requestnoteVo);
								Collections.sort(requestnoteList,requestNoteVo.NoteIdComparator );
							}
							}
						
							 
						responseVo.setNoteList(requestnoteList);	
						
						
						requestList.add(responseVo);
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
	public ResponseVo getResponseById(String requestId) throws Exception {
		// TODO Auto-generated method stub
		  Session session = null;
		    Transaction tx = null;
		   ResponseVo  responseVo = new  ResponseVo();
		   Request request = null;
			Users usersTemp = null;
			try
			{
	           	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            request = (Request)
	            			session.createCriteria(Request.class)
	            			.add(Restrictions.eq("id", Integer.valueOf(requestId)))
	            			.uniqueResult();
	            String emailid="";
	            String userCategory = "";
				String userProject = "";
				String userRequestType= "";            
	          String status="";
	          String firstName="";
	          String lastName="";
	          String name="";
	            Hibernate.initialize(request);
	            if(request != null){
	            	if(request.getAttachment() != null && request.getAttachment().length != 0)
	            	{	            		
	            		responseVo.setFile(request.getAttachment());
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
	            		responseVo.setFileName(request.getFilename().trim());
	            	}
	            	
	        		if(request != null)
					{
						
						usersTemp = (Users) session
								.createCriteria(Users.class)
								.add(Restrictions.eq("emailid",
										request.getCreatedby().trim()).ignoreCase())
								.uniqueResult();
						if(usersTemp !=null ){
						emailid= usersTemp.getEmailid();
						firstName=usersTemp.getFirstname();
						lastName= usersTemp.getLastname();
							
						if(firstName != null && !firstName.trim().equals(""))
						{
							name = firstName.trim();
						}
						
						if(lastName != null && !lastName.trim().equals(""))
						{
							name = name + " " +lastName.trim();
						}
					}
					}
				
	            	
	            	
	            	responseVo.setTitle(request.getTitle());
	            	responseVo.setNewRequestId(request.getId());

	            	if(!name. equalsIgnoreCase("")){
						responseVo.setFriendName(name);
						}else{
							responseVo.setFriendName(emailid);	
						}
	            	
					if(userCategory.equalsIgnoreCase("")){
						responseVo.setUsercategory("General");
					}else{
	            	responseVo.setUsercategory(userCategory);
					}
					if(userProject.equalsIgnoreCase("")){
						responseVo.setUserproject("General");
					}else{
					responseVo.setUserproject(userProject);
					}
					if(userRequestType.equalsIgnoreCase("")){
						responseVo.setUserrequesttype("General");
					}else{
					responseVo.setUserrequesttype(userRequestType);
					}
					responseVo.setDescription(request.getDescription());
					responseVo.setCompletiondate(request.getCompletiondate());
					responseVo.setFileName(request.getFilename());
					responseVo.setPriority(request.getPriority());
					responseVo.setWeightage(request.getWeightage());
					responseVo.setEstimatedeffort(request.getEstimatedeffort());			
					responseVo.setActualeffort(request.getActualeffort());
			
					if(request.getRequeststatus()==2)
					{
						responseVo.setStage("Accepted");
					}
				 else if(request.getRequeststatus()==3)
					{
						responseVo.setStage("Returned");
					}
				 else if(request.getRequeststatus()==1){
					 responseVo.setStage("Requested");
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

			return responseVo;
		
		}
	

	@Override
	public int updateResponsetById(String requestId, Integer stage,
			Date completiondate,String userName,String message, String actualeffort) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
		    Transaction tx = null;
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
		            
		            	if(completiondate != null){
		           	requestworkflow.setCompletiondate(completiondate);
		        	  	
		            	}
		            	
		            	requestworkflow.setRequeststatus(stage);
                     if(stage==2){
                    	 requestworkflow.setAcceptdate(new Date());
                    	
		            		
		            	}
                     requestworkflow.setDatemodified(new Date());
 		        	requestworkflow.setModifiedby(userName);
 		        	requestworkflow.setActualeffort(actualeffort);
		            	session.update(requestworkflow);
		            	
		            	
		            	if(message !=null && ! message.trim().equals("")){	
				           	requestnotes=new Requestnotes();
			            		requestnotes.setRequest(requestworkflow);
				            	requestnotes.setMessage(message);	            	
				            	requestnotes.setCreatedby(userName);
				            	requestnotes.setCreatedon(new Date());
				            	session.save(requestnotes);
				            	
				            }
		            	
		            	
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
