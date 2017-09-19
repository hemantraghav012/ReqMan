package com.reqman.daoimpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.servlet.http.Part;

import org.apache.poi.util.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.NewrequestInterface;
import com.reqman.pojo.Category;
import com.reqman.pojo.Project;
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

	

	@SuppressWarnings("unchecked")
	@Override
	public int save(String title, String description, Integer usercategory,
			Integer userproject ,  Integer userrequesttype, 
			UploadedFile attachment,String userName,Date completiondate, Integer[] userfriendlist) throws Exception {
		// TODO Auto-generated method stub
		
		
		Session session = null;
        SessionFactory hsf = null;
        Transaction tx = null;
        Users users = null;
        int result = 0;
        Userproject userproject1 = null;
        Usercategory usercategory1=null;
        Userrequesttype userrequesttype1=null;
       Userfriendlist userfriendlist1=null;
       Requestworkflow requestworkflow=null;
        Request request=null;
        
        try {
        	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            users = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
           
           
            userproject1 = (  Userproject)session.createCriteria(  Userproject.class).add(Restrictions.eq("id", userproject))
              		.uniqueResult();            
          
            userrequesttype1 = ( Userrequesttype)session.createCriteria( Userrequesttype.class).add(Restrictions.eq("id", userrequesttype))
               		.uniqueResult(); 
      
            usercategory1 = (Usercategory)session.createCriteria(Usercategory.class).add(Restrictions.eq("id", usercategory))
             		.uniqueResult();
            
            
            for(int friendlist:userfriendlist){
            	
            
            
            
          userfriendlist1 = (Userfriendlist)session.createCriteria(  Userfriendlist.class)
            .add(Restrictions.eq("id", friendlist))
                 .uniqueResult(); 		
            
        
         requestworkflow=new  Requestworkflow();
            
         requestworkflow.setUserfriendlist(userfriendlist1);
         
     	session.save(requestworkflow);
            }
     //	Requestworkflow        requestworkflow1= (Requestworkflow)session.createCriteria(  Requestworkflow.class)
        // .add(Restrictions.eq(" id", userfriendlist))
          //    .uniqueResult();
	
     	
            	request=new Request();
            	
            	request.setTitle(title.trim());
            	request.setDescription(description);
            	request.setUsercategory(usercategory1);           	 	
                    request.setUserproject(userproject1);
            	request.setUserrequesttype(userrequesttype1);           
            	request.setAttachment(attachment.getContents()); 
            	request.setFilename(attachment.getFileName());
            	
             	
       //request.setRequestworkflows( (requestworkflow1);
           	request.setCompletiondate(completiondate);
            	request.setStatus(true);
            	request.setCreatedby(userName);
            	request.setDatecreated(new Date());
            	 
            	session.save(request);
            	
            	
            	
            	result = 3;
            	tx.commit();
         
            
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

	


	
	
	
	@SuppressWarnings("unchecked")
	public List<NewrequestVo> getNewrequestDetails(String userName) throws Exception
	{
			List<NewrequestVo> requestList = new ArrayList<NewrequestVo>();
			Users usersTemp = null;
		    Session session = null;
		    Transaction tx = null;
		    NewrequestVo newrequestVo = null;
			try
			{
	           	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            usersTemp = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid",userName.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            if(usersTemp != null){
	            
	            	 //request = new Request();
	          
	            	
	            				newrequestVo = new NewrequestVo();
	                			
	            				Request request = (Request)session.createCriteria(Request.class)
	            				.add(Restrictions.eq("createdby",userName.toLowerCase().trim()).ignoreCase())
	    	            		.list();
	            				newrequestVo.setTitle(request.getTitle());	                			
	            				newrequestVo.setDescription(request.getDescription());
	            				newrequestVo.setCompletiondate(request.getCompletiondate());
	            				newrequestVo.setUsercategory(request.getUsercategory());
	            				newrequestVo.setUserproject(request.getUserproject());
	            				newrequestVo.setUserrequesttype(request.getUserrequesttype());
	            				
	                			requestList.add(newrequestVo);
	            		
	            
	            	
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

			return requestList;
		}







	
}
