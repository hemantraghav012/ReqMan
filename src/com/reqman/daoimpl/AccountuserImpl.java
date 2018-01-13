package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.AccountuserInterface;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.vo.AccountuserVo;
import com.reqman.vo.UserroleVo;

public class AccountuserImpl implements AccountuserInterface {

	@Override
	public List<AccountuserVo> getaccountuserDetails(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<AccountuserVo> requestList = new ArrayList<AccountuserVo>();		
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Accountusers accountusers= null;
		AccountuserVo accountuserVo = null;
		
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
				List<Accountusers> accountuserlist = (List<Accountusers>) session
						.createCriteria(Accountusers.class)					
						.list();
			   
				if (accountuserlist != null && accountuserlist .size() != 0) {
					
				String	accountname = "";
				String	username = "";
					for (Accountusers accountuserDB : accountuserlist) {			
						accountname="";
						username="";
						accountuserVo = new AccountuserVo();			
						
						Hibernate.initialize(accountusers);
						Hibernate.initialize(accountuserDB.getAccount());
						Hibernate.initialize(accountuserDB.getUsers());
						
						
						if(accountuserDB != null && accountuserDB.getAccount() != null)
						{
							accountname = accountuserDB.getAccount().getName() != null 
									? accountuserDB.getAccount().getName()  : "" ;										
						}
						
						if(accountuserDB != null && accountuserDB.getUsers() != null)
						{
							username = accountuserDB.getUsers().getEmailid() != null 
									? accountuserDB.getUsers().getEmailid()  : "" ;										
						}
												
						accountuserVo.setAccountuserid(accountuserDB.getId());
						accountuserVo.setAccountname(accountname);
						accountuserVo.setUsername(username);
						
						
							requestList.add(accountuserVo);
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

}
