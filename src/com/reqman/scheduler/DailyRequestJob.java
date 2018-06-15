package com.reqman.scheduler;

import java.util.List;

import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.util.DailySendemail;
import com.reqman.util.Monthlyemail;

public class DailyRequestJob {

public void request(){
		
	DailySendemail rm= new DailySendemail();	
			
			try {
				GetRolequery reinf = new GetRolequery();
				List<String> emailList=reinf.getusersincompleatedrequest();
		       System.out.println("requestemail--"+emailList );
		       for(String userName: emailList){
				rm.sendmaildaily(userName,userName);
		       }
				System.out.println("PendingRequestJob12");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("PendingRequestJob ");
			

		}
	}
