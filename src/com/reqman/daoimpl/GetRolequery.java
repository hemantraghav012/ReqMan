package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.GetrolequeryInterface;
import com.reqman.util.RequestConstants;




public class GetRolequery implements GetrolequeryInterface{
	@SuppressWarnings("unchecked")
	public String getRoleNameByLoginId(String loginId) throws Exception 
	{
	    Session session = null;
	    Transaction tx = null;
	    String roleName = "";
	    List<Object[]> rows = null;
	    SQLQuery query = null;
	    String sqlQuery = "";
	    String roleId = "";
	    try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            sqlQuery ="select r.id as roleId,r.name as roleName from reqman.roles as r, reqman.users as u,reqman.userroles as ur"
            		+ " where u.id=ur.userid and ur.roleid=r.id and r.status=true and u.emailid='"+loginId+"'";
            query = session.createSQLQuery(sqlQuery);
            rows = query.list();
            
            if(rows != null && rows.size() != 0)
            {
            	for(Object[] row : rows)
            	{
            		roleId = row[0].toString();
            		roleName = row[1].toString();
            	}
            }

            	tx.commit();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			if(tx != null)
			{
				tx.rollback();
			}
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return roleName;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRequestListByRole(String roleName, String loginId) throws Exception 
	{
	    Session session = null;
	    Transaction tx = null;
	    List<Object[]> rows = null;
	    SQLQuery query = null;
	    String sqlQuery = "";
	    Integer requestId = 0;
	    Integer friendId = 0;
	    
	    StringBuffer sb = new StringBuffer();
	    String[] accountArr = {};
	    String accountName = "";
	    List<Integer> requestIdList = new ArrayList<Integer>();
	    try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            accountArr = loginId.split("@");
            if(accountArr != null && accountArr.length > 1)
            {
            	accountName = accountArr[1];
            }
            
            if(roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE))
            {
            	sb.append("select rq.id,uf.friendid from reqman.users as u, reqman.userfriendlist as uf,reqman.request as rq,reqman.roles as r,"
            			+ "reqman.userroles as ur, reqman.accountusers as au, reqman.account as a where u.id=uf.userid and uf.id=rq.friendid "
            			+ "and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid and r.name = '"+roleName+"' "
            			+ "and u.emailid='"+loginId+"'");
            }
            else if(roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER))
            {
            	sb.append("select rq.id,uf.friendid from reqman.users as u, reqman.userfriendlist as uf,reqman.request as rq,reqman.roles as r,"
            			+ "reqman.userroles as ur, reqman.accountusers as au, reqman.account as a where u.id=uf.userid and uf.id=rq.friendid "
            			+ "and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid and r.name = '"+roleName+"' "
            			+ "and u.emailid='"+loginId+"'");
            }
            else if(roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE))
            {
            	sb.append("select rq.id,uf.friendid from reqman.users as u, reqman.userfriendlist as uf,reqman.request as rq,reqman.roles as r,"
            			+ "reqman.userroles as ur, reqman.accountusers as au, reqman.account as a where u.id=uf.userid and "
            			+ "uf.id=rq.friendid and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid "
            			+ "and r.name = '"+roleName+"' and a.name='"+accountName+"'");
            }
            else if(roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE))
            {
            	sb.append("select rq.id,uf.friendid from reqman.users as u, reqman.userfriendlist as uf,reqman.request as rq,"
            			+ "reqman.roles as r,reqman.userroles as ur where uf.id=rq.friendid and u.id=ur.userid and "
            			+ "ur.roleid=r.id and r.name = '"+roleName+"'");
            }
            else
            {
            	sb.append("");
            	
            }
            
            if(sb.length() == 0)
            {
            	return requestIdList;
            }
            
            sqlQuery = sb.toString();
            query = session.createSQLQuery(sqlQuery);
            rows = query.list();
            
            if(rows != null && rows.size() != 0)
            {
            	for(Object[] row : rows)
            	{
            		requestId = row[0] != null ? (Integer)row[0] : 0;
            		friendId = row[1] != null ?  (Integer)row[1] : 0;
            		requestIdList.add(requestId);
            	}
            }

            	tx.commit();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			if(tx != null)
			{
				tx.rollback();
			}
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return requestIdList;
	}


	@SuppressWarnings("null")
	public List<String> AllUser() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> emailList=new ArrayList<String>();
		   Session session = null;
		   Transaction tx = null;	 	
	       String emailid=null;
		   StringBuffer sb = new StringBuffer();
		   List<String> rows = null;
		   SQLQuery query = null;
		   String sqlQuery = "";
		   int result=0;
			
				try
				{
					session = HibernateUtil.getSession();
		            tx = session.beginTransaction();		           					 					

				sb.append("select distinct u.emailid from reqman.users as u, reqman.userfriendlist as uf,"
						+"reqman.request as r where u.id=uf.friendid and uf.id=r.friendid and r.status=true and"
						+ " r.requeststatus=1;");						
				
				 sqlQuery = sb.toString();
		            query =  session.createSQLQuery(sqlQuery);
		            
		            rows = query.list();
		            
		            if(rows != null && rows.size() != 0)
		            {
		            	for(String row : rows)
		            	{
		            		emailid = (String) (row!= null ? row : "");
		            	
		            		emailList.add(emailid);
		            		System.out.println("jj"+emailList);
		            		System.out.println(emailid);
		            	}
		            }

		            	tx.commit();
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

		
		return emailList;
	}
//account user Admin
	
	

	
	public Integer getAccountideByLoginId(String loginId) throws Exception 
	{
	    Session session = null;
	    Transaction tx = null;
	    
	    Integer accountid=0; 
	    List<Object[]> rows = null;
	    SQLQuery query = null;
	    String sqlQuery = "";
	    String roleId = "";
	    try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            sqlQuery ="select u.emailid,ac.accountid from reqman.roles as r,reqman.accountusers as ac, reqman.account as acco,"
            		+ "reqman.users as u,reqman.userroles as ur "
            		+" where u.id=ur.userid and ur.roleid=r.id and ac.userid=u.id and "
            		+" ac.accountid=acco.id and r.status=true and u.emailid='"+loginId+"'";
            query = session.createSQLQuery(sqlQuery);
            rows = query.list();
            
            if(rows != null && rows.size() != 0)
            {
            	for(Object[] row : rows)
            	{
            		roleId = row[0].toString();
            		accountid = (Integer) row[1];
            	}
            }

            	tx.commit();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			if(tx != null)
			{
				tx.rollback();
			}
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return accountid;
	}
	
	/*
	public static void main(String args[]) throws Exception
	{
		
		GetRolequery  gr=new GetRolequery();
		gr.getAccountideByLoginId("hemantraghav012@outlook.com");
		
		Integer accountid = 0;
		System.out.println("accounti:"+accountid);
	}
	*/
}
