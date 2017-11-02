package com.reqman.daoimpl;

import java.util.ArrayList;
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
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.ResponseVo;

public class ResponseImpl implements responseInterface {

	

	@SuppressWarnings({ "unchecked", "null" })
	public List<ResponseVo> getresponseDetails(String userName)
			throws Exception {
		List<ResponseVo> requestList = new ArrayList<ResponseVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request=null;
		List<Integer> friendList = null;
	
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
			
		//Integer id1=usersTemp.getId();	
		
						
			Criteria crit1 = session.createCriteria(Userfriendlist.class);
            crit1.add(Restrictions.eq("status", true));
            crit1.add(Restrictions.eq("usersByFriendid.id", usersTemp.getId()));
            crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setProjection(
                            Projections.distinct(Projections.projectionList()
                                    .add(Projections.property("id"))));
            friendList = (List<Integer>) crit1.list();
	
		
			
				List<Request> requesPojoList = (List<Request>) session.createCriteria(Request.class)
						.add(Restrictions.in("userfriendlist.id",friendList)).list();
			
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
						responseVo = new ResponseVo();
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
						if(requestDB.getStatus()==true && requestDB.getRequeststatus()==1){
						responseVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
						responseVo.setDescription(requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
						responseVo.setCompletiondate(requestDB.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
						responseVo.setFriendName(name);
						responseVo.setUsercategory(userCategory);
						responseVo.setUserproject(userProject);
						responseVo.setUserrequesttype(userRequestType);
						responseVo.setCreatedby(requestDB.getCreatedby());
					
						
						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true )
						{
							responseVo.setStatus("Active");
						}
						else
						{
							responseVo.setStatus("In Active");
						}
						
						responseVo.setNewRequestId(requestDB.getId());
									
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
	            	responseVo.setTitle(request.getTitle());
	            	responseVo.setNewRequestId(request.getId());
	            	responseVo.setUsercategory(userCategory);
					responseVo.setUserproject(userProject);
					responseVo.setUserrequesttype(userRequestType);
					responseVo.setDescription(request.getDescription());
					responseVo.setCompletiondate(request.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(request.getCompletiondate()) : "");
					//responseVo.setAttachment((UploadedFile) (requestworkflow.getAttachment() !=null ? requestworkflow.getAttachment() :""));
					responseVo.setFileName(request.getFilename());
			
					if(request.getRequeststatus()==2)
					{
						responseVo.setStage("Accepted");
					}
				 else if(request.getRequeststatus()==3)
					{
						responseVo.setStage("Send Back");
					}
				 else if(request.getRequeststatus()==1){
					 responseVo.setStage("Request");
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
			Date completiondate,String userName) throws Exception {
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
		            	
		           //	requestworkflow.setDescription(description);
		            	
		            	requestworkflow.setRequeststatus(stage);
                     if(stage==2){
                    	 requestworkflow.setAcceptdate(new Date());
                    	
		            		
		            	}
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
