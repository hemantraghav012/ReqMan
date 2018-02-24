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

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.reqman.dao.responseInterface;
import com.reqman.daoimpl.ResponseImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.ResponseVo;
import com.reqman.vo.UpdatestatusVo;



@ManagedBean(name="responsebean",eager = true)
@RequestScoped
@ViewScoped
public class Responsebean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 176771090688847539L;
	
	 private String requestId;
	 private Integer stage;
	 private Date completiondate;
	 private StreamedContent file;
	 private Boolean status;
	 private String title;
	 private String description;
	 private Integer requeststatus;
	 private List<ResponseVo> responseList = new ArrayList<ResponseVo>();
	 private responseInterface responseInterface = new ResponseImpl();
	 private List<ResponseVo> filteredResponseList = new ArrayList<ResponseVo>();
	 private ResponseVo responseVo = new ResponseVo();
	 private  String message;  
	 private String actualeffort;
		private String estimatedeffort;
		private String priority;
		private Integer weightage;
	 
	@PostConstruct
   public void init() 
	{
		try
		{
			 	
			System.out.println("--create request-->");
			responseList = new ArrayList<ResponseVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);						
			responseList = responseInterface.getresponseDetails(userName);
			setFilteredResponseList(responseList);
			
			
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	 public String responsePage()
		{
			try
			{
				responseList = new ArrayList<ResponseVo>();
				System.out.println("--create new request-->");
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				responseList = responseInterface.getresponseDetails(userName);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return "responsetonewrequest";
		}
		
	
	
	
public void modifyAction() {
		
		
        try
        {
        	System.out.println("modify action"+requestId);
            //addMessage("Welcome to Primefaces!!");
        	setRequestId(requestId);
        	responseVo = responseInterface.getResponseById(requestId);      	
			
        	if(responseVo != null && responseVo.getStage().trim().equalsIgnoreCase("Request")){
        		setTitle(responseVo.getTitle() != null ? responseVo.getTitle(): "");
        		setDescription(responseVo.getDescription() != null ? responseVo.getDescription() : "");  
        		setCompletiondate(responseVo.getCompletiondate());
        		 setActualeffort(responseVo.getActualeffort());
          		setStage(1);
        
        	}
        	
        	else if(responseVo != null && responseVo.getStage().trim().equalsIgnoreCase("Returned")){
        		setTitle(responseVo.getTitle() != null ? responseVo.getTitle(): "");
        		setDescription(responseVo.getDescription() != null ? responseVo.getDescription() : "");
        		setCompletiondate(responseVo.getCompletiondate());
        		 setActualeffort(responseVo.getActualeffort());
        		setStage(3);
        	
        	}
        	else {
        		setTitle(responseVo.getTitle() != null ? responseVo.getTitle(): "");
        		setDescription(responseVo.getDescription() != null ? responseVo.getDescription() : "");
        		setCompletiondate(responseVo.getCompletiondate());
        		 setActualeffort(responseVo.getActualeffort());
          		setStage(2);
        
        	
        		
        	}
        	System.out.println("modify action"+completiondate);
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifyresponse.xhtml");

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
	public String updateResponse()
	{
		int result = 0;
		try{
			System.out.println("--updateRequest-status-"+stage);
		
			System.out.println("--updateRequest-status-"+completiondate);
			
			
			System.out.println("--updateRequesr requestid-"+requestId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");			
			System.out.println("--usersession--userName-->"+userName);
			System.out.println("--usersession--userName-->"+completiondate);
        	result = responseInterface.updateResponsetById(requestId,stage, completiondate,userName,message,actualeffort);
        	
        	if(result == 2)
        	{
        		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the Category",
								"Problem while modifying the Category"));
				return "modifyresponse.xhtml";
        	}
        	
        	if(result == 1)
        	{
        		responseList = responseInterface.getresponseDetails(userName);
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
			return "modifyresponse.xhtml";
		}
		return "responsetonewrequest";
	}
	
	
	 public StreamedContent fileDownloadView() { 
		 	System.out.println("hello");
		 	InputStream stream = null;
		 	try{
		 		//System.out.println("modify action"+requestId);
	            //addMessage("Welcome to Primefaces!!");
	        	setRequestId(requestId);
	        	responseVo = responseInterface.getResponseById(requestId);
	        	//stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resource/temp/avatar.jpg");
	        	if(responseVo.getFile() != null && responseVo.getFile().length != 0)
	        	{
	        		stream = new ByteArrayInputStream(responseVo.getFile());
	        		
	        	}
	        	String fileExtn = "";
	        	String strArr[] = {};
	        	
	        	if(responseVo.getFileName() != null && !responseVo.getFileName().trim().equals(""))
	        	{
	        		strArr = responseVo.getFileName().split(".");
	        		for(String extn : strArr){
	        			fileExtn = extn;
	        		}
	        		file = new DefaultStreamedContent(stream, fileExtn, "downloaded_"+responseVo.getFileName().trim());
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
	
	
	
	
	
	
	
	
	
	


	public List<ResponseVo> getResponseList() {
		return responseList;
	}


	public void setResponseList(List<ResponseVo> responseList) {
		this.responseList = responseList;
	}


	


	public List<ResponseVo> getFilteredResponseList() {
		return filteredResponseList;
	}


	public void setFilteredResponseList(List<ResponseVo> filteredResponseList) {
		this.filteredResponseList = filteredResponseList;
	}


	public ResponseVo getResponseVo() {
		return responseVo;
	}


	public void setResponseVo(ResponseVo responseVo) {
		this.responseVo = responseVo;
	}


	public Integer getRequeststatus() {
		return requeststatus;
	}

	public void setRequeststatus(Integer requeststatus) {
		this.requeststatus = requeststatus;
	}


	public String getRequestId() {
		return requestId;
	}





	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}





	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public Date getCompletiondate() {
		return completiondate;
	}

	public void setCompletiondate(Date completiondate) {
		this.completiondate = completiondate;
	}


	public StreamedContent getFile() {
		return file;
	}


	public void setFile(StreamedContent file) {
		this.file = file;
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





	public Boolean getStatus() {
		return status;
	}





	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
	
	



	
}
