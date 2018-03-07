package com.reqman.beans;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.reqman.dao.NewrequestInterface;
import com.reqman.dao.Publicdomaininterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.PublicdomainImpl;
import com.reqman.util.Filterpublicemaildomainname;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.PublicemaildomainVo;
import com.reqman.vo.UserVo;


@ManagedBean(name = "domainbean", eager = true)
@SessionScoped
public class Publicmaildomainbean implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3948935460907717988L;
	private UploadedFile excelfile;
    private String domainname;
    private boolean status;
	private Publicdomaininterface publicdomainInterface = new PublicdomainImpl();
	private PublicemaildomainVo publicemaildomainVo = new PublicemaildomainVo();	
	private StreamedContent file;
	private Filterpublicemaildomainname filterpublicemaildomain =new Filterpublicemaildomainname();
	 private List<PublicemaildomainVo> publicdonainList = new ArrayList<PublicemaildomainVo>();	
	
	
	
	
	
	
	@SuppressWarnings("static-access")
	public String submit()
	 {
		
			int result = 0;			
			try {
				
				publicdonainList=filterpublicemaildomain.readXLSXFile(excelfile);
				HttpSession session = SessionUtils.getSession();
				String userName = (String) session.getAttribute("username");
				
				if(publicdonainList !=null && publicdonainList.size() != 0){
				result = publicdomainInterface.save(publicdonainList,domainname,status,userName.toLowerCase().trim());
				}
				else{
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Domain List already exist",
									"Domain List already exist"));
					return "publicdomain";
				}
				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Domain List already exist",
									"Domain List already exist"));
					return "publicdomain";
				}
				if (result == 2) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											FacesMessage.SEVERITY_WARN,
											"Domain List already exist and in active, please activate by using modify Domain List ",
											"Domain List already exist and in active, please activate by using modify Domain List"));
					return "publicdomain";
				}
				
						FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Domain List created  successfully.",
									"Domain List created  successfully."));
						
				

			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Server Error " + e.getMessage(), "Server Error "
										+ e.getMessage()));
				return "publicdomain";
			}
			return "home";
		}
		

	
	
	
	
	 public StreamedContent fileDownloadView() { 
		 	System.out.println("hellodownload");
		 	InputStream stream = null;
		 	try{
		 		
				
				
	        	

	        	
		 	}
		 	catch(Exception e){
		 		e.printStackTrace();
		 	}
	        
	       return file;
	    }
	 
	
	
	
	
	 
	 
	 
	 
	public UploadedFile getExcelfile() {
		return excelfile;
	}

	public void setExcelfile(UploadedFile excelfile) {
		this.excelfile = excelfile;
	}







	public boolean isStatus() {
		return status;
	}







	public void setStatus(boolean status) {
		this.status = status;
	}







	public String getDomainname() {
		return domainname;
	}







	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}






	public PublicemaildomainVo getPublicemaildomainVo() {
		return publicemaildomainVo;
	}






	public void setPublicemaildomainVo(PublicemaildomainVo publicemaildomainVo) {
		this.publicemaildomainVo = publicemaildomainVo;
	}






	public StreamedContent getFile() {
		return file;
	}






	public void setFile(StreamedContent file) {
		this.file = file;
	}



	 
	 
	 
	 

}
