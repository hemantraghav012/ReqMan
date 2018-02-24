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

import com.reqman.pojo.Userproject;
import com.reqman.pojo.Users;

import com.reqman.vo.ProjectVo;

public class ProjectMasterImpl implements ProjectMasterInterface {
	// for save impl
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
			project = (Project) session
					.createCriteria(Project.class)
					.add(Restrictions.eq("name", name.toLowerCase().trim())
							.ignoreCase()).uniqueResult();

			if (project != null && project.getStatus() != null
					&& project.getStatus().booleanValue() == true) {
				result = 1;
			} else if (project != null && project.getStatus() != null
					&& project.getStatus().booleanValue() == false) {
				result = 2;
			} else {
				project = new Project();
				project.setName(name);
				project.setCreatedby("SYSTEM");
				project.setDatecreated(new Date());
				project.setStatus(true);
				session.save(project);
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

	// for save impl
	public int saveproject(String projectName, Boolean status, String emailId,
			Boolean projectaccess) throws Exception {
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
			users = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							emailId.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			project = (Project) session
					.createCriteria(Project.class)
					.add(Restrictions.eq("name",
							projectName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (project != null) {
				Userproject userprojectExist = null;
				if (project.getUserprojects() != null
						&& project.getUserprojects().size() != 0) {
					for (Userproject userprojectDB : project.getUserprojects()) {
						if (userprojectDB != null
								&& userprojectDB.getUsers() != null
								&& userprojectDB.getUsers().getEmailid() != null) {
							if (userprojectDB.getUsers().getEmailid().trim()
									.equalsIgnoreCase(emailId)) {
								userprojectExist = userprojectDB;
								break;
							}
						}
					}
				}

				if (userprojectExist != null
						&& userprojectExist.getStatus().equals(true)) {
					result = 1;
				} else if (userprojectExist != null
						&& userprojectExist.getStatus().equals(false)) {
					result = 2;
				} else {
					userproject = new Userproject();
					userproject.setProject(project);
					userproject.setUsers(users);
					userproject.setStatus(true);
					userproject.setProjectaccess(projectaccess);
					session.save(userproject);

					result = 3;
					tx.commit();
				}
			} else {
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
				userproject.setProjectaccess(projectaccess);
				session.save(userproject);

				result = 3;
				tx.commit();
			}

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result = 3;
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}

	// for display data in grid impl
	public List<ProjectVo> getProjectDetails(String emailId) throws Exception {
		// TODO Auto-generated method stub

		List<ProjectVo> projectList = new ArrayList<ProjectVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		ProjectVo projectVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							emailId.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserprojects() != null
						&& usersTemp.getUserprojects().size() != 0) {
					for (Userproject userprojectDB : usersTemp
							.getUserprojects()) {
						if (userprojectDB != null
								&& userprojectDB.getProject() != null
								&& userprojectDB.getProject().getStatus() == true) {
							projectVo = new ProjectVo();
							// projectVo.setSrNo(counter);
							projectVo.setName(userprojectDB.getProject()
									.getName());
							projectVo.setUserProjectId(userprojectDB.getId());

							if (userprojectDB.getStatus().equals(true)) {
								projectVo.setStatus("Active");
							} else {
								projectVo.setStatus("In-Active");
							}

							if (userprojectDB.getProjectaccess().equals(true)) {
								projectVo.setProjectaccess("Task Owner Only");
							} else {
								projectVo.setProjectaccess("All Project Team");
							}

							counter++;
							projectList.add(projectVo);
						}
					}
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return projectList;
	}

	// for pie graph true status impl
	@Override
	public List<ProjectVo> getProjectStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectVo> projectList1 = new ArrayList<ProjectVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		ProjectVo projectVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserprojects() != null
						&& usersTemp.getUserprojects().size() != 0) {
					for (Userproject userprojectDB : usersTemp
							.getUserprojects()) {
						if (userprojectDB != null
								&& userprojectDB.getProject() != null
								&& userprojectDB.getProject().getStatus() == true
								&& userprojectDB.getStatus() == true) {
							projectVo = new ProjectVo();
							// projectVo.setSrNo(counter);
							projectVo.setName(userprojectDB.getProject()
									.getName());
							projectVo.setUserProjectId(userprojectDB.getId());

							if (userprojectDB.getStatus().equals(true)) {
								projectVo.setStatus("Active");
							} else {
								projectVo.setStatus("In-Active");
							}
							counter++;
							projectList1.add(projectVo);
						}
					}
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return projectList1;
	}

	// for pie graph false status impl
	@Override
	public List<ProjectVo> getProjectfalseStatus(String userName)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProjectVo> projectList1 = new ArrayList<ProjectVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		ProjectVo projectVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session
					.createCriteria(Users.class)
					.add(Restrictions.eq("emailid",
							userName.toLowerCase().trim()).ignoreCase())
					.uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserprojects() != null
						&& usersTemp.getUserprojects().size() != 0) {
					for (Userproject userprojectDB : usersTemp
							.getUserprojects()) {
						if (userprojectDB != null
								&& userprojectDB.getProject() != null
								&& userprojectDB.getProject().getStatus() == true
								&& userprojectDB.getStatus() == false) {
							projectVo = new ProjectVo();
							// projectVo.setSrNo(counter);
							projectVo.setName(userprojectDB.getProject()
									.getName());
							projectVo.setUserProjectId(userprojectDB.getId());

							if (userprojectDB.getStatus().equals(true)) {
								projectVo.setStatus("Active");
							} else {
								projectVo.setStatus("In-Active");
							}
							counter++;
							projectList1.add(projectVo);
						}
					}
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return projectList1;
	}

	// for update status through grid impl
	@Override
	public int updateProject(String oldValue, String newValue,
			Integer updateprojectId) throws Exception {
		// TODO Auto-generated method stub
		Userproject userprojectTemp = null;
		Session session = null;
		Transaction tx = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			userprojectTemp = (Userproject) session
					.createCriteria(Userproject.class)
					.add(Restrictions.eq("id", updateprojectId)).uniqueResult();

			if (userprojectTemp != null) {
				if (oldValue != null
						&& oldValue.trim().equalsIgnoreCase("In-Active")
						&& newValue != null
						&& newValue.trim().equalsIgnoreCase("Active")) {
					userprojectTemp.setStatus(true);

				}

				if (oldValue != null
						&& oldValue.trim().equalsIgnoreCase("Active")
						&& newValue != null
						&& newValue.trim().equalsIgnoreCase("In-Active")) {
					userprojectTemp.setStatus(false);
				}

				if (oldValue != null
						&& oldValue.trim().equalsIgnoreCase("All Project Team")
						&& newValue != null
						&& newValue.trim().equalsIgnoreCase("Task Owner Only")) {
					userprojectTemp.setProjectaccess(true);

				}

				if (oldValue != null
						&& oldValue.trim().equalsIgnoreCase("Task Owner Only")
						&& newValue != null
						&& newValue.trim().equalsIgnoreCase("All Project Team")) {
					userprojectTemp.setProjectaccess(false);
				}

				session.update(userprojectTemp);
				tx.commit();
				result = 1;

			}
		} catch (Exception e) {
			e.printStackTrace();
			result = 2;
		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}

}
