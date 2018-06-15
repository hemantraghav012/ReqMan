package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.SchedulejobInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Schdulejobs;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.SchedulejobVo;
import com.reqman.vo.requestNoteVo;



public class SchedulejobImpl implements SchedulejobInterface {

	@Override
	public int saveschedulejob(String jobname, Boolean status, String userName, String day, Integer date, Integer hour,
			Integer minute, String description) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		Schdulejobs schdulejobs = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			schdulejobs = (Schdulejobs) session.createCriteria(Schdulejobs.class)
					.add(Restrictions.eq("jobname", jobname.toLowerCase().trim()).ignoreCase()).uniqueResult();

			schdulejobs = new Schdulejobs();

			schdulejobs.setDay(day);
			schdulejobs.setDate(date);
			schdulejobs.setHour(hour);
			schdulejobs.setMinute(minute);
			schdulejobs.setStatus(true);
			schdulejobs.setCreatedby(userName);
			schdulejobs.setCreatedon(new Date());
			schdulejobs.setJobname(jobname);
			schdulejobs.setDescription(description);

			session.save(schdulejobs);
			tx.commit();
			result = 3;

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

	@Override
	public SchedulejobVo getscheduleById(Integer schedulejobid) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		SchedulejobVo schedulejobVo = new SchedulejobVo();
		Schdulejobs schedulejobs = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			schedulejobs = (Schdulejobs) session.createCriteria(Schdulejobs.class)
					.add(Restrictions.eq("id", Integer.valueOf(schedulejobid))).uniqueResult();

			if (schedulejobs != null) {
				if (schedulejobs.getDay() != null && !schedulejobs.getDay().isEmpty()) {
					schedulejobVo.setDay(schedulejobs.getDay());
				} else {
					schedulejobVo.setDay("null");
				}
				if (schedulejobs.getDate() != null) {
					schedulejobVo.setDate(schedulejobs.getDate());
				} else {
					schedulejobVo.setDate(0);

				}
				schedulejobVo.setHour(schedulejobs.getHour());
				schedulejobVo.setMinute(schedulejobs.getMinute());
				schedulejobVo.setJobname(schedulejobs.getJobname());

				schedulejobVo.setDescription(schedulejobs.getDescription());

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

		return schedulejobVo;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulejobVo> getschedulejobDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<SchedulejobVo> SchedulejobList = new ArrayList<SchedulejobVo>();

		Session session = null;
		Transaction tx = null;
		Users usersTemp = null;
		Schdulejobs Schedulejobs = null;
		SchedulejobVo schedulejobVo = null;

		try {

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				@SuppressWarnings("unchecked")
				List<Schdulejobs> schdulejobsPojoList = (List<Schdulejobs>) session.createCriteria(Schdulejobs.class)
						.list();

				if (schdulejobsPojoList != null && schdulejobsPojoList.size() != 0) {

					for (Schdulejobs schedulejobDB : schdulejobsPojoList) {

						schedulejobVo = new SchedulejobVo();

						schedulejobVo.setSchedulejobId(schedulejobDB.getId());

						if (schedulejobDB.getDay() != null && !schedulejobDB.getDay().isEmpty()) {
							schedulejobVo.setDay(schedulejobDB.getDay());
						} else {
							schedulejobVo.setDay("null");
						}
						if (schedulejobDB.getDate() != null) {
							schedulejobVo.setDate(schedulejobDB.getDate());
						} else {
							schedulejobVo.setDate(0);

						}

						schedulejobVo.setHour(schedulejobDB.getHour());
						schedulejobVo.setMinute(schedulejobDB.getMinute());
						schedulejobVo.setJobname(schedulejobDB.getJobname());
						schedulejobVo.setDescription(schedulejobDB.getDescription());
						SchedulejobList.add(schedulejobVo);

					}
					tx.commit();

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

		return SchedulejobList;
	}

	@Override
	public int updatescheduleById(Integer schedulejobid, Boolean status, String description, String jobname, String day,
			Integer hour, Integer minute, Integer date, String userName) throws Exception {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction tx = null;
		Schdulejobs schedulejobs = null;
		int result = 0;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			schedulejobs = (Schdulejobs) session.createCriteria(Schdulejobs.class)
					.add(Restrictions.eq("id", Integer.valueOf(schedulejobid))).uniqueResult();

			if (schedulejobs != null) {

				schedulejobs.setDay(day);
				schedulejobs.setHour(hour);
				schedulejobs.setMinute(minute);
				schedulejobs.setDescription(description);
				schedulejobs.setJobname(jobname);
				schedulejobs.setDate(date);
				session.update(schedulejobs);

				tx.commit();
				result = 1;

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

		return result;

	}
	
}
