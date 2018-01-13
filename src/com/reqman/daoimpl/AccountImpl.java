package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.AccountInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Users;
import com.reqman.vo.AccountVo;


public class AccountImpl implements AccountInterface {

	

	@Override
	public List<AccountVo> getaccountDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<AccountVo> requestList = new ArrayList<AccountVo>();		
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Account account= null;
		AccountVo accountVo = null;
		
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
				List<Account> accountList = (List<Account>) session
						.createCriteria(Account.class)					
						.list();
						
				
				
			   
				if (accountList != null && accountList .size() != 0) {
					for (Account accountDB : accountList) {
						
						
						accountVo = new AccountVo();				
						Hibernate.initialize(account);
						
						accountVo.setId(accountDB.getId());
						accountVo.setCeatedby(accountDB.getCreatedby());
						accountVo.setDatecreated(accountDB.getDatecreated());
						accountVo.setName(accountDB.getName());
						if(accountDB.getStatus().equals(true))
            			{
							accountVo.setStatus("Active");
            			}
            			else
            			{
            				accountVo.setStatus("In Active");
            			}
						
						
							requestList.add(accountVo);
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
