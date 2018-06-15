package com.reqman.scheduler;

import java.util.List;

import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.util.Monthlyemail;


public class MonthlyRequestJob {

	public void request(){
		
		Monthlyemail rm= new Monthlyemail();	
			
			try {
				GetRolequery reinf = new GetRolequery();
				List<String> emailList=reinf.AllUser();
		       System.out.println("requestemail--"+emailList );
		       for(String userName: emailList){
				rm.sendmailMonthly(userName,userName);
		       }
				System.out.println("PendingRequestJob12");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("PendingRequestJob ");
			

		}
	}
