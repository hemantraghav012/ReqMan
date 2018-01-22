package com.reqman.daoimpl;

import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.SubscribeInterface;
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
        	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", user))
					.add(Restrictions.eq("status", true))
					.uniqueResult();
			if(users != null)
			{
				customerpayment = getCustomerpayment(rootObject, users);
				
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
	
	private Customerpayment getCustomerpayment(RootObject rootObject, Users users) throws Exception
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
		
		
		
		if(rootObject != null && rootObject.getData() != null && rootObject.getData().getSubscription() != null)
		{
			customerpayment = new Customerpayment();
			customerpayment.setUsers(users);
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
			
		}
		
		return customerpayment;
	}
}
