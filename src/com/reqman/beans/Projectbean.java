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
import com.reqman.dao.ProjectMasterInterface;
import com.reqman.daoimpl.ProjectMasterImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.ProjectVo;

@ManagedBean(name="projectbean",eager = true)
@ViewScoped
public class Projectbean implements Serializable{

	
	private static final long serialVersionUID = -3573038442804289644L;
	

	
	private ProjectMasterInterface  projectMasterInterface = new ProjectMasterImpl();
	private  List<ProjectVo> projectList1 = new ArrayList<ProjectVo>();
	private  List<ProjectVo> projectList2 = new ArrayList<ProjectVo>();
	private  List<ProjectVo> projectList = new ArrayList<ProjectVo>();
	private List<ProjectVo> filteredProjectList = new ArrayList<ProjectVo>();	
	private String projectName;	
	private Boolean status;	
	private String projectId;
	private ProjectVo selectedProject;
	  private PieChartModel piechart;
	  private  ProjectVo projectVo = new ProjectVo();
	private Boolean projectaccess;
	
	@PostConstruct
    public void init() {
		try
		{
			projectList = new ArrayList<ProjectVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			projectList = projectMasterInterface.getProjectDetails(userName.toLowerCase().trim());
			setFilteredProjectList(projectList);
			projectList1 = projectMasterInterface.getProjectStatus(userName.toLowerCase().trim());
			projectList2 = projectMasterInterface.getProjectfalseStatus(userName.toLowerCase().trim());
			 createPieModels();
			 projectaccess = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	 private void createPieModels() {
	        piechart = new PieChartModel();
	     	        
	    int random1 = projectList2.size(); // Method to get data from db
	    int random2 = projectList1.size();  // Method to get data from db

	    piechart.getData().put("Active", random2);	
	    piechart.getData().put("In-Active", random1);
	    
	    piechart.setTitle("Project Status");
	    piechart.setLegendPosition("ne");
	    
	    piechart.setShowDataLabels(true);
	 }
	
	
	
	


	public String projectPage()
	{
		try
		{
			projectList = new ArrayList<ProjectVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName.toLowerCase().trim());
			projectList = projectMasterInterface.getProjectDetails(userName.toLowerCase().trim());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "project";
	}
	
	
	public String newProject()
	{
		try
		{
			projectList = new ArrayList<ProjectVo>();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "project";
	}
	
	
	public String saveProject()
	{
		int result = 0;
		UserSession usersession = new UserSession();
		try
		{
			projectList = new ArrayList<ProjectVo>();
			System.out.println("--projectName-->"+projectName);
			System.out.println("--status-->"+status);
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			result = projectMasterInterface.saveproject(projectName, status, userName.toLowerCase().trim(),projectaccess);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This project allready exists in your list. ",
								"If inactive you can active the project status from the list shown below."));
			//	return "project";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This project allready exists in your list. ",
								"If inactive you can active the project status from the list shown below."));
			//	return "project";
			}
			if(result == 3)
			{
				
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"New Project added successfully.",""));
			}
			
			projectList = projectMasterInterface.getProjectDetails(userName.toLowerCase().trim());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),""));
			return "project";
		}
		return "project";
	}
	
	
	
	 public void onCellEdit(CellEditEvent event) {
	     int result = 0;
	     String oldValue = "";
	     String newValue = "";
	     Integer updateprojectId = 0;
		 try
		 {
			 oldValue = (String)event.getOldValue();
		     newValue = (String)event.getNewValue();
		     updateprojectId = (Integer) event.getComponent().getAttributes().get("updateProjectId");
	         System.out.println("updateprojectId"+updateprojectId);
	        if(newValue != null && !newValue.equals(oldValue)) 
	        {
	        	result = projectMasterInterface.updateProject(oldValue, newValue, updateprojectId);
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"The status/project access of the project has been changed successfully.","Old status/project access is " + oldValue + ", status/project access " + newValue);
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


	public List<ProjectVo> getProjectList() {
		return projectList;
	}


	public void setProjectList(List<ProjectVo> projectList) {
		this.projectList = projectList;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public List<ProjectVo> getFilteredProjectList() {
		return filteredProjectList;
	}


	public void setFilteredProjectList(List<ProjectVo> filteredProjectList) {
		this.filteredProjectList = filteredProjectList;
	}


	public ProjectVo getSelectedProject() {
		return selectedProject;
	}


	public void setSelectedProject(ProjectVo selectedProject) {
		this.selectedProject = selectedProject;
	}


	public List<ProjectVo> getProjectList1() {
		return projectList1;
	}


	public void setProjectList1(List<ProjectVo> projectList1) {
		this.projectList1 = projectList1;
	}


	public PieChartModel getPiechart() {
		return piechart;
	}


	public void setPiechart(PieChartModel piechart) {
		this.piechart = piechart;
	}



	public ProjectVo getProjectVo() {
		return projectVo;
	}



	public void setProjectVo(ProjectVo projectVo) {
		this.projectVo = projectVo;
	}

	public List<ProjectVo> getProjectList2() {
		return projectList2;
	}



	public void setProjectList2(List<ProjectVo> projectList2) {
		this.projectList2 = projectList2;
	}



	public Boolean getProjectaccess() {
		return projectaccess;
	}



	public void setProjectaccess(Boolean projectaccess) {
		this.projectaccess = projectaccess;
	}



	


}
