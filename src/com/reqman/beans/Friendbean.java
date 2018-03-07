package com.reqman.beans;

import java.io.IOException;
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
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Users;
//import com.reqman.vo.EmailUtility;
import com.reqman.util.SessionUtils;
import com.reqman.util.UserSession;
import com.reqman.util.sendEmail1;
import com.reqman.vo.FriendVo;
import com.reqman.vo.ProjectVo;
import com.reqman.vo.UserVo;



@ManagedBean(name="friendbean",eager = true)
@ViewScoped
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
	private  List<UserVo> getfriendList ;
	
	 
	
	


	@PostConstruct
    public void init() {
		try
		{
			friendList = new ArrayList<FriendVo>();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
			friendList = friendMasterInterface.getUsersDetails(userName.toLowerCase().trim());
			setFilteredFriendList(friendList);
			friendList1 = friendMasterInterface.getUsersStatus(userName.toLowerCase().trim());
			friendList2 = friendMasterInterface.getUsersfasleStatus(userName.toLowerCase().trim());
			 createPieModels();
			  getfriendList = friendMasterInterface.AllUsers(userName.toLowerCase().trim());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	 public List<UserVo> getUsers() throws Exception  
     {   
		 HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName); 
        getfriendList = friendMasterInterface.AllUsers(userName.toLowerCase().trim());        
       
         
         return   getfriendList;  
     }  
	
	
	
	
	 private void createPieModels() {
	        piechart = new PieChartModel();
	     	        
	    int random1 = friendList2.size(); // Method to get data from db
	    int random2 = friendList1.size();  // Method to get data from db
	    
	    piechart.getData().put("Active", random2);	
	    piechart.getData().put("In-Active", random1);
	  
	    piechart.setTitle("Friend Status");
	    piechart.setLegendPosition("ne");
	   
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
			friendList = friendMasterInterface.getUsersDetails(userName.toLowerCase().trim());
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
		return "friend";
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
			
				result = friendMasterInterface.savefriend(frienduser,status, friendfirstname, friendlastname,password, friendshortname,userName.toLowerCase().trim(), hashkey );
			
			if(result == 1)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This team member allready exists in your list. ",
								"If inactive you can active the team member status from the list shown below."));
				//return "friend";
			}
			if(result == 2)
			{
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"This team member allready exists in your list. ",
								"If inactive you can active the team member status from the list shown below."));
				//return "friend";
			}
			if(result == 3)
			{
				
			
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Team member is added successfully.",""));
			}
			
			friendList = friendMasterInterface.getUsersDetails(userName.toLowerCase().trim());
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Server Error "+e.getMessage(),""));
			return "friend";
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
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "The status of the team member has been changed successfully.","Old status is " + oldValue + ", New status is " + newValue);
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



	public List<UserVo> getGetfriendList() {
		return getfriendList;
	}



	public void setGetfriendList(List<UserVo> getfriendList) {
		this.getfriendList = getfriendList;
	}




	
	
	
}
