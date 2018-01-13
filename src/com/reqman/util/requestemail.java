package com.reqman.util;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.reqman.dao.NewrequestInterface;
import com.reqman.dao.UpdatestatusInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.RequesttypeMasterImpl;
import com.reqman.daoimpl.UpdatestatusImpl;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.UpdatestatusVo;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;



public class requestemail {	
	
	
	public void useremailid() throws Exception{
		RequesttypeMasterImpl reinf = new RequesttypeMasterImpl();
		List<String> emailList=reinf.AllUser();
       System.out.println("requestemail--"+emailList );
	}
	
	
	@SuppressWarnings("null")
	public String RequestGrid(String To, String emailid) throws Exception, IOException{
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		List<NewrequestVo> requestListemail = new ArrayList<NewrequestVo>();
		List<UpdatestatusVo> updatestatusListemail = new ArrayList<UpdatestatusVo>();
		 NewrequestInterface newrequestInterface = new NewrequestImpl();	
         UpdatestatusInterface updatestatusInterface = new UpdatestatusImpl();
					
         String userName="";
		 ;	
		String userproject = null;
		String usercategory= null;
		String userrequesttype= null;
		String friendname= null;
		String changedate= null;
		String title=null;
		String description=null;
		Float completionpercentage = null;
		Integer stage = null;
		String createdby=null;
		try{
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<html><head><style type='text/css'>");
			sb.append("table {font-family:Trebuchet MS, Arial, Helvetica, sans-serif;border-collapse: collapse; width: 100%;}");
			sb.append("table td, table th {border: 1px solid #ddd;padding: 8px;}");
			sb.append("table tr:hover {background-color: #ddd;}");
			sb.append("table th {padding-top: 12px;padding-bottom: 12px;text-align: left;background-color:blue; color: white;}");
			sb.append("</style></head>");			
			sb.append("<body>");
			
			
			
			sb.append("<center>");
			sb.append("<h2>");
			sb.append("Tasks From  Requestor");
			sb.append("</h2>");
			sb.append("</center>");
			sb.append("<table>");
			sb.append("<tbody>");
			sb.append("<tr>");
			sb.append("<th>");
			sb.append("Title");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Description");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Due Date");			
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Category");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Project");			
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Type");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Task From");			
			sb.append("</th>");			
			sb.append("<th>");
			sb.append("%Age Completion");
			sb.append("</th>");
			sb.append("</tr>");
			
		
		
			updatestatusListemail = updatestatusInterface.getupdatestatusDetailsforemail(emailid,title, description,userproject, usercategory, userrequesttype, createdby, changedate,completionpercentage,stage);			
				
				for (UpdatestatusVo updatestatusDB : updatestatusListemail) {

			sb.append("<tr>");
			sb.append("<td>");
		    sb.append(updatestatusDB.getTitle());
			sb.append("</td>");
			sb.append("<td>");
		    sb.append(updatestatusDB.getDescription());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(updatestatusDB.getCompletiondate());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(updatestatusDB.getUsercategory());			
			sb.append("</td>");
			sb.append("<td>");
			sb.append(updatestatusDB.getUserproject());			
			sb.append("</td>");
			sb.append("<td>");
			sb.append(updatestatusDB.getUserrequesttype());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(updatestatusDB.getFriendName());
			sb.append("</td>");
					
			sb.append("<td>");
			sb.append(updatestatusDB.getCompletionpercentage());
			sb.append("</td>");			
			sb.append("</tr>");			
			}

			sb.append("</tbody>");
			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
				
			
			
				
				
				sb.append("<html>");
				sb.append("<head>");
				sb.append("<html><head><style type='text/css'>");
				sb.append("table {font-family:Trebuchet MS, Arial, Helvetica, sans-serif;border-collapse: collapse; width: 100%;}");
				sb.append("table td, table th {border: 1px solid #ddd;padding: 8px;}");
				sb.append("table tr:hover {background-color: #ddd;}");
				sb.append("table th {padding-top: 12px;padding-bottom: 12px;text-align: left;background-color:blue; color: white;}");
				sb.append("</style></head>");			
				sb.append("<body>");
				
			
			sb.append("<center>");
			sb.append("<h2>");
			sb.append("Pending Requests From Team Member");
			sb.append("</h2>");
			sb.append("</center>");
			sb.append("<table>");
			sb.append("<tbody>");
			sb.append("<tr>");
			sb.append("<th>");
			sb.append("Title");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Description");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Due Date");			
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Category");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Project");			
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Type");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Team Member");			
			sb.append("</th>");
			sb.append("<th>");
			sb.append("stage");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("%Age Completion");
			sb.append("</th>");
			sb.append("</tr>");
			
		
				requestListemail = newrequestInterface.getNewrequestDetailsforemail(emailid,title, description,userproject, usercategory, userrequesttype, friendname, changedate,completionpercentage,stage);			
				
				for (NewrequestVo requestDB : requestListemail) {

			sb.append("<tr>");
			sb.append("<td>");
		    sb.append(requestDB.getTitle());
			sb.append("</td>");
			sb.append("<td>");
		    sb.append(requestDB.getDescription());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(requestDB.getChangedate());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(requestDB.getUsercategory());			
			sb.append("</td>");
			sb.append("<td>");
			sb.append(requestDB.getUserproject());			
			sb.append("</td>");
			sb.append("<td>");
			sb.append(requestDB.getUserrequesttype());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(requestDB.getFriendName());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(requestDB.getStage());
			sb.append("</td>");			
			sb.append("<td>");
			sb.append(requestDB.getCompletionpercentage());
			sb.append("</td>");			
			sb.append("</tr>");			
			}
				
			
			sb.append("</tbody>");
			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
			
			sb.append("© Collabor8.com | emailid");
			sb.append("© Collabor8.com | support@collabor8.com");
			
			content = sb.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return content;
	}

public void friendemail(String To, String emailid) throws Exception {
		
	
		StringBuffer sb = new StringBuffer();
		try{
			
			   //SendGrid sg = new SendGrid("SXoOwlD1RJ2kbfiCfYuR4A");
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject = emailid+" Welcome to Collabor8!";
		    Email to = new Email(To);
		    //Content content = getContent(To, firstName, password);
		    Content content = new Content("text/html", RequestGrid(To,emailid));
		    //Content content = new Content("hello password");
		    Mail mail = new Mail(from, subject, to, content);
		    SendGrid sg = new SendGrid(SearchConstants.EMAIL_KEY);
		    Request request = new Request();
		    
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      System.out.println(response.getStatusCode());
		      System.out.println(response.getBody());
		      System.out.println(response.getHeaders());

		      
		}
		catch(Exception e){
			
			throw new Exception(e);
		}
		

    }  
public static void main(String[] args) throws IOException 
{
	try{
		String emailid = null;
requestemail rr= new requestemail();
//	rr.friendemail("hemantraghav012@gmail.com", "Sumit12");
//	System.out.println("-password--"+emailid);
	rr.useremailid();
	List<String> emailList = null;
	System.out.println("requestemail--"+emailList);
	/*String password1 = new sendEmail1().resetAccount("naveen.namburu@gmail.com", "Naveen Namburu");
	System.out.println("-password1--"+password1);*/
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
	
}
