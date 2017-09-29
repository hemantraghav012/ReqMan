package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.SuggestionInterface;
import com.reqman.pojo.Category;
import com.reqman.pojo.Request;
import com.reqman.pojo.Suggestion;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.SuggestionVo;

public class SuggestionImpl implements SuggestionInterface {

	@Override
	public int savesuggestion(String message, Boolean status, String userName) throws Exception {
		// TODO Auto-generated method stub
		   Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	       Suggestion suggestion = null;
	       Users users = null;
	        int result = 0;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            if(users != null)
	            {
	                suggestion=new Suggestion();
	            	suggestion.setMessage(message);
	            	suggestion.setCreatedby(userName);
	            	suggestion.setDatecreated(new Date());            	
	            	suggestion.setStatus(true);
	            	session.save(suggestion);
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
	@SuppressWarnings("unchecked")
	@Override
	public List<SuggestionVo> getsuggestionDetails(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<SuggestionVo> suggestionList = new ArrayList<SuggestionVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    SuggestionVo suggestionVo = null;
	    //Suggestion suggestion= null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
         
            		
            	//Suggestion suggestionDB = new Suggestion();
            
				List<Suggestion> suggestionlist  = (List<Suggestion>) session
						.createCriteria(Suggestion.class)
						.add(Restrictions.eq("createdby",
								userName.toLowerCase().trim()).ignoreCase())
						.list();
            			
			for(Suggestion suggestion:suggestionlist) {
				
		
            				suggestionVo = new SuggestionVo();                		
            				suggestionVo.setMessage( suggestion.getMessage() !=null ? suggestion.getMessage() : "");
            				suggestionVo.setSuggestionid(suggestion.getId());              			
                			
                			suggestionList.add(suggestionVo);
            			
			}
            		}
            	
            	tx.commit();
            
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

		return suggestionList;
	}

}
