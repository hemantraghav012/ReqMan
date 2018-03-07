package com.reqman.beans;

import java.io.IOException;
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
import com.reqman.daoimpl.NewRequestquery;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.util.Dateconverter;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AdminRequestVo;
import com.reqman.vo.NewrequestVo;



@ManagedBean(name = "adminrequest", eager = true)
@RequestScoped
@ViewScoped
public class adminrequestbean implements Serializable {

	
	private static final long serialVersionUID = -8210271274398850935L;
	
	
	
	private AdminRequestVo adminrequestVo = new AdminRequestVo();

	private NewRequestqueryInterface newrequestqueryInterface = new NewRequestquery();
	private NewrequestInterface newrequestInterface = new NewrequestImpl();
	
	private List<AdminRequestVo> adminrequestList = new ArrayList<AdminRequestVo>();
	private List<AdminRequestVo> filteredRequestList = new ArrayList<AdminRequestVo>();
	
	
	
	
	@PostConstruct
	public void init() {
		try {

			adminrequestList = new ArrayList<AdminRequestVo>();			
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("--usersession--userName-->" + userName);					
			adminrequestList = newrequestInterface.getadminrequestDetails(userName.toLowerCase().trim());			
			setFilteredRequestList(adminrequestList);
			
			

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

		// for display error message
		public void addMessage(String summary) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					summary, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}


	
	
	
	
	
	
	
	
	public AdminRequestVo getAdminrequestVo() {
		return adminrequestVo;
	}
	public void setAdminrequestVo(AdminRequestVo adminrequestVo) {
		this.adminrequestVo = adminrequestVo;
	}
	public List<AdminRequestVo> getAdminrequestList() {
		return adminrequestList;
	}
	public void setAdminrequestList(List<AdminRequestVo> adminrequestList) {
		this.adminrequestList = adminrequestList;
	}
	public List<AdminRequestVo> getFilteredRequestList() {
		return filteredRequestList;
	}
	public void setFilteredRequestList(List<AdminRequestVo> filteredRequestList) {
		this.filteredRequestList = filteredRequestList;
	}
	
	
	
	
}
