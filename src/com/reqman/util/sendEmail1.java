package com.reqman.util;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class sendEmail1 { 
	
	public String createPasswordContent(String To, String firstName, String password){
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		try{
			sb.append("Welcome to Collabor8,"+ firstName + "!");
			sb.append("<br>");
			sb.append("Your account has been created! You can find the members' login URL as well as your account username and password below in this e-mail. To keep your account safe,");
			sb.append("<br>");
			sb.append("we recommend changing your password by going to "+temp+"Account"+temp+" after logging in. ");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("If you encounter any problems logging in to your account or require any technical help using Collabor8, contact us at support@Collabor8.com and we'll be in");
			sb.append("touch to assist you as soon as we can. For subscription, billing or training matters, reach out to Venkata@Collabor8.com for assistance.");
			sb.append("<br>");
			sb.append("Login URL");
			sb.append("<br>");
			sb.append("http://updatecollabor8.herokuapp.com");
			sb.append("<br>");
			sb.append("Username");
			sb.append("<br>");
			sb.append(To);
			sb.append("<br>");
			sb.append("Password");
			sb.append("<br>");
			sb.append(password);
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Thank You, "+firstName+".");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Venkata Konidala");
			sb.append("<br>");
			sb.append("reqman.com");
			sb.append("<br>");
			sb.append("© reqman.com | support@Collabor8.com");
			
			content = sb.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return content;
	}
	
	
	
	public String createAccount(String To, String firstName) throws Exception {
		
		String password = "";
		StringBuffer sb = new StringBuffer();
		try{
			password = RandomPasswordGenerator.getPassword();
			   //SendGrid sg = new SendGrid("SXoOwlD1RJ2kbfiCfYuR4A");
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject = firstName+" Welcome to Collabor8!";
		    Email to = new Email(To);
		    //Content content = getContent(To, firstName, password);
		    Content content = new Content("text/html", createPasswordContent(To, firstName, password));
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
			password = "";
			throw new Exception(e);
		}
		
		return password;
    }  
	
	
	
	public String resetPasswordContent(String To, String password, String userName){
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		try{
			sb.append("Hey "+ userName + ",");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Someone has requested a password reset for the following account:");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("http://Req Man.com/members");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Username: "+userName);
			sb.append("<br>");
			sb.append("<br>");
			sb.append("If this was a mistake, just ignore this email and nothing will happen.");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Here’s your logins again");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Login here https://Req Man.com/members");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Username - "+To);
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Password - "+password);
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Any issue’s, please email support@Req Man.com");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Talk Soon");
			sb.append("<br>");
			sb.append("<br>");
			sb.append("Venkata");
			sb.append("<br>");
			sb.append("This is for reset password");
			
			content = sb.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return content;
	}

	
	
	
	public String resetAccount(String To, String userName) throws Exception {
		
		String password = "";
		try{
			password = RandomPasswordGenerator.getPassword();
			   //SendGrid sg = new SendGrid("SXoOwlD1RJ2kbfiCfYuR4A");
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject = "Password Reset Request";
		    Email to = new Email(To);
		    Content content = new Content("text/html", resetPasswordContent(To, password, userName));
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
			password = "";
			throw new Exception(e);
		}
		
		return password;
    }  
	
	

	public static void main(String[] args) throws IOException 
	{
		try{
		////String password = new sendEmail1().createAccount("hemantraghav012@gmail.com", "Sumit");
		//System.out.println("-password--"+password);
		
		/*String password1 = new sendEmail1().resetAccount("naveen.namburu@gmail.com", "Naveen Namburu");
		System.out.println("-password1--"+password1);*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


}


