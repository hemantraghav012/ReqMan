package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.RoleMasterInterface;
import com.reqman.pojo.Category;
import com.reqman.pojo.Request;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.RoleVo;
import com.reqman.vo.requestNoteVo;

public class RoleMasterImpl implements RoleMasterInterface {

	@Override
	public List<RoleVo> getroleDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<RoleVo> roleList = new ArrayList<RoleVo>();
		
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	    RoleVo roleVo = null;
	    Roles roles = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
           
            Criteria crit = session.createCriteria(Roles.class);
						
			List<Roles> rolesPojoList = (List<Roles>)crit.list();
            	
            		for(Roles rolesDB : rolesPojoList)
            		{
            			roleVo=new RoleVo();
            			roleVo.setId(rolesDB.getId());
            			roleVo.setName(rolesDB.getName());
            			roleVo.setDatecreated(rolesDB.getDatecreated());
            			roleVo.setCreatedby(rolesDB.getCreatedby());
            			roleVo.setStatus(rolesDB.getStatus());
            			roleList.add(roleVo);
            			}
            		
            	
            	tx.commit();
            
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

		return roleList;
	}

	@Override
	public int getsaverole(Integer id, String name) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	      Roles roles = null;
	        int result = 0;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            
	           
	            	roles=new Roles();
	            	roles=new Roles();
	            	roles.setId(id);
	            	roles.setName(name);	            
	            	roles.setCreatedby("SYSTEM");	            
	            	roles.setDatecreated(new Date());
	            	roles.setStatus(true);
	            	session.save(roles);
	            	tx.commit();
	            	result = 3;
	            
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
