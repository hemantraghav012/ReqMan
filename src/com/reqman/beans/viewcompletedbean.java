package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
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
import com.reqman.dao.UpdatestatusInterface;
import com.reqman.daoimpl.UpdatestatusImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.UpdatestatusVo;




@ManagedBean(name = "viewcompleted", eager = true)
@RequestScoped
@ViewScoped
public class viewcompletedbean implements Serializable {

	
	private static final long serialVersionUID = -4023889678999425276L;
	
	
	 private UpdatestatusInterface updatestatusInterface = new UpdatestatusImpl();
	 private UpdatestatusVo updatestatusVo = new UpdatestatusVo();
	 private List<UpdatestatusVo> completedtaskList = new ArrayList<UpdatestatusVo>();
	 private List<UpdatestatusVo> filteredCompletedList = new ArrayList<UpdatestatusVo>();
	

	 
	 @PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--create request-->");
				completedtaskList =new ArrayList<UpdatestatusVo>();
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);				
				completedtaskList=updatestatusInterface.getcompletedtaskDetails(userName.toLowerCase().trim());
				setFilteredCompletedList(completedtaskList); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		// for print excel
		public void postProcessXLS(Object document) {
			HSSFWorkbook wb = (HSSFWorkbook) document;
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow header = sheet.getRow(0);

			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				HSSFCell cell = header.getCell(i);

				cell.setCellStyle(cellStyle);
			}

		}

		// for print pdf
		public void preProcessPDF(Object document) throws IOException,
				BadElementException, DocumentException {
			Document pdf = (Document) document;
			pdf.open();
			pdf.setPageSize(PageSize.A4);

			// ExternalContext externalContext =
			// FacesContext.getCurrentInstance().getExternalContext();
			// String logo = externalContext.getRealPath("") + File.separator +
			// "resources" + File.separator + "demo" + File.separator + "images" +
			// File.separator + "prime_logo.png";

			pdf.addTitle("Collabor8");
		}




		public UpdatestatusVo getUpdatestatusVo() {
			return updatestatusVo;
		}

		public void setUpdatestatusVo(UpdatestatusVo updatestatusVo) {
			this.updatestatusVo = updatestatusVo;
		}


		public List<UpdatestatusVo> getCompletedtaskList() {
			return completedtaskList;
		}



		public void setCompletedtaskList(List<UpdatestatusVo> completedtaskList) {
			this.completedtaskList = completedtaskList;
		}



		public List<UpdatestatusVo> getFilteredCompletedList() {
			return filteredCompletedList;
		}

		public void setFilteredCompletedList(List<UpdatestatusVo> filteredCompletedList) {
			this.filteredCompletedList = filteredCompletedList;
		}
		
		
	
}
