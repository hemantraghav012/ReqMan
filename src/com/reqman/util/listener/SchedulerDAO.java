package com.reqman.util.listener;

    import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.quartz.Scheduler;

import com.reqman.common.HibernateUtil;
import com.reqman.pojo.Category;
import com.reqman.pojo.Schdulejobs;
import com.reqman.util.CommonConstants;

	
	public class SchedulerDAO {
	
	@SuppressWarnings("unchecked")
	public List<Schdulejobs> findAll() {

		Session session = null;
		Transaction tx = null;
		List<Schdulejobs> objValue = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			objValue = (List<Schdulejobs>) session.createCriteria(Schdulejobs.class).list();

		} catch (Exception re) {

			re.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			// throw re;
		} finally {
			if (session != null)
				session.close();
		}
		return objValue;

	}
		
		
		
		
	

	}
