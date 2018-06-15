package com.reqman.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.CategoryMasterInterface;
import com.reqman.daoimpl.query.NewRequestquery;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Category;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.DatasummaryVo;

public class CategoryMasterImpl implements CategoryMasterInterface {

	private final String schemaName = HibernateUtil.schemaName;

	// for save impl
	public int savecategory(String name) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Category category = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			category = (Category) session.createCriteria(Category.class)
					.add(Restrictions.eq("name", name.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (category != null && category.getStatus() != null && category.getStatus().booleanValue() == true) {
				result = 1;
			} else if (category != null && category.getStatus() != null
					&& category.getStatus().booleanValue() == false) {
				result = 2;
			} else {
				category = new Category();
				category.setName(name);
				category.setCreatedby("SYSTEM");
				category.setDatecreated(new Date());
				category.setStatus(true);
				session.save(category);
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
	public int savecategory(String categoryName, Boolean status, String emailId) throws Exception {

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
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase()).uniqueResult();

			category = (Category) session.createCriteria(Category.class)
					.add(Restrictions.eq("name", categoryName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (category != null) {
				Usercategory usercategoryExist = null;
				if (category.getUsercategories() != null && category.getUsercategories().size() != 0) {
					for (Usercategory usercategoryDB : category.getUsercategories()) {
						if (usercategoryDB != null && usercategoryDB.getUsers() != null
								&& usercategoryDB.getUsers().getEmailid() != null) {
							if (usercategoryDB.getUsers().getEmailid().trim().equalsIgnoreCase(emailId)) {
								usercategoryExist = usercategoryDB;
								break;
							}
						}
					}
				}

				if (usercategoryExist != null && usercategoryExist.getStatus().equals(true)) {
					result = 1;
				} else if (usercategoryExist != null && usercategoryExist.getStatus().equals(false)) {
					result = 2;
				} else {
					usercategory = new Usercategory();
					usercategory.setCategory(category);
					usercategory.setUsers(users);
					usercategory.setStatus(true);

					session.save(usercategory);

					result = 3;
					tx.commit();
				}
			} else {
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
	@SuppressWarnings("unchecked")
	public List<CategoryVo> getCategoryDetails(String emailId) throws Exception {
		List<CategoryVo> categoryList = new ArrayList<CategoryVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		CategoryVo categoryVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailId.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUsercategories() != null && usersTemp.getUsercategories().size() != 0) {
					for (Usercategory usercategoryDB : usersTemp.getUsercategories()) {
						if (usercategoryDB != null && usercategoryDB.getCategory() != null
								&& usercategoryDB.getCategory().getStatus() == true) {
							categoryVo = new CategoryVo();
							// categoryVo.setSrNo(counter);
							categoryVo.setName(usercategoryDB.getCategory().getName());
							categoryVo.setUserCategoryId(usercategoryDB.getId());
							if (usercategoryDB.getStatus().equals(true)) {
								categoryVo.setStatus("Active");
							} else {
								categoryVo.setStatus("In-Active");
							}
							counter++;
							categoryList.add(categoryVo);
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

		return categoryList;
	}

	// for pie graph true status impl
	@Override
	public List<CategoryVo> getCategoryStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<CategoryVo> categoryList1 = new ArrayList<CategoryVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		CategoryVo categoryVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				if (usersTemp.getUsercategories() != null && usersTemp.getUsercategories().size() != 0) {
					for (Usercategory usercategoryDB : usersTemp.getUsercategories()) {
						if (usercategoryDB != null && usercategoryDB.getCategory() != null
								&& usercategoryDB.getCategory().getStatus() == true
								&& usercategoryDB.getStatus() == true) {
							categoryVo = new CategoryVo();
							categoryVo.setName(usercategoryDB.getCategory().getName());
							categoryVo.setUserCategoryId(usercategoryDB.getId());
							if (usercategoryDB.getStatus().equals(true)) {
								categoryVo.setStatus("Active");
							} else {
								categoryVo.setStatus("In-Active");
							}

							categoryList1.add(categoryVo);
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

		return categoryList1;
	}

	// for pie graph false status impl
	@Override
	public List<CategoryVo> getCategoryStatusfalse(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<CategoryVo> categoryList2 = new ArrayList<CategoryVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		CategoryVo categoryVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				if (usersTemp.getUsercategories() != null && usersTemp.getUsercategories().size() != 0) {
					for (Usercategory usercategoryDB : usersTemp.getUsercategories()) {
						if (usercategoryDB != null && usercategoryDB.getCategory() != null
								&& usercategoryDB.getCategory().getStatus() == true
								&& usercategoryDB.getStatus() == false) {
							categoryVo = new CategoryVo();
							categoryVo.setName(usercategoryDB.getCategory().getName());
							categoryVo.setUserCategoryId(usercategoryDB.getId());
							if (usercategoryDB.getStatus().equals(true)) {
								categoryVo.setStatus("Active");
							} else {
								categoryVo.setStatus("In-Active");
							}

							categoryList2.add(categoryVo);
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

		return categoryList2;

	}

	// for update status through grid impl
	public int updateCategory(String oldValue, String newValue, Integer updatecategoryId) throws Exception {

		Usercategory usercategoryTemp = null;
		Session session = null;
		Transaction tx = null;
		int result = 0;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usercategoryTemp = (Usercategory) session.createCriteria(Usercategory.class)
					.add(Restrictions.eq("id", updatecategoryId)).uniqueResult();

			if (usercategoryTemp != null) {
				if (oldValue != null && oldValue.trim().equalsIgnoreCase("In-Active") && newValue != null
						&& newValue.trim().equalsIgnoreCase("Active")) {
					usercategoryTemp.setStatus(true);

				}

				if (oldValue != null && oldValue.trim().equalsIgnoreCase("Active") && newValue != null
						&& newValue.trim().equalsIgnoreCase("In-Active")) {
					usercategoryTemp.setStatus(false);
				}
				session.update(usercategoryTemp);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryVo> getAccountwiseCategory(String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<CategoryVo> AccountcategoryList = new ArrayList<CategoryVo>();
		List<String> rows = null;
		CategoryVo categoryVo = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();
		String[] accountArr = {};
		Users users = null;
		Integer userId = null;
		Accountusers accountusers = null;
		String organizationkey = null;
		Userroles userroles = null;
		Integer roleid = null;
		try {

			/*
			accountArr = userName.split("@");
			if (accountArr != null && accountArr.length > 1) {
				accountName = accountArr[1];
			}*/

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			if(userName != null){
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
				userId = users.getId();
				
				userroles = (Userroles) session.createCriteria(		Userroles.class)
						.add(Restrictions.eq("users.id", userId)).uniqueResult();
				roleid = userroles.getRoles().getId();
				
				
				accountusers = (Accountusers) session.createCriteria(Accountusers.class)
						.add(Restrictions.eq("users.id",userId)).uniqueResult();
				organizationkey = accountusers.getAccount().getOrganizationkey();
			
			}
			
			if(userName != null && organizationkey !=null && roleid == 2){
			
			
			sb.append("select c.name from ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("users as u, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("usercategory as uc, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("category as c,");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("accountusers as au, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("account as a where u.id=uc.userid and  uc.categoryid=c.id  and");
			sb.append("  au.userid=u.id and a.id=au.accountid ");
			sb.append("  and a.organizationkey ='" + organizationkey + "'");
			}
			
			else if(userName != null && organizationkey !=null && roleid == 1){
					
					sb.append("select c.name from ");

					if (schemaName != null && !schemaName.trim().equals("")) {

						sb.append(schemaName);
						sb.append(".");
					}
					sb.append("category as c");
				
			}
			
			

			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();

			if (rows != null && rows.size() != 0) {
				for (String categoryname : rows) {
					categoryVo = new CategoryVo();
					categoryVo.setName(categoryname);

					AccountcategoryList.add(categoryVo);

				}
			
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null)
				session.close();
		}

		return AccountcategoryList;

	}
	
	/*
	 * public static void main(String args[]) throws Exception {
	 * 
	 * CategoryMasterImpl gr=new CategoryMasterImpl(); Integer cv=0; String
	 * userName="hemantraghav012@gmail.com";
	 * 
	 * gr.getAccountwiseCategory(userName);
	 * 
	 * 
	 * 
	 * }
	 */

}
