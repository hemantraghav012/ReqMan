package com.reqman.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.reqman.dao.NewrequestInterface;
import com.reqman.daoimpl.NewrequestImpl;
import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.vo.MonthlysummeryemailVo;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class Monthlyrequestsummaryforadmin {
	
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

	public void useremailid() throws Exception {
		GetRolequery reinf = new GetRolequery();
		List<String> emailList = reinf.AllAccountAdmin();
		System.out.println("requestemail--" + emailList);
	}
	
	@SuppressWarnings("null")
	public String monthlyGridContent(String To, String emailid) throws Exception, IOException {
		StringBuffer sb = new StringBuffer();
		String content = "";
		String temp = "\"";
		String link = MAIL_REGISTRATION_SITE_LINK;

		NewrequestInterface newrequestInterface = new NewrequestImpl();
		List<MonthlysummeryemailVo> monthlysummeryemailVo = new ArrayList<MonthlysummeryemailVo>();

		try {

			monthlysummeryemailVo = newrequestInterface.getAdminsummarybyrequester(emailid.toLowerCase().trim());

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
			sb.append("Dear " + emailid);
			sb.append("</h3>");
			sb.append(
					"<p style=\"padding-left:40px;padding-right:40px;text-decoration:none;color:black;font-size:16px; \">");
			sb.append("Summary all your Collabor8 transactions is shared below-");
			sb.append("<br></br>");
			sb.append("<br></br>");
			sb.append("Please");
			sb.append("<a href=\"" + link + "\">  click here </a>");
			sb.append("to use Collabor8.");
			sb.append("</p>");

			sb.append("<div style=\"color:black;background-color:#fff; font-size:16px; padding:30px;\">");

			for (MonthlysummeryemailVo monthlysummeryemailVoDB : monthlysummeryemailVo) {

				sb.append("<span>");
				sb.append("Total Number Of Users:&nbsp;&nbsp;&nbsp;");
				sb.append("</span>");
				sb.append(monthlysummeryemailVoDB.getCountuser());

				sb.append("<br></br>");
				sb.append("<hr></hr>");
				sb.append("<br></br>");

				sb.append("<span>");
				sb.append("Total Number Of Requests Raised By You:&nbsp;&nbsp;&nbsp;");
				sb.append("</span>");
				sb.append(monthlysummeryemailVoDB.getTotalrequestraise());

				sb.append("<br></br>");
				sb.append("<hr></hr>");
				sb.append("<br></br>");

				sb.append("<span>");
				sb.append("Average Rating Of Requests Raised By All Requesters:&nbsp;&nbsp;&nbsp;");
				sb.append("</span>");
				sb.append(monthlysummeryemailVoDB.getFeedbacktoraise());

				sb.append("<br></br>");
				sb.append("<hr></hr>");
				sb.append("<br></br>");

			}

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


	public void sendmailMonthly(String To, String emailid) throws Exception {

		StringBuffer sb = new StringBuffer();
		try {

			Email from = new Email(SearchConstants.FROM_ADD);
			String subject = "Welcome To Collabor8  " + emailid;
			Email to = new Email(emailid);
			Content content = new Content("text/html", monthlyGridContent(To, emailid));
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
	
/*
	public static void main(String args[]) throws Exception
	{
		BigInteger emailList1=null;
		BigInteger emailList2=null;
		Monthlyrequestsummaryforadmin  gr=new Monthlyrequestsummaryforadmin();
		
		gr.sendmailMonthly("hemantraghav012@gmail.com","hemantraghav012@gmail.com");
		
	
		
	}	
	*/
	
		
	}


