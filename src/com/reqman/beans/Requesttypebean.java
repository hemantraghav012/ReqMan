package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.CellEditEvent;
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
@ViewScoped
public class Requesttypebean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 428748461879170255L;
	

private  List<RequesttypeVo> requesttypeList = new ArrayList<RequesttypeVo>();
private  List<RequesttypeVo> requesttypeList1 = new ArrayList<RequesttypeVo>();
private  List<RequesttypeVo> requesttypeList2 = new ArrayList<RequesttypeVo>();
private  List<RequesttypeVo> accountrequesttypeList = new ArrayList<RequesttypeVo>();
	private requesttypeMasterInterface requesttypeMasterInterface = new RequesttypeMasterImpl();
	private	RequesttypeVo requesttypeVo = new RequesttypeVo();
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
			requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName.toLowerCase().trim());
			setFilteredRequesttypeList(requesttypeList);
			requesttypeList1 = requesttypeMasterInterface.getRequesttypeStatus(userName.toLowerCase().trim());
			requesttypeList2 = requesttypeMasterInterface.getRequesttypefalseStatus(userName.toLowerCase().trim());
			accountrequesttypeList = requesttypeMasterInterface.getallaccountrequesttype(userName.toLowerCase().trim());
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

	    piechart.getData().put("Active", random2);	
	    piechart.getData().put("In-Active", random1);
  
	    piechart.setTitle("Type Status");
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
		requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName.toLowerCase().trim());
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
		return "requesttype";
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
			result = requesttypeMasterInterface.saverequesttype(requesttypeName, status, userName.toLowerCase().trim());
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This type allready exists in your list. ",
								"If inactive you can active the type status from the list shown below."));
			//	return "requesttype";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This type allready exists in your list. ",
								"If inactive you can active the type status from the list shown below."));
				//return "requesttype";
			}
			if(result == 3)
			{
				
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"New Type added successfully.",
								""));
			}
			requesttypeList = requesttypeMasterInterface.getRequesttypeDetails(userName.toLowerCase().trim());
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),
							""));
			return "requesttype";
		}
		return "requesttype";
	}
	
	public void onCellEdit(CellEditEvent event) {
	     int result = 0;
	     String oldValue = "";
	     String newValue = "";
	     Integer updaterequesttypeId = 0;
		 try
		 {
			 oldValue = (String)event.getOldValue();
		     newValue = (String)event.getNewValue();
		     updaterequesttypeId = (Integer) event.getComponent().getAttributes().get("updateRequesttypeId");
	         System.out.println("updaterequesttypeId"+updaterequesttypeId);
	        if(newValue != null && !newValue.equals(oldValue)) 
	        {
	        	result = requesttypeMasterInterface.updateRequesttype(oldValue, newValue, updaterequesttypeId);
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"The status of the type has been changed successfully.","Old status is " + oldValue + ", New status is " + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error", e.getMessage().toString());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		 }
	       
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



	public RequesttypeVo getRequesttypeVo() {
		return requesttypeVo;
	}



	public void setRequesttypeVo(RequesttypeVo requesttypeVo) {
		this.requesttypeVo = requesttypeVo;
	}



	public List<RequesttypeVo> getAccountrequesttypeList() {
		return accountrequesttypeList;
	}



	public void setAccountrequesttypeList(List<RequesttypeVo> accountrequesttypeList) {
		this.accountrequesttypeList = accountrequesttypeList;
	}
	
	
}
