package com.reqman.daoimpl.query;

import java.math.BigInteger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.reqman.common.HibernateUtil;

public class CountUsersquery {
	private final String schemaName = HibernateUtil.schemaName;

	//total number of account users
	
	
	public BigInteger getAdminusercount(String loginId) throws Exception {
		Session session = null;
		Transaction tx = null;
		Object row = null;

		BigInteger totalrequest = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();
		String[] accountArr = {};
		String accountName = "";
		try {

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			accountArr = loginId.split("@");
			if (accountArr != null && accountArr.length > 1) {
				accountName = accountArr[1];
			}

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			sb.append("select count(*) from ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("users as u,");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("accountusers as au,");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("account as a where  au.userid=u.id and a.id=au.accountid and a.status=true and a.name='"
					+ accountName + "'");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			System.out.println(row);
			totalrequest = (BigInteger) row;
			System.out.println(totalrequest);
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

		return totalrequest;
	}
	
	
	public BigInteger getAlluserscount(String loginId) throws Exception {
		Session session = null;
		Transaction tx = null;
		Object row = null;

		BigInteger totalrequest = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			sb.append("select count(u.createdon) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u where u.status=true");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			System.out.println(row);
			totalrequest = (BigInteger) row;
			System.out.println(totalrequest);
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

		return totalrequest;
	}
	
	

}
