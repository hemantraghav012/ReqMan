package com.reqman.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


import com.reqman.common.HibernateUtilH;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.pojo.Users;

public class UserDetailsImpl implements UserDetailsInterface {

/*	@Override
	public int validate(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
*/	
	
	public int validate(String userName, String password) throws Exception
    {
        Session session = null;
        SessionFactory hsf = null;
        Transaction tx = null;
        Users users = null;
        int result = 0;
        try {
            if(userName != null && !userName.trim().equals(""))
            {
            	
            	//hsf = HibernateSessionFactory.getSessionFactory();
                //session = hsf.openSession();
            	session = HibernateUtilH.getSession();
                tx = session.beginTransaction();
                users = (Users)session.createCriteria(Users.class)
                		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
                		.add(Restrictions.eq("password", password.toLowerCase().trim()).ignoreCase())
                		.uniqueResult();
                
                if(users != null){
                	
                	tx.commit();
                	result = 1;
                }
                else
                {
                	result = 2;
                }
		        
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

}
