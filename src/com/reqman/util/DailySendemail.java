package com.reqman.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.reqman.dao.NewrequestInterface;
import com.reqman.dao.UpdatestatusInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.UpdatestatusImpl;
import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.vo.MonthlysummeryemailVo;
import com.reqman.vo.dailyDuedatewisesendRequestVo;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class DailySendemail {
	
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
	
	@SuppressWarnings("null")
	public String dailyGridContent(String To, String emailid) throws Exception, IOException {
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp = "\"";
		String link = MAIL_REGISTRATION_SITE_LINK;

		NewrequestInterface newrequestInterface = new NewrequestImpl();
		UpdatestatusInterface updatestatusInterface = new UpdatestatusImpl();
		List<dailyDuedatewisesendRequestVo> dailyDuedatewisesendVo = new ArrayList<dailyDuedatewisesendRequestVo>();
		List<dailyDuedatewisesendRequestVo> dailyDuedatewiseonteammembersendVo = new ArrayList<dailyDuedatewisesendRequestVo>();

		try {

			dailyDuedatewisesendVo = newrequestInterface.getduedatesendrequest(emailid.toLowerCase().trim());
			dailyDuedatewiseonteammembersendVo = updatestatusInterface
					.getduedatesendrequestonteammember(emailid.toLowerCase().trim());

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

			sb.append(
					"table {font-family:Trebuchet MS, Arial, Helvetica, sans-serif;border-collapse: collapse; width: 100%;}");
			sb.append("table td, table th {border: 1px solid #ddd;padding: 8px;}");
			sb.append(" tr:hover {background-color:#d3d3d3;}");
			sb.append(
					"table th {padding-top: 2px;padding-bottom: 5px;text-align: left;background-color:#f36c00; color: white;}");
			sb.append("</style></head>");

			sb.append("<body>");
			sb.append("<table>");
			sb.append("<div>");
			sb.append("<h1  style=\"color:#fff;font-size:32px;font-weight: bold;padding-left:15px;\">");
			sb.append("Collabor8");
			sb.append("</h1>");
			sb.append("<br></br>");
			sb.append("<h3 style=\"padding-left:40px;  text-decoration: none;color:black;font-size:16px; \">");
			sb.append("Dear " + emailid);
			sb.append("</h3>");
			sb.append(
					"<p style=\"padding-left:40px;padding-right:40px;text-decoration:none;color:black;font-size:16px; \">");
			sb.append("Following request are due to be completed after 2 days.");
			sb.append("<br></br>");
			sb.append("This information is being shared with both the requester as well as the team member.");
			sb.append("You are reminded to take action required,if any.");
			sb.append("<br></br>");
			sb.append("<br></br>");
			sb.append("Please");
			sb.append("<a href=\"" + link + "\">  click here </a>");
			sb.append("to use Collabor8.");
			sb.append("</p>");
			sb.append("<div style=\"color:black;background-color:#fff; font-size:16px; padding:30px;\">");
			sb.append("<center>");
			sb.append("<h2>");
			sb.append("Requests initiated by you:-");
			sb.append("</h2>");
			sb.append("</center>");
			sb.append("<table>");
			sb.append("<tbody>");
			sb.append("<tr>");
			sb.append("<th>");
			sb.append("Title");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Team Member");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("%Age Completion");
			sb.append("</th>");
			sb.append("</tr>");

			if (dailyDuedatewisesendVo != null && dailyDuedatewisesendVo.size() != 0) {
				for (dailyDuedatewisesendRequestVo dailyDuedatewisesendRequestVoDB : dailyDuedatewisesendVo) {

					sb.append("<tr>");
					sb.append("<td>");
					sb.append(dailyDuedatewisesendRequestVoDB.getTitle());
					sb.append("</td>");
					sb.append(dailyDuedatewisesendRequestVoDB.getName());
					sb.append("</td>");
					sb.append(dailyDuedatewisesendRequestVoDB.getCompletionpercentage());
					sb.append("</td>");
					sb.append("</tr>");
				}

			} else {
				sb.append("<tr>");
				sb.append("<td>");
				sb.append("At present, you have no records in this section:");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("--");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("--");
				sb.append("</td>");
				sb.append("</tr>");
			}
			sb.append("</tbody>");
			sb.append("</table>");

			sb.append("<br></br>");
			sb.append("<br></br>");

			sb.append("<center>");
			sb.append("<h2>");
			sb.append("Requests by other team members on you:-");
			sb.append("</h2>");
			sb.append("</center>");
			sb.append("<table>");
			sb.append("<tbody>");
			sb.append("<tr>");
			sb.append("<th>");
			sb.append("Title");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("Team Member");
			sb.append("</th>");
			sb.append("<th>");
			sb.append("%Age Completion");
			sb.append("</th>");
			sb.append("</tr>");

			if (dailyDuedatewiseonteammembersendVo != null && dailyDuedatewiseonteammembersendVo.size() != 0) {
				for (dailyDuedatewisesendRequestVo dailyDuedatewisesendRequestVoDB : dailyDuedatewiseonteammembersendVo) {

					sb.append("<tr>");
					sb.append("<td>");
					sb.append(dailyDuedatewisesendRequestVoDB.getTitle());
					sb.append("</td>");
					sb.append(dailyDuedatewisesendRequestVoDB.getName());
					sb.append("</td>");
					sb.append(dailyDuedatewisesendRequestVoDB.getCompletionpercentage());
					sb.append("</td>");
					sb.append("</tr>");
				}
			} else {
				sb.append("<tr>");
				sb.append("<td>");
				sb.append("At present, you have no records in this section:");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("--");
				sb.append("</td>");
				sb.append("<td>");
				sb.append("--");
				sb.append("</td>");
				sb.append("</tr>");
			}

			sb.append("</tbody>");
			sb.append("</table>");

			sb.append("</div>");

			sb.append("<br></br>");
			sb.append("<br></br>");
			sb.append("<h4 style=\"background-color:#f36c00;color:#fff;font-size:16px;padding-left:15px; \">");
			sb.append("Collabor8 Team");
			sb.append("</h4>");
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

	public void sendmaildaily(String To, String emailid) throws Exception {

		StringBuffer sb = new StringBuffer();
		try {

			Email from = new Email(SearchConstants.FROM_ADD);
			String subject = "Collabor8 Status Report" ;
			Email to = new Email(emailid);
			Content content = new Content("text/html", dailyGridContent(To, emailid));
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

