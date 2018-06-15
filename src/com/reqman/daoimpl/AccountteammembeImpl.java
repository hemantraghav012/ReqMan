package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.AccountteammemberInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Users;
import com.reqman.vo.AccountteammemberVo;


public class AccountteammembeImpl implements AccountteammemberInterface {

	@Override
	public List<AccountteammemberVo> getaccountteammemberDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<AccountteammemberVo> requestList = new ArrayList<AccountteammemberVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Accountusers accountusers = null;
		AccountteammemberVo accountteammemberVo = null;
		Integer userid = null;
		Integer accountid = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {
			 userid = usersTemp.getId();
				
				accountusers = (Accountusers) session.createCriteria(Accountusers.class)
						.add(Restrictions.eq("users.id", userid)).uniqueResult();

				 accountid = accountusers.getAccount().getId();
				
				if(accountid != null){
				@SuppressWarnings("unchecked")
					List<Accountusers> accountuserlist = (List<Accountusers>) session.createCriteria(Accountusers.class)
							.add(Restrictions.eq("account.id", accountid)).list();

				if (accountuserlist != null && accountuserlist.size() != 0) {

					String accountname = "";
					String username = "";
					String accountkey = "";
					for (Accountusers accountuserDB : accountuserlist) {
						accountname = "";
						username = "";
						accountkey = "";
						accountteammemberVo = new AccountteammemberVo();

						Hibernate.initialize(accountusers);
						Hibernate.initialize(accountuserDB.getAccount());
						Hibernate.initialize(accountuserDB.getUsers());

						if (accountuserDB != null && accountuserDB.getAccount() != null) {
							accountname = accountuserDB.getAccount().getName() != null
									? accountuserDB.getAccount().getName() : "";
						}

						if (accountuserDB != null && accountuserDB.getAccount() != null) {
							accountkey = accountuserDB.getAccount().getOrganizationkey() != null
									? accountuserDB.getAccount().getOrganizationkey() : "";
						}
						if (accountuserDB != null && accountuserDB.getUsers() != null) {
							username = accountuserDB.getUsers().getEmailid() != null
									? accountuserDB.getUsers().getEmailid() : "";
						}

						if(userid != accountuserDB.getUsers().getId()){
						
						accountteammemberVo.setAccountteammemberid(accountuserDB.getId());
						accountteammemberVo.setAccountname(accountname);
						accountteammemberVo.setUsername(username);
						accountteammemberVo.setOrganizationkey(accountkey);
						accountteammemberVo.setOrganizationkey(accountkey);
						requestList.add(accountteammemberVo);
						}
					}
				}
				}
				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null) {
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
	public int getuseraccountById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Accountusers accountusers = null;
		Account account = null;
		AccountteammemberVo accountteammemberVo = null;
		Integer result = 0;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			accountusers = (Accountusers) session.createCriteria(Accountusers.class)
					.add(Restrictions.eq("id", id)).uniqueResult();

			if (accountusers != null) {
				
				System.out.println(accountusers.getAccount().getId());
				
				
				account = (Account) session.createCriteria(Account.class)
						.add(Restrictions.eq("organizationkey", "collabor8")).uniqueResult();
				
				if(account != null){
				accountusers.setAccount(account);
				
				session.save(accountusers);
				result = 1;
				}else{
					result = 2;
				}
				
			}
			tx.commit();
			
		
	} catch (Exception e) {
		if (tx != null) {
			tx.rollback();
		}

		e.printStackTrace();
		throw new Exception(e);
	} finally {
		if (session != null)
			session.close();
	}

	return result;
	}

}