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
import com.reqman.dao.UpdatestatusInterface;
import com.reqman.pojo.Request;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.UpdatestatusVo;

public class UpdatestatusImpl implements UpdatestatusInterface {

	@SuppressWarnings("unchecked")
	@Override
	public List<UpdatestatusVo> getupdatestatusDetails(String userName) throws Exception{
		// TODO Auto-generated method stub
		List<UpdatestatusVo> requestList = new ArrayList<UpdatestatusVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request=null;
		List<Integer> friendList = null;
	
		UpdatestatusVo updatestatusVo = null;
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
	
		
			
            List<Request> requesPojoList = null;
				if(friendList != null && friendList.size() != 0)
				{
					requesPojoList = (List<Request>) session.createCriteria(Request.class)
							.add(Restrictions.in("userfriendlist.id",friendList)).list();
				}
				
			
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
						updatestatusVo = new UpdatestatusVo();
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
						if(requestDB.getStatus()==true && (requestDB.getRequeststatus() == 2 ||requestDB.getRequeststatus()==4
								||requestDB.getRequeststatus()==5 ||requestDB.getRequeststatus()==8)){
						updatestatusVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
						updatestatusVo.setDescription(requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
						updatestatusVo.setCompletiondate(requestDB.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
						updatestatusVo.setFriendName(name);
						updatestatusVo.setUsercategory(userCategory);
						updatestatusVo.setUserproject(userProject);
						updatestatusVo.setUserrequesttype(userRequestType);
						updatestatusVo.setCreatedby(requestDB.getCreatedby());
						updatestatusVo.setCompletionpercentage(requestDB.getCompletionpercentage());
						
						if(requestDB != null && requestDB.getStatus() != null 
								&& requestDB.getStatus().booleanValue() == true )
						{
							updatestatusVo.setStatus("Active");
						}
						else
						{
							updatestatusVo.setStatus("In Active");
						}
						
						updatestatusVo.setNewRequestId(requestDB.getId());
									
						requestList.add(updatestatusVo);
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
	public UpdatestatusVo getRequestById(String requestId) {
		// TODO Auto-generated method stub
		 Session session = null;
		    Transaction tx = null;
		   UpdatestatusVo  updatestatusVo = new  UpdatestatusVo();
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
	            		updatestatusVo.setFile(request.getAttachment());
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
	            		updatestatusVo.setFileName(request.getFilename().trim());
	            	}
	            	updatestatusVo.setTitle(request.getTitle());
	            	updatestatusVo.setNewRequestId(request.getId());
	            	updatestatusVo.setUsercategory(userCategory);
					updatestatusVo.setUserproject(userProject);
					updatestatusVo.setUserrequesttype(userRequestType);
					updatestatusVo.setDescription(request.getDescription());
					updatestatusVo.setCompletiondate(request.getCompletiondate() != null ?  Dateconverter.convertDateToStringDDMMDDYYYY(request.getCompletiondate()) : "");
					//updatestatusVo.setAttachment((UploadedFile) (requestworkflow.getAttachment() !=null ? requestworkflow.getAttachment() :""));
					updatestatusVo.setFileName(request.getFilename());
					updatestatusVo.setCompletionpercentage(request.getCompletionpercentage());
					
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

			return updatestatusVo;
		
		}
	


	@Override
	public int updateRequestById(String requestId, Date completiondate,
			Float completionpercentage,Integer stage) {
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
		            	requestworkflow.setCompletionpercentage(completionpercentage);
		            	if(completionpercentage>0 && completionpercentage<99.9){
		            		stage=4;
		            		requestworkflow.setRequeststatus(stage);
		            	}
		            		else if(completionpercentage == 0){
		            			stage=2;
		            			requestworkflow.setRequeststatus(stage);		            		
		            	}
		            		else if(completionpercentage == 100){
		            			stage=5;
		            			requestworkflow.setRequeststatus(stage);		            		
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
