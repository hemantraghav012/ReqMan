package com.reqman.daoimpl;


import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.CategoryMasterInterface;
import com.reqman.pojo.Category;


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

	

	

}
