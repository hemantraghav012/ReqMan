package com.reqman.scheduler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.reqman.daoimpl.RequesttypeMasterImpl;
import com.reqman.util.SessionUtils;
import com.reqman.util.requestemail;

public class PendingRequestJob implements Job 
{
	public void execute(JobExecutionContext context)
		throws JobExecutionException 
	{
//	ArrayList<String> alluseremailid=new ArrayList<String>();
	
	requestemail rm= new requestemail();	
		
		try {
			RequesttypeMasterImpl reinf = new RequesttypeMasterImpl();
			List<String> emailList=reinf.AllUser();
	       System.out.println("requestemail--"+emailList );
	       for(String userName: emailList){
			rm.friendemail(userName,userName);
	       }
			System.out.println("PendingRequestJob12");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("PendingRequestJob ");
		

	}
}
