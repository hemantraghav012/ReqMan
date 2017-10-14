package com.reqman.util;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class sendEmailonfriend {
	public String addnewfriend(String To, String firstName,  String friendfirstName){
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		try{
			
			 
			sb.append("Dear  "+ firstName + "!");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Welcome to Collabo8.");
			sb.append("<br>");			
			sb.append("Your team member " + friendfirstName + " has invited you to use this time saving productive tool  This tool will help  save lots of time, not just for you but also your other team members.");
			sb.append("<br>");			
			sb.append("Its user friendly features will enable raising new request very quickly. You can create your own  request categories, types and project.");
			sb.append("This master information can be used to categorize requests, monitor the progress and analyze data.");			
			sb.append("<br>");		
			sb.append("<br>");			
			sb.append("Your team members will find its very essay to update  progress of the tasks. User friendly dashboard and and data export features will help you understand friends and trigger improvement. Automatic notification and reminders will help you and team member to save follow-up time.");
			sb.append("<br>");
			sb.append("<br>");			
			sb.append("You can watch video and linked below to see demo. You are welcome to share improvement suggestions.");
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
	
public void friendemail(String To, String firstName,String friendfirstName) throws Exception {
		
	
		StringBuffer sb = new StringBuffer();
		try{
			
			   //SendGrid sg = new SendGrid("SXoOwlD1RJ2kbfiCfYuR4A");
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject = firstName+" Welcome to Collabor8!";
		    Email to = new Email(To);
		    //Content content = getContent(To, firstName, password);
		    Content content = new Content("text/html", addnewfriend(To, firstName,friendfirstName));
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
