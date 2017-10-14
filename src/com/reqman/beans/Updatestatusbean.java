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
import com.reqman.dao.UpdatestatusInterface;
import com.reqman.daoimpl.UpdatestatusImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.UpdatestatusVo;



@ManagedBean(name="updatestatusbean",eager = true)
@RequestScoped
@ViewScoped
public class Updatestatusbean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2224803034628444871L;
	
	
	private Integer stage;
	 private String title;
	 private String description;
	 private UploadedFile attachment;
	 private Float completionpercentage;
	 private Date completiondate;	
	 private String requestId;
	 private Boolean status;
	 private StreamedContent file;
	 private List<UpdatestatusVo> updatestatusList = new ArrayList<UpdatestatusVo>();
	 private List<UpdatestatusVo> filteredUpdateList = new ArrayList<UpdatestatusVo>();
	 private UpdatestatusInterface updatestatusInterface = new UpdatestatusImpl();
	
	 private UpdatestatusVo updatestatusVo = new UpdatestatusVo();

	
	


	 
	 
	 
	 
	 
	 
	 @PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--create request-->");
				updatestatusList = new ArrayList<UpdatestatusVo>();
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				updatestatusList = updatestatusInterface.getupdatestatusDetails(userName);
				setFilteredUpdateList(updatestatusList);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	 
	 
	 
	 public String updatestatusPage()
		{
			try
			{
				updatestatusList = new ArrayList<UpdatestatusVo>();
				System.out.println("--create new request-->");
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				updatestatusList= updatestatusInterface.getupdatestatusDetails(userName);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return "updatestatustask";
		}
		
	 
	 
	 public void modifyAction() {
			
			
	        try
	        {
	        	System.out.println("modify action"+requestId);
	            //addMessage("Welcome to Primefaces!!");
	        	setRequestId(requestId);
	        	updatestatusVo = updatestatusInterface.getRequestById(requestId);      	
				
	        
	        		setTitle(updatestatusVo.getTitle() != null ? updatestatusVo.getTitle(): "");
	        		setDescription(updatestatusVo.getDescription() != null ? updatestatusVo.getDescription() : "");
	        		setCompletionpercentage(updatestatusVo.getCompletionpercentage());
	          		
	        	
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
				
	        	result = updatestatusInterface.updateRequestById(requestId, completiondate, completionpercentage);
	        	
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
	        		updatestatusList = updatestatusInterface.getupdatestatusDetails(userName);
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
			return "updatestatustask";
		}
		
		
		 public StreamedContent fileDownloadView() { 
			 	System.out.println("hello");
			 	InputStream stream = null;
			 	try{
			 		//System.out.println("modify action"+requestId);
		            //addMessage("Welcome to Primefaces!!");
		        	setRequestId(requestId);
		        	updatestatusVo = updatestatusInterface.getRequestById(requestId);
		        	//stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resource/temp/avatar.jpg");
		        	if(updatestatusVo.getFile() != null && updatestatusVo.getFile().length != 0)
		        	{
		        		stream = new ByteArrayInputStream(updatestatusVo.getFile());
		        		
		        	}
		        	String fileExtn = "";
		        	String strArr[] = {};
		        	
		        	if(updatestatusVo.getFileName() != null && !updatestatusVo.getFileName().trim().equals(""))
		        	{
		        		strArr = updatestatusVo.getFileName().split(".");
		        		for(String extn : strArr){
		        			fileExtn = extn;
		        		}
		        		file = new DefaultStreamedContent(stream, fileExtn, "downloaded_"+updatestatusVo.getFileName().trim());
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


		public UploadedFile getAttachment() {
			return attachment;
		}


		public void setAttachment(UploadedFile attachment) {
			this.attachment = attachment;
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


		public String getRequestId() {
			return requestId;
		}


		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}


		public Boolean getStatus() {
			return status;
		}


		public void setStatus(Boolean status) {
			this.status = status;
		}


		public List<UpdatestatusVo> getUpdatestatusList() {
			return updatestatusList;
		}


		public void setUpdatestatusList(List<UpdatestatusVo> updatestatusList) {
			this.updatestatusList = updatestatusList;
		}


		public UpdatestatusVo getUpdatestatusVo() {
			return updatestatusVo;
		}


		public void setUpdatestatusVo(UpdatestatusVo updatestatusVo) {
			this.updatestatusVo = updatestatusVo;
		}


	


		public List<UpdatestatusVo> getFilteredUpdateList() {
			return filteredUpdateList;
		}


		public void setFilteredUpdateList(List<UpdatestatusVo> filteredUpdateList) {
			this.filteredUpdateList = filteredUpdateList;
		}


		public StreamedContent getFile() {
			return file;
		}


		public void setFile(StreamedContent file) {
			this.file = file;
		}


		public Integer getStage() {
			return stage;
		}


		public void setStage(Integer stage) {
			this.stage = stage;
		}


		
		
	 
	 
	
	 

}
