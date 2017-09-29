package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.model.chart.PieChartModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.reqman.dao.requesttypeMasterInterface;
import com.reqman.daoimpl.RequesttypeMasterImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.ProjectVo;
import com.reqman.vo.RequesttypeVo;

@ManagedBean(name="requesttypebean",eager = true)
@RequestScoped
public class Requesttypebean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 428748461879170255L;
	

private  List<RequesttypeVo> requesttypeList = new ArrayList<RequesttypeVo>();
private  List<RequesttypeVo> requesttypeList1 = new ArrayList<RequesttypeVo>();
private  List<RequesttypeVo> requesttypeList2 = new ArrayList<RequesttypeVo>();
	private requesttypeMasterInterface requesttypeMasterInterface = new RequesttypeMasterImpl();
	
	private  List<RequesttypeVo> filteredRequesttypeList = new ArrayList<RequesttypeVo>();
	
	private String requesttypeName;
	
	private Boolean status;
	private ProjectVo selectedRequesttype;
	private String requesttypeId;
	  private PieChartModel piechart;
	
	
	@PostConstruct
    public void init() {
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
			setFilteredRequesttypeList(requesttypeList);
			requesttypeList1 = requesttypeMasterInterface.getRequesttypeStatus(userName);
			requesttypeList2 = requesttypeMasterInterface.getRequesttypefalseStatus(userName);
			 createPieModels();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	 private void createPieModels() {
	        piechart = new PieChartModel();
	     	        
	    int random1 = requesttypeList2.size(); // Method to get data from db
	    int random2 = requesttypeList1.size();  // Method to get data from db

	    piechart.getData().put("false status", random1);
	    piechart.getData().put("true Status", random2);	  
	    piechart.setTitle("Requesttype Status");
	    piechart.setLegendPosition("ne");
	    //piechart.setSeriesColors("green,red");
	   // piechart.setFill(false);
	    piechart.setShowDataLabels(true);
	 }
	
	
	public String requesttypePage()
	{
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
		requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "requesttype";
	}
	
	
	public String newRequesttype()
	{
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "createrequesttype";
	}
	
	
	public String saveRequesttype()
	{
		int result = 0;
		UserSession usersession = new UserSession();
		try
		{
			requesttypeList = new ArrayList<RequesttypeVo>();
			System.out.println("--requesttypeName-->"+requesttypeName);
			System.out.println("--status-->"+status);
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			result = requesttypeMasterInterface.saverequesttype(requesttypeName, status, userName);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"requesttype already exist",
								"requesttype already exist"));
				return "createrequesttype";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"requesttype already exist and in active, please activate by using modify category ",
								"requesttypeList already exist and in active, please activate by using modify category"));
				return "createrequesttype";
			}
			if(result == 3)
			{
				
				requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"requesttype created  successfully.",
								"requesttype created  successfully."));
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),
							"Server Error "+e.getMessage()));
			return "createrequesttype";
		}
		return "requesttype";
	}
	
	
	public void modifyAction() {
		
		RequesttypeVo requesttypeVo = new RequesttypeVo();
        
        try{
        	System.out.println("modify action"+requesttypeId);
            //addMessage("Welcome to Primefaces!!");
        	setRequesttypeId(requesttypeId);
        	requesttypeVo = requesttypeMasterInterface.getUserRequesttypeById(requesttypeId);
        	if(requesttypeVo != null && requesttypeVo.getStatus().trim().equalsIgnoreCase("Active")){
        		setRequesttypeName(requesttypeVo.getName() != null ? requesttypeVo.getName() : "");
        		setStatus(true);
        	}
        	else
        	{
        		setRequesttypeName(requesttypeVo.getName() != null ? requesttypeVo.getName() : "");
        		setStatus(false);
        	}
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifyrequesttype.xhtml");

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
	public String updateRequesttype(){
		int result = 0;
		try{
			System.out.println("--updateRequesttype-status-"+status);
			System.out.println("--updateRequesttypeCategory-requesttypeId-"+requesttypeId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			
        	result = requesttypeMasterInterface.updateUserRequesttypeById(requesttypeId, status);
        	
        	if(result == 2){
        		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the requesttype",
								"Problem while modifying the requesttype"));
				return "modifyrequesttype.xhtml";
        	}
        	
        	if(result == 1){
        		requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName);
        	}
        	
        	
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the requesttype",
							"Problem while modifying the requesttype"));
			return "modifyrequesttype.xhtml";
		}
		return "requesttype";
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
 
        
        pdf.addTitle("Collabor8");
    }

	
	
	
	
	
	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public List<RequesttypeVo> getRequesttypeList() {
		return requesttypeList;
	}

	public void setRequesttypeList(List<RequesttypeVo> requesttypeList) {
		this.requesttypeList = requesttypeList;
	}

	public String getRequesttypeName() {
		return requesttypeName;
	}

	public void setRequesttypeName(String requesttypeName) {
		this.requesttypeName = requesttypeName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getRequesttypeId() {
		return requesttypeId;
	}

	public void setRequesttypeId(String requesttypeId) {
		this.requesttypeId = requesttypeId;
	}


	public List<RequesttypeVo> getFilteredRequesttypeList() {
		return filteredRequesttypeList;
	}


	public void setFilteredRequesttypeList(List<RequesttypeVo> filteredRequesttypeList) {
		this.filteredRequesttypeList = filteredRequesttypeList;
	}


	public ProjectVo getSelectedRequesttype() {
		return selectedRequesttype;
	}


	public void setSelectedRequesttype(ProjectVo selectedRequesttype) {
		this.selectedRequesttype = selectedRequesttype;
	}


	public List<RequesttypeVo> getRequesttypeList1() {
		return requesttypeList1;
	}


	public void setRequesttypeList1(List<RequesttypeVo> requesttypeList1) {
		this.requesttypeList1 = requesttypeList1;
	}


	public PieChartModel getPiechart() {
		return piechart;
	}


	public void setPiechart(PieChartModel piechart) {
		this.piechart = piechart;
	}



	public List<RequesttypeVo> getRequesttypeList2() {
		return requesttypeList2;
	}



	public void setRequesttypeList2(List<RequesttypeVo> requesttypeList2) {
		this.requesttypeList2 = requesttypeList2;
	}
	
	
}
