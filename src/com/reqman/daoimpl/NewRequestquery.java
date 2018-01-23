package com.reqman.daoimpl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.NewRequestqueryInterface;

import com.reqman.pojo.Request;
import com.reqman.pojo.Users;

public class NewRequestquery implements NewRequestqueryInterface{

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigInteger> piechart(String userName) throws Exception {
		// TODO Auto-generated method stub
		 Map<String, BigInteger> requestmap = new HashMap<String, BigInteger>();
		    Session session = null;
		    Transaction tx = null;		
		    Users  usersTemp1=null;			  
		    List<Object[]> rows = null;
		    SQLQuery query = null;
		    String sqlQuery = "";
		    BigInteger countfriendemail=null;	
		    String friendemail=null;
		    
		    int result=0;
		  
				try
				{
				session = HibernateUtil.getSession();
		        tx = session.beginTransaction();
		            usersTemp1 = (Users) session.createCriteria(Users.class)
		            		.add(Restrictions.eq("emailid",userName.toLowerCase().trim()).ignoreCase())
							.uniqueResult();
					if (usersTemp1 != null)
					{
						 sqlQuery = " select (select u1.emailid from reqman.users as u1,reqman.userfriendlist as uf1  where "
                                  + " u1.id=uf1.friendid and u1.status=true and uf1.status=true and uf1.id=r.friendid), count(r.id) from reqman.request as r" 
                                  + " where r.status=true and r.requeststatus in (2,4,5) and r.friendid in "
                                  + " (select uf.id from reqman.users as u,reqman.userfriendlist as uf where u.id=uf.userid "
                                  + " and u.status=true and uf.status=true and u.emailid='"+userName+"') group by r.friendid";    
				          query = session.createSQLQuery(sqlQuery);
				          rows = query.list();
				            
				            if(rows != null && rows.size() != 0)
				            {	
				            	String feiend="";
				            	for(Object[] row : rows)
				            	{
				            		countfriendemail = (BigInteger) row[1];
				            		friendemail = (String) row[0];				            				
	                        	  requestmap.put(friendemail,countfriendemail);								
							     }
						    }
					  }
				   }
		            catch(Exception e)
		    		{
		    			e.printStackTrace();
		    			if(tx != null)
		    			{
		    				tx.rollback();
		    			}
		    			result = 2;
		    		}
		    		finally 
		    		{
		            	if(session != null)
		                session.close();
		    	    }

		
		return requestmap;
	}
	
	
	public Map<String,Double> barchart(String userName) throws Exception {
		  // TODO Auto-generated method stub
		   Map<String, Double> requestbarmap = new HashMap<String, Double>();
		   Session session = null;
		      Transaction tx = null;  
		     Users  usersTemp1=null;    
		     Request request=null;
		     int result = 0;
		     List<Object[]> rows = null;
		      SQLQuery query = null;
		      String sqlQuery = "";
		      BigInteger percentageDB=null; 
		      BigInteger countDB=null; 
		      Boolean grade=null;
		      Double currentpercentage=0.0;
		      String friendemail=null;
		      
		    try
		    {
		     session = HibernateUtil.getSession();
		           tx = session.beginTransaction();
		               usersTemp1 = (Users) session.createCriteria(Users.class)
		                 .add(Restrictions.eq("emailid",userName.toLowerCase().trim()).ignoreCase())
		        .uniqueResult();
		      if (usersTemp1 != null)
		      {
	 sqlQuery = " select (select u1.firstname from reqman.users as u1,reqman.userfriendlist as uf1 where "
               +" u1.id=uf1.friendid and u1.status=true and uf1.status=true and uf1.id=r.friendid),"
               +" (sum((r.completionpercentage*100)/(100 /(DATE_PART('day',r.completiondate-r.acceptdate))"
               +" *(DATE_PART('day',CURRENT_TIMESTAMP-r.acceptdate))))) / count(r.id ) as average"
               +" from reqman.request as r  "
	           +" where r.status=true and r.requeststatus in (4,5)and DATE_PART('day',CURRENT_TIMESTAMP-r.acceptdate) != 0 and r.friendid in" 
		       +" (select uf.id from reqman.users as u,reqman.userfriendlist as uf where u.id=uf.userid "
			   +" and u.status=true and uf.status=true and u.emailid='"+userName+"') group by r.friendid";
	 
		               query = session.createSQLQuery(sqlQuery);
		               rows = query.list();
		                 
		           if(rows != null && rows.size() != 0)
		             { 
		                  
		                  for(Object[] row : rows)
		                  {
		                    friendemail = (String) row[0]; 
		                   currentpercentage=(Double) row[1];
		                
		                 
		                   requestbarmap.put(friendemail,currentpercentage); 
		                    
		                   
		                 }
		              }
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

		  
		  return requestbarmap;
		 }

}
