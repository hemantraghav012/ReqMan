package com.reqman.beans;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.reqman.dao.AccountuserInterface;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.dao.NewRequestqueryInterface;
import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.AccountuserImpl;
import com.reqman.daoimpl.FriendMasterImpl;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.query.NewRequestquery;
import com.reqman.pojo.Request;
import com.reqman.util.Dateconverter;
import com.reqman.util.JavaSoundRecorder;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.AccountuserVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.QuickcreaterequestVo;
import com.reqman.vo.UserVo;
import com.reqman.vo.quickrequestvalidation;
import com.reqman.vo.requestNoteVo;
import com.sun.el.parser.ParseException;

@ManagedBean(name = "createrequest", eager = true)
//@RequestScoped
@ViewScoped
public class CreateRequestbean implements Serializable {
	private static final long serialVersionUID = 3076255353187837257L;

	private String title;
	private String description;
	private Integer usercategory;
	private Integer userproject;
	private Integer userrequesttype;
	private Integer[] userfriendlist;
	private UploadedFile attachment;
	private Date completiondate;
	private List<Request> request;
	private String requestId;
	private Boolean status;
	private StreamedContent file;
	private NewrequestVo selectedReuest;
	private Integer stage;
	private BarChartModel barModel;
	private BarChartModel barModelcloser;
	private BarChartModel barModelaverage;
	private HorizontalBarChartModel horizontalBarModel;
	private String currentDate;
	private String onemonthpastDate;
	private int completionpercentage;
	private String message;
	private Date startDate;
	private Date endDate;
	private Integer userfriend;
	private Integer rating;
	private String feedback;
	private List<UserVo> friendList;
	private PieChartModel piechart;
	private List<UserVo> selectedUsers;
	private Integer[] searchteammember;
	private Integer[] searchcategory;
	private Integer[] searchproject;
	private Integer[] searchtype;
	private Integer[] searchstage;
	private String actualeffort;
	private String estimatedeffort;
	private String priority;
	private Integer weightage;
	 private String titlelist1;
	 private String titlelist2;
	 private String titlelist3;
	 private String titlelist4;
	 private String titlelist5;
	 private String titlelist6;
	 private String titlelist7;
	 private String titlelist8;
	 private String titlelist9;
	 private String titlelist10;
	
		private Integer[] userfriendlist1;
		private Integer[] userfriendlist2;
		private Integer[] userfriendlist3;
		private Integer[] userfriendlist4;
		private Integer[] userfriendlist5;
		private Integer[] userfriendlist6;
		private Integer[] userfriendlist7;
		private Integer[] userfriendlist8;
		private Integer[] userfriendlist9;
		private Integer[] userfriendlist10;
		
		private List<UserVo> selectedUsers1;
		private List<UserVo> selectedUsers2;
		private List<UserVo> selectedUsers3;
		private List<UserVo> selectedUsers4;
		private List<UserVo> selectedUsers5;
		private List<UserVo> selectedUsers6;
		private List<UserVo> selectedUsers7;
		private List<UserVo> selectedUsers8;
		private List<UserVo> selectedUsers9;
		private List<UserVo> selectedUsers10;
		
		private Date completiondate1;
		private Date completiondate2;
		private Date completiondate3;
		private Date completiondate4;
		private Date completiondate5;
		private Date completiondate6;
		private Date completiondate7;
		private Date completiondate8;
		private Date completiondate9;
		private Date completiondate10;
		private byte[] soundrecord;
		
		
	private NewrequestVo newrequestVo = new NewrequestVo();
	private List<QuickcreaterequestVo> quickrequestList1 = new ArrayList<QuickcreaterequestVo>();
	private List<quickrequestvalidation> quickrequestListval = new ArrayList<quickrequestvalidation>();
	private NewRequestqueryInterface newrequestqueryInterface = new NewRequestquery();
	private NewrequestInterface newrequestInterface = new NewrequestImpl();
	private FriendMasterInterface friendMasterInterface = new FriendMasterImpl();
	private List<NewrequestVo> newrequestList = new ArrayList<NewrequestVo>();
	private List<NewrequestVo> filteredRequestList = new ArrayList<NewrequestVo>();
	private AccountuserInterface accountuserInterface = new AccountuserImpl();
	
	 private AccountuserVo accountuserVo = new AccountuserVo();
	 private Boolean imagestatus;
	
	  

	 
	 
	 
	 
	@PostConstruct
	public void init() {
		try {

			newrequestList = new ArrayList<NewrequestVo>();			
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("--usersession--userName-->" + userName);
			
			if (startDate == null) {
				startDate = Dateconverter.getPreToPreMonthDate(new Date());
			}
			if (endDate == null) {
				endDate = new Date();
			}			
			newrequestList = newrequestInterface.getNewrequestDetails(userName.toLowerCase().trim(),
					startDate, endDate);
			
			setFilteredRequestList(newrequestList);
			
			createBarModels();
			createPieModels();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// for get date from request page
	public String daterange() {
		try {
			newrequestList = new ArrayList<NewrequestVo>();
			System.out.println("--create new request-->");
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			newrequestList = newrequestInterface.getNewrequestDetails(userName.toLowerCase().trim(),
					startDate, endDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "request";
	}

	// for open request page and requestgrid
	public String createRequestPage() {
		try {
			newrequestList = new ArrayList<NewrequestVo>();
			System.out.println("--create new request-->");
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("--usersession--userName-->" + userName);		
			
			accountuserVo = accountuserInterface.getAccountuserById(userName.toLowerCase().trim());      	

        	System.out.println("modify action"+userName);	        		
        		setImagestatus(accountuserVo.getImageStatus());	
			
			newrequestList = newrequestInterface.getNewrequestDetails(userName.toLowerCase().trim(),
					startDate, endDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "request";
	}

	// for open create new request page
	public String createRequest() {
		try {
			newrequestList = new ArrayList<NewrequestVo>();
			System.out.println("newrequestfriend");
		} catch (Exception e) {
			e.printStackTrace();
		}    
		return "newrequestfriend";
	}
	
	// for open create new request page
		public String quickcreateRequest() {
			try {
				newrequestList = new ArrayList<NewrequestVo>();
				System.out.println("quickcreateRequest-newrequestfriend");
			} catch (Exception e) {
				e.printStackTrace();
			}    
			return "quickcreateRequest";
		}

		
		
		
	// for save method
	public String save() {
		int result = 0;
		UserSession usersession = new UserSession();
		try {
			newrequestList = new ArrayList<NewrequestVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");

			System.out.println("friendlist" + selectedUsers);
			System.out.println("friendlist" + friendList);

			if (selectedUsers != null && selectedUsers.size() != 0) {
				userfriendlist = new Integer[selectedUsers.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist[count] = userVo.getUserId();

						count++;
					}
				}
			}

			
			//soundrecord = CreateRequestbean.readBytesFromFile(filedata1);
			result = newrequestInterface.save(title, description, usercategory,soundrecord,description,
					userproject, userrequesttype, attachment, userName.toLowerCase().trim(),
					completiondate, userfriendlist,estimatedeffort,weightage,priority);

			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Request already exist",
								"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));
				return "newrequestfriend";
			}
			if (result == 2) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_WARN,
										"Request already exist and in-active. ",
										"please activate by using modify request"));
				return "newrequestfriend";
			}
			if (result == 3) {
				newrequestList = newrequestInterface.getNewrequestDetails(
						userName, startDate, endDate);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Request created  successfully.",
								""));

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error " + e.getMessage(), "Server Error "
									+ e.getMessage()));
			return "newrequestfriend";
		}
		return "request";
	}

	
	
	 
	
	
	public void validateFile(FacesContext ctx,  UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        UploadedFile file = (UploadedFile)value;
        int fileByte = file.getContents().length;
        if(fileByte != 0){
        if(fileByte > 11000000){
            msgs.add(new FacesMessage("Too big must be at most 10MB"));
        }
        if (!(file.getContentType().startsWith("image"))) {
          
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
        }else{
        	
        }
    }
	
	
	 public void updateRequestnotesStatus(){
		 
			int result=0;
			 try
		        {
		        	System.out.println("modify action"+requestId);
		            
		        	setRequestId(requestId);
		        	
		        	newrequestInterface.getRequestStatusUpdateByrequestId(requestId);
		        
		        

		        }
		        catch(Exception e){
		        	e.printStackTrace();
		        }
		        
		    }
	
	 public void onCellEdit(CellEditEvent event) {
	     int result = 0;
	     String oldValue = "";
	     String newValue = "";
	     Integer updaterequestId = 0;
		 try
		 {
			 HttpSession session = SessionUtils.getSession();
				String userName = (String) session.getAttribute("username");
			 
			 oldValue = (String)event.getOldValue();
		     newValue = (String)event.getNewValue();
		     updaterequestId = (Integer) event.getComponent().getAttributes().get("updateRequestId");
	         System.out.println("updatecategoryId"+updaterequestId);
	        if(newValue != null && !newValue.equals(oldValue)) 
	        {
	        	result = newrequestInterface.updateRequestonGrid(oldValue, newValue, updaterequestId,userName);
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"The stage of the Request has been changed successfully.","");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	            
	        }
	        newrequestList = newrequestInterface.getNewrequestDetails(userName.toLowerCase().trim(),
					startDate, endDate);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error", e.getMessage().toString());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		 }
	       
	    }
	 
	 
	  
	
	// for show value in modify request page
	public void modifyAction() {
		try {
			System.out.println("modify action" + requestId);
			// addMessage("Welcome to Primefaces!!");
			setRequestId(requestId);
			newrequestVo = newrequestInterface.getRequestById(requestId);
			System.out.println("--rating	-" + rating);
			System.out.println("--feedback	-" + feedback);
			if (newrequestVo != null && newrequestVo.getStatus().trim().equalsIgnoreCase("Active")) {
				setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle() : "");
				setDescription(newrequestVo.getDescription() != null ? newrequestVo.getDescription() : "");
				setCompletiondate(newrequestVo.getCompletiondate());
				setUserproject(newrequestVo.getProject());
				setUsercategory(newrequestVo.getCategory());
				setUserfriend(newrequestVo.getUserfriend());
				setRating(newrequestVo.getRating());
				setFeedback(newrequestVo.getFeedback());
				setEstimatedeffort(newrequestVo.getEstimatedeffort());
				setWeightage(newrequestVo.getWeightage());
				setPriority(newrequestVo.getPriority());
				setActualeffort(newrequestVo.getActualeffort());
				setCompletionpercentage(newrequestVo.getCompletionpercentage());

				if (newrequestVo.getStage().trim().equalsIgnoreCase("Requested")) {
					setStage(1);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Accepted")) {
					setStage(2);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Returned")) {
					setStage(3);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("In-progress")) {
					setStage(4);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Completed")) {
					setStage(5);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Cancel")) {
					setStage(6);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Hold")) {
					setStage(7);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Close")) {
					setStage(8);
				}

				setStatus(true);

			} else {
				setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle() : "");
				setDescription(newrequestVo.getDescription() != null ? newrequestVo.getDescription() : "");
				setCompletiondate(newrequestVo.getCompletiondate());
				setUserproject(newrequestVo.getProject());
				setUsercategory(newrequestVo.getCategory());
				setUserrequesttype(newrequestVo.getRequesttype());
				setUserfriend(newrequestVo.getUserfriend());
				setRating(newrequestVo.getRating());
				setFeedback(newrequestVo.getFeedback());
				setEstimatedeffort(newrequestVo.getEstimatedeffort());
				setWeightage(newrequestVo.getWeightage());
				setPriority(newrequestVo.getPriority());
				setActualeffort(newrequestVo.getActualeffort());
				setCompletionpercentage(newrequestVo.getCompletionpercentage());
				
				if (newrequestVo.getStage().trim().equalsIgnoreCase("Requested")) {
					setStage(1);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Accepted")) {
					setStage(2);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Returned")) {
					setStage(3);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("In-progress")) {
					setStage(4);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Completed")) {
					setStage(5);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Cancel")) {
					setStage(6);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Hold")) {
					setStage(7);
				} else if (newrequestVo.getStage().trim().equalsIgnoreCase("Close")) {
					setStage(8);
				}

				setStatus(false);
			}

			FacesContext.getCurrentInstance().getExternalContext().dispatch("modifyrequest.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// for update request
	public String updateRequest() {
		int result = 0;
		try {

			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("--usersession--userName-->" + userName);
			System.out.println("--rating	-" + rating);
			System.out.println("--feedback	-" + feedback);
			result = newrequestInterface.updateRequestById(requestId, status, description, completiondate, attachment,
					completionpercentage, stage, message, userName.toLowerCase().trim(), userproject, usercategory,
					userrequesttype, userfriend, rating, feedback, estimatedeffort, weightage, priority);

			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Problem while modifying the Category", ""));
				return "modifyrequest.xhtml";
			}

			if (result == 1) {
				newrequestList = newrequestInterface.getNewrequestDetails(userName, startDate, endDate);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Request updated  successfully.", ""));

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Problem while modifying the Category", ""));
			return "modifyrequest.xhtml";
		}
		return "request";
	}

	//request grid upper side showing image 
	public void update(){
		int result = 0;
		try {
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");

			result = accountuserInterface.updateimagestatus(userName, imagestatus);

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Problem while modifying the Category", ""));

		}

		// return "request";
	}
	
	
	/*   Quick Request meathod Start*/
	
	public String quicksubmit() {

		int result = 0;
		QuickcreaterequestVo quickcreaterequestVo1 = null;
		QuickcreaterequestVo quickcreaterequestVo2 = null;
		QuickcreaterequestVo quickcreaterequestVo3 = null;
		QuickcreaterequestVo quickcreaterequestVo4 = null;
		QuickcreaterequestVo quickcreaterequestVo5 = null;
		QuickcreaterequestVo quickcreaterequestVo6 = null;
		QuickcreaterequestVo quickcreaterequestVo7 = null;
		QuickcreaterequestVo quickcreaterequestVo8 = null;
		QuickcreaterequestVo quickcreaterequestVo9 = null;
		QuickcreaterequestVo quickcreaterequestVo10 = null;

		try {
			newrequestList = new ArrayList<NewrequestVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");

			if (titlelist1 != null && selectedUsers1 != null && selectedUsers1.size() != 0) {
				userfriendlist1 = new Integer[selectedUsers1.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers1) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist1[count] = userVo.getUserId();

						count++;

					}
					quickcreaterequestVo1 = new QuickcreaterequestVo();
					quickcreaterequestVo1.setTitlelist(titlelist1);
					quickcreaterequestVo1.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo1.setCompletiondate(completiondate1);
					quickrequestList1.add(quickcreaterequestVo1);
				}

			}

			if (titlelist2 != null && selectedUsers2 != null && selectedUsers2.size() != 0) {
				userfriendlist2 = new Integer[selectedUsers2.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers2) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist2[count] = userVo.getUserId();

						count++;
					}

					quickcreaterequestVo2 = new QuickcreaterequestVo();

					quickcreaterequestVo2.setTitlelist(titlelist2);
					quickcreaterequestVo2.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo2.setCompletiondate(completiondate2);

					quickrequestList1.add(quickcreaterequestVo2);
				}

			}
			if (titlelist3 != null && selectedUsers3 != null && selectedUsers3.size() != 0) {
				userfriendlist3 = new Integer[selectedUsers3.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers3) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist3[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo3 = new QuickcreaterequestVo();

					quickcreaterequestVo3.setTitlelist(titlelist3);
					quickcreaterequestVo3.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo3.setCompletiondate(completiondate3);

					quickrequestList1.add(quickcreaterequestVo3);
				}

			}

			if (titlelist4 != null && selectedUsers4 != null && selectedUsers4.size() != 0) {
				userfriendlist4 = new Integer[selectedUsers4.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers4) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist4[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo4 = new QuickcreaterequestVo();

					quickcreaterequestVo4.setTitlelist(titlelist4);
					quickcreaterequestVo4.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo4.setCompletiondate(completiondate4);

					quickrequestList1.add(quickcreaterequestVo4);
				}

			}

			if (titlelist5 != null && selectedUsers5 != null && selectedUsers5.size() != 0) {
				userfriendlist5 = new Integer[selectedUsers5.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers5) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist5[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo5 = new QuickcreaterequestVo();

					quickcreaterequestVo5.setTitlelist(titlelist5);
					quickcreaterequestVo5.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo5.setCompletiondate(completiondate5);

					quickrequestList1.add(quickcreaterequestVo5);
				}

			}

			if (titlelist6 != null && selectedUsers6 != null && selectedUsers6.size() != 0) {
				userfriendlist6 = new Integer[selectedUsers6.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers6) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist6[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo6 = new QuickcreaterequestVo();

					quickcreaterequestVo6.setTitlelist(titlelist6);
					quickcreaterequestVo6.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo6.setCompletiondate(completiondate6);

					quickrequestList1.add(quickcreaterequestVo6);
				}

			}

			if (titlelist7 != null && selectedUsers7 != null && selectedUsers7.size() != 0) {
				userfriendlist7 = new Integer[selectedUsers7.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers7) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist7[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo7 = new QuickcreaterequestVo();

					quickcreaterequestVo7.setTitlelist(titlelist7);
					quickcreaterequestVo7.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo7.setCompletiondate(completiondate7);

					quickrequestList1.add(quickcreaterequestVo7);
				}

			}

			if (titlelist8 != null && selectedUsers8 != null && selectedUsers8.size() != 0) {
				userfriendlist8 = new Integer[selectedUsers8.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers8) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist8[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo8 = new QuickcreaterequestVo();

					quickcreaterequestVo8.setTitlelist(titlelist8);
					quickcreaterequestVo8.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo8.setCompletiondate(completiondate8);

					quickrequestList1.add(quickcreaterequestVo8);
				}

			}

			if (titlelist9 != null && selectedUsers9 != null && selectedUsers9.size() != 0) {
				userfriendlist9 = new Integer[selectedUsers9.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers9) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist9[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo9 = new QuickcreaterequestVo();

					quickcreaterequestVo9.setTitlelist(titlelist9);
					quickcreaterequestVo9.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo9.setCompletiondate(completiondate9);

					quickrequestList1.add(quickcreaterequestVo9);
				}

			}

			if (titlelist10 != null && selectedUsers10 != null && selectedUsers10.size() != 0) {
				userfriendlist10 = new Integer[selectedUsers10.size()];
				int count = 0;
				for (UserVo userVo : selectedUsers10) {
					if (userVo != null && userVo.getUserId() != null) {
						userfriendlist10[count] = userVo.getUserId();

						count++;
					}
					quickcreaterequestVo10 = new QuickcreaterequestVo();

					quickcreaterequestVo10.setTitlelist(titlelist10);
					quickcreaterequestVo10.setUserfriendlist(userVo.getUserId());
					quickcreaterequestVo10.setCompletiondate(completiondate10);

					quickrequestList1.add(quickcreaterequestVo10);
				}

			}

			result = newrequestInterface.quickrequestsave(quickrequestList1, userName);

			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Request already exist",
						"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));
				return "quickcreateRequest";
			}
			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Request already exist and in-active. ", "please activate by using modify request"));
				return "quickcreateRequest";
			}
			if (result == 3) {
				newrequestList = newrequestInterface.getNewrequestDetails(userName, startDate, endDate);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Request created  successfully.", ""));

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Server Error " + e.getMessage(), "Server Error " + e.getMessage()));
			return "quickcreateRequest";
		}
		return "request";

	}
	
	
	public void handleKeyEvent() {
		int result = 0;

		try {
			newrequestList = new ArrayList<NewrequestVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");

			if (title != null && selectedUsers != null && selectedUsers.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(title, selectedUsers, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));

				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name. ", ""));

					}
				}

			}

			if (titlelist1 != null && selectedUsers1 != null && selectedUsers1.size() != 0) {

				result = newrequestInterface.quickrequestcheckval(titlelist1, selectedUsers1, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));

				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist2 != null && selectedUsers2 != null && selectedUsers2.size() != 0) {

				result = newrequestInterface.quickrequestcheckval(titlelist2, selectedUsers2, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist3 != null && selectedUsers3 != null && selectedUsers3.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist3, selectedUsers3, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist4 != null && selectedUsers4 != null && selectedUsers4.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist4, selectedUsers4, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist5 != null && selectedUsers5 != null && selectedUsers5.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist5, selectedUsers5, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist6 != null && selectedUsers6 != null && selectedUsers6.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist6, selectedUsers6, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist7 != null && selectedUsers7 != null && selectedUsers7.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist7, selectedUsers7, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist8 != null && selectedUsers8 != null && selectedUsers8.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist8, selectedUsers8, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist9 != null && selectedUsers9 != null && selectedUsers9.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist9, selectedUsers9, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

			if (titlelist10 != null && selectedUsers10 != null && selectedUsers10.size() != 0) {
				result = newrequestInterface.quickrequestcheckval(titlelist10, selectedUsers10, userName);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist",
							"This request title already exists in  requested, send back ,in-progress or hold stage. Please choose another title or re-use the existing request."));

				}
				if (result == 2) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Request already exist and in-active. ", "please activate by using modify request"));
				}

				if (result == 3) {
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Please enter title and  task owner's name ", ""));

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Server Error " + e.getMessage(), "Server Error " + e.getMessage()));

		}

	}
	
	
	
	
	
	
	// record duration, in milliseconds
	   public static  long RECORD_TIME = 10000;  // 1 minute
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");  
	  
	 
	   // the line from which audio data is captured
	    TargetDataLine line;
	    
	    /**
	     * Defines an audio format
	     */
	    AudioFormat getAudioFormat() {
	        float sampleRate = 16000;
	        int sampleSizeInBits = 8;
	        int channels = 2;
	        boolean signed = true;
	        boolean bigEndian = true;
	        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	        return format;
	    }
	 
	    /**
	     * Captures the sound and record into a WAV file
	     */
	    public void start() {
	    	int result = 0;
	        try {
	        	
	        	
	        	/*
	        	String path = this.getClass().getClassLoader().getResource("").getPath();
	        	String fullPath = URLDecoder.decode(path, "UTF-8");
	        	String pathArr[] = fullPath.split("/WEB-INF/classes/");
	        	System.out.println(fullPath);
	        	System.out.println(pathArr[0]);
	        	fullPath = pathArr[0];
	        	String reponsePath = "";
	        	// to read a file from webcontent
	        	reponsePath = new File(\\fullPath).getPath() + File.separatorChar + "WebContent\\recodingTemp\\newfile.wav";
	        	
	        	 String filedata1 = reponsePath;
	        	 System.out.println(reponsePath);
	        	 */
	   	      
	        	

String filedata1  = "recodingTemp\\newfile.wav";
ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
String newFileName = servletContext.getRealPath("") +    File.separator+ filedata1;

	        	
System.out.println(newFileName);
	      	   
	     	    // path of the wav file
	     	    File wavFile = new File(newFileName);
	     	   
	     	    // format of audio file
	     	    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	     	 
	     	   
	     	
	        	
	        	
	            AudioFormat format = getAudioFormat();
	            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	 
	            // checks if system supports the data line
	            if (!AudioSystem.isLineSupported(info)) {
	                System.out.println("Line not supported");
	                System.exit(0);
	            }
	            line = (TargetDataLine) AudioSystem.getLine(info);
	            line.open(format);
	            line.start();   // start capturing
	 
	            System.out.println("Start capturing...");
	 
	            AudioInputStream ais = new AudioInputStream(line);
	 
	            System.out.println("Start recording...");
	 
	            // start recording
	            AudioSystem.write(ais, fileType, wavFile);
	            System.out.println("Start recording122..."+ais);
	            
	            
	            byte[] bFile = readBytesFromFile(filedata1);
	           // AudioSystem.write(ais.getFrameLength());
	         //   String filePath = "f:/Test/new.txt";
		    	
	            soundrecord = bFile ;
	        //	byte[] jj=null;
	           
	          result = newrequestInterface.soundrecoder(bFile,filedata1);
	            
	            
	           	            
	            
	            
	 
	        } catch (LineUnavailableException ex) {
	            ex.printStackTrace();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	    
	    private static byte[] readBytesFromFile(String filePath) {

	        FileInputStream fileInputStream = null;
	        byte[] bytesArray = null;

	        try {

	            File file = new File(filePath);
	            bytesArray = new byte[(int) file.length()];

	            //read file into bytes[]
	            fileInputStream = new FileInputStream(file);
	            fileInputStream.read(bytesArray);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fileInputStream != null) {
	                try {
	                    fileInputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }

	        }

	        return bytesArray;

	    }

	    
	    
	    
	    
	    
	    /**
	     * Closes the target data line to finish capturing and recording
	     */
	    public void finish() {
	        line.stop();
	        line.close();
	        System.out.println("Finished");
	        
	    }

	
	public void soundRecode(){
		   final CreateRequestbean recorder = new CreateRequestbean();
	      // creates a new thread that waits for a specified
  // of time before stopping
  Thread stopper = new Thread(new Runnable() {
      public void run() {
          try {
              Thread.sleep(CreateRequestbean.RECORD_TIME);
          } catch (InterruptedException ex) {
              ex.printStackTrace();
          }
          recorder.finish();
      }
  });

  stopper.start();

  // start recording
  recorder.start();
		
  
  
  

		
		
	}
	
	
	
	 
    

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// for download file from the grid
	public StreamedContent fileDownloadView() {
		System.out.println("hello");
		InputStream stream = null;
		try {

			setRequestId(requestId);
			newrequestVo = newrequestInterface.getRequestById(requestId);
			if (newrequestVo.getFile() != null && newrequestVo.getFile().length != 0) {
				stream = new ByteArrayInputStream(newrequestVo.getFile());

			}
			String fileExtn = "";
			String strArr[] = {};

			System.out.print(newrequestVo.getFileName());
			if (newrequestVo.getFileName() != null && !newrequestVo.getFileName().trim().equals("")) {
				strArr = newrequestVo.getFileName().split(".");
				for (String extn : strArr) {
					fileExtn = extn;
				}
				file = new DefaultStreamedContent(stream, fileExtn, "downloaded_" + newrequestVo.getFileName().trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}

	// for create request page get team member name and email for team member
	// field
	public List<UserVo> getUserDetails(String query) throws Exception {
		HttpSession session = SessionUtils.getSession();
		List<UserVo> filteredUsers = new ArrayList<UserVo>();
		String userName = (String) session.getAttribute("username");
		System.out.println("--usersession--userName-->" + userName);
		friendList = friendMasterInterface.AllUsers(userName.toLowerCase().trim());

		if (friendList != null && friendList.size() != 0 && query != null) {
			for (UserVo userVo : friendList) {
				if (userVo != null && userVo.getName() != null && userVo.getName().toLowerCase().trim()
						.matches("(.*)" + query.toLowerCase().trim() + "(.*)")) {
					filteredUsers.add(userVo);
				}
			}
		}
		// Str.matches("(.*)Tutorials(.*)"))

		// friendList1 = friendMasterInterface.getUsersStatus(userName);

		return filteredUsers;
	}

	// for Pie graph
	@SuppressWarnings("unchecked")
	private void createPieModels() {
		piechart = new PieChartModel();
		Map<String, BigInteger> requestmap = new HashMap<String, BigInteger>();
		try {
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			requestmap = newrequestqueryInterface.piechart(userName.toLowerCase().trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (requestmap != null && !requestmap.entrySet().isEmpty()) {
			for (@SuppressWarnings("rawtypes")
			Map.Entry m : requestmap.entrySet()) {
				String friendemailid = (String) m.getKey();
				BigInteger countfriendemailid = (BigInteger) m.getValue();
				piechart.set(friendemailid, countfriendemailid);
			}

			piechart.setTitle("Number Of Requests");
			piechart.setLegendPosition("ne");
			piechart.setShowDataLabels(true);
		}

	}

	// Start Bar graph chart
	private void createBarModels() {
		createBarModel();
		createBarMode2();
		createBarMode3();
	}

	private void createBarMode3() {
		// TODO Auto-generated method stub
		barModelcloser = initBarModel3();
		barModelcloser.setTitle("Avg Of Last 365 days Closure Ttime Performance");

		// barModel.setLegendPosition("ne");

		Axis xAxis = barModelcloser.getAxis(AxisType.X);
		xAxis.setLabel("Team Member Name");

		Axis yAxis = barModelcloser.getAxis(AxisType.Y);
		yAxis.setLabel("Avg Of Last 365 days Closure Ttime Performance");
		yAxis.setMin(0.0);
		// yAxis.setMax(4);
	}

	private BarChartModel initBarModel3() {
		// TODO Auto-generated method stub
		Map<String, Double> requestbarmap3 = new HashMap<String, Double>();

		try {
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			requestbarmap3 = newrequestqueryInterface.barchartforcompleteddate(userName.toLowerCase().trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub BarChartModel model = new
		// BarChartModel();
		BarChartModel model = new BarChartModel();
		ChartSeries chartseries1 = new ChartSeries();
		if (requestbarmap3 != null && !requestbarmap3.entrySet().isEmpty()) {
			for (Map.Entry m : requestbarmap3.entrySet()) {
				String requestnumber = (String) m.getKey();
				Double friendemailid = (Double) m.getValue();

				chartseries1.set(requestnumber, friendemailid);
			}
		}
		model.addSeries(chartseries1);

		return model;

	}

	private void createBarMode2() {
		// TODO Auto-generated method stub
		barModelaverage = initBarModel2();
		barModelaverage.setTitle("Avg Of Last 365 days Rating");

		// barModel.setLegendPosition("ne");

		Axis xAxis = barModelaverage.getAxis(AxisType.X);
		xAxis.setLabel("Team Member Name");

		Axis yAxis = barModelaverage.getAxis(AxisType.Y);
		yAxis.setLabel("PerFormance Rating Out Of 5");
		yAxis.setMin(0.0);

		// yAxis.setMax(4);
	}

	private BarChartModel initBarModel2() {
		// TODO Auto-generated method stub
		Map<String, BigDecimal> requestbarmap1 = new HashMap<String, BigDecimal>();

		try {
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			requestbarmap1 = newrequestqueryInterface.barchartforaverage(userName.toLowerCase().trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub BarChartModel model = new
		// BarChartModel();
		BarChartModel model = new BarChartModel();
		ChartSeries chartseries1 = new ChartSeries();
		if (requestbarmap1 != null && !requestbarmap1.entrySet().isEmpty()) {
			for (Map.Entry m : requestbarmap1.entrySet()) {
				String requestnumber = (String) m.getKey();
				BigDecimal friendemailid = (BigDecimal) m.getValue();

				chartseries1.set(requestnumber, friendemailid);
			}
		}
		model.addSeries(chartseries1);

		return model;

	}

	// for set bar graph label and title
	private void createBarModel() {
		// TODO Auto-generated method stub
		barModel = initBarModel();
		barModel.setTitle("Schedule Performance");

		// barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Team Member Name");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("PerFormance in % Age");
		yAxis.setMin(0.0);
		// yAxis.setMax(4);
	}

	// for bar graph get value from impl package
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private BarChartModel initBarModel() {
		Map<String, Double> requestbarmap = new HashMap<String, Double>();

		try {
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			requestbarmap = newrequestqueryInterface.barchart(userName.toLowerCase().trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub BarChartModel model = new
		// BarChartModel();
		BarChartModel model = new BarChartModel();
		ChartSeries chartseries1 = new ChartSeries();
		if (requestbarmap != null && !requestbarmap.entrySet().isEmpty()) {
			for (Map.Entry m : requestbarmap.entrySet()) {
				String requestnumber = (String) m.getKey();
				Double friendemailid = (Double) m.getValue();

				chartseries1.set(requestnumber, friendemailid);
			}
		}
		// model.setLegendPosition("e");
		// model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.addSeries(chartseries1);

		return model;

	}

	// for print excel
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}

	}

	// for print pdf
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		// ExternalContext externalContext =
		// FacesContext.getCurrentInstance().getExternalContext();
		// String logo = externalContext.getRealPath("") + File.separator +
		// "resources" + File.separator + "demo" + File.separator + "images" +
		// File.separator + "prime_logo.png";

		pdf.addTitle("Collabor8");
	}

	// for display error message
	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Integer getUsercategory() {
		return usercategory;
	}

	public void setUsercategory(Integer usercategory) {
		this.usercategory = usercategory;
	}

	public Integer getUserproject() {
		return userproject;
	}

	public void setUserproject(Integer userproject) {
		this.userproject = userproject;
	}

	public Integer getUserrequesttype() {
		return userrequesttype;
	}

	public void setUserrequesttype(Integer userrequesttype) {
		this.userrequesttype = userrequesttype;
	}

	public List<NewrequestVo> getNewrequestList() {
		return newrequestList;
	}

	public void setNewrequestList(List<NewrequestVo> newrequestList) {
		this.newrequestList = newrequestList;
	}

	public Integer[] getUserfriendlist() {
		return userfriendlist;
	}

	public void setUserfriendlist(Integer[] userfriendlist) {
		this.userfriendlist = userfriendlist;
	}

	public UploadedFile getAttachment() {
		return attachment;
	}

	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}

	public Date getCompletiondate() {
		return completiondate;
	}

	public void setCompletiondate(Date completiondate) {
		this.completiondate = completiondate;
	}

	public List<Request> getRequest() {
		return request;
	}

	public void setRequest(List<Request> request) {
		this.request = request;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public NewrequestVo getNewrequestVo() {
		return newrequestVo;
	}

	public void setNewrequestVo(NewrequestVo newrequestVo) {
		this.newrequestVo = newrequestVo;
	}

	public NewrequestVo getSelectedReuest() {
		return selectedReuest;
	}

	public void setSelectedReuest(NewrequestVo selectedReuest) {
		this.selectedReuest = selectedReuest;
	}

	public List<NewrequestVo> getFilteredRequestList() {
		return filteredRequestList;
	}

	public void setFilteredRequestList(List<NewrequestVo> filteredRequestList) {
		this.filteredRequestList = filteredRequestList;
	}

	

	public int getCompletionpercentage() {
		return completionpercentage;
	}

	public void setCompletionpercentage(int completionpercentage) {
		this.completionpercentage = completionpercentage;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}

	public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
		this.horizontalBarModel = horizontalBarModel;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOnemonthpastDate() {
		return onemonthpastDate;
	}

	public void setOnemonthpastDate(String onemonthpastDate) {
		this.onemonthpastDate = onemonthpastDate;
	}

	public Integer[] getSearchteammember() {
		return searchteammember;
	}

	public void setSearchteammember(Integer[] searchteammember) {
		this.searchteammember = searchteammember;
	}

	public Integer[] getSearchcategory() {
		return searchcategory;
	}

	public void setSearchcategory(Integer[] searchcategory) {
		this.searchcategory = searchcategory;
	}

	public Integer[] getSearchproject() {
		return searchproject;
	}

	public void setSearchproject(Integer[] searchproject) {
		this.searchproject = searchproject;
	}

	public Integer[] getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(Integer[] searchtype) {
		this.searchtype = searchtype;
	}

	public Integer[] getSearchstage() {
		return searchstage;
	}

	public void setSearchstage(Integer[] searchstage) {
		this.searchstage = searchstage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getUserfriend() {
		return userfriend;
	}

	public void setUserfriend(Integer userfriend) {
		this.userfriend = userfriend;
	}

	public PieChartModel getPiechart() {
		return piechart;
	}

	public void setPiechart(PieChartModel piechart) {
		this.piechart = piechart;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public List<UserVo> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<UserVo> friendList) {
		this.friendList = friendList;
	}

	public List<UserVo> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<UserVo> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public String getActualeffort() {
		return actualeffort;
	}

	public void setActualeffort(String actualeffort) {
		this.actualeffort = actualeffort;
	}

	public String getEstimatedeffort() {
		return estimatedeffort;
	}

	public void setEstimatedeffort(String estimatedeffort) {
		this.estimatedeffort = estimatedeffort;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Integer getWeightage() {
		return weightage;
	}

	public void setWeightage(Integer weightage) {
		this.weightage = weightage;
	}

	public AccountuserVo getAccountuserVo() {
		return accountuserVo;
	}

	public void setAccountuserVo(AccountuserVo accountuserVo) {
		this.accountuserVo = accountuserVo;
	}

	public Boolean getImagestatus() {
		return imagestatus;
	}

	public void setImagestatus(Boolean imagestatus) {
		this.imagestatus = imagestatus;
	}

	public BarChartModel getBarModelaverage() {
		return barModelaverage;
	}

	public void setBarModelaverage(BarChartModel barModelaverage) {
		this.barModelaverage = barModelaverage;
	}

	public BarChartModel getBarModelcloser() {
		return barModelcloser;
	}

	public void setBarModelcloser(BarChartModel barModelcloser) {
		this.barModelcloser = barModelcloser;
	}

	public String getTitlelist1() {
		return titlelist1;
	}

	public void setTitlelist1(String titlelist1) {
		this.titlelist1 = titlelist1;
	}

	public String getTitlelist2() {
		return titlelist2;
	}

	public void setTitlelist2(String titlelist2) {
		this.titlelist2 = titlelist2;
	}

	public String getTitlelist3() {
		return titlelist3;
	}

	public void setTitlelist3(String titlelist3) {
		this.titlelist3 = titlelist3;
	}

	public String getTitlelist4() {
		return titlelist4;
	}

	public void setTitlelist4(String titlelist4) {
		this.titlelist4 = titlelist4;
	}

	public String getTitlelist5() {
		return titlelist5;
	}

	public void setTitlelist5(String titlelist5) {
		this.titlelist5 = titlelist5;
	}

	public Integer[] getUserfriendlist1() {
		return userfriendlist1;
	}

	public void setUserfriendlist1(Integer[] userfriendlist1) {
		this.userfriendlist1 = userfriendlist1;
	}

	public Integer[] getUserfriendlist2() {
		return userfriendlist2;
	}

	public void setUserfriendlist2(Integer[] userfriendlist2) {
		this.userfriendlist2 = userfriendlist2;
	}

	public Integer[] getUserfriendlist3() {
		return userfriendlist3;
	}

	public void setUserfriendlist3(Integer[] userfriendlist3) {
		this.userfriendlist3 = userfriendlist3;
	}

	public Integer[] getUserfriendlist4() {
		return userfriendlist4;
	}

	public void setUserfriendlist4(Integer[] userfriendlist4) {
		this.userfriendlist4 = userfriendlist4;
	}

	public Integer[] getUserfriendlist5() {
		return userfriendlist5;
	}

	public void setUserfriendlist5(Integer[] userfriendlist5) {
		this.userfriendlist5 = userfriendlist5;
	}

	public List<UserVo> getSelectedUsers1() {
		return selectedUsers1;
	}

	public void setSelectedUsers1(List<UserVo> selectedUsers1) {
		this.selectedUsers1 = selectedUsers1;
	}

	public List<UserVo> getSelectedUsers2() {
		return selectedUsers2;
	}

	public void setSelectedUsers2(List<UserVo> selectedUsers2) {
		this.selectedUsers2 = selectedUsers2;
	}

	public List<UserVo> getSelectedUsers3() {
		return selectedUsers3;
	}

	public void setSelectedUsers3(List<UserVo> selectedUsers3) {
		this.selectedUsers3 = selectedUsers3;
	}

	public List<UserVo> getSelectedUsers4() {
		return selectedUsers4;
	}

	public void setSelectedUsers4(List<UserVo> selectedUsers4) {
		this.selectedUsers4 = selectedUsers4;
	}

	public List<UserVo> getSelectedUsers5() {
		return selectedUsers5;
	}

	public void setSelectedUsers5(List<UserVo> selectedUsers5) {
		this.selectedUsers5 = selectedUsers5;
	}

	public Date getCompletiondate1() {
		return completiondate1;
	}

	public void setCompletiondate1(Date completiondate1) {
		this.completiondate1 = completiondate1;
	}

	public Date getCompletiondate2() {
		return completiondate2;
	}

	public void setCompletiondate2(Date completiondate2) {
		this.completiondate2 = completiondate2;
	}

	public Date getCompletiondate3() {
		return completiondate3;
	}

	public void setCompletiondate3(Date completiondate3) {
		this.completiondate3 = completiondate3;
	}

	public Date getCompletiondate4() {
		return completiondate4;
	}

	public void setCompletiondate4(Date completiondate4) {
		this.completiondate4 = completiondate4;
	}

	public Date getCompletiondate5() {
		return completiondate5;
	}

	public void setCompletiondate5(Date completiondate5) {
		this.completiondate5 = completiondate5;
	}

	public List<QuickcreaterequestVo> getQuickrequestList1() {
		return quickrequestList1;
	}

	public void setQuickrequestList1(List<QuickcreaterequestVo> quickrequestList1) {
		this.quickrequestList1 = quickrequestList1;
	}

	public String getTitlelist6() {
		return titlelist6;
	}

	public void setTitlelist6(String titlelist6) {
		this.titlelist6 = titlelist6;
	}

	public String getTitlelist7() {
		return titlelist7;
	}

	public void setTitlelist7(String titlelist7) {
		this.titlelist7 = titlelist7;
	}

	public String getTitlelist8() {
		return titlelist8;
	}

	public void setTitlelist8(String titlelist8) {
		this.titlelist8 = titlelist8;
	}

	public String getTitlelist9() {
		return titlelist9;
	}

	public void setTitlelist9(String titlelist9) {
		this.titlelist9 = titlelist9;
	}

	public String getTitlelist10() {
		return titlelist10;
	}

	public void setTitlelist10(String titlelist10) {
		this.titlelist10 = titlelist10;
	}

	public Integer[] getUserfriendlist6() {
		return userfriendlist6;
	}

	public void setUserfriendlist6(Integer[] userfriendlist6) {
		this.userfriendlist6 = userfriendlist6;
	}

	public Integer[] getUserfriendlist7() {
		return userfriendlist7;
	}

	public void setUserfriendlist7(Integer[] userfriendlist7) {
		this.userfriendlist7 = userfriendlist7;
	}

	public Integer[] getUserfriendlist8() {
		return userfriendlist8;
	}

	public void setUserfriendlist8(Integer[] userfriendlist8) {
		this.userfriendlist8 = userfriendlist8;
	}

	public Integer[] getUserfriendlist9() {
		return userfriendlist9;
	}

	public void setUserfriendlist9(Integer[] userfriendlist9) {
		this.userfriendlist9 = userfriendlist9;
	}

	public Integer[] getUserfriendlist10() {
		return userfriendlist10;
	}

	public void setUserfriendlist10(Integer[] userfriendlist10) {
		this.userfriendlist10 = userfriendlist10;
	}

	public List<UserVo> getSelectedUsers6() {
		return selectedUsers6;
	}

	public void setSelectedUsers6(List<UserVo> selectedUsers6) {
		this.selectedUsers6 = selectedUsers6;
	}

	public List<UserVo> getSelectedUsers7() {
		return selectedUsers7;
	}

	public void setSelectedUsers7(List<UserVo> selectedUsers7) {
		this.selectedUsers7 = selectedUsers7;
	}

	public List<UserVo> getSelectedUsers8() {
		return selectedUsers8;
	}

	public void setSelectedUsers8(List<UserVo> selectedUsers8) {
		this.selectedUsers8 = selectedUsers8;
	}

	public List<UserVo> getSelectedUsers9() {
		return selectedUsers9;
	}

	public void setSelectedUsers9(List<UserVo> selectedUsers9) {
		this.selectedUsers9 = selectedUsers9;
	}

	public List<UserVo> getSelectedUsers10() {
		return selectedUsers10;
	}

	public void setSelectedUsers10(List<UserVo> selectedUsers10) {
		this.selectedUsers10 = selectedUsers10;
	}

	public Date getCompletiondate6() {
		return completiondate6;
	}

	public void setCompletiondate6(Date completiondate6) {
		this.completiondate6 = completiondate6;
	}

	public Date getCompletiondate7() {
		return completiondate7;
	}

	public void setCompletiondate7(Date completiondate7) {
		this.completiondate7 = completiondate7;
	}

	public Date getCompletiondate8() {
		return completiondate8;
	}

	public void setCompletiondate8(Date completiondate8) {
		this.completiondate8 = completiondate8;
	}

	public Date getCompletiondate9() {
		return completiondate9;
	}

	public void setCompletiondate9(Date completiondate9) {
		this.completiondate9 = completiondate9;
	}

	public Date getCompletiondate10() {
		return completiondate10;
	}

	public void setCompletiondate10(Date completiondate10) {
		this.completiondate10 = completiondate10;
	}

	public List<quickrequestvalidation> getQuickrequestListval() {
		return quickrequestListval;
	}

	public void setQuickrequestListval(List<quickrequestvalidation> quickrequestListval) {
		this.quickrequestListval = quickrequestListval;
	}

	public byte[] getSoundrecord() {
		return soundrecord;
	}

	public void setSoundrecord(byte[] soundrecord) {
		this.soundrecord = soundrecord;
	}

}
