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
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.chart.PieChartModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.reqman.dao.FriendMasterInterface;
import com.reqman.daoimpl.FriendMasterImpl;
import com.reqman.pojo.Category;
import com.reqman.pojo.Users;
//import com.reqman.vo.EmailUtility;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.util.sendEmail1;
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
	private  List<FriendVo> friendList1 = new ArrayList<FriendVo>();
	private  List<FriendVo> friendList2 = new ArrayList<FriendVo>();
	private String frienduser;
    private Boolean status;	
	private String userid;
	private String friendfirstname;
	private String friendlastname;
	private String friendshortname;
	private String friendId;
	private String password;
	private String hashkey;
	private FriendVo selectedFriend;
	  private PieChartModel piechart;
	
	
	
	 
	
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
			friendList1 = friendMasterInterface.getUsersStatus(userName);
			friendList2 = friendMasterInterface.getUsersfasleStatus(userName);
			 createPieModels();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	 private void createPieModels() {
	        piechart = new PieChartModel();
	     	        
	    int random1 = friendList2.size(); // Method to get data from db
	    int random2 = friendList1.size();  // Method to get data from db

	    piechart.getData().put("false status", random1);
	    piechart.getData().put("true Status", random2);	  
	    piechart.setTitle("Friend Status");
	    piechart.setLegendPosition("ne");
	    //piechart.setSeriesColors("green,red");
	   // piechart.setFill(false);
	    piechart.setShowDataLabels(true);
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
			
				result = friendMasterInterface.savefriend(frienduser,status, friendfirstname, friendlastname,password, friendshortname,userName, hashkey );
			
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
	
	
	
	

	 public void onCellEdit(CellEditEvent event) {
	     int result = 0;
	     String oldValue = "";
	     String newValue = "";
	     Integer updatefriendId = 0;
		 try
		 {
			 oldValue = (String)event.getOldValue();
		     newValue = (String)event.getNewValue();
		     updatefriendId = (Integer) event.getComponent().getAttributes().get("updateFriendId");
	         System.out.println("updatefriendId"+updatefriendId);
	        if(newValue != null && !newValue.equals(oldValue)) 
	        {
	        	result = friendMasterInterface.updateFriend(oldValue, newValue, updatefriendId);
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error", e.getMessage().toString());
	            FacesContext.getCurrentInstance().addMessage(null, msg);
		 }
	       
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


	public List<FriendVo> getFriendList1() {
		return friendList1;
	}


	public void setFriendList1(List<FriendVo> friendList1) {
		this.friendList1 = friendList1;
	}


	public FriendVo getSelectedFriend() {
		return selectedFriend;
	}


	public void setSelectedFriend(FriendVo selectedFriend) {
		this.selectedFriend = selectedFriend;
	}


	public PieChartModel getPiechart() {
		return piechart;
	}


	public void setPiechart(PieChartModel piechart) {
		this.piechart = piechart;
	}


	public List<FriendVo> getFriendList2() {
		return friendList2;
	}


	public void setFriendList2(List<FriendVo> friendList2) {
		this.friendList2 = friendList2;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getHashkey() {
		return hashkey;
	}


	public void setHashkey(String hashkey) {
		this.hashkey = hashkey;
	}


	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
