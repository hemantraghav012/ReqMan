package com.reqman.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;



import javax.faces.context.FacesContext;
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

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.pojo.Request;
import com.reqman.util.SessionUtils;
import com.reqman.vo.NewrequestVo;


@ManagedBean(name="updatestatus",eager = true)
@RequestScoped
@ViewScoped
public class Updatestatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2224803034628444871L;
	
	private Float completionpercentage;
	 private Date completiondate;
	 private  List<Request> request ;
	 private String requestId;
	 private List<NewrequestVo> newrequestList = new ArrayList<NewrequestVo>();
	 private Boolean status;
	 private NewrequestInterface newrequestInterface = new NewrequestImpl();
	
	 private NewrequestVo newrequestVo = new NewrequestVo();
	 private NewrequestVo selectedReuest;
	 private List<NewrequestVo> filteredRequestList = new ArrayList<NewrequestVo>();
	 private String title;
	 private String description;
	 private Integer  usercategory;
	 private Integer userproject;
	 private Integer userrequesttype;
	 private Integer[]  userfriendlist;
	 private UploadedFile attachment;

	 private StreamedContent file;
	 
	 
	 
	 
	 
	 
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
				newrequestList = newrequestInterface.getNewrequestDetails(userName);
				setFilteredRequestList(newrequestList);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
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
	        		
	        		setCompletionpercentage(newrequestVo.getCompletionpercentage());
	        	
	        		
	        		setStatus(true);
	        		
	        	}
	        	else
	        	{
	        		setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle(): "");
	        	
	        		setCompletionpercentage(newrequestVo.getCompletionpercentage());
	        		setStatus(false);
	        	}
	        	
	        	FacesContext.getCurrentInstance()
	            .getExternalContext().dispatch("modifyupdatestatus.xhtml");

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
				
				
				System.out.println("--updateRequesr requestid-"+requestId);
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");			
				System.out.println("--usersession--userName-->"+userName);
				
	        	result = newrequestInterface.updateRequestById(requestId,status,description, completiondate,attachment, completionpercentage);
	        	
	        	if(result == 2)
	        	{
	        		FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Problem while modifying the Category",
									"Problem while modifying the Category"));
					return "modifyupdatestatus";
	        	}
	        	
	        	if(result == 1)
	        	{
	        		newrequestList = newrequestInterface.getNewrequestDetails(userName);
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
				return "modifyupdatestatus.xhtml";
			}
			return "updatestatus";
		}
		
		
		 public StreamedContent fileDownloadView() { 
			 	System.out.println("hello");
			 	InputStream stream = null;
			 	try{
			 		//System.out.println("modify action"+requestId);
		            //addMessage("Welcome to Primefaces!!");
		        	setRequestId(requestId);
		        	newrequestVo = newrequestInterface.getRequestById(requestId);
		        	//stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resource/temp/avatar.jpg");
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


		
		
	 
	 
	 
	public Float getCompletionpercentage() {
		return completionpercentage;
	}
	public void setCompletionpercentage(Float completionpercentage) {
		this.completionpercentage = completionpercentage;
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
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public List<NewrequestVo> getNewrequestList() {
		return newrequestList;
	}
	public void setNewrequestList(List<NewrequestVo> newrequestList) {
		this.newrequestList = newrequestList;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
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


	public StreamedContent getFile() {
		return file;
	}


	public void setFile(StreamedContent file) {
		this.file = file;
	}
	
	
	 
	 
	 
	 

}
