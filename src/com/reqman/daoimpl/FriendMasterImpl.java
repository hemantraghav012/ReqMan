package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.Users;
import com.reqman.util.SendemailonFriendasNewuser;
import com.reqman.util.sendEmailonfriend;
import com.reqman.util.setinfoEmail;
import com.reqman.vo.FriendVo;
import com.reqman.vo.UserVo;

public class FriendMasterImpl implements FriendMasterInterface {

	private final String schemaName = HibernateUtil.schemaName;

	// for save impl
	@Override
	public int savefriend(String firstname, String lastname, String emailid, String password, String shortname,
			String hashkey) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
	/*	Session session = null;
		
		Transaction tx = null;
		Users users = null;
	
		String emailArr[] = {};
	
		Account accountDetails = new Account();
		Userroles userrolesDetails = null;
		Roles roles = null;
		roles = new Roles();
		userrolesDetails = new Userroles();
		Accountusers accountusers = null;
		 String emailidsendemail = null;
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
				
		//		hashkey = new SendemailonFriendasNewuser().friendemail(emailid, emailidsendemail,emailidsendemail);

				users = new Users();
				users.setEmailid(emailid.toLowerCase().trim());
				users.setCreatedby("SYSTEM");
				users.setCreatedon(new Date());
				users.setHashkey(hashkey != null ? hashkey.trim() : "");
				users.setStatus(true);
				session.save(users);

				emailArr = emailid.split("@");
				if (emailArr != null && emailArr.length >= 2) {
					accountDetails = (Account) session.createCriteria(Account.class)
							.add(Restrictions.eq("name", emailArr[1].toLowerCase().trim()).ignoreCase())
							.add(Restrictions.eq("status", true)).uniqueResult();

					if (accountDetails == null) {
						accountDetails = new Account();
						accountDetails.setName(emailArr[1].trim());
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
		}*/

		return result;
	}

	// for save impl

	@Override
	public int savefriend(String frienduser, Boolean status, String friendfirstname, String friendlastname,
			String password, String friendshortname, String userName, String hashkey) throws Exception {

		// TODO Auto-generated method stub
		Session session = null;	
		Transaction tx = null;
		Users users = null;
		Userroles userrolesDetails = null;
		Roles roles = null;
		roles = new Roles();
		userrolesDetails = new Userroles();
		int result = 0;
		String emailArr[] = {};
		Account accountDetails = new Account();
		roles = new Roles();
		userrolesDetails = new Userroles();
		Accountusers accountusers = null;
		Userfriendlist userfriendlist = null;
		String usename = null;
		String friendusename = null;
		String emailidsendemail = null;
		String usernamesendemail = null;
		String friendusername1 = null;
		String username1 = null;
		String userkey = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			friendusename = "";

			friendusename = users.getFirstname();

			if (friendusename != null && !friendusename.equals("")) {
				usename = friendusename;
			} else {
				usename = users.getEmailid();
			}

			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", frienduser.toLowerCase().trim()).ignoreCase()).uniqueResult();

			
			if (users != null) {
				
				friendusername1 = "";
				
				friendusername1 = users.getFirstname();

				if (friendusername1 != null && !friendusername1.equals("")) {
					username1 = friendusername1;
				} else {
					username1 = users.getEmailid();
				}
				
				Userfriendlist userfriendExist = null;
				if (users.getUserfriendlistsForFriendid() != null
						&& users.getUserfriendlistsForFriendid().size() != 0) {
					for (Userfriendlist userfriendDB : users.getUserfriendlistsForFriendid()) {
						if (userfriendDB != null && userfriendDB.getUsersByUserid() != null
								&& userfriendDB.getUsersByUserid().getEmailid() != null) {
							if (userfriendDB.getUsersByUserid().getEmailid().trim().equalsIgnoreCase(userName)) {
								userfriendExist = userfriendDB;
								break;
							}
						}
					}
				}

				if (userfriendExist != null && userfriendExist.getStatus().equals(true)) {
					result = 1;
				} else if (userfriendExist != null && userfriendExist.getStatus().equals(false)) {
					result = 2;
				} else {

					userfriendlist = new Userfriendlist();
					userfriendlist.setUsersByFriendid(users);
					users = (Users) session.createCriteria(Users.class)
							.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

					userfriendlist.setUsersByUserid(users);
					userfriendlist.setStatus(true);
					userfriendlist.setCreatedby(userName != null ? userName.trim() : "");
					userfriendlist.setDatecreated(new Date());
					session.save(userfriendlist);
					sendEmailonfriend sef = new sendEmailonfriend();
					emailidsendemail ="";
					if(username1 !=null && !username1.equals("")){
						emailidsendemail = username1.substring(0,1).toUpperCase() + username1.substring(1).toLowerCase();
					}
					usernamesendemail= "";
					if(usename !=null && !usename.equals("")){
						usernamesendemail = usename.substring(0,1).toUpperCase() + usename.substring(1).toLowerCase();
					}
					
					sef.friendemail(frienduser, emailidsendemail, usernamesendemail);

					result = 3;
					
				}
			} 
			else {
				  emailidsendemail ="";
					
					if(frienduser !=null && !frienduser.equals("")){
						emailidsendemail = frienduser.substring(0,1).toUpperCase() + frienduser.substring(1).toLowerCase();
					}
					usernamesendemail= "";
					if(usename !=null && !usename.equals("")){
						usernamesendemail = usename.substring(0,1).toUpperCase() + usename.substring(1).toLowerCase();
					}
				hashkey = new SendemailonFriendasNewuser().friendemail(frienduser, emailidsendemail,usernamesendemail);
				System.out.println("-hashkey--" + hashkey);
				
				//sendEmailonfriend sef = new sendEmailonfriend();

				System.out.println("-password--" + password);

				users = new Users();
				users.setEmailid(frienduser != null ? frienduser.toLowerCase().trim() : "");

				users.setStatus(true);
				users.setCreatedby(userName != null ? userName.trim() : "");
				users.setCreatedon(new Date());
				users.setHashkey(hashkey != null ? hashkey.trim() : "");

				session.save(users);
				
	
				System.out.println("-password--" + hashkey);
				emailArr = frienduser.split("@");
				if (emailArr != null && emailArr.length >= 2) {
					// userkey=RandomStringUtils.randomAlphanumeric(10).toLowerCase().trim();				
						
					userkey = "collabor8";
						accountDetails = (Account) session.createCriteria(Account.class)
								.add(Restrictions.eq("name", userkey.toLowerCase().trim()).ignoreCase())
								.add(Restrictions.eq("status", true)).uniqueResult();


					if (accountDetails == null) {
						accountDetails = new Account();
						accountDetails.setName(userkey.toLowerCase().trim());
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
				if (frienduser != null) {
					
					userfriendlist = new Userfriendlist();
					userfriendlist.setUsersByFriendid(users);
					
					
					users = (Users) session.createCriteria(Users.class)
							.add(Restrictions.eq("emailid", frienduser.toLowerCase().trim()).ignoreCase())
							.uniqueResult();

					
					
					userfriendlist.setUsersByUserid(users);
					userfriendlist.setStatus(true);
					userfriendlist.setCreatedby(frienduser != null ? frienduser.trim() : "");
					userfriendlist.setDatecreated(new Date());
					session.save(userfriendlist);
					
			/*		emailidsendemail ="";
					if(frienduser !=null && !frienduser.equals("")){
						emailidsendemail = frienduser.substring(0,1).toUpperCase() + frienduser.substring(1).toLowerCase();
					}
					usernamesendemail= "";
					if(usename !=null && !usename.equals("")){
						usernamesendemail = usename.substring(0,1).toUpperCase() + usename.substring(1).toLowerCase();;;
					}
					
				//	if(hashkey == null){
				//	sef.friendemail(frienduser, emailidsendemail, usernamesendemail);
				//	}*/

				}
                if (userName != null) {
					
					userfriendlist = new Userfriendlist();
					userfriendlist.setUsersByFriendid(users);
					
					
					users = (Users) session.createCriteria(Users.class)
							.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase())
							.uniqueResult();

					
					
					userfriendlist.setUsersByUserid(users);
					userfriendlist.setStatus(true);
					userfriendlist.setCreatedby(userName != null ? userName.trim() : "");
					userfriendlist.setDatecreated(new Date());
					session.save(userfriendlist);
                }	
			}
				result = 3;
				tx.commit();
			

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

	// for display data in grid impl
	@Override
	public List<FriendVo> getUsersDetails(String userName) {
		// TODO Auto-generated method stub
		List<FriendVo> friendList = new ArrayList<FriendVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		FriendVo friendVo = null;
		Integer friendid = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				Integer counter = 1;
				if (usersTemp.getUserfriendlistsForUserid() != null
						&& usersTemp.getUserfriendlistsForUserid().size() != 0) {
					for (Userfriendlist userfriendDB : usersTemp.getUserfriendlistsForUserid()) {
						if (userfriendDB != null && userfriendDB.getUsersByFriendid() != null
								&& userfriendDB.getUsersByFriendid().getStatus() == true) {
							
			 friendid = userfriendDB.getUsersByFriendid().getId();
			
						
			//byte[] image = FriendMasterImpl.getImageDetails(friendid);
							friendVo = new FriendVo();
							String firstName = "";
							String lastName = "";
							String name = "";
							String emailid = "";
							if (userfriendDB != null && userfriendDB.getUsersByFriendid() != null
									&& userfriendDB.getUsersByFriendid() != null) {

								emailid = userfriendDB.getUsersByFriendid().getEmailid() != null
										? userfriendDB.getUsersByFriendid().getEmailid() : "";

								firstName = userfriendDB.getUsersByFriendid().getFirstname() != null
										? userfriendDB.getUsersByFriendid().getFirstname() : "";

								lastName = userfriendDB.getUsersByFriendid().getLastname() != null
										? userfriendDB.getUsersByFriendid().getLastname() : "";

								if (firstName != null && !firstName.trim().equals("")) {
									name = firstName.trim();
								}

								if (lastName != null && !lastName.trim().equals("")
										|| name != null && !name.trim().equals("")) {
									name = name + " " + lastName.trim();
								} else {
									name = emailid;
								}
							}
						//	if(image !=null){
						//	friendVo.setImage(image);
							//}
							
							friendVo.setFriendid(userfriendDB.getUsersByFriendid().getId());;
							Integer hh=userfriendDB.getUsersByFriendid().getId();
							System.out.println(hh);
							friendVo.setName(name.trim());
							friendVo.setFirstname(userfriendDB.getUsersByFriendid().getFirstname());
							friendVo.setLastname(userfriendDB.getUsersByFriendid().getLastname());
							friendVo.setEmailid(userfriendDB.getUsersByFriendid().getEmailid());
							friendVo.setShortname(userfriendDB.getUsersByFriendid().getShortname());
							friendVo.setUserFriendId(userfriendDB.getId());
							if (userfriendDB.getStatus().equals(true)) {
								friendVo.setStatus("Active");
							} else {
								friendVo.setStatus("In-Active");
							}
							counter++;
							friendList.add(friendVo);
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

		return friendList;
	}

	// for pie graph true status impl
	@Override
	public List<FriendVo> getUsersStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<FriendVo> friendList1 = new ArrayList<FriendVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		FriendVo friendVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserfriendlistsForUserid() != null
						&& usersTemp.getUserfriendlistsForUserid().size() != 0) {
					for (Userfriendlist userfriendDB : usersTemp.getUserfriendlistsForUserid()) {
						if (userfriendDB != null && userfriendDB.getUsersByFriendid() != null
								&& userfriendDB.getUsersByFriendid().getStatus() == true
								&& userfriendDB.getStatus() == true) {
							friendVo = new FriendVo();

							friendVo.setFirstname(userfriendDB.getUsersByFriendid().getFirstname());
							friendVo.setLastname(userfriendDB.getUsersByFriendid().getLastname());
							friendVo.setEmailid(userfriendDB.getUsersByFriendid().getEmailid());
							friendVo.setShortname(userfriendDB.getUsersByFriendid().getShortname());
							friendVo.setUserFriendId(userfriendDB.getId());
							if (userfriendDB.getStatus().equals(true)) {
								friendVo.setStatus("Active");
							} else {
								friendVo.setStatus("In-Active");
							}
							counter++;
							friendList1.add(friendVo);
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

		return friendList1;
	}

	// for pie graph false status impl
	@Override
	public List<FriendVo> getUsersfasleStatus(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<FriendVo> friendList1 = new ArrayList<FriendVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		FriendVo friendVo = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				int counter = 1;
				if (usersTemp.getUserfriendlistsForUserid() != null
						&& usersTemp.getUserfriendlistsForUserid().size() != 0) {
					for (Userfriendlist userfriendDB : usersTemp.getUserfriendlistsForUserid()) {
						if (userfriendDB != null && userfriendDB.getUsersByFriendid() != null
								&& userfriendDB.getUsersByFriendid().getStatus() == true
								&& userfriendDB.getStatus() == false) {
							friendVo = new FriendVo();

							friendVo.setFirstname(userfriendDB.getUsersByFriendid().getFirstname());
							friendVo.setLastname(userfriendDB.getUsersByFriendid().getLastname());
							friendVo.setEmailid(userfriendDB.getUsersByFriendid().getEmailid());
							friendVo.setShortname(userfriendDB.getUsersByFriendid().getShortname());
							friendVo.setUserFriendId(userfriendDB.getId());
							if (userfriendDB.getStatus().equals(true)) {
								friendVo.setStatus("Active");
							} else {
								friendVo.setStatus("In-Active");
							}
							counter++;
							friendList1.add(friendVo);
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

		return friendList1;
	}

	// for update status through grid impl
	@Override
	public int updateFriend(String oldValue, String newValue, Integer updatefriendId) throws Exception {
		// TODO Auto-generated method stub
		Userfriendlist userfriendlistTemp = null;
		Session session = null;
		Transaction tx = null;
		int result = 0;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			userfriendlistTemp = (Userfriendlist) session.createCriteria(Userfriendlist.class)
					.add(Restrictions.eq("id", updatefriendId)).uniqueResult();

			if (userfriendlistTemp != null) {
				if (oldValue != null && oldValue.trim().equalsIgnoreCase("In-Active") && newValue != null
						&& newValue.trim().equalsIgnoreCase("Active")) {
					userfriendlistTemp.setStatus(true);

				}

				if (oldValue != null && oldValue.trim().equalsIgnoreCase("Active") && newValue != null
						&& newValue.trim().equalsIgnoreCase("In-Active")) {
					userfriendlistTemp.setStatus(false);
				}
				session.update(userfriendlistTemp);
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

	// for get user name show on create request page in team member field
	@SuppressWarnings("unchecked")
	@Override
	public List<UserVo> AllUsers(String userName) throws Exception {
		List<UserVo> getfriendList = new ArrayList<UserVo>();
		UserVo userVo = null;
		Users users = null;
		Session session = null;
		Transaction tx = null;
		List<Integer> friendList = null;
		String firstName = "";
		String lastName = "";
		String name = "";
		String emailid = "";
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			users = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();

			Integer userid = users.getId();

			Criteria crit1 = session.createCriteria(Userfriendlist.class);
			crit1.add(Restrictions.eq("status", true));
			crit1.add(Restrictions.eq("usersByUserid.id", users.getId()));
			crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setProjection(
					Projections.distinct(Projections.projectionList().add(Projections.property("usersByFriendid.id"))));
			friendList = (List<Integer>) crit1.list();

			if (friendList != null && friendList.size() != 0) {
				List<Users> userList = (List<Users>) session.createCriteria(Users.class)
						.add(Restrictions.in("id", friendList)).list();

				for (Users userDB : userList) {
					userVo = new UserVo();
					name = "";
					userVo.setEmailid(userDB.getEmailid());
					userVo.setUserId(userDB.getId());

					emailid = userDB.getEmailid() != null ? userDB.getEmailid() : "";
					firstName = userDB.getFirstname() != null ? userDB.getFirstname() : "";

					lastName = userDB.getLastname() != null ? userDB.getLastname() : "";

					if (firstName != null && !firstName.trim().equals("")) {
						name = firstName.trim();
						userVo.setFirstname(firstName);
					}

					if (lastName != null && !lastName.trim().equals("")) {

						userVo.setLastname(lastName);

					}

					if (emailid != null && !emailid.trim().equals("")) {
						name = name.trim() + " " + lastName.trim() + "(" + emailid.trim() + ")";
						userVo.setEmailid(emailid);
						userVo.setName(name);

					}

					getfriendList.add(userVo);
				}

			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.close();
		return getfriendList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendVo> getaccountUsers(String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<FriendVo> AccountfriendList = new ArrayList<FriendVo>();
		List<String> rows = null;
		FriendVo friendVo = null;
		SQLQuery query = null;
		String sqlQuery = "";
		StringBuffer sb = new StringBuffer();
		String[] accountArr = {};
		
		String firstName = null;
		String lastName = null;
	
		String name = null;
		Users usersTemp = null;
		Users users = null;
		Integer userId = null;
		Accountusers accountusers = null;
		String organizationkey = null;
		Userroles userroles = null;
		Integer roleid = null;
		
		try {

			
			/*accountArr = userName.split("@");
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
				
			sb.append("select distinct u.emailid from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userfriendlist as ut ");
			
				
			sb.append("where ut.userid ");
			sb.append("in(select distinct u.id from ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("users as u,  ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userfriendlist as ut, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("accountusers as au, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("account as a where u.id=ut.userid and  ut.userid=u.id  and");
			sb.append("  au.userid=u.id and a.id=au.accountid ");
			sb.append(" and a.organizationkey ='" + organizationkey + "')and ut.friendid=u.id");

		
					
			}
			else if(userName != null && organizationkey !=null && roleid == 1){
				
			sb.append("select distinct u.emailid from ");

			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}

			sb.append("users as u, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userfriendlist as ut,");
			
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userroles as ur");
			
			
			
			sb.append(" where ut.userid ");
			sb.append("in(select distinct u.id from ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("users as u,  ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("userfriendlist as ut,   ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("accountusers as au, ");
			if (schemaName != null && !schemaName.trim().equals("")) {

				sb.append(schemaName);
				sb.append(".");
			}
			sb.append("account as a where u.id=ut.userid and  ut.userid=u.id");
		
			sb.append(" )and ut.friendid=u.id");

		
			
		
			}
			
			sqlQuery = sb.toString();
			query = session.createSQLQuery(sqlQuery);
			rows = query.list();
			
			
			if (rows != null && rows.size() != 0) {
				for (String accountfriendremailid : rows) {

					firstName = "";
					lastName = "";
					
					name = "";

					usersTemp = (Users) session.createCriteria(Users.class)
							.add(Restrictions.eq("emailid", accountfriendremailid.toLowerCase().trim()).ignoreCase())
							.uniqueResult();

					if (usersTemp != null) {
						firstName = usersTemp.getFirstname() != null ? usersTemp.getFirstname() : "";

						lastName = usersTemp.getLastname() != null ? usersTemp.getLastname() : "";

						if (firstName != null && !firstName.trim().equals("")) {
							name = firstName.trim();
						}

						if (lastName != null && !lastName.trim().equals("")
								|| name != null && !name.trim().equals("")) {
							name = name + " " + lastName.trim();
						} else {
							name = accountfriendremailid;
						}

						friendVo = new FriendVo();
						System.out.println(name);
						friendVo.setName(name.trim());

						AccountfriendList.add(friendVo);
					}
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

		return AccountfriendList;

	}

	
/*
	@Override
	public byte[] getImageDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Users users = null;
		Integer userid = 0;
		GetRolequery gr = new GetRolequery();

		byte[] imageBytes = null;
		try {
			
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			//userid = gr.getfriendid(userName);
			users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("emailid", userName))
					.uniqueResult();
			userid = users.getId();
			
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
*/
	@Override
	public byte[] getuserImage(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Users users = null;
		Integer userid = 0;
		GetRolequery gr = new GetRolequery();

		byte[] imageBytes = null;
		try {
			
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			//userid = gr.getfriendid(userName);
			users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("id", id))
					.uniqueResult();
			
			
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

}
