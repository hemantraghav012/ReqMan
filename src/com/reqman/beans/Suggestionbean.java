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

import com.reqman.dao.CategoryMasterInterface;
import com.reqman.dao.SuggestionInterface;
import com.reqman.daoimpl.CategoryMasterImpl;
import com.reqman.daoimpl.SuggestionImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.SuggestionVo;




@ManagedBean(name="suggestionbean",eager = true)
@ViewScoped
@RequestScoped
public class Suggestionbean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279569817641243590L;
	
	private Integer id;
	private String message;
	private Boolean status;	
	private String messagetype;
	
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
			suggestionList = suggestionInterface.getsuggestionDetails(userName.toLowerCase().trim());
		
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public String save(){
		
		int result = 0;
		UserSession usersession = new UserSession();
		try
		{
			suggestionList = new ArrayList<SuggestionVo>();
			System.out.println("--address-->"+message);
			System.out.println("--status-->"+status);
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			System.out.println("--usersession--userName-->"+userName);
			result = suggestionInterface.savesuggestion(message, status,messagetype, userName.toLowerCase().trim());
			
			
			if(result == 3)
			{
				
				suggestionList = suggestionInterface.getsuggestionDetails(userName.toLowerCase().trim());
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Customer Input created  successfully.",
								""));
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
			return "suggestion";
		}
		
		
		
		return "suggestion";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public List<SuggestionVo> getSuggestionList() {
		return suggestionList;
	}

	public void setSuggestionList(List<SuggestionVo> suggestionList) {
		this.suggestionList = suggestionList;
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




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public SuggestionVo getSuggestionVo() {
		return suggestionVo;
	}




	public void setSuggestionVo(SuggestionVo suggestionVo) {
		this.suggestionVo = suggestionVo;
	}
	

	
}
