package com.reqman.beans;


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

import org.primefaces.model.UploadedFile;





import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.pojo.Request;
import com.reqman.util.SessionUtils;


import com.reqman.vo.NewrequestVo;

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
		private Boolean status;
	 private NewrequestInterface newrequestInterface = new NewrequestImpl();	
	
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
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public String createRequestPage()
	{
		try
		{
			newrequestList = new ArrayList<NewrequestVo>();
			System.out.println("--create new request-->");
			
		
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

		NewrequestInterface newrequestinterface = new NewrequestImpl();
		int result = 0;
		try {
			newrequestList = new ArrayList<NewrequestVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");

			System.out.println("friendlist" + userfriendlist);

			result = newrequestinterface.save(title, description, usercategory,
					userproject, userrequesttype, attachment, userName,
					completiondate, userfriendlist);

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
		
		NewrequestVo newrequestVo = new NewrequestVo();
        try
        {
        	System.out.println("modify action"+requestId);
            //addMessage("Welcome to Primefaces!!");
        	setRequestId(requestId);
        	newrequestVo = newrequestInterface.getRequestById(requestId);
        	if(newrequestVo != null && newrequestVo.getStatus().trim().equalsIgnoreCase("Active")){
        		setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle() : "");
        		setStatus(true);
        	}
        	else
        	{
        		setTitle(newrequestVo.getTitle() != null ? newrequestVo.getTitle(): "");
        		setStatus(false);
        	}
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifyrequest.xhtml");

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	public String updateCategory()
	{
		int result = 0;
		try{
			System.out.println("--updateCategory-status-"+status);
			System.out.println("--updateCategory-categoryId-"+requestId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			System.out.println("--usersession--userName-->"+userName);
			
        	result = newrequestInterface.updateRequestById(requestId, status,description);
        	
        	if(result == 2)
        	{
        		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the Category",
								"Problem while modifying the Category"));
				return "modifycategory.xhtml";
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
			return "modifyrequest.xhtml";
		}
		return "request";
	}
	
/*	public void postProcessXLS(Object document) {
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
    }*/
	

	private void setStatus(boolean b) {
		// TODO Auto-generated method stub
		
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


	
	
	
	
}
