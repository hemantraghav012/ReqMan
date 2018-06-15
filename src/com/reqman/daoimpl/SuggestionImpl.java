package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.SuggestionInterface;
import com.reqman.pojo.Category;
import com.reqman.pojo.Request;
import com.reqman.pojo.Suggestion;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Users;
import com.reqman.util.CommonConstants;
import com.reqman.util.Dateconverter;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.SuggestionVo;

public class SuggestionImpl implements SuggestionInterface {

	@Override
	public int savesuggestion(String message, Boolean status,String messagetype, String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Suggestion suggestion = null;
		Users users = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (users != null) {
				suggestion = new Suggestion();
				suggestion.setMessage(message);
				suggestion.setCreatedby(userName);
				suggestion.setDatecreated(new Date());
				suggestion.setStatus(true);
				suggestion.setRequeststatus(1);
				suggestion.setMessagetype(messagetype);
				session.save(suggestion);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SuggestionVo> getsuggestionDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<SuggestionVo> suggestionList = new ArrayList<SuggestionVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		SuggestionVo suggestionVo = null;
		// Suggestion suggestion= null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				// Suggestion suggestionDB = new Suggestion();

				List<Suggestion> suggestionlist = (List<Suggestion>) session.createCriteria(Suggestion.class)
						.add(Restrictions.eq("createdby", userName.toLowerCase().trim()).ignoreCase()).list();

				for (Suggestion suggestion : suggestionlist) {

					suggestionVo = new SuggestionVo();
					suggestionVo.setMessage(suggestion.getMessage() != null ? suggestion.getMessage() : "");
					suggestionVo.setId(suggestion.getId());
					suggestionVo.setMessagetype(suggestion.getMessagetype());
					suggestionVo.setDatecreated( Dateconverter.convertDateToStringDDMMDDYYYY(suggestion.getDatecreated()));
					suggestionVo.setCreatedby(suggestion.getCreatedby());
					
					if(suggestion.getRequeststatus() == CommonConstants.Open ){
						suggestionVo.setRequeststatus("Open");
					}
					else if(suggestion.getRequeststatus() == CommonConstants.In_progress){
						suggestionVo.setRequeststatus("In-progress");
					}
					else if(suggestion.getRequeststatus() == CommonConstants.Close){
						suggestionVo.setRequeststatus("Close");
					}
					
					suggestionVo.setActionowner(suggestion.getActionowner());
					
					if(suggestion.getStatus().booleanValue() == true){
						suggestionVo.setStatus("Active");
					}
					else{
						suggestionVo.setStatus("In-Active");
					}
					
					suggestionList.add(suggestionVo);

				}
			}

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return suggestionList;
	}

	@Override
	public SuggestionVo getsuggestionById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = null;
		Transaction tx = null;
		SuggestionVo suggestionVo = new SuggestionVo();
		Suggestion suggestion = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			suggestion = (Suggestion) session.createCriteria(Suggestion.class)
					     .add(Restrictions.eq("id", Integer.valueOf(id))).uniqueResult();
			
			Hibernate.initialize(suggestion);
			if (suggestion != null) {
				
				suggestionVo.setId(suggestion.getId());
				suggestionVo.setMessage(suggestion.getMessage());
				suggestionVo.setActionowner(suggestion.getActionowner());
				suggestionVo.setAdminremarks(suggestion.getAdminremarks());
				suggestionVo.setCreatedby(suggestion.getCreatedby());
				suggestionVo.setDatecreated( Dateconverter.convertDateToStringDDMMDDYYYY(suggestion.getDatecreated()));
				suggestionVo.setMessagetype(suggestion.getMessagetype());
				
				if(suggestion.getRequeststatus() == CommonConstants.Open ){
					suggestionVo.setRequeststatus("Open");
				}
				else if(suggestion.getRequeststatus() == CommonConstants.In_progress){
					suggestionVo.setRequeststatus("In-progress");
				}
				else if(suggestion.getRequeststatus() == CommonConstants.Close){
					suggestionVo.setRequeststatus("Close");
				}
				
				suggestionVo.setActionowner(suggestion.getActionowner());
				
				if(suggestion.getStatus().booleanValue() == true){
					suggestionVo.setStatus("Active");
				}
				else{
					suggestionVo.setStatus("In-Active");
				}
				
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

		return suggestionVo;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SuggestionVo> getallsuggestionDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<SuggestionVo> suggestionList = new ArrayList<SuggestionVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		SuggestionVo suggestionVo = null;
		// Suggestion suggestion= null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();		

	

				// Suggestion suggestionDB = new Suggestion();

				
		
				List<Suggestion> suggestionlist = (List<Suggestion>) session.createCriteria(Suggestion.class)
						.list();

				for (Suggestion suggestion : suggestionlist) {

					suggestionVo = new SuggestionVo();
					suggestionVo.setMessage(suggestion.getMessage() != null ? suggestion.getMessage() : "");
					suggestionVo.setId(suggestion.getId());
					suggestionVo.setMessagetype(suggestion.getMessagetype());
					suggestionVo.setDatecreated( Dateconverter.convertDateToStringDDMMDDYYYY(suggestion.getDatecreated()));
					suggestionVo.setCreatedby(suggestion.getCreatedby());
					
					if(suggestion.getRequeststatus() == CommonConstants.Open ){
						suggestionVo.setRequeststatus("Open");
					}
					else if(suggestion.getRequeststatus() == CommonConstants.In_progress){
						suggestionVo.setRequeststatus("In-progress");
					}
					else if(suggestion.getRequeststatus() == CommonConstants.Close){
						suggestionVo.setRequeststatus("Close");
					}
					suggestionVo.setAdminremarks(suggestion.getAdminremarks());
					suggestionVo.setActionowner(suggestion.getActionowner());
					
					if(suggestion.getStatus().booleanValue() == true){
						suggestionVo.setStatus("Active");
					}
					else{
						suggestionVo.setStatus("In-Active");
					}
					
					suggestionList.add(suggestionVo);

				}
		

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return suggestionList;
	}

	@Override
	public int updateSuggestionById(Integer id, String requeststatus, String actionowner, String adminremarks)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		SuggestionVo suggestionVo = new SuggestionVo();
		Suggestion suggestion = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			suggestion = (Suggestion) session.createCriteria(Suggestion.class)
					     .add(Restrictions.eq("id", Integer.valueOf(id))).uniqueResult();
			if(suggestion != null){
				
				suggestion.setActionowner(actionowner);
				suggestion.setAdminremarks(adminremarks);
				
				if(requeststatus.equalsIgnoreCase("Open")){					
					suggestion.setRequeststatus(CommonConstants.Open);
				}else if(requeststatus.equalsIgnoreCase("In-progress")){
					suggestion.setRequeststatus(CommonConstants.In_progress);
				}else if(requeststatus.equalsIgnoreCase("Close")){
					suggestion.setRequeststatus(CommonConstants.Close);
				}
				
				
				session.update(suggestion);
				
				result = 1;
			}else{
				result = 2;
			}
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}
		
	

}
