package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.AppAdminInterface;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.vo.AdminVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.requestNoteVo;

public class AppAdminImpl implements AppAdminInterface{

	@Override
	public List<AdminVo> getappadminDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<AdminVo> requestList = new ArrayList<AdminVo>();		
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Users users= null;
		AdminVo adminVo = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {

				@SuppressWarnings("unchecked")
				List<Users> usersList = (List<Users>) session
						.createCriteria(Users.class)					
						.list();
						
				
				
			   
				if (usersList != null && usersList .size() != 0) {
					for (Users usersDB : usersList) {
						
						
						adminVo = new AdminVo();						
					
						Hibernate.initialize(users);
						adminVo.setEmailid(usersDB.getEmailid()!= null ? usersDB.getEmailid().trim() : "");
						adminVo.setFirstname(usersDB.getFirstname());
						adminVo.setLastname(usersDB.getLastname());
						adminVo.setShortname(usersDB.getShortname());
						adminVo.setStatus(usersDB.getStatus());
						adminVo.setPassword(usersDB.getPassword());
						adminVo.setCreatedby(usersDB.getCreatedby());
						adminVo.setCreatedon(usersDB.getCreatedon()!= null ?  Dateconverter.convertDateToStringDDMMDDYYYY(usersDB.getCreatedon()) : "");
						adminVo.setHashkey(usersDB.getHashkey());
						requestList.add(adminVo);
					}
					
				}
				tx.commit();
			}
		} catch (Exception e) 
		{
			if(tx != null)
			{
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
	
	
	
	}


