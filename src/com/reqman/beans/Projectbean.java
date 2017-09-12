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
@RequestScoped
public class Projectbean implements Serializable{

	
	private static final long serialVersionUID = -3573038442804289644L;
	

	
	private ProjectMasterInterface  projectMasterInterface = new ProjectMasterImpl();
	
	private  List<ProjectVo> projectList = new ArrayList<ProjectVo>();
	private List<ProjectVo> filteredProjectList = new ArrayList<ProjectVo>();
	
	private String projectName;	
	private Boolean status;	
	private String projectId;
	private ProjectVo selectedProject;
	
	@PostConstruct
    public void init() {
		try
		{
			projectList = new ArrayList<ProjectVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			projectList = projectMasterInterface.getProjectDetails(userName);
			setFilteredProjectList(projectList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String projectPage()
	{
		try
		{
			projectList = new ArrayList<ProjectVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			projectList = projectMasterInterface.getProjectDetails(userName);
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
		return "createproject";
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
			result = projectMasterInterface.saveproject(projectName, status, userName);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Project already exist",
								"Project already exist"));
				return "createproject";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"project already exist and in active, please activate by using modify project ",
								"Project already exist and in active, please activate by using modify projec"));
				return "createproject";
			}
			if(result == 3)
			{
				
			projectList = projectMasterInterface.getProjectDetails(userName);
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Project created  successfully.",
								"Project created  successfully."));
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
			return "createproject";
		}
		return "project";
	}
	
	
	public void modifyAction() {
		
		ProjectVo projectVo = new ProjectVo();
        
        try{
        	System.out.println("modify action"+ projectId);
            //addMessage("Welcome to Primefaces!!");
        	setProjectId(projectId);
        	projectVo = projectMasterInterface.getUserProjectById(projectId);
        	if(projectVo != null && projectVo.getStatus().trim().equalsIgnoreCase("Active")){
        		setProjectName(projectVo.getName() != null ? projectVo.getName() : "");
        		setStatus(true);
        	}
        	else
        	{
        		setProjectName(projectVo.getName() != null ? projectVo.getName() : "");
        		setStatus(false);
        	}
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifyproject.xhtml");

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
	public String updateProject(){
		int result = 0;
		try{
			System.out.println("--updateproject-status-"+status);
			System.out.println("--updateProject-projectId-"+projectId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			
        	result = projectMasterInterface.updateUserprojectById(projectId, status);
        	
        	if(result == 2){
        		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the Project",
								"Problem while modifying the Project"));
				return "modifyproject.xhtml";
        	}
        	
        	if(result == 1){
        		projectList = projectMasterInterface.getProjectDetails(userName);
        	}
        	
        	
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the Project",
							"Problem while modifying the Project"));
			return "modifyproject.xhtml";
		}
		return "project";
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

	

}
