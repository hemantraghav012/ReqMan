package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.requesttypeMasterInterface;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Requesttype;

import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.RequesttypeVo;

public class RequesttypeMasterImpl implements requesttypeMasterInterface {

	private final String schemaName = HibernateUtil.schemaName;
	// for save impl
	@Override
	public int saverequesttype(String name) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Requesttype requesttype = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			requesttype = (Requesttype) session.createCriteria(Requesttype.class)
					.add(Restrictions.eq("name", name.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (requesttype != null && requesttype.getStatus() != null
					&& requesttype.getStatus().booleanValue() == true) {
				result = 1;
			} else if (requesttype != null && requesttype.getStatus() != null
					&& requesttype.getStatus().booleanValue() == false) {
				result = 2;
			} else {
				requesttype = new Requesttype();
				requesttype.setName(name);
				requesttype.setCreatedby("SYSTEM");
				requesttype.setDatecreated(new Date());
				requesttype.setStatus(true);
				session.save(requesttype);
				tx.commit();
				result = 3;
			}
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result = 4;
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}

	// for save impl
	@Override
	public int saverequesttype(String requesttypeName, Boolean status, String emailId) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		int result = 0;
		Requesttype requesttype = null;
		Userrequesttype userrequesttype = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase()).uniqueResult();

			requesttype = (Requesttype) session.createCriteria(Requesttype.class)
					.add(Restrictions.eq("name", requesttypeName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (requesttype != null) {
				Userrequesttype userrequesttypeExist = null;
				if (requesttype.getUserrequesttypes() != null && requesttype.getUserrequesttypes().size() != 0) {
					for (Userrequesttype userrequesttypeDB : requesttype.getUserrequesttypes()) {
						if (userrequesttypeDB != null && userrequesttypeDB.getUsers() != null
								&& userrequesttypeDB.getUsers().getEmailid() != null) {
							if (userrequesttypeDB.getUsers().getEmailid().trim().equalsIgnoreCase(emailId)) {
								userrequesttypeExist = userrequesttypeDB;
								break;
							}
						}
					}
				}

				if (userrequesttypeExist != null && userrequesttypeExist.getStatus().equals(true)) {
					result = 1;
				} else if (userrequesttypeExist != null && userrequesttypeExist.getStatus().equals(false)) {
					result = 2;
				} else {
					userrequesttype = new Userrequesttype();
					userrequesttype.setRequesttype(requesttype);
					userrequesttype.setUsers(users);
					userrequesttype.setStatus(true);

					session.save(userrequesttype);

					result = 3;
					tx.commit();
				}
			} else {
				requesttype = new Requesttype();
				requesttype.setName(requesttypeName.trim());
				requesttype.setStatus(true);
				requesttype.setCreatedby(emailId);
				requesttype.setDatecreated(new Date());

				session.save(requesttype);

				userrequesttype = new Userrequesttype();
				userrequesttype.setRequesttype(requesttype);
				userrequesttype.setUsers(users);
				userrequesttype.setStatus(true);

				session.save(userrequesttype);

				result = 3;
				tx.commit();
			}

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result = 3;
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}

	// for display data in grid impl
	@SuppressWarnings("unchecked")
	public List<RequesttypeVo> getRequesttypeDetails(String emailId) throws Exception {
		// TODO Auto-generated method stub
		List<RequesttypeVo> requesttypeList = new ArrayList<RequesttypeVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		RequesttypeVo requesttypeVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserrequesttypes() != null && usersTemp.getUserrequesttypes().size() != 0) {
					for (Userrequesttype userrequesttypeDB : usersTemp.getUserrequesttypes()) {
						if (userrequesttypeDB != null && userrequesttypeDB.getRequesttype() != null
								&& userrequesttypeDB.getRequesttype().getStatus() == true) {
							requesttypeVo = new RequesttypeVo();
							// requesttypeVo.setSrNo(counter);
							requesttypeVo.setName(userrequesttypeDB.getRequesttype().getName());
							requesttypeVo.setUserRequesttypeId(userrequesttypeDB.getId());
							if (userrequesttypeDB.getStatus().equals(true)) {
								requesttypeVo.setStatus("Active");
							} else {
								requesttypeVo.setStatus("In-Active");
							}
							counter++;
							requesttypeList.add(requesttypeVo);
						}
					}
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return requesttypeList;
	}

	// for pie graph true status impl
	@Override
	public List<RequesttypeVo> getRequesttypeStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<RequesttypeVo> requesttypeList1 = new ArrayList<RequesttypeVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		RequesttypeVo requesttypeVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserrequesttypes() != null && usersTemp.getUserrequesttypes().size() != 0) {
					for (Userrequesttype userrequesttypeDB : usersTemp.getUserrequesttypes()) {
						if (userrequesttypeDB != null && userrequesttypeDB.getRequesttype() != null
								&& userrequesttypeDB.getRequesttype().getStatus() == true
								&& userrequesttypeDB.getStatus() == true) {
							requesttypeVo = new RequesttypeVo();
							// requesttypeVo.setSrNo(counter);
							requesttypeVo.setName(userrequesttypeDB.getRequesttype().getName());
							requesttypeVo.setUserRequesttypeId(userrequesttypeDB.getId());
							if (userrequesttypeDB.getStatus().equals(true)) {
								requesttypeVo.setStatus("Active");
							} else {
								requesttypeVo.setStatus("In-Active");
							}
							counter++;
							requesttypeList1.add(requesttypeVo);
						}
					}
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return requesttypeList1;
	}

	// for pie graph false status impl
	@Override
	public List<RequesttypeVo> getRequesttypefalseStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<RequesttypeVo> requesttypeList1 = new ArrayList<RequesttypeVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		RequesttypeVo requesttypeVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserrequesttypes() != null && usersTemp.getUserrequesttypes().size() != 0) {
					for (Userrequesttype userrequesttypeDB : usersTemp.getUserrequesttypes()) {
						if (userrequesttypeDB != null && userrequesttypeDB.getRequesttype() != null
								&& userrequesttypeDB.getRequesttype().getStatus() == true
								&& userrequesttypeDB.getStatus() == false) {
							requesttypeVo = new RequesttypeVo();
							// requesttypeVo.setSrNo(counter);
							requesttypeVo.setName(userrequesttypeDB.getRequesttype().getName());
							requesttypeVo.setUserRequesttypeId(userrequesttypeDB.getId());
							if (userrequesttypeDB.getStatus().equals(true)) {
								requesttypeVo.setStatus("Active");
							} else {
								requesttypeVo.setStatus("In-Active");
							}
							counter++;
							requesttypeList1.add(requesttypeVo);
						}
					}
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return requesttypeList1;
	}

	// for update status through grid impl
	@Override
	public int updateRequesttype(String oldValue, String newValue, Integer updaterequesttypeId) throws Exception {
		// TODO Auto-generated method stub

		Userrequesttype userrequesttypeTemp = null;
		Session session = null;
		Transaction tx = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			userrequesttypeTemp = (Userrequesttype) session.createCriteria(Userrequesttype.class)
					.add(Restrictions.eq("id", updaterequesttypeId)).uniqueResult();

			if (userrequesttypeTemp != null) {
				if (oldValue != null && oldValue.trim().equalsIgnoreCase("In-Active") && newValue != null
						&& newValue.trim().equalsIgnoreCase("Active")) {
					userrequesttypeTemp.setStatus(true);

				}

				if (oldValue != null && oldValue.trim().equalsIgnoreCase("Active") && newValue != null
						&& newValue.trim().equalsIgnoreCase("In-Active")) {
					userrequesttypeTemp.setStatus(false);
				}
				session.update(userrequesttypeTemp);
				tx.commit();
				result = 1;

			}
		} catch (Exception e) {
			e.printStackTrace();
			result = 2;
		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequesttypeVo> getallaccountrequesttype(String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<RequesttypeVo> AccounttypeList = new ArrayList<RequesttypeVo>();
		List<String> rows = null;
		RequesttypeVo requesttypeVo = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();
		String[] accountArr = {};
	
		Users users = null;
		Integer userId = null;
		Accountusers accountusers = null;
		String organizationkey = null;
		Userroles userroles = null;
		Integer roleid = null;
		try {

			

		/*	accountArr = userName.split("@");
			if (accountArr != null && accountArr.length > 1) {
				accountName = accountArr[1];
			}*/

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			
			if(userName != null){
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
				userId = users.getId();
				
				userroles = (Userroles) session.createCriteria(		Userroles.class)
						.add(Restrictions.eq("users.id", userId)).uniqueResult();
				roleid = userroles.getRoles().getId();
				
				
				accountusers = (Accountusers) session.createCriteria(Accountusers.class)
						.add(Restrictions.eq("users.id",userId)).uniqueResult();
				organizationkey = accountusers.getAccount().getOrganizationkey();
			
			}
			
			if(userName != null && organizationkey !=null && roleid == 2){
			
			sb.append("select t.name from ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("users as u, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userrequesttype as ut, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("requesttype as t,");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("accountusers as au,");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("account as a where u.id=ut.userid and  ut.requesttypeid=t.id  and");
			sb.append("  au.userid=u.id and a.id=au.accountid ");
			sb.append("  and a.organizationkey='" + organizationkey + "'");
			
			}
			
			else if(userName != null  && roleid == 1){
				
				sb.append("select t.name from ");
				if (schemaName != null && !schemaName.trim().equals("")) {

					sb.append(schemaName);
					sb.append(".");
				}
				sb.append("requesttype as t");
				
								
				}
			
			
			
			
			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (String requesttypename : rows) {
					requesttypeVo = new RequesttypeVo();
					requesttypeVo.setName(requesttypename);

					AccounttypeList.add(requesttypeVo);

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

		return AccounttypeList;
	}
}
