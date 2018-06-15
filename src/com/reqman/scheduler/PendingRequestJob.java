package com.reqman.scheduler;


import java.util.List;
import com.reqman.daoimpl.query.GetRolequery;
import com.reqman.util.requestemail;

public class PendingRequestJob 
{
	public void request(){
	
	requestemail rm= new requestemail();	
		
		try {
			GetRolequery reinf = new GetRolequery();
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
