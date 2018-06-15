package com.reqman.daoimpl.query;

import java.math.BigInteger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.pojo.Users;
import com.reqman.vo.HomeVo;

public class Homerequestquery {
	
	private final String schemaName = HibernateUtil.schemaName;
	
	public BigInteger pendingrequest_onteammemberquery(String userName){
		
		Session session = null;
		Transaction tx = null;
		Users usersTemp = null;
		Object row = null;
		BigInteger totalpendingrequest = null;
		
		SQLQuery query = null;
		String sqlQuery = "";		
		StringBuffer sb = new StringBuffer();
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();				
			
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
	
			if(usersTemp != null){
			sb.append("select count(*) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request as r ");
			sb.append("where r.createdby='"+userName+"' and r.requeststatus=1");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalpendingrequest = (BigInteger) row;
			
			
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

		return totalpendingrequest;
	}
	
public BigInteger inprogressrequest_onteammemberquery(String userName){
		
		Session session = null;
		Transaction tx = null;
		Users usersTemp = null;
		Object row = null;
		BigInteger totalinprogressrequest = null;
		
		SQLQuery query = null;
		String sqlQuery = "";		
		StringBuffer sb = new StringBuffer();
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();				
			
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
	
			if(usersTemp != null){
			sb.append("select count(*) from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("request as r ");
			sb.append("where r.createdby='"+userName+"' and r.requeststatus in(2,4)");

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			row = query.uniqueResult();
			totalinprogressrequest = (BigInteger) row;
			
			
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

		return totalinprogressrequest;
	}
	
public BigInteger overduerequest_onteammemberquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totaloverduerequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus in(2,4) and (DATE_PART('day',CURRENT_TIMESTAMP - r.completiondate)>0)");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totaloverduerequest = (BigInteger) row;
		
		
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

	return totaloverduerequest;
}

public BigInteger delayrequest_onteammemberquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totaldelayrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus in(2,4)");
		sb.append("and	(((DATE_PART('day',CURRENT_DATE - r.datecreated)+0.01)*100)/ (DATE_PART('day', r.completiondate - r.datecreated)+0.01)) > r.completionpercentage");
		sb.append(" and (DATE_PART('day',CURRENT_TIMESTAMP - r.completiondate)<=0)");
		
		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totaldelayrequest = (BigInteger) row;
		
		
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

	return totaldelayrequest;
}


public BigInteger ontargetrequest_onteammemberquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalontargetrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus in(2,4)");
		sb.append("and	(((DATE_PART('day',CURRENT_DATE - r.datecreated)+0.01)*100)/ (DATE_PART('day', r.completiondate - r.datecreated)+0.01)) < r.completionpercentage");
		sb.append(" and (DATE_PART('day',CURRENT_TIMESTAMP - r.completiondate)<=0)");
		
		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalontargetrequest = (BigInteger) row;
		
		
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

	return totalontargetrequest;
}


public BigInteger totalrequest_onteammemberquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' ");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalrequest = (BigInteger) row;
		
		
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

	return totalrequest;
}



public BigInteger completedrequest_onteammemberquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalcompletedrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus=5");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalcompletedrequest = (BigInteger) row;
		
		
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

	return totalcompletedrequest;
}




public BigInteger holdrequest_onteammemberquery(String userName) {
	// TODO Auto-generated method stub

	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalholdrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus=7");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalholdrequest = (BigInteger) row;
		
		
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

	return totalholdrequest;
}

public BigInteger closerequest_onteammemberquery(String userName) {
	// TODO Auto-generated method stub

	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalcloserequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus=8");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalcloserequest = (BigInteger) row;
		
		
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

	return totalcloserequest;
}

public BigInteger cancelrequest_onteammemberquery(String userName) {
	// TODO Auto-generated method stub

	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalcancelrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus=6");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalcancelrequest = (BigInteger) row;
		
		
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

	return totalcancelrequest;
}


public BigInteger emptyduedate_request_onteammemberquery(String userName) {
	// TODO Auto-generated method stub

	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalacceptrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus = 2 and  completiondate  IS NULL");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalacceptrequest = (BigInteger) row;
		
		
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

	return totalacceptrequest;
}


public BigInteger return_request_onteammemberquery(String userName) {
	// TODO Auto-generated method stub
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalreturnrequest = null;
	
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.createdby='"+userName+"' and r.requeststatus = 3 ");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalreturnrequest = (BigInteger) row;
		
		
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

	return totalreturnrequest;

}





/**  end on teammember request*/




public BigInteger pendingrequest_onyouquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalpendingrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		userid = usersTemp.getId();
		
		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus=1");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalpendingrequest = (BigInteger) row;
		
		
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

	return totalpendingrequest;
}

public BigInteger inprogressrequest_onyouquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalinprogressrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
	session = HibernateUtil.getSession();
	tx = session.beginTransaction();				
	
	usersTemp = (Users) session.createCriteria(Users.class)
			.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

	userid = usersTemp.getId();
	
	if(usersTemp != null){
	sb.append("select count(*) from ");

	if (schemaName != null && !schemaName.trim().equals("")) {

		sb.append(schemaName);
		sb.append(".");
	}

	sb.append("request as r ");
	sb.append("where r.friendid in (select  uf.id from ");
	
	if (schemaName != null && !schemaName.trim().equals("")) {

		sb.append(schemaName);
		sb.append(".");
	}
	
	sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus in (2,4) ");

	sqlQuery = sb.toString();
	query = session.createSQLQuery(sqlQuery);
	row = query.uniqueResult();
	totalinprogressrequest = (BigInteger) row;
	
	
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

	return totalinprogressrequest;
}



public BigInteger overduerequest_onyouquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totaloverduerequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		userid = usersTemp.getId();
		
		if(usersTemp != null){
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus in(2,4) ");
		sb.append("and (DATE_PART('day',CURRENT_TIMESTAMP - r.completiondate)>0) ");
		
		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totaloverduerequest = (BigInteger) row;
		
		
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

	return totaloverduerequest;
}

public BigInteger delayrequest_onyourquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totaldelayrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try{
	session = HibernateUtil.getSession();
	tx = session.beginTransaction();				
	
	usersTemp = (Users) session.createCriteria(Users.class)
			.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
	

	userid = usersTemp.getId();
	

	if(usersTemp != null){
	sb.append("select count(*) from ");

	if (schemaName != null && !schemaName.trim().equals("")) {

		sb.append(schemaName);
		sb.append(".");
	}

	sb.append("request as r ");
	sb.append("where r.friendid in (select  uf.id from ");
	
	if (schemaName != null && !schemaName.trim().equals("")) {

		sb.append(schemaName);
		sb.append(".");
	}
	
	sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus in(2,4) ");
	sb.append("and (((DATE_PART('day',CURRENT_DATE - r.datecreated)+0.01)*100)/(DATE_PART('day', r.completiondate - r.datecreated)+0.01)) > r.completionpercentage ");
	sb.append(" and (DATE_PART('day',CURRENT_TIMESTAMP - r.completiondate)<=0)");
	sqlQuery = sb.toString();
	query = session.createSQLQuery(sqlQuery);
	row = query.uniqueResult();
	totaldelayrequest = (BigInteger) row;
	
	
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
	return totaldelayrequest;
}



public BigInteger ontargetrequest_onyouquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalontargetrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try{
	session = HibernateUtil.getSession();
	tx = session.beginTransaction();				
	
	usersTemp = (Users) session.createCriteria(Users.class)
			.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

	userid = usersTemp.getId();
	
	if(usersTemp != null){
	sb.append("select count(*) from ");

	if (schemaName != null && !schemaName.trim().equals("")) {

		sb.append(schemaName);
		sb.append(".");
	}

	sb.append("request as r ");
	sb.append("where r.friendid in (select  uf.id from ");
	
	if (schemaName != null && !schemaName.trim().equals("")) {

		sb.append(schemaName);
		sb.append(".");
	}
	
	sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus in(2,4) ");
	sb.append("and (((DATE_PART('day',CURRENT_DATE - r.datecreated)+0.01)*100)/(DATE_PART('day', r.completiondate - r.datecreated)+0.01)) < r.completionpercentage ");
	sb.append(" and (DATE_PART('day',CURRENT_TIMESTAMP - r.completiondate)<=0)");
	
	sqlQuery = sb.toString();
	query = session.createSQLQuery(sqlQuery);
	row = query.uniqueResult();
	totalontargetrequest = (BigInteger) row;
	
	
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
	return totalontargetrequest;
}




public BigInteger totalrequest_onyouquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		
		
		if(usersTemp != null){
			
			userid = usersTemp.getId();
			
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
		sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') ");

		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalrequest = (BigInteger) row;
		
		
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

	return totalrequest;
}

public BigInteger completedrequestrequest_onyouquery(String userName){
	
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalpendingrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
			userid = usersTemp.getId();
			
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus=5 ");


		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalpendingrequest = (BigInteger) row;
		
		
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

	return totalpendingrequest;
}



public BigInteger holdrequest_onyouquery(String userName) {
	// TODO Auto-generated method stub
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalholdrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
			userid = usersTemp.getId();
			
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus = 7 ");


		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalholdrequest = (BigInteger) row;
		
		
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

	return totalholdrequest;
}

public BigInteger closerequest_onyouquery(String userName) {
	// TODO Auto-generated method stub
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalcloserequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
			userid = usersTemp.getId();
			
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus = 8 ");


		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalcloserequest = (BigInteger) row;
		
		
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

	return totalcloserequest;
}

public BigInteger cancelrequest_onyouquery(String userName) {
	// TODO Auto-generated method stub
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalcancelrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
			userid = usersTemp.getId();
			
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus = 6  ");


		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalcancelrequest = (BigInteger) row;
		
		
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

	return totalcancelrequest;
}


public BigInteger emptyduedate_request_onyouquery(String userName) {
	// TODO Auto-generated method stub
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalcancelrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
			userid = usersTemp.getId();
			
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus = 2 and  completiondate  IS NULL ");


		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalcancelrequest = (BigInteger) row;
		
		
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

	return totalcancelrequest;
}

public BigInteger return_request_onyouquery(String userName) {
	// TODO Auto-generated method stub
	Session session = null;
	Transaction tx = null;
	Users usersTemp = null;
	Object row = null;
	BigInteger totalreturnrequest = null;
	Integer userid = null;
	SQLQuery query = null;
	String sqlQuery = "";		
	StringBuffer sb = new StringBuffer();
	
	try {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();				
		
		usersTemp = (Users) session.createCriteria(Users.class)
				.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

		if(usersTemp != null){
			userid = usersTemp.getId();
			
		sb.append("select count(*) from ");

		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}

		sb.append("request as r ");
sb.append("where r.friendid in (select  uf.id from ");
		
		if (schemaName != null && !schemaName.trim().equals("")) {

			sb.append(schemaName);
			sb.append(".");
		}
		
		sb.append("userfriendlist as uf where uf.friendid = '" + userid + "') and r.requeststatus = 3 ");


		sqlQuery = sb.toString();
		query = session.createSQLQuery(sqlQuery);
		row = query.uniqueResult();
		totalreturnrequest = (BigInteger) row;
		
		
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

	return totalreturnrequest;
}







}
