package com.reqman.daoimpl;

import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.SubscribeInterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Customerpayment;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.util.RequestConstants;
import com.reqman.vo.zoho.subscription.hostpage.RootObject;

public class SubscribeImpl implements SubscribeInterface
{

	public int saveSubscription(RootObject rootObject, String user) throws Exception
	{
		int result = 0;
		
		Session session = null;
        Transaction tx = null;
        Users users = null;
        Customerpayment customerpayment = null;
        boolean roleTest = false;
        Roles roles = null;
        Userroles userrolesTemp = null;
        try
        {
			customerpayment = getCustomerpayment(rootObject);
        	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", user))
					.add(Restrictions.eq("status", true))
					.uniqueResult();
			
			if(users != null && customerpayment != null 
					&& customerpayment.getPlancode().trim().equalsIgnoreCase(RequestConstants.PLAN_INDIVIDUAL))
			{
				setIndividualPlanDetails(userrolesTemp, users, customerpayment, session, tx, roleTest, roles);
			}
			else if(users != null && customerpayment != null 
					&& customerpayment.getPlancode().trim().equalsIgnoreCase(RequestConstants.PLAN_ORGANIZATIONAL))
			{
				setOrganizationalPlanDetails(userrolesTemp, users, customerpayment, session, tx, roleTest, roles);
			}
			
            
	 		result = 1;
		}
		catch(Exception e)
		{
			if(tx != null)
			{
	            tx.rollback();
			}
            e.printStackTrace();
            result = 2;
            throw new Exception(e);
		}
		finally
		{
			if(session != null)
			{
	            session.close();
			}
		}
		
		return result;
	}
	
	private void setOrganizationalPlanDetails(Userroles userrolesTemp, Users users, Customerpayment customerpayment,
			Session session, Transaction tx, boolean roleTest, Roles roles) throws Exception
	{

		String organizationName = "";
		
		String emailId = "";
		String emailArr[] = {};
		SQLQuery query = null;
		String sqlQuery = "";
		int updatedCount = 0;
		
		if(users != null && users.getEmailid() != null)
		{
			emailId = users.getEmailid();
			
			if(emailId != null)
			{
				emailArr = emailId.split("@");
				
				if(emailArr != null && emailArr.length > 1)
				{
					organizationName = emailArr[1].trim();
				}
			}
		}
		
		Account account = (Account)session.createCriteria(Account.class)
				.add(Restrictions.eq("name", organizationName.toLowerCase()).ignoreCase())
				.uniqueResult();
		if(account != null && account.getId() != 0){
			sqlQuery ="update reqman.userroles set roleid="+RequestConstants.REQUESTOR_ROLE_ID+" where id in (select ur.id from reqman.users as u, "
					+ "reqman.userroles as ur, reqman.account as a where u.accountid=a.id and u.id=ur.userid and "
					+ "a.name = '"+account.getName().trim()+"' and ur.roleid="+RequestConstants.TEAM_MEMBER_ID+")";
            query = session.createSQLQuery(sqlQuery);
            updatedCount = query.executeUpdate();
            System.out.println("--updatedCount-->"+updatedCount);
		}
		
		
 		tx.commit();

	
	}

	
	private void setIndividualPlanDetails(Userroles userrolesTemp, Users users, Customerpayment customerpayment,
			Session session, Transaction tx, boolean roleTest, Roles roles) throws Exception
	{

		customerpayment.setUsers(users);
		session.save(customerpayment);
		
		if(users.getUserroleses() != null && users.getUserroleses().size() != 0)
		{
			Hibernate.initialize(users.getUserroleses());
			
			for(Userroles userroles : users.getUserroleses())
			{
				if(userroles != null && userroles.getRoles() != null 
						&& (userroles.getRoles().getName().trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)
						|| userroles.getRoles().getName().trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)
						|| userroles.getRoles().getName().trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)))
				{
					roleTest = true;
				}
				else if(userroles != null && userroles.getRoles() != null 
						&& userroles.getRoles().getName().trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER))
				{
					roles=(Roles)session.createCriteria(Roles.class)
                    		.add(Restrictions.eq("id", RequestConstants.REQUESTOR_ROLE_ID))
                    		.uniqueResult();
					
					userroles.setRoles(roles);
					session.update(userroles);
					
				}
				else
				{
					roles=(Roles)session.createCriteria(Roles.class)
                    		.add(Restrictions.eq("id", RequestConstants.REQUESTOR_ROLE_ID))
                    		.uniqueResult();
					userrolesTemp = new Userroles();
					userrolesTemp.setRoles(roles);
					userrolesTemp.setUsers(users);
					session.save(userrolesTemp);
				}
			}
		}
		else
		{
			roles=(Roles)session.createCriteria(Roles.class)
            		.add(Restrictions.eq("id", RequestConstants.REQUESTOR_ROLE_ID))
            		.uniqueResult();
			userrolesTemp = new Userroles();
			userrolesTemp.setRoles(roles);
			userrolesTemp.setUsers(users);
			session.save(userrolesTemp);
		}
		
 		tx.commit();

	
	}
	
	private Customerpayment getCustomerpayment(RootObject rootObject) throws Exception
	{
		Customerpayment customerpayment = null;
		String productName = "";
		String productId = "";
		
		String subscriptionName = "";
		String subscriptionId = "";
		
		String customerId = "";
		
		Date currenttermstartat = null;
		Date currenttermend = null;
		
		String startDate = null;
		String endDate = null;
		
		String planName = null;
		String planCode = null;
		
		
		
		if(rootObject != null && rootObject.getData() != null && rootObject.getData().getSubscription() != null)
		{
			customerpayment = new Customerpayment();
			productName = rootObject.getData().getSubscription()
					.getProductName() != null ? rootObject.getData()
					.getSubscription().getProductName() : "";
					
			productId = rootObject.getData().getSubscription()
					.getProductId() != null ? rootObject.getData()
					.getSubscription().getProductId() : "0";
					
			subscriptionName = rootObject.getData().getSubscription()
					.getName() != null ? rootObject.getData()
					.getSubscription().getName() : "";
					
			subscriptionId = rootObject.getData().getSubscription()
					.getSubscriptionId() != null ? rootObject.getData()
					.getSubscription().getSubscriptionId() : "0";
					
			if(rootObject.getData().getSubscription().getCustomer() != null)
			{
				customerId = rootObject.getData().getSubscription()
						.getCustomer().getCustomerId() != null ? rootObject
						.getData().getSubscription().getCustomer()
						.getCustomerId() : "0";
			}
			
			if(rootObject.getData().getSubscription().getPlan() != null)
			{
				planName = rootObject.getData().getSubscription()
						.getPlan().getName() != null ? rootObject
						.getData().getSubscription().getPlan()
						.getName() : "0";
			}
			
			if(rootObject.getData().getSubscription().getPlan() != null)
			{
				planCode = rootObject.getData().getSubscription()
						.getPlan().getPlanCode() != null ? rootObject
						.getData().getSubscription().getPlan()
						.getPlanCode() : "0";
			}
			
			
			if(rootObject.getData().getSubscription().getCustomer() != null)
			{
				customerId = rootObject.getData().getSubscription()
						.getCustomer().getCustomerId() != null ? rootObject
						.getData().getSubscription().getCustomer()
						.getCustomerId() : "0";
			}
			
			
			startDate = rootObject.getData().getSubscription()
					.getCurrentTermStartsAt() != null ? rootObject.getData()
					.getSubscription().getCurrentTermStartsAt() : "";
					
			endDate = rootObject.getData().getSubscription()
					.getCurrentTermEndsAt() != null ? rootObject.getData()
					.getSubscription().getCurrentTermEndsAt() : "";
					
			currenttermstartat = Dateconverter.getMinTimeByDate(Dateconverter.covertStringToDateYYYYMMDD(startDate));
			
			currenttermend = Dateconverter.getMaxTimeByDate(Dateconverter.covertStringToDateYYYYMMDD(endDate));
			
			customerpayment.setProductname(productName);
			try
			{
				customerpayment.setProductid(Long.parseLong(productId));
				customerpayment.setSubscriptionid(Long.parseLong(subscriptionId));
				customerpayment.setCustomerid(Long.parseLong(customerId));
			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
			}
			
			customerpayment.setSubscriptionname(subscriptionName);
			customerpayment.setCurrenttermstartat(currenttermstartat);
			customerpayment.setCurrenttermend(currenttermend);
			customerpayment.setStatus(true);
			customerpayment.setCreatedon(new Date());
			customerpayment.setPlancode(planCode);
			customerpayment.setPlanname(planName);
			
		}
		
		return customerpayment;
	}
}
