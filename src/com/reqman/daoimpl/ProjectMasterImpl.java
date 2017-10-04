package com.reqman.daoimpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;

import com.reqman.dao.ProjectMasterInterface;
import com.reqman.pojo.Project;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.ProjectVo;

public class ProjectMasterImpl implements ProjectMasterInterface{


	public int saveproject(String name) throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	       Project project = null;
	        int result = 0;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	           project = (Project)session.createCriteria(Project.class)
	            		.add(Restrictions.eq("name", name.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            if(project != null && project.getStatus() != null && project.getStatus().booleanValue() == true){
	            	result = 1;
	            }
	            else if(project != null && project.getStatus() != null && project.getStatus().booleanValue() == false){
	            	result = 2;
	            }
	            else
	            {
	            	project=new Project();
	            	project.setName(name);	            
	            	project.setCreatedby("SYSTEM");	            
	            	project.setDatecreated(new Date());
	            	project.setStatus(true);
	            	session.save(project);
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
	

	
	public int saveproject(String projectName, Boolean status, String emailId)
			throws Exception {
		// TODO Auto-generated method stub
		 Session session = null;
	        SessionFactory hsf = null;
	        Transaction tx = null;
	        Users users = null;
	        int result = 0;
	        Project project = null;
	        Userproject userproject = null;
	        try {
	        	session = HibernateUtil.getSession();
	            tx = session.beginTransaction();
	            users = (Users)session.createCriteria(Users.class)
	            		.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            project = (Project)session.createCriteria(Project.class)
	            		.add(Restrictions.eq("name", projectName.toLowerCase().trim()).ignoreCase())
	            		.uniqueResult();
	            
	            if(project != null)
	            {
	            	Userproject userprojectExist = null;
	            	if(project.getUserprojects() != null && project.getUserprojects().size() != 0){
	            		for(Userproject userprojectDB : project.getUserprojects())
	            		{
	            			if(userprojectDB != null && userprojectDB.getUsers() != null && userprojectDB.getUsers().getEmailid() != null){
	            				if(userprojectDB.getUsers().getEmailid().trim().equalsIgnoreCase(emailId))
	            				{
	            					userprojectExist = userprojectDB;
	            					break;
	            				}
	            			}
	            		}
	            	}
	            	
	            	if(userprojectExist != null && userprojectExist.getStatus().equals(true))
	            	{
	            		result = 1;
	            	}
	            	else if(userprojectExist != null && userprojectExist.getStatus().equals(false))
	            	{
	            		result = 2;
	            	}
	            	else
	            	{
	            		userproject = new Userproject();
	            		userproject.setProject(project);
	                	userproject.setUsers(users);
	                	userproject.setStatus(true);
	                	
	                	session.save(userproject);
	                	
	                	result = 3;
	                	tx.commit();
	            	}
	            }
	            else
	            {
	            	project = new Project();
	            	project.setName(projectName.trim());
	            	project.setStatus(true);
	            	project.setCreatedby(emailId);
	            	project.setDatecreated(new Date());
	            	
	            	session.save(project);
	            	
	            	userproject = new Userproject();
	            	userproject.setProject(project);
	            	userproject.setUsers(users);
	            	userproject.setStatus(true);
	            	
	            	session.save(userproject);
	            	
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




	public List<ProjectVo> getProjectDetails(String emailId) throws Exception {
		// TODO Auto-generated method stub

		List<ProjectVo> projectList = new ArrayList<ProjectVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	   ProjectVo projectVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUserprojects() != null && usersTemp.getUserprojects().size() != 0)
            	{
            		for(Userproject userprojectDB : usersTemp.getUserprojects())
            		{
            			if(userprojectDB != null && userprojectDB.getProject() != null 
            					&& userprojectDB.getProject().getStatus() == true)
            			{
            				projectVo = new ProjectVo();
            				//projectVo.setSrNo(counter);
            				projectVo.setName(userprojectDB.getProject().getName());
            				projectVo.setUserProjectId(userprojectDB.getId());
            				
                			if(userprojectDB.getStatus().equals(true))
                			{
                				projectVo.setStatus("Active");
                			}
                			else
                			{
                				projectVo.setStatus("InActive");
                			}
                			counter++;
                			projectList.add(projectVo);
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

		return projectList;
	}
	


	
	public ProjectVo getUserProjectById(String projectId) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
	    Transaction tx = null;
	   ProjectVo projectVo = new ProjectVo();
	    Userproject userproject = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userproject = (Userproject)
            			session.createCriteria(Userproject.class)
            			.add(Restrictions.eq("id", Integer.valueOf(projectId)))
            			.uniqueResult();
            
            int counter = 1;
            if(userproject != null){
            	//projectVo.setSrNo(counter);
            	projectVo.setName(userproject.getProject().getName());
            	projectVo.setUserProjectId(userproject.getId());
    			if(userproject.getStatus() == true)
    			{
    				projectVo.setStatus("Active");
    			}
    			else
    			{
    				projectVo.setStatus("InActive");
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

		return projectVo;
	
	}
	




	public int updateUserprojectById(String projectId, boolean status)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
	    Transaction tx = null;
	    Userproject userproject = null;
	    int result = 0;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            userproject = (Userproject)
            			session.createCriteria(Userproject.class)
            			.add(Restrictions.eq("id", Integer.valueOf(projectId)))
            			.uniqueResult();
            
            if(userproject != null){
            	userproject.setStatus(status);
            	session.update(userproject);
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
	public List<ProjectVo> getProjectStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectVo> projectList1 = new ArrayList<ProjectVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	   ProjectVo projectVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUserprojects() != null && usersTemp.getUserprojects().size() != 0)
            	{
            		for(Userproject userprojectDB : usersTemp.getUserprojects())
            		{
            			if(userprojectDB != null && userprojectDB.getProject() != null 
            					&& userprojectDB.getProject().getStatus() == true && userprojectDB.getStatus() == true)
            			{
            				projectVo = new ProjectVo();
            				//projectVo.setSrNo(counter);
            				projectVo.setName(userprojectDB.getProject().getName());
            				projectVo.setUserProjectId(userprojectDB.getId());
            				
                			if(userprojectDB.getStatus().equals(true))
                			{
                				projectVo.setStatus("Active");
                			}
                			else
                			{
                				projectVo.setStatus("InActive");
                			}
                			counter++;
                			projectList1.add(projectVo);
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

		return projectList1;
	}



	@Override
	public List<ProjectVo> getProjectfalseStatus(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProjectVo> projectList1 = new ArrayList<ProjectVo>();
		Users usersTemp = null;
	    Session session = null;
	    Transaction tx = null;
	   ProjectVo projectVo = null;
		try
		{
           	session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            usersTemp = (Users)session.createCriteria(Users.class)
            		.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
            		.uniqueResult();
            
            if(usersTemp != null){
            	
            	int counter = 1;
            	if(usersTemp.getUserprojects() != null && usersTemp.getUserprojects().size() != 0)
            	{
            		for(Userproject userprojectDB : usersTemp.getUserprojects())
            		{
            			if(userprojectDB != null && userprojectDB.getProject() != null 
            					&& userprojectDB.getProject().getStatus() == true && userprojectDB.getStatus() == false)
            			{
            				projectVo = new ProjectVo();
            				//projectVo.setSrNo(counter);
            				projectVo.setName(userprojectDB.getProject().getName());
            				projectVo.setUserProjectId(userprojectDB.getId());
            				
                			if(userprojectDB.getStatus().equals(true))
                			{
                				projectVo.setStatus("Active");
                			}
                			else
                			{
                				projectVo.setStatus("InActive");
                			}
                			counter++;
                			projectList1.add(projectVo);
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

		return projectList1;
	}

	

}
