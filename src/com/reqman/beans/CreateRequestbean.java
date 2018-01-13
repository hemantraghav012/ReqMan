package com.reqman.beans;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.pojo.Request;
import com.reqman.util.Dateconverter;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.requestNoteVo;
import com.sun.el.parser.ParseException;


@ManagedBean(name="createrequest",eager = true)
@RequestScoped
@ViewScoped

public class CreateRequestbean implements Serializable
{
	private static final long serialVersionUID = 3076255353187837257L;
	
	 private String title;
	 private String description;
	 private Integer  usercategory;
	 private Integer userproject;
	 private Integer userrequesttype;
	 private Integer[]  userfriendlist;
	 private UploadedFile attachment;
	 private Date completiondate;
	 private  List<Request> request ;
	 private String requestId;
	 private List<NewrequestVo> newrequestList = new ArrayList<NewrequestVo>();	 
	 private List<NewrequestVo> closerequestList = new ArrayList<NewrequestVo>();
	 private List<NewrequestVo> newrequestList3 = new ArrayList<NewrequestVo>();	 
	 private Boolean status;
	 private NewrequestInterface newrequestInterface = new NewrequestImpl();
	 private StreamedContent file;
	 private NewrequestVo newrequestVo = new NewrequestVo();	
	 private NewrequestVo selectedReuest;
	 private List<NewrequestVo> filteredRequestList = new ArrayList<NewrequestVo>();
	 private List<NewrequestVo> filteredCloseRequestList = new ArrayList<NewrequestVo>();
	 
	 private Integer stage;
	 private BarChartModel barModel;
	 private HorizontalBarChartModel horizontalBarModel;
     private String currentDate;
     private String onemonthpastDate;   
	 private Float completionpercentage;
     private  String message;     
     private Date startDate;
     private Date endDate;
     private Integer userfriend;
   
     private Integer[]  searchteammember;
     private Integer[]  searchcategory;
     private Integer[]  searchproject;
     private Integer[]  searchtype;
     private Integer[]  searchstage;
     
     private PieChartModel piechart;
     
     
     
     
	@PostConstruct
    public void init() 
	{
		try
		{
			 	
			System.out.println("--create request-->");
			newrequestList = new ArrayList<NewrequestVo>();			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			if(startDate == null)
			{
				 startDate = Dateconverter.getPreToPreMonthDate(new Date());	
			}
			if(endDate == null)
			{
				endDate = new Date();
			}
			closerequestList=new ArrayList<NewrequestVo>();
			closerequestList=newrequestInterface.getColserequestDetails(userName);	
			newrequestList = newrequestInterface.getNewrequestDetails(userName,startDate,endDate);			
			newrequestList3 = newrequestInterface.getallproject(userName);			 
			setFilteredRequestList(newrequestList);
			setFilteredCloseRequestList(closerequestList);
			createBarModels();
			 createPieModels();
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
		

	
	
	
	
	
	
	 private void createPieModels() {
	        piechart = new PieChartModel();	     	        
	        Map<String,BigInteger> requestmap = new HashMap<String,BigInteger>();
			 try { 
			 HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");			
					requestmap = newrequestInterface.piechart(userName );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	      
                 if(requestmap !=null)
                   {
		        for(Map.Entry m:requestmap.entrySet())
		        {  
		        	String friendemailid= (String) m.getKey();
		        	BigInteger countfriendemailid= (BigInteger) m.getValue();
		        	 piechart.set(friendemailid,countfriendemailid);
		         }  
		       
	    piechart.setTitle("Request");
	    piechart.setLegendPosition("ne");	   
	    piechart.setShowDataLabels(true);
	 }
	
	 }
	
	
	public String daterange()
	{
		try
		{
			newrequestList = new ArrayList<NewrequestVo>();
			System.out.println("--create new request-->");
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");			
			newrequestList = newrequestInterface.getNewrequestDetails(userName,startDate,endDate);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "request";
	}
	
		//Start Bar chart 
	 private void createBarModels() {
	        createBarModel();
	      	    }
	
	 private void createBarModel() {
			// TODO Auto-generated method stub
		    barModel = initBarModel();	        
	       barModel.setTitle("Schedule Performance");
	      
	    // barModel.setLegendPosition("ne");
	         
	        Axis xAxis = barModel.getAxis(AxisType.X);
	        xAxis.setLabel("Team Member Name");
	         
	        Axis yAxis = barModel.getAxis(AxisType.Y);
	        yAxis.setLabel("PerFormance in % Age");
	        yAxis.setMin(0.0 );
	       // yAxis.setMax(4);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private BarChartModel initBarModel() {
		 Map<String,Double> requestbarmap = new HashMap<String,Double>();
	
			 try 
			  {	       
			    HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");			
				requestbarmap = newrequestInterface.barchart(userName );
			  } catch (Exception e)
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }			
			
			// TODO Auto-generated method stub BarChartModel model = new BarChartModel();
			 BarChartModel model = new BarChartModel();				 
			   ChartSeries chartseries1 = new ChartSeries();	
			    
			  //1 for Poor
			 
			  
	            for(Map.Entry m:requestbarmap.entrySet())
	            {  
	        	  String requestnumber= (String) m.getKey();
	        	  Double friendemailid= (Double) m.getValue();	
	        	  
	        	   chartseries1.set(requestnumber,friendemailid);
	          }
	            
	          model.addSeries(chartseries1);
	          
	      return model;
	      
		}

		
	
	
	
	
	
	
	
	public String createRequestPage()
	{
		try
		{
			newrequestList = new ArrayList<NewrequestVo>();
			System.out.println("--create new request-->");
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			newrequestList = newrequestInterface.getNewrequestDetails(userName,startDate,endDate);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "request";
	}
	
	
	public String createRequest()
	{
		try
		{
			newrequestList = new ArrayList<NewrequestVo>();
			System.out.println("newrequestfriend");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "newrequestfriend";
	}
	
	
   
	public String save()
 {
		int result = 0;
		UserSession usersession = new UserSession();
		try {
			newrequestList = new ArrayList<NewrequestVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");

			System.out.println("friendlist" + userfriendlist);

			result = newrequestInterface.save(title, description, usercategory,	userproject, userrequesttype,
					                                  attachment, userName,	completiondate, userfriendlist);

			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Category already exist",
								"Category already exist"));
				return "newrequestfriend";
			}
			if (result == 2) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_WARN,
										"Category already exist and in active, please activate by using modify category ",
										"Category already exist and in active, please activate by using modify category"));
				return "newrequestfriend";
			}
			if (result == 3) {
				newrequestList = newrequestInterface.getNewrequestDetails(userName,startDate,endDate);
					FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Category created  successfully.",
								"Category created  successfully."));
					
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
	

	public void modifyAction() {		
        try
        {
        	System.out.println("modify action"+requestId);
            //addMessage("Welcome to Primefaces!!");
        	setRequestId(requestId);
        	newrequestVo = newrequestInterface.getRequestById(requestId);      	
			
        	if(newrequestVo != null && newrequestVo.getStatus().trim().equalsIgnoreCase("Active")){
        		 setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle(): "");
        		 setDescription(newrequestVo.getDescription() != null ? newrequestVo.getDescription() : "");        		
        	     setCompletiondate(newrequestVo.getCompletiondate());
        	     setUserproject(newrequestVo.getProject());
        	     setUsercategory(newrequestVo.getCategory());
        	     if(newrequestVo.getStage().trim().equalsIgnoreCase("Request")){
        	    	 setStage(1);
        	     } else if(newrequestVo.getStage().trim().equalsIgnoreCase("Accepted")){
        	    	 setStage(2);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Returned")){
        	    	 setStage(3);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("In-progress")){
        	    	 setStage(4);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Completed")){
        	    	 setStage(5);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Cancel")){
        	    	 setStage(6);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Hold")){
        	    	 setStage(7);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Close")){
        	    	 setStage(8);
        	     }
        	     setUserfriend(newrequestVo.getUserfriend());
        		 setStatus(true);
        		
        	}
        	else
        	{
        		setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle(): "");
        		setDescription(newrequestVo.getDescription() != null ? newrequestVo.getDescription() : "");
        		setCompletiondate(newrequestVo.getCompletiondate());
        		setUserproject(newrequestVo.getProject());
        		setUsercategory(newrequestVo.getCategory());
            	setUserrequesttype(newrequestVo.getRequesttype());
            	setUserfriend(newrequestVo.getUserfriend());
            	  if(newrequestVo.getStage().trim().equalsIgnoreCase("Request")){
        	    	 setStage(1);
        	     } else if(newrequestVo.getStage().trim().equalsIgnoreCase("Accepted")){
        	    	 setStage(2);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Returned")){
        	    	 setStage(3);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("In-progress")){
        	    	 setStage(4);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Completed")){
        	    	 setStage(5);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Cancel")){
        	    	 setStage(6);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Hold")){
        	    	 setStage(7);
        	     } else  if(newrequestVo.getStage().trim().equalsIgnoreCase("Close")){
        	    	 setStage(8);
        	     }
        		setStatus(false);
        	}
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifyrequest.xhtml");

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
	public String updateRequest()
	{
		int result = 0;
		try{
			System.out.println("--updateRequest-status-"+status);
			System.out.println("--updateRequest-status-"+description);
			System.out.println("--updateRequest-status-"+completiondate);
			System.out.println("--updateRequest-project-"+userproject);
			System.out.println("--updateRequest-category-"+usercategory);
			System.out.println("--updateRequest-requesttype	-"+userrequesttype);
			System.out.println("--updateRequest-requesttype	-"+title);
			
			System.out.println("--updateRequesr requestid-"+requestId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");			
			System.out.println("--usersession--userName-->"+userName);
			
        	result = newrequestInterface.updateRequestById(requestId,status,description, completiondate,attachment, 
        			completionpercentage,stage,message,userName,userproject,usercategory,userrequesttype,userfriend);
        	
        	if(result == 2)
        	{
        		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the Category",
								"Problem while modifying the Category"));
				return "modifyrequest.xhtml";
        	}
        	
        	if(result == 1)
        	{
        		newrequestList =newrequestInterface.getNewrequestDetails(userName,startDate,endDate);
    				}
        	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the Category",
							"Problem while modifying the Category"));
			return "modifyrequest.xhtml";
		}
		return "request";
	}
	
	
	
	
	 public StreamedContent fileDownloadView() { 
		 	System.out.println("hello");
		 	InputStream stream = null;
		 	try{
		 		
	        	setRequestId(requestId);
	        	newrequestVo = newrequestInterface.getRequestById(requestId);	        	
	        	if(newrequestVo.getFile() != null && newrequestVo.getFile().length != 0)
	        	{
	        		stream = new ByteArrayInputStream(newrequestVo.getFile());
	        		
	        	}
	        	String fileExtn = "";
	        	String strArr[] = {};
	        	
	        	if(newrequestVo.getFileName() != null && !newrequestVo.getFileName().trim().equals(""))
	        	{
	        		strArr = newrequestVo.getFileName().split(".");
	        		for(String extn : strArr){
	        			fileExtn = extn;
	        		}
	        		file = new DefaultStreamedContent(stream, fileExtn, "downloaded_"+newrequestVo.getFileName().trim());
	        	}
	        	

	        	
		 	}
		 	catch(Exception e){
		 		e.printStackTrace();
		 	}
	        
	       return file;
	    }
	 
	
	 
	
	 
	 
	 
	 
	 
	 
	 
	
	 public void postProcessXLS(Object document) {
	        HSSFWorkbook wb = (HSSFWorkbook) document;
	        HSSFSheet sheet = wb.getSheetAt(0);
	        HSSFRow header = sheet.getRow(0);
	         
	        HSSFCellStyle cellStyle = wb.createCellStyle();  
	        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
	        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	         
	        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
	            HSSFCell cell = header.getCell(i);
	             
	            cell.setCellStyle(cellStyle);
	        }
	        
	        
	     }
	     
	    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
	        Document pdf = (Document) document;
	        pdf.open();
	        pdf.setPageSize(PageSize.A4);
	 
	        //ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        //String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
	         
	        pdf.addTitle("Collabor8");
	    }
		
	

	

	public void addMessage(String summary) 
	{
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
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

	public Float getCompletionpercentage() {
		return completionpercentage;
	}

	public void setCompletionpercentage(Float completionpercentage) {
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


		public List<NewrequestVo> getNewrequestList3() {
			return newrequestList3;
		}


		public void setNewrequestList3(List<NewrequestVo> newrequestList3) {
			this.newrequestList3 = newrequestList3;
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


		public List<NewrequestVo> getCloserequestList() {
			return closerequestList;
		}


		public void setCloserequestList(List<NewrequestVo> closerequestList) {
			this.closerequestList = closerequestList;
		}


		public List<NewrequestVo> getFilteredCloseRequestList() {
			return filteredCloseRequestList;
		}


		public void setFilteredCloseRequestList(
				List<NewrequestVo> filteredCloseRequestList) {
			this.filteredCloseRequestList = filteredCloseRequestList;
		}


		public PieChartModel getPiechart() {
			return piechart;
		}


		public void setPiechart(PieChartModel piechart) {
			this.piechart = piechart;
		}






	
	
}
