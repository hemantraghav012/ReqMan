package com.reqman.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
import com.reqman.daoimpl.FriendMasterImpl;
import com.reqman.pojo.Users;
//import com.reqman.vo.EmailUtility;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.vo.FriendVo;
import com.reqman.vo.ProjectVo;



@ManagedBean(name="friendbean",eager = true)
@RequestScoped
public class Friendbean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5614461673505707743L;
	
	
	
private  List<FriendVo> friendList = new ArrayList<FriendVo>();
	
	private FriendMasterInterface  friendMasterInterface = new FriendMasterImpl();
	
	private  List<FriendVo> filteredFriendList = new ArrayList<FriendVo>();
	private String frienduser;
    private Boolean status;	
	private String userid;
	private String friendfirstname;
	private String friendlastname;
	private String friendshortname;
	private String friendId;
	private ProjectVo selectedFriend;
	
	private  List<Users> usersList ;
	
	
	
	
	
	
	
	/*public List<String> countryList() {  
		ArrayList<FriendVo> list =new ArrayList<FriendVo>();  
		
		
		 
		return list;  
		}  
	
	
	 public List < Users > getUsers() throws Exception  
     {   
     	 
         usersList = friendMasterInterface.AllUsers(); 
         
         return usersList;  
     }  */
	
	
	
	
	@PostConstruct
    public void init() {
		try
		{
			friendList = new ArrayList<FriendVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			friendList = friendMasterInterface.getUsersDetails(userName);
			setFilteredFriendList(friendList);
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
 
        
        pdf.addTitle("Collabor8");
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


	public List<FriendVo> getFilteredFriendList() {
		return filteredFriendList;
	}


	public void setFilteredFriendList(List<FriendVo> filteredFriendList) {
		this.filteredFriendList = filteredFriendList;
	}


	public ProjectVo getSelectedFriend() {
		return selectedFriend;
	}


	public void setSelectedFriend(ProjectVo selectedFriend) {
		this.selectedFriend = selectedFriend;
	}


	public List<Users> getUsersList() {
		return usersList;
	}


	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
