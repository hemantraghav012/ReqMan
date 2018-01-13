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
				sb.append("<h1>");
				sb.append(emailid +", Welcome to Collabor8 !");		
				sb.append("</h1>");	
				sb.append("<br>");
				sb.append("Your account has been created! You can Add the members' login URL as well as your account username and password below in this e-mail.To keep your account safe,");
				sb.append("<br>");
				sb.append("Usage is very simple . Enter one or more team members email id and start logging requests for them.");
				sb.append("<br>");
				sb.append("Optionally use request categories , projects and types for better analysis and management. Get more information on Collabor8.com Feel free to writte to us at helpdesk@collabor8.com ");
				sb.append("<h2>");
				sb.append("  Thank you for registration. Your mail ("+To+") is under verification<br/>");
				sb.append("</h2>");
				sb.append("<h1>");
			    sb.append("  Please click <a href=\""+link+"\">here</a> or open below link in browser<br/>");
			    sb.append("</h1>");			
				sb.append("Thank You, "+emailid+".");
				sb.append("<br>");
				sb.append("support@Collabor8.com");
				
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
