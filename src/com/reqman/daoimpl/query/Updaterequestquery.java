package com.reqman.daoimpl.query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.pojo.Users;
import com.reqman.vo.dailyDuedatewisesendRequestVo;

public class Updaterequestquery {

	private final String schemaName = HibernateUtil.schemaName;
	
	public BigInteger gettotalRequestreceive(String loginId) throws Exception 
	{
		Session session = null;
		Transaction tx = null;
		Users usersTemp = null;
		Object row = null;
		BigInteger totalrequest = null;
		SQLQuery query = null;
		String sqlQuery = "";
		String roleId = "";
		Integer userid = 0;
		StringBuffer sb = new StringBuffer();
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", loginId.toLowerCase().trim()).ignoreCase()).uniqueResult();
			userid = usersTemp.getId();
			sb.append("select count(*) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request as r ");
			sb.append("where friendid in(select  uf.id from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("userfriendlist as uf where uf.friendid = '" + userid + "')");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalrequest = (BigInteger) row;

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
	
	public BigDecimal getTotalaveragereceive(String loginId) throws Exception {
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Object row = null;
		BigDecimal totalaverage = null;
		SQLQuery query = null;
		String sqlQuery = "";
		String roleId = "";
		Integer userid = 0;
		StringBuffer sb = new StringBuffer();
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", loginId.toLowerCase().trim()).ignoreCase()).uniqueResult();
			userid = usersTemp.getId();

			sb.append("select avg(rating) as totalaverage from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request as r ");
			sb.append("where friendid in(select  uf.id from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("userfriendlist as uf where uf.friendid ='" + userid + "')");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalaverage = (BigDecimal) row;
			totalaverage = totalaverage.setScale(2, BigDecimal.ROUND_HALF_EVEN);

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

		return totalaverage;
	}
	
	public List<dailyDuedatewisesendRequestVo> getduedaterequestonteammember(String loginId) throws Exception {
		List<dailyDuedatewisesendRequestVo> requestList = new ArrayList<dailyDuedatewisesendRequestVo>();
		Session session = null;
		Transaction tx = null;
		List<Object[]> rows = null;
		dailyDuedatewisesendRequestVo dilyduedatewisesendrequestVo = null;
		SQLQuery query = null;
		String sqlQuery = "";
		Users usersTemp = null;
		Integer userid = 0;
		StringBuffer sb = new StringBuffer();
		try {

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", loginId.toLowerCase().trim()).ignoreCase()).uniqueResult();
			userid = usersTemp.getId();

			sb.append("select  r.title,r.createdby,r.completionpercentage from ");

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

			sb.append("request as r ");
			sb.append("where u.id=uf.friendid and uf.id=r.friendid and r.status=true and");
			sb.append(" r.requeststatus in(1,2,3,4,7) and DATE_PART('day',r.completiondate-current_timestamp)<=2 and ");
			sb.append("uf. friendid in(select  uf.id from ");
			sb.append("userfriendlist as uf where uf.friendid ='" + userid + "')");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (Object[] row : rows) {
					dilyduedatewisesendrequestVo = new dailyDuedatewisesendRequestVo();
					dilyduedatewisesendrequestVo.setTitle(row[0].toString());
					dilyduedatewisesendrequestVo.setTeammemberemailid(row[1].toString());
					dilyduedatewisesendrequestVo.setCompletionpercentage(row[2].toString());

					requestList.add(dilyduedatewisesendrequestVo);

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

		return requestList;

	}

	public Integer countRequestnoteStatus(int requestid) {
		// TODO Auto-generated method stub
		Integer requestList = 0;
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

			sb.append("select count(teammemberstatus) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("requestnotes where teammemberstatus=false and requestid='" + requestid + "'");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalrequest = (BigInteger) row;
			requestList = totalrequest.intValue();

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

		return requestList;
	}
	
	
}