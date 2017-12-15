package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.requesttypeMasterInterface;
import com.reqman.pojo.Requesttype;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Users;
import com.reqman.util.RequestConstants;
import com.reqman.vo.RequesttypeVo;


public class RequesttypeMasterImpl implements requesttypeMasterInterface{

	@Override
	public int saverequesttype(String name) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	    Requesttype requesttype = null;
	        int result = 0;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            requesttype = (Requesttype)session.createCriteria(Requesttype.class)
	            		.add(Restrictions.eq("name", name.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            if(requesttype != null && requesttype.getStatus() != null && requesttype.getStatus().booleanValue() == true){
	            	result = 1;
	            }
	            else if(requesttype != null && requesttype.getStatus() != null && requesttype.getStatus().booleanValue() == false){
	            	result = 2;
	            }
	            else
	            {
	            	requesttype=new Requesttype();
	            	requesttype.setName(name);	            
	            	requesttype.setCreatedby("SYSTEM");	            
	            	requesttype.setDatecreated(new Date());
	            	requesttype.setStatus(true);
	            	session.save(requesttype);
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

	@Override
	public int saverequesttype(String requesttypeName, Boolean status,
			String emailId) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	        Users users = null;
	        int result = 0;
	        Requesttype requesttype = null;
	        Userrequesttype userrequesttype = null;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            requesttype = (Requesttype)session.createCriteria(Requesttype.class)
	            		.add(Restrictions.eq("name", requesttypeName.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            if(requesttype != null)
	            {
	            	Userrequesttype userrequesttypeExist = null;
	            	if(requesttype.getUserrequesttypes() != null && requesttype.getUserrequesttypes().size() != 0){
	            		for(Userrequesttype userrequesttypeDB : requesttype.getUserrequesttypes())
	            		{
	            			if(userrequesttypeDB != null && userrequesttypeDB.getUsers() != null && userrequesttypeDB.getUsers().getEmailid() != null){
	            				if(userrequesttypeDB.getUsers().getEmailid().trim().equalsIgnoreCase(emailId))
	            				{
	            					userrequesttypeExist = userrequesttypeDB;
	            					break;
	            				}
	            			}
	            		}
	            	}
	            	
	            	if(userrequesttypeExist != null && userrequesttypeExist.getStatus().equals(true))
	            	{
	            		result = 1;
	            	}
	            	else if(userrequesttypeExist != null && userrequesttypeExist.getStatus().equals(false))
	            	{
	            		result = 2;
	            	}
	            	else
	            	{
	            		userrequesttype = new Userrequesttype();
	                	userrequesttype.setRequesttype(requesttype);
	                	userrequesttype.setUsers(users);
	                	userrequesttype.setStatus(true);
	                	
	                	session.save(userrequesttype);
	                	
	                	result = 3;
	                	tx.commit();
	            	}
	            }
	            else
	            {
	            	requesttype = new Requesttype();
	            	requesttype.setName(requesttypeName.trim());
	            	requesttype.setStatus(true);
	            	requesttype.setCreatedby(emailId);
	            	requesttype.setDatecreated(new Date());
	            	
	            	session.save(requesttype);
	            	
	            	userrequesttype = new Userrequesttype();
	            	userrequesttype.setRequesttype(requesttype);
	            	userrequesttype.setUsers(users);
	            	userrequesttype.setStatus(true);
	            	
	            	session.save(userrequesttype);
	            	
	            	result = 3;
	            	tx.commit();
	            }
	            
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
	public List<RequesttypeVo> getRequesttypeDetails(String emailId)
			throws Exception {
		// TODO Auto-generated method stub
		List<RequesttypeVo> requesttypeList = new ArrayList<RequesttypeVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    RequesttypeVo requesttypeVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUserrequesttypes() != null && usersTemp.getUserrequesttypes().size() != 0)
            	{
            		for(Userrequesttype userrequesttypeDB : usersTemp.getUserrequesttypes())
            		{
            			if(userrequesttypeDB != null && userrequesttypeDB.getRequesttype() != null 
            					&& userrequesttypeDB.getRequesttype().getStatus() == true)
            			{
            				requesttypeVo = new RequesttypeVo();
            				//requesttypeVo.setSrNo(counter);
            				requesttypeVo.setName(userrequesttypeDB.getRequesttype().getName());
            				requesttypeVo.setUserRequesttypeId(userrequesttypeDB.getId());
                			if(userrequesttypeDB.getStatus().equals(true))
                			{
                				requesttypeVo.setStatus("Active");
                			}
                			else
                			{
                				requesttypeVo.setStatus("InActive");
                			}
                			counter++;
                			requesttypeList.add(requesttypeVo);
            			}
            		}
            	}
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

		return requesttypeList;
	}
	

	@Override
	public RequesttypeVo getUserRequesttypeById(String requesttypeId)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
	    Transaction tx = null;
RequesttypeVo requesttypeVo = new RequesttypeVo();
	    Userrequesttype userrequesttype = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userrequesttype = (Userrequesttype)
            			session.createCriteria(Userrequesttype.class)
            			.add(Restrictions.eq("id", Integer.valueOf(requesttypeId)))
            			.uniqueResult();
            
            int counter = 1;
            if(userrequesttype != null){
            //	requesttypeVo.setSrNo(counter);
            	requesttypeVo.setName(userrequesttype.getRequesttype().getName());
            	requesttypeVo.setUserRequesttypeId(userrequesttype.getId());
    			if(userrequesttype.getStatus() == true)
    			{
    				requesttypeVo.setStatus("Active");
    			}
    			else
    			{
    				requesttypeVo.setStatus("InActive");
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

		return requesttypeVo;
	
	}
	@Override
	public int updateUserRequesttypeById(String requesttypeId, boolean status)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
	    Transaction tx = null;
	    Userrequesttype userrequesttype = null;
	    int result = 0;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userrequesttype = (Userrequesttype)
            			session.createCriteria(Userrequesttype.class)
            			.add(Restrictions.eq("id", Integer.valueOf(requesttypeId)))
            			.uniqueResult();
            
            if(userrequesttype != null){
            	userrequesttype.setStatus(status);
            	session.update(userrequesttype);
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

	@Override
	public List<RequesttypeVo> getRequesttypeStatus(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<RequesttypeVo> requesttypeList1 = new ArrayList<RequesttypeVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    RequesttypeVo requesttypeVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUserrequesttypes() != null && usersTemp.getUserrequesttypes().size() != 0)
            	{
            		for(Userrequesttype userrequesttypeDB : usersTemp.getUserrequesttypes())
            		{
            			if(userrequesttypeDB != null && userrequesttypeDB.getRequesttype() != null 
            			&& userrequesttypeDB.getRequesttype().getStatus() == true && userrequesttypeDB.getStatus() == true)
            			{
            				requesttypeVo = new RequesttypeVo();
            				//requesttypeVo.setSrNo(counter);
            				requesttypeVo.setName(userrequesttypeDB.getRequesttype().getName());
            				requesttypeVo.setUserRequesttypeId(userrequesttypeDB.getId());
                			if(userrequesttypeDB.getStatus().equals(true))
                			{
                				requesttypeVo.setStatus("Active");
                			}
                			else
                			{
                				requesttypeVo.setStatus("InActive");
                			}
                			counter++;
                			requesttypeList1.add(requesttypeVo);
            			}
            		}
            	}
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

		return requesttypeList1;
	}

	@Override
	public List<RequesttypeVo> getRequesttypefalseStatus(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<RequesttypeVo> requesttypeList1 = new ArrayList<RequesttypeVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    RequesttypeVo requesttypeVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUserrequesttypes() != null && usersTemp.getUserrequesttypes().size() != 0)
            	{
            		for(Userrequesttype userrequesttypeDB : usersTemp.getUserrequesttypes())
            		{
            			if(userrequesttypeDB != null && userrequesttypeDB.getRequesttype() != null 
            			&& userrequesttypeDB.getRequesttype().getStatus() == true && userrequesttypeDB.getStatus() == false)
            			{
            				requesttypeVo = new RequesttypeVo();
            				//requesttypeVo.setSrNo(counter);
            				requesttypeVo.setName(userrequesttypeDB.getRequesttype().getName());
            				requesttypeVo.setUserRequesttypeId(userrequesttypeDB.getId());
                			if(userrequesttypeDB.getStatus().equals(true))
                			{
                				requesttypeVo.setStatus("Active");
                			}
                			else
                			{
                				requesttypeVo.setStatus("InActive");
                			}
                			counter++;
                			requesttypeList1.add(requesttypeVo);
            			}
            		}
            	}
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

		return requesttypeList1;
	}

	@Override
	public int updateRequesttype(String oldValue, String newValue,
			Integer updaterequesttypeId) throws Exception {
		// TODO Auto-generated method stub

		Userrequesttype userrequesttypeTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    int result = 0;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userrequesttypeTemp = (Userrequesttype)session.createCriteria(Userrequesttype.class)
            		.add(Restrictions.eq("id", updaterequesttypeId))
            		.uniqueResult();
            
            if(userrequesttypeTemp != null)
            {   
            	if(oldValue != null && oldValue.trim().equalsIgnoreCase("InActive") 
            			&& newValue != null && newValue.trim().equalsIgnoreCase("Active"))
            	{
            		userrequesttypeTemp.setStatus(true);
            		
            	}
            	
            	if(oldValue != null && oldValue.trim().equalsIgnoreCase("Active") 
            			&& newValue != null && newValue.trim().equalsIgnoreCase("InActive"))
            	{
            		userrequesttypeTemp.setStatus(false);
            	}
            	session.update(userrequesttypeTemp);
            	tx.commit();
            	result = 1;
            	
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = 2;
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return result;
	}
	
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


	

	public static void main(String args[]) throws Exception
	{
		RequesttypeMasterImpl reinf = new RequesttypeMasterImpl();
		String emaiilId = "naveen.namburu1@yahoo.com";
		String roleName = reinf.getRoleNameByLoginId("naveen.namburu1@yahoo.com");
		
		List<Integer> requestIdList = reinf.getRequestListByRole(roleName, emaiilId);
		
		System.out.println("-roleName->"+roleName);
	}

	

}
