package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.daoimpl.FriendMasterImpl;
//import com.reqman.vo.EmailUtility;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.FriendVo;



@ManagedBean(name="friendbean",eager = true)
@RequestScoped
public class Friendbean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5614461673505707743L;
	
	
	
private  List<FriendVo> friendList = new ArrayList<FriendVo>();
	
	private FriendMasterInterface  friendMasterInterface = new FriendMasterImpl();
	
	private String frienduser;
    private Boolean status;	
	private String userid;
	private String friendfirstname;
	private String friendlastname;
	private String friendshortname;
	private String friendId;
	
	
	
	@PostConstruct
    public void init() {
		try
		{
			friendList = new ArrayList<FriendVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			friendList = friendMasterInterface.getUsersDetails(userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String friendPage()
	{
		try
		{
			friendList = new ArrayList<FriendVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			System.out.println("--usersession--userName-->"+userName);
			friendList = friendMasterInterface.getUsersDetails(userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "friend";
	}
	
	
	public String newFriend()
	{
		try
		{
			friendList = new ArrayList<FriendVo>();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "addfriend";
	}
	
	
	public String savefriend()
	{
		int result = 0;
		UserSession usersession = new UserSession();
		try
		{
			friendList = new ArrayList<FriendVo>();
			
			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			
		
			result = friendMasterInterface.savefriend(frienduser,status, friendfirstname, friendlastname, friendshortname,userName);
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"friend already exist",
								"friendt already exist"));
				return "addfriend";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"friend already exist and in active, please activate by using modify friend ",
								"friend already exist and in active, please activate by using modify friend"));
				return "addfriend";
			}
			if(result == 3)
			{
				
			friendList = friendMasterInterface.getUsersDetails(userName);
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"friend created  successfully.",
								"friend created  successfully."));
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
			return "addfriend";
		}
		return "friend";
	}
	
	
	
	
public void modifyAction() {
		
		FriendVo friendVo = new FriendVo();
        
        try{
        	System.out.println("modify action"+ friendId);
            //addMessage("Welcome to Primefaces!!");
        	setFriendId(friendId);
        	friendVo  = friendMasterInterface.getUserFriendById(friendId);
        	if(friendVo  != null && friendVo .getStatus().trim().equalsIgnoreCase("Active")){
        		setFrienduser(friendVo.getEmailid()  != null ? friendVo.getEmailid() : "");
        		setStatus(true);
        	}
        	else
        	{
        		setFrienduser(friendVo.getEmailid() != null ? friendVo.getEmailid() : "");
        		setStatus(false);
        	}
        	
        	FacesContext.getCurrentInstance()
            .getExternalContext().dispatch("modifyfriend.xhtml");

        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
	public String updateFriend(){
		int result = 0;
		try{
			System.out.println("--updatefriend-status-"+status);
			System.out.println("--updatefriend-projectId-"+friendId);
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			
        	result =friendMasterInterface.updateUserFriendById(friendId, status);
        	
        	if(result == 2){
        		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the friend",
								"Problem while modifying the friend"));
				return "modifyfriend.xhtml";
        	}
        	
        	if(result == 1){
        		friendList = friendMasterInterface.getUsersDetails(userName);
        	}
        	
        	
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Problem while modifying the Friend",
							"Problem while modifying the Friend"));
			return "modifyfriend.xhtml";
		}
		return "friend";
	}
	
	
	


	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	
	
	
	
	

	public String getFriendId() {
		return friendId;
	}


	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}


	public List<FriendVo> getFriendList() {
		return friendList;
	}


	public void setFriendList(List<FriendVo> friendList) {
		this.friendList = friendList;
	}


	public String getFrienduser() {
		return frienduser;
	}


	public void setFrienduser(String frienduser) {
		this.frienduser = frienduser;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}




	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getFriendfirstname() {
		return friendfirstname;
	}


	public void setFriendfirstname(String friendfirstname) {
		this.friendfirstname = friendfirstname;
	}


	public String getFriendlastname() {
		return friendlastname;
	}


	public void setFriendlastname(String friendlastname) {
		this.friendlastname = friendlastname;
	}


	public String getFriendshortname() {
		return friendshortname;
	}


	public void setFriendshortname(String friendshortname) {
		this.friendshortname = friendshortname;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
