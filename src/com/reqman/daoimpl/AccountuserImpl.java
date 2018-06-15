package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.AccountuserInterface;
import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.vo.AccountVo;
import com.reqman.vo.AccountuserVo;
import com.reqman.vo.UserroleVo;

public class AccountuserImpl implements AccountuserInterface {

	@Override
	public List<AccountuserVo> getaccountuserDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<AccountuserVo> requestList = new ArrayList<AccountuserVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Accountusers accountusers = null;
		AccountuserVo accountuserVo = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				@SuppressWarnings("unchecked")
				List<Accountusers> accountuserlist = (List<Accountusers>) session.createCriteria(Accountusers.class)
						.list();

				if (accountuserlist != null && accountuserlist.size() != 0) {

					String accountname = "";
					String username = "";
					String accountkey = "";
					for (Accountusers accountuserDB : accountuserlist) {
						accountname = "";
						username = "";
						accountkey = "";
						accountuserVo = new AccountuserVo();

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

						accountuserVo.setAccountuserid(accountuserDB.getId());
						accountuserVo.setAccountname(accountname);
						accountuserVo.setUsername(username);
						accountuserVo.setOrganizationkey(accountkey);
						requestList.add(accountuserVo);
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
	public int updateimagestatus(String userName, Boolean imagestatus) throws Exception {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction tx = null;
		Accountusers accountusers = null;
		Users users = null;
		Integer usersId = null;
		Boolean getimageStatus = null;
		Accountusers accountusersDB = null;
		Accountusers accountusersTemp = null;
		Integer accountuserId = null;
		int result = 0;

		try {

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim())).uniqueResult();

			usersId = users.getId();

			if (users != null && usersId != null) {

				accountusersDB = (Accountusers) session.createCriteria(Accountusers.class)
						.add(Restrictions.eq("users.id", usersId)).uniqueResult();
				accountuserId = accountusersDB.getId();
				getimageStatus = accountusersDB.getImagestatus();
				System.out.println(getimageStatus);

				if (accountusersDB != null && getimageStatus != null) {

					if (getimageStatus == true) {
						accountusersDB.setImagestatus(false);
					} else if (getimageStatus == false) {
						accountusersDB.setImagestatus(true);
					}
					session.update(accountusersDB);
					tx.commit();

					result = 1;
				}

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

		return result;
	}

	@Override
	public AccountuserVo getAccountuserById(String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		AccountuserVo accountuserVo = new AccountuserVo();
		Accountusers accountusersTemp = null;
		Integer userid = 0;
		Users usersTemp = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim())).uniqueResult();

			userid = usersTemp.getId();

			accountusersTemp = (Accountusers) session.createCriteria(Accountusers.class)
					.add(Restrictions.eq("users.id", userid)).uniqueResult();

			accountuserVo.setImageStatus(accountusersTemp.getImagestatus());
			accountuserVo.setAccountuserid(accountusersTemp.getId());

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null)
				session.close();
		}

		return accountuserVo;

	}


}
