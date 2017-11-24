package com.reqman.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateconverter {

	 public static String convertDateToStringDDMMDDYYYY(Date date) {
		// DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
		 
	        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	        String newdate = null;
	        try {
	            newdate = df.format(date);
	            //

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return newdate;

	    }
	
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
}
