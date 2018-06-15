package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.reqman.dao.CategoryMasterInterface;
import com.reqman.dao.SchedulejobInterface;
import com.reqman.daoimpl.CategoryMasterImpl;
import com.reqman.daoimpl.SchedulejobImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.AccountVo;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.SchedulejobVo;


@ManagedBean(name="schedulejob",eager = true)
@RequestScoped
public class schedulejobbean implements Serializable{

	
	private static final long serialVersionUID = 125366988194130635L;
	private Integer schedulejobid;
	private String jobname;
	private String description;
	private String day;
	private Integer hour;
	private Integer minute;
	private Boolean status;	
	private SchedulejobVo  schedulejobVo = new SchedulejobVo ();
	private SchedulejobInterface  schedulejobInterface = new  SchedulejobImpl();	
	 private List<SchedulejobVo> schedulejobList = new ArrayList<SchedulejobVo>();	
	 private List<SchedulejobVo> filteredschedulejobList = new ArrayList<SchedulejobVo>();
	private Integer date;
	
	
	
	 
	 @PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--Account-->");
				schedulejobList = new  ArrayList<SchedulejobVo>();			
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				schedulejobList = schedulejobInterface.getschedulejobDetails(userName.toLowerCase().trim());
				
				setFilteredschedulejobList(schedulejobList);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}	 
	 
	 
	 

	
	
	
	public String save()
	{
		int result = 0;
		UserSession usersession = new UserSession();
		try
		{
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			System.out.println("--usersession--userName-->"+userName);
			result = schedulejobInterface.saveschedulejob(jobname, status, userName.toLowerCase().trim(),day,date,hour,minute,description);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This Schedulejob allready exists in your list. ",
								"If inactive you can active the Schedulejob status from the list shown below."));
				//return "category";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This Schedulejob allready exists in your list. ",
								"If inactive you can active the Schedulejob status from the list shown below."));
			//	return "category";
			}
			if(result == 3)
			{
				
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"New Schedulejob added successfully.",""));
			}
			
			schedulejobList = schedulejobInterface.getschedulejobDetails(userName.toLowerCase().trim());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),
							""));
			return "schedulejob";
		}
		return "home";
	}
	
	
	
	
	
	
	
	
	 
	 
	 
	 
	
	public void modifyAction() {
		try {
			System.out.println("modify action" + schedulejobid);			
			setSchedulejobid(schedulejobid);
			schedulejobVo = schedulejobInterface.getscheduleById(schedulejobid);
			
			setJobname(schedulejobVo.getJobname());
			setDescription(schedulejobVo.getDescription());
			setDay(schedulejobVo.getDay());
			setHour(schedulejobVo.getHour());
			setMinute(schedulejobVo.getMinute());
			setDate(schedulejobVo.getDate());

			FacesContext.getCurrentInstance().getExternalContext()
					.dispatch("modifyschedulejob.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	public String updateSchedulejob() {
		int result = 0;
		try {
			
			System.out.println("--updateRequesr requestid-" + schedulejobid);
			HttpSession session = SessionUtils.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("--usersession--userName-->" + userName);
			
			result = schedulejobInterface.updatescheduleById(schedulejobid, status,
					description, jobname, day, hour, minute, date, userName.toLowerCase().trim());

			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the Schedulejob",
								"Problem while modifying the Schedulejob"));
				return "modifyschedulejob.xhtml";
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the Schedulejob",
							""));
			return "modifyschedulejob.xhtml";
		}
		return "home";
	}

	
	
	
	
	
	
	
	
	public Integer getSchedulejobid() {
		return schedulejobid;
	}
	public void setSchedulejobid(Integer schedulejobid) {
		this.schedulejobid = schedulejobid;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	

	public Integer getHour() {
		return hour;
	}







	public void setHour(Integer hour) {
		this.hour = hour;
	}







	public Integer getMinute() {
		return minute;
	}







	public void setMinute(Integer minute) {
		this.minute = minute;
	}







	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}

	public SchedulejobVo getSchedulejobVo() {
		return schedulejobVo;
	}

	public void setSchedulejobVo(SchedulejobVo schedulejobVo) {
		this.schedulejobVo = schedulejobVo;
	}

	public List<SchedulejobVo> getSchedulejobList() {
		return schedulejobList;
	}

	public void setSchedulejobList(List<SchedulejobVo> schedulejobList) {
		this.schedulejobList = schedulejobList;
	}

	public List<SchedulejobVo> getFilteredschedulejobList() {
		return filteredschedulejobList;
	}



	public void setFilteredschedulejobList(
			List<SchedulejobVo> filteredschedulejobList) {
		this.filteredschedulejobList = filteredschedulejobList;
	}







	public Integer getDate() {
		return date;
	}







	public void setDate(Integer date) {
		this.date = date;
	}
	
	
	
}
