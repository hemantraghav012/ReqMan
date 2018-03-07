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
	public static String Click_here;
	
	public String addnewfriend(String To, String emailid,  String friendemailid){
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp ="\"";
		String here="http://localhost:8080/ReqMan/faces/login.xhtml";
		 Click_here= here;
		 
		
		try{
			
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<html><head><style type='text/css'>");
			sb.append("span {color:red;}");	
			sb.append("h3 {letter-spacing:1px;text-decoration:none;font-size:14px;}");
			sb.append("div {letter-spacing:1px;text-decoration:none;font-size:14px;max-width:550px; color:black; width: 100% !important;  background-color: #fff;  margin: 0 auto;   overflow: hidden;  font-family: roboto; }");
			sb.append("table {border-style:ridge;padding: 40px;border-color: skyblue; border-radius: 8px;}");
			sb.append("ul{text-align: left}");
			sb.append("ul li {text-align: left}");
			sb.append("p {letter-spacing:1px;font-size:16px;}");
			sb.append("</style></head>");			
			sb.append("<body>");						
			sb.append("<table>");
			sb.append("<div>");
			sb.append("<h1>");
			sb.append("Join the team at ");
			sb.append("<span>");
			sb.append("Collabor8!");
			sb.append("</span>");
			
			sb.append("</h1>");
			sb.append("<hr></hr>");	
			sb.append("<h3>");
			sb.append("Dear  "+ emailid + "!");	
			sb.append("</h3>");
			sb.append("<br></br>");
			sb.append("Welcome to Collabo8.");
			sb.append("<br></br>");
			sb.append("<p>");
			sb.append(" Your team member," + friendemailid +", has invited you to use Collabor8. ");				
			sb.append("This web based tool will help improve outcomes while saving lots of time, not just for you but also your other team members.");	
			sb.append("</p>");	
			sb.append("<br></br>");				
			sb.append(" Benefits will include :-");									
			sb.append("<ul>");				
			sb.append("<li>");
			sb.append("Saving time for all team members , Approx. 30-60 minutes every day!");
			sb.append("</li>");
			sb.append("<li>");
			sb.append("Improving accountability and role clarity.");
			sb.append("</li>");
			sb.append("<li>");
			sb.append("Improving team collaboration,");
			sb.append("</li>");
			sb.append("<li>");
			sb.append("Measuring and enhancing performance ,");
			sb.append("</li>");
			
			sb.append("<li>");
			sb.append("Better resource management.");
			sb.append("</li>");
			sb.append("<li>");
			sb.append("Good record of work done for better performance appraisals.");
			sb.append("</li>");
			
			sb.append("<li>");
			sb.append("No task is forgotten!");
			sb.append("</li>");			
			sb.append("</ul>");
			
			sb.append("<br></br>");
			sb.append("Please click");
		    sb.append("<a href=\"" + Click_here + "\">link here.</a>");	
			sb.append("to start using collabor8.");
			sb.append("<br></br>");
			sb.append("<br></br>");
			sb.append("Its user friendly features include:-");	
			sb.append("<ul>");				
			sb.append("<li>");
			sb.append(" Raise Requests on team members.");
			sb.append("</li>");
			sb.append("<li>");
			sb.append(" Agree on details like due date and accepting the request.");
			sb.append("</li>");
			sb.append("<li>");
			sb.append("Share notes , risks ,  documents and progress among team members.");
			sb.append("</li>");
			sb.append("<li>");
			sb.append("Filter information on any parameter.");
			sb.append("</li>");
			
			sb.append("<li>");
			sb.append("Export information for further analysis.");
			sb.append("</li>");
			sb.append("<li>");
			sb.append(" Automate Alerts  for approaching or past deadlines.");
			sb.append("</li>");
			
			sb.append("<li>");
			sb.append("Analyze performance including estimation accuracy.");
			sb.append("</li>");	
			sb.append("<li>");
			sb.append(" Classifying & Prioritizing tasks.");
			sb.append("</li>");	
			sb.append("<li>");
			sb.append("Estimation of time & efforts and comparison with actuals for improving planning capabilities.");
			sb.append("</li>");	
			sb.append("<li>");
			sb.append("Ratings and feedbacks for completed tasks and their analysis.");
			sb.append("</li>");	
			sb.append("<li>");
			sb.append("Submit Improvement ideas.");
			sb.append("</li>");	
			sb.append("</ul>");
			
			
			
			sb.append("Wishing you happy collabor8ing!");
			sb.append("<br></br>");
			sb.append("<br></br>");
			sb.append("Collabor8 Team");
			sb.append("<br></br>");
			sb.append("<br></br>");
					
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
	
public void friendemail(String To, String emailid,String friendemailid) throws Exception {
		
	
		StringBuffer sb = new StringBuffer();
		try{
			
			   //SendGrid sg = new SendGrid("SXoOwlD1RJ2kbfiCfYuR4A");
		    Email from = new Email(SearchConstants.FROM_ADD);
		    String subject = emailid+" Welcome to Collabor8!";
		    Email to = new Email(To);
		    //Content content = getContent(To, firstName, password);
		    Content content = new Content("text/html", addnewfriend(To, emailid,friendemailid));
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
