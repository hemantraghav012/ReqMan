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
	private Boolean requeststatus;
	private Boolean teammemberstatus;
	
	
	
	
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
	

			
		

	
	
	
	
	public Boolean getRequeststatus() {
				return requeststatus;
			}

			public void setRequeststatus(Boolean requeststatus) {
				this.requeststatus = requeststatus;
			}

			public Boolean getTeammemberstatus() {
				return teammemberstatus;
			}

			public void setTeammemberstatus(Boolean teammemberstatus) {
				this.teammemberstatus = teammemberstatus;
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
