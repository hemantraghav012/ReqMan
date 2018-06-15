package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.UserroleInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Request;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.vo.AccountVo;
import com.reqman.vo.UserroleVo;

public class UserroleImpl implements UserroleInterface{

	@Override
	public List<UserroleVo> getuserroleDetails(String userName) throws Exception {

		// TODO Auto-generated method stub
		List<UserroleVo> requestList = new ArrayList<UserroleVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Userroles userroles = null;
		UserroleVo userroleVo = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				@SuppressWarnings("unchecked")
				List<Userroles> userrolelist = (List<Userroles>) session.createCriteria(Userroles.class).list();

				if (userrolelist != null && userrolelist.size() != 0) {

					String rolename = "";
					String username = "";
					for (Userroles userroleDB : userrolelist) {
						rolename = "";
						username = "";
						userroleVo = new UserroleVo();

						Hibernate.initialize(userroles);
						Hibernate.initialize(userroleDB.getRoles());
						Hibernate.initialize(userroleDB.getUsers());

						if (userroleDB != null && userroleDB.getRoles() != null) {
							rolename = userroleDB.getRoles().getName() != null ? userroleDB.getRoles().getName() : "";
						}

						if (userroleDB != null && userroleDB.getUsers() != null) {
							username = userroleDB.getUsers().getEmailid() != null ? userroleDB.getUsers().getEmailid()
									: "";
						}

						userroleVo.setUserroleid(userroleDB.getId());
						userroleVo.setRolename(rolename);
						userroleVo.setUsername(username);

						requestList.add(userroleVo);
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
	public int updateUserrole(String oldValue, String newValue, Integer updateUserroleid) throws Exception {
		// TODO Auto-generated method stub
		Userroles userroleTemp = null;
		Session session = null;
		Transaction tx = null;
		Roles roles = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			userroleTemp = (Userroles) session.createCriteria(Userroles.class)
					.add(Restrictions.eq("id", updateUserroleid)).uniqueResult();
			Hibernate.initialize(roles);
			Hibernate.initialize(userroleTemp.getRoles());
			if (userroleTemp != null) {
				if (oldValue != null && newValue != null) {

					roles = (Roles) session.createCriteria(Roles.class).add(Restrictions.eq("name", newValue))
							.uniqueResult();
					userroleTemp.setRoles(roles);
				}
				session.update(userroleTemp);

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

	@Override
	public Map<String, Double> linechart(String userName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}