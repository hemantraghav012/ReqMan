package com.reqman.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class newRequestsend {
	
	public String newrequestContent(String To,String friendname, String requestorname, String title, 
			String description,String duedate, String projectname, String categoryname,String typename, 
			String priority, Integer weight, String estimatedeffort){
		
		
		
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		String link="http://localhost:8080/ReqMan/faces/login.xhtml";
		
		try{	
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<html><head><style type='text/css'>");
			sb.append("body{color:black;}");						
			sb.append("div {letter-spacing:1px;text-decoration:none;font-size:14px; color:black;  background-color: #fff;  margin: 0px;}");
			sb.append("h1 {background-color:#f36c00;}");			
			sb.append("table {font-family:Trebuchet MS, Arial, Helvetica, sans-serif;border-collapse: collapse; width: 100%;color:black; border: 1px solid;padding:0px 20px 0px 20px;border-color: black; border-radius: 8px;}");
			sb.append("</style></head>");			
			sb.append("<body>");						
			sb.append("<table>");
			sb.append("<div>"); 
			
			sb.append("<h1  style=\"color:#fff;font-size:32px;font-weight: bold;padding-left:15px;\">"); 			
			sb.append("Collabor8");				
			sb.append("</h1>");
			
			sb.append("<br></br>");	
			
			sb.append("<h3 style=\"padding-left:40px;  text-decoration: none;color:black; \">");
			sb.append("Dear " +friendname);		
			sb.append("</h3>");			
			sb.append("<p style=\"padding-left:40px;padding-right:40px;text-decoration:none;color:black; \">");
			sb.append("Your team member "+requestorname+" has raised a new request for you. Please go to the ");				
			sb.append("<a href=\""+ link +"\"> link here. </a>");
			sb.append("and accept the request after confirming / filling completion date. The request can be returned to the requester with remarks.");
			sb.append("</p>");	
						

			sb.append("<div style=\"color:black;background-color:#fff; font-size:16px; padding:30px;\">");
			
			 sb.append("Title:&nbsp;&nbsp;&nbsp;");
			 sb.append(title);
			 
			 sb.append("<br></br>");
			 sb.append("<hr></hr>");	
			 sb.append("<br></br>");
				
			sb.append("Description:&nbsp;&nbsp;&nbsp;");
			   sb.append(description);
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			sb.append("Due Date:&nbsp;&nbsp;&nbsp; ");			
			sb.append(duedate);
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			
			sb.append("Category:&nbsp;&nbsp;&nbsp; ");
			sb.append(categoryname);
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			
			sb.append("Project:&nbsp;&nbsp;&nbsp; ");			
			sb.append(projectname);		
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			sb.append("Type:&nbsp;&nbsp;&nbsp; ");
			sb.append(typename);
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			sb.append("Team Member:&nbsp;&nbsp;&nbsp; ");			
			sb.append(requestorname);
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			sb.append("Priority:&nbsp;&nbsp;&nbsp; ");			
			sb.append(priority);		
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			
			sb.append("Weight:&nbsp;&nbsp;&nbsp; ");			
			sb.append(weight);		
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");
			
			sb.append("Estimated Effort:&nbsp;&nbsp;&nbsp;");			
			sb.append(estimatedeffort);	
			sb.append("<br></br>");
			sb.append("<hr></hr>");	
			sb.append("<br></br>");

			
			sb.append("</div>");
			
			sb.append("<br></br>");
			sb.append("<br></br>");
			sb.append("<h4 style=\"background-color:#f36c00;color:#fff; \">");
			sb.append("support@Collabor8.com");		
			sb.append("</h4>");
			sb.append("</div>");
			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
			content = sb.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return content;
	}
	
	
	
	public void createnewrequest(String To,String friendname, String requestorname, String title, 
			String description, String duedate, String projectname, String categoryname,String typename, 
			String priority, Integer weight, String estimatedeffort) throws Exception {
	
			
		StringBuffer sb = new StringBuffer();
		try{
					
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject =" New Request From   "+requestorname+",   -   "+title;
		    Email to = new Email(To);		   
		    Content content = new Content("text/html", newrequestContent(To,friendname, requestorname, title,  description, duedate, projectname,  categoryname, typename,	priority, weight, estimatedeffort));		   
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
	//	newRequestsend rr= new newRequestsend();
		
		//	rr.createnewrequest("hemantraghav012@gmail.com","kaizensystech@gmail.com", "title","description", "projectname","categoryname", "typename", "priority",null, emailid, 12, "estimatedeffort");
			
			//			System.out.println("-password--"+emailid);
			//System.out.println("requestemail--"+emailList);
			/*String password1 = new sendEmail1().resetAccount("naveen.namburu@gmail.com", "Naveen Namburu");
			System.out.println("-password1--"+password1);*/
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
			
		}


