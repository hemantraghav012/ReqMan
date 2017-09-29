package com.reqman.daoimpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.common.HibernateUtil;
import com.reqman.dao.CategoryMasterInterface;
import com.reqman.pojo.Category;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;


public class CategoryMasterImpl implements CategoryMasterInterface{


	public int savecategory( String name )
			throws Exception {
		// TODO Auto-generated method stub
		   Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	       Category category = null;
	        int result = 0;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	           category = (Category)session.createCriteria(Category.class)
	            		.add(Restrictions.eq("name", name.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            if(category != null && category.getStatus() != null && category.getStatus().booleanValue() == true){
	            	result = 1;
	            }
	            else if(category != null && category.getStatus() != null && category.getStatus().booleanValue() == false){
	            	result = 2;
	            }
	            else
	            {
	            	 category=new Category();
	            	category.setName(name);	            
	            	category.setCreatedby("SYSTEM");	            
	            	category.setDatecreated(new Date());
	            	category.setStatus(true);
	            	session.save(category);
	            	tx.commit();
	            	result = 3;
	            }
	        } catch (Exception e) {
	        	if(tx != null)
	            tx.rollback();
	            e.printStackTrace();
	            result = 4;
	            throw new Exception(e);
	        } finally {
	        	if(session != null)
	            session.close();
	        }
			
			return result;
	 	}
	
	
	public int savecategory(String categoryName, Boolean status, String emailId) throws Exception
	{

        Session session = null;
        SessionFactory hsf = null;
        Transaction tx = null;
        Users users = null;
        int result = 0;
        Category category = null;
        Usercategory usercategory = null;
        try {
        	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            users = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            category = (Category)session.createCriteria(Category.class)
            		.add(Restrictions.eq("name", categoryName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(category != null)
            {
            	Usercategory usercategoryExist = null;
            	if(category.getUsercategories() != null && category.getUsercategories().size() != 0){
            		for(Usercategory usercategoryDB : category.getUsercategories())
            		{
            			if(usercategoryDB != null && usercategoryDB.getUsers() != null && usercategoryDB.getUsers().getEmailid() != null){
            				if(usercategoryDB.getUsers().getEmailid().trim().equalsIgnoreCase(emailId))
            				{
            					usercategoryExist = usercategoryDB;
            					break;
            				}
            			}
            		}
            	}
            	
            	if(usercategoryExist != null && usercategoryExist.getStatus().equals(true))
            	{
            		result = 1;
            	}
            	else if(usercategoryExist != null && usercategoryExist.getStatus().equals(false))
            	{
            		result = 2;
            	}
            	else
            	{
            		usercategory = new Usercategory();
                	usercategory.setCategory(category);
                	usercategory.setUsers(users);
                	usercategory.setStatus(true);
                	
                	session.save(usercategory);
                	
                	result = 3;
                	tx.commit();
            	}
            }
            else
            {
            	category = new Category();
            	category.setName(categoryName.trim());
            	category.setStatus(true);
            	category.setCreatedby(emailId);
            	category.setDatecreated(new Date());
            	
            	session.save(category);
            	
            	usercategory = new Usercategory();
            	usercategory.setCategory(category);
            	usercategory.setUsers(users);
            	usercategory.setStatus(true);
            	
            	session.save(usercategory);
            	
            	result = 3;
            	tx.commit();
            }
            
        } catch (Exception e) {
        	if(tx != null)
            tx.rollback();
            e.printStackTrace();
            result = 3;
            throw new Exception(e);
        } finally {
        	if(session != null)
            session.close();
        }
		
		return result;
 	
	}

	@SuppressWarnings("unchecked")
	public List<CategoryVo> getCategoryDetails(String emailId) throws Exception
	{
		List<CategoryVo> categoryList = new ArrayList<CategoryVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    CategoryVo categoryVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUsercategories() != null && usersTemp.getUsercategories().size() != 0)
            	{
            		for(Usercategory usercategoryDB : usersTemp.getUsercategories())
            		{
            			if(usercategoryDB != null && usercategoryDB.getCategory() != null 
            					&& usercategoryDB.getCategory().getStatus() == true)
            			{
            				categoryVo = new CategoryVo();
                			//categoryVo.setSrNo(counter);
                			categoryVo.setName(usercategoryDB.getCategory().getName());
                			categoryVo.setUserCategoryId(usercategoryDB.getId());
                			if(usercategoryDB.getStatus().equals(true))
                			{
                				categoryVo.setStatus("Active");
                			}
                			else
                			{
                				categoryVo.setStatus("InActive");
                			}
                			counter++;
                			categoryList.add(categoryVo);
            			}
            		}
            	}
            	tx.commit();
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return categoryList;
	}
	
	
	
	public CategoryVo getUserCategoryById(String categoryId) throws Exception
	{
	    Session session = null;
	    Transaction tx = null;
	    CategoryVo categoryVo = new CategoryVo();
	    Usercategory usercategory = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usercategory = (Usercategory)
            			session.createCriteria(Usercategory.class)
            			.add(Restrictions.eq("id", Integer.valueOf(categoryId)))
            			.uniqueResult();
            
            int counter = 1;
            if(usercategory != null){
            	//categoryVo.setSrNo(counter);
    			categoryVo.setName(usercategory.getCategory().getName());
    			categoryVo.setUserCategoryId(usercategory.getId());
    			if(usercategory.getStatus() == true)
    			{
    				categoryVo.setStatus("Active");
    			}
    			else
    			{
    				categoryVo.setStatus("InActive");
    			}
    			
    			tx.commit();
            }
 		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(tx != null){
				tx.rollback();
			}
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return categoryVo;
	
	}
	
	public int updateUserCategoryById(String categoryId, boolean status) throws Exception
	{

	    Session session = null;
	    Transaction tx = null;
	    Usercategory usercategory = null;
	    int result = 0;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usercategory = (Usercategory)
            			session.createCriteria(Usercategory.class)
            			.add(Restrictions.eq("id", Integer.valueOf(categoryId)))
            			.uniqueResult();
            
            if(usercategory != null){
            	usercategory.setStatus(status);
            	session.update(usercategory);
    			tx.commit();
    			result = 1;
            }
 		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(tx != null){
				tx.rollback();
			}
			result = 2;
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return result;
	
	
	}


	
	@Override
	public List<CategoryVo> getCategoryStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<CategoryVo> categoryList1 = new ArrayList<CategoryVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    CategoryVo categoryVo = null;	
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null  ){   

            
            	if(usersTemp.getUsercategories() != null && usersTemp.getUsercategories().size() != 0 )
            	{
            		for(Usercategory usercategoryDB : usersTemp.getUsercategories())
            		{
            			if(usercategoryDB != null && usercategoryDB.getCategory() != null 
            					&& usercategoryDB.getCategory().getStatus() == true  && usercategoryDB.getStatus() == true)
            			{
            				categoryVo = new CategoryVo();                			
                			categoryVo.setName(usercategoryDB.getCategory().getName());
                			categoryVo.setUserCategoryId(usercategoryDB.getId());           			
                			if(usercategoryDB.getStatus().equals(true))
                			{
                				categoryVo.setStatus("Active");
                			}
                			else
                			{
                				categoryVo.setStatus("InActive");
                			}
                			
                			categoryList1.add(categoryVo);
            			}
            		}
            	}
            	tx.commit();
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return categoryList1;
	}


	@Override
	public List<CategoryVo> getCategoryStatusfalse(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<CategoryVo> categoryList2 = new ArrayList<CategoryVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    CategoryVo categoryVo = null;	
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null  ){   

            
            	if(usersTemp.getUsercategories() != null && usersTemp.getUsercategories().size() != 0 )
            	{
            		for(Usercategory usercategoryDB : usersTemp.getUsercategories())
            		{
            			if(usercategoryDB != null && usercategoryDB.getCategory() != null 
            					&& usercategoryDB.getCategory().getStatus() == true  && usercategoryDB.getStatus() == false)
            			{
            				categoryVo = new CategoryVo();                			
                			categoryVo.setName(usercategoryDB.getCategory().getName());
                			categoryVo.setUserCategoryId(usercategoryDB.getId());           			
                			if(usercategoryDB.getStatus().equals(true))
                			{
                				categoryVo.setStatus("Active");
                			}
                			else
                			{
                				categoryVo.setStatus("InActive");
                			}
                			
                			categoryList2.add(categoryVo);
            			}
            		}
            	}
            	tx.commit();
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
        	if(session != null)
            session.close();
	    }

		return categoryList2;

	
	}
	

}
