package com.reqman.daoimpl.query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


import com.reqman.common.HibernateUtil;
import com.reqman.common.HibernateUtilH;
import com.reqman.dao.NewRequestqueryInterface;
import com.reqman.pojo.Request;
import com.reqman.pojo.Users;
import com.reqman.vo.DatasummaryVo;
import com.reqman.vo.dailyDuedatewisesendRequestVo;

public class NewRequestquery implements NewRequestqueryInterface{
	private final String schemaName = HibernateUtil.schemaName;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigInteger> piechart(String userName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, BigInteger> requestmap = new HashMap<String, BigInteger>();
		Session session = null;
		Transaction tx = null;
		Users usersTemp1 = null;
		List<Object[]> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		BigInteger countfriendemail = null;
		String friendemail = null;
		StringBuffer sb = new StringBuffer();
		int result = 0;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp1 = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp1 != null) {
				sb.append(" select (select u1.emailid from  ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u1,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf1  where ");
				sb.append(
						" u1.id=uf1.friendid and u1.status=true and uf1.status=true and uf1.id=r.friendid), count(r.id) from ");
				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("request as r");
				sb.append(" where r.status=true and r.requeststatus in (2,4,5) and r.friendid in ");
				sb.append(" (select uf.id from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf where u.id=uf.userid ");
				sb.append(" and u.status=true and uf.status=true and u.emailid='" + userName.toLowerCase().trim()
						+ "') group by r.friendid");

				sqlQuery = sb.toString();
				query = session.createSQLQuery(sqlQuery);
				rows = query.list();

				if (rows != null && rows.size() != 0) {
					String feiend = "";
					for (Object[] row : rows) {
						countfriendemail = (BigInteger) row[1];
						friendemail = (String) row[0];
						requestmap.put(friendemail, countfriendemail);
					}
				}
			}
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
		
		return requestmap;
	}
	
	public Map<String, Double> barchart(String userName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Double> requestbarmap = new HashMap<String, Double>();
		Session session = null;
		Transaction tx = null;
		Users usersTemp1 = null;
		Request request = null;
		int result = 0;
		List<Object[]> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		BigInteger percentageDB = null;
		BigInteger countDB = null;
		Boolean grade = null;
		Double currentpercentage = null;
		String friendemail = null;
		StringBuffer sb = new StringBuffer();
		Users usersTemp = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp1 = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp1 != null) {
				sb.append("select (select u1.emailid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u1, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf1 where ");
				sb.append(" u1.id=uf1.friendid and u1.status=true and uf1.status=true and uf1.id=r.friendid),");
				sb.append("(avg((r.completionpercentage*100)/(100 /(DATE_PART('day',r.completiondate-r.acceptdate)+1)");
				sb.append(" *(DATE_PART('day',CURRENT_TIMESTAMP-r.acceptdate)+1)))) as average");
				sb.append(" from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("request as r  ");
				sb.append(" where r.status=true and r.requeststatus in (2,4,5)");
				sb.append(" and r.friendid in");
				sb.append(" (select uf.id from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf where u.id=uf.userid ");
				sb.append("and u.status=true and uf.status=true and u.emailid='" + userName.toLowerCase().trim()
						+ "') group by r.friendid");

				sqlQuery = sb.toString();
				query = session.createSQLQuery(sqlQuery);
				rows = query.list();

				if (rows != null && rows.size() != 0) {

					for (Object[] row : rows) {
						friendemail = (String) row[0];
						currentpercentage = (Double) row[1];
						usersTemp = (Users) session.createCriteria(Users.class)
								.add(Restrictions.eq("emailid", friendemail.toLowerCase().trim()).ignoreCase())
								.uniqueResult();
						if (usersTemp != null && usersTemp.getFirstname() != null
								&& !usersTemp.getFirstname().equals("")) {
							friendemail = usersTemp.getFirstname();
						}
						requestbarmap.put(friendemail, currentpercentage);

					}
				}
			}
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

		return requestbarmap;
	}
	
	
	public Map<String, BigDecimal> barchartforaverage(String userName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, BigDecimal> requestbarmap = new HashMap<String, BigDecimal>();
		Session session = null;
		Transaction tx = null;
		Users usersTemp1 = null;
		Request request = null;
		int result = 0;
		List<Object[]> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		BigInteger percentageDB = null;
		BigInteger countDB = null;
		Boolean grade = null;
		BigDecimal currentpercentage = null;
		String friendemail = null;
		StringBuffer sb = new StringBuffer();
		Users usersTemp = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp1 = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp1 != null) {
				sb.append("select (select u1.emailid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u1,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf1 where ");
				sb.append(" u1.id=uf1.friendid and u1.status=true and uf1.status=true and uf1.id=r.friendid),");
				sb.append(" avg (r.rating) as average ");
				sb.append("from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("request as r");
				sb.append(
						" where r.status=true and  DATE_PART('year', CURRENT_DATE)=DATE_PART('year', r.datecreated)and r.rating IS NOT NULL and r.friendid in ");
				sb.append("(select uf.id from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u,");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf where u.id=uf.userid ");
				sb.append("and u.status=true and uf.status=true and u.emailid='" + userName.toLowerCase().trim()
						+ "') group by r.friendid ");

				sqlQuery = sb.toString();
				query = session.createSQLQuery(sqlQuery);
				rows = query.list();

				if (rows != null && rows.size() != 0) {

					for (Object[] row : rows) {
						friendemail = (String) row[0];
						currentpercentage = (BigDecimal) row[1];
						
						if(currentpercentage != null){
						currentpercentage = currentpercentage.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						}
						
						if(friendemail != null){
						usersTemp = (Users) session.createCriteria(Users.class)
								.add(Restrictions.eq("emailid", friendemail.toLowerCase().trim()).ignoreCase())
								.uniqueResult();
						
						if (usersTemp != null && usersTemp.getFirstname() != null
								&& !usersTemp.getFirstname().equals("")) {
							
							if(usersTemp != null && usersTemp.getFirstname() != null){
							friendemail = usersTemp.getFirstname();
							}else{
								friendemail = usersTemp.getEmailid();
							}
						}
						}
						requestbarmap.put(friendemail, currentpercentage);

					}
				}
			}
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

		return requestbarmap;
	}
	
	@Override
	public Map<String, Double> barchartforcompleteddate(String userName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Double> requestbarmap = new HashMap<String, Double>();
		Session session = null;
		Transaction tx = null;
		Users usersTemp1 = null;
		Request request = null;
		int result = 0;
		List<Object[]> rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		Double currentpercentage = null;
		String friendemail = null;
		StringBuffer sb = new StringBuffer();
		Users usersTemp = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp1 = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp1 != null) {
				sb.append("select (select u1.emailid from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("users as u1, ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("userfriendlist as uf1 where");
				sb.append(" u1.id=uf1.friendid and u1.status=true and uf1.status=true and uf1.id=r.friendid),");
				sb.append(
						"avg ((DATE_PART('day',r.completiondate-r.acceptdate)+1)*100/(DATE_PART('day',r.teammembercompletiondate-r.acceptdate)+1)) as average ");
				sb.append(" from ");

				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}

				sb.append("request as r  where r.status=true and ");
				sb.append("r.teammembercompletiondate IS NOT NULL and requeststatus in(5,8) and r.friendid in ");
				sb.append("(select uf.id from ");
				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("users as u,");
				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("userfriendlist as uf where u.id=uf.userid ");
				sb.append("and u.status=true and uf.status=true and u.emailid='" + userName.toLowerCase().trim()
						+ "') group by r.friendid ");

				sqlQuery = sb.toString();
				query = session.createSQLQuery(sqlQuery);
				rows = query.list();

				if (rows != null && rows.size() != 0) {

					for (Object[] row : rows) {
						System.out.println((String) row[0]);
						friendemail = (String) row[0];
						currentpercentage = (Double) row[1];
						System.out.println(friendemail);
						usersTemp = (Users) session.createCriteria(Users.class)
								.add(Restrictions.eq("emailid", friendemail.toLowerCase().trim()).ignoreCase())
								.uniqueResult();
						if (usersTemp != null && usersTemp.getFirstname() != null
								&& !usersTemp.getFirstname().equals("")) {
							friendemail = usersTemp.getFirstname();
						}
						requestbarmap.put(friendemail, currentpercentage);

					}
				}
			}
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

		return requestbarmap;

	}

	public BigInteger gettotalRequestByLoginId(String loginId) throws Exception {
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
			sb.append("select count(*) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request where createdby ='" + loginId.toLowerCase() + "'and status=true");

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
	
	public BigDecimal getTotalaveragebyrequester(String loginId) throws Exception {
		BigDecimal totalaverage = null;
		Session session = null;
		Transaction tx = null;
		Object rows = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			sb.append("select avg(rating) from  ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request where createdby ='" + loginId.toLowerCase() + "'and status=true");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.uniqueResult();
			totalaverage = (BigDecimal) rows;
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
	
	public BigInteger getAdminRequestsummaryt(String loginId) throws Exception {
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

			sb.append("account as a where u.id=uf.userid and ");

			sb.append(
					"uf.id=rq.friendid and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid ");
			sb.append(" and a.name='" + accountName.toLowerCase() + "'");

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
	
	public BigDecimal getAdminRequestAverger(String loginId) throws Exception {
		Session session = null;
		Transaction tx = null;
		Object row = null;

		BigDecimal totalrequest = null;
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
			sb.append("select avg(rating) from ");

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

			sb.append("roles as r,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userroles as ur,");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("accountusers as au, ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("account as a where u.id=uf.userid and ");

			sb.append(
					"uf.id=rq.friendid and u.id=ur.userid and ur.roleid=r.id and au.userid=u.id and a.id=au.accountid ");
			sb.append(" and a.name='" + accountName.toLowerCase() + "'");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalrequest = (BigDecimal) row;
			totalrequest = totalrequest.setScale(2, BigDecimal.ROUND_HALF_EVEN);

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
	
	public List<dailyDuedatewisesendRequestVo> getduedaterequest(String loginId) throws Exception {
		List<dailyDuedatewisesendRequestVo> requestList = new ArrayList<dailyDuedatewisesendRequestVo>();
		Session session = null;
		Transaction tx = null;
		List<Object[]> rows = null;
		dailyDuedatewisesendRequestVo dilyduedatewisesendrequestVo = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();

		try {

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			sb.append("select  r.title,u.emailid,r.completionpercentage from ");

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

			sb.append("request as r where u.id=uf.friendid and uf.id=r.friendid and r.status=true and");
			sb.append(
					" r.requeststatus in(1,2,3,4,7) and DATE_PART('day',r.completiondate-current_timestamp)<=2 and r.createdby='"
							+ loginId.toLowerCase().trim() + "'");
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

	public List<DatasummaryVo> gettotalrequest(Date startDate, Date endDate) throws Exception {
		List<DatasummaryVo> requestList = new ArrayList<DatasummaryVo>();
		Session session = null;
		Transaction tx = null;
		List<Object[]> rows = null;
		DatasummaryVo datasummaryVo = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();

		try {

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			sb.append(
					"select count(r.datecreated)as datecreated,count(r.acceptdate) as acceptdate,count(r.completiondate) as completiondate,");
			sb.append(" count(r.datemodified) as datemodified,count(r.approveddate) as approveddate from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request as r ");
			// + "where r.datecreated BETWEEN '"+startDate+"' AND '"+endDate+"'
			// ";

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (Object[] row : rows) {
					datasummaryVo = new DatasummaryVo();
					datasummaryVo.setRequest_created(row[0].toString());
					datasummaryVo.setRequest_accepted(row[1].toString());
					datasummaryVo.setRequest_completed(row[2].toString());
					datasummaryVo.setRequest_modified(row[3].toString());
					datasummaryVo.setRequest_closed(row[4].toString());

					requestList.add(datasummaryVo);

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

	// for count requestnotes status false

	public Integer countRequestnoteStatus(int requestid) throws Exception {
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

			sb.append("select count(requeststatus) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("requestnotes where requeststatus=false and requestid='" + requestid + "'");

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
	
	
	public Integer expectedcompletionpercentage(int requestid) throws Exception {
		Integer requestList = 0;
		Session session = null;
		Transaction tx = null;
		Object row = null;
		Double totalrequest = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			// select (DATE_PART('day',CURRENT_DATE - acceptdate)*100)/
			// DATE_PART('day', completiondate - acceptdate)
			// from reqman.request where createdby='sktanwar@gmail.com' and
			// requeststatus=5
			sb.append(
					"select ((DATE_PART('day',CURRENT_DATE - datecreated)+0.001)*100)/ (DATE_PART('day', completiondate - datecreated)+0.001) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request where id='" + requestid + "'");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalrequest = (Double) row;
			if(totalrequest != null && totalrequest != 0.0){
				requestList = totalrequest.intValue();
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

	
	public Integer performance(int requestid) throws Exception {
		Integer requestList = 0;
		Session session = null;
		Transaction tx = null;
		Object row = null;		
		Double totalrequest = null;
		BigDecimal performance=null;
		SQLQuery query = null;
		String sqlQuery = "";
		Object row1 = null;
		SQLQuery query1 = null;
		String sqlQuery1 = "";
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			// select (DATE_PART('day',CURRENT_DATE - acceptdate)*100)/
			// DATE_PART('day', completiondate - acceptdate)
			// from reqman.request where createdby='sktanwar@gmail.com' and
			// requeststatus=5
			sb.append(
					"select (completionpercentage + 0.0001)/ ((DATE_PART('day',CURRENT_DATE - datecreated)+0.0001) / (DATE_PART('day', completiondate - datecreated)+0.0001)) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request where id='" + requestid + "'");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalrequest = (Double) row;
			
		
			
			
			/*
			sb1.append("select (completionpercentage + 0.01)*100/("+totalrequest+" + 0.01) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb1.append(schemaName);
				sb1.append(".");
			}

			sb1.append("request where id='" + requestid + "'");

			sqlQuery1 = sb1.toString();
			query1 = session.createSQLQuery(sqlQuery1);
			row1 = query1.uniqueResult();
			performance = (BigDecimal) row1;
			
			*/
			
			if(totalrequest != null){
				requestList = totalrequest.intValue();
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
	
	
	
	public Integer attachmentstatustrue(int requestid) throws Exception {
		Integer requestList = null;
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

			sb.append("select count(attachmentstatus) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append(
					"request where attachmentstatus=true and attachmentstatus is not null and id='" + requestid + "'");

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

	public static void main(String[] args) throws Exception{
		NewRequestquery nr =new  NewRequestquery();
		nr.performance(1);
		System.out.println("Hello World");
		
	}
	
}
