package com.reqman.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.chart.PieChartModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.reqman.dao.CategoryMasterInterface;
import com.reqman.daoimpl.CategoryMasterImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.CategoryVo;

@ManagedBean(name="categorybean",eager = true)
@ViewScoped
public class Categorybean implements Serializable
{
	private static final long serialVersionUID = 3076255353187837257L;
	
	private List<CategoryVo> categoryList = new ArrayList<CategoryVo>();	
	private List<CategoryVo> categoryList1 = new ArrayList<CategoryVo>();
	private List<CategoryVo> categoryList2 = new ArrayList<CategoryVo>();
	private List<CategoryVo> filteredCategoryList = new ArrayList<CategoryVo>();
	private String categoryName;	
	private Boolean status;	
	private String categoryId;
	private CategoryVo selectedCategory;
	private CategoryVo categoryVo = new CategoryVo();
	private CategoryMasterInterface categoryMasterInterface = new CategoryMasterImpl();	
	
    private PieChartModel piechart;
	
	@PostConstruct
    public void init() 
	{
		try
		{
			categoryList = new ArrayList<CategoryVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			categoryList = categoryMasterInterface.getCategoryDetails(userName);
			setFilteredCategoryList(categoryList);
			categoryList1 = categoryMasterInterface.getCategoryStatus(userName);
			categoryList2 = categoryMasterInterface.getCategoryStatusfalse(userName);
			 createPieModels();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	 private void createPieModels() {
	        piechart = new PieChartModel();
	     	        
	    int random1 = categoryList2.size(); // Method to get data from db
	    int random2 = categoryList1.size();  // Method to get data from db

	    piechart.getData().put("Active", random2);	
	    piechart.getData().put("In-Actve", random1);
	     
	    piechart.setTitle("Category Status");
	    piechart.setLegendPosition("ne");
	    //piechart.setSeriesColors("green,red");
	   // piechart.setFill(false);
	    piechart.setShowDataLabels(true);
	 }
	
	
	
	
	
	
	
	public String categoryPage()
	{
		try
		{
			categoryList = new ArrayList<CategoryVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			categoryList = categoryMasterInterface.getCategoryDetails(userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "category";
	}
	
	
	public String newCategory()
	{
		try
		{
			categoryList = new ArrayList<CategoryVo>();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "category";
	}
	
	
	public String saveCategory()
	{
		int result = 0;
		UserSession usersession = new UserSession();
		try
		{
			categoryList = new ArrayList<CategoryVo>();
			System.out.println("--categoryName-->"+categoryName);
			System.out.println("--status-->"+status);
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			System.out.println("--usersession--userName-->"+userName);
			result = categoryMasterInterface.savecategory(categoryName, status, userName);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This Category allready exists in your list. ",
								"If inactive you can acivate the category status from the list shown below."));
				//return "category";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This Category allready exists in your list. ",
								"If inactive you can acivate the category status from the list shown below."));
			//	return "category";
			}
			if(result == 3)
			{
				
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"New Category added successfully.",
								"New Category added successfully."));
			}
			
			categoryList = categoryMasterInterface.getCategoryDetails(userName);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),
							"Server Error "+e.getMessage()));
			return "category";
		}
		return "category";
	}
	
	
	 public void onCellEdit(CellEditEvent event) {
	     int result = 0;
	     String oldValue = "";
	     String newValue = "";
	     Integer updatecategoryId = 0;
		 try
		 {
			 oldValue = (String)event.getOldValue();
		     newValue = (String)event.getNewValue();
		     updatecategoryId = (Integer) event.getComponent().getAttributes().get("updateCategoryId");
	         System.out.println("updatecategoryId"+updatecategoryId);
	        if(newValue != null && !newValue.equals(oldValue)) 
	        {
	        	result = categoryMasterInterface.updateCategory(oldValue, newValue, updatecategoryId);
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"The status of the category has been change successfully.","Old status is " + oldValue + ", New status is " + newValue);
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
	
	
	
	
	public void modifyAction() {
		
		
        try
        {
        	System.out.println("modify action"+categoryId);
            //addMessage("Welcome to Primefaces!!");
        	setCategoryId(categoryId);
        	categoryVo = categoryMasterInterface.getUserCategoryById(categoryId);
        	if(categoryVo != null && categoryVo.getStatus().trim().equalsIgnoreCase("Active")){
        		
        		setCategoryName(categoryVo.getName() != null ? categoryVo.getName() : "");
        		setStatus(true);
        	}
        	else
        	{
        		setCategoryName(categoryVo.getName() != null ? categoryVo.getName() : "");
        		setStatus(false);
        	}
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifycategory.xhtml");

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
			System.out.println("--updateCategory-categoryId-"+categoryId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			System.out.println("--usersession--userName-->"+userName);
			
        	result = categoryMasterInterface.updateUserCategoryById(categoryId, status);
        	
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
        		categoryList = categoryMasterInterface.getCategoryDetails(userName);
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
			return "modifycategory.xhtml";
		}
		return "category";
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
	
	public List<CategoryVo> getCategoryList() 
	{
		return categoryList;
	}

	public void setCategoryList(List<CategoryVo> categoryList) 
	{
		this.categoryList = categoryList;
	}

	public String getCategoryName() 
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName) 
	{
		this.categoryName = categoryName;
	}

	public Boolean getStatus() 
	{
		return status;
	}

	public void setStatus(Boolean status) 
	{
		this.status = status;
	}

	public String getCategoryId() 
	{
		return categoryId;
	}

	public void setCategoryId(String categoryId) 
	{
		this.categoryId = categoryId;
	}


	public List<CategoryVo> getFilteredCategoryList() {
		return filteredCategoryList;
	}


	public void setFilteredCategoryList(List<CategoryVo> filteredCategoryList) {
		this.filteredCategoryList = filteredCategoryList;
	}


	public CategoryVo getSelectedCategory() {
		return selectedCategory;
	}


	public void setSelectedCategory(CategoryVo selectedCategory) {
		this.selectedCategory = selectedCategory;
	}


	public List<CategoryVo> getCategoryList1() {
		return categoryList1;
	}


	public void setCategoryList1(List<CategoryVo> categoryList1) {
		this.categoryList1 = categoryList1;
	}
	
	public CategoryVo getCategoryVo() {
		return categoryVo;
	}


	public void setCategoryVo(CategoryVo categoryVo) {
		this.categoryVo = categoryVo;
	}


	public PieChartModel getPiechart() {
		return piechart;
	}


	public void setPiechart(PieChartModel piechart) {
		this.piechart = piechart;
	}

	public List<CategoryVo> getCategoryList2() {
		return categoryList2;
	}

	public void setCategoryList2(List<CategoryVo> categoryList2) {
		this.categoryList2 = categoryList2;
	}


}
