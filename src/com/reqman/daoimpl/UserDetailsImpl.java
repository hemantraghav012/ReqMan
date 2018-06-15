package com.reqman.daoimpl;

import java.util.Date;
import java.util.Random;

import javax.management.relation.Role;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.beans.Login;
import com.reqman.common.HibernateUtil;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Publicemaildomains;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.util.RequestConstants;
import com.reqman.util.accountregisteremail;
import com.reqman.util.forgotpasswordemail;
import com.reqman.util.sendEmail1;
import com.reqman.util.sendEmailonfriend;
import com.reqman.util.setinfoEmail;
import com.reqman.vo.UserupdateVo;

public class UserDetailsImpl implements UserDetailsInterface {

	
	/* Login method for validate emailid and password */
	@SuppressWarnings("null")
	public int validate(String userName, String password, String userrole) throws Exception {
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		GetRolequery reinf = new GetRolequery();
		int result = 0;
		String roleName = "";
		Login login = null;

		try {
			if (userName != null && !userName.trim().equals("")) {
				System.out.println("-userName.toLowerCase().trim()--" + userName.toLowerCase().trim());
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
						.add(Restrictions.eq("password", password.toLowerCase().trim()).ignoreCase()).uniqueResult();

				if (users != null) {
					roleName = reinf.getRoleNameByLoginId(userName.toLowerCase().trim());
				}

				if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)) {
					result = 3;
				}

				else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {
					result = 4;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {
					result = 2;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {
					result = 1;
				}

				tx.commit();
			}

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result = 5;
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}

	public int saveUser(String emailid, String password, String firstname, String lastname, String shortname,
			String hashkey, UploadedFile photo,String organizationkey) throws Exception {
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		Users userskey = null;
		int result = 0;
		String emailArr[] = {};
		String account = "";
		Account accountDetails = new Account();
		Userroles userrolesDetails = null;
		Roles roles = null;
		roles = new Roles();
		userrolesDetails = new Userroles();
		Accountusers accountusers = null;
		Userfriendlist userfriendlist = null;
		String firstname_sendemail=null;
		
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			System.out.println("-hashkey--" + hashkey);
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (users != null && users.getStatus() != null && users.getStatus().booleanValue() == true) {
				result = 1;
			} else if (users != null && users.getStatus() != null && users.getStatus().booleanValue() == false) {
				result = 2;
			} else {
				
				firstname_sendemail = "";
				if(firstname !=null && !firstname.equals("")){
					
					firstname_sendemail = firstname.substring(0,1).toUpperCase() + firstname.substring(1).toLowerCase();
				}else{
					firstname_sendemail = emailid.substring(0,1).toUpperCase() + emailid.substring(1).toLowerCase();;
				}
				
				hashkey = new sendEmail1().createAccount(emailid, firstname_sendemail);
				System.out.println("-hashkey--" + hashkey);

		
				
				users = new Users();
				users.setEmailid(emailid.toLowerCase().trim());
				users.setFirstname(firstname != null ? firstname.trim() : "");
				users.setLastname(lastname != null ? lastname.trim() : "");
				users.setPassword(password);
				users.setShortname(shortname != null ? shortname : "");
				users.setCreatedby("SYSTEM");
				users.setCreatedon(new Date());
				users.setStatus(true);
				users.setHashkey(hashkey != null ? hashkey.trim() : "");
				
				session.save(users);

				emailArr = emailid.split("@");
				if (emailArr != null && emailArr.length >= 2) {
					
					
				//	 organizationkey=RandomStringUtils.randomAlphanumeric(10).toLowerCase().trim();	
					if(organizationkey != null && !organizationkey.trim().equals("")){
						accountDetails = (Account) session.createCriteria(Account.class)
								.add(Restrictions.eq("organizationkey", organizationkey.toLowerCase().trim()).ignoreCase())
								.add(Restrictions.eq("status", true)).uniqueResult();
						if(accountDetails == null){
							organizationkey = "collabor8";
						}
						
					}else{
						organizationkey = "collabor8";
					}
					
					
					
					accountDetails = (Account) session.createCriteria(Account.class)
							.add(Restrictions.eq("organizationkey", organizationkey.toLowerCase().trim()).ignoreCase())
							.add(Restrictions.eq("status", true)).uniqueResult();
					
					

					if (accountDetails == null) {
						accountDetails = new Account();
						
						
						accountDetails.setName("collabor8");
						accountDetails.setOrganizationkey(organizationkey.toLowerCase().trim());
						accountDetails.setStatus(true);
						accountDetails.setCreatedby("SYSTEM");
						accountDetails.setDatecreated(new Date());
						session.save(accountDetails);
					}

					accountusers = new Accountusers();
					accountusers.setAccount(accountDetails);
					accountusers.setUsers(users);
					accountusers.setImagestatus(true);
					session.save(accountusers);
				}

				if (roles != null && userrolesDetails != null) {
					roles = (Roles) session.createCriteria(Roles.class).add(Restrictions.eq("id", 3)).uniqueResult();

					userrolesDetails = new Userroles();
					userrolesDetails.setRoles(roles);
					userrolesDetails.setUsers(users);
					session.save(userrolesDetails);
				}

				userfriendlist = new Userfriendlist();
				userfriendlist.setUsersByFriendid(users);
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

				userfriendlist.setUsersByUserid(users);
				userfriendlist.setStatus(true);
				userfriendlist.setCreatedby(emailid != null ? emailid.trim() : "");
				userfriendlist.setDatecreated(new Date());
				session.save(userfriendlist);
				//sendEmailonfriend sef = new sendEmailonfriend();
				//sef.friendemail(emailid, emailid, emailid);

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
	
	
	private void SetRoles(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserupdateVo getUseremailid(String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		UserupdateVo userupdateVo = new UserupdateVo();
		Users users = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (users != null) {
				userupdateVo.setEmailid(users.getEmailid());
				userupdateVo.setFirstname(users.getFirstname());
				userupdateVo.setLastname(users.getLastname());
				userupdateVo.setShortname(users.getShortname());
				userupdateVo.setPassword(users.getPassword());
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

		return userupdateVo;

	}
	

	
	@Override
	public int updateUsers(String emailid, String firstname, String lastname, String shortname, String password,
			UploadedFile photo,String organizationkey) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Users users = null;
		Accountusers accountusers = null;
		Account account = null;
		GetRolequery reinf = new GetRolequery();
		int result = 0;
		String roleName = "";

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (users != null) {
			Integer userId = users.getId();	
				
				users.setFirstname(firstname);
				users.setLastname(lastname);
				users.setShortname(shortname);
				
				if(password !=null && !password.isEmpty()){
				users.setPassword(password);
				}
				if (photo.getFileName() != null && !photo.getFileName().isEmpty()) {
					users.setPhoto(photo.getContents());
				}
				session.update(users);
				
				
				
				if(organizationkey != null && !organizationkey.trim().equals("")){
					accountusers = (Accountusers) session.createCriteria(Accountusers.class)
							.add(Restrictions.eq("users.id",userId ))
							.uniqueResult();

					if(accountusers !=null){
						
						account = (Account) session.createCriteria(Account.class)
								.add(Restrictions.eq("organizationkey",organizationkey.toLowerCase().trim()).ignoreCase())
								.uniqueResult();
						if(account != null){
					accountusers.setAccount(account);					
					accountusers.setImagestatus(true);
					session.update(accountusers);
						}
					}
				}
				
				tx.commit();

				if (users != null) {
					roleName = reinf.getRoleNameByLoginId(emailid.toLowerCase().trim());

				}

				if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)) {

					result = 3;
				}

				else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {

					result = 4;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {

					result = 2;

				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {

					result = 1;
				}

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

	@Override
	public int updatepasswordByHashkey(String hash, String emailid, String password) throws Exception {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction tx = null;
		Users users = null;
		GetRolequery reinf = new GetRolequery();
		int result = 0;
		String roleName = "";
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("hashkey", hash)).uniqueResult();

			if (users != null) {
				users.setPassword(password);

				session.update(users);
				
				if (users != null) {
					roleName = reinf.getRoleNameByLoginId(emailid.toLowerCase().trim());
				}

				if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)) {
					result = 3;
				}

				else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {
					result = 4;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {
					result = 2;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {
					result = 1;
				}
				tx.commit();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			result = 2;
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}

	@Override
	public int updateinformationByHashkey(String hash, String emailid, String password, String firstname,
			String lastname, String shortname) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Users users = null;
		GetRolequery reinf = new GetRolequery();
		int result = 0;
		String roleName = "";
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("hashkey", hash)).uniqueResult();

			if (users != null) {
				users.setPassword(password);
				users.setFirstname(firstname);
				users.setLastname(lastname);
				users.setShortname(shortname);
				session.update(users);
				if (users != null) {
					roleName = reinf.getRoleNameByLoginId(emailid.toLowerCase().trim());
				}

				if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)) {
					result = 3;
				}

				else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {
					result = 4;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {
					result = 2;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {
					result = 1;
				}
				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			result = 2;
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}

	@Override
	public int savesocialUser(String emailid, String password, String firstname, String lastname, String shortname,
			String hashkey) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		int result = 0;
		String emailArr[] = {};
		String account = "";
		Account accountDetails = new Account();
		Userroles userrolesDetails = null;
		Roles roles = null;
		roles = new Roles();
		userrolesDetails = new Userroles();
		Accountusers accountusers = null;
		Userfriendlist userfriendlist = null;
		String emailidsendemail=null;
		Users userskey = null;
		String organizationkey = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (users != null && users.getStatus() != null && users.getStatus().booleanValue() == true) {
				result = 1;
			} else if (users != null && users.getStatus() != null && users.getStatus().booleanValue() == false) {
				result = 2;
			} else {
				emailidsendemail ="";
				
				if(emailid !=null && !emailid.equals("")){
					emailidsendemail = emailid.substring(0,1).toUpperCase() + emailid.substring(1).toLowerCase();;;
				}
				
				hashkey = new setinfoEmail().createAccount2(emailid, emailidsendemail);
				
				
					users = new Users();
					
				users.setEmailid(emailid.toLowerCase().trim());

				users.setCreatedby("SYSTEM");
				users.setCreatedon(new Date());
				users.setStatus(true);
				users.setHashkey(hashkey != null ? hashkey.trim() : "");
				session.save(users);

				emailArr = emailid.split("@");
				if (emailArr != null && emailArr.length >= 2) {
					
					 //organizationkey=RandomStringUtils.randomAlphanumeric(10).toLowerCase().trim();				
						
					 organizationkey = "collabor8";
					accountDetails = (Account) session.createCriteria(Account.class)
							.add(Restrictions.eq("organizationkey", organizationkey.toLowerCase().trim()).ignoreCase())
							.add(Restrictions.eq("status", true)).uniqueResult();

					if (accountDetails == null) {
						accountDetails = new Account();
						accountDetails.setName("collabor8");
						accountDetails.setOrganizationkey(organizationkey.toLowerCase().trim());
						accountDetails.setStatus(true);
						accountDetails.setCreatedby("SYSTEM");
						accountDetails.setDatecreated(new Date());
						session.save(accountDetails);
					}

					accountusers = new Accountusers();
					accountusers.setAccount(accountDetails);
					accountusers.setUsers(users);
					accountusers.setImagestatus(true);
					session.save(accountusers);

				}
				if (roles != null && userrolesDetails != null) {

					roles = (Roles) session.createCriteria(Roles.class).add(Restrictions.eq("id", 3)).uniqueResult();

					userrolesDetails = new Userroles();
					userrolesDetails.setRoles(roles);
					userrolesDetails.setUsers(users);
					session.save(userrolesDetails);
				}

				userfriendlist = new Userfriendlist();
				userfriendlist.setUsersByFriendid(users);
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

				userfriendlist.setUsersByUserid(users);
				userfriendlist.setStatus(true);
				userfriendlist.setCreatedby(emailid != null ? emailid.trim() : "");
				userfriendlist.setDatecreated(new Date());
				session.save(userfriendlist);
			//	sendEmailonfriend sef = new sendEmailonfriend();
				//sef.friendemail(emailid, emailid, emailid);

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

	
	public byte[] getImageDetails(String userName) throws Exception {

		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		UserupdateVo userupdateVo = new UserupdateVo();
		Users users = null;
		byte[] imageBytes = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (users != null) {
				if (users.getPhoto() != null) {
					imageBytes = users.getPhoto();
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

		return imageBytes;

	}

	@Override
	public int forgotpassword(String emailid, String hashkey) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		int result = 0;
		try {
			if (emailid != null && !emailid.trim().equals("")) {
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

				if (users != null) {

					hashkey = new forgotpasswordemail().createAccount(emailid, emailid);

					tx.commit();
					result = 1;
				} else {
					result = 2;
				}

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

	@Override
	public int forgotpasswordwithemail(String hash, String emailid, String password) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Users users = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (users != null) {
				users.setPassword(password);

				session.update(users);
				tx.commit();
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			result = 2;
		} finally {
			if (session != null)
				session.close();
		}

		return result;

	}
	

	@Override
	public int saveUserthrowgoogle(String googleemail) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		GetRolequery reinf = new GetRolequery();
		int result = 0;
		String roleName = "";
		String emailArr[] = {};
		String account = "";
		Account accountDetails = new Account();
		Userroles userrolesDetails = null;
		Roles roles = null;
		roles = new Roles();
		Accountusers accountusers = null;
		userrolesDetails = new Userroles();
		Userfriendlist userfriendlist = null;
        Users userskey = null;
        String organizationkey = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", googleemail.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (users != null) {
				roleName = reinf.getRoleNameByLoginId(googleemail.toLowerCase().trim());

			}

			if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)) {
				result = 3;
			}

			else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {

				result = 4;
			} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {

				result = 2;

			} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {

				result = 1;
			}

			else {

				System.out.println("-googleemail--" + googleemail);

					users = new Users();
					
				users.setEmailid(googleemail.toLowerCase().trim());
				users.setCreatedby("SYSTEM");
				users.setCreatedon(new Date());
				users.setStatus(true);
				session.save(users);

				emailArr = googleemail.split("@");
				if (emailArr != null && emailArr.length >= 2) {
					// organizationkey=RandomStringUtils.randomAlphanumeric(10).toLowerCase().trim();				
					organizationkey = "collabor8";
					accountDetails = (Account) session.createCriteria(Account.class)
							.add(Restrictions.eq("organizationkey", organizationkey.toLowerCase().trim()).ignoreCase())
							.add(Restrictions.eq("status", true)).uniqueResult();

					if (accountDetails == null) {
						accountDetails = new Account();
						accountDetails.setName("collabor8");
						accountDetails.setOrganizationkey(organizationkey.toLowerCase().trim());
						accountDetails.setStatus(true);
						accountDetails.setCreatedby("SYSTEM");
						accountDetails.setDatecreated(new Date());
						session.save(accountDetails);
					}

					accountusers = new Accountusers();
					accountusers.setAccount(accountDetails);
					accountusers.setUsers(users);
					accountusers.setImagestatus(true);
					session.save(accountusers);

				}
				if (roles != null && userrolesDetails != null) {
					roles = (Roles) session.createCriteria(Roles.class).add(Restrictions.eq("id", 3)).uniqueResult();

					userrolesDetails = new Userroles();
					userrolesDetails.setRoles(roles);
					userrolesDetails.setUsers(users);
					session.save(userrolesDetails);
				}

				userfriendlist = new Userfriendlist();
				userfriendlist.setUsersByFriendid(users);
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", googleemail.toLowerCase().trim()).ignoreCase()).uniqueResult();

				userfriendlist.setUsersByUserid(users);
				userfriendlist.setStatus(true);
				userfriendlist.setCreatedby(googleemail != null ? googleemail.trim() : "");
				userfriendlist.setDatecreated(new Date());
				session.save(userfriendlist);
				sendEmailonfriend sef = new sendEmailonfriend();
				sef.friendemail(googleemail, googleemail, googleemail);

				if (users != null) {
					roleName = reinf.getRoleNameByLoginId(googleemail.toLowerCase().trim());

				}

				if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.REQUESTOR_ROLE)) {

					result = 3;
				}

				else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.TEAM_MEMBER)) {

					result = 4;
				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.ACCOUNT_ADMIN_ROLE)) {

					result = 2;

				} else if (roleName != null && roleName.trim().equalsIgnoreCase(RequestConstants.APP_ADMIN_ROLE)) {

					result = 1;
				}
				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result = 5;
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}

	@SuppressWarnings("unused")
	@Override
	public int saveAccountadminUser(String emailid, String password, String firstname, String lastname,
			String shortname, String hashkey,String organizationname) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		SessionFactory hsf = null;
		Transaction tx = null;
		Users users = null;
		int result = 0;
		String emailArr[] = {};
		String emailArrcheck[] = {};
		String account = "";
		Account accountDetails = new Account();
		Userroles userrolesDetails = null;
		Roles roles = null;
		roles = new Roles();
		userrolesDetails = new Userroles();
		Accountusers accountusers = null;
		Userfriendlist userfriendlist = null;
		Userroles userroleTemp = null;
	 String	firstname_sendemail = null;
		Publicemaildomains publicemaildomains = null;
		Users userskey = null;
		String organizationkey = null;
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			emailArrcheck = emailid.split("@");
			/*if (emailArrcheck != null && emailArrcheck.length >= 2) {

				Criteria crit = session.createCriteria(Publicemaildomains.class);

				crit.add(Restrictions.eq("name", emailArrcheck[1].toLowerCase().trim()).ignoreCase());

				publicemaildomains = (Publicemaildomains) crit.uniqueResult();
			}
			
			if (publicemaildomains != null) {
				result = 7;
				tx.commit();
			} else {
*/
			
				users = (Users) session.createCriteria(Users.class)
						.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();
				
				 organizationkey=RandomStringUtils.randomAlphanumeric(10).toLowerCase().trim();	
				
				 String first_three_char = organizationname.substring(0,3);
					organizationkey =  first_three_char + organizationkey ;
					
				 
				 
				if (users != null && users.getStatus() != null && users.getStatus().booleanValue() == true) {
					Integer userid = users.getId();
					
					
					userroleTemp = (Userroles) session.createCriteria(Userroles.class)
							.add(Restrictions.eq("users.id", userid)).uniqueResult();
					Hibernate.initialize(roles);
					Hibernate.initialize(userroleTemp.getRoles());
					if (userroleTemp != null && userroleTemp.getRoles().getId() != 2) {

						roles = (Roles) session.createCriteria(Roles.class).add(Restrictions.eq("id", 2))
								.uniqueResult();

						userroleTemp.setRoles(roles);

						session.update(userroleTemp);
					
					
				//	if(roles.getId() != 2){
						// organizationkey=RandomStringUtils.randomAlphanumeric(10).toLowerCase().trim();				
							
						accountDetails = (Account) session.createCriteria(Account.class)
								.add(Restrictions.eq("organizationkey", organizationkey.toLowerCase().trim()).ignoreCase())
								.add(Restrictions.eq("status", true)).uniqueResult();

						if (accountDetails == null) {
							accountDetails = new Account();
							accountDetails.setName(organizationname);
							accountDetails.setOrganizationkey(organizationkey.toLowerCase().trim());
							accountDetails.setStatus(true);
							accountDetails.setCreatedby("SYSTEM");
							
							accountDetails.setDatecreated(new Date());
							session.save(accountDetails);
						}

						accountusers = (Accountusers) session.createCriteria(Accountusers.class)
								.add(Restrictions.eq("users.id",userid ))
								.uniqueResult();

						if(accountusers !=null){
						
						accountusers.setAccount(accountDetails);
						//accountusers.setUsers(users);
						accountusers.setImagestatus(true);
						session.update(accountusers);
					
						
					
				}
						result = 1;
					} else {

						result = 6;
					}

					tx.commit();

				} else if (users != null && users.getStatus() != null && users.getStatus().booleanValue() == false) {
					result = 2;
					tx.commit();
				}

				else {
					firstname_sendemail = "";
					if(firstname !=null && !firstname.equals("")){
						
						firstname_sendemail = firstname.substring(0,1).toUpperCase() + firstname.substring(1).toLowerCase();
					}else{
						firstname_sendemail = emailid.substring(0,1).toUpperCase() + emailid.substring(1).toLowerCase();
					}
					hashkey = new accountregisteremail().createAccount(emailid, firstname_sendemail,organizationkey);
					System.out.println("-hashkey--" + hashkey);

					
						users = new Users();
						
						
					users.setEmailid(emailid.toLowerCase().trim());
					users.setFirstname(firstname != null ? firstname.trim() : "");
					users.setLastname(lastname != null ? lastname.trim() : "");
					users.setPassword(password);
					users.setShortname(shortname != null ? shortname : "");
					users.setCreatedby("SYSTEM");
					users.setCreatedon(new Date());
					users.setStatus(true);
					users.setHashkey(hashkey != null ? hashkey.trim() : "");
					
					session.save(users);

					emailArr = emailid.split("@");
					if (emailArr != null && emailArr.length >= 2) {
					//	 organizationkey=RandomStringUtils.randomAlphanumeric(10).toLowerCase().trim();				
							
						accountDetails = (Account) session.createCriteria(Account.class)
								.add(Restrictions.eq("organizationkey", organizationkey.toLowerCase().trim()).ignoreCase())
								.add(Restrictions.eq("status", true)).uniqueResult();
						
						if (accountDetails == null) {
							accountDetails = new Account();
							accountDetails.setName(organizationname);
							accountDetails.setOrganizationkey(organizationkey.toLowerCase().trim());
							accountDetails.setStatus(true);
							accountDetails.setCreatedby("SYSTEM");
							
							accountDetails.setDatecreated(new Date());
							session.save(accountDetails);
						}

						accountusers = new Accountusers();
						accountusers.setAccount(accountDetails);
						accountusers.setUsers(users);
						accountusers.setImagestatus(true);
						session.save(accountusers);
					}

					if (roles != null && userrolesDetails != null) {
						roles = (Roles) session.createCriteria(Roles.class).add(Restrictions.eq("id", 2))
								.uniqueResult();

						userrolesDetails = new Userroles();
						userrolesDetails.setRoles(roles);
						userrolesDetails.setUsers(users);
						session.save(userrolesDetails);
					}

					userfriendlist = new Userfriendlist();
					userfriendlist.setUsersByFriendid(users);
					users = (Users) session.createCriteria(Users.class)
							.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

					userfriendlist.setUsersByUserid(users);
					userfriendlist.setStatus(true);
					userfriendlist.setCreatedby(emailid != null ? emailid.trim() : "");
					userfriendlist.setDatecreated(new Date());
					session.save(userfriendlist);
					//sendEmailonfriend sef = new sendEmailonfriend();
					//sef.friendemail(emailid, emailid, emailid);

					tx.commit();
					result = 3;
				}

			//} 
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

			
	}