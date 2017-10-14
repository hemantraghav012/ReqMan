package com.reqman.util;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class requestsendEmail {

	
	public String addnewrequest(String To, String friendfirstName, String firstName,String title){
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		try{
			sb.append("Dear  "+ friendfirstName + "!");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Welcome to Collabo8.");
			sb.append("<br>");			
			sb.append("Your team member "+temp+firstName+temp+" has2. has raised a request on you.");
			sb.append("<br>");			
			sb.append("Brief description is "+temp+title+temp+".");
			sb.append("Using Collabor8 you will be able to update the progress. The requestor will be able to see the progress in their dashboard.");			
			sb.append("<br>");		
			sb.append("<br>");			
			sb.append("Please have a good day.");
			sb.append("<br>");
			sb.append("<br>");			
			sb.append("Collabor8 Team.");
			sb.append("<br>");
			sb.append("<br>");			
			sb.append("<br>");
			sb.append("Collabor8.com");
			sb.append("<br>");
			sb.append("© Collabor8.com | support@collabor8.com");
			
			content = sb.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return content;
	}
	
public void requestemail(String To,String friendfirstName, String firstName, String title) throws Exception {
		
	
		StringBuffer sb = new StringBuffer();
		try{
			
			   //SendGrid sg = new SendGrid("SXoOwlD1RJ2kbfiCfYuR4A");
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject = firstName+" Welcome to Collabor8!";
		    Email to = new Email(To);
		    //Content content = getContent(To, firstName, password);
		    Content content = new Content("text/html", addnewrequest(To, friendfirstName, firstName,title));
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
	
	
	
}
