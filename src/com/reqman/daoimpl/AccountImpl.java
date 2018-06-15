package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.AccountInterface;
import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Users;
import com.reqman.vo.AccountVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.UserupdateVo;


public class AccountImpl implements AccountInterface {

	@Override
	public List<AccountVo> getaccountDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<AccountVo> requestList = new ArrayList<AccountVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Account account = null;
		AccountVo accountVo = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				@SuppressWarnings("unchecked")
				List<Account> accountList = (List<Account>) session.createCriteria(Account.class).list();

				if (accountList != null && accountList.size() != 0) {
					for (Account accountDB : accountList) {

						accountVo = new AccountVo();
						Hibernate.initialize(account);

						accountVo.setId(accountDB.getId());
						accountVo.setCeatedby(accountDB.getCreatedby());
						accountVo.setOrganizationkey(accountDB.getOrganizationkey());
						accountVo.setDatecreated(accountDB.getDatecreated());
						accountVo.setName(accountDB.getName());
						if (accountDB.getStatus().equals(true)) {
							accountVo.setStatus("Active");
						} else {
							accountVo.setStatus("In Active");
						}

						requestList.add(accountVo);
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
	public AccountVo getAccountById(Integer id, String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		AccountVo accountVo = new AccountVo();
		Account accountTemp = null;
		Integer accountid = 0;
		GetRolequery gr = new GetRolequery();

		try {
			accountid = gr.getAccountideByLoginId(userName);

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			accountTemp = (Account) session.createCriteria(Account.class).add(Restrictions.eq("id", accountid))
					.uniqueResult();

			accountVo.setName(accountTemp.getName());
			accountVo.setOrganizationkey(accountTemp.getOrganizationkey());
			accountVo.setId(accountTemp.getId());
			accountVo.setImagename(accountTemp.getImagename());

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

		return accountVo;

	}

	
	@Override
	public int updatelogo(String accountname, boolean status, Integer id, String userName, UploadedFile logo,
			String imagename) throws Exception {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction tx = null;
		AccountVo accountVo = new AccountVo();
		Account account = null;
		Integer accountid = 0;
		GetRolequery gr = new GetRolequery();

		int result = 0;

		try {
			accountid = gr.getAccountideByLoginId(userName);
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			account = (Account) session.createCriteria(Account.class).add(Restrictions.eq("id", accountid))
					.uniqueResult();

			if (account != null) {
				// account.setStatus(status);
				if (logo.getFileName() != null && !logo.getFileName().isEmpty()) {
					account.setPhoto(logo.getContents());
					account.setImagename(logo.getFileName());
				}
				
				account.setName(accountname);
			
			}
			session.update(account);

			tx.commit();
			result = 1;

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			result = 2;
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}
	
	
	@Override
	public byte[] getImageDetails(String userName) throws Exception {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction tx = null;
		Account account = null;
		Integer accountid = 0;
		GetRolequery gr = new GetRolequery();

		byte[] imageBytes = null;
		try {
			accountid = gr.getAccountideByLoginId(userName);
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			account = (Account) session.createCriteria(Account.class).add(Restrictions.eq("id", accountid))
					.uniqueResult();
			if (account != null) {
				if (account.getPhoto() != null) {
					imageBytes = account.getPhoto();
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null)
				session.close();
		}

		return imageBytes;

	}


}
