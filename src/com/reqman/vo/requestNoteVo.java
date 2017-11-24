package com.reqman.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

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

public class requestNoteVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2868754614364417825L;
	
	private Integer noteId;
	private String message;
	private String createdon;
	private String createdby;
	private String  time;
	
	
	
	
	
	// Comparator for sorting the list by Id //
	 public static Comparator<requestNoteVo> NoteIdComparator = new Comparator<requestNoteVo>() {

		 @Override
		  public int compare(requestNoteVo s1, requestNoteVo s2) {
		   Integer Id1 = s1.getNoteId();
		   Integer Id2 = s2.getNoteId();

		   // ascending order
		   return Id2.compareTo(Id1);

		   
		  }
		 };
	
	 
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
			
		

	
	
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	
	

}
