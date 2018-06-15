package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import com.reqman.dao.FriendMasterInterface;
import com.reqman.dao.NewRequestqueryInterface;
import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.FriendMasterImpl;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.query.NewRequestquery;
import com.reqman.util.Dateconverter;
import com.reqman.util.SessionUtils;
import com.reqman.vo.NewrequestVo;


@ManagedBean(name = "closerequest", eager = true)
@RequestScoped
@ViewScoped
public class closerequestbean implements Serializable{

	
	private static final long serialVersionUID = -5976944506230485077L;
	
	private NewrequestVo newrequestVo = new NewrequestVo();

	
	private NewrequestInterface newrequestInterface = new NewrequestImpl();
	private List<NewrequestVo> closerequestList = new ArrayList<NewrequestVo>();
	
	private List<NewrequestVo> filteredCloseRequestList = new ArrayList<NewrequestVo>();

	
	
	
	@PostConstruct
	public void init() {
		try {

			closerequestList = new ArrayList<NewrequestVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("--usersession--userName-->" + userName);			
			closerequestList = newrequestInterface.getColserequestDetails(userName.toLowerCase().trim());			
			setFilteredCloseRequestList(closerequestList);
			

		} catch (Exception e) {
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
	
	


	public NewrequestVo getNewrequestVo() {
		return newrequestVo;
	}




	public void setNewrequestVo(NewrequestVo newrequestVo) {
		this.newrequestVo = newrequestVo;
	}




	public List<NewrequestVo> getCloserequestList() {
		return closerequestList;
	}




	public void setCloserequestList(List<NewrequestVo> closerequestList) {
		this.closerequestList = closerequestList;
	}




	public List<NewrequestVo> getFilteredCloseRequestList() {
		return filteredCloseRequestList;
	}




	public void setFilteredCloseRequestList(
			List<NewrequestVo> filteredCloseRequestList) {
		this.filteredCloseRequestList = filteredCloseRequestList;
	}

	
	
	
}
