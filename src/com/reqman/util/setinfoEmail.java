package com.reqman.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class setinfoEmail {

	  private static String MAIL_REGISTRATION_SITE_LINK = "";		
	  private static char[] symbols = null;	  
	  private static Random random = new Random();
	  
	  static {
			Properties myResources = new Properties();
			InputStream propertiesStream;
			try {
				
			    StringBuilder tmp = new StringBuilder();
			    for (char ch = '0'; ch <= '9'; ++ch)
			      tmp.append(ch);
			    for (char ch = 'a'; ch <= 'z'; ++ch)
			      tmp.append(ch);
			    symbols = tmp.toString().toCharArray();

			    Thread currentThread = Thread.currentThread();
			    ClassLoader contextClassLoader = currentThread.getContextClassLoader();
			    propertiesStream = contextClassLoader.getResourceAsStream("ReqManConfig.properties");
			    if (propertiesStream != null) {
			    	myResources.load(propertiesStream);
			    } else {
			      // Properties file not found!
			    }			
				if(propertiesStream != null){
					myResources.load(propertiesStream);
					MAIL_REGISTRATION_SITE_LINK = myResources.getProperty("AppUrl1");				
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			 catch (Throwable e) {
					e.printStackTrace();
				}
	  }
	  

	  public static String prepareRandomString(int len) {
		char[] buf = new char[len];
	    for (int idx = 0; idx < buf.length; ++idx) 
	      buf[idx] = symbols[random.nextInt(symbols.length)];
	    return new String(buf);
	  }
	
	  public String setinformationContent(String To, String emailid, String hashkey){
			StringBuffer sb = new StringBuffer();
			String content = "";
			String temp ="\"";
			String link = MAIL_REGISTRATION_SITE_LINK+"?emailid="+To+"&hash="+hashkey;
			
			try{
				sb.append("<h3>");
				sb.append(emailid +", Welcome to Collabor8 !");		
				sb.append("</h3>");	
				sb.append("<br></br>");	
				sb.append("Your account has been created! Your email-id is your user id and you can set your password by clicking the");				
				sb.append("<a href=\""+link+"\">link here.</a>");			
				sb.append("<br></br>");			
				sb.append("Collabor8 is simple to use.");				
				sb.append("<h5");
				sb.append("<br></br>");
				sb.append("<ul>");			
				sb.append("<li>");
				sb.append("Enter one or more team members email id.");
				sb.append("</li>");
				sb.append("<li>");
				sb.append("Attach documents to tasks and projects.");
				sb.append("</li>");
				sb.append("<li>");
				sb.append("Start raising requests on them.");
				sb.append("</li>");
				sb.append("<li>");
				sb.append("Optionally use request categories, projects and types for better analysis and management.");
				sb.append("</li>");
				sb.append("</ul>");
				sb.append("</h5>");
				sb.append("<br></br>");
				sb.append("Get more information on Collabor8.com. Feel free to write to us at helpdesk@collabor8.com");
				sb.append("<br></br>");
				sb.append("Happy Collabor8ing!");
				sb.append("<br></br>");
				sb.append("support@Collabor8.com");
				sb.append("<br></br>");
				
				content = sb.toString();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return content;
		}
		
		
		
		public String createAccount2(String To, String emailid) throws Exception {
			
			String hashkey = "";
				
			StringBuffer sb = new StringBuffer();
			try{
				 hashkey = prepareRandomString(30);				
			    Email from = new Email(SearchConstants.FROM_ADD);
			    String subject = emailid+" Welcome to Collabor8!";
			    Email to = new Email(To);			
			    Content content = new Content("text/html", setinformationContent(To, emailid, hashkey));			    
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
				hashkey= "";
				throw new Exception(e);
			}
			
			return hashkey;
	    }  
		
}
