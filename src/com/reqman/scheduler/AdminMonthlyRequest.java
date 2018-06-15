package com.reqman.scheduler;

import java.util.List;

import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.util.Monthlyemail;
import com.reqman.util.Monthlyrequestsummaryforadmin;

public class AdminMonthlyRequest {

public void request(){
		
	Monthlyrequestsummaryforadmin rm= new Monthlyrequestsummaryforadmin();	
			
			try {
				GetRolequery reinf = new GetRolequery();
				List<String> emailList=reinf.AllAccountAdmin();
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
