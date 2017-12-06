package com.reqman.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.vo.NewrequestVo;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class requestemail {

	
	@SuppressWarnings("null")
	public String RequestGrid(String To, String emailid,String title, String description) throws Exception{
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		
		
		try{
			sb.append("<html>");
			sb.append("<head>");
			
			  sb.append("<html><head><style type='text/css'>");
			  sb.append("table , th, td { border-width: 1px; border-style: solid; border-color: black; } ");
			  sb.append("tbody {background-color:yellow; } ");
			  sb.append("</style></head>");
			
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<h2>");
			sb.append("Request Grid");
			sb.append("</h2>");
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
			sb.append("Project");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Category");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Type");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Due Date");
			sb.append("</th>");
			sb.append("<th>");
			sb.append(" %Age Completion");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("stage");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Team Member");
			sb.append("</th>");
			sb.append("</tr>");
			
				
			//	System.out.println("title11--"+);
				//System.out.println("dec11--"+nr.getDescription());
			sb.append("<tr>");
			sb.append("<td>");
		sb.append(description);
			sb.append("</td>");
			sb.append("<td>");
		sb.append(title);
			sb.append("</td>");
			sb.append("<td>");
			sb.append("my Project");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("my Category");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("my Type");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("12-14-15");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("24%");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("in-progress");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("Raghav");
			sb.append("</td>");
			sb.append("</tr>");
			
			
		
			
			
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

public void friendemail(String To, String emailid,String title, String description) throws Exception {
		
	
		StringBuffer sb = new StringBuffer();
		try{
			
			   //SendGrid sg = new SendGrid("SXoOwlD1RJ2kbfiCfYuR4A");
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject = emailid+" Welcome to Collabor8!";
		    Email to = new Email(To);
		    //Content content = getContent(To, firstName, password);
		    Content content = new Content("text/html", RequestGrid(To, emailid,title, description));
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
//requestemail rr= new requestemail();
//	rr.friendemail("hemantraghav012@gmail.com", "Sumit12", emailid, emailid);
//	System.out.println("-password--"+emailid);
	
	/*String password1 = new sendEmail1().resetAccount("naveen.namburu@gmail.com", "Naveen Namburu");
	System.out.println("-password1--"+password1);*/
	}
	catch(Exception e){
		e.printStackTrace();
	}
}

}
