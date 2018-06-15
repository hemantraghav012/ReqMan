package com.reqman.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
	
	 private static String MAIL_REGISTRATION_SITE_LINK = "";
	 
	 
	static {
		Properties myResources = new Properties();
		InputStream propertiesStream;
		try {

			Thread currentThread = Thread.currentThread();
			ClassLoader contextClassLoader = currentThread.getContextClassLoader();
			propertiesStream = contextClassLoader.getResourceAsStream("ReqManConfig.properties");
			if (propertiesStream != null) {
				myResources.load(propertiesStream);
			} else {
				// Properties file not found!
			}
			if (propertiesStream != null) {
				myResources.load(propertiesStream);
				MAIL_REGISTRATION_SITE_LINK = myResources.getProperty("AppUrl3");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String newrequestContent(String To, String friendname, String requestorname, String title,
			String description, String duedate, String projectname, String categoryname, String typename,
			String priority, Integer weight, String estimatedeffort) {

		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp = "\"";
		String link = MAIL_REGISTRATION_SITE_LINK;

		try {
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<html><head><style type='text/css'>");
			sb.append("body{color:black;}");
			sb.append("span{color:black;font-weight:bold;}");
			sb.append(
					"div {letter-spacing:1px;text-decoration:none;font-size:16px; color:black;  background-color: #fff;  margin: 0px;}");
			sb.append("h1 {background-color:#f36c00;}");
			sb.append(
					"table {font-family:Trebuchet MS, Arial, Helvetica, sans-serif;border-collapse: collapse; width: 100%;color:black; border: 1px solid;padding:0px 20px 0px 20px;border-color: black; border-radius: 8px;}");
			sb.append("</style></head>");
			sb.append("<body>");
			sb.append("<table>");
			sb.append("<div>");

			sb.append("<h1  style=\"color:#fff;font-size:32px;font-weight: bold;padding-left:15px;\">");
			sb.append("Collabor8");
			sb.append("</h1>");

			sb.append("<br></br>");

			sb.append("<h3 style=\"padding-left:40px;  text-decoration: none;color:black;font-size:16px; \">");
			sb.append("Dear " + friendname);
			sb.append("</h3>");
			sb.append(
					"<p style=\"padding-left:40px;padding-right:40px;text-decoration:none;color:black;font-size:16px; \">");
			sb.append("Your team member " + requestorname + " has raised a new request for you. Please go to the ");
			sb.append("<a href=\"" + link + "\"> link here. </a>");
			sb.append(
					"and accept the request after confirming / filling completion date. The request can be returned to the requester with remarks.");
			sb.append("</p>");

			sb.append("<div style=\"color:black;background-color:#fff; font-size:16px; padding:30px;\">");

			sb.append("<span>");
			sb.append("Title:&nbsp;&nbsp;&nbsp;");
			sb.append("</span>");
			sb.append(title);

			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Description:&nbsp;&nbsp;&nbsp;");
			sb.append("</span>");
			sb.append(description);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Due Date:&nbsp;&nbsp;&nbsp;");
			sb.append("</span>");
			sb.append(duedate);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Category:&nbsp;&nbsp;&nbsp; ");
			sb.append("</span>");
			sb.append(categoryname);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Project:&nbsp;&nbsp;&nbsp; ");
			sb.append("</span>");
			sb.append(projectname);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Type:&nbsp;&nbsp;&nbsp; ");
			sb.append("</span>");
			sb.append(typename);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Team Member:&nbsp;&nbsp;&nbsp; ");
			sb.append("</span>");
			sb.append(requestorname);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Priority:&nbsp;&nbsp;&nbsp; ");
			sb.append("</span>");
			sb.append(priority);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Weight:&nbsp;&nbsp;&nbsp; ");
			sb.append("</span>");
			sb.append(weight);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("<span>");
			sb.append("Estimated Effort:&nbsp;&nbsp;&nbsp;");
			sb.append("</span>");
			sb.append(estimatedeffort);
			sb.append("<br></br>");
			sb.append("<hr></hr>");
			sb.append("<br></br>");

			sb.append("</div>");

			sb.append("<br></br>");
			sb.append("<br></br>");
			
			sb.append("</div>");
			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
			content = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
	
	public void createnewrequest(String To, String friendname, String requestorname, String title, String description,
			String duedate, String projectname, String categoryname, String typename, String priority, Integer weight,
			String estimatedeffort) throws Exception {

		StringBuffer sb = new StringBuffer();
		try {

			Email from = new Email(SearchConstants.FROM_ADD);
			String subject = " New Request From   " + requestorname + ",   -   " + title;
			Email to = new Email(To);
			Content content = new Content("text/html", newrequestContent(To, friendname, requestorname, title,
					description, duedate, projectname, categoryname, typename, priority, weight, estimatedeffort));
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
		} catch (Exception e) {

			throw new Exception(e);
		}
	}
}


