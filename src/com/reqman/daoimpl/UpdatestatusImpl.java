package com.reqman.daoimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.UpdatestatusInterface;
import com.reqman.daoimpl.query.NewRequestquery;
import com.reqman.daoimpl.query.Updaterequestquery;
import com.reqman.pojo.Project;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Users;
import com.reqman.util.Dateconverter;
import com.reqman.util.completerequestsend;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.UpdatestatusVo;
import com.reqman.vo.dailyDuedatewisesendRequestVo;
import com.reqman.vo.requestNoteVo;

public class UpdatestatusImpl implements UpdatestatusInterface {

	
	// for show all data in grid (update tasks status )
	@SuppressWarnings("unchecked")
	@Override
	public List<UpdatestatusVo> getupdatestatusDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<UpdatestatusVo> requestList = new ArrayList<UpdatestatusVo>();
		Users usersTemp = null;
		Users usersDB = null;
		Session session = null;
		Transaction tx = null;
		Request request = null;
		List<Integer> friendList = null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo = null;
		UpdatestatusVo updatestatusVo = null;
		Integer countvalue = 0;
		NewRequestquery newRequestquery = new NewRequestquery();
		List<requestNoteVo> requestnoteList = null;
		Updaterequestquery updaterequestquery = new Updaterequestquery();

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp != null) {

				// Integer id1=usersTemp.getId();

				Criteria crit1 = session.createCriteria(Userfriendlist.class);
				crit1.add(Restrictions.eq("status", true));
				crit1.add(Restrictions.eq("usersByFriendid.id", usersTemp.getId()));
				crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setProjection(
						Projections.distinct(Projections.projectionList().add(Projections.property("id"))));
				friendList = (List<Integer>) crit1.list();

				List<Request> requesPojoList = null;
				if (friendList != null && friendList.size() != 0) {
					requesPojoList = (List<Request>) session.createCriteria(Request.class)
							.add(Restrictions.in("userfriendlist.id", friendList)).list();
				}
				String emailid = "";
				String userCategory = "";
				String userProject = "";
				String userRequestType = "";
				String firstName = "";
				String lastName = "";
				String name = "";
				Integer attachmentstatus = 0;
				if (requesPojoList != null && requesPojoList.size() != 0) {
					for (Request requestDB : requesPojoList) {
						emailid = "";
						userCategory = "";
						userProject = "";
						userRequestType = "";
						updatestatusVo = new UpdatestatusVo();
						firstName = "";
						lastName = "";
						name = "";

						countvalue = updaterequestquery.countRequestnoteStatus(requestDB.getId());
						attachmentstatus = newRequestquery.attachmentstatustrue(requestDB.getId());

						requestnoteList = new ArrayList<requestNoteVo>();

						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						Hibernate.initialize(requestDB.getRequestnoteses());
						if (requestDB != null && requestDB.getUsercategory() != null
								&& requestDB.getUsercategory().getCategory() != null) {
							userCategory = requestDB.getUsercategory().getCategory().getName() != null
									? requestDB.getUsercategory().getCategory().getName() : "";
						}

						if (requestDB != null && requestDB.getUserproject() != null
								&& requestDB.getUserproject().getProject() != null) {
							userProject = requestDB.getUserproject().getProject().getName() != null
									? requestDB.getUserproject().getProject().getName() : "";
						}

						if (requestDB != null && requestDB.getUserrequesttype() != null
								&& requestDB.getUserrequesttype().getRequesttype() != null) {
							userRequestType = requestDB.getUserrequesttype().getRequesttype() != null
									? requestDB.getUserrequesttype().getRequesttype().getName() : "";
						}

						if (requestDB != null) {

							usersTemp = (Users) session.createCriteria(Users.class)
									.add(Restrictions.eq("emailid", requestDB.getCreatedby().trim()).ignoreCase())
									.uniqueResult();
							if (usersTemp != null) {
								emailid = usersTemp.getEmailid();
								firstName = usersTemp.getFirstname();
								lastName = usersTemp.getLastname();

								if (firstName != null && !firstName.trim().equals("")) {
									name = firstName.trim();
								}

								if (lastName != null && !lastName.trim().equals("")) {
									name = name + " " + lastName.trim();
								}
							}
						}

						if (requestDB.getStatus() == true
								&& (requestDB.getRequeststatus() == 2 || requestDB.getRequeststatus() == 4
										|| requestDB.getRequeststatus() == 5 || requestDB.getRequeststatus() == 7)) {

							if (countvalue != 0) {

								updatestatusVo.setTeammembernotescount(countvalue);
							}

							if (attachmentstatus != 0) {

								updatestatusVo.setAttachmentstatus(attachmentstatus);
							}

							updatestatusVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
							updatestatusVo.setDescription(requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
							updatestatusVo.setCompletiondate(requestDB.getCompletiondate() != null
									? Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
							updatestatusVo.setModifydate(requestDB.getDatemodified());
							updatestatusVo.setDatecompletion(requestDB.getCompletiondate());
							
						
							
							updatestatusVo.setSerialid(requestDB.getSerialid());
							
							
							
							if (!name.equalsIgnoreCase("")) {
								updatestatusVo.setFriendName(name);
							} else {
								updatestatusVo.setFriendName(emailid);
							}
							if (userCategory.equalsIgnoreCase("")) {
								updatestatusVo.setUsercategory("General");
							} else {
								updatestatusVo.setUsercategory(userCategory);
							}
							if (userProject.equalsIgnoreCase("")) {
								updatestatusVo.setUserproject("General");
							} else {
								updatestatusVo.setUserproject(userProject);
							}
							if (userRequestType.equalsIgnoreCase("")) {
								updatestatusVo.setUserrequesttype("General");
							} else {
								updatestatusVo.setUserrequesttype(userRequestType);
							}
							updatestatusVo.setCreatedby(requestDB.getCreatedby());
							updatestatusVo.setCompletionpercentage(requestDB.getCompletionpercentage());

							if (requestDB.getRequeststatus() == 2) {
								updatestatusVo.setStage("Accepted");
							} else if (requestDB.getRequeststatus() == 4) {
								updatestatusVo.setStage("In-progress");
							}

							else if (requestDB.getRequeststatus() == 5) {
								updatestatusVo.setStage("Completed");
							} else if (requestDB.getRequeststatus() == 6) {
								updatestatusVo.setStage("Cancelled");
							} else if (requestDB.getRequeststatus() == 7) {
								updatestatusVo.setStage("Hold");
							} else if (requestDB.getRequeststatus() == 8) {
								updatestatusVo.setStage("Closed");
							}

							if (requestDB != null && requestDB.getStatus() != null
									&& requestDB.getStatus().booleanValue() == true) {
								updatestatusVo.setStatus("Active");
							} else {
								updatestatusVo.setStatus("In-Active");
							}

							updatestatusVo.setNewRequestId(requestDB.getId());

							String createdbyfirstname = "";
							String createdbylastname = "";
							String createdbyname = "";
							String createdbyemailid = "";
							if (requestDB.getRequestnoteses() != null && requestDB.getRequestnoteses().size() != 0) {

								for (Requestnotes requestnotes : requestDB.getRequestnoteses()) {
									createdbyfirstname = "";
									createdbylastname = "";
									createdbyname = "";
									createdbyemailid = "";

									if (requestnotes.getCreatedby() != null) {

										usersDB = (Users) session.createCriteria(Users.class)
												.add(Restrictions.eq("emailid", requestnotes.getCreatedby()))
												.uniqueResult();
										createdbyemailid = usersDB.getEmailid() != null ? usersDB.getEmailid() : "";
										createdbyfirstname = usersDB.getFirstname() != null ? usersDB.getFirstname()
												: "";

										createdbylastname = usersDB.getLastname() != null ? usersDB.getLastname() : "";

										if (createdbyfirstname != null && !createdbyfirstname.trim().equals("")) {
											createdbyname = createdbyfirstname.trim();
										}

										if (createdbylastname != null && !createdbylastname.trim().equals("")) {
											createdbyname = createdbyname + " " + createdbylastname.trim();
										} else {
											createdbyname = createdbyemailid;
										}

									}
									requestnoteVo = new requestNoteVo();
									
									requestnoteVo.setNoteId(requestnotes.getId());
									requestnoteVo.setMessage(
											requestnotes.getMessage() != null ? requestnotes.getMessage().trim() : "");
									requestnoteVo.setCreatedon(requestnotes.getCreatedon() != null
											? Dateconverter.convertDateToStringDDMMDDYYYY(requestnotes.getCreatedon())
											: "");
									requestnoteVo.setTime(requestnotes.getCreatedon() != null
											? Dateconverter.convertTimeToStringhhmmss(requestnotes.getCreatedon())
											: "");

									requestnoteVo.setCreatedby(createdbyname);
									requestnoteList.add(requestnoteVo);
									Collections.sort(requestnoteList, requestNoteVo.NoteIdComparator);
								}
							}

							updatestatusVo.setNoteList(requestnoteList);

							requestList.add(updatestatusVo);
						}
					}
				}

				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return requestList;
	}

	// for display data in modify/view page
	@Override
	public UpdatestatusVo getRequestById(String requestId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Users usersTemp = null;
		UpdatestatusVo updatestatusVo = new UpdatestatusVo();
		Request request = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			request = (Request) session.createCriteria(Request.class)
					.add(Restrictions.eq("id", Integer.valueOf(requestId))).uniqueResult();

			String emailid = "";
			String firstName = "";
			String lastName = "";
			String name = "";
			String userCategory = "";
			String userProject = "";
			String userRequestType = "";
			String status = "";
			Hibernate.initialize(request);
			if (request != null) {
				if (request.getAttachment() != null && request.getAttachment().length != 0) {
					updatestatusVo.setFile(request.getAttachment());
				}

				if (request != null && request.getUsercategory() != null
						&& request.getUsercategory().getCategory() != null) {
					userCategory = request.getUsercategory().getCategory().getName() != null
							? request.getUsercategory().getCategory().getName() : "";
				}

				if (request != null && request.getUserproject() != null
						&& request.getUserproject().getProject() != null) {
					userProject = request.getUserproject().getProject().getName() != null
							? request.getUserproject().getProject().getName() : "";
				}

				if (request != null && request.getUserrequesttype() != null
						&& request.getUserrequesttype().getRequesttype() != null) {
					userRequestType = request.getUserrequesttype().getRequesttype() != null
							? request.getUserrequesttype().getRequesttype().getName() : "";
				}
				if (request.getFilename() != null && !request.getFilename().trim().equals("")) {
					updatestatusVo.setFileName(request.getFilename().trim());
				}

				if (request != null) {

					usersTemp = (Users) session.createCriteria(Users.class)
							.add(Restrictions.eq("emailid", request.getCreatedby().trim()).ignoreCase()).uniqueResult();
					if (usersTemp != null) {
						emailid = usersTemp.getEmailid();
						firstName = usersTemp.getFirstname();
						lastName = usersTemp.getLastname();

						if (firstName != null && !firstName.trim().equals("")) {
							name = firstName.trim();
						}

						if (lastName != null && !lastName.trim().equals("")) {
							name = name + " " + lastName.trim();
						}
					}
				}

				updatestatusVo.setTitle(request.getTitle());
				updatestatusVo.setNewRequestId(request.getId());

				if (!name.equalsIgnoreCase("")) {
					updatestatusVo.setFriendName(name);
				} else {
					updatestatusVo.setFriendName(emailid);
				}

				if (userCategory.equalsIgnoreCase("")) {
					updatestatusVo.setUsercategory("General");
				} else {
					updatestatusVo.setUsercategory(userCategory);
				}
				if (userProject.equalsIgnoreCase("")) {
					updatestatusVo.setUserproject("General");
				} else {
					updatestatusVo.setUserproject(userProject);
				}
				if (userRequestType.equalsIgnoreCase("")) {
					updatestatusVo.setUserrequesttype("General");
				} else {
					updatestatusVo.setUserrequesttype(userRequestType);
				}
				updatestatusVo.setDescription(request.getDescription() != null ? request.getDescription() :"");
				updatestatusVo.setCompletiondate(request.getCompletiondate() != null
						? Dateconverter.convertDateToStringDDMMDDYYYY(request.getCompletiondate()) : "");
				updatestatusVo.setFileName(request.getFilename());
				updatestatusVo.setCompletionpercentage(request.getCompletionpercentage());
				updatestatusVo.setPriority(request.getPriority());
				updatestatusVo.setWeightage(request.getWeightage());
				updatestatusVo.setEstimatedeffort(request.getEstimatedeffort());
				updatestatusVo.setActualeffort(request.getActualeffort());
				updatestatusVo.setRequeststage(request.getRequeststatus());
				updatestatusVo.setRating(request.getRating());
				updatestatusVo.setFeedback(request.getFeedback());
				updatestatusVo.setDatecompletion(request.getCompletiondate());

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

		return updatestatusVo;

	}

//for update task status

	@Override
	public int updateRequestById(String requestId, Date completiondate, int completionpercentage, Integer stage,
			String message, String userName, String actualeffort) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		Request requestworkflow = null;
		Users users = null;
		Userproject userproject = null;
		Usercategory usercategory = null;
		Userrequesttype userrequesttype = null;

		int result = 0;
		Requestnotes requestnotes = null;
		try {
			completerequestsend sr = new completerequestsend();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			requestworkflow = (Request) session.createCriteria(Request.class)
					.add(Restrictions.eq("id", Integer.valueOf(requestId))).uniqueResult();

			if (requestworkflow != null) {

				String projectname = "";
				String categoryname = "";
				String typename = "";
				String title = "";
				String description = "";
				String priority = "";
				String estimatedeffort = "";
				Integer weight = 0;
				String duedate = "";
				String friendname = "";
				String requestorname = "";
				String createdby = "";
				Date completiondate1 = null;

				userproject = requestworkflow.getUserproject();
				usercategory = requestworkflow.getUsercategory();
				userrequesttype = requestworkflow.getUserrequesttype();
				title = requestworkflow.getTitle();
				
				description = requestworkflow.getDescription();
				
				priority = requestworkflow.getPriority();
				estimatedeffort = requestworkflow.getEstimatedeffort();
				weight = requestworkflow.getWeightage();
				completiondate1 = requestworkflow.getCompletiondate();
				duedate = Dateconverter.convertDateToStringDDMMDDYYYY(completiondate1);
				createdby = requestworkflow.getCreatedby();
				if (userproject != null) {
					projectname = userproject.getProject().getName();
				}
				if (usercategory != null) {
					categoryname = usercategory.getCategory().getName();
				}
				if (userrequesttype != null) {
					typename = userrequesttype.getRequesttype().getName();
				}
				

				if (userName != null) {

					users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("emailid", userName))
							.uniqueResult();

					if (users.getFirstname() != null && !users.getFirstname().equals("")  ) {
						friendname = users.getFirstname().substring(0,1).toUpperCase() + users.getFirstname().substring(1).toLowerCase();
					} else {
						friendname = users.getEmailid().substring(0,1).toUpperCase() + users.getEmailid().substring(1).toLowerCase();
					}

				}

				if (createdby != null) {
					users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("emailid", createdby))
							.uniqueResult();

					if (!(users.getFirstname()).equalsIgnoreCase("") && users.getFirstname() != null) {
						requestorname = users.getFirstname().substring(0,1).toUpperCase() + users.getFirstname().substring(1).toLowerCase();
					} else {
						requestorname = users.getEmailid().substring(0,1).toUpperCase() + users.getEmailid().substring(1).toLowerCase();
					}

				}

				requestworkflow.setCompletionpercentage(completionpercentage);
				if (completionpercentage > 0 && completionpercentage < 99.9) {
					stage = 4;
					requestworkflow.setRequeststatus(stage);
				} else if (completionpercentage == 0) {
					stage = 2;
					requestworkflow.setRequeststatus(stage);
				} else if (completionpercentage == 100) {
					stage = 5;
					requestworkflow.setRequeststatus(stage);
					requestworkflow.setTeammembercompletiondate(new Date());
					
					
					sr.completerequest(createdby, requestorname, friendname, title, description, duedate, projectname,
							categoryname, typename, priority, weight, estimatedeffort);
				}
				requestworkflow.setModifiedby(userName);
				requestworkflow.setDatemodified(new Date());
				requestworkflow.setActualeffort(actualeffort);
				if(completiondate != null){
				requestworkflow.setCompletiondate(completiondate);
				}
				session.update(requestworkflow);

				if (message != null && !message.trim().equals("")) {
					requestnotes = new Requestnotes();
					requestnotes.setRequest(requestworkflow);
					requestnotes.setMessage(message);
					requestnotes.setCreatedby(userName);
					requestnotes.setCreatedon(new Date());
					requestnotes.setRequeststatus(false);
					requestnotes.setTeammemberstatus(true);
					session.save(requestnotes);

				}
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

	// show common task for teammember

	@SuppressWarnings("unchecked")
	@Override
	public List<UpdatestatusVo> getallProject(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<UpdatestatusVo> requestList = new ArrayList<UpdatestatusVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request = null;
		List<Integer> friendList = null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo = null;
		UpdatestatusVo updatestatusVo = null;

		List<requestNoteVo> requestnoteList = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp != null) {

				// Integer id1=usersTemp.getId();

				Criteria crit1 = session.createCriteria(Userfriendlist.class);
				crit1.add(Restrictions.eq("status", true));
				crit1.add(Restrictions.eq("usersByFriendid.id", usersTemp.getId()));
				crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setProjection(
						Projections.distinct(Projections.projectionList().add(Projections.property("id"))));
				friendList = (List<Integer>) crit1.list();

				List<Integer> projectList = null;
				if (friendList != null && friendList.size() != 0) {
					Criteria crit2 = session.createCriteria(Request.class);
					crit2.add(Restrictions.eq("status", true));
					crit2.add(Restrictions.in("userfriendlist.id", friendList));
					crit2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setProjection(Projections
							.distinct(Projections.projectionList().add(Projections.property("userproject.id"))));
					projectList = (List<Integer>) crit2.list();

				}
				List<Request> requesPojoList = null;
				if (projectList != null && projectList.size() != 0) {
					requesPojoList = (List<Request>) session.createCriteria(Request.class)
							.add(Restrictions.eq("status", true)).add(Restrictions.in("userproject.id", projectList))
							.list();
				}

				if (requesPojoList != null && requesPojoList.size() != 0) {
					for (Request requestDB : requesPojoList) {
						if (requestDB.getUserproject().getProjectaccess() == false) {
							String userCategory = "";
							String userProject = "";
							String userRequestType = "";
							String firstName = "";
							String lastName = "";
							String name = "";
							String email = "";

							userCategory = "";
							userProject = "";
							userRequestType = "";
							updatestatusVo = new UpdatestatusVo();
							firstName = "";
							lastName = "";
							name = "";
							email = "";

							requestnoteList = new ArrayList<requestNoteVo>();

							Hibernate.initialize(requestDB.getUsercategory());
							Hibernate.initialize(requestDB.getUserproject());
							Hibernate.initialize(requestDB.getUserrequesttype());
							Hibernate.initialize(requestDB.getUserfriendlist());
							Hibernate.initialize(requestDB.getRequestnoteses());

							if (requestDB != null && requestDB.getUsercategory() != null
									&& requestDB.getUsercategory().getCategory() != null) {
								userCategory = requestDB.getUsercategory().getCategory().getName() != null
										? requestDB.getUsercategory().getCategory().getName() : "";
							}

							if (requestDB != null && requestDB.getUserproject() != null
									&& requestDB.getUserproject().getProject() != null) {
								userProject = requestDB.getUserproject().getProject().getName() != null
										? requestDB.getUserproject().getProject().getName() : "";
							}

							if (requestDB != null && requestDB.getUserrequesttype() != null
									&& requestDB.getUserrequesttype().getRequesttype() != null) {
								userRequestType = requestDB.getUserrequesttype().getRequesttype() != null
										? requestDB.getUserrequesttype().getRequesttype().getName() : "";
							}

							if (requestDB != null && requestDB.getUserfriendlist() != null
									&& requestDB.getUserfriendlist().getUsersByFriendid() != null) {
								email = requestDB.getUserfriendlist().getUsersByFriendid().getEmailid() != null
										? requestDB.getUserfriendlist().getUsersByFriendid().getEmailid() : "";

								firstName = requestDB.getUserfriendlist().getUsersByFriendid().getFirstname() != null
										? requestDB.getUserfriendlist().getUsersByFriendid().getFirstname() : "";

								lastName = requestDB.getUserfriendlist().getUsersByFriendid().getLastname() != null
										? requestDB.getUserfriendlist().getUsersByFriendid().getLastname() : "";

								if (firstName != null && !firstName.trim().equals("")) {
									name = firstName.trim();
								}

								if (lastName != null && !lastName.trim().equals("")) {
									name = name + " " + lastName.trim();
								}
							}
							if (requestDB.getStatus() == true && (requestDB.getRequeststatus() == 2
									|| requestDB.getRequeststatus() == 4 || requestDB.getRequeststatus() == 5)) {
								updatestatusVo
										.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
								updatestatusVo.setDescription(
										requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
								updatestatusVo.setCompletiondate(requestDB.getCompletiondate() != null
										? Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate())
										: "");

								if (!name.equalsIgnoreCase("")) {
									updatestatusVo.setFriendName(name);
								} else {
									updatestatusVo.setFriendName(email);
								}

								updatestatusVo.setUsercategory(userCategory);
								updatestatusVo.setUserproject(userProject);
								updatestatusVo.setUserrequesttype(userRequestType);
								updatestatusVo.setCreatedby(requestDB.getCreatedby());
								updatestatusVo.setCompletionpercentage(requestDB.getCompletionpercentage());

								if (requestDB != null && requestDB.getStatus() != null
										&& requestDB.getStatus().booleanValue() == true) {
									updatestatusVo.setStatus("Active");
								} else {
									updatestatusVo.setStatus("In-Active");
								}

								updatestatusVo.setNewRequestId(requestDB.getId());
								if (requestDB.getRequestnoteses() != null
										&& requestDB.getRequestnoteses().size() != 0) {

									for (Requestnotes requestnotes : requestDB.getRequestnoteses()) {
										firstName = "";
										lastName = "";
										name = "";
										email = "";
										if (usersTemp != null && requestnotes.getCreatedby() != null) {
											usersTemp = (Users) session.createCriteria(Users.class)
													.add(Restrictions.eq("emailid", requestnotes.getCreatedby()))
													.uniqueResult();

											firstName = usersTemp.getFirstname() != null ? usersTemp.getFirstname()
													: "";

											lastName = usersTemp.getLastname() != null ? usersTemp.getLastname() : "";

											if (firstName != null && !firstName.trim().equals("")) {
												name = firstName.trim();
											}

											if (lastName != null && !lastName.trim().equals("")) {
												name = name + " " + lastName.trim();
											}

										}

										requestnoteVo = new requestNoteVo();
										requestnoteVo.setNoteId(requestnotes.getId());
										requestnoteVo.setMessage(requestnotes.getMessage() != null
												? requestnotes.getMessage().trim() : "");
										requestnoteVo.setCreatedon(requestnotes.getCreatedon() != null ? Dateconverter
												.convertDateToStringDDMMDDYYYY(requestnotes.getCreatedon()) : "");
										requestnoteVo.setTime(requestnotes.getCreatedon() != null
												? Dateconverter.convertTimeToStringhhmmss(requestnotes.getCreatedon())
												: "");

										requestnoteVo.setCreatedby(name != null ? name : "");
										requestnoteList.add(requestnoteVo);
										Collections.sort(requestnoteList, requestNoteVo.NoteIdComparator);
									}
								}

								updatestatusVo.setNoteList(requestnoteList);

								requestList.add(updatestatusVo);
							}
						}
					}
				}

				/*
				 * Hibernate.initialize(usersTemp.getUserprojects());
				 * Userproject userprojecttemp=null;
				 * if(usersTemp.getUserprojects() != null &&
				 * usersTemp.getUserprojects().size() !=0) { for(Userproject
				 * userproject:usersTemp.getUserprojects()){
				 * userprojecttemp=userproject;
				 * Hibernate.initialize(userprojecttemp.getRequests());
				 * 
				 * }
				 * 
				 * }
				 */

				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return requestList;
	}

	// for send data details in email

	@SuppressWarnings("unchecked")
	@Override
	public List<UpdatestatusVo> getupdatestatusDetailsforemail(String userName, String title, String description,
			String userproject, String usercategory, String userrequesttype, String createdby, String changedate,
			Float completionpercentage, Integer stage) throws Exception {
		// TODO Auto-generated method stub
		List<UpdatestatusVo> requestList = new ArrayList<UpdatestatusVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request = null;
		List<Integer> friendList = null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo = null;
		UpdatestatusVo updatestatusVo = null;

		List<requestNoteVo> requestnoteList = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp != null) {

				// Integer id1=usersTemp.getId();

				Criteria crit1 = session.createCriteria(Userfriendlist.class);
				crit1.add(Restrictions.eq("status", true));
				crit1.add(Restrictions.eq("usersByFriendid.id", usersTemp.getId()));
				crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setProjection(
						Projections.distinct(Projections.projectionList().add(Projections.property("id"))));
				friendList = (List<Integer>) crit1.list();

				List<Request> requesPojoList = null;
				if (friendList != null && friendList.size() != 0) {
					requesPojoList = (List<Request>) session.createCriteria(Request.class)
							.add(Restrictions.in("userfriendlist.id", friendList)).list();
				}

				String userCategory = "";
				String userProject = "";
				String userRequestType = "";
				String firstName = "";
				String lastName = "";
				String name = "";
				String emailid = "";

				if (requesPojoList != null && requesPojoList.size() != 0) {
					for (Request requestDB : requesPojoList) {
						userCategory = "";
						userProject = "";
						userRequestType = "";
						updatestatusVo = new UpdatestatusVo();
						firstName = "";
						lastName = "";
						name = "";
						emailid = "";

						requestnoteList = new ArrayList<requestNoteVo>();

						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						Hibernate.initialize(requestDB.getRequestnoteses());
						if (requestDB != null && requestDB.getUsercategory() != null
								&& requestDB.getUsercategory().getCategory() != null) {
							userCategory = requestDB.getUsercategory().getCategory().getName() != null
									? requestDB.getUsercategory().getCategory().getName() : "";
						}

						if (requestDB != null && requestDB.getUserproject() != null
								&& requestDB.getUserproject().getProject() != null) {
							userProject = requestDB.getUserproject().getProject().getName() != null
									? requestDB.getUserproject().getProject().getName() : "";
						}

						if (requestDB != null && requestDB.getUserrequesttype() != null
								&& requestDB.getUserrequesttype().getRequesttype() != null) {
							userRequestType = requestDB.getUserrequesttype().getRequesttype() != null
									? requestDB.getUserrequesttype().getRequesttype().getName() : "";
						}

						if (requestDB != null) {

							usersTemp = (Users) session.createCriteria(Users.class)
									.add(Restrictions.eq("emailid", requestDB.getCreatedby().trim()).ignoreCase())
									.uniqueResult();
							if (usersTemp != null) {
								emailid = usersTemp.getEmailid();
								firstName = usersTemp.getFirstname();
								lastName = usersTemp.getLastname();

								if (firstName != null && !firstName.trim().equals("")) {
									name = firstName.trim();
								}

								if (lastName != null && !lastName.trim().equals("")) {
									name = name + " " + lastName.trim();
								}
							}
						}

						if (requestDB.getStatus() == true
								&& (requestDB.getRequeststatus() == 2 || requestDB.getRequeststatus() == 4
										|| requestDB.getRequeststatus() == 5 || requestDB.getRequeststatus() == 8)) {
							updatestatusVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
							updatestatusVo.setDescription(
									requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
							updatestatusVo.setCompletiondate(requestDB.getCompletiondate() != null
									? Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");

							if (!name.equalsIgnoreCase("")) {
								updatestatusVo.setFriendName(name);
							} else {
								updatestatusVo.setFriendName(emailid);
							}
							updatestatusVo.setUsercategory(userCategory);
							updatestatusVo.setUserproject(userProject);
							updatestatusVo.setUserrequesttype(userRequestType);
							updatestatusVo.setCreatedby(requestDB.getCreatedby());
							updatestatusVo.setCompletionpercentage(requestDB.getCompletionpercentage());

							if (requestDB != null && requestDB.getStatus() != null
									&& requestDB.getStatus().booleanValue() == true) {
								updatestatusVo.setStatus("Active");
							} else {
								updatestatusVo.setStatus("In-Active");
							}

							updatestatusVo.setNewRequestId(requestDB.getId());

							requestList.add(updatestatusVo);
						}
					}
				}

				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return requestList;
	}

	// show close and cancel data in grid

	@SuppressWarnings("unchecked")
	@Override
	public List<UpdatestatusVo> getcompletedtaskDetails(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<UpdatestatusVo> requestList = new ArrayList<UpdatestatusVo>();
		Users usersTemp = null;
		Session session = null;
		Transaction tx = null;
		Request request = null;
		List<Integer> friendList = null;
		NewrequestVo newrequestVo = null;
		requestNoteVo requestnoteVo = null;
		UpdatestatusVo updatestatusVo = null;

		List<requestNoteVo> requestnoteList = null;

		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", userName.toLowerCase().trim()).ignoreCase()).uniqueResult();
			if (usersTemp != null) {

				// Integer id1=usersTemp.getId();

				Criteria crit1 = session.createCriteria(Userfriendlist.class);
				crit1.add(Restrictions.eq("status", true));
				crit1.add(Restrictions.eq("usersByFriendid.id", usersTemp.getId()));
				crit1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setProjection(
						Projections.distinct(Projections.projectionList().add(Projections.property("id"))));
				friendList = (List<Integer>) crit1.list();

				List<Request> requesPojoList = null;
				if (friendList != null && friendList.size() != 0) {
					requesPojoList = (List<Request>) session.createCriteria(Request.class)
							.add(Restrictions.in("userfriendlist.id", friendList)).list();
				}

				String userCategory = "";
				String userProject = "";
				String userRequestType = "";
				String firstName = "";
				String lastName = "";
				String name = "";
				if (requesPojoList != null && requesPojoList.size() != 0) {
					for (Request requestDB : requesPojoList) {
						userCategory = "";
						userProject = "";
						userRequestType = "";
						updatestatusVo = new UpdatestatusVo();
						firstName = "";
						lastName = "";
						name = "";
						requestnoteList = new ArrayList<requestNoteVo>();

						Hibernate.initialize(requestDB.getUsercategory());
						Hibernate.initialize(requestDB.getUserproject());
						Hibernate.initialize(requestDB.getUserrequesttype());
						Hibernate.initialize(requestDB.getUserfriendlist());
						Hibernate.initialize(requestDB.getRequestnoteses());
						if (requestDB != null && requestDB.getUsercategory() != null
								&& requestDB.getUsercategory().getCategory() != null) {
							userCategory = requestDB.getUsercategory().getCategory().getName() != null
									? requestDB.getUsercategory().getCategory().getName() : "";
						}

						if (requestDB != null && requestDB.getUserproject() != null
								&& requestDB.getUserproject().getProject() != null) {
							userProject = requestDB.getUserproject().getProject().getName() != null
									? requestDB.getUserproject().getProject().getName() : "";
						}

						if (requestDB != null && requestDB.getUserrequesttype() != null
								&& requestDB.getUserrequesttype().getRequesttype() != null) {
							userRequestType = requestDB.getUserrequesttype().getRequesttype() != null
									? requestDB.getUserrequesttype().getRequesttype().getName() : "";
						}

						if (requestDB != null && requestDB.getUserfriendlist() != null
								&& requestDB.getUserfriendlist().getUsersByFriendid() != null) {
							firstName = requestDB.getUserfriendlist().getUsersByFriendid().getFirstname() != null
									? requestDB.getUserfriendlist().getUsersByFriendid().getFirstname() : "";

							lastName = requestDB.getUserfriendlist().getUsersByFriendid().getLastname() != null
									? requestDB.getUserfriendlist().getUsersByFriendid().getLastname() : "";

							if (firstName != null && !firstName.trim().equals("")) {
								name = firstName.trim();
							}

							if (lastName != null && !lastName.trim().equals("")) {
								name = name + " " + lastName.trim();
							}
						}
						if (requestDB.getStatus() == true && requestDB.getRequeststatus() == 8
								|| requestDB.getRequeststatus() == 6) {
							updatestatusVo.setTitle(requestDB.getTitle() != null ? requestDB.getTitle().trim() : "");
							updatestatusVo.setDescription(
									requestDB.getDescription() != null ? requestDB.getDescription().trim() : "");
							updatestatusVo.setCompletiondate(requestDB.getCompletiondate() != null
									? Dateconverter.convertDateToStringDDMMDDYYYY(requestDB.getCompletiondate()) : "");
							updatestatusVo.setFriendName(name);
							updatestatusVo.setUsercategory(userCategory);
							updatestatusVo.setUserproject(userProject);
							updatestatusVo.setUserrequesttype(userRequestType);
							updatestatusVo.setCreatedby(requestDB.getCreatedby());
							updatestatusVo.setCompletionpercentage(requestDB.getCompletionpercentage());

							if (requestDB.getRequeststatus() == 2) {
								updatestatusVo.setStage("Accepted");
							} else if (requestDB.getRequeststatus() == 4) {
								updatestatusVo.setStage("In-progress");
							}

							else if (requestDB.getRequeststatus() == 5) {
								updatestatusVo.setStage("Completed");
							} else if (requestDB.getRequeststatus() == 6) {
								updatestatusVo.setStage("Cancel");
							} else if (requestDB.getRequeststatus() == 7) {
								updatestatusVo.setStage("Hold");
							} else if (requestDB.getRequeststatus() == 8) {
								updatestatusVo.setStage("Close");
							}

							if (requestDB != null && requestDB.getStatus() != null
									&& requestDB.getStatus().booleanValue() == true) {
								updatestatusVo.setStatus("Active");
							} else {
								updatestatusVo.setStatus("In-Active");
							}

							updatestatusVo.setNewRequestId(requestDB.getId());
							if (requestDB.getRequestnoteses() != null && requestDB.getRequestnoteses().size() != 0) {

								for (Requestnotes requestnotes : requestDB.getRequestnoteses()) {
									firstName = "";
									lastName = "";
									name = "";

									if (usersTemp != null && requestnotes.getCreatedby() != null) {
										usersTemp = (Users) session.createCriteria(Users.class)
												.add(Restrictions.eq("emailid", requestnotes.getCreatedby()))
												.uniqueResult();

										firstName = usersTemp.getFirstname() != null ? usersTemp.getFirstname() : "";

										lastName = usersTemp.getLastname() != null ? usersTemp.getLastname() : "";

										if (firstName != null && !firstName.trim().equals("")) {
											name = firstName.trim();
										}

										if (lastName != null && !lastName.trim().equals("")) {
											name = name + " " + lastName.trim();
										}

									}

									requestnoteVo = new requestNoteVo();
									requestnoteVo.setNoteId(requestnotes.getId());
									requestnoteVo.setMessage(
											requestnotes.getMessage() != null ? requestnotes.getMessage().trim() : "");
									requestnoteVo.setCreatedon(requestnotes.getCreatedon() != null
											? Dateconverter.convertDateToStringDDMMDDYYYY(requestnotes.getCreatedon())
											: "");
									requestnoteVo.setTime(requestnotes.getCreatedon() != null
											? Dateconverter.convertTimeToStringhhmmss(requestnotes.getCreatedon())
											: "");

									requestnoteVo.setCreatedby(name != null ? name : "");
									requestnoteList.add(requestnoteVo);
									Collections.sort(requestnoteList, requestNoteVo.NoteIdComparator);
								}
							}

							updatestatusVo.setNoteList(requestnoteList);

							requestList.add(updatestatusVo);
						}
					}
				}

				tx.commit();
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return requestList;
	}


	
	// Send email for daily duedate before two days
	@Override
	public List<dailyDuedatewisesendRequestVo> getduedatesendrequestonteammember(String emailid) throws Exception {
		// TODO Auto-generated method stub

		List<dailyDuedatewisesendRequestVo> requestList1 = new ArrayList<dailyDuedatewisesendRequestVo>();
		Users usersTemp = null;
		Users usersTemp1 = null;
		Session session = null;
		Transaction tx = null;
		dailyDuedatewisesendRequestVo dailyduedatewisesendrequestVo = null;
		Updaterequestquery updaterequestquery = new Updaterequestquery();
		List<dailyDuedatewisesendRequestVo> gettotalrequestduedatewiseonteammember = null;
		try {
			gettotalrequestduedatewiseonteammember = updaterequestquery
					.getduedaterequestonteammember(emailid.toLowerCase().trim());

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			usersTemp = (Users) session.createCriteria(Users.class)
					.add(Restrictions.eq("emailid", emailid.toLowerCase().trim()).ignoreCase()).uniqueResult();

			if (usersTemp != null) {

				for (dailyDuedatewisesendRequestVo dailysendrequesyDB : gettotalrequestduedatewiseonteammember) {

					dailyduedatewisesendrequestVo = new dailyDuedatewisesendRequestVo();

					dailyduedatewisesendrequestVo.setTitle(dailysendrequesyDB.getTitle());
					dailyduedatewisesendrequestVo.setTeammemberemailid(dailysendrequesyDB.getTeammemberemailid());
					dailyduedatewisesendrequestVo.setCompletionpercentage(dailysendrequesyDB.getCompletionpercentage());

					String teammemberemailid = dailyduedatewisesendrequestVo.getTeammemberemailid();

					usersTemp1 = (Users) session.createCriteria(Users.class)
							.add(Restrictions.eq("emailid", teammemberemailid.toLowerCase().trim()).ignoreCase())
							.uniqueResult();

					if (usersTemp1 != null) {

						if (usersTemp1.getFirstname() != null) {
							dailyduedatewisesendrequestVo.setName(usersTemp1.getFirstname());
						} else {
							System.out.println(dailyduedatewisesendrequestVo.getTeammemberemailid());
							dailyduedatewisesendrequestVo.setName(teammemberemailid);
						}
					}

					requestList1.add(dailyduedatewisesendrequestVo);

				}
				tx.commit();

			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return requestList1;
	}
		
		
	@SuppressWarnings("unused")
	@Override
	public void getRequestStatusUpdateByrequestId(String requestId) throws Exception {
		// TODO Auto-generated method stub

		Requestnotes requestnotes = null;
		Session session = null;
		Transaction tx = null;
		requestNoteVo requestnoteVo = null;
		Request request = null;
		// Requestnotes requestnotes=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			Integer getrequestid = Integer.parseInt(requestId);

			@SuppressWarnings("unchecked")
			List<Requestnotes> requestnotestemp = (List<Requestnotes>) session.createCriteria(Requestnotes.class)
					.add(Restrictions.eq("request.id", getrequestid)).list();

			if (requestnotestemp != null) {
				List<Integer> allid = null;

				for (Requestnotes requestnotesDb : requestnotestemp) {

					Requestnotes requestnotesDb1 = (Requestnotes) session.createCriteria(Requestnotes.class)
							.add(Restrictions.eq("id", requestnotesDb.getId())).uniqueResult();
					System.out.println(requestnotesDb1.getId());
					System.out.println(requestnotesDb1.getId());
					if (requestnotesDb1 != null) {

						System.out.println(requestnotesDb1.getId());

						// requestnotes.setRequeststatus(true);
						requestnotesDb1.setTeammemberstatus(true);
						session.update(requestnotesDb1);

						// System.out.println(id);
					}

				}
				tx.commit();
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public int getUpdateRequestById(Integer oldValue, Integer completionpercentage, Integer updateRequestId, String userName) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = null;
		Transaction tx = null;
		Request requestworkflow = null;
		Users users = null;
		Userproject userproject = null;
		Usercategory usercategory = null;
		Userrequesttype userrequesttype = null;
        Integer stage=0;
		int result = 0;
		Requestnotes requestnotes = null;
		try {
			completerequestsend sr = new completerequestsend();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			requestworkflow = (Request) session.createCriteria(Request.class)
					.add(Restrictions.eq("id", Integer.valueOf(updateRequestId))).uniqueResult();

			if (requestworkflow != null) {
				
				
				if(requestworkflow.getCompletiondate() != null){

				String projectname = "";
				String categoryname = "";
				String typename = "";
				String title = "";
				String description = "";
				String priority = "";
				String estimatedeffort = "";
				Integer weight = 0;
				String duedate = "";
				String friendname = "";
				String requestorname = "";
				String createdby = "";
				Date completiondate1 = null;

				
				System.out.println("compleation date"+ requestworkflow.getCompletiondate());
				
				
				userproject = requestworkflow.getUserproject();
				usercategory = requestworkflow.getUsercategory();
				userrequesttype = requestworkflow.getUserrequesttype();
				title = requestworkflow.getTitle();
				
				description = requestworkflow.getDescription();
				
				priority = requestworkflow.getPriority();
				estimatedeffort = requestworkflow.getEstimatedeffort();
				weight = requestworkflow.getWeightage();
				completiondate1 = requestworkflow.getCompletiondate();
				duedate = Dateconverter.convertDateToStringDDMMDDYYYY(completiondate1);
				createdby = requestworkflow.getCreatedby();
				
				
				
				
				
				
				if (userproject != null) {
					projectname = userproject.getProject().getName();
				}
				if (usercategory != null) {
					categoryname = usercategory.getCategory().getName();
				}
				if (userrequesttype != null) {
					typename = userrequesttype.getRequesttype().getName();
				}
				

				if (userName != null) {

					users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("emailid", userName))
							.uniqueResult();

					if (users.getFirstname() != null && !users.getFirstname().equals("")  ) {
						friendname = users.getFirstname().substring(0,1).toUpperCase() + users.getFirstname().substring(1).toLowerCase();
					} else {
						friendname = users.getEmailid().substring(0,1).toUpperCase() + users.getEmailid().substring(1).toLowerCase();
					}

				}

				if (createdby != null) {
					users = (Users) session.createCriteria(Users.class).add(Restrictions.eq("emailid", createdby))
							.uniqueResult();

					if (!(users.getFirstname()).equalsIgnoreCase("") && users.getFirstname() != null) {
						requestorname = users.getFirstname().substring(0,1).toUpperCase() + users.getFirstname().substring(1).toLowerCase();
					} else {
						requestorname = users.getEmailid().substring(0,1).toUpperCase() + users.getEmailid().substring(1).toLowerCase();
					}

				}

				requestworkflow.setCompletionpercentage(completionpercentage);
				if (completionpercentage > 0 && completionpercentage < 99.9) {
					stage = 4;
					requestworkflow.setRequeststatus(stage);
				} else if (completionpercentage == 0) {
					stage = 2;
					requestworkflow.setRequeststatus(stage);
				} else if (completionpercentage == 100) {
					stage = 5;
					requestworkflow.setRequeststatus(stage);
					requestworkflow.setTeammembercompletiondate(new Date());
					
					
					sr.completerequest(createdby, requestorname, friendname, title, description, duedate, projectname,
							categoryname, typename, priority, weight, estimatedeffort);
				}
				requestworkflow.setModifiedby(userName);
				requestworkflow.setDatemodified(new Date());
				//requestworkflow.setActualeffort(actualeffort);
								
			
				session.update(requestworkflow);
				
				result = 1;
				}else{
					result = 2;
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
		
		
		
		
	
	/*
	 * public static void main(String args[]) throws Exception { try { //
	 * MonthlysummeryemailVo monthlysummeryemailVo=new MonthlysummeryemailVo();
	 * UpdatestatusImpl ne =new UpdatestatusImpl();
	 * ne.getduedatesendrequestonteammember("hemantraghav012@gmail.com");
	 * 
	 * 
	 * 
	 * 
	 * } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * }
	 */

		
}
