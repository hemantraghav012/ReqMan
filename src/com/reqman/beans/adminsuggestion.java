package com.reqman.beans;

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

import com.reqman.dao.SuggestionInterface;
import com.reqman.daoimpl.SuggestionImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.SuggestionVo;


@ManagedBean(name="adminsuggestionbean",eager = true)
@ViewScoped
@RequestScoped
public class adminsuggestion implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8378046303743756016L;
	
	
	private Integer id;
	private String message;
	private Boolean status;	
	private String messagetype;
	private String requeststatus;
	private String adminremarks;
	private String actionowner;
	
	
	private SuggestionVo suggestionVo = new SuggestionVo();
	private SuggestionInterface suggestionInterface =  new SuggestionImpl();
	private List<SuggestionVo> suggestionList = new ArrayList<SuggestionVo>();
	
	
	
	@PostConstruct
    public void init() 
	{
		try
		{
			suggestionList = new ArrayList<SuggestionVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			suggestionList = suggestionInterface.getallsuggestionDetails(userName.toLowerCase().trim());
		
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
public void modifyAction(){
		
		try{
		
			setId(id);
			System.out.print("id is 5-"+id);
			suggestionVo = suggestionInterface.getsuggestionById(id);
			
			if(suggestionVo != null){
				
			setRequeststatus(suggestionVo.getRequeststatus() != null ? suggestionVo.getRequeststatus() : "");
			setActionowner(suggestionVo.getActionowner() != null ? suggestionVo.getActionowner() : "");
			setAdminremarks(suggestionVo.getAdminremarks() !=null ? suggestionVo.getAdminremarks() : "");
			
			}
			
		FacesContext.getCurrentInstance().getExternalContext()
		.dispatch("modifysuggestion.xhtml");

} catch (Exception e) {
e.printStackTrace();
}
	
	}	
	
	public String updateSuggestion(){
		
		int result = 0;
		try {

			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("--usersession--userName-->" + userName);
			
			result = suggestionInterface.updateSuggestionById(id, requeststatus, actionowner, adminremarks);

			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Problem while modifying the Suggestion", ""));
				return "modifysuggestion.xhtml";
			}

			if (result == 1) {
				
				suggestionList = suggestionInterface.getallsuggestionDetails(userName.toLowerCase().trim());
				
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Suggestion updated  successfully.", ""));

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Problem while modifying the Suggestion", ""));
			return "modifysuggestion.xhtml";
		}
		return "adminsuggestion.xhtml";
	}
		
		
		
		
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}
	public SuggestionVo getSuggestionVo() {
		return suggestionVo;
	}
	public void setSuggestionVo(SuggestionVo suggestionVo) {
		this.suggestionVo = suggestionVo;
	}
	public List<SuggestionVo> getSuggestionList() {
		return suggestionList;
	}
	public void setSuggestionList(List<SuggestionVo> suggestionList) {
		this.suggestionList = suggestionList;
	}






	public String getRequeststatus() {
		return requeststatus;
	}





	public void setRequeststatus(String requeststatus) {
		this.requeststatus = requeststatus;
	}





	public String getAdminremarks() {
		return adminremarks;
	}





	public void setAdminremarks(String adminremarks) {
		this.adminremarks = adminremarks;
	}





	public String getActionowner() {
		return actionowner;
	}





	public void setActionowner(String actionowner) {
		this.actionowner = actionowner;
	}





	
	
	
	
	
	
	
	

}
