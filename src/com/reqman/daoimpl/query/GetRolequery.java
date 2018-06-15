package com.reqman.daoimpl.query;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.GetrolequeryInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Users;
import com.reqman.util.RequestConstants;




public class GetRolequery implements GetrolequeryInterface{
	
	private final String schemaName = HibernateUtil.schemaName;

	@SuppressWarnings("unchecked")
	public String getRoleNameByLoginId(String loginId) throws Exception {
		Session session = null;
		Transaction tx = null;
		String roleName = "";
		List<Object[]> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		String roleId = "";
		StringBuffer sb = new StringBuffer();
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			sb.append("select r.id as roleId,r.name as roleName from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("roles as r,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("userroles as ur");
			sb.append(" where u.id=ur.userid and ur.roleid=r.id and r.status=true and u.emailid='"
					+ loginId.toLowerCase() + "'");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (Object[] row : rows) {
					roleId = row[0].toString();
					roleName = row[1].toString();
				}
			}

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

		return roleName;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRequestListByRole(String roleName, String loginId) throws Exception {
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
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			accountArr = loginId.split("@");
			/*if (accountArr != null && accountArr.length > 1) {
				accountName = accountArr[1];
			}*/

			if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)) {
				sb.append("select rq.id,uf.friendid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("request as rq,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("roles as r,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("userroles as ur, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("accountusers as au, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("account as a ");

				sb.append("where u.id=uf.userid and uf.id=rq.friendid ");
				sb.append(
						"and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid and r.name = '"
								+ roleName + "' ");
				sb.append("and u.emailid='" + loginId.toLowerCase() + "'");
			} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {
				sb.append("select rq.id,uf.friendid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("request as rq,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("roles as r,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("userroles as ur, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("accountusers as au, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("account as a ");

				sb.append("where u.id=uf.userid and uf.id=rq.friendid ");
				sb.append(
						"and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid and r.name = '"
								+ roleName + "' ");
				sb.append("and u.emailid='" + loginId.toLowerCase() + "'");
			} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {
				sb.append("select rq.id,uf.friendid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("request as rq,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("roles as r,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("userroles as ur, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("accountusers as au, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("account as a ");

				sb.append("where u.id=uf.userid and uf.id=rq.friendid ");
				sb.append(
						"and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid and r.name = '"
								+ roleName + "' ");
				sb.append(" and u.emailid='" + loginId.toLowerCase() + "'");
			} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {
				sb.append("select rq.id,uf.friendid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("request as rq,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("roles as r,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("userroles as ur, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("accountusers as au, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("account as a ");

				sb.append("where u.id=uf.userid and uf.id=rq.friendid ");
				sb.append(
						"and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid and r.name = '"
								+ roleName + "' ");
				sb.append(" and u.emailid='" + loginId.toLowerCase() + "'");
			} else {
				sb.append("");

			}

			if (sb.length() == 0) {
				return requestIdList;
			}

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (Object[] row : rows) {
					requestId = row[0] != null ? (Integer) row[0] : 0;
					friendId = row[1] != null ? (Integer) row[1] : 0;
					requestIdList.add(requestId);
				}
			}

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

		return requestIdList;
	}

	// for admin or account admin show request grid
	@SuppressWarnings("unchecked")
	public List<Integer> getAdminRequestListByRole(String roleName, String loginId) throws Exception {
		Session session = null;
		Transaction tx = null;
		List<Object[]> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		Integer requestId = 0;
		Integer friendId = 0;
	    Users users = null;
	    Accountusers accountusers = null;
	    Account account = null;
	    
		StringBuffer sb = new StringBuffer();
		String[] accountArr = {};
		String organizationkey = "";
		Integer userId = null;
		Integer accountId = null;
		List<Integer> requestIdList = new ArrayList<Integer>();
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			/*accountArr = loginId.split("@");
			if (accountArr != null && accountArr.length > 1) {
				accountName = accountArr[1];
			}*/
			
			if(loginId != null && roleName != null){
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", loginId.toLowerCase().trim()).ignoreCase()).uniqueResult();
			userId = users.getId();
			
			accountusers = (Accountusers) session.createCriteria(Accountusers.class)
					.add(Restrictions.eq("users.id",userId)).uniqueResult();
			organizationkey = accountusers.getAccount().getOrganizationkey();
			
			/*if(accountusers != null){
				account = (Account) session.createCriteria(Account.class)
						.add(Restrictions.eq("id",accountId)).uniqueResult();
				organizationkey = account.getOrganizationkey();
				
			}*/
			
			
			}
			if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {
				sb.append("select rq.id,uf.friendid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("request as rq,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("roles as r,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("userroles as ur, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("accountusers as au, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("account as a ");

				sb.append("where u.id=uf.userid and uf.id=rq.friendid ");
				sb.append(
						"and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid and r.name = '"
								+ roleName + "' ");
				sb.append("and u.emailid='" + loginId.toLowerCase().trim() + "'");
			} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {
				sb.append("select rq.id,uf.friendid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("request as rq,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("roles as r,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("userroles as ur, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("accountusers as au, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("account as a ");

				sb.append("where u.id=uf.userid and ");
				sb.append(
						"uf.id=rq.friendid and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid ");
				sb.append(" and a.organizationkey ='" + organizationkey + "'");
			} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {
				sb.append("select rq.id,uf.friendid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("request as rq,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("roles as r, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userroles as ur ");

				sb.append(" where uf.id=rq.friendid and u.id=ur.userid and ur.roleid=r.id ");
			} else {
				sb.append("");

			}

			if (sb.length() == 0) {
				return requestIdList;
			}

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (Object[] row : rows) {
					requestId = row[0] != null ? (Integer) row[0] : 0;
					friendId = row[1] != null ? (Integer) row[1] : 0;
					requestIdList.add(requestId);
				}
			}

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

		return requestIdList;
	}
	
	// weekly get all users
	@SuppressWarnings("null")
	public List<String> AllUser() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> emailList = new ArrayList<String>();
		Session session = null;
		Transaction tx = null;
		String emailid = null;
		StringBuffer sb = new StringBuffer();
		List<String> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		int result = 0;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			sb.append("select Distinct u.emailid from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u where u.status=true");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);

			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (String row : rows) {
					emailid = (String) (row != null ? row : "");

					emailList.add(emailid);

				}
			}

			tx.commit();
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

		return emailList;
	}

	@SuppressWarnings("null")
	public List<String> AllAccountAdmin() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> emailList = new ArrayList<String>();
		Session session = null;
		Transaction tx = null;
		String emailid = null;
		StringBuffer sb = new StringBuffer();
		List<String> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		int result = 0;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			sb.append("select distinct u.emailid from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u, ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("userfriendlist as uf,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userroles as ur,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("request as r ");
			sb.append("where  r.status=true and ur.roleid=2 and  u.id=ur.userid and u.status=true");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);

			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (String row : rows) {
					emailid = (String) (row != null ? row : "");

					emailList.add(emailid);

				}
			}

			tx.commit();
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

		return emailList;
	}
	
	
	// account user Admin

	public Integer getAccountideByLoginId(String loginId) throws Exception {
		Session session = null;
		Transaction tx = null;

		Integer accountid = 0;
		List<Object[]> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		String roleId = "";
		StringBuffer sb = new StringBuffer();
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			sb.append("select u.emailid,ac.accountid from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("roles as r,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("accountusers as ac, ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("account as acco,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("userroles as ur ");
			sb.append(" where u.id=ur.userid and ur.roleid=r.id and ac.userid=u.id and ");
			sb.append(" ac.accountid=acco.id and r.status=true and u.emailid='" + loginId.toLowerCase().trim() + "'");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (Object[] row : rows) {
					roleId = row[0].toString();
					accountid = (Integer) row[1];
				}
			}

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

		return accountid;
	}
	
	// getuser for daily send email

	public List<String> getusersincompleatedrequest() throws Exception {
		Session session = null;
		Transaction tx = null;
		ArrayList<String> emailList = new ArrayList<String>();
		List<String> getusers = null;
		List<String> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		String emailid = "";
		StringBuffer sb = new StringBuffer();
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			sb.append("select Distinct u.emailid from ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u ");
			sb.append("where u.status=true");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (String row : rows) {
					emailid = (String) (row != null ? row : "");

					emailList.add(emailid);
					System.out.println("jj" + emailList);
					System.out.println(emailid);
				}
			}
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

		return emailList;
	}
	
	
}
