package com.reqman.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dateconverter {

	 
	
	 public static String convertTimeToStringhhmmss(Date date) {
			// DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			 
		        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy H:mm a");
		        String newtime = null;
		        try {
		            newtime = df.format(date);
		            //

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return newtime;

		    }
	 
	 
	 public static String convertDateToStringDDMMDDYYYY(Date date) {
					 
		        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		        String newdate = null;
		        try {
		            newdate = df.format(date);
		          
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return newdate;

		    }
	 public static Date getPreToPreMonthDate(Date date){
		    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		    Calendar cal = Calendar.getInstance();  
		    cal.setTime(date);  
		     
		    cal.add(Calendar.MONTH, -1);

		    Date preMonthDate = cal.getTime();
			return preMonthDate;  
		   
		}
	 
}
